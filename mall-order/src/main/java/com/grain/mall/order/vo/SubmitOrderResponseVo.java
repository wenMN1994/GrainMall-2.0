package com.grain.mall.order.vo;

import com.grain.mall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/14 13:32
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;
    private Integer code; // 0：成功  错误状态码
}
