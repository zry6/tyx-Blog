package com.zry.comment.idempotent.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 接口幂等keyStr实现策略
 *
 *
 * @author zry
 * @create 2022-04-27 10:37
 */
public interface IdempotentStrategyInterface {
    /**
     * 根据不同策略获取key值，如果出现异常或者不符合条件，则返回null
     *
     * @return key值
     */
    String process(ProceedingJoinPoint pjp) throws IllegalAccessException, JsonProcessingException;
}
