package com.zry.simpleBlog.comment.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author zry
 * @ClassName UUIDUtil.java
 * @Description UUID工具类
 * @createTime 2022年01月02日
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
