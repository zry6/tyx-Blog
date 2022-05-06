package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.entity.BlogTags;
import com.zry.simpleBlog.mapper.BlogTagsMapper;
import com.zry.simpleBlog.service.IBlogTagsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags> implements IBlogTagsService {

}
