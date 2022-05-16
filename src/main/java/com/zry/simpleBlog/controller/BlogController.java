package com.zry.simpleBlog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.CheckLogin;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.ArchivesDto;
import com.zry.simpleBlog.dto.BlogDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.IBlogService;
import com.zry.simpleBlog.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @CheckLogin(isLogin = false)
    @GetMapping("blogs/{id}")
    public RespBean getBlog(@PathVariable Integer id) {
        BlogDto blog = blogService.getBlogById(id);
        return RespBean.success(blog);
    }

    /**
     * 首页文章展示分页
     *
     * @return mv
     */

    @ApiOperation(value = "文章展示分页", notes = "文章展示分页: 可选参数[分类][标签],并且如果标签参数存在那么按照标签查询")
    @GetMapping("blogs")
    public RespBean blogPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(required = false) Long typeId, @RequestParam(required = false) Long tagId) {
        //只展示已发布的文章
        Page<BlogDto> pageDto = null;
        if (tagId == null) {
            BlogQuery query = new BlogQuery();
            query.setTypeId(typeId);
            pageDto = blogService.blogPage(page, pageSize, query);
        } else {
            pageDto = blogService.blogPage(page, pageSize, tagId);
        }
        return RespBean.success(pageDto);
    }

    /**
     * 推荐博客文章
     *
     * @return mv
     */
    @ApiOperation(value = "推荐文章列表", notes = "默认推荐文章展示前5个")
    @GetMapping("recommendBlogs")
    @Cacheable(value = "Blog_Recommends")
    public RespBean recommendBlog(@RequestParam(defaultValue = "5") Integer num) {
        List<Blog> blogList = blogService.list(new QueryWrapper<Blog>().select("id", "title").eq("published", true).eq("recommend", true).orderByAsc("update_time").last("limit " + num));
        return RespBean.success(blogList);
    }

    @ApiOperation(value = "时间归档", notes = "按时间倒序Map结构{key : year ，value: List<Blog>}")
    @GetMapping("archives")
    public RespBean archives() {
        Map<String, List<ArchivesDto>> stringListMap = blogService.mapArchives();
        return RespBean.success(stringListMap);
    }


//    /**
//     * 功能描述: 这是一个特殊的方法，主要生成留言板文章数据。留言板是一篇id固定的文章
//     *
//     * @create 2022/5/15
//     */
//    @CheckLogin
//    @ApiOperation(value = "初始化博客", notes = "主要生成留言板文章数据")
//    @PostMapping("init")
//    public RespBean init() {
//        Blog blog;
//        blog = blogService.getById(1);
//        if (blog != null) {
//            return RespBean.success("留言板已存在");
//        }
//        blog = new Blog();
//        blog.setId(1L);
//        blog.setUserId(1L);
//        blog.setTitle("留言板");
//        blog.setContent("畅所欲言吧");
//        blog.setCommentable(true);
//        blog.setPublished(false);
//        blog.setFlag("原创");
//        blog.setCreateTime(new Date());
//        blogService.save(blog);
//        return RespBean.success();
//    }

    @PostConstruct
    public void init() {
        User user = userService.getById(1);
        if(user != null){
            return;
        }
        user = new User();
        user.setId(1L);
        user.setUsername("root");
        user.setType(1);
        //两次root加密之后的
        user.setPassword("b5fb686c5752edd1c337ac7231c6cea5");
        user.setNickname("tyux");
        user.setAvatar("/images/avatar/zry.jpg");
        user.setSalt("1a2b3c4d");
        userService.save(user);


        Blog blog = blogService.getById(1);
        if (blog != null) {
            return;
        }
        blog = new Blog();
        blog.setId(1L);
        blog.setUserId(1L);
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
