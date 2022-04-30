package com.zry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.entity.Comment;
import com.zry.service.ICommentService;
import com.zry.mapper.CommentMapper;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
