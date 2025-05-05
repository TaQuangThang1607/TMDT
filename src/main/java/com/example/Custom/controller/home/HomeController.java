package com.example.Custom.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Custom.domain.dto.ProductDTO;
import com.example.Custom.service.ProductService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String showHome(Model model) {
        List<ProductDTO> dtos = productService.getAllProduct();
        model.addAttribute("product", dtos);
        return "home/index";
    }
}
