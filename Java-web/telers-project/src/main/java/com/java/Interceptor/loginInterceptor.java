package com.java.Interceptor;


import com.java.JWT.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class loginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;//令牌工具类

    /*
    * 拦截器
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取token（是否存在令牌，有进行令牌解析，无则返回401表示未登录）
        String token = response.getHeader("token");
        if (token == null || token.isEmpty()) {

            response.setStatus(401);
            return false;//不放行
        }

        //令牌解析
        try {
           jwtUtil.praseToken(token);
        } catch (Exception e) {//令牌非法返回401
            log.info("token非法");
            response.setStatus(401);
            return false;//不放行
        }
        return true;//解析成功放行
    }

}
