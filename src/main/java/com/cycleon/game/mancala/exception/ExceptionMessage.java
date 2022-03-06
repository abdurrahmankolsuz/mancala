package com.cycleon.game.mancala.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
public class ExceptionMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
}
