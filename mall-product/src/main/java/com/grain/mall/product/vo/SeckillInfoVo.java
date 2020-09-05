package com.grain.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/5 16:40
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SeckillInfoVo {

    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * 商品秒杀随机码
     */
    private String randomCode;

    /**
     * 当前商品开始时间
     */
    private Long startTime;

    /**
     * 当前商品结束时间
     */
    private Long endTime;
}
