package com.cycleon.game.mancala.dto;

import com.cycleon.game.mancala.model.PlayerTurn;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class GameDto {
    private Integer id;
    private BoardDto board;
    private Boolean isOver;
    @Enumerated(EnumType.STRING)
    private PlayerTurn playerTurn;
}
