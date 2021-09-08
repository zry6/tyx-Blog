package com.zry.simpleblog.service;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Tag;

import java.util.List;

/**
 * @author zry
 * @ClassName TagService.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
public interface TagService {
    Boolean addTag(Tag tag);
    Tag findTagByName(Tag tag);
    Tag findTagById(Long id);
    PageInfo listTag(int pageNum, int i);
    List<Tag> listTag();

    List<Tag> listTag(String tagIds);

    Boolean updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    List<Tag> getTagsByBlogId(Long id);

    List<Tag> listTagTop(int i);
}
