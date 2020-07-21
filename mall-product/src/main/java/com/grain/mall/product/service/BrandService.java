package com.grain.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.product.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-05-23 15:04:44
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);

    List<BrandEntity> getBrandsByIds(List<Long> brandIds);
}

