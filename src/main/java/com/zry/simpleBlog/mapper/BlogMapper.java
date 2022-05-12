package com.zry.simpleBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.dto.ArchivesDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.dto.TypeDto;
import com.zry.simpleBlog.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
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
     */
    Page<Blog> selectPageByBlogQuery(Page<Blog> page, @Param("query") BlogQuery query);

    /**
     * 功能描述: 这个用来查询分页，除了内容
     *
     * @param page ,query查询条件
     */
    Page<Blog> selectAdminPage(Page<Blog> page, @Param("query") BlogQuery query);

    /**
     * 功能描述: 查询各个分类id对应博客数量
     */
    List<TypeDto> selectCountAndTypesId();

    List<String> findGroupYear();

    List<ArchivesDto> findBlogByYear(@Param("year") String year);
}
