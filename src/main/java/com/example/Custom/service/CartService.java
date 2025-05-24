package com.example.Custom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.OrderDetail;
import com.example.Custom.domain.Product;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.CheckoutCartDTO;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.repository.OrderDetailRepository;
import com.example.Custom.repository.OrderRepository;
import com.example.Custom.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    
    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private ProductRepository productRepository;
    

    


    public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository,
            OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
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
     public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleUpdateCartBeforeCheckout(List<CartItem> CartItems) {
        for (CartItem CartItem : CartItems) {
            Optional<CartItem> cdOptional = this.cartItemRepository.findById(CartItem.getId());
            if (cdOptional.isPresent()) {
                CartItem currentCardDetail = cdOptional.get();
                currentCardDetail.setQuantity(CartItem.getQuantity());
                this.cartItemRepository.save(currentCardDetail);
            }
        }
    }
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (quantity >= 1 && quantity <= cartItem.getProduct().getStock()) {
                cartItem.setQuantity(quantity);
                cartItem.setPrice(cartItem.getProduct().getPrice());
                cartItemRepository.save(cartItem);
            }
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }


    public void handlePlaceOrder(Order order, List<CheckoutCartDTO.CheckoutCartItemDTO> cartItems) {
        order = orderRepository.save(order);

        for (CheckoutCartDTO.CheckoutCartItemDTO item : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            orderDetail.setProducts(product);
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetailRepository.save(orderDetail);
        }

        // Xóa giỏ hàng
        User user = order.getUser();
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            List<CartItem> cartItemss = cart.getCartItems();
            for (CartItem ci : cartItemss) {
                cartItemRepository.deleteById(ci.getId());
            }
            cartRepository.deleteById(cart.getId());
        }
    }
}
