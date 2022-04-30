package com.zry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zry.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zry
 * @create 2022-04-10 22:54
 */
@Data
@NoArgsConstructor
public class UserDto implements Serializable, BaseDto  {

    public UserDto(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        this.id = user.getId();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.type = user.getType();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }

    public User castUser() {
        User user = new User();
        user.setId(this.id);
        user.setAvatar(this.avatar);
        user.setEmail(this.email);
        user.setNickname(this.nickname);
        user.setUsername(this.username);
        user.setType(this.type);
        user.setIntroduction(this.introduction);
        user.setCreateTime(this.createTime);
        user.setUpdateTime(this.updateTime);
        return user;
    }

    private Long id;

    private String avatar;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String nickname;

    private Integer type;

    private String introduction;

    @Size(message = "用户名应为2-20个字符",min = 2, max = 20)
    private String username;

    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
