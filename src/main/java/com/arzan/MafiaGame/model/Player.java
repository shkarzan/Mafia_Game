package com.arzan.MafiaGame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "game_id",referencedColumnName = "id")
    private Game game;

    private String role;
    private boolean alive = true;

}
