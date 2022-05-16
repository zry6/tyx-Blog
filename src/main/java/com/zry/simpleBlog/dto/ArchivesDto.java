package com.zry.simpleBlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/** 添加文章数据
 * @author zry
 * @create 2022-04-21 10:22
 */
@Data
@NoArgsConstructor
public class ArchivesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String flag;

    private String title;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
