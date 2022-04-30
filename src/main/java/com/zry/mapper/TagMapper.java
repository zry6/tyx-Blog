package com.zry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zry.entity.BlogTags;
import com.zry.entity.Tag;
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
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectListByTagIds(@Param("blogTagsList")List<BlogTags> blogTags);
}
