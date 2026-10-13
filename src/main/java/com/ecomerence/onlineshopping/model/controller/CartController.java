package com.ecomerence.onlineshopping.model.controller;

import com.ecomerence.onlineshopping.model.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String view(Authentication auth, Model model) {
        model.addAttribute("cart", cartService.getOrCreateCart(auth.getName()));
        return "cart/view";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long productId,
                      @RequestParam(defaultValue = "1") int quantity,
                      Authentication auth,
                      RedirectAttributes ra) {
        try {
            cartService.addItem(auth.getName(), productId, quantity);
            ra.addFlashAttribute("success", "Added to cart");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long itemId,
                         @RequestParam int quantity,
                         Authentication auth) {
        cartService.updateQuantity(auth.getName(), itemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long itemId, Authentication auth) {
        cartService.removeItem(auth.getName(), itemId);
        return "redirect:/cart";
    }}
