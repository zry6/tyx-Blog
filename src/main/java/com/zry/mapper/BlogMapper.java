package com.zry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.dto.BlogQuery;
import com.zry.dto.TypeDto;
import com.zry.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 功能描述: 这个用来查询分页，除了内容
     *
     * @param page ,query查询条件
     * @author zry
     * @create 2022/4/25
     */
    Page<Blog> selectPageNoContent(Page<Blog> page, @Param("query") BlogQuery query);
    /**
     * 功能描述:
     *
     * @author zry
     * @create 2022/4/30
     */
    List<TypeDto> selectCountAndTypesId();
}
