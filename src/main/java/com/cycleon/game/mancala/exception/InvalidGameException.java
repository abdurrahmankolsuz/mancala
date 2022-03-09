package com.cycleon.game.mancala.exception;

public class InvalidGameException extends BaseException {

    public InvalidGameException(Integer gameId) {
        super(String.format("Game %1$s is already taken! ", gameId));
    }
}
