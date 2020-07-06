package com.grain.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.product.entity.CategoryEntity;
import com.grain.mall.product.vo.CategoryTwoVo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-05-23 15:04:44
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 三级分类-查询-递归树形结构
     * @return
     */
    List<CategoryEntity> listWithTree();

    /**
     * 批量删除商品分类
     * @param asList
     */
    void removeMenuByIds(List<Long> asList);

    /**
     * 找到catelogId的完整路径
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    void updateCascade(CategoryEntity category);

    /**
     * 获取一级分类
     * @return
     */
    List<CategoryEntity> getLevelOneCategorys();

    /**
     * 获取二级分类和三级分类JSON
     * @return
     */
    Map<String, List<CategoryTwoVo>> getCategoryJson();
}

