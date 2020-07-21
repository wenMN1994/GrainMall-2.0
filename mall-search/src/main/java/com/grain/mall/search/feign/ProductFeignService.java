package com.grain.mall.search.feign;

import com.grain.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/20 20:00
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient("mall-product")
public interface ProductFeignService {

    @GetMapping("/product/attr/info/{attrId}")
    public R attrInfo(@PathVariable("attrId") Long attrId);

    @RequestMapping("/product/brand/infos")
    public R brandsInfo(@RequestParam("brandIds") List<Long> brandIds);
}
