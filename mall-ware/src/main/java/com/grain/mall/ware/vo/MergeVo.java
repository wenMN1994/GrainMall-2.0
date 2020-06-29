package com.grain.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/29 15:45
 * @description：合并采购单Vo
 * @modified By：
 * @version: $
 */
@Data
public class MergeVo {

    /**
     * 整单id
     */
    private Long purchaseId;

    /**
     * 合并项集合
     */
    private List<Long> items;
}
