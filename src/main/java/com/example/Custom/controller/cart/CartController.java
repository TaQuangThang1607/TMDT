package com.example.Custom.controller.cart;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.User;
import com.example.Custom.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    private ProductService productService;

    

    public CartController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/cart")
    public String show(Model model,HttpServletRequest request){
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id); 
        
        Cart cart = productService.fetchByUser(user);
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
        try {
            HttpSession session = request.getSession(false);
            long cartDetailId = id;
            this.productService.handleRemoveCartItem(cartDetailId, session);
        } catch (Exception e) {
            return "redirect:/cart?error=remove_failed";
        }
        return "redirect:/cart";
    }
}
