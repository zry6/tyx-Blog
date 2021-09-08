package com.zry.simpleblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.NotFoundException;
import com.zry.simpleblog.util.MarkdownUtils;
import com.zry.simpleblog.util.StringToList;
import com.zry.simpleblog.dao.*;
import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.vo.BlogQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zry
 * @ClassName BlogServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月01日
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private TypeDao typeDao;
    @Resource
    private BlogTagDao blogTagDao;
    @Resource
    private CommentDao commentDao;
    @Resource
    private UserDao userDao;

/**获取用户和类型信息并将他们添加到博客列表中，完成初始化
 * @return blogList 博客了列表
 * @description
 * @title
 * @author zry
 */
    private List<Blog> setUserAndType(List<Blog> blogList) {
        int j = 0;
        for (Blog b : blogList) {
            b.setUser(userDao.queryUserById(b.getUser().getId()));
            b.setType(typeDao.queryTypeById(b.getType().getId()));
            blogList.set(j, b);
            j++;
        }
        return blogList;
    }

    /**
     * 按页获取博客内容，返回PageInfo对象
     *
     * @param pageNum 第几页
     * @param i       每页几个元素
     * @param blog    封装的查询条件
     * @description
     * @title
     * @author zry
     */
    @Override
    public PageInfo listBlog(int pageNum, int i, BlogQuery blog) {
        PageHelper.startPage(pageNum, i);
        List<Blog> blogList;
        blogList = blogDao.findAll(blog);

        PageInfo pageInfo = new PageInfo(setUserAndType(blogList));
        return pageInfo;
    }

    @Override
    public PageInfo listBlog(int pageNum, int i) {
        PageHelper.startPage(pageNum, i);
        List<Blog> blogList;
        blogList = blogDao.findAllPublished();
        PageInfo pageInfo = new PageInfo(setUserAndType(blogList));
        return pageInfo;
    }

    @Override
    public PageInfo listBlog(int pageNum, int i, String query) {
        PageHelper.startPage(pageNum, i);
        List<Blog> blogList = blogDao.findByQuery(query);
        PageInfo pageInfo = new PageInfo(setUserAndType(blogList));
        return pageInfo;
    }
/**展示区域用tagId查blogList
 * @description
 * @title
 * @author zry
 */
    @Override
    public PageInfo listBlogByTagId(int pageNum, int i, Long id) {
        PageHelper.startPage(pageNum, i);
        List<Blog> blogList = blogTagDao.queryBlogByTagId(id);
        PageInfo pageInfo = new PageInfo(setUserAndType(blogList));
        return pageInfo;
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.queryBlog(id);
        if (blog == null){
            throw new NotFoundException("博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blog.setUser(userDao.queryUserById(blog.getUser().getId()));
        blogDao.updateViews(id);
        return blog;
    }


    @Transactional
    @Override
    public void saveBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setViews(0); //浏览次数
            blogDao.save(blog);//这里blogId从数据库拿来了就有了
        } else {
            blogTagDao.deleteBlogTag(blog.getId());//删除修改前的存在
            blogDao.updateBlog(blog);
        }
        if (blog.getTagIds() != null && !blog.getTagIds().isEmpty()) {
            List<Long> tagIds = StringToList.convertToList(blog.getTagIds());
            blogTagDao.saveBlogAndTag(blog.getId(), tagIds);
        }
    }

    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogDao.queryBlog(id);
        blog.setUser(userDao.queryUserById(blog.getUser().getId()));
        return blog;
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        commentDao.deleteComment(id);
        blogTagDao.deleteBlogTag(id);
        blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(int count) {
        return blogDao.listRecommendBlogTop(count);
    }

}
