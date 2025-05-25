package com.arzan.MafiaGame.controller;


import com.arzan.MafiaGame.model.*;
import com.arzan.MafiaGame.repo.GameRepo;
import com.arzan.MafiaGame.repo.PlayerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @PostMapping("/create")
    public ResponseEntity<String> createGame(@RequestParam String username){
        Game game = new Game();
        game.setGameCode(UUID.randomUUID().toString().substring(0,6));
        game.setHost(username);
        gameRepo.save(game);

        return ResponseEntity.ok(game.getGameCode());
    }

    private boolean checkIfUsernameExist(String username,Game game){
        for(Player p : game.getPlayers()){
            if(p.getUsername().equals(username)) return true;
        }
        return false;
    }

    @PostMapping("/{gameCode}/start")
    public ResponseEntity<?> startGame(@PathVariable String gameCode,@RequestParam int mafiaCount){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
            List<Player> players = game.getPlayers();
            if(players.size() < 8) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough players to start the game");
            List<String> roles = new ArrayList<>();
            roles.add("Police");
            roles.add("Doctor");
            for (int i = 0; i < mafiaCount; i++) {
                roles.add("Mafia");
            }
            while (roles.size() < players.size()) {
                roles.add("Citizen");
            }
            Collections.shuffle(roles,new Random());
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setRole(roles.get(i));
                playerRepo.save(players.get(i));
            }
            game.setGameState("NIGHT");
            game.setActive(true);
            gameRepo.save(game);
            return ResponseEntity.ok(game.getGameCode());
        }
        return ResponseEntity.status(404).body("No Game found");
    }

    @PostMapping("/{gameCode}/nextPhase")
    public ResponseEntity<?> nextPhase(@PathVariable String gameCode){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null && game.isActive()){
            switch (game.getGameState()){
                case "NIGHT" -> game.setGameState("DAY");
                case "DAY" -> game.setGameState("NIGHT");
            }
            gameRepo.save(game);
            return ResponseEntity.ok(game.getGameState());
        }
        return ResponseEntity.status(404).body("No Game Found");
    }

    @PostMapping("/{gameCode}/{playerId}")
    public ResponseEntity<?> actionOnPlayer(@PathVariable String gameCode,@PathVariable Long playerId,@RequestParam String action){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
            List<Player> players = game.getPlayers().stream().peek(val->{
                if(val.getId().equals(playerId)){
                    val.setAlive(!action.equals("KILL"));
                }
            }).toList();
            playerRepo.saveAll(players);
            return ResponseEntity.ok(players);
        }
        return ResponseEntity.status(404).body("No Game Found");
    }

    @PostMapping("/{gameCode}/join")
    public ResponseEntity<?> joinGame(@PathVariable String gameCode, @RequestParam String username){
        Game game = gameRepo.findByGameCode(gameCode);
        Player optional = playerRepo.findByUsername(username);
        if(game != null){
            if(checkIfUsernameExist(username,game)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exist");
            Player player = Objects.requireNonNullElseGet(optional, Player::new);
            player.setUsername(username);
            player.setGame(game);
            if(game.isActive()){
                player.setRole("Citizen");
            }
            playerRepo.save(player);
            JoinLobbyResponse response = new JoinLobbyResponse();
            response.setGameCode(game.getGameCode());
            response.setUsername(player.getUsername());
            response.setId(player.getId());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body("Game with code:"+gameCode+" not found");
    }

    @PostMapping("/{gameCode}/setClue")
    public ResponseEntity<?> setClue(@PathVariable String gameCode,@RequestParam String clue){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
            game.setClue(clue);
            gameRepo.save(game);
            return ResponseEntity.ok(game.getClue());
        }
        return ResponseEntity.status(404).body("No Game Found");
    }

    @DeleteMapping("/{gameCode}/{playerId}")
    public String removePlayerFromRoom(@PathVariable String gameCode,@PathVariable Long playerId){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
            Player player = playerRepo.findById(playerId).orElseThrow(()-> new RuntimeException("No Player found"));
            player.setGame(null);
            playerRepo.save(player);
            return "Exited";
        }
        return "No Game Found";
    }

    @Transactional
    @DeleteMapping("/{gameCode}")
    public ResponseEntity<String> deleteGame(@PathVariable String gameCode){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){

            for(Player player : game.getPlayers()){
                player.setGame(null);
                player.setAlive(true);
                player.setRole(null);
            }
            playerRepo.saveAll(game.getPlayers());
            gameRepo.delete(game);
            return ResponseEntity.ok("Game deleted");
        }
        return ResponseEntity.status(404).body("No Game Found");
    }

    @GetMapping("/{gameCode}/players")
    public ResponseEntity<?> getPlayers(@PathVariable String gameCode){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
//            List<Player> players = game.getPlayers();
//            return ResponseEntity.ok(players.stream().map(PlayersDTO::new).toList());
            return ResponseEntity.ok(game.getPlayers());
        }
        return ResponseEntity.status(404).body("No Game found");
    }

    @GetMapping("/{gameCode}")
    public ResponseEntity<?> getGame(@PathVariable String gameCode){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null) return ResponseEntity.ok(game);
        return ResponseEntity.status(404).body("No Game Found");
    }

    @GetMapping("/{gameCode}/getPlayerRole/{playerId}")
    public ResponseEntity<?> getPlayerRole(@PathVariable String gameCode,@PathVariable Long playerId){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null) {
            var opt = playerRepo.findById(playerId);
            if(opt.isPresent()){
                return ResponseEntity.ok(new InGameResponse(opt.get()));
            }
            return ResponseEntity.status(404).body("No Player found");
        }
        return ResponseEntity.status(404).body("No Game Found");
    }

    @GetMapping("/{gameCode}/getHost")
    public ResponseEntity<String> getHost(@PathVariable String gameCode){
        Game game = gameRepo.findByGameCode(gameCode);
        if(game != null){
            return ResponseEntity.ok(game.getHost());
        }
        return ResponseEntity.status(404).body("No Game found");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gameRepo.findAll());
    }
}
