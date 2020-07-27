package com.grain.mall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/27 21:06
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class MallWebConfig implements WebMvcConfigurer {

    /**
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         *         @GetMapping("/login.html")
         *         public String loginPage(){
         *
         *             return "login";
         *         }
         */
        registry.addViewController("/login.html").setViewName("login");

        /**
         *     @GetMapping("/register.html")
         *     public String registerPage(){
         *
         *         return "register";
         *     }
         */
        registry.addViewController("/register.html").setViewName("register");
    }
}
