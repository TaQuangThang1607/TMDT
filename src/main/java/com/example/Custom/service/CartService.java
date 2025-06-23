package com.example.Custom.service;

import java.util.ArrayList;
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
import com.example.Custom.domain.dto.CartDTO;
import com.example.Custom.domain.dto.CheckoutCartDTO;
import com.example.Custom.domain.dto.CartItemDTO;
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


    @Transactional
    public void handlePlaceOrder(Order order, List<CheckoutCartDTO.CheckoutCartItemDTO> checkoutItems) {
        orderRepository.save(order);

        for (CheckoutCartDTO.CheckoutCartItemDTO itemDTO : checkoutItems) {
            // Lấy sản phẩm từ repository
            Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + itemDTO.getProductId()));

            // Giảm số lượng stock
            int newStock = product.getStock() - itemDTO.getQuantity();
            if (newStock < 0) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            product.setStock(newStock);

            // Tăng số lượng đã bán
            product.setSoldQuantity(product.getSoldQuantity() + itemDTO.getQuantity());

            // Lưu lại sản phẩm
            productRepository.save(product);

            // Lưu chi tiết đơn hàng
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProducts(product);
            detail.setPrice(itemDTO.getPrice());
            detail.setQuantity(itemDTO.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Xóa CartItem của user (giỏ hàng)
        User user = order.getUser();
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            cartItemRepository.deleteByCart(cart);
        }
    }

    public CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        List<CartItemDTO> itemDTOs = new ArrayList<>();
        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                CartItemDTO itemDTO = new CartItemDTO();
                itemDTO.setId(item.getId());
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());
                itemDTOs.add(itemDTO);
            }
        }
        dto.setCartItems(itemDTOs);
        return dto;
    }

    public void handleUpdateCartBeforeCheckout(List<CartItemDTO> cartItemDTOs) {
    for (CartItemDTO dto : cartItemDTOs) {
        CartItem cartItem = cartItemRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(dto.getQuantity());
        cartItemRepository.save(cartItem);
    }
}
}
