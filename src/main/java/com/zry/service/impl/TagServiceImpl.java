package com.zry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.comment.aop.exception.GlobalException;
import com.zry.comment.respBean.RespBeanEnum;
import com.zry.dto.TagDto;
import com.zry.entity.Tag;
import com.zry.mapper.BlogTagsMapper;
import com.zry.mapper.TagMapper;
import com.zry.service.ITagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
    public Page<Tag> tagPage(Integer current, Integer size) {
        return tagMapper.selectPage(new Page<>(current, size), Wrappers.emptyWrapper());
    }

    @Override
    public List<TagDto> listAndBlogCount() {
        List<TagDto> tagDtos = blogTagsMapper.selectCountAndTagsId();
        for (TagDto tagDto : tagDtos) {
            Tag tag = tagMapper.selectById(tagDto.getId());
            if(tag != null){
                tagDto.setName(tag.getName());
            }
        }
        return tagDtos;
    }

    @Override
    public void updateById(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setId(tagDto.getId());
        //检查分类是否已经存在
        if (isExistTag(tagDto)) {
            throw new GlobalException(RespBeanEnum.TAG_EXISTED);
        }
        tagMapper.updateById(tag);
    }

    @Override
    public void saveTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        //检查分类是否已经存在
        if (isExistTag(tagDto)) {
            throw new GlobalException(RespBeanEnum.TAG_EXISTED);
        }
        tagMapper.insert(tag);
    }
    //检查分类是否已经存在
    boolean isExistTag(TagDto tagDto){
        //检查分类是否已经存在
        Tag tag = tagMapper.selectOne(new QueryWrapper<Tag>().eq("name",tagDto.getName()));
        return tag != null;
    }

}
