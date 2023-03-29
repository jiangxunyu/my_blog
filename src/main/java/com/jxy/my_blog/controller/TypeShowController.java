package com.jxy.my_blog.controller;

import com.jxy.my_blog.entity.TType;
import com.jxy.my_blog.service.TBlogService;
import com.jxy.my_blog.service.TTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TTypeService tTypeService;
    @Autowired
    private TBlogService tBlogService;

    /**
     * 点击类型查找博客
     * @param page
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    private String types(@RequestParam(required = false,defaultValue = "1") Integer page, @PathVariable Long id, Model model){
        List<TType> types = tTypeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", tBlogService.listBlogWithType(page, id));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
