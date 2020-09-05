package com.grain.mall.seckill.scheduled;

import com.grain.mall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Autowired
    SeckillService seckillService;

    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillSkuLatest3Days(){
        // 重复上架无需处理
        log.info("上架秒杀的商品信息...");
        seckillService.uploadSeckillSkuLatest3Days();
    }
}
