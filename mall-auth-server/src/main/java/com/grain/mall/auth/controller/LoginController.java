package com.grain.mall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.grain.common.constant.AuthServerConstant;
import com.grain.common.exception.BizCodeEnum;
import com.grain.common.utils.R;
import com.grain.common.vo.MemberRespVo;
import com.grain.mall.auth.feign.MemberFeignService;
import com.grain.mall.auth.feign.ThirdPartFeignService;
import com.grain.mall.auth.vo.UserLoginVo;
import com.grain.mall.auth.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 验证码发送
     * @param phone
     * @return
     */
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
        String code = UUID.randomUUID().toString().substring(0, 5);
        String substring = code+"_"+System.currentTimeMillis();
        // redis缓存验证码
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, substring,3, TimeUnit.MINUTES);
        thirdPartFeignService.sendCode(phone,code);
        return R.ok();
    }

    /**
     * 会员注册
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错转发到注册页
            return "redirect:http://auth.grainmall.com/register.html";
        }

        // 1、校验验证码
        String code = vo.getCode();
        String s = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if(StringUtils.isEmpty(s)){
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错转发到注册页
            return "redirect:http://auth.grainmall.com/register.html";
        }else{
            if(code.equals(s.split("_")[0])){
                // 删除redis中的验证码
                stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                // 验证码正确。注册，调用远程服务
                R r = memberFeignService.register(vo);
                if(r.getCode() == 0){
                    // 注册成功返回首页，回到登录页
                    return "redirect:http://auth.grainmall.com/login.html";
                } else {
                    // 失败
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData("msg", new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.grainmall.com/register.html";
                }
            }else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                // 校验出错转发到注册页
                return "redirect:http://auth.grainmall.com/register.html";
            }
        }
    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session){
        Object sessionAttribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(sessionAttribute == null){
            //没登录跳转登录页
            return "login";
        }else {
            return "redirect:http://grainmall.com";
        }
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session){

        // 调用远程服务登录
        R login = memberFeignService.login(vo);
        if(login.getCode() == 0){
            // 登录成功处理
            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {
            });
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://grainmall.com";
        } else {
            // 登录失败
            Map<String,String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.grainmall.com/login.html";
        }

    }
}
