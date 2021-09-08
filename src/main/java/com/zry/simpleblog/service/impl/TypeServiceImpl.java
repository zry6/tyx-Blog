package com.zry.simpleblog.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.dao.TypeDao;
import com.zry.simpleblog.entity.Type;
import com.zry.simpleblog.service.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zry
 * @ClassName TypeServiceImpl.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeDao typeDao;

    @Override
    public Boolean saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    public Type findTypeByName(Type type) {
        return typeDao.queryTypeByName(type.getName());
    }

    @Override
    public Type findTypeById(Long id) {
        return typeDao.queryTypeById(id);
    }

    @Override
    public PageInfo listType(int pageNum, int i) {
        String orderBy = "id ASC";
        PageHelper.startPage(pageNum, i,orderBy);
        List<Type> typeList = typeDao.findAll();
        PageInfo pageInfo = new PageInfo(typeList);
        return pageInfo;
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public Boolean updateType(Long id, Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteTypeById(id);
    }
    //前端展示中
    @Override
    public List<Type> listTypeTop(int count) {
        List<Type> typeList = typeDao.listTypeTop(count);
        return typeList;
    }
}
