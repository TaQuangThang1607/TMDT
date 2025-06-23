package com.example.Custom.controller.cart;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.CartDTO;
import com.example.Custom.domain.dto.CartItemDTO;
import com.example.Custom.domain.dto.CheckoutCartDTO;
import com.example.Custom.domain.dto.OrderDTO;
import com.example.Custom.domain.dto.OrderDetailDTO;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.OrderRepository;
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
    private OrderRepository orderRepository;
    
    public OrderController(ProductService productService,
            CartItemRepository cartItemRepository, CartService cartService,
             UserRepository userRepository, OrderService orderService,
             OrderRepository orderRepository) {
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.orderRepository=orderRepository;
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
    public String getCheckoutPage(@ModelAttribute("cartDTO") CartDTO cartDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        Long id = (Long) session.getAttribute("id");
        User user = new User();
        user.setId(id);

        // Lấy giỏ hàng thực tế từ database để đảm bảo dữ liệu chính xác
        Cart cart = cartService.fetchByUser(user);
        if (cart == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart?error=" + URLEncoder.encode("Giỏ hàng trống", StandardCharsets.UTF_8);
        }

        // Cập nhật số lượng từ cartDTO
        cartService.handleUpdateCartBeforeCheckout(cartDTO.getCartItems());

        // Tạo CheckoutCartDTO
        CheckoutCartDTO checkoutCartDTO = new CheckoutCartDTO();
        checkoutCartDTO.setCartId(cart.getId());
        for (CartItemDTO itemDTO : cartDTO.getCartItems()) {
            CheckoutCartDTO.CheckoutCartItemDTO checkoutItem = new CheckoutCartDTO.CheckoutCartItemDTO();
            checkoutItem.setId(itemDTO.getId());
            checkoutItem.setProductId(itemDTO.getProductId());
            checkoutItem.setProductName(itemDTO.getProductName());
            checkoutItem.setPrice(itemDTO.getPrice());
            checkoutItem.setColor(itemDTO.getColor());
            checkoutItem.setSize(itemDTO.getSize());
            checkoutItem.setImageUrl(itemDTO.getImageUrl());
            checkoutItem.setQuantity(itemDTO.getQuantity());
            checkoutCartDTO.getCartItems().add(checkoutItem);
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

        return "redirect:/order/qr?orderId=" + order.getId();
    }
    
@GetMapping("/order/qr")
public String showOrderQrPayment(@RequestParam("orderId") Long orderId, Model model) {
    Order order = orderRepository.findById(orderId).orElse(null);
    if (order == null) {
        return "redirect:/cart";
    }
    String transferContent = "ORDER-" + order.getId();
    long amount = (long) order.getTotalPrice(); // hoặc ép kiểu phù hợp

    String qrUrl = "/images/myQR.jpg";
    model.addAttribute("qrUrl", qrUrl);
    model.addAttribute("amount", amount);
    model.addAttribute("transferContent", transferContent);
    model.addAttribute("orderId", order.getId());
    return "home/qrPayment";
}


    @GetMapping("/api/order/{id}")
    @ResponseBody
    public OrderDetailDTO getOrderDetail(@PathVariable Long id) {

        

        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        
        List<OrderDetailDTO.Item> items = order.getDetails().stream().map(detail -> {
            OrderDetailDTO.Item item = new OrderDetailDTO.Item();
            item.setProductName(detail.getProducts().getName());
            item.setQuantity(detail.getQuantity());
            item.setPrice(detail.getPrice());
            return item;
        }).toList();
        
        dto.setDetails(items);
        return dto;
    }

}
