package com.zry.simpleBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.TagDto;
import com.zry.simpleBlog.entity.Tag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface ITagService extends IService<Tag> {

    RespBean updateById(TagDto tagDto);

    void saveTag(TagDto tagDto);

    RespBean tagPage(Integer current, Integer size);

    /**
     * 功能描述: 获取全部标签包含对应的博客数量
     *
     * @author zry
     * @create 2022/5/3
     */
    List<TagDto> listAndBlogCount();
}
