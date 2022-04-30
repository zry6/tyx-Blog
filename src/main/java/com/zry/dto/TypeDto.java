package com.zry.dto;

import com.zry.entity.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author zry
 * @create 2022-04-16 22:26
 */
@Data
@NoArgsConstructor
public class TypeDto implements Serializable, BaseDto {

    private Long id;
    @NotNull(message = "分类名不能为空")
    @Size(message = "分类名应在1-10个字符哦", min = 1, max = 10)
    private String name;

    private Integer blogCount;

    public TypeDto(Type type) {
        this.id = type.getId();
        this.name = type.getName();
    }
}
