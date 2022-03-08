package com.cycleon.game.mancala.service;

import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.model.Game;

import java.util.List;

public interface GameService {
    GameDto createNewGame();

    GameDto joinGame(Integer gameId);

    GameDto getGame(Integer id);

    GameDto move(Integer id, Integer pocketIndex);

    List<GameDto> getAllAvailableGames();
}
