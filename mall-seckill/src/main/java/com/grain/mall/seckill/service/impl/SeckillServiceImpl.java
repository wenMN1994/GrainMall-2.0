package com.grain.mall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.grain.common.to.mq.SeckillOrderTo;
import com.grain.common.utils.R;
import com.grain.common.vo.MemberRespVo;
import com.grain.mall.seckill.feign.CouponFeignService;
import com.grain.mall.seckill.feign.ProductFeignService;
import com.grain.mall.seckill.interceptor.LoginUserInterceptor;
import com.grain.mall.seckill.service.SeckillService;
import com.grain.mall.seckill.to.SeckillSkuRedisTo;
import com.grain.mall.seckill.vo.SeckillSessionsWithSkus;
import com.grain.mall.seckill.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 17:26
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";

    private final String SKUKILL_CACHE_PREFIX = "seckill:skus:";

    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public void uploadSeckillSkuLatest3Days() {
        // 扫描最近三天需要参与秒杀的活动
        R session = couponFeignService.getLates3DaySession();
        if(session.getCode() == 0){
            // 上架商品
            List<SeckillSessionsWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            // 缓存到redis
            // 1、缓存活动信息
            saveSessionInfos(sessionData);
            // 2、缓存活动的关联商品信息
            saveSessionSkuInfos(sessionData);
        }

    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        // 1、确定当前时间属于哪个秒杀场次
        long time = new Date().getTime();
        Set<String> keys = stringRedisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
        for (String key : keys) {
            String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
            String[] split = replace.split("_");
            Long start = Long.parseLong(split[0]);
            Long end = Long.parseLong(split[1]);
            if(time >= start && time <= end){
                // 2、获取这个秒杀场次需要的所有商品
                List<String> range = stringRedisTemplate.opsForList().range(key,-100,100);
                BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                List<String> list = hashOps.multiGet(range);
                if(list != null){
                    List<SeckillSkuRedisTo> collect = list.stream().map(item -> {
                        SeckillSkuRedisTo redis = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
                        // redis.setRandomCode(null); // 当前秒杀开始就需要随机码
                        return redis;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }


        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        // 1、找到所有需要参与秒杀的商品的key
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if(keys != null && keys.size() > 0){
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if(Pattern.matches(regx,key)){
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
                    // 随机码
                    long currentTime = new Date().getTime();
                    if(currentTime >= skuRedisTo.getStartTime() && currentTime <= skuRedisTo.getEndTime()){

                    } else {
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
            }
        }
        return null;
    }

    // TODO 上架秒杀商品的时候，每一个数据都要设置过期时间
    @Override
    public String kill(String killId, String key, Integer num) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        // 1、获取当前秒杀商品的详细信息
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

        String json = hashOps.get(killId);
        if(StringUtils.isEmpty(json)){
            return null;
        }else {
            SeckillSkuRedisTo redis = JSON.parseObject(json, SeckillSkuRedisTo.class);
            // 检验合法性
            Long startTime = redis.getStartTime();
            Long endTime = redis.getEndTime();
            long time = new Date().getTime();
            long ttl = endTime - startTime;
            // 1、校验时间合法性
            if(time >= startTime && time <= endTime){
                // 2、检验随机码和商品id
                String randomCode = redis.getRandomCode();
                String skuId = redis.getPromotionSessionId() + "_" + redis.getSkuId();
                if(randomCode.equals(key) && killId.equals(skuId)){
                    // 3、验证购物数量是否合理
                    if(num <= redis.getSeckillLimit()){
                        // 4、验证这个人是否已经购买过。幂等性；只要秒杀成功，就去redis占位。userId_SessionId_skuId
                        String redisKey = memberRespVo.getId()+"_"+skuId;
                        // 自动过期
                        Boolean isInRedis = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        if(isInRedis){
                            // 占位成功说明从来没有买过
                            RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                            boolean tryAcquire = semaphore.tryAcquire(num);
                            if(tryAcquire){
                                // 秒杀成功，快速下单，发送MQ消息
                                String orderSn = IdWorker.getTimeId();
                                SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                                seckillOrderTo.setOrderSn(orderSn);
                                seckillOrderTo.setMemberId(memberRespVo.getId());
                                seckillOrderTo.setNum(num);
                                seckillOrderTo.setPromotionSessionId(redis.getPromotionSessionId());
                                seckillOrderTo.setSkuId(redis.getSkuId());
                                seckillOrderTo.setSeckillPrice(redis.getSeckillPrice());
                                log.info("准备发送MQ消息");
                                rabbitTemplate.convertAndSend("order-event-exchange","order.seckill.order",seckillOrderTo);
                                return orderSn;
                            }
                            return null;
                        }else {
                            // 说明已经购买过
                            return null;
                        }
                    }
                }else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return null;
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
            Boolean hasKey = stringRedisTemplate.hasKey(key);
            if(!hasKey){
                List<String> collect = session.getRelationSkuEntities().stream().map(item ->
                        item.getPromotionSessionId().toString()+"_"+item.getSkuId().toString()
                ).collect(Collectors.toList());
                // 缓存活动信息
                stringRedisTemplate.opsForList().leftPushAll(key,collect);
            }

        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session -> {
            // 准备hash操作
            BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkuEntities().stream().forEach(seckillSkuVo -> {
                // 秒杀随机码
                String token = UUID.randomUUID().toString().replace("-", "");

                if(!ops.hasKey(seckillSkuVo.getPromotionSessionId().toString()+"_"+seckillSkuVo.getSkuId().toString())){
                    // 缓存商品
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                    // 1、sku的基本信息
                    R skuInfo = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                    if(skuInfo.getCode() == 0){
                        SkuInfoVo info = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        redisTo.setSkuInfo(info);
                    }
                    // 2、sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo, redisTo);
                    // 3、设置上当前商品的秒杀时间信息
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());
                    redisTo.setRandomCode(token);
                    String jsonString = JSON.toJSONString(redisTo);
                    ops.put(seckillSkuVo.getPromotionSessionId().toString()+"_"+seckillSkuVo.getSkuId().toString(), jsonString);
                    // 使用库存作为分布式信号量
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    // 商品可以秒杀的数量作为信号量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }
            });
        });
    }

}
