package com.cycleon.game.mancala.exception;

public class InvalidPocketIndexException extends BaseException {

  public InvalidPocketIndexException(Integer pocketIndex) {
    super("Invalid Pocket Index : " + pocketIndex);
  }
}
