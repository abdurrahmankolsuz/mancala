package com.cycleon.game.mancala.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardDto {
    private Integer id;
    private List<PocketDto> pockets;

}
