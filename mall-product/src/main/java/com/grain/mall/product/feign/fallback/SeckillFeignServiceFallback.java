package com.grain.mall.product.feign.fallback;

import com.grain.common.exception.BizCodeEnum;
import com.grain.common.utils.R;
import com.grain.mall.product.feign.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/9 17:22
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
@Component
public class SeckillFeignServiceFallback implements SeckillFeignService {
    @Override
    public R getSkuSeckillInfo(Long skuId) {
        log.info("调用熔断方法...getSkuSeckillInfo");
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(),BizCodeEnum.TO_MANY_REQUEST.getMsg());
    }
}
