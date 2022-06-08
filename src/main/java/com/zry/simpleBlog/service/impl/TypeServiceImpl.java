package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.TypeDto;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.Type;
import com.zry.simpleBlog.mapper.BlogMapper;
import com.zry.simpleBlog.mapper.TypeMapper;
import com.zry.simpleBlog.service.ITypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private BlogMapper blogMapper;

    @Override
    public Page<Type> typePage(Integer pageCurrent, Integer pageSiz) {
        return typeMapper.selectPage(new Page<>(pageCurrent, pageSiz), Wrappers.emptyWrapper());
    }

    @Override
    public RespBean saveType(TypeDto typeDto) {
        Type type = new Type();
        type.setName(typeDto.getName());
        //检查分类是否已经存在
        if (isExistType(typeDto)) {
            throw new BusinessException(RespBeanEnum.TYPE_EXISTED);
        }
        typeMapper.insert(type);
        return RespBean.success(RespBeanEnum.POST_SUCCESS);
    }

    @Override
    public RespBean updateById(TypeDto typeDto) {
        Type type = new Type();
        type.setName(typeDto.getName());
        type.setId(typeDto.getId());
        //检查分类是否已经存在
        if (isExistType(typeDto)) {
            throw new BusinessException(RespBeanEnum.TYPE_EXISTED);
        }
        typeMapper.updateById(type);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }

    @Override
    public RespBean listAndBlogCount() {
        //在博客表中查询出分类id和对应文章数量
        List<TypeDto> typeDos = blogMapper.selectCountAndTypesId();
        for (TypeDto typeDto : typeDos) {
            Type type = typeMapper.selectById(typeDto.getId());
            if (type != null) {
                typeDto.setName(type.getName());
            }
        }
        return RespBean.success(typeDos);
    }
    @Override
    public RespBean remove(Long id) {
        List<Blog> blogs = blogMapper.selectList(new QueryWrapper<Blog>().select("id").eq("type_id", id));
        if (!blogs.isEmpty()) {
            throw new BusinessException("删除失败，该分类下还存在文章哦");
        }
        int i = typeMapper.deleteById(id);
        if (i != 1) {
           return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
    }

    /**
     * 检查分类是否已经存在
     */
    boolean isExistType(TypeDto typeDto) {
        //检查分类是否已经存在
        Type type1 = typeMapper.selectOne(new QueryWrapper<Type>().eq("name", typeDto.getName()));
        return type1 != null;
    }


}
