package com.example.Custom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.repository.CategoryRepository;
import com.example.Custom.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "admin/product/show";
    }

    @GetMapping("/create")
    public String showCreateProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product/createProduct";
    }

    @PostMapping
    public String CreateProduct(@ModelAttribute("product") ProductDTO dto) {
        this.productService.handleCreateProduct(dto);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProduct(Model model, @PathVariable Long id) {
        ProductDTO dto = productService.getProductById(id);
        model.addAttribute("product", dto);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product/updateProduct";
    }

    @PostMapping("/update/{id}")
    public String updateProductById(@ModelAttribute("product") ProductDTO dto,
            @PathVariable Long id) {
        productService.handleUpdateProduct(dto, id);

        return "redirect:/admin/products";
    }

    @GetMapping("/remove/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.handleRemoveProduct(id);
        return "redirect:/admin/products";
    }
}
