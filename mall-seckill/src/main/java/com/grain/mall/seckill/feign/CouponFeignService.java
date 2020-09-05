package com.grain.mall.seckill.feign;

import com.grain.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 17:34
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient("mall-coupon")
public interface CouponFeignService {

    /**
     * 获取最近3天秒杀的商品
     * @return
     */
    @GetMapping("/coupon/seckillsession/lates3DaySession")
    R getLates3DaySession();

    @RequestMapping("/info/{id}")
   R info(@PathVariable("id") Long id);
}
