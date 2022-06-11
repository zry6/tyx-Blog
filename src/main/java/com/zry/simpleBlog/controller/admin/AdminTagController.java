package com.zry.simpleBlog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zry.simpleBlog.comment.aop.annotations.AuthCheck;
import com.zry.simpleBlog.comment.aop.annotations.LogWeb;
import com.zry.simpleBlog.comment.enums.AuthRankEnum;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.TagDto;
import com.zry.simpleBlog.entity.BlogTags;
import com.zry.simpleBlog.entity.Tag;
import com.zry.simpleBlog.service.IBlogTagsService;
import com.zry.simpleBlog.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "后台标签管理接口")
@RestController
@RequestMapping("/admin")
public class AdminTagController {
    @Resource
    private ITagService tagService;
    @Resource
    private IBlogTagsService blogTagsService;

    /**
     * 功能描述: 获取标签分页
     * page.class
     * current当前页
     * pages 总页数
     * size 每页大小
     * total 全部数据总数
     * records list数据
     *
     * @author zry
     */
    @GetMapping("/tagPage")
    @LogWeb(title = "标签管理", action = "获取标签分页")
    public RespBean tagPage(@ApiParam(name = "pageSize", value = "页大小") @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                            @ApiParam(name = "pageNum", value = "页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return tagService.tagPage(pageNum, pageSize);
    }

    /**
     * 功能描述: 根据id获取标签
     *
     * @author zry
     */
    @ApiOperation(value = "按id获取标签")
    @GetMapping("/tags/{id}")
    @LogWeb(title = "标签管理", action = "按id获取标签")
    public RespBean getTag(@PathVariable @Valid Integer id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return RespBean.error(RespBeanEnum.TAG_NOT_EXISTED);
        }
        return RespBean.success(new TagDto(tag));
    }

    /**
     * 功能描述: 新增标签
     *
     * @author zry
     */
    @ApiOperation(value = "添加标签")
    @PostMapping("/tags")
    @AuthCheck(rank = AuthRankEnum.GOD)
    @LogWeb(title = "标签管理", action = "新增标签")
    public RespBean postTag(@RequestBody @Valid TagDto tagDto) {
        tagService.saveTag(tagDto);
        return RespBean.success(RespBeanEnum.POST_SUCCESS);
    }

    /**
     * 功能描述: 删除标签
     *
     * @author zry
     */
    @ApiOperation(value = "按id删除标签")
    @DeleteMapping("/tags/{id}")
    @AuthCheck(rank = AuthRankEnum.GOD)
    @LogWeb(title = "标签管理", action = "按id删除标签")
    public RespBean deleteTag(@PathVariable Long id) {
        List<BlogTags> list = blogTagsService.list(new QueryWrapper<BlogTags>().select("id").eq("tags_id", id));
        if (!list.isEmpty()) {
            throw new BusinessException("删除失败，该标签下还存在文章哦");
        }
        if (!tagService.removeById(id)) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        blogTagsService.remove(new QueryWrapper<BlogTags>().eq("tags_id", id));
        return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
    }

    /**
     * 功能描述: 更新标签
     *
     * @author zry
     */
    @ApiOperation(value = "按id更新标签")
    @PutMapping("/tags/{id}")
    @AuthCheck(rank = AuthRankEnum.GOD)
    @LogWeb(title = "标签管理", action = "按id更新标签")
    public RespBean updateTag(@RequestBody @Valid TagDto tagDto, @PathVariable Long id) {
        tagDto.setId(id);
        return tagService.updateById(tagDto);
    }
}
