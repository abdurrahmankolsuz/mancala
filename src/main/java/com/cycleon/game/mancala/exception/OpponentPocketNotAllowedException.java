package com.cycleon.game.mancala.exception;

public class OpponentPocketNotAllowedException extends BaseException {
    public OpponentPocketNotAllowedException() {
        super("You are not allowed to touch opponent pockets");
    }
}
