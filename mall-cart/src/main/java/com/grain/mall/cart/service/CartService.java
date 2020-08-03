package com.grain.mall.cart.service;

import com.grain.mall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/2 19:11
 * @description：
 * @modified By：
 * @version: $
 */
public interface CartService {

    /**
     * 添加商品到购物车
     * @param skuId
     * @param num
     * @return
     */
    CartItem addToCat(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 根据skuId查询购物车中购物项
     * @param skuId
     * @return
     */
    CartItem getCartItem(Long skuId);
}
