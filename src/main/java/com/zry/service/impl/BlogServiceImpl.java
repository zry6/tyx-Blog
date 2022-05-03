package com.zry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.comment.aop.exception.GlobalException;
import com.zry.comment.respBean.RespBeanEnum;
import com.zry.comment.utils.StringUtil;
import com.zry.comment.utils.UserContext;
import com.zry.dto.BlogDto;
import com.zry.dto.BlogQuery;
import com.zry.dto.PostBlogDto;
import com.zry.entity.*;
import com.zry.mapper.*;
import com.zry.service.IBlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private BlogTagsMapper blogTagsMapper;
    @Resource
    private CommentMapper commentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostBlogDto saveBlog(PostBlogDto blog) {
        User user = UserContext.getCurrentUser();
        if (user == null) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        //检查type是否存在
        Long typeId = blog.getTypeId();
        getAndCheckType(typeId);
        //检查tags是否存在  循环查询tag是否存在
        List<Long> tagList = getAndCheckTags(blog.getTagIds());
        //补充文章属性
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setUserId(user.getId());
        blog.setViews(0L);

        //插入t_blog
        blogMapper.insert(blog.caseToBlog());
        //插入t_blog_tags表
        if (!tagList.isEmpty()) {
            blogTagsMapper.insert(blog.getId(), tagList);
        }
        return blog;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlog(PostBlogDto blog) {
        User user = UserContext.getCurrentUser();
        if (user == null) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        //检查type是否存在
        Long typeId = blog.getTypeId();
        getAndCheckType(typeId);
        //插入t_blog
        blog.setUserId(user.getId());
        blog.setUpdateTime(new Date());

        blogMapper.update(blog.caseToBlog(), new QueryWrapper<Blog>().eq("id", blog.getId()));
        //检查tags是否存在  循环查询tag是否存在
        List<Long> tagList = getAndCheckTags(blog.getTagIds());
        //修改t_blog_tags表
        blogTagsMapper.delete(new QueryWrapper<BlogTags>().eq("blogs_id", blog.getId()));
        //插入t_blog_tags表
        if (!tagList.isEmpty()) {
            blogTagsMapper.insert(blog.getId(), tagList);
        }
    }

    @Override
    public Page<BlogDto> blogPageNoContent(Integer current, Integer size, BlogQuery query) {
        Page<Blog> blogVoPage = blogMapper.selectPageNoContent(new Page<>(current, size),query);
        //类型转换
        Page<BlogDto> page = (Page<BlogDto>) blogVoPage.convert(u -> {
            BlogDto v = new BlogDto();
            BeanUtils.copyProperties(u, v);
            return v;
        });
        List<BlogDto> blogVos = page.getRecords();
        for (BlogDto blogVo : blogVos) {
            blogVo.setType(typeMapper.selectById(blogVo.getTypeId()));
        }
        return page;
    }


    @Override
    public BlogDto getBlogById(Integer id) {
        User user = UserContext.getCurrentUser();
        Blog blog = blogMapper.selectById(id);
        if (null == blog) {
            return null;
        }
        if (!blog.getPublished() && user == null) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        BlogDto blogDto = new BlogDto(blog);
        //获取分类
        blogDto.setType(typeMapper.selectById(blogDto.getTypeId()));
        //获取标签
        List<BlogTags> blogTags = blogTagsMapper.selectList(new QueryWrapper<BlogTags>().eq("blogs_id", blogDto.getId()));
        if (blogTags != null && blogTags.size() > 0) {
            log.debug(String.valueOf(blogTags.size()));
            List<Tag> tags = tagMapper.selectListByTagIds(blogTags);
            blogDto.setTags(tags);
            blogDto.init();
        }
        return blogDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        int i = blogMapper.deleteById(id);
        if (i != 1) {
            throw new GlobalException(RespBeanEnum.DELETE_ERROR);
        }
        blogTagsMapper.delete(new QueryWrapper<BlogTags>().eq("blogs_id", id));
        commentMapper.delete(new QueryWrapper<Comment>().eq("blog_id", id));

    }

    /**
     * 功能描述: 更新或添加文章时，检查并获取Tags
     */
    List<Long> getAndCheckTags(String tagIds) {
        List<Long> tagList = StringUtil.convertToList(tagIds);
        for (Long tagId : tagList) {
            Tag tag = tagMapper.selectById(tagId);
            if (tag == null) {
                throw new GlobalException(RespBeanEnum.TAG_NOT_EXISTED);
            }
        }
        return tagList;
    }

    /**
     * 功能描述: 更新或添加文章时，检查并获取Type
     */
    void getAndCheckType(Long typeId) {
        Type type = typeMapper.selectById(typeId);
        if (type == null) {
            throw new GlobalException(RespBeanEnum.TYPE_NOT_EXISTED);
        }
    }

}
