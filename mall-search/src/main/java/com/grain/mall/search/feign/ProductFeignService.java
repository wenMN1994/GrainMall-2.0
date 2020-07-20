package com.grain.mall.search.feign;

import com.grain.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    // @RequiresPermissions("product:attr:info")
    public R attrInfo(@PathVariable("attrId") Long attrId);
}
