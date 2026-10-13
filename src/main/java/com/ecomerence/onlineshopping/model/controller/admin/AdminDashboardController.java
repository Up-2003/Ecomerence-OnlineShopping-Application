package com.ecomerence.onlineshopping.model.controller.admin;

import com.ecomerence.onlineshopping.model.repository.CategoryRepository;
import com.ecomerence.onlineshopping.model.repository.OrderRepository;
import com.ecomerence.onlineshopping.model.repository.ProductRepository;
import com.ecomerence.onlineshopping.model.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public AdminDashboardController(ProductRepository p, OrderRepository o,
                                    UserRepository u, CategoryRepository c) {
        this.productRepo = p;
        this.orderRepo = o;
        this.userRepo = u;
        this.categoryRepo = c;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("productCount", productRepo.count());
        model.addAttribute("orderCount", orderRepo.count());
        model.addAttribute("userCount", userRepo.count());
        model.addAttribute("categoryCount", categoryRepo.count());
        model.addAttribute("recentOrders",
                orderRepo.findAll(org.springframework.data.domain.PageRequest.of(0, 5,
                                org.springframework.data.domain.Sort.by("createdAt").descending()))
                        .getContent());
        return "admin/dashboard";
    }
}
