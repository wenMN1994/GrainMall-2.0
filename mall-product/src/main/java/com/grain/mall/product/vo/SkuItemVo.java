package com.grain.mall.product.vo;

import com.grain.mall.product.entity.SkuImagesEntity;
import com.grain.mall.product.entity.SkuInfoEntity;
import com.grain.mall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/24 16:17
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SkuItemVo {

    // 1、sku基本信息获取 pms_sku_info
    SkuInfoEntity info;

    boolean hasStock = true;

    // 2、sku图片信息 pms_sku_images
    List<SkuImagesEntity> images;

    // 3、获取spu的销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;

    // 4、获取spu的介绍 pms_spu_info_desc
    SpuInfoDescEntity desp;

    // 5、获取spu的规格参数
    List<SpuItemAttrGroupVo> groupAttrs;

    // 6、当前商品的秒杀优惠信息
    SeckillInfoVo seckillInfo;
}
