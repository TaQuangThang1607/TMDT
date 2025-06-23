package com.example.Custom.controller.cart;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.CartDTO;
import com.example.Custom.domain.dto.CartItemDTO;
import com.example.Custom.service.CartService;
import com.example.Custom.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    private ProductService productService;
    private CartService cartService;

    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String show(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        long id = (long) session.getAttribute("id");
        User user = new User();
        user.setId(id);

        // Lấy giỏ hàng từ service
        Cart cart = cartService.fetchByUser(user);

        // Chuyển đổi sang CartDTO
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        double totalPrice = 0;

        if (cart != null) {
            cartDTO.setId(cart.getId());
            cartDTO.setUserId(id);
            cartItemDTOs = cart.getCartItems().stream().map(item -> {
                CartItemDTO itemDTO = new CartItemDTO();
                itemDTO.setId(item.getId());
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getProduct().getPrice());
                itemDTO.setColor(item.getProduct().getColor());
                itemDTO.setSize(item.getProduct().getSize());
                itemDTO.setImageUrl(item.getProduct().getImageUrl());
                return itemDTO;
            }).toList();
            totalPrice = cartItemDTOs.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        }

        cartDTO.setCartItems(cartItemDTOs);
        model.addAttribute("cartDTO", cartDTO);
        model.addAttribute("totalPrice", totalPrice);

        // Thêm thông báo nếu giỏ hàng trống
        if (cartItemDTOs.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng của bạn đang trống!");
        }

        return "home/cart";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String removeProductToCartItem(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        try {
            cartService.handleRemoveCartItem(id, session);
            return "redirect:/cart";
        } catch (Exception e) {
            System.err.println("Error removing cart item: " + e.getMessage());
            return "redirect:/cart?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/update-cart-quantity/{id}")
    public String updateCartQuantity(@PathVariable Long id, @RequestParam("quantity") int quantity, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        try {
            cartService.updateCartItemQuantity(id, quantity);
            return "redirect:/cart";
        } catch (Exception e) {
            System.err.println("Error updating cart quantity: " + e.getMessage());
            return "redirect:/cart?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }
}