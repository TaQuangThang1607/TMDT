package com.example.Custom.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.OrderDetail;
import com.example.Custom.domain.Product;
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
    public List<Order> findAll() {
    return orderRepository.findAll();
    }
    public Order findById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    // Thống kê doanh thu theo thời gian
    public Map<String, Double> getRevenueByTimeRange(LocalDate startDate, LocalDate endDate, String timeUnit) {
        List<Order> orders = orderRepository.findByCreatedDateBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        Map<String, Double> revenueMap = new HashMap<>();

        for (Order order : orders) {
            LocalDate orderDate = order.getCreatedDate().toLocalDate();
            String key;
            if ("day".equalsIgnoreCase(timeUnit)) {
                key = orderDate.toString();
            } else if ("month".equalsIgnoreCase(timeUnit)) {
                key = YearMonth.from(orderDate).toString();
            } else if ("year".equalsIgnoreCase(timeUnit)) {
                key = String.valueOf(orderDate.getYear());
            } else {
                key = orderDate.toString(); // Default to day
            }
            revenueMap.merge(key, order.getTotalPrice(), Double::sum);
        }

        return revenueMap;
    }

    // Thống kê doanh thu theo danh mục trong một tháng
    public Map<String, Double> getRevenueByCategory(LocalDate month) {
        YearMonth yearMonth = YearMonth.from(month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<Order> orders = orderRepository.findByCreatedDateBetween(startOfMonth, endOfMonth);
        Map<String, Double> revenueByCategory = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail detail : order.getDetails()) {
                Product product = detail.getProducts();
                String categoryName = product.getCategory().getName();
                double revenue = detail.getQuantity() * product.getPrice();
                revenueByCategory.merge(categoryName, revenue, Double::sum);
            }
        }

        return revenueByCategory;
    }

    public Map<String, Double> getRevenueByCategory(YearMonth month) {
        LocalDateTime startOfMonth = month.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = month.atEndOfMonth().atTime(23, 59, 59);

        List<Order> orders = orderRepository.findByCreatedDateBetween(startOfMonth, endOfMonth);
        Map<String, Double> revenueByCategory = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail detail : order.getDetails()) {
                Product product = detail.getProducts();
                if (product != null && product.getCategory() != null) {
                    String categoryName = product.getCategory().getName();
                    double revenue = detail.getQuantity() * product.getPrice();
                    revenueByCategory.merge(categoryName, revenue, Double::sum);
                }
            }
        }

        return revenueByCategory;
    }
}