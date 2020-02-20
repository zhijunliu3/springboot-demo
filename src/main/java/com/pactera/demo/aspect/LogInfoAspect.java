package com.pactera.demo.aspect;

import com.pactera.demo.annotation.LogInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogInfoAspect implements Ordered {

    @Pointcut("@annotation(com.pactera.demo.annotation.LogInfo)")
    public void logInfoPointCut() {

    }

    @Around("logInfoPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 获取切入的方法信息
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取切入的方法
        Method method = methodSignature.getMethod();
        // 获取注解
        LogInfo logInfo = method.getDeclaredAnnotation(LogInfo.class);
        try {
            // 执行方法
            Object obj = point.proceed();
            System.out.println(logInfo.value());
            return obj;
        } finally {

        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
