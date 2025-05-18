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

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.User;
import com.example.Custom.service.CartService;
import com.example.Custom.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    private ProductService productService;
    private CartService cartService;
    

    public CartController(ProductService productService,CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }


    @GetMapping("/cart")
    public String show(Model model,HttpServletRequest request){
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id); 
        
        Cart cart = cartService.fetchByUser(user);
        List<CartItem> cartItems = cart==null ? new ArrayList<>() : cart.getCartItems();
        double total =0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getPrice() * cartItem.getQuantity();
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", total);
        model.addAttribute("cart", cart);
        


        return "home/cart";
    }


    @PostMapping("/delete-cart-product/{id}")
    public String removeProductToCartItem(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            cartService.handleRemoveCartItem(id, session);
            return "redirect:/cart";
        } catch (Exception e) {
            // Log lỗi nếu cần
            System.err.println("Error removing cart item: " + e.getMessage());
            return "redirect:/cart?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }
}
