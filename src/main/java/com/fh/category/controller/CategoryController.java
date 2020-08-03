package com.fh.category.controller;

import com.fh.category.service.CategoryService;
import com.fh.common.ServerResponse;
import com.fh.util.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("CategoryController")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询树形结构
    @RequestMapping("categoryList")
    @Ignore
    public ServerResponse categoryList(){
        return categoryService.categoryList();
    }
}
