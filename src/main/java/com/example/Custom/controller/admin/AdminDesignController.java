package com.example.Custom.controller.admin;

import com.example.Custom.repository.DesignRequestRepository;
import com.example.Custom.service.DesignRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminDesignController {
    @Autowired
    private DesignRequestRepository repository;

    @Autowired
    private DesignRequestService designRequestService; 

    @GetMapping("/admin/design-requests")
    public String viewDesignRequests(Model model) {
        model.addAttribute("requests", repository.findAll());
        return "admin/designRequests";
    }

    @PostMapping("/admin/design/update-status")
    public String updateDesignStatus(@RequestParam("id") Long id,
                                    @RequestParam("status") String status) {
        designRequestService.updateStatus(id, status);
        return "redirect:/admin/design-requests";
    }
}