package com.example.Custom.controller.cart;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.User;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.service.CartService;
import com.example.Custom.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    private ProductService productService;
    private CartItemRepository cartItemRepository;
    private CartService cartService;
    
    public OrderController(ProductService productService,
            CartItemRepository cartItemRepository, CartService cartService) {
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
    }

    


    @GetMapping("/order")
    public String show(Model model, HttpServletRequest request){
        User user = new User();
        HttpSession session = request.getSession();
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
        model.addAttribute("cart",cart);

        return "home/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart) {
        List<CartItem> cartItems = cart == null ? new ArrayList<CartItem>() : cart.getCartItems();
        this.cartService.handleUpdateCartBeforeCheckout(cartItems);
        return "redirect:/checkout";
    }
    @GetMapping("/checkout")
    public String showCheckouPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = new User();
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
        model.addAttribute("cart",cart);

        return "home/checkout";
    }
}
