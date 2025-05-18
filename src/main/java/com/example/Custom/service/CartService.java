package com.example.Custom.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public void handleRemoveCartItem(Long cartItemId, HttpSession session) {
        if (session == null) {
            throw new IllegalStateException("Session is not available");
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            throw new IllegalArgumentException("CartItem not found with ID: " + cartItemId);
        }

        CartItem cartItem = cartItemOptional.get();
        Cart currentCart = cartRepository.findById(cartItem.getCart().getId())
            .orElseThrow(() -> new IllegalStateException("Cart not found for CartItem"));

        currentCart.getCartItems().remove(cartItem);

        cartItemRepository.delete(cartItem);
        cartItemRepository.flush();

        long remainingItems = cartItemRepository.countByCart(currentCart);
        System.out.println("Remaining items for cart " + currentCart.getId() + ": " + remainingItems);

        if (remainingItems == 0) {
            System.out.println("Deleting cart " + currentCart.getId());
            cartRepository.delete(currentCart);
            session.setAttribute("sum", 0);
        } else {
            cartRepository.save(currentCart);
            session.setAttribute("sum", (int) remainingItems);
        }
    }
}
