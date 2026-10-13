package com.ecomerence.onlineshopping.model.controller;

import com.ecomerence.onlineshopping.model.service.CategoryService;
import com.ecomerence.onlineshopping.model.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService p, CategoryService c) {
        this.productService = p;
        this.categoryService = c;
    }

    @GetMapping("/")
    public String home(Model model) {
        var pageable = PageRequest.of(0, 8, Sort.by("createdAt").descending());
        model.addAttribute("featuredProducts", productService.findAll(pageable));
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }
}
