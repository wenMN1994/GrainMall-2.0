package com.grain.mall.member.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/1 9:26
 * @description：
 * @modified By：
 * @version: $
 */
public class MemberNotExistException extends RuntimeException {

    public MemberNotExistException() {
        super("用户不存在");
    }
}
