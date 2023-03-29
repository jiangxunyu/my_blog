package com.jxy.my_blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxy.my_blog.entity.TBlog;
import com.jxy.my_blog.vo.BlogQuery;
import com.jxy.my_blog.vo.MyPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public interface TBlogService extends IService<TBlog> {

    MyPage listBlog(Integer page);

    List<TBlog> listRecommendBlogTop(int size);

    TBlog getBlogById(Long id);

    TBlog getAndConvert(Long id);

    MyPage listBlog(Integer page, String query);

    MyPage listBlogWithType(Integer page, Long typeId);

    MyPage listBlogWithTag(Integer page, Long tagId);

    Map<String,List<TBlog>> archiveBlog();

    Long countBlog();

    TBlog saveBlog(TBlog blog);

    TBlog updateBlog(TBlog blog);

    void deleteBlog(Long id);

    MyPage<TBlog> queryBlog(Integer page, BlogQuery query);
}
