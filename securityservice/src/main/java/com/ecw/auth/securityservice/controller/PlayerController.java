package com.ecw.auth.securityservice.controller;


import com.ecw.auth.securityservice.config.JwtUtil;
import com.ecw.auth.securityservice.dto.LoginRequest;
import com.ecw.auth.securityservice.entity.Player;
import com.ecw.auth.securityservice.service.OtpService;
import com.ecw.auth.securityservice.service.PlayerService;
import com.ecw.auth.securityservice.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;

    public PlayerController(PlayerService playerService, PasswordEncoder passwordEncoder, OtpService otpService, JwtUtil jwtUtil) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
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
        ResponseEntity<Player>  responseEntity = ResponseEntity.ok(playerService.registerPlayer(player));
        otpService.generateAndSendOtp(player.getEmail());
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(playerService.login(request.getUsername(), request.getPassword()));

        }

}