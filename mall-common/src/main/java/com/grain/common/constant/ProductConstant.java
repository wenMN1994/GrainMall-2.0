package com.grain.common.constant;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/25 22:05
 * @description：商品常量
 * @modified By：
 * @version: $
 */
public class ProductConstant {

    public enum  AttrEnum{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String msg;

        AttrEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
