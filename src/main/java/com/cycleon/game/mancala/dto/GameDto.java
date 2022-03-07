package com.cycleon.game.mancala.dto;

import com.cycleon.game.mancala.model.GameStatus;
import com.cycleon.game.mancala.model.PlayerTurn;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class GameDto {
    private Integer id;
    private BoardDto board;
    private GameStatus gameStatus;
    @Enumerated(EnumType.STRING)
    private PlayerTurn playerTurn;
}
