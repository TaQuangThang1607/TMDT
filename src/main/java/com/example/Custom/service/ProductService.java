package com.example.Custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



    @Transactional
    public void handleAddToProduct(String email, Long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (product.getStock() <= 0) {
            throw new RuntimeException("Product is out of stock: " + product.getName());
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>()); // Khởi tạo danh sách rỗng
            cart = cartRepository.save(cart);
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + 1;
            if (newQuantity > product.getStock()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setPrice(product.getPrice());
            cart.getCartItems().add(cartItem); // Thêm vào danh sách cartItems
            cartItemRepository.save(cartItem);
        }

        // Cập nhật số lượng sản phẩm trong session
        int cartItemCount = cartItemRepository.countByCart(cart);
        session.setAttribute("sum", cartItemCount);
    }


        public Page<ProductDTO> getProductsWithFilters(String name, Double minPrice, Double maxPrice, Long categoryId,
                                                   String size, String color, String material, Integer minStock,
                                                   Integer minSold, Pageable pageable) {
        Category category = categoryId != null ? categoryRepository.findById(categoryId).orElse(null) : null;
        Page<Product> products = productRepository.findByFilters(name, minPrice, maxPrice, category, size, color,
                material, minStock, minSold, pageable);
        return products.map(this::mapToDto);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<String> getAllSizes() {
        return productRepository.findDistinctSizes();
    }

    public List<String> getAllColors() {
        return productRepository.findDistinctColors();
    }

    public List<String> getAllMaterials() {
        return productRepository.findDistinctMaterials();
    }

    public List<Product> getRecommendedProducts() {
        return productRepository.findByRecommendedTrue();
    }

    // lấy tất cả sản phẩm 
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product toggleRecommendation(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setRecommended(!product.isRecommended());
        return productRepository.save(product);
    }
}
