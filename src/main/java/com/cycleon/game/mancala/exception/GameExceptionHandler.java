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
  public ResponseEntity<Object> handleInvalidPocketIndexException(InvalidPocketIndexException exception) {
    final String msg = exception.getMessage();
    ExceptionMessage message =
            new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), new Date(), msg);
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {OpponentPocketNotAllowedException.class})
  public ResponseEntity<Object> handleOpponentPocketViolationException(OpponentPocketNotAllowedException exception) {
    final String msg = exception.getMessage();
    ExceptionMessage message =
            new ExceptionMessage(HttpStatus.FORBIDDEN.value(), new Date(), msg);
    return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {EmptyPocketException.class})
  public ResponseEntity<Object> handleEmptyPocketException(EmptyPocketException exception) {
    final String msg = exception.getMessage();
    ExceptionMessage message =
            new ExceptionMessage(HttpStatus.FORBIDDEN.value(), new Date(), msg);
    return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {GameNotFoundException.class})
  public ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException exception) {
    final String msg = exception.getMessage();
    log.error(msg);
    ExceptionMessage message =
            new ExceptionMessage(HttpStatus.NOT_FOUND.value(), new Date(), msg);
    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }
}
