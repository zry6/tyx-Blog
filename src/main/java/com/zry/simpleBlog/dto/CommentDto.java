package com.zry.simpleBlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentField;
import com.zry.simpleBlog.comment.idempotent.IdempotentInterface;
import com.zry.simpleBlog.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 14470
 */
@Data
@NoArgsConstructor
public class CommentDto implements Serializable, BaseDto, IdempotentInterface {

    private Long id;

    @IdempotentField
    @NotNull(message = "昵称不能为空")
    private String nickname;

    @IdempotentField
    private String email;

    @IdempotentField
    @NotNull(message = "内容不能为空")
    private String content;

    @IdempotentField
    @ApiModelProperty(example = "/images/avatar/avatar1.png")
    private String avatar;

    @IdempotentField
    @ApiModelProperty(example = "{id: 1}")
    private CommentDto parentComment;



    private boolean adminComment;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;


    @IdempotentField
    private Long blogId;


    @ApiModelProperty(hidden = true)
    private List<CommentDto> replyComments = new ArrayList<>();


    public Comment caseToComment() {
        Comment comment = new Comment();
        comment.setAdminComment(this.adminComment);
        comment.setContent(this.getContent());
        if (this.getParentComment() == null || StringUtils.isEmpty(this.getParentComment().getId())) {
            comment.setParentCommentId(null);
        } else {
            comment.setParentCommentId(this.getParentComment().getId());
        }
        comment.setEmail(this.getEmail());
        comment.setBlogId(this.getBlogId());
        comment.setCreateTime(this.getCreateTime());
        comment.setNickname(this.getNickname());
        comment.setAvatar(this.getAvatar());
        return comment;
    }
}
