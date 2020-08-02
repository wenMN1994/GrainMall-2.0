package com.grain.mall.cart.to;

import lombok.Data;
import lombok.ToString;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/2 20:35
 * @description：
 * @modified By：
 * @version: $
 */
@ToString
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey;

    private boolean tempUser = false;
}
