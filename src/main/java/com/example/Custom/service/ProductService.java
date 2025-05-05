package com.example.Custom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Custom.domain.Category;
import com.example.Custom.domain.Product;
import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.repository.CategoryRepository;
import com.example.Custom.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
}
