package com.cycleon.game.mancala.config;

public class Constants {
  public static final int PLAYER_ONE_MANCALA_INDEX = 7;
  public static final int PLAYER_TWO_MANCALA_INDEX = 14;
  public static final int EMPTY_STONE = 0;
  public static final int POCKET_FIRST_INDEX = 1;
  public static final int POCKET_LAST_INDEX = 14;
  public static final int STONES = 4;

  public static final String MESSAGE_PREFIX = "/topic";
  public static final String APP_PREFIX = "/api/game";
  public static final String STOMP_ENDPOINT = "/stomp-endpoint";
  public static final String GAME_STATUS = "/game-status";
  public static final String AVAILABLE_GAMES = "/available-games";

  private Constants() {}
}
