package com.example.Custom.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/dashboard")
public class DashboardController {

    @GetMapping
    public String showDashboard() {
        return "admin/Dashboard/index";
    }
}
