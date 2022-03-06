package com.cycleon.game.mancala.repository;

import com.cycleon.game.mancala.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
