package com.zry.simpleBlog.dto;

import com.zry.simpleBlog.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author zry
 * @create 2022-04-16 22:26
 */
@Data
@NoArgsConstructor
public class TagDto implements Serializable {

    private Long id;
    @NotBlank(message = "标签名不能为空")
    @Size(message = "标签名应在1-10个字符哦", min = 1, max = 10)
    private String name;

    private Integer blogCount;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
