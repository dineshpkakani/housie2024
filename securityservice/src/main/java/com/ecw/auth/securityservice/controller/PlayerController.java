package com.ecw.auth.securityservice.controller;


import com.ecw.auth.securityservice.entity.Player;
import com.ecw.auth.securityservice.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    public PlayerController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ API to check email existence
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean exists = playerService.emailExists(email);
        if (exists) {
            return ResponseEntity.ok("Email already exists");
        } else {
            return ResponseEntity.ok("Email available");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Player> doRegister1(@RequestBody Player player) {
        if (playerService.emailExists(player.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        return ResponseEntity.ok(playerService.registerPlayer(player));
    }
}