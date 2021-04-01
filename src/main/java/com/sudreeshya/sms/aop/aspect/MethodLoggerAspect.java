package com.sudreeshya.sms.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Component
@Aspect
@Slf4j
public class MethodLoggerAspect {

    @Around("@annotation(com.sudreeshya.sms.aop.annotation.MethodLogger)")
    public void entering(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        log.debug("{} called..", method.getName());
        proceedingJoinPoint.proceed();
    }
}
