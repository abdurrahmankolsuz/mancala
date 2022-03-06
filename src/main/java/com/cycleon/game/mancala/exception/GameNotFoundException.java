package com.cycleon.game.mancala.exception;

public class GameNotFoundException extends BaseException {
    public GameNotFoundException(Integer gameId) {
        super(String.format("Game %1$s not found! ", gameId));
    }
}
