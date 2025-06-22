package com.example.Custom.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Custom.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminreCommend {
    private final ProductService productService;

    /**
     * Displays the recommendation page for products.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render
     */
    @GetMapping("/recommend")
    public String showRecommendationPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/recommend/show";
    }

    @GetMapping("/recommend/toggle/{id}")
    public String toggleRecommendation(@PathVariable("id") Long id) {
        productService.toggleRecommendation(id);
        return "redirect:/admin/recommend";
    }
}
