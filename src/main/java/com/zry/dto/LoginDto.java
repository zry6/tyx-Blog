package com.zry.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数据传输对象 Login
 *
 * @author zry
 * @create 2022-04-09 16:09
 */
@Data
public class LoginDto implements Serializable, BaseDto {

    @Size(min = 2, max = 20)
    @NotBlank(message = "昵称不能为空")
    String username;

    @Length(min = 32)
    @NotBlank(message = "密码不能为空")
    String password;

}
