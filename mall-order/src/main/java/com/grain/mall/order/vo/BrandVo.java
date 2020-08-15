package com.grain.mall.order.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.grain.common.valid.AddGroup;
import com.grain.common.valid.ListValue;
import com.grain.common.valid.UpdateGroup;
import com.grain.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/15 21:23
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class BrandVo {

    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌logo地址
     */
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    private Integer showStatus;
    /**
     * 检索首字母
     */
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;
}
