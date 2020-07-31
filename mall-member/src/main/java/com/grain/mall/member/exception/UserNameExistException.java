package com.grain.mall.member.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/31 22:22
 * @description：
 * @modified By：
 * @version: $
 */
public class UserNameExistException extends RuntimeException {

    public UserNameExistException() {
        super("用户名已被使用");
    }
}
