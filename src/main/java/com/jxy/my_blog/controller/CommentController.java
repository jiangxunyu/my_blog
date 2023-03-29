package com.jxy.my_blog.controller;

import com.jxy.my_blog.entity.TComment;
import com.jxy.my_blog.entity.TUser;
import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.service.TCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private TCommentService tCommentService;

    @Autowired
    private TBlogService tBlogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", tCommentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(TComment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(tBlogService.getBlogById(blogId));
        TUser user = (TUser) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        tCommentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
