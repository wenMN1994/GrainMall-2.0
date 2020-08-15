package com.grain.mall.order.to;

import com.grain.mall.order.entity.OrderEntity;
import com.grain.mall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/14 14:18
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class OrderCreateTo {

    private OrderEntity order;

    private List<OrderItemEntity> orderItems;

    private BigDecimal payPrice; // 订单计算的应付价格

    private BigDecimal fare; // 运费
}
