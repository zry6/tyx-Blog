package com.zry.simpleBlog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.AuthCheck;
import com.zry.simpleBlog.comment.enums.AuthEnum;
import com.zry.simpleBlog.comment.aop.annotations.LogWeb;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.dto.TypeDto;
import com.zry.simpleBlog.entity.Type;
import com.zry.simpleBlog.service.ITypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "后台分类管理接口")
@RestController
@RequestMapping("/admin")
public class AdminTypeController {
    @Resource
    private ITypeService typeService;

    /**
     * 功能描述: 获取分类分页
     * page.class
     * current当前页
     * pages 总页数
     * size 每页大小
     * total 全部数据总数
     * records list数据
     *
     * @author zry
     */
    @GetMapping("/types")
    @LogWeb(title = "分类管理", action = "获取分类分页")
    public RespBean typePage(@ApiParam(name = "pageSize", value = "页大小") @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @ApiParam(name = "pageNum", value = "页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Type> pageVo = typeService.typePage(pageNum, pageSize);
        return RespBean.success(pageVo);
    }

    /**
     * 功能描述: 根据id获取分类
     *
     * @author zry
     */
    @GetMapping("/types/{id}")
    @LogWeb(title = "分类管理", action = "按id获取分类")
    public RespBean getType(@PathVariable @Valid Integer id) {
        Type type = typeService.getById(id);
        if (type == null) {
            return RespBean.error(RespBeanEnum.TYPE_NOT_EXISTED);
        }
        return RespBean.success(new TypeDto(type));
    }

    /**
     * 功能描述: 新增分类
     *
     * @author zry
     */
    @PostMapping("/types")
    @AuthCheck(rank = AuthEnum.GOD)
    @LogWeb(title = "分类管理", action = "新增分类")
    public RespBean postType(@RequestBody @Valid TypeDto typeDto) {
        return typeService.saveType(typeDto);
    }

    /**
     * 功能描述: 新增分类
     *
     * @author zry
     */
    @DeleteMapping("/types/{id}")
    @AuthCheck(rank = AuthEnum.GOD)
    @LogWeb(title = "分类管理", action = "按id删除分类")
    public RespBean deleteType(@PathVariable Long id) {
        return typeService.remove(id);
    }

    /**
     * 功能描述: 更新分类
     *
     * @author zry
     */
    @PutMapping("/types/{id}")
    @AuthCheck(rank = AuthEnum.GOD)
    @LogWeb(title = "分类管理", action = "按id更新分类")
    public RespBean updateType(@RequestBody @Valid TypeDto typeDto, @PathVariable Long id) {
        typeDto.setId(id);
        return typeService.updateById(typeDto);
    }
}
