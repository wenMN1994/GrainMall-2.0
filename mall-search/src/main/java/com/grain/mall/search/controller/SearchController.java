package com.grain.mall.search.controller;

import com.grain.mall.search.service.MallSearchService;
import com.grain.mall.search.vo.SearchParam;
import com.grain.mall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/11 22:53
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model){

        // 1、根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);

        model.addAttribute("result", result);
        return "list";
    }
}
