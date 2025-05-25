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
        messagingTemplate.convertAndSend("/topic/general/"+message.getGameCode(),message);
    }

    @MessageMapping("/chat/mafia")
//    @SendTo("/topic/mafia")
    public void sendMafia(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/mafia/"+message.getGameCode(),message);
    }

    @MessageMapping("/chat/police")
    public void sendPolice(ChatMessage message){
        messagingTemplate.convertAndSend("/topic/police/"+message.getGameCode(),message);
    }

    @MessageMapping("/chat/doctor")
    public void sendDoctor(ChatMessage message){
        messagingTemplate.convertAndSend("/topic/doctor/"+message.getGameCode(),message);
    }
}
