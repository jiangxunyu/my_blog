package com.jxy.my_blog.controller;

import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.service.TTagService;
import com.jxy.my_blog.service.TTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private TBlogService tBlogService;
    @Autowired
    private TTypeService tTypeService;
    @Autowired
    private TTagService tTagService;

    @GetMapping("/")
    public String index(@RequestParam(required = false,defaultValue = "1") Integer page, Model model) {
        model.addAttribute("page",tBlogService.listBlog(page));
        model.addAttribute("types", tTypeService.listTypeTop(6));
        model.addAttribute("tags", tTagService.listTagTop(10));
        model.addAttribute("recommendBlogs", tBlogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam String query,Model model){
        model.addAttribute("page",tBlogService.listBlog(page,query));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/{page}")
    public String indexPage(@PathVariable(required = false) Integer page, Model model) {
        model.addAttribute("page",tBlogService.listBlog(page));
        model.addAttribute("types", tTypeService.listTypeTop(6));
        model.addAttribute("tags", tTagService.listTagTop(10));
        model.addAttribute("recommendBlogs", tBlogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", tBlogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", tBlogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}

















