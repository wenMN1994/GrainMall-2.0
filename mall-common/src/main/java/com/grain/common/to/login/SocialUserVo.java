package com.grain.common.to.login;

import lombok.Data;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/1 15:19
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SocialUserVo {

    private String accessToken;
    private String remindIn;
    private long expiresIn;
    private String uid;
    private String isRealName;
}
