package com.zry.simpleBlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentField;
import com.zry.simpleBlog.comment.idempotent.IdempotentInterface;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 添加文章数据
 * @author zry
 * @create 2022-04-21 10:22
 */
@Data
@NoArgsConstructor
public class PostBlogDto implements Serializable, BaseDto, IdempotentInterface {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean appreciation;

    private Boolean commentable;

    @IdempotentField
    @NotNull(message = "内容不能为空")
    @Size(min = 1,message = "内容不能为空")
    private String content;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend;

    private Boolean shareStatement;

    @IdempotentField
    @NotNull(message = "标题不能为空")
    @Size(min = 1,message = "标题不能为空")
    private String title;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Long views;
    @IdempotentField
    @NotNull(message = "分类不能为空")
    private Long typeId;

    private Long userId;

    private String tagIds;


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

    public Blog caseToBlog(){
        Blog blog = new Blog();
        blog.setId(this.id);
        blog.setCreateTime(this.createTime);
        blog.setUpdateTime(this.updateTime);
        blog.setTypeId(this.typeId);
        blog.setTitle(this.title);
        blog.setUserId(this.userId);
        blog.setViews(this.views);
        blog.setAppreciation(this.appreciation);
        blog.setCommentable(this.commentable);
        blog.setFirstPicture(this.firstPicture);
        blog.setContent(this.content);
        blog.setFlag(this.flag);
        blog.setShareStatement(this.shareStatement);
        blog.setDescription(this.description);
        blog.setRecommend(this.recommend);
        blog.setPublished(this.published);
        return blog;
    }


}
