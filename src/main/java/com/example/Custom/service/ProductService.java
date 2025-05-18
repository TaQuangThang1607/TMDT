package com.example.Custom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpSession;

@Slf4j
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

    public void handleAddToProduct(String email, long productId) {
        User user = userService.getUserByEmail(email);
        if(user != null){
            Cart cart = cartRepository.findByUser(user);
            if(cart ==null){
                // create new cart
                Cart ortherCart = new Cart();
                ortherCart.setUser(user);
                ortherCart.setSum(0);
                cart = this.cartRepository.save(ortherCart);
            }
            Optional<Product> product = this.productRepository.findById(productId);
            if(product.isPresent()){
                Product currentProduct = product.get();
                
                CartItem orldItem = cartItemRepository.findByCartAndProduct(cart, currentProduct);

                if(orldItem == null){
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProduct(currentProduct);
                    cartItem.setPrice(currentProduct.getPrice());
                    cartItem.setQuantity(1);

                    this.cartItemRepository.save(cartItem);

                    cart.setSum(cart.getSum()+1);
                    cartRepository.save(cart);
                }else{
                    orldItem.setQuantity(orldItem.getQuantity() +1);
                    this.cartItemRepository.save(orldItem);
                }
            }
        }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    @Transactional
    public void handleRemoveCartItem(long cartItemId, HttpSession session) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Cart currentCart = cartItem.getCart();

            
            cartItemRepository.deleteById(cartItemId);
            
            if(currentCart != null) {
                int newSum = currentCart.getSum() - 1;
                currentCart.setSum(newSum);  
                
                if(newSum <= 0) {
                    cartRepository.deleteById(currentCart.getId());
                    session.setAttribute("sum", 0);
                    // session.setAttribute("cartEmpty", true);
                } else {
                    cartRepository.save(currentCart);
                    session.setAttribute("sum", newSum);
                    // session.setAttribute("cartEmpty", true);
                }
            } else {
                session.setAttribute("sum", 0);
                // session.setAttribute("cartEmpty", true);
            }
        }
    }
    
}
