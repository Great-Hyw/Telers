package com.java.Config;

import com.java.Interceptor.loginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
 *
 * @Description: 拦截器配置类
 *
 * */
@Configuration
public class WebCongin implements WebMvcConfigurer {

    @Autowired
    private loginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)//添加拦截器
                .addPathPatterns("/**")//拦截所有
                .excludePathPatterns("/login");//登录接口不拦截
    }
}
