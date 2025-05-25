package com.arzan.MafiaGame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String gameCode;
    private String host;

    @OneToMany(mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    private boolean active;

    private String gameState;

    private String clue;

    private List<ChatMessage> generalMsg = new ArrayList<>();
    private List<ChatMessage> mafiaMsg = new ArrayList<>();
    private List<ChatMessage> doctorMsg = new ArrayList<>();
    private List<ChatMessage> policeMsg = new ArrayList<>();
}
