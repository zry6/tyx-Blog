package com.zry.simpleBlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.Tag;
import com.zry.simpleBlog.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 展示博客全部
 *
 * @author zry
 * @create 2022-04-21 10:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean appreciation;

    private Boolean commentable;

    @NotBlank(message = "内容不能为空")
    private String content;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend;

    private Boolean shareStatement;

    @NotBlank(message = "标题不能为空")
    private String title;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateTime;

    private Long views;
    @NotNull(message = "类型不能为空")
    private Long typeId;

    private Long userId;

    private String tagIds;

    private Type type;

    private List<Tag> tags;

    private UserDto user;

    public void init() {
        this.tagIds = tagsToIds(this.tags);
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.createTime = blog.getCreateTime();
        this.updateTime = blog.getUpdateTime();
        this.typeId = blog.getTypeId();
        this.title = blog.getTitle();
        this.userId = blog.getUserId();
        this.views = blog.getViews();
        this.appreciation = blog.getAppreciation();
        this.commentable = blog.getCommentable();
        this.firstPicture = blog.getFirstPicture();
        this.content = blog.getContent();
        this.flag = blog.getFlag();
        this.shareStatement = blog.getShareStatement();
        this.description = blog.getDescription();
        this.recommend = blog.getRecommend();
        this.published = blog.getPublished();
    }

}
