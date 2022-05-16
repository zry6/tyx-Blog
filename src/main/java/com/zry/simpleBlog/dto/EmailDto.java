package com.zry.simpleBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zry
 * @create 2022-05-16 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String emailTo;
    private String content;
    private String blogTitle;
    private String url;
}
