package org.redrock.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: Shiina18
 * @date: 2019/3/15 22:10
 * @description:
 */
@Component
@Aspect
public class TestAspect {
    @Pointcut("execution(public * org.redrock.aopdemo.controller.*.*(..))")
    public void test() {
    }

    @Before("test()")
    public void before() {
        System.out.println("aop-----before");
    }

    @After("test()")
    public void after() {
        System.out.println("aop-----after");
    }

    @AfterReturning(value = "test()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj) {
        System.out.println("aop-----afterReturning");
    }

    @Around("test()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object res = null;
        System.out.println("aop-----around 1");
        res = pjp.proceed();
        System.out.println("aop-----around 2");
        return res;
    }

    @AfterThrowing(value = "test()", throwing = "e")
    public void afterThrowing(Throwable e) {
        e.printStackTrace();
    }

}
