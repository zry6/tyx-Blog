package com.zry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Data
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Boolean adminComment;

    private String avatar;

    private String content;

    private Date createTime;

    private String email;

    private String nickname;

    private Long blogId;

    private Long parentCommentId;


}
