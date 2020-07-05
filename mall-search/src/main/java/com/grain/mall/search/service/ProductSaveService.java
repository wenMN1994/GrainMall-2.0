package com.grain.mall.search.service;

import com.grain.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/5 14:50
 * @description：
 * @modified By：
 * @version: $
 */
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
