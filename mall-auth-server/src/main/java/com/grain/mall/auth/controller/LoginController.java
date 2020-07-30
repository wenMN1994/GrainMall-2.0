package com.grain.mall.auth.controller;

import com.grain.common.constant.AuthServerConstant;
import com.grain.common.exception.BizCodeEnum;
import com.grain.common.utils.R;
import com.grain.mall.auth.feign.ThirdPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/27 19:37
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class LoginController {

    @Autowired
    ThirdPartService thirdPartService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone){
        // 1、接口防刷

        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if(!StringUtils.isEmpty(redisCode)){
            long l = Long.parseLong(redisCode.split("_")[1]);
            if(System.currentTimeMillis() - l < 60000){
                // 60秒内不能再发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        // 2、验证码的再次验证 redis
        String code = UUID.randomUUID().toString().substring(0, 5)+"_"+System.currentTimeMillis();
        // redis缓存验证码
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code,3, TimeUnit.MINUTES);
        thirdPartService.sendCode(phone,code.split("_")[0]);
        return R.ok();
    }
}
