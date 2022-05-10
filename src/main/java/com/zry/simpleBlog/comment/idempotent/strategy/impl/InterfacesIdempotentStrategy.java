package com.zry.simpleBlog.comment.idempotent.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentField;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentStrategy;
import com.zry.simpleBlog.comment.idempotent.strategy.IdempotentStrategyInterface;
import com.zry.simpleBlog.comment.idempotent.IdempotentInterface;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * IDEMPOTENT_INTERFACE策略
 * 不允许参数列表为空;
 * DTO必须实现IdempotentInterface接口;
 * DTO中如果存在IdempotentField表及字段；使用字段幂等；否则使用DTO幂等;
 *
 * @author zry
 * @create 2022-04-27 17:26
 */

@Component("IDEMPOTENT_INTERFACE")
@Slf4j
public class InterfacesIdempotentStrategy implements IdempotentStrategyInterface {
    public static final String PREFIX = "idempotent_interface_";
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String process(ProceedingJoinPoint pjp) throws IllegalAccessException, JsonProcessingException {
        log.info("idempotent strategy：{}", IdempotentStrategy.IDEMPOTENT_INTERFACE.name());
        Object[] args = pjp.getArgs();
        Object dto = null;
        for (Object arg : args) {
            //是否实现了接口
            boolean assignable = IdempotentInterface.class.isAssignableFrom(arg.getClass());
            if (assignable) {
                dto = arg;
                break;
            }
        }
        if (dto == null) {
            log.info("no class implements of IdempotentStrategyInterface in list of parameter");
            return null;
        }
        StringBuilder keyStr = new StringBuilder(PREFIX);
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            IdempotentField idempotentField = field.getAnnotation(IdempotentField.class);
            if (null == idempotentField) {
                continue;
            }
            //获取字段上的值
            keyStr.append(field.get(dto)).append("_");
        }
        if (keyStr.toString().equals(PREFIX)) {
            log.info("use dto");
            keyStr.append(objectMapper.writeValueAsString(dto));
        } else {
            log.info("use idempotentField");
        }
        return keyStr.toString();
    }
}
