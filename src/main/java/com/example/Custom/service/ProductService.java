package com.example.Custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Category;
import com.example.Custom.domain.Product;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.repository.CartItemRepository;
import com.example.Custom.repository.CartRepository;
import com.example.Custom.repository.CategoryRepository;
import com.example.Custom.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;
    private UserService userService;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
            UserService userService, CartRepository cartRepository,CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.cartRepository=cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // load all product
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product_id not found"));
        return mapToDto(product);
    }

    public ProductDTO handleCreateProduct(ProductDTO dto) {
        Product product = mapToEntity(dto);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        Product pd = productRepository.save(product);
        return mapToDto(pd);
    }

    public ProductDTO handleUpdateProduct(ProductDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setMaterial(dto.getMaterial());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));
        product.setCategory(category);

        Product update = this.productRepository.save(product);
        return mapToDto(update);
    }

    public void handleRemoveProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        productRepository.delete(product);
    }

    // map dto to product
    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setMaterial(dto.getMaterial());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    // map Product to ProductDTO
    public ProductDTO mapToDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());
        dto.setMaterial(product.getMaterial());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }



    public void handleAddToProduct(String email, Long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // Tìm hoặc tạo mới Cart
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }

        // Tìm sản phẩm
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        Product product = productOptional.get();

        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItem == null) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(1);

            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
        }

        cartRepository.save(cart);

        if (session != null) {
            session.setAttribute("sum", cart.getCartItems().size());
        }
    }
    

    
}
