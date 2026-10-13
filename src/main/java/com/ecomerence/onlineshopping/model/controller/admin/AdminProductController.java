package com.ecomerence.onlineshopping.model.controller.admin;

import com.ecomerence.onlineshopping.dto.ProductForm;
import com.ecomerence.onlineshopping.model.service.CategoryService;
import com.ecomerence.onlineshopping.model.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService p, CategoryService c) {
        this.productService = p;
        this.categoryService = c;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("id").descending());
        model.addAttribute("products", productService.findAll(pageable));
        return "admin/products/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ProductForm());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("mode", "create");
        return "admin/products/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") ProductForm form,
                         BindingResult result,
                         Model model,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("mode", "create");
            return "admin/products/form";
        }
        productService.create(form);
        ra.addFlashAttribute("success", "Product created");
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var p = productService.findById(id);
        ProductForm form = new ProductForm();
        form.setName(p.getName());
        form.setDescription(p.getDescription());
        form.setPrice(p.getPrice());
        form.setStock(p.getStock());
        form.setImageUrl(p.getImageUrl());
        form.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);

        model.addAttribute("form", form);
        model.addAttribute("productId", id);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("mode", "edit");
        return "admin/products/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ProductForm form,
                         BindingResult result,
                         Model model,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("productId", id);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("mode", "edit");
            return "admin/products/form";
        }
        productService.update(id, form);
        ra.addFlashAttribute("success", "Product updated");
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        productService.delete(id);
        ra.addFlashAttribute("success", "Product deleted");
        return "redirect:/admin/products";
    }}
