package com.zry.simpleBlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.simpleBlog.dto.TypeDto;
import com.zry.simpleBlog.entity.Type;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface ITypeService extends IService<Type> {
    /**
     * 功能描述: 后台类型分页
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/16
     */
    Page<Type> typePage(Integer pageCurrent, Integer pageSiz);

    /**
     * 新增分类
     *
     * @param typeDto 类型传输数据
     */
    void saveType(TypeDto typeDto);
    /**
     * 功能描述: 更新分类
     *
     * @author zry
     * @create 2022/4/17
     */
    void updateById(TypeDto typeDto);

    /**
     * 功能描述: 获取存在对应文章的分类和对应的文章数
     *
     * @author zry
     * @create 2022/4/30
     */
    List<TypeDto> listAndBlogCount();
    /**
     * 功能描述: 删除分类
     *
     * @author zry
     * @create 2022/5/17
     */
    boolean remove(Long id);
}
