package com.grain.mall.cart.controller;

import com.grain.mall.cart.interceptor.CartInterceptor;
import com.grain.mall.cart.to.UserInfoTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/2 19:17
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class CartController {

    /**
     * 浏览器有一个cookie；user-key；标识用户身份，一个月后过期；
     * 如果第一次使用购物车功能，都会给一个临时的用户身份；
     * 浏览器以后保存，每次访问都会带着这个cookie；
     *
     * 登录：session有
     * 没登录：按照cookie里面带来的user-key来做。
     * 第一次：如果没有临时用户，帮忙创建一个临时用户
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListString(){

        // 1、快速得到用户信息，id，user-key
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println(userInfoTo);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(){

        return "success";
    }
}
