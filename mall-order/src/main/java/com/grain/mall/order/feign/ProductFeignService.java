package com.grain.mall.order.feign;

import com.grain.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/14 17:01
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient("mall-product")
public interface ProductFeignService {

    @RequestMapping("/product/brand/info/{brandId}")
    R getBrandById(@PathVariable("brandId") Long brandId);

    @GetMapping("/product/spuinfo/skuId/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);
}
