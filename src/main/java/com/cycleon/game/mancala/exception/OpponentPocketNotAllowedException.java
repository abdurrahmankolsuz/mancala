package com.cycleon.game.mancala.exception;

public class OpponentPocketNotAllowedException extends BaseException {
    public OpponentPocketNotAllowedException() {
        super("It is not your turn");
    }
}
