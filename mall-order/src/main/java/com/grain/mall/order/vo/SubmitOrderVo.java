package com.grain.mall.order.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/14 11:09
 * @description：封装订单提交的数据
 * @modified By：
 * @version: $
 */
@ToString
@Data
public class SubmitOrderVo {

    private Long addrId; // 收货地址id
    private Integer payType; // 支付方式
    // 无需提交需要购买的商品，去购物车再获取一遍选中的商品
    // 优惠、发票
    private String orderToken; // 防重令牌
    private BigDecimal payPrice; // 应付价格 验价
    private String note; // 订单备注
    // 用户相关信息，直接去session去除登录的用户
}
