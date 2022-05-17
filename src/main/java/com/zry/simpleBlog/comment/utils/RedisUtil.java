package com.zry.simpleBlog.comment.utils;


/**
 * RedisKeyUtils, 用于根据一定规则生成 key
 *
 * @author zry
 * @create 2022-04-09 23:17
 */
public class RedisUtil {

    /**
     * 用户登录前缀标识
     */
    public static final String USER_KEY_PREFIX = "user";

    /**
     * 文章前缀标识
     */
    public static final String ARTICLE_KEY_PREFIX = "article";


    /**
     * 分割字符，默认[:]，
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * 功能描述: 根据生成的ticket，生成用户Token的key
     */
    public static String getUserKey(String ticket) {
        return USER_KEY_PREFIX + KEY_SPLIT_CHAR + ticket;
    }

    /**
     * 功能描述: 根据生成的ticket，生成用户Token的key
     */
    public static String getArticleKey( Long blogId) {
        return ARTICLE_KEY_PREFIX + KEY_SPLIT_CHAR + blogId;
    }
}
