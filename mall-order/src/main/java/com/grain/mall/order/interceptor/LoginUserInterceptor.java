package com.grain.mall.order.interceptor;

import com.grain.common.constant.AuthServerConstant;
import com.grain.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/8 8:51
 * @description：
 * @modified By：
 * @version: $
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean matchStatus = antPathMatcher.match("/order/order/status/**", uri);
        boolean matchPayed = antPathMatcher.match("/payed/notify", uri);
        if(matchStatus || matchPayed){
            return true;
        }

        MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if(attribute != null){
            loginUser.set(attribute);
            return true;
        }else {
            // 没登录就去登录
            request.getSession().setAttribute("msg","请先进行登录");
            response.sendRedirect("http://auth.grainmall.com/login.html");
            return false;
        }
    }
}
