package com.grain.mall.search.service;

import com.grain.mall.search.vo.SearchParam;
import com.grain.mall.search.vo.SearchResult;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/12 10:27
 * @description：
 * @modified By：
 * @version: $
 */
public interface MallSearchService {

    /**
     *
     * @param param 检索的所有参数
     * @return 检索的结果
     */
    SearchResult search(SearchParam param);
}
