package com.example.Custom.controller.admin;

import com.example.Custom.domain.Order;
import com.example.Custom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/order")
    public String orderList(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orderList";
    }

    @PostMapping("/admin/order/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                   @RequestParam("status") String status) {
Order order = orderService.findById(orderId);
order.setStatus(status);
orderService.save(order);
        return "redirect:/admin/order";
    }
}