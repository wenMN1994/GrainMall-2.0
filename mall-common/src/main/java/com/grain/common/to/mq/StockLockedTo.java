package com.grain.common.to.mq;

import lombok.Data;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/1 17:40
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class StockLockedTo {
    /**
     * 库存工作单的id
     */
    private Long id;
    /**
     * 工作单详情
     */
    private StockDetailTo detail;
}
