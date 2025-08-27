package com.ecw.auth.securityservice.service;

import com.ecw.auth.securityservice.config.JwtUtil;
import com.ecw.auth.securityservice.entity.Player;
import com.ecw.auth.securityservice.repository.PlayerRepository;
import com.ecw.auth.securityservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public String login(String email, String rawPassword) {
        Optional<Player> player= Optional.of(playerRepository.findByEmail(email)
                .filter(p -> passwordEncoder.matches(rawPassword, p.getPassword())).get());
        if(player.isPresent()){
            Player plyr=player.get();

            if(Constants.ADMIN.equals(plyr.getRole())) {
                return jwtUtil.generateToken(plyr.getEmail(), "ADMIN");
                //return restTemplate.getForObject("http://GATEWAY-SERVICE/player/" + id, String.class);
            }else{
                return  jwtUtil.generateToken(plyr.getEmail(), "PLAYER");
            }

        }else{
            return "Invalid Credentials";
        }
    }
}
