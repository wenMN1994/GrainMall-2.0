package com.grain.mall.search.vo;

import com.grain.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/12 10:53
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SearchResult {

    private List<SkuEsModel> products; // 查询到的所有商品信息

    /**
     * 以下是分页信息
     */
    private Integer pageNum;
    private Long total;
    private Integer totalPages;
    private List<Integer> pageNavs;

    private List<BrandVo> brands; // 当前查询到的结果，所有涉及到的品牌

    private List<CatalogVo> catalogs; // 当前查询到的结果，所有涉及到的分类

    private List<AttrVo> attrs; // 当前查询到的结果，所有涉及到的属性

    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }
    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;
    }


    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

}
