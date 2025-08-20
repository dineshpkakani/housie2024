package com.ecw.auth.securityservice.controller;

import com.ecw.auth.securityservice.dao.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class registerController {

    @PostMapping("/auth/register")
    public ResponseEntity<String> doRegister(@RequestBody Player player) {
        System.out.println(player.firstName() + " " + player.lastName() + " " + player.email() + " " + player.phone() + " " + player.password());
        return  null;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> doLogin() {
        System.out.println("Login successfully thanks ganesh dada");
        return  null;
    }
    @PostMapping("/login")
    public ResponseEntity<String> doLoginWithoutAuth() {
        System.out.println("Login called thanks ganesh dada");
        return  null;
    }

}
