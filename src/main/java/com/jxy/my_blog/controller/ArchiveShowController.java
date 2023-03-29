package com.jxy.my_blog.controller;

import com.jxy.my_blog.service.TBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    private TBlogService tBlogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", tBlogService.archiveBlog());
        model.addAttribute("blogCount", tBlogService.countBlog());
        return "archives";
    }
}
