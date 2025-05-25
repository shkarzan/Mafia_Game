package com.arzan.MafiaGame.repo;

import com.arzan.MafiaGame.model.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepo extends JpaRepository<Player,Long> {

    Player findByUsername(String username);

    @Modifying
    @Query("UPDATE Player p SET p.game = null WHERE p.game.id = :gameId")
    int detachPlayersFromGame(@Param("gameId") Long gameId);

}
