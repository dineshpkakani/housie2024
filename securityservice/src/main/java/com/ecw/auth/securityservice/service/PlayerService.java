package com.ecw.auth.securityservice.service;

import com.ecw.auth.securityservice.entity.Player;
import com.ecw.auth.securityservice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean emailExists(String email) {
        return playerRepository.existsByEmail(email);
    }
    public Player registerPlayer(Player player) {
        return playerRepository.save(player);
    }
}
