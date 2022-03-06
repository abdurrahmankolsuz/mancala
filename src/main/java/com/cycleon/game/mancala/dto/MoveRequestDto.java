package com.cycleon.game.mancala.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoveRequestDto {
  private Integer gameId;
  private Integer pocketId;
}
