package com.arzan.MafiaGame.controller;

import com.arzan.MafiaGame.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final List<ChatMessage> generalMsg = new ArrayList<>();
    private final List<ChatMessage> mafiaMsg = new ArrayList<>();
    private final List<ChatMessage> doctorMsg = new ArrayList<>();
    private final List<ChatMessage> policeMsg = new ArrayList<>();

    @MessageMapping("/chat/general")
//    @SendTo("/topic/general")
    public void sendGeneral(ChatMessage message){
        generalMsg.add(message);
        messagingTemplate.convertAndSend("/topic/general/"+message.getGameCode(),generalMsg);
    }

    @MessageMapping("/chat/mafia")
//    @SendTo("/topic/mafia")
    public void sendMafia(ChatMessage message) {
        mafiaMsg.add(message);
        messagingTemplate.convertAndSend("/topic/mafia/"+message.getGameCode(),mafiaMsg);
    }

    @MessageMapping("/chat/police")
    public void sendPolice(ChatMessage message){
        policeMsg.add(message);
        messagingTemplate.convertAndSend("/topic/police/"+message.getGameCode(),policeMsg);
    }

    @MessageMapping("/chat/doctor")
    public void sendDoctor(ChatMessage message){
        doctorMsg.add(message);
        messagingTemplate.convertAndSend("/topic/doctor/"+message.getGameCode(),doctorMsg);
    }
}
