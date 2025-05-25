package com.arzan.MafiaGame.controller;


import com.arzan.MafiaGame.model.Player;
import com.arzan.MafiaGame.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerRepo playerRepo;

    @GetMapping("/all")
    public List<Player> allPlayers(){
        return playerRepo.findAll();
    }


    @GetMapping("/username/{username}")
    ResponseEntity<?> getPlayerByUsername(@PathVariable String username){
        Player p = playerRepo.findByUsername(username);
        if (p != null) return ResponseEntity.ok(p);
        return ResponseEntity.status(404).body("No Player found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> geyPlayerById(@PathVariable Long id){
        if(playerRepo.findById(id).isPresent()){
            return ResponseEntity.ok(playerRepo.findById(id).get());
        }
        return ResponseEntity.status(404).body("Player not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if(playerRepo.existsById(id)) {
            playerRepo.deleteById(id);
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.status(404).body("No Player found");
    }
}
