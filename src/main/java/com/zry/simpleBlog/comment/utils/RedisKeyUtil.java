package com.zry.simpleBlog.comment.utils;


/**
 * RedisKeyUtils, 用于根据一定规则生成 key
 *
 * @author zry
 * @create 2022-04-09 23:17
 */
public class RedisKeyUtil {

    /**
     * 功能描述: 根据生成的ticket，生成用户Token的key
     */
    public static String getUserKey(String ticket) {
        return "user:" + ticket;
    }
}
