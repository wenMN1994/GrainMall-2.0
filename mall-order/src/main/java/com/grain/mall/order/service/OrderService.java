package com.grain.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:49:52
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

