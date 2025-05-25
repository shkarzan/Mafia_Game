package com.arzan.MafiaGame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayersDTO {
    private Long id;
    private String username;
    private boolean alive;

    public PlayersDTO(Player player){
        this.id = player.getId();
        this.username = player.getUsername();
        this.alive = player.isAlive();
    }
}
