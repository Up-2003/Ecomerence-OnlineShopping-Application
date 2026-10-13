package com.ecomerence.onlineshopping.model.controller;

import com.ecomerence.onlineshopping.dto.CheckoutForm;
import com.ecomerence.onlineshopping.model.service.CartService;
import com.ecomerence.onlineshopping.model.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService o, CartService c) {
        this.orderService = o;
        this.cartService = c;
    }

    @GetMapping("/checkout")
    public String checkoutPage(Authentication auth, Model model) {
        var cart = cartService.getOrCreateCart(auth.getName());
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("form", new CheckoutForm());
        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("form") CheckoutForm form,
                           BindingResult result,
                           Authentication auth,
                           Model model,
                           RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("cart", cartService.getOrCreateCart(auth.getName()));
            return "order/checkout";
        }

        try {
            var order = orderService.checkout(auth.getName(), form);
            ra.addFlashAttribute("order", order);
            return "redirect:/orders/success/" + order.getOrderNumber();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cart", cartService.getOrCreateCart(auth.getName()));
            return "order/checkout";
        }
    }

    @GetMapping("/success/{orderNumber}")
    public String success(@PathVariable String orderNumber, Model model) {
        model.addAttribute("order", orderService.findByOrderNumber(orderNumber));
        return "order/success";
    }

    @GetMapping
    public String history(Authentication auth,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        model.addAttribute("orders", orderService.findByUser(auth.getName(), pageable));
        return "order/history";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/detail";
    }
}

