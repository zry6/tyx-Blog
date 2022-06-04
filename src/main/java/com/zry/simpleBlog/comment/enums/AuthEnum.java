package com.zry.simpleBlog.comment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 鉴权枚举
 * 定义该操作所需用户最低的权限等级
 * @author zry
 * @create 2022-04-26 22:08
 */
@Getter
@AllArgsConstructor
public enum AuthEnum {
    /**
     * admin 最高权限用户，拥有所有权限
     */
    ADMINISTRATOR(1),
    /**
     * vip 无删除其它博主 博文权限
     */
    VIP(2),
    /**
     * user 无更新，删除权限
     */
    USER(3),
    /**
     * 只有前台浏览，评论权限
     */
    VISITOR(4);
    /**
     * 用户权限等级
     */
    private final Integer rank;
}
