package com.grain.mall.member.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/31 22:23
 * @description：
 * @modified By：
 * @version: $
 */
public class MobileExistException extends RuntimeException {

    public MobileExistException() {
        super("手机号已被注册");
    }
}
