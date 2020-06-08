package com.grain.mall.order.dao;

import com.grain.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:49:52
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
