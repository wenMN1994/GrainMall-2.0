package com.grain.mall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/30 21:17
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserRegisterVo {

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6, max = 18, message = "用户名必须是6-18位字符")
    private String userNum;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码必须是6-18位字符")
    private String password;

    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;
}
