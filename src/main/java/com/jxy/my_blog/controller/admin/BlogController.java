package com.jxy.my_blog.controller.admin;

import com.jxy.my_blog.entity.TBlog;
import com.jxy.my_blog.entity.TUser;
import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.service.TTagService;
import com.jxy.my_blog.service.TTypeService;
import com.jxy.my_blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private TBlogService blogService;
    @Autowired
    private TTypeService typeService;
    @Autowired
    private TTagService tagService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1") Integer page, BlogQuery query, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.queryBlog(page, query));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(required = false,defaultValue = "1") Integer page,
                         BlogQuery query, Model model) {
        model.addAttribute("page", blogService.queryBlog(page, query));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new TBlog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        TBlog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(TBlog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((TUser) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        TBlog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

}
