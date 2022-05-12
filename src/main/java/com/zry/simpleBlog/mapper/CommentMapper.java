package com.zry.simpleBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.entity.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 功能描述: 根据父级评论id查找 子级回复list集合
     *
     * @create 2022/5/12
     */
    List<CommentDto> selectReplyList(@Param("id") Long id);

    void save(CommentDto comment);
}
