package com.grain.common.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/6/23 15:12
 * @description：状态码
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15：用户
 *  21：库存
 * @modified By：
 * @version: $
 */
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    SMS_CODE_EXCEPTION(10002,"短信验证码频率太高，稍后再试"),
    TO_MANY_REQUEST(10003,"请求流量过大"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    USER_EXIST_EXCEPTION(15001,"用户名已被注册"),
    MOBILE_EXIST_EXCEPTION(15002,"手机号码已被注册"),
    USER_NOT_EXIST_EXCEPTION(15003,"用户不存在"),
    PASSWORD_ERROR_EXCEPTION(15004,"密码错误"),
    OAUTH2_LOGIN_EXCEPTION(15005, "社交登录失败"),
    NO_STOCK_EXCEPTION(21000, "商品库存不足");

    private int code;
    private String msg;
    BizCodeEnum(int code,String msg){
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
