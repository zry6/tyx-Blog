package com.zry.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.dto.TagDto;
import com.zry.entity.Tag;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface ITagService extends IService<Tag> {

    void updateById(TagDto tagDto);

    void saveTag(TagDto tagDto);

    Page<Tag> tagPage(Integer current, Integer size);
}
