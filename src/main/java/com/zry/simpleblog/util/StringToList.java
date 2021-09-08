package com.zry.simpleblog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zry
 * @ClassName StringToList.java
 * @Description TODO
 * @createTime 2021年09月01日
 */
public class StringToList {
    //    工具：作用将字符串 分割为字符数组
    public static List<Long> convertToList(String ids){
        List<Long> list = new ArrayList();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i=0 ; i < idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

}
