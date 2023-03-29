package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.*;
import com.jxy.my_blog.exception.CommException;
import com.jxy.my_blog.mapper.TBlogMapper;
import com.jxy.my_blog.mapper.TBlogTagDao;
import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.util.MarkdownUtils;
import com.jxy.my_blog.util.PageUtils;
import com.jxy.my_blog.vo.BlogQuery;
import com.jxy.my_blog.vo.MyPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements TBlogService {

    @Autowired
    private TBlogMapper tBlogDao;
    @Autowired
    private TBlogTagDao tBlogTagDao;

    /**
     * 查第几页数据，默认每页显示10条
     * @param page
     * @return
     */
    @Override
    public MyPage<TBlog> listBlog(Integer page) {
        MyPage<TBlog> myPage = new MyPage<>();
        IPage<TBlog> viewPage = new Page<>(page,10);
        IPage<TBlog> result = tBlogDao.listBlog(viewPage);
        PageUtils<TBlog> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    @Override
    public List<TBlog> listRecommendBlogTop(int size) {
        IPage<TBlog> viewPage = new Page<>(1,size);
        IPage<TBlog> result = tBlogDao.listBlog(viewPage);
        return result.getRecords();
    }

    public TBlog getBlogById(Long id){
        return tBlogDao.getBlog(id);
    }

    @Override
    public TBlog getAndConvert(Long id) {
        TBlog blog = tBlogDao.getBlog(id);
        if (blog == null) {
            throw new CommException("该博客不存在");
        }
        TBlog b = new TBlog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        tBlogDao.updateViews(id);
        return b;
    }

    @Override
    public MyPage<TBlog> listBlog(Integer page, String query) {
        MyPage<TBlog> myPage = new MyPage<>();
        IPage<TBlog> viewPage = new Page<>(page,10);
        IPage<TBlog> result = tBlogDao.listBlogWithQuery(viewPage,query);
        PageUtils<TBlog> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    @Override
    public MyPage<TBlog> listBlogWithType(Integer page, Long typeId) {
        MyPage<TBlog> myPage = new MyPage<>();
        IPage<TBlog> viewPage = new Page<>(page,10);
        IPage<TBlog> result = tBlogDao.listBlogWithType(viewPage,typeId);
        PageUtils<TBlog> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    @Override
    public MyPage<TBlog> listBlogWithTag(Integer page, Long tagId) {
        MyPage<TBlog> myPage = new MyPage<>();
        IPage<TBlog> viewPage = new Page<>(page,10);
        IPage<TBlog> result = tBlogDao.listBlogWithTag(viewPage,tagId);
        PageUtils<TBlog> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    /**
     * 归档
     * @return
     */
    @Override
    public Map<String, List<TBlog>> archiveBlog() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Map<String, List<TBlog>> map = new HashMap<>();
        List<TBlog> tBlogs = tBlogDao.listAllBlog();
        if (!CollectionUtils.isEmpty(tBlogs)){
            map = tBlogs.stream().collect(Collectors.groupingBy(tBlog -> dateFormat.format(tBlog.getUpdateTime())));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return tBlogDao.selectCount(null);
    }

    @Override
    public TBlog saveBlog(TBlog blog) {
        TType type = blog.getType();
        List<TTag> tags = blog.getTags();
        TUser user = blog.getUser();
        blog.setTags(null);
        blog.setType(null);
        blog.setUser(null);
        blog.setComments(null);
        if (null != type){
            blog.setTypeId(type.getId());
        }
        if (null != user){
            blog.setUserId(user.getId());
        }
        Date createTime = blog.getCreateTime();
        if (null == createTime){
            blog.setCreateTime(new Date());
        }
        Date updateTime = blog.getUpdateTime();
        if (null == updateTime){
            blog.setUpdateTime(new Date());
        }
        tBlogDao.insert(blog);

        if (!CollectionUtils.isEmpty(tags)){
            for (TTag tag : tags) {
                TBlogTag tBlogTag = new TBlogTag();
                tBlogTag.setBlogId(blog.getId());
                tBlogTag.setTagId(tag.getId());
                tBlogTagDao.insert(tBlogTag);
            }
        }
        return blog;
    }

    @Transactional
    @Override
    public TBlog updateBlog(TBlog blog) {
        TType type = blog.getType();
        List<TTag> tags = blog.getTags();
        TUser user = blog.getUser();
        blog.setTags(null);
        blog.setType(null);
        blog.setUser(null);
        blog.setComments(null);
        if (null != type){
            blog.setTypeId(type.getId());
        }
        if (null != user){
            blog.setUserId(user.getId());
        }
        Date createTime = blog.getCreateTime();
        if (null == createTime){
            blog.setCreateTime(new Date());
        }
        Date updateTime = blog.getUpdateTime();
        if (null == updateTime){
            blog.setUpdateTime(new Date());
        }
        tBlogDao.updateById(blog);

        tBlogTagDao.deleteByBlogId(blog.getId());
        if (!CollectionUtils.isEmpty(tags)){
            for (TTag tag : tags) {
                TBlogTag tBlogTag = new TBlogTag();
                tBlogTag.setBlogId(blog.getId());
                tBlogTag.setTagId(tag.getId());
                tBlogTagDao.insert(tBlogTag);
            }
        }
        return blog;
    }

    @Override
    public void deleteBlog(Long id) {
        tBlogDao.deleteById(id);
    }

    @Override
    public MyPage<TBlog> queryBlog(Integer page, BlogQuery query) {
        MyPage<TBlog> myPage = new MyPage<>();
        IPage<TBlog> viewPage = new Page<>(page,10);
        IPage<TBlog> result = tBlogDao.queryBlog(viewPage,query);
        PageUtils<TBlog> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

}








































































