package com.zry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zry.entity.BlogTags;
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
    int insert(@Param("blogId")Long blogId,@Param("tagList") List<Long> tagList);
}
