package com.java.Interceptor;


import com.java.Util.CullentUtil;
import com.java.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
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
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {

            response.setStatus(401);
            log.info("zheli a ");
            return false;//不放行
        }

        //令牌解析
        try {
            Claims claims = jwtUtil.praseToken(token);
            Integer id = (Integer) claims.get("id");
            CullentUtil.set(id);

        } catch (Exception e) {//令牌非法返回401
            log.info("token非法");
            response.setStatus(401);
            return false;//不放行
        }
        return true;//解析成功放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        CullentUtil.remove();
    }

}
