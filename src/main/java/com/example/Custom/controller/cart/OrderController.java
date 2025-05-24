package com.example.Custom.controller.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.CheckoutCartDTO;
import com.example.Custom.domain.dto.OrderDTO;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.UserRepository;
import com.example.Custom.service.CartService;
import com.example.Custom.service.OrderService;
import com.example.Custom.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    private ProductService productService;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository; 
    private CartService cartService;
    private OrderService orderService;
    
    public OrderController(ProductService productService,
            CartItemRepository cartItemRepository, CartService cartService,
             UserRepository userRepository, OrderService orderService) {
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.orderService = orderService;
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
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = new User();
        long id = (long) session.getAttribute("id");
        user.setId(id);

        Cart updatedCart = cartService.fetchByUser(user);
        List<CartItem> cartItems = updatedCart == null ? new ArrayList<>() : updatedCart.getCartItems();

        cartService.handleUpdateCartBeforeCheckout(cartItems);

        CheckoutCartDTO checkoutCartDTO = new CheckoutCartDTO();
        checkoutCartDTO.setCartId(updatedCart.getId());
        for (CartItem cartItem : cartItems) {
            CheckoutCartDTO.CheckoutCartItemDTO itemDTO = new CheckoutCartDTO.CheckoutCartItemDTO();
            itemDTO.setId(cartItem.getId());
            itemDTO.setProductId(cartItem.getProduct().getId());
            itemDTO.setProductName(cartItem.getProduct().getName());
            itemDTO.setPrice(cartItem.getProduct().getPrice());
            itemDTO.setColor(cartItem.getProduct().getColor());
            itemDTO.setSize(cartItem.getProduct().getSize());
            itemDTO.setImageUrl(cartItem.getProduct().getImageUrl());
            itemDTO.setQuantity(cartItem.getQuantity());
            checkoutCartDTO.getCartItems().add(itemDTO);
        }

        session.setAttribute("checkoutCart", checkoutCartDTO);

        return "redirect:/checkout";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        CheckoutCartDTO checkoutCart = (CheckoutCartDTO) session.getAttribute("checkoutCart");

        Cart cart = null;
        List<CartItem> cartItems = new ArrayList<>();
        double total = 0;
        if (checkoutCart == null) {
            User user = new User();
            long id = (long) session.getAttribute("id");
            user.setId(id);
            cart = cartService.fetchByUser(user);
            cartItems = cart == null ? new ArrayList<>() : cart.getCartItems();
            for (CartItem cartItem : cartItems) {
                total += cartItem.getPrice() * cartItem.getQuantity();
            }
        } else {
            for (CheckoutCartDTO.CheckoutCartItemDTO item : checkoutCart.getCartItems()) {
                total += item.getPrice() * item.getQuantity();
            }
            model.addAttribute("cartItems", checkoutCart.getCartItems());
        }

        model.addAttribute("totalPrice", total);
        model.addAttribute("cart", cart != null ? cart : checkoutCart);

        // CHỖ NÀY PHẢI ĐỔ RA ĐÚNG TÊN DTO TRÊN FORM:
        model.addAttribute("orderDTO", new OrderDTO());

        return "home/checkout";
    }


    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("orderDTO") OrderDTO orderDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("id");

        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        CheckoutCartDTO checkoutCart = (CheckoutCartDTO) session.getAttribute("checkoutCart");

        if (checkoutCart == null || checkoutCart.getCartItems().isEmpty()) {
            throw new RuntimeException("Checkout cart is empty");
        }

        Order order = orderService.createOrderFromDTO(orderDTO, checkoutCart, user);

        cartService.handlePlaceOrder(order, checkoutCart.getCartItems());

        // Clean up session
        session.removeAttribute("checkoutCart");
        session.removeAttribute("cartItems");
        session.removeAttribute("cart");
        session.setAttribute("sum", 0);

        return "redirect:/thanks";
    }
    
    @GetMapping("/thanks")
    public String thanks(){
        return "home/thanks";
    }

}
