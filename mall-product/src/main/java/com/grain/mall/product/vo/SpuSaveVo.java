package com.grain.mall.product.vo;

import com.grain.mall.product.vo.spusave.BaseAttrs;
import com.grain.mall.product.vo.spusave.Bounds;
import com.grain.mall.product.vo.spusave.Skus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/28 15:45
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;

}