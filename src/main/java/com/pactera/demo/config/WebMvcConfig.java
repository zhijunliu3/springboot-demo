package com.pactera.demo.config;

import com.pactera.demo.interceptor.AuthInterceptor;
import com.pactera.demo.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: web请求适配器配置
 * @author: LiuZhiJun
 * @create: 2019-10-10 15:44
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public HandlerInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Bean
    public HandlerInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token拦截
//        registry.addInterceptor(tokenInterceptor());
        // 权限拦截
        registry.addInterceptor(authInterceptor());
    }
}
