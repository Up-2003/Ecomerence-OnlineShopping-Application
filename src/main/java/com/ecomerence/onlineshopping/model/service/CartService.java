package com.ecomerence.onlineshopping.model.service;

import com.ecomerence.onlineshopping.model.entity.Cart;
import com.ecomerence.onlineshopping.model.entity.CartItem;
import com.ecomerence.onlineshopping.model.entity.Product;
import com.ecomerence.onlineshopping.model.entity.User;
import com.ecomerence.onlineshopping.model.repository.CartItemRepository;
import com.ecomerence.onlineshopping.model.repository.CartRepository;
import com.ecomerence.onlineshopping.model.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final UserService userService;

    public CartService(CartRepository cr, CartItemRepository ir,
                       ProductRepository pr, UserService us) {
        this.cartRepo = cr;
        this.itemRepo = ir;
        this.productRepo = pr;
        this.userService = us;
    }

    public Cart getOrCreateCart(String username) {
        User user = userService.findByUsername(username);
        return cartRepo.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                });
    }

    public void addItem(String username, Long productId, int quantity) {
        Cart cart = getOrCreateCart(username);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        CartItem item = itemRepo.findByCartIdAndProductId(cart.getId(), productId)
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setProduct(product);
                    ci.setQuantity(0);
                    cart.addItem(ci);
                    return ci;
                });

        item.setQuantity(item.getQuantity() + quantity);
        cartRepo.save(cart);
    }

    public void updateQuantity(String username, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(username);
        CartItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (quantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(quantity);
        }
        cartRepo.save(cart);
    }

    public void removeItem(String username, Long itemId) {
        Cart cart = getOrCreateCart(username);
        itemRepo.findById(itemId).ifPresent(cart::removeItem);
        cartRepo.save(cart);
    }

    public void clear(String username) {
        Cart cart = getOrCreateCart(username);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
