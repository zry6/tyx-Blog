package com.zry.simpleBlog.controller;


import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.TagDto;
import com.zry.simpleBlog.entity.Tag;
import com.zry.simpleBlog.service.ITagService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "标签接口")
@RestController
public class TagController {
    @Resource
    private ITagService tagService;


    @GetMapping("tags")
    private RespBean getTags() {
        List<Tag> list = tagService.list();
        return RespBean.success(list);
    }
    /**
     * 功能描述: 获取 tag 和对应的文章数量 这不包含未发布的
     *
     * @create 2022/4/30
     */
    @GetMapping("tags/blogCount")
    private RespBean getTagSAndBlogCount() {
        List<TagDto> list = tagService.listAndBlogCount();
        return RespBean.success(list);
    }

}
