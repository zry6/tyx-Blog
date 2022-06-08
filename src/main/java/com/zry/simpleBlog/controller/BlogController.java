package com.zry.simpleBlog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.AuthCheck;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.BlogDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.IBlogService;
import com.zry.simpleBlog.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@RestController
public class BlogController {
    @Resource
    private IBlogService blogService;

    @Resource
    private IUserService userService;

    @Value("${init.article.guestbook.id}")
    private Long guestbookId;

    @Value("${init.user.id}")
    private Long userId;

    /**
     * 功能描述:根据博客id返回文章
     *
     * @param id 博客id
     * @CheckLogin(isLogin = false) 是指:
     * 将登录的用户信息加入UserContext中.
     * 若未登陆,UserContext中则为null.
     * @author zry
     * @create 2022/4/28
     */
    @AuthCheck(isLogin = false)
    @GetMapping("blogs/{id}")
    public RespBean getBlog(@PathVariable Integer id) {
        return blogService.getBlogById(id);
    }

    /**
     * 首页文章展示分页
     *
     * @return mv
     */

    @ApiOperation(value = "文章展示分页", notes = "文章展示分页: 可选参数[分类][标签],并且如果标签参数存在那么按照标签查询")
    @GetMapping("blogs")
    public RespBean blogPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(required = false) Long typeId, @RequestParam(required = false) Long tagId) {
        RespBean respBean;
        //只展示已发布的文章
        Page<BlogDto> pageDto = null;
        if (tagId == null) {
            BlogQuery query = new BlogQuery();
            query.setTypeId(typeId);
            respBean = blogService.blogPage(page, pageSize, query);
        } else {
            respBean = blogService.blogPage(page, pageSize, tagId);
        }
        return respBean;
    }

    /**
     * 推荐博客文章
     *
     * @return mv
     */
    @Cacheable(value = "Blog_Recommends")
    @ApiOperation(value = "推荐文章列表", notes = "默认推荐文章展示前5个")
    @GetMapping("recommendBlogs")
    public RespBean recommendBlog(@RequestParam(defaultValue = "5") Integer num) {
        List<Blog> blogList = blogService.list(new QueryWrapper<Blog>().select("id", "title").eq("published", true).eq("recommend", true).orderByAsc("update_time").last("limit " + num));
        return RespBean.success(blogList);
    }

    @ApiOperation(value = "时间归档", notes = "按时间倒序Map结构{key : year ，value: List<Blog>}")
    @GetMapping("archives")
    public RespBean archives() {
        return blogService.mapArchives();
    }

    /**
     * 功能描述: 初始项目，不需要就去掉
     *
     * @create 2022/6/2
     */
//    @PostConstruct
//    public void init() {
//        initUser();
//        initBlog();
//    }
    private void initUser() {
        User user = userService.getById(1);
        if (user != null) {
            return;
        }
        user = new User();
        user.setId(userId);
        user.setUsername("root");
        user.setRank(1);
        //root两次加密之后的
        user.setPassword("b5fb686c5752edd1c337ac7231c6cea5");
        user.setNickname("tyux");
        user.setAvatar("/images/avatar/zry.jpg");
        user.setSalt("1a2b3c4d");
        userService.save(user);
    }


    private void initBlog() {
        Blog blog = blogService.getById(1);
        if (blog != null) {
            return;
        }
        blog = new Blog();
        blog.setId(guestbookId);
        blog.setUserId(userId);
        blog.setTitle("留言板");
        blog.setContent("畅所欲言吧");
        blog.setCommentable(true);
        blog.setPublished(false);
        blog.setFlag("原创");
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blogService.save(blog);
    }


}
