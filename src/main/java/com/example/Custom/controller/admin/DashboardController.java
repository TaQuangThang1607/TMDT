package com.example.Custom.controller.admin;


import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Custom.service.OrderService;
import com.example.Custom.service.ProductService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {
    private final OrderService orderService;
    

    
    @GetMapping("edit_home")
    public String edithome(){
        return "admin/Dashboard/edit";

    }
    @GetMapping("/dashboard")
    public String showRevenueStats(Model model,
                                  @RequestParam(value = "startDate", required = false) String startDateStr,
                                  @RequestParam(value = "endDate", required = false) String endDateStr,
                                  @RequestParam(value = "timeUnit", defaultValue = "day") String timeUnit,
                                  @RequestParam(value = "month", required = false) String monthStr) {
        LocalDate startDate = startDateStr != null ? LocalDate.parse(startDateStr) : LocalDate.now().minusMonths(1);
        LocalDate endDate = endDateStr != null ? LocalDate.parse(endDateStr) : LocalDate.now();
        YearMonth month = monthStr != null ? YearMonth.parse(monthStr) : YearMonth.now();

        model.addAttribute("revenueData", orderService.getRevenueByTimeRange(startDate, endDate, timeUnit));
        model.addAttribute("categoryRevenue", orderService.getRevenueByCategory(month));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("month", month);
        model.addAttribute("timeUnit", timeUnit);
        return "admin/Dashboard/index";
    }
    

   
}
