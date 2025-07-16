package com.ecw.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String getHome(){
        return "get home logged in successfully";
    }
    @PostMapping("/home")
    public String postHome(){
        return "Post home logged in successfully";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "adminHome";
    }
    @GetMapping("/user/home")
    public String userHome() {
        return "userHome";
    }

}


