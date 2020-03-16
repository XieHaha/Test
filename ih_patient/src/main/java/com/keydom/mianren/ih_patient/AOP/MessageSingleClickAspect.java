package com.keydom.mianren.ih_patient.AOP;

import com.keydom.mianren.ih_patient.callback.MessageSingleClick;
import com.keydom.mianren.ih_patient.utils.XClickUtil;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect

public class MessageSingleClickAspect {

    @Pointcut("execution(@com.keydom.mianren.ih_patient.callback.MessageSingleClick * *(..))")
    public boolean messageMethodAnnotated() {
        return false;
    }
    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("messageMethodAnnotated()")
    public boolean messageAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        IMMessage message = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof IMMessage) {
                message = (IMMessage) arg;
                break;
            }
        }
        if (message == null) {
            return false;
        }
        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(MessageSingleClick.class)) {
            return false;
        }
        MessageSingleClick singleClick = method.getAnnotation(MessageSingleClick.class);
        // 判断是否快速点击
        if (!XClickUtil.isFastDoubleClick(message, singleClick.value())) {
            // 不是快速点击，执行原方法
            joinPoint.proceed();
            return false;

        }else{
            return false;
        }
    }
}
