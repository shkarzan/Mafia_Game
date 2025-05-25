package com.arzan.MafiaGame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InGameResponse {
    private String gameCode;
    private Long id;
    private String role;
    private String username;
    public InGameResponse(Player p){
        this.gameCode = p.getGame().getGameCode();
        this.id = p.getId();
        this.role = p.getRole();
        this.username = p.getUsername();
    }
}
