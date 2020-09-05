package com.grain.mall.seckill.scheduled;

import com.grain.mall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 17:02
 * @description：秒杀商品定时上架
 *                  每天晚上3点，上架最近三天需要秒杀的商品
 * @modified By：
 * @version: $
 */
@Slf4j
@Service
public class SeckillSkuScheduled {

    private final String UPLOAD_LOCK = "seckill:upload:lock:";

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedissonClient redissonClient;

    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillSkuLatest3Days(){
        // 重复上架无需处理
        log.info("上架秒杀的商品信息...");
        // 分布式锁.锁的业务执行完成，状态更新完成。释放锁以后。其他人获取到就会拿到最新的状态
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploadSeckillSkuLatest3Days();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
