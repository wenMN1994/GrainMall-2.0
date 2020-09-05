package com.grain.mall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.grain.common.utils.R;
import com.grain.mall.seckill.feign.CouponFeignService;
import com.grain.mall.seckill.feign.ProductFeignService;
import com.grain.mall.seckill.service.SeckillService;
import com.grain.mall.seckill.to.SeckillSkuRedisTo;
import com.grain.mall.seckill.vo.SeckillSessionsWithSkus;
import com.grain.mall.seckill.vo.SeckillSkuVo;
import com.grain.mall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 17:26
 * @description：
 * @modified By：
 * @version: $
 */
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

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
            List<String> collect = session.getRelationSkuEntities().stream().map(item ->
                    item.getSkuId().toString()
            ).collect(Collectors.toList());
            // 缓存活动信息
            stringRedisTemplate.opsForList().leftPushAll(key,collect);
        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session -> {
            // 准备hash操作
            BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkuEntities().stream().forEach(seckillSkuVo -> {
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
                // 4、秒杀随机码
                String token = UUID.randomUUID().toString().replace("-", "");
                redisTo.setRandomCode(token);
                String jsonString = JSON.toJSONString(redisTo);
                // 使用库存作为分布式信号量
                RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                // 商品可以秒杀的数量作为信号量
                semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                ops.put(seckillSkuVo.getSkuId().toString(), jsonString);
            });
        });
    }

}
