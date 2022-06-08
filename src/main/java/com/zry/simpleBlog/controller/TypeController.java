package com.zry.simpleBlog.controller;


import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.entity.Type;
import com.zry.simpleBlog.service.ITypeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "分类接口")
@RestController
public class TypeController {
    @Resource
    private ITypeService typeService;

    @GetMapping("types")
    private RespBean getTypes() {
        List<Type> list = typeService.list();
        return RespBean.success(list);
    }
    /**
     * 功能描述: 获取type和对应的文章数量
     *
     * @create 2022/4/30
     */
    @GetMapping("types/blogCount")
    private RespBean getTypesAndBlogCount() {
        return typeService.listAndBlogCount();
    }
}
