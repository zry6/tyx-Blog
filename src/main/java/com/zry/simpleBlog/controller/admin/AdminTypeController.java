package com.zry.simpleBlog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.CheckLogin;
import com.zry.simpleBlog.comment.aop.annotations.LogWeb;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.dto.TypeDto;
import com.zry.simpleBlog.entity.Type;
import com.zry.simpleBlog.service.ITypeService;
import io.swagger.annotations.Api;
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
    public RespBean typePage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Type> pageVo = typeService.typePage(page, pageSize);
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
    @CheckLogin
    @LogWeb(title = "分类管理", action = "新增分类")
    public RespBean postType(@RequestBody @Valid TypeDto typeDto) {
        typeService.saveType(typeDto);
        return RespBean.success(RespBeanEnum.POST_SUCCESS);
    }

    /**
     * 功能描述: 新增分类
     *
     * @author zry
     */
    @DeleteMapping("/types/{id}")
    @CheckLogin
    @LogWeb(title = "分类管理", action = "按id删除分类")
    public RespBean deleteType(@PathVariable Long id) {
        boolean b = typeService.remove(id);
        if (!b) {
            RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
    }

    /**
     * 功能描述: 更新分类
     *
     * @author zry
     */
    @PutMapping("/types/{id}")
    @CheckLogin
    @LogWeb(title = "分类管理", action = "按id更新分类")
    public RespBean updateType(@RequestBody @Valid TypeDto typeDto, @PathVariable Long id) {
        typeDto.setId(id);
        typeService.updateById(typeDto);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }
}
