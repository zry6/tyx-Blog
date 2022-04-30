package com.zry.comment.respBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zry
 * @create 2022-04-09 10:22
 */

@ApiModel(
        value = "RespBean",
        description = "全局统一返回json的封装"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean implements Serializable {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "数据类型")
    private String message;
    @ApiModelProperty(value = "数据实体")
    private Object obj;
    /**
     * 功能描述: 返回成功结果
     *
     * @return RespBean
     * @author zry
     */
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 功能描述: 返回成功结果
     *
     * @return RespBean
     * @author zry
     */
    public static RespBean success(Object obj) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    /**
     * 功能描述: 返回成功结果
     *
     * @return RespBean
     * @author zry
     */
    public static RespBean success(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    /**
     * 功能描述: 返回成功结果
     *
     * @return RespBean
     * @author zry
     */
    public static RespBean success(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }

    /**
     * 功能描述：返回失败结果
     *
     * @param respBeanEnum;
     * @return
     */
    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }
    /**
     * 功能描述：返回失败结果
     *
     * @param messageCode,detailMessage;
     * @return RespBean
     */
    public static RespBean error(int messageCode, String detailMessage) {
        return new RespBean(messageCode, detailMessage, null);
    }
    /**
     * 功能描述：返回失败结果
     *
     * @param respBeanEnum,obj;
     * @return RespBean
     */
    public static RespBean error(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }

}
