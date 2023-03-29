package com.jxy.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxy.my_blog.entity.TBlog;
import com.jxy.my_blog.vo.BlogQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Component
public interface TBlogMapper extends BaseMapper<TBlog> {

    IPage<TBlog> listBlog(IPage<TBlog> viewPage);

    void updateViews(Long id);

    IPage<TBlog> listBlogWithQuery(IPage<TBlog> viewPage, String query);

    IPage<TBlog> listBlogWithType(IPage<TBlog> viewPage, Long typeId);

    IPage<TBlog> listBlogWithTag(IPage<TBlog> viewPage, Long tagId);

    List<TBlog> listAllBlog();

    TBlog getBlog(Long id);

    IPage<TBlog> queryBlog(IPage<TBlog> viewPage, BlogQuery query);
}
