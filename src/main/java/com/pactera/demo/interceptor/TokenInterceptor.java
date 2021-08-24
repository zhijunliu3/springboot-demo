package com.pactera.demo.interceptor;

import com.pactera.demo.common.exception.FASErrorCodeEnum;
import com.pactera.demo.common.exception.FASException;
import com.pactera.demo.context.UserContextHolder;
import com.pactera.demo.user.entity.UserDO;
import com.pactera.demo.user.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    TokenMapper tokenMapper;

//    @Autowired
//    ValueOperations<String, String> valueOperations;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws FASException {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            String token = request.getHeader("token");
            UserDO user = tokenMapper.getUserByToken(token);
            if(null == user){
                throw new FASException(FASErrorCodeEnum.ERROR_90001);
            }else{
                UserContextHolder.holder.set(user);
//                valueOperations.set(token, JSON.toJSONString(user), 2, TimeUnit.HOURS);
            }
        }
        return true;
    }
}
