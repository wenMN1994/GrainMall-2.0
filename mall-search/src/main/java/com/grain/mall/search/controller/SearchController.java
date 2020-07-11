package com.grain.mall.search.controller;

import org.springframework.stereotype.Controller;
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

    @GetMapping("/list.html")
    public String listPage(){

        return "list";
    }
}
