package com.grain.mall.ware.vo;

import lombok.Data;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/16 22:01
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class LockStockResult {

    private Long skuId;
    private Integer num;
    private Boolean locked;
}
