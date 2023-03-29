package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.TBlog;
import com.jxy.my_blog.entity.TBlogComment;
import com.jxy.my_blog.entity.TComment;
import com.jxy.my_blog.entity.TParentComment;
import com.jxy.my_blog.exception.CommException;
import com.jxy.my_blog.mapper.TBlogCommentDao;
import com.jxy.my_blog.mapper.TCommentMapper;
import com.jxy.my_blog.mapper.TParentCommentDao;
import com.jxy.my_blog.service.TCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements TCommentService {

    @Autowired
    private TCommentMapper tCommentMapper;
    @Autowired
    private TBlogCommentDao tBlogCommentDao;
    @Autowired
    private TParentCommentDao tParentCommentDao;

    @Transactional
    @Override
    public void saveComment(TComment comment) {
        comment.setCreateTime(new Date());
        Random random = new Random();
        comment.setId(random.nextLong());
        tCommentMapper.addComment(comment);
        Long parentCommentId = comment.getParentComment().getId();
        TBlog blog = comment.getBlog();
        if (null == blog){
            throw new CommException("博客id不能为空！");
        }
        Long blogId = blog.getId();
        TBlogComment tBlogComment = new TBlogComment();
        tBlogComment.setBlogId(blogId);
        tBlogComment.setCommentId(comment.getId());
        tBlogCommentDao.insert(tBlogComment);
        TParentComment tParentComment = new TParentComment();
        tParentComment.setCommentId(comment.getId());
        if (parentCommentId == -1) {
            //添加顶级评论
            tParentComment.setParentId(0L);
        } else {
            //添加下级评论
            tParentComment.setParentId(parentCommentId);
        }
        tParentCommentDao.insert(tParentComment);
    }

    @Override
    public List<TComment> listCommentByBlogId(Long blogId) {
        //通过博客id找顶级评论
        List<TComment> comments = tCommentMapper.getTopCommentByBlogId(blogId);
        for (TComment comment : comments) {
            TComment tComment = new TComment();
            BeanUtils.copyProperties(comment,tComment);
            tComment.setReplayComments(null);
            tComment.setParentComment(null);
            comment.setParentComment(tComment);
        }
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<TComment> eachComment(List<TComment> comments) {
        combineChildren(comments);
        return comments;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<TComment> comments) {
        for (TComment comment : comments) {
            List<TComment> replayComments = tCommentMapper.getAllChildren(comment.getId());
            comment.setReplayComments(replayComments);
            for (TComment replayComment : replayComments) {
                List<TComment> nextReplayComments = tCommentMapper.getAllChildren(replayComment.getId());
                if (!CollectionUtils.isEmpty(nextReplayComments)){
                    replayComment.setReplayComments(nextReplayComments);
                    TComment tComment = new TComment();
                    BeanUtils.copyProperties(replayComment,tComment);
                    tComment.setReplayComments(null);
                    tComment.setParentComment(null);
                    for (TComment nextReplayComment : nextReplayComments) {
                        nextReplayComment.setParentComment(tComment);
                    }
                }
                //设置父节点
                TComment parentComment = tCommentMapper.getParent(replayComment.getId());
                replayComment.setParentComment(parentComment);
                recursively(replayComment);
            }
        }
    }

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(TComment comment) {
        if (comment.getReplayComments().size()>0) {
            List<TComment> replayComments = tCommentMapper.getAllChildren(comment.getId());
            if (!CollectionUtils.isEmpty(replayComments)){
                comment.setReplayComments(replayComments);
                for (TComment reply : replayComments) {
                    TComment tComment = new TComment();
                    BeanUtils.copyProperties(comment,tComment);
                    tComment.setReplayComments(null);
                    tComment.setParentComment(null);
                    reply.setParentComment(tComment);
                    if (reply.getReplayComments().size()>0) {
                        recursively(reply);
                    }
                }
            }
        }
    }
}
