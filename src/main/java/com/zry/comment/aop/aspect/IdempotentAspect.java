package com.zry.comment.aop.aspect;

import com.zry.comment.aop.exception.GlobalException;
import com.zry.comment.annotations.Idempotent;
import com.zry.comment.annotations.IdempotentStrategy;
import com.zry.comment.respBean.RespBeanEnum;
import com.zry.comment.idempotent.IdempotentStrategyContext;
import com.zry.service.RedisService;
import com.zry.comment.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 接口幂等切面
 *
 * @author zry
 * @create 2022-04-27 12:07
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {
    @Resource
    private RedisService redisService;
    @Resource
    private IdempotentStrategyContext idempotentStrategyContext;

    final static String LOCK_VALUE = "1";

    /**
     * 切点，标注了@Idempotent的controller方法
     */
    @Pointcut(value = "@annotation(com.zry.comment.annotations.Idempotent)")
    public void idempotent() {
    }

    @Around("idempotent()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("--------idempotent aspect start-------");
        Object[] args = pjp.getArgs();
        if (null == args || args.length == 0) {
            log.error("args is null，skip idempotent，execute target class");
            return pjp.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Idempotent idempotent = methodSignature.getMethod().getAnnotation(Idempotent.class);
        //注解上的参数
        long timeout = idempotent.timeout();
        IdempotentStrategy strategy = idempotent.strategy();
        //策略模式  获取幂等操作的key
        String keyStr = idempotentStrategyContext.accept(strategy, pjp);
        //如果key为空的话 直接跳过幂等操作
        if (StringUtils.isEmpty(keyStr)) {
            log.error("keyStr is null,skip idempotent,execute target class");
            return pjp.proceed();
        }
        //对key进行一次hash
        String key = MD5Util.md5(keyStr);
        log.info("redis key：{}", key);
        boolean setNxRes = redisService.setNx(key, LOCK_VALUE,  timeout,TimeUnit.SECONDS);

        if (setNxRes) {
            log.info("try lock success,execute target class");
            Object processResult = pjp.proceed();
            log.info("target result:{}", processResult);
            //执行方法，将返回值写入redis
            redisService.setEx(key, processResult, timeout,TimeUnit.SECONDS);
            return processResult;
        } else {
                log.info("try lock failed");
                Object value = redisService.get(key);
                if (LOCK_VALUE.equals(value)) {
                    log.error("same request executing");
                throw new GlobalException(RespBeanEnum.RUNNING);
            } else {
                log.info("same request already be executed,return success result");
                //第一次已经处理完成，但未过超时时间，所以后续同样请求使用同一个返回结果
                return value;
            }
        }


    }


}
