package com.grain.mall.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 18:34
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SeckillSessionsWithSkus {

    /**
     * id
     */
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 每日开始时间
     */
    private Date startTime;
    /**
     * 每日结束时间
     */
    private Date endTime;
    /**
     * 启用状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    private List<SeckillSkuVo> relationSkuEntities;
}
