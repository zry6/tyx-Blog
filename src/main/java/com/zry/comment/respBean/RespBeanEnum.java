package com.zry.comment.respBean;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述: 返回 枚举单例
 *
 * @author zry
 * @return
 * @create 2022/4/9
 */
@Getter
@AllArgsConstructor
public enum RespBeanEnum  {
//3xx 重定向
//4xx 客户端错误
//5xx 服务器错误
//2xx 状态码
// GET:    200 OK  POST:   201  Created
// PUT:    200 OK  PATCH:  200  OK       DELETE: 204 No Content
    SUCCESS(200, "SUCCESS"),
    POST_SUCCESS(201 , "创建成功"),
    UPDATE_SUCCESS(203, "更新成功"),
    DELETE_SUCCESS(204 , "删除成功"),
    RUNNING(205,"请求正在处理中"),

    //5xx状态码表示服务端错误
    SERVICE_ERROR(500, "服务器错误,请联系管理员"),
    SERVICE_UNAVAILABLE(503, "服务器当前无法处理请求,网站维护状态"),

    //4xx 状态码表示客户端错误
    ERROR(400, "客户端请求错误"),
    TOKEN_ERROR(400101, "登录凭证已过期，请重新登录"),
    AUTH_ERROR(403, "权限不足"),
    NOT_FOUND_ERROR(404,    "请求的资源不存在"),

    LOGIN_ERROR(400001, "用户名或密码错误"),
    UPDATE_ERROR(400002, "更新失败"),
    POST_ERROR(400004, "创建失败"),
    DELETE_ERROR(400006, "删除失败"),
    TYPE_EXISTED(400005, "分类已存在"),
    TYPE_NOT_EXISTED(400008, "分类不存在"),
    TAG_NOT_EXISTED(400009, "标签不存在"),
    TAG_EXISTED(400010, "标签已存在"),
    DELETE_CACHE_ERROR(400003, "删除缓存异常"),
    GET_CACHE_ERROR(400011, "获取缓存异常"),
    SET_CACHE_ERROR(400012, "写缓存异常");


    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 附带消息
     */
    private final String message;

}
