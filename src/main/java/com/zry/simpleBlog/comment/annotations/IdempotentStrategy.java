package com.zry.simpleBlog.comment.annotations;

import lombok.Getter;

/**
 * 幂等策略枚举
 *
 * @author zry
 * @create 2022-04-26 22:08
 */
@Getter
public enum IdempotentStrategy {
    /**
     * 这里我们暂时不用
     * DTO必须继承于BaseIdempotentDto，使用继承与BaseDto的类，如果dto中有指定字段，使用指定字段；否则使用dto整体做幂等
     */
    BASE_IDEMPOTENT_DTO,

    /**
     * 使用实现了IdempotentInterface接口的类做幂等；如果dto中有指定字段，使用指定字段；否则使用dto整体做幂等
     */
    IDEMPOTENT_INTERFACE,

    /**
     * 这里我们暂时不用
     * 使用整个参数列表做幂等，和@idempotentfield无关
     */
    LIST_PARAMETER,
    ;

}
