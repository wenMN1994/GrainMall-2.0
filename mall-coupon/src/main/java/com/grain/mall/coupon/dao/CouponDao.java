package com.grain.mall.coupon.dao;

import com.grain.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:07:42
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
