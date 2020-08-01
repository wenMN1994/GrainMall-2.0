package com.grain.mall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.grain.common.utils.HttpUtils;
import com.grain.common.vo.SocialUserVo;
import com.grain.common.utils.R;
import com.grain.mall.auth.feign.MemberFeignService;
import com.grain.common.vo.MemberRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/1 14:47
 * @description：处理社交登录请求
 * @modified By：
 * @version: $
 */
@Slf4j
@Controller
public class OAuth2Controller {

    @Value("${social.weibo.app-key}")
    private String clientId;
    @Value("${social.weibo.app-secret}")
    private String clientSecret;
    @Value("${social.weibo.redirect-uri}")
    private String redirectUri;

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String,String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", redirectUri);
        map.put("code", code);
        // 根据code换取accessToken
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), new HashMap<>(), map);

        // 处理
        if(response.getStatusLine().getStatusCode() == 200){
            // 获取到accessToken
            String json = EntityUtils.toString(response.getEntity());
            SocialUserVo socialUserVo = JSON.parseObject(json, SocialUserVo.class);
            // 知道当前是哪个社交用户
            // 当前用户如果是第一次登录网站，自动注册进来（为当前社交用户生成一个会员信息账号，以后这个社交账号就对应指定的会员）
            R oauthLogin = memberFeignService.oauthLogin(socialUserVo);
            if(oauthLogin.getCode() == 0){
                MemberRespVo data = oauthLogin.getData("data", new TypeReference<MemberRespVo>() {
                });
                log.info("登陆成功：用户信息-->{}",data.toString());
                session.setAttribute("loginUser", data);
                return "redirect:http://grainmall.com";
            }else {
                return "redirect:http://auth.grainmall.com/login.html";
            }
        }else {
            return "redirect:http://auth.grainmall.com/login.html";
        }
    }
}
