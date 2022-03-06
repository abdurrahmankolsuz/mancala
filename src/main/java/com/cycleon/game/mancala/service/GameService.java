package com.cycleon.game.mancala.service;

import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.model.Game;

public interface GameService {
    GameDto createNewGame(Integer numberOfStones);

    GameDto getGameInstance(Integer id);

    Game move(Integer id, Integer pocketIndex);

    GameDto gameToDTO(Game game);
}
