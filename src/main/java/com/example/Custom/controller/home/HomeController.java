package com.example.Custom.controller.home;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.domain.dto.RegisterDTO;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.service.ProductService;
import com.example.Custom.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
    private ProductService productService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private CartRepository cartRepository;

    public HomeController(ProductService productService, UserService userService, 
    PasswordEncoder passwordEncoder,CartRepository cartRepository) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository= cartRepository;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        List<ProductDTO> dtos = productService.getAllProduct();
        model.addAttribute("product", dtos);
        return "home/index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(dto);

        String hashPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);

        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.saveUser(user);

        return "redirect:/login";

    }


    @PostMapping("/add-product-to-cart/{id}")
    public String addToCart(@PathVariable Long id,HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
    
        // Thêm sản phẩm vào giỏ
        this.productService.handleAddToProduct(email, id,session);
    
        // Cập nhật lại số lượng trong session
        User user = userService.getUserByEmail(email);
        Cart cart = cartRepository.findByUser(user);
        session.setAttribute("sum", cart.getCartItems().size());
    
    return "redirect:/";

    }


    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "home/deny";
    }
}
