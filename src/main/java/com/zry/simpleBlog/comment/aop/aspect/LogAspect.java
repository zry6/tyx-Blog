package com.zry.simpleBlog.comment.aop.aspect;

import com.zry.simpleBlog.comment.aop.annotations.LoWeb;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * 日志切面
 * 用切面AOP  日志信息 访问者的ip ...
 * 这里是针对注解LogAnnotation的
 * zry
 * @author 14470
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 此处的切点是注解的方式
     * 只要出现 @LogAnnotation注解都会进入
     */
    @Pointcut("@annotation(com.zry.simpleBlog.comment.aop.annotations.LoWeb)")
    public void logPointCut() {
    }

    /**
     * 环绕增强,相当于MethodInterceptor
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行方法之前
        long beginTime = System.currentTimeMillis();
        doBeforeLog(point);

        //执行方法
        Object result = point.proceed();
        log.info("Result : {}", result);
        //执行时长(毫秒)  方法之后
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveSysLog(point, time);
        } catch (Exception e) {
            log.error("logPointCut,exception:{}", e, e);
        }
        return result;
    }

    /**
     * 方法执行之前的日志
     */
    private void doBeforeLog(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();
        LoWeb loWeb = method.getAnnotation(LoWeb.class);

        String operation = null;
        if (loWeb != null) {
            //获取注解上的描述
            operation = loWeb.title() + "-" + loWeb.action();
            log.info("operationAnnotation : {}", operation);
        }

        //获取请求属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();


        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = point.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        log.info("Request : {}", requestLog);
    }


    /**
     * 方法执行之后
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {

        Signature signature = joinPoint.getSignature();

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数

        log.info("请求{}.{}耗时{}毫秒", className, methodName, time);
    }

    private static class RequestLog {
        private final String url;
        private final String ip;
        private final String classMethod;
        private final Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
