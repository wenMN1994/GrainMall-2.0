package com.grain.mall.product.feign;

import com.grain.common.utils.R;
import com.grain.mall.product.feign.fallback.SeckillFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/5 16:36
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient(name = "mall-seckill", fallback = SeckillFeignServiceFallback.class)
public interface SeckillFeignService {

    @GetMapping("/sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
