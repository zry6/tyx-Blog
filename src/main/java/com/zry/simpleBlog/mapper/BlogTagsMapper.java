package com.zry.simpleBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zry.simpleBlog.dto.TagDto;
import com.zry.simpleBlog.entity.BlogTags;
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
public interface BlogTagsMapper extends BaseMapper<BlogTags> {
    /**
     * 功能描述: 插入文章的标签关系
     *
     * @param blogId,tagId;
     * @return
     * @author zry
     */
    int insertBlogTags(@Param("blogId")Long blogId, @Param("tagList") List<Long> tagList);


    /**
     * 功能描述: 返回存在博客的标签，和对应博客的数量。
     *
     * @author zry
     * @create 2022/4/30
     */
    List<TagDto> selectCountAndTagsId();
}
