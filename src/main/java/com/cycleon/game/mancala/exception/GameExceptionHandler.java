package com.cycleon.game.mancala.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.MalformedURLException;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class GameExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  protected void handleExceptions(Exception ex) {
    log.error(ex.getMessage(), ex);
  }

  @ExceptionHandler(value = {InvalidPocketIndexException.class})
  public ResponseEntity<Object> handleInvalidInputException(InvalidPocketIndexException exception) {
    final String msg = exception.getMessage();
    log.error(msg);
    ExceptionMessage message =
            new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), new Date(), msg);
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

}
