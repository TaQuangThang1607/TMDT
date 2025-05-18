package com.example.Custom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.CartRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    
    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    

    public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }


    public void handleRemoveCartItem(Long cartItemId, HttpSession session) {
        if (session == null) {
            throw new IllegalStateException("Session is not available");
        }

        Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Cart currentCart = cartItem.getCart();
            
            if (currentCart == null) {
                throw new IllegalStateException("Cart not found for CartItem");
            }

            // Xóa CartItem khỏi danh sách cartItems của Cart
            currentCart.getCartItems().remove(cartItem);
            
            // Xóa CartItem khỏi database
            this.cartItemRepository.deleteById(cartItemId);

            // Kiểm tra nếu Cart không còn CartItem
            if (currentCart.getCartItems().isEmpty()) {
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            } else {
                // Cập nhật session với số lượng CartItem
                session.setAttribute("sum", currentCart.getCartItems().size());
                this.cartRepository.save(currentCart);
            }
        } else {
            throw new IllegalArgumentException("CartItem not found with ID: " + cartItemId);
        }
    }
}
