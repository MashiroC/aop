package org.redrock.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.redrock.aopdemo.annotation.Cache;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Shiina18
 * @date: 2019/3/15 20:16
 * @description:
 */
@Component
@Aspect
public class CacheAspect {

    private Map<String, Object> cacheMap = new HashMap<>();

    @Pointcut("execution(public * org.redrock.aopdemo.controller.*.*(..))")
    public void cache() {

    }

    @Around("cache()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Cache isCache = method.getDeclaredAnnotation(Cache.class);
        if (isCache != null) {
            String key = method.getName() + "/" + Arrays.toString(pjp.getArgs());
            Object value = cacheMap.get(key);
            if (value != null) {
                return value;
            }
        }
        return pjp.proceed();
    }

    @AfterReturning(value = "cache()", returning = "object")
    public void after(JoinPoint joinPoint, Object object) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Cache isCache = method.getDeclaredAnnotation(Cache.class);
        if (isCache != null) {
            String key = method.getName() + "/" + Arrays.toString(joinPoint.getArgs());
            cacheMap.putIfAbsent(key, object);
        }
    }

}
