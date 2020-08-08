package com.grain.mall.order.config;

import com.grain.mall.order.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/8 8:54
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
    }
}
