package com.arzan.MafiaGame.controller;

import com.arzan.MafiaGame.model.ChatMessage;
import com.arzan.MafiaGame.model.Game;
import com.arzan.MafiaGame.repo.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GameRepo gameRepo;

    @MessageMapping("/chat/general")
//    @SendTo("/topic/general")
    public void sendGeneral(ChatMessage message){
        Game game = gameRepo.findByGameCode(message.getGameCode());
        game.getGeneralMsg().add(message);
        gameRepo.save(game);
        messagingTemplate.convertAndSend("/topic/general/"+message.getGameCode(),game.getGeneralMsg());
    }

    @MessageMapping("/chat/mafia")
//    @SendTo("/topic/mafia")
    public void sendMafia(ChatMessage message) {
        Game game = gameRepo.findByGameCode(message.getGameCode());
        game.getMafiaMsg().add(message);
        gameRepo.save(game);
        messagingTemplate.convertAndSend("/topic/mafia/"+message.getGameCode(),game.getMafiaMsg());
    }

    @MessageMapping("/chat/police")
    public void sendPolice(ChatMessage message){
        Game game = gameRepo.findByGameCode(message.getGameCode());
        game.getPoliceMsg().add(message);
        gameRepo.save(game);

        messagingTemplate.convertAndSend("/topic/police/"+message.getGameCode(),game.getPoliceMsg());
    }

    @MessageMapping("/chat/doctor")
    public void sendDoctor(ChatMessage message){
        Game game = gameRepo.findByGameCode(message.getGameCode());
        game.getDoctorMsg().add(message);
        gameRepo.save(game);
        messagingTemplate.convertAndSend("/topic/doctor/"+message.getGameCode(),game.getDoctorMsg());
    }
}
