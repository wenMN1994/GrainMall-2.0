package com.grain.mall.seckill.controller;

import com.grain.common.utils.R;
import com.grain.mall.seckill.service.SeckillService;
import com.grain.mall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/5 14:00
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    /**
     * 返回当前时间可以参与的秒杀商品信息
     * @return
     */
    @ResponseBody
    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus(){
        List<SeckillSkuRedisTo> vos = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(vos);
    }

    @ResponseBody
    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId){
        SeckillSkuRedisTo seckillSkuRedisTo = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().setData(seckillSkuRedisTo);
    }

    @GetMapping("/kill")
    public String seckill(@RequestParam("killId") String killId,
                     @RequestParam("key") String key,
                     @RequestParam("num") Integer num,
                     Model model){
        String orderSn = seckillService.kill(killId,key,num);
        model.addAttribute("orderSn",orderSn);
        return "success";
    }
}
