package com.example.Custom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.CheckoutCartDTO;
import com.example.Custom.domain.dto.OrderDTO;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.repository.OrderRepository;
import com.example.Custom.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Order createOrderFromDTO(OrderDTO orderDTO, CheckoutCartDTO checkoutCart, User user) {
        Cart cart = cartRepository.findById(checkoutCart.getCartId())
                        .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverAddress(orderDTO.getReceiverAddress());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order.setReceiverNote(orderDTO.getReceiverNote());
        order.setCreatedDate(LocalDateTime.now());
        order.setUser(user);
        // order.setCart(cart);
        order.setStatus("PENDING");

        double totalPrice = 0;
        for (CheckoutCartDTO.CheckoutCartItemDTO itemDTO : checkoutCart.getCartItems()) {
            totalPrice += itemDTO.getPrice() * itemDTO.getQuantity();
        }
        order.setTotalPrice(totalPrice);

        // Save the order (and optionally create order details)
        return orderRepository.save(order);
    }

    public List<Order> getOrderByUser(User user) {
        return orderRepository.findByUser(user);
    }
}