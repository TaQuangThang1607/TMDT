package com.example.Custom.controller.authorize;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoinController {
    @GetMapping("/login")
    public String show() {
        return "authLogin/show";
    }
}
