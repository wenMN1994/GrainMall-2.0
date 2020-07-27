package com.grain.mall.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/27 19:37
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class LoginController {

    @GetMapping("/login.html")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/register.html")
    public String registerPage(){

        return "register";
    }
}
