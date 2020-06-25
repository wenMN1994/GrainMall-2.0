package com.grain.mall.product.vo;

import lombok.Data;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/25 17:15
 * @description：规格参数列表响应Vo
 * @modified By：
 * @version: $
 */
@Data
public class AttrRespVo extends AttrVo {

    /**
     * 所属分类名字
     */
    private String catelogName;

    /**
     * 所属分组名字
     */
    private String groupName;

    /**
     * 分类完整路径
     */
    private Long[] catelogPath;

}
