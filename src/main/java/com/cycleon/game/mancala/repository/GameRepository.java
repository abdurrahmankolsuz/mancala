package com.cycleon.game.mancala.repository;

import com.cycleon.game.mancala.model.Game;
import com.cycleon.game.mancala.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findGamesByGameStatusNot(GameStatus gameStatus);
}
