package com.grain.mall.seckill.service;

import com.grain.mall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 17:25
 * @description：
 * @modified By：
 * @version: $
 */
public interface SeckillService {
    /**
     * 上架最近3天的秒杀商品
     */
    void uploadSeckillSkuLatest3Days();

    /**
     *
     * @return
     */
    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    /**
     *
     * @param skuId
     * @return
     */
    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);
}
