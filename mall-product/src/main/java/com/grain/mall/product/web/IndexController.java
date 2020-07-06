package com.grain.mall.product.web;

import com.grain.mall.product.entity.CategoryEntity;
import com.grain.mall.product.service.CategoryService;
import com.grain.mall.product.vo.CategoryTwoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/6 13:27
 * @description：商城首页控制器
 * @modified By：
 * @version: $
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        // 获取一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevelOneCategorys();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<CategoryTwoVo>> getCategoryJson(){

        Map<String, List<CategoryTwoVo>> catalogJson = categoryService.getCategoryJson();
        return catalogJson;
    }
}
