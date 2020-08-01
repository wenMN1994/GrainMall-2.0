package com.grain.mall.member.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/1 9:27
 * @description：
 * @modified By：
 * @version: $
 */
public class PasswordErrorException extends RuntimeException {

    public PasswordErrorException() {
        super("密码错误");
    }
}
