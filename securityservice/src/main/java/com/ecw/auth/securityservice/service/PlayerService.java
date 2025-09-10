package com.ecw.auth.securityservice.service;

import com.ecw.auth.securityservice.config.JwtUtil;
import com.ecw.auth.securityservice.entity.Player;
import com.ecw.auth.securityservice.repository.PlayerRepository;
import com.ecw.auth.securityservice.util.Constants;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public PlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public boolean emailExists(String email) {
        return playerRepository.existsByEmail(email);
    }
    public Player registerPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Map<String,String> login(String email, String rawPassword) {
        Optional<Player> player = Optional.of(playerRepository.findByEmail(email)
                .filter(p -> passwordEncoder.matches(rawPassword, p.getPassword())).get());
        Map map = new HashMap<String, String>();
        if (player.isPresent()) {

            Player plyr = player.get();

            if (Constants.ADMIN.equals(plyr.getRole())) {
                map.put("tkn", jwtUtil.generateToken(plyr.getEmail(), "ADMIN"));
                map.put("rid", "1");
                map.put("landingpage", "/home");
                //return restTemplate.getForObject("http://GATEWAY-SERVICE/player/" + id, String.class);
            } else {
                map.put("tkn", jwtUtil.generateToken(plyr.getEmail(), "PLAYER"));
                map.put("rid", "2");
                map.put("landingpage", "/home");
            }

        } else {
            map.put("tkn", "Invalid Credentials");
        }
        return map;
    }

}
