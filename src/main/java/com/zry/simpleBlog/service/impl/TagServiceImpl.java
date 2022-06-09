package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.TagDto;
import com.zry.simpleBlog.entity.Tag;
import com.zry.simpleBlog.mapper.BlogTagsMapper;
import com.zry.simpleBlog.mapper.TagMapper;
import com.zry.simpleBlog.service.ITagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标签service  ， 因为标签涉及到多个表的查询，所以给他用上redis缓存
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private BlogTagsMapper blogTagsMapper;

    @Override
    public RespBean tagPage(Integer pageNum, Integer pageSize) {
        return RespBean.success(tagMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.emptyWrapper()));
    }

    @Cacheable(value = "TagDto_List")
    @Override
    public List<TagDto> listAndBlogCount() {
        List<TagDto> tagDtos = blogTagsMapper.selectCountAndTagsId();
        for (TagDto tagDto : tagDtos) {
            Tag tag = tagMapper.selectById(tagDto.getId());
            if (tag != null) {
                tagDto.setName(tag.getName());
            }
        }
        return tagDtos;
    }

    @CacheEvict(value = {"TagDto_List"}, allEntries = true)
    @Override
    public RespBean updateById(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setId(tagDto.getId());
        //检查分类是否已经存在
        if (isExistTag(tagDto)) {
            throw new BusinessException(RespBeanEnum.TAG_EXISTED);
        }
        tagMapper.updateById(tag);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }

    /**
     * 功能描述: 新增个标签为什么不需要删除缓存呢？
     * 因为新增的标签并没有关联的文章，所以listAndBlogCount方法用不到他，也就不用缓存
     */
    @Override
    public void saveTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        //检查分类是否已经存在
        if (isExistTag(tagDto)) {
            throw new BusinessException(RespBeanEnum.TAG_EXISTED);
        }
        tagMapper.insert(tag);
    }

    /**
     * 检查分类是否已经存在
     */
    boolean isExistTag(TagDto tagDto) {
        //检查分类是否已经存在
        Tag tag = tagMapper.selectOne(new QueryWrapper<Tag>().eq("name", tagDto.getName()));
        return tag != null;
    }

}
