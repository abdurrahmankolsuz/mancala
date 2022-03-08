package com.cycleon.game.mancala.exception;

public class InvalidGameException extends BaseException {

  public InvalidGameException(Integer gameId) {
    super("Game is already taken : " + gameId);
  }
}
