package com.zry.simpleBlog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zry
 * @create 2022-05-19 17:55
 */
@Data
public class UpdatePswDto implements Serializable {

    @Length(min = 4)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Length(min = 4)
    @NotBlank(message = "密码不能为空")
    private String checkPassword;
}
