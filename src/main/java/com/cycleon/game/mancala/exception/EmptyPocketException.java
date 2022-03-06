package com.cycleon.game.mancala.exception;

public class EmptyPocketException extends BaseException {
    public EmptyPocketException() {
        super("It is already empty. Can't see it!");
    }
}
