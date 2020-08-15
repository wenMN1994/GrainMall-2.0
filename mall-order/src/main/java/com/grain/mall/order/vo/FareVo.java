package com.grain.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/14 14:53
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class FareVo {

    private MemberAddressVo address;
    private BigDecimal fare;
}
