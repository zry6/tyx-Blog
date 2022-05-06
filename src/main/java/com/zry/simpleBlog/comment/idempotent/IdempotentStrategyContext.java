package com.zry.simpleBlog.comment.idempotent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zry.simpleBlog.comment.annotations.IdempotentStrategy;
import com.zry.simpleBlog.comment.idempotent.strategy.IdempotentStrategyInterface;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 幂等策略转换器
 * @author zry
 * @create 2022-04-27 10:46
 */
@Component
public class IdempotentStrategyContext extends AbstractStrategyContext<IdempotentStrategyInterface> {

    /**
     * 策略转换方法
     */
    public <T> String accept(IdempotentStrategy idempotentStrategy, ProceedingJoinPoint pjp) throws IllegalAccessException, JsonProcessingException {
        return getStrategy(idempotentStrategy.name()).process(pjp);
    }

}
