package com.zry.simpleblog.service;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Type;

import java.util.List;

/**
 * @author zry
 * @ClassName TypeService.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
public interface TypeService {
    Boolean saveType(Type type);
    Type findTypeByName(Type type);
    Type findTypeById(Long type);
    PageInfo listType(int pageNum, int i);

    List<Type> listType();

    Boolean updateType(Long id, Type type);

    void deleteType(Long id);

    List<Type> listTypeTop(int i);
}
