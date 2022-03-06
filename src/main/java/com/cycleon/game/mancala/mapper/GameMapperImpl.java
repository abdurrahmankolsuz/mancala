package com.cycleon.game.mancala.mapper;

import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMapperImpl implements GameMapper {

    private final BoardMapper boardMapper;

    @Autowired
    public GameMapperImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public GameDto toDTO(Game entity) {
        GameDto gameDto = new GameDto();
        gameDto.setId(entity.getId());
                gameDto.setBoard(boardMapper.toDTO(entity.getBoard()));
        gameDto.setPlayerTurn(entity.getPlayerTurn());
        gameDto.setIsOver(entity.getIsOver());
        return gameDto;
    }

    @Override
    public Game toEntity(GameDto dto) {
        Game game = new Game();
        game.setId(dto.getId());
        game.setBoard(boardMapper.toEntity(dto.getBoard()));
        game.setIsOver(dto.getIsOver());
        game.setPlayerTurn(dto.getPlayerTurn());

        return game;
    }
}
