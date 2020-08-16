package com.grain.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/16 21:55
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class WareSkuLockVo {

    private String orderSn; // 订单号

    private List<OrderItemVo> locks; // 需要锁住的所有库存信息
}
