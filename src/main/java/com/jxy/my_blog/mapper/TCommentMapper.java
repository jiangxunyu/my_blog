package com.jxy.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxy.my_blog.entity.TComment;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
public interface TCommentMapper extends BaseMapper<TComment> {

    List<TComment> getTopCommentByBlogId(Long blogId);

    List<TComment> getAllChildren(Long parentId);

    void addComment(TComment comment);

    TComment getParent(Long id);
}
