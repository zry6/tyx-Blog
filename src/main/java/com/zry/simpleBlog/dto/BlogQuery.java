package com.zry.simpleBlog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zry
 * @description
 * @title
 */
@Data
@NoArgsConstructor
@ApiModel(value = "BlogQuery", description = "可选的用于条件查询的实体")
public class BlogQuery implements Serializable, BaseDto{

    @ApiParam(value = "标题" ,example = "java")
    private String title;
    @ApiParam(value = "分类id" , example = "1")
    private Long typeId;
    @ApiParam(value = "已发布" ,example = "true")
    private boolean published;

    private Long userId;
}
