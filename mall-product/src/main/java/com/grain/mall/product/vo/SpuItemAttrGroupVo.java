package com.grain.mall.product.vo;

import com.grain.mall.product.vo.spusave.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/25 15:14
 * @description：
 * @modified By：
 * @version: $
 */
@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
