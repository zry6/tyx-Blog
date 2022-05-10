package com.zry.simpleBlog.comment.idempotent;

import com.zry.simpleBlog.comment.exception.BusinessException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

/**
 * 策略模板
 * @author zry
 * @create 2022-04-27 10:39
 */
@Data
public abstract class AbstractStrategyContext<R> {
    /**
     * 功能描述: 这个map用来存的是策略实例
     * key 策略枚举的name，value ：策略的实例单例，它是自动注入的
     *
     * @author zry
     * @create 2022/4/27
     */
    @Autowired
    Map<String, R> map;

    /**
     * 根据typeName获取对应的策略实例
     *
     * @param type 策略名称
     * @return 策略实例
     */
    protected R getStrategy(String type) {
        System.out.println(map);
        return Optional.ofNullable(getMap().get(type)).orElseThrow(() -> new BusinessException("幂等类型：" + type + "未定义"));
    }

}
