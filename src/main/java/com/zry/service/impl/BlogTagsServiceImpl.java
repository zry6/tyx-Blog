package com.zry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.entity.BlogTags;
import com.zry.mapper.BlogTagsMapper;
import com.zry.service.IBlogTagsService;
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
