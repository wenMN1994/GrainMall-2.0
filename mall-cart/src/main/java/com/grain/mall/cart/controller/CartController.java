package com.grain.mall.cart.controller;

import com.grain.mall.cart.interceptor.CartInterceptor;
import com.grain.mall.cart.service.CartService;
import com.grain.mall.cart.to.UserInfoTo;
import com.grain.mall.cart.vo.Cart;
import com.grain.mall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

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

    @Autowired
    CartService cartService;

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
    public String cartListString(Model model) throws ExecutionException, InterruptedException {

        Cart cart = cartService.getCart();
        model.addAttribute("cartInfo", cart);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {

        cartService.addToCat(skuId, num);

        redirectAttributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.grainmall.com/addToCartSuccess.html";
    }

    /**
     * 跳转到成功页
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model){
        // 重定向到成功页面，再次查询购物车数据即可
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
        cartService.checkItem(skuId, check);
        return "redirect:http://cart.grainmall.com/cart.html";
    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.grainmall.com/cart.html";
    }
}
