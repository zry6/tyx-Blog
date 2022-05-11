package com.zry.simpleBlog.dto;

import com.zry.simpleBlog.entity.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 南宫乘风
 *
 * @author 14470
 */
@Data
@NoArgsConstructor
public class CommentDto implements Serializable, BaseDto {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;

    private Date createTime;

    private Blog blog;

    private List<CommentDto> replyComments = new ArrayList<>();

    private CommentDto parentComment;

    private boolean adminComment;

}
