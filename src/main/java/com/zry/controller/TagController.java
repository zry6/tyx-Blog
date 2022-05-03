package com.zry.controller;


import com.zry.comment.respBean.RespBean;
import com.zry.dto.TagDto;
import com.zry.entity.Tag;
import com.zry.service.ITagService;
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
     * 功能描述: 获取type和对应的文章数量
     *
     * @create 2022/4/30
     */
    @GetMapping("tags/blogCount")
    private RespBean getTypesAndBlogCount() {
        List<TagDto> list = tagService.listAndBlogCount();
        return RespBean.success(list);
    }

}
