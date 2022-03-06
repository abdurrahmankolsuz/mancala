package com.cycleon.game.mancala.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GameExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  protected void handleExceptions(Exception ex) {
    log.error(ex.getMessage(), ex);
  }
}
