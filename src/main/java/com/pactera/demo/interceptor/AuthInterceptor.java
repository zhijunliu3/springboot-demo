package com.pactera.demo.interceptor;

import com.pactera.demo.annotation.AuthVerification;
import com.pactera.demo.common.exception.FASErrorCodeEnum;
import com.pactera.demo.common.exception.FASException;
import com.pactera.demo.user.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws FASException {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            AuthVerification authVerification = AnnotationUtils.findAnnotation(method.getMethod(), AuthVerification.class);
            if(authVerification != null){
                String authCode = authVerification.value();
                String token = request.getHeader("token");
                List<String> authCodes = roleMapper.getAuthCodesByToken(token);
                Optional<String> optional = authCodes.stream().filter(auth -> authCode.equals(auth)).findAny();
                if(!optional.isPresent()){
                    throw new FASException(FASErrorCodeEnum.ERROR_90002);
                }
            }
        }
        return true;
    }
}
