package com.arzan.MafiaGame.repo;

import com.arzan.MafiaGame.model.Game;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game,Long> {
    Game findByGameCode(String gameCode);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM game where game_code=:gameCode",nativeQuery = true)
    int deleteByGameCode(String gameCode);
}
