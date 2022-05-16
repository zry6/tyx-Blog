package com.zry.simpleBlog.dto;

import com.zry.simpleBlog.comment.aop.annotations.IdempotentField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 暂时不需要
 * @author zry
 * @create 2022-04-27 9:35
 */

@Data
@ApiModel(value = "BaseIdempotentDto对象", description = "需要使用messageId实现幂等的dto对象基类")
public class BaseIdempotentDto {

    @IdempotentField
    @ApiModelProperty(value = "消息ID,用于实现幂等操作", name = "messageId", dataType = "String")
    protected String messageId;
}
