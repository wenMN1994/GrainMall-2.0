package com.grain.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/28 21:23
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}
