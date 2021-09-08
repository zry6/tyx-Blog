package com.zry.simpleblog.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.dao.TagDao;
import com.zry.simpleblog.entity.Tag;
import com.zry.simpleblog.service.TagService;
import com.zry.simpleblog.util.StringToList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zry
 * @ClassName TagServiceImpl.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagDao tagDao;

    @Override
    public Boolean addTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Tag findTagByName(Tag tag) {
        return tagDao.queryTagByName(tag.getName());
    }

    @Override
    public Tag findTagById(Long id) {
        return tagDao.queryTagById(id);
    }

    @Override
    public PageInfo listTag(int pageNum, int i) {
        String orderBy = "id ASC";
        PageHelper.startPage(pageNum, i, orderBy);
        List<Tag> tagList = tagDao.findAll();
        PageInfo pageInfo = new PageInfo(tagList);
        return pageInfo;
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        List<Tag> tagList = new ArrayList();
        if (!"".equals(tagIds) && tagIds != null) {
            List<Long> list = StringToList.convertToList(tagIds);
            for (Long aLong : list) {
                tagList.add(tagDao.queryTagById(aLong));
            }
        }
        return tagList;
    }

    @Override
    public Boolean updateTag(Long id, Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteTagBlogByTagId(id);
        tagDao.deleteTagById(id);
    }

    @Override
    public List<Tag> getTagsByBlogId(Long id) {
        List<Tag> tagList = new ArrayList<>();
        List<Long> tagIds = tagDao.getTagIdByBlogId(id);
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long aLong : tagIds) {
                Tag tag = tagDao.queryTagById(aLong);
                tagList.add(tag);
            }
        }
        return tagList;
    }
    //前端展示中
    @Override
    public List<Tag> listTagTop(int count) {
        List<Tag> tagList = tagDao.listTagTop(count);
        return tagList;
    }

}
