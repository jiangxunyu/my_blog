package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.TBlogComment;
import com.jxy.my_blog.mapper.TBlogCommentDao;
import com.jxy.my_blog.service.TBlogCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public class TBlogCommentServiceImpl extends ServiceImpl<TBlogCommentDao, TBlogComment> implements TBlogCommentService {

}
