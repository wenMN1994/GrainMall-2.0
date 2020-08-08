package com.grain.mall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/8 9:29
 * @description：
 * @modified By：
 * @version: $
 */
public class OrderConfirmVo {

    // 订单防重令牌
    @Getter@Setter
    String orderToken;

    // 收货地址
    @Getter@Setter
    List<MemberAddressVo> address;

    // 所有选中的购物项
    @Getter@Setter
    List<OrderItemVo> items;

    // 发票记录...

    // 优惠券信息...
    @Getter@Setter
    Integer integration;

    // 订单总额
    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if(items != null){
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum = sum.add(multiply);
            }
        }

        return sum;
    }

    // 应付价格
    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
