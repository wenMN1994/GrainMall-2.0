package com.grain.mall.product.web;

import com.grain.mall.product.service.SkuInfoService;
import com.grain.mall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/24 15:53
 * @description：商品详情页
 * @modified By：
 * @version: $
 */
@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId){
        SkuItemVo vo = skuInfoService.item(skuId);
        return "item";
    }
}
