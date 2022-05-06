package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.aop.exception.GlobalException;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.comment.utils.StringUtil;
import com.zry.simpleBlog.comment.utils.UserContext;
import com.zry.simpleBlog.dto.BlogDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.dto.PostBlogDto;
import com.zry.simpleBlog.dto.UserDto;
import com.zry.simpleBlog.entity.*;
import com.zry.simpleBlog.mapper.*;
import com.zry.simpleBlog.service.IBlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveBlog(PostBlogDto blogDto) {
        User user = UserContext.getCurrentUser();
        if (user == null) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        //检查type是否存在
        Long typeId = blogDto.getTypeId();
        getAndCheckType(typeId);
        //检查tags是否存在  循环查询tag是否存在
        List<Long> tagList = getAndCheckTags(blogDto.getTagIds());
        //补充文章属性
        blogDto.setCreateTime(new Date());
        blogDto.setUpdateTime(new Date());
        blogDto.setUserId(user.getId());
        blogDto.setViews(0L);
        Blog blog = blogDto.caseToBlog();
        //插入t_blog
        blogMapper.insert(blog);
        //插入t_blog_tags表
        if (!tagList.isEmpty()) {
            blogTagsMapper.insert(blog.getId(), tagList);
        }
        return blog.getId();
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

    /**
     * 博客首页和分类的分页展示
     */
    @Override
    public Page<BlogDto> blogPage(Integer current, Integer size, BlogQuery query) {
        Page<Blog> blogVoPage = blogMapper.selectPageByBlogQuery(new Page<>(current, size), query);
        //pageClassConvert类型转换
        Page<BlogDto> page = pageClassConvert(blogVoPage);
        //填充分类和博主信息
        page = setTypeAndUSerInfo(page);
        return page;
    }

    /**
     * 填充分类和博主信息
     *
     * @param page
     * @return
     */
    Page<BlogDto> setTypeAndUSerInfo(Page<BlogDto> page) {
        List<BlogDto> blogVos = page.getRecords();
        for (BlogDto blogVo : blogVos) {
            blogVo.setUser(new UserDto(userMapper.selectById(blogVo.getUserId())));
            blogVo.setType(typeMapper.selectById(blogVo.getTypeId()));
        }
        return page;
    }

    @Override
    public Page<BlogDto> blogPage(Integer current, Integer size, Long tagId) {
        List<BlogTags> blogTagsList = blogTagsMapper.selectList(new QueryWrapper<BlogTags>().eq("tags_id", tagId));
        if(blogTagsList == null || blogTagsList.size() == 0){
            return null;
        }
        List<Long> resultList = new ArrayList<>();
        //遍历集合取值
        blogTagsList.forEach(item -> {
            resultList.add(item.getBlogsId());
        });

        Page<Blog> blogVoPage = blogMapper.selectPage(new Page<>(current, size), new QueryWrapper<Blog>().eq("published", true).in("id", resultList));
        //pageClassConvert类型转换
        Page<BlogDto> page = pageClassConvert(blogVoPage);
        // 填充分类和博主信息，标签
        List<BlogDto> blogVos = page.getRecords();
        for (BlogDto blog : blogVos) {
            blog.setUser(new UserDto(userMapper.selectById(blog.getUserId())));
            blog.setType(typeMapper.selectById(blog.getTypeId()));
            //填充标签
            setTagsByBlogId(blog);
        }
        return page;
    }

    /**
     * 为一个文章填充标签
     * @param blog
     */
    private void setTagsByBlogId(BlogDto blog) {
        List<BlogTags> blogTags = blogTagsMapper.selectList(new QueryWrapper<BlogTags>().eq("blogs_id", blog.getId()));
        if (blogTags != null && blogTags.size() > 0) {
            log.debug(String.valueOf(blogTags.size()));
            List<Tag> tags = tagMapper.selectListByTagIds(blogTags);
            blog.setTags(tags);
            blog.init();
        }
    }

    /**
     * 后台管理博客列表分页
     */
    @Override
    public Page<BlogDto> adminBlogPage(Integer current, Integer size, BlogQuery query) {
        Page<Blog> blogVoPage = blogMapper.selectAdminPage(new Page<>(current, size), query);
        //pageClassConvert类型转换
        Page<BlogDto> page = pageClassConvert(blogVoPage);
        //填充分类
        List<BlogDto> blogVos = page.getRecords();
        for (BlogDto blogVo : blogVos) {
            blogVo.setType(typeMapper.selectById(blogVo.getTypeId()));
        }
        return page;
    }

    /**
     * 功能描述: page类型转换
     */
    private Page<BlogDto> pageClassConvert(Page<Blog> blogVoPage) {
        return (Page<BlogDto>) blogVoPage.convert(u -> {
            log.debug(u.toString());
            BlogDto v = new BlogDto();
            BeanUtils.copyProperties(u, v);
            return v;
        });
    }

    @Override
    public BlogDto getBlogById(Integer id) {
        User user = UserContext.getCurrentUser();
        Blog blog = blogMapper.selectById(id);
        if (null == blog) {
            return null;
        }
        //未登录状态不能看未发布的
        if (!blog.getPublished() && user == null) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        BlogDto blogDto = new BlogDto(blog);
        //获取分类
        blogDto.setType(typeMapper.selectById(blogDto.getTypeId()));
        //获取标签
        setTagsByBlogId(blogDto);
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
    private List<Long> getAndCheckTags(String tagIds) {
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
    private void getAndCheckType(Long typeId) {
        Type type = typeMapper.selectById(typeId);
        if (type == null) {
            throw new GlobalException(RespBeanEnum.TYPE_NOT_EXISTED);
        }
    }

}