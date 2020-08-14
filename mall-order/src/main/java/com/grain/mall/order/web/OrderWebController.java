package com.grain.mall.order.web;

import com.grain.mall.order.service.OrderService;
import com.grain.mall.order.vo.OrderConfirmVo;
import com.grain.mall.order.vo.SubmitOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.util.concurrent.ExecutionException;

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

    @Autowired
    OrderService orderService;

    /**
     * 去结算页
     * @param model
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", confirmVo);
        return "confirm";
    }

    /**
     * 下单功能
     * @param vo
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(SubmitOrderVo vo){

        // 下单：去创建订单，验令牌，验价格，锁库存......
        // 下单成功跳转到支付选择页
        // 下单失败回到订单确认页重新确认订单信息
        System.out.println("订单提交的数据..."+vo.toString());
        return null;
    }
}
