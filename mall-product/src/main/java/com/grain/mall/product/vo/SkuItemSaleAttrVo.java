package com.grain.mall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/25 15:15
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SkuItemSaleAttrVo {
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性值
     */
    private List<String> attrValues;
}
