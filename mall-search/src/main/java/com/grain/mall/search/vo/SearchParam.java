package com.grain.mall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/12 10:12
 * @description：封装页面所有可能传递过来的查询条件
 * @modified By：
 * @version: $
 */
@Data
public class SearchParam {

    private String keyword; // 页面传递过来的全文匹配关键字

    private Long category3Id; // 三级分类id

    private String sort; // 排序条件

    private Integer hasStock; // 是否显示有货

    private String skuPrice; // 价格区间查询

    private List<Long> brandId; // 品牌id，可以多选

    private List<String> attrs; // 按照属性进行筛选

    private Integer pageNum; //页码
}
