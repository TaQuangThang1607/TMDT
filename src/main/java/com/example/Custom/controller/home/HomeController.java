package com.example.Custom.controller.home;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.Comment;
import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.domain.dto.ProductFilterParams;
import com.example.Custom.domain.dto.RegisterDTO;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.service.CommentService;
import com.example.Custom.service.OrderService;
import com.example.Custom.service.ProductService;
import com.example.Custom.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final OrderService orderService; 
    private final CommentService commentService;


    

    @GetMapping("/")
    public String showHome(Model model) {
        List<ProductDTO> dtos = productService.getAllProduct();
        model.addAttribute("product", dtos);
        model.addAttribute("recommendedProducts", productService.getRecommendedProducts());

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
    public String addToCart(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        try {
            // Thêm sản phẩm vào giỏ
            this.productService.handleAddToProduct(email, id, session);
            return "redirect:/";
        } catch (RuntimeException e) {
            // Truyền thông báo lỗi qua redirect
            return "redirect:/?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }


    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "home/deny";
    }

    @GetMapping("/history-order")
    public String showHistoryPage(Model model, HttpServletRequest request){
        User user = new User();
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("id");
        user.setId(id); 

        if(id == null){
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrderByUser(user);
        model.addAttribute("orderHitory", orders);
        
        return "home/history";
    }
    @GetMapping("/design")
    public String designPage() {
        return "home/design";
    }

    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Long id, Model model, HttpServletRequest request) {
        

        ProductDTO product = productService.getProductById(id);
        List<Comment> comments = commentService.getCommentsByProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "home/product-detail";
    }

    @PostMapping("/product/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute("newComment") Comment newComment,
                             @RequestParam(value = "parentCommentId", required = false) Long parentCommentId,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("id");
        commentService.addComment(id, userId, newComment.getContent(), parentCommentId);
        return "redirect:/product/" + id;
    }

    //danh sach san pham
    @GetMapping("/products")
    public String showProducts(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "minPrice", required = false) Double minPrice,
                              @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                              @RequestParam(value = "categoryId", required = false) Long categoryId,
                              @RequestParam(value = "sizeFilter", required = false) String sizeFilter,
                              @RequestParam(value = "color", required = false) String color,
                              @RequestParam(value = "material", required = false) String material,
                              @RequestParam(value = "minStock", required = false) Integer minStock,
                              @RequestParam(value = "minSold", required = false) Integer minSold) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getProductsWithFilters(name, minPrice, maxPrice, categoryId,
                sizeFilter, color, material, minStock, minSold, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("sizes", productService.getAllSizes());
        model.addAttribute("colors", productService.getAllColors());
        model.addAttribute("materials", productService.getAllMaterials());
        model.addAttribute("filterParams", new ProductFilterParams(name, minPrice, maxPrice, categoryId, sizeFilter,
                color, material, minStock, minSold));

        return "home/products";
    }

   
}
