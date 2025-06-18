package com.example.Custom.controller.home;

import com.example.Custom.model.DesignRequest;
import com.example.Custom.service.DesignRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class DesignController {
    @Autowired
    private DesignRequestService designRequestService;

    @PostMapping("/submitDesign")
    public String submitDesign(
            @RequestParam("size") String size,
            @RequestParam("color") String color,
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            Model model) throws IOException {

        // Đường dẫn thư mục lưu file ngoài project
        String uploadDir = "D:/uploads/design/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        // Xử lý tên file tránh null và ký tự lạ
        String originalFilename = image.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + (originalFilename != null ? originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_") : "image.jpg");
        File dest = new File(uploadDir + fileName);
        image.transferTo(dest);

        // Lưu thông tin vào CSDL
        DesignRequest request = new DesignRequest();
        request.setSize(size);
        request.setColor(color);
        request.setImagePath("/uploads/design/" + fileName);
        request.setDescription(description);
        request.setPrice(500000L); // Giá cố định
        request.setStatus("NEW");
        request.setCreatedAt(LocalDateTime.now());  
        designRequestService.save(request);

        // Chuyển hướng sang trang QR
        return "redirect:/design/qr?id=" + request.getId();
    }

    @GetMapping("/design/qr")
    public String showQrPage(@RequestParam("id") Long id, Model model) {
        DesignRequest request = designRequestService.findById(id)
            .orElseThrow(() -> new RuntimeException("Design request not found"));

        String transferContent = "THIETKE-" + request.getId();
        long amount = request.getPrice();

// Đường dẫn ảnh QR tĩnh
     String qrUrl = "/images/myQR.jpg";

    model.addAttribute("qrUrl", qrUrl);
    model.addAttribute("amount", amount);
    model.addAttribute("transferContent", transferContent);
    model.addAttribute("requestId", request.getId());
    return "home/qrPayment";   
    }
}