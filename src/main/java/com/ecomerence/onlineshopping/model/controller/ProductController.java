package com.ecomerence.onlineshopping.model.controller;

import com.ecomerence.onlineshopping.model.entity.Product;
import com.ecomerence.onlineshopping.model.service.CategoryService;
import com.ecomerence.onlineshopping.model.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService p, CategoryService c) {
        this.productService = p;
        this.categoryService = c;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       @RequestParam(required = false) String sort,
                       Model model) {
        Sort sortBy = switch (sort == null ? "newest" : sort) {
            case "price-asc" -> Sort.by("price").ascending();
            case "price-desc" -> Sort.by("price").descending();
            case "name" -> Sort.by("name").ascending();
            default -> Sort.by("createdAt").descending();
        };

        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Product> products = productService.findAll(pageable);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("sort", sort);
        return "product/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("name"));
        model.addAttribute("products", productService.search(q, pageable));
        model.addAttribute("query", q);
        return "product/search";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Long categoryId,
                         @RequestParam(required = false) BigDecimal minPrice,
                         @RequestParam(required = false) BigDecimal maxPrice,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("name"));
        model.addAttribute("products",
                productService.filter(categoryId, minPrice, maxPrice, pageable));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", categoryId);
        return "product/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/detail";
    }
}
