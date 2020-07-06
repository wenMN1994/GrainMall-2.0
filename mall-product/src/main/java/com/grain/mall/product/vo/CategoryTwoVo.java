package com.grain.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/6 14:18
 * @description：
 * @modified By：
 * @version: $
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryTwoVo {

    private String catalog1Id; // 1级分类id

    private List<CategoryThreeVo> catalog3List; // 3级子分类

    private String id;

    private String name;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CategoryThreeVo {
        private String catalog2Id; // 2级分类id
        private String id;
        private String name;
    }
}
