package com.arzan.MafiaGame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinLobbyResponse {
    private String gameCode;
    private String username;
    private Long id;
}
