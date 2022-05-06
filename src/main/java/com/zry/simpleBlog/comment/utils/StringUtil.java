package com.zry.simpleBlog.comment.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zry
 * @create 2022-04-21 10:30
 */
public class StringUtil {
    /**
     * 功能描述:作用将字符串以 ',' 分割为字符数组
     *
     * @param
     * @return
     */
    public static List<Long> convertToList(String ids){
        List<Long> list = new ArrayList();

        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (String s : idarray) {
                list.add(new Long(s));
            }
        }
        return list;
    }
}