package com.grain.mall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/6 22:08
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class OrderWebController {

    @GetMapping("/toTrade")
    public String toTrade(){

        return "confirm";
    }
}
