package com.cycleon.game.mancala.repository;


import com.cycleon.game.mancala.model.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<Pocket, Integer> {
}
