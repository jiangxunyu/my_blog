package com.jxy.my_blog.controller;

import com.jxy.my_blog.entity.TTag;
import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.service.TTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TTagService tTagService;

    @Autowired
    private TBlogService tBlogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(required = false,defaultValue = "1") Integer page,@PathVariable Long id, Model model) {
        List<TTag> tags = tTagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", tBlogService.listBlogWithTag(page,id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
