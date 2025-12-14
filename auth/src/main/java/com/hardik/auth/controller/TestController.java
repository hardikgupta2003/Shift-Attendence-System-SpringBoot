package com.hardik.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public/hello")
    public String publicApi() {
        return "Public API working";
    }

    @GetMapping("/secure/hello")
    public String secureApi() {
        return "Secure API working";
    }
}