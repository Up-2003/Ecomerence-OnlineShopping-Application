package com.ecomerence.onlineshopping.model.service;
import com.ecomerence.onlineshopping.dto.CheckoutForm;
import com.ecomerence.onlineshopping.model.entity.*;
import com.ecomerence.onlineshopping.model.repository.OrderRepository;
import com.ecomerence.onlineshopping.model.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final CartService cartService;
    private final UserService userService;

    public OrderService(OrderRepository o, ProductRepository p,
                        CartService c, UserService u) {
        this.orderRepo = o;
        this.productRepo = p;
        this.cartService = c;
        this.userService = u;
    }

    public Order checkout(String username, CheckoutForm form) {
        Cart cart = cartService.getOrCreateCart(username);

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        User user = userService.findByUsername(username);

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(form.getShippingAddress());
        order.setPhone(form.getPhone());
        order.setNote(form.getNote());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cart.getItems()) {
            Product p = ci.getProduct();

            if (p.getStock() < ci.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock: " + p.getName());
            }

            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setProductImage(p.getImageUrl());
            oi.setPrice(p.getPrice());
            oi.setQuantity(ci.getQuantity());
            order.addItem(oi);

            // Deduct stock
            p.setStock(p.getStock() - ci.getQuantity());
            productRepo.save(p);

            total = total.add(oi.getSubtotal());
        }

        order.setTotalAmount(total);
        order = orderRepo.save(order);

        cartService.clear(username);

        return order;
    }

    public Page<Order> findByUser(String username, Pageable pageable) {
        User user = userService.findByUsername(username);
        return orderRepo.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
    }

    public Order findById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order findByOrderNumber(String number) {
        return orderRepo.findByOrderNumber(number)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Admin
    public Page<Order> findAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    public void updateStatus(Long orderId, OrderStatus status) {
        Order order = findById(orderId);
        order.setStatus(status);
        orderRepo.save(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + LocalDateTime.now().getYear() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
