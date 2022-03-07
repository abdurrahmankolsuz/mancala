package com.cycleon.game.mancala.service.impl;

import com.cycleon.game.mancala.config.Constants;
import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.exception.EmptyPocketException;
import com.cycleon.game.mancala.exception.GameNotFoundException;
import com.cycleon.game.mancala.exception.InvalidPocketIndexException;
import com.cycleon.game.mancala.exception.OpponentPocketNotAllowedException;
import com.cycleon.game.mancala.mapper.GameMapper;
import com.cycleon.game.mancala.model.*;
import com.cycleon.game.mancala.repository.GameRepository;
import com.cycleon.game.mancala.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final SimpMessagingTemplate websocket;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper, SimpMessagingTemplate websocket) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.websocket = websocket;
    }

    @Override
    public GameDto createNewGame() {
        Game game = new Game();
        Board board = new Board();

        List<Pocket> pockets = new ArrayList<>();
        for (int index = Constants.POCKET_FIRST_INDEX; index <= Constants.POCKET_LAST_INDEX; index++) {
            Pocket pocket = new Pocket();
            pocket.setPocketIdentifier(index);
            if (index == Constants.PLAYER_ONE_MANCALA_INDEX || index == Constants.PLAYER_TWO_MANCALA_INDEX)
                pocket.setQuantityOfStones(Constants.EMPTY_STONE);
            else
                pocket.setQuantityOfStones(Constants.STONES);
            pocket.setBoard(board);
            pockets.add(pocket);
        }
        board.setPockets(pockets);
        game.setBoard(board);
        game.setGameStatus(GameStatus.NEW);
        gameRepository.saveAndFlush(game);
        log.info("Game: {} created successfully!", game.getId());
        return gameMapper.toDTO(game);
    }

    @Override
    public GameDto getGame(Integer id) {
        if (gameRepository.findById(id).isPresent())
            return gameMapper.toDTO(gameRepository.findById(id).get());
        throw new GameNotFoundException(id);
    }

    @Override
    public GameDto move(Integer id, Integer pocketIndex) {
        Game game;
        if (gameRepository.findById(id).isPresent())
            game = gameRepository.findById(id).get();
        else {
            throw new GameNotFoundException(id);
        }
        Board board = game.getBoard();

        if (pocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) || pocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX))
            throw new InvalidPocketIndexException(pocketIndex);

        if (game.getPlayerTurn() == null) {
            if (pocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX)
                game.setPlayerTurn(PlayerTurn.PLAYER_TWO);
            else
                game.setPlayerTurn(PlayerTurn.PLAYER_ONE);
        }

        if (game.getPlayerTurn().equals(PlayerTurn.PLAYER_TWO) && pocketIndex > Constants.PLAYER_ONE_MANCALA_INDEX ||
                game.getPlayerTurn().equals(PlayerTurn.PLAYER_ONE) && pocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX)
            throw new OpponentPocketNotAllowedException();


        Pocket selectedPocket = board.getPockets().stream().filter(p -> p.getPocketIdentifier().equals(pocketIndex)).findFirst().get();
        Integer stonesInPocket = selectedPocket.getQuantityOfStones();

        if (stonesInPocket.equals(Constants.EMPTY_STONE))
            throw new EmptyPocketException();

        selectedPocket.setQuantityOfStones(Constants.EMPTY_STONE);

        game.setCurrentPocketIndex(pocketIndex);

        for (int i = 0; i < stonesInPocket - 1; i++) {
            moveRight(game, false);
        }

        moveRight(game, true);

        Integer currentPocketIndex = game.getCurrentPocketIndex();

        if (!currentPocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) && !currentPocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX))
            game.setPlayerTurn(nextTurn(game.getPlayerTurn()));

        game.setGameStatus(checkGameOver(game) ? GameStatus.OVER : GameStatus.IN_PROGRESS);
        gameRepository.saveAndFlush(game);

        GameDto gameDto = gameMapper.toDTO(game);
        notifyGame(gameDto);
        return gameDto;
    }

    @Override
    public List<GameDto> getAllAvailableGames() {
        List<GameDto> gameDtoList = new ArrayList<>();
        for (Game game :
                gameRepository.findGamesByGameStatusEquals(GameStatus.NEW)) {
            gameDtoList.add(gameMapper.toDTO(game));
        }
        return gameDtoList;
    }


    /*
        13   12   11   10   9    8
    14                               7
         1    2    3   4   5    6

   *If the last stone drops into an empty pocket owned by the player, and the opposite pocket contains stones,
    both the last stone and the opposite stones are captured and placed into the player's mancala.
   *If the last stone drops into the player's mancala,
   the player gets an additional move. There is no limit on the number of moves a player can make in their turn.

     */

    private void moveRight(Game game, Boolean isLastStone) {
        Integer currentPocketIndex = game.getCurrentPocketIndex() % Constants.POCKET_LAST_INDEX + 1;
        PlayerTurn playerTurn = game.getPlayerTurn();
        if (currentPocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) && playerTurn.equals(PlayerTurn.PLAYER_ONE) ||
                currentPocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX) && playerTurn.equals(PlayerTurn.PLAYER_TWO)) {
            currentPocketIndex = currentPocketIndex % Constants.POCKET_LAST_INDEX + 1;
        }
        game.setCurrentPocketIndex(currentPocketIndex);

        Integer finalCurrentPocketIndex = currentPocketIndex;
        Pocket targetPocket = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(finalCurrentPocketIndex)).findFirst().get();
        if (!isLastStone || currentPocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) || currentPocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX)) {
            targetPocket.setQuantityOfStones(targetPocket.getQuantityOfStones() + 1);
            return;
        }

        Pocket oppositePocket = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(Constants.POCKET_LAST_INDEX - finalCurrentPocketIndex)).findFirst().get();
        if (targetPocket.getQuantityOfStones().equals(Constants.EMPTY_STONE) && oppositePocket.getQuantityOfStones().equals(Constants.EMPTY_STONE)) {
            Integer numberOfOppositePocket = oppositePocket.getQuantityOfStones();
            oppositePocket.setQuantityOfStones(Constants.EMPTY_STONE);
            Integer currentPlayerMancalaIndex = getCurrentPlayerMancalaIndex(currentPocketIndex);
            Pocket currentPlayerMancala = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(currentPlayerMancalaIndex)).findFirst().get();
            currentPlayerMancala.setQuantityOfStones(currentPlayerMancala.getQuantityOfStones() + numberOfOppositePocket + 1);
            return;
        }
        targetPocket.setQuantityOfStones(targetPocket.getQuantityOfStones() + 1);
    }

    private Integer getCurrentPlayerMancalaIndex(Integer currentPocketIndex) {
        Integer currentPlayerMancalaIndex = currentPocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX ? Constants.PLAYER_ONE_MANCALA_INDEX : Constants.PLAYER_TWO_MANCALA_INDEX;
        return currentPlayerMancalaIndex;
    }

    private PlayerTurn nextTurn(PlayerTurn currentTurn) {
        if (currentTurn.equals(PlayerTurn.PLAYER_ONE))
            return PlayerTurn.PLAYER_TWO;
        return PlayerTurn.PLAYER_ONE;
    }

    private boolean checkGameOver(Game game) {
        Integer playerOneStones = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier() >= Constants.POCKET_FIRST_INDEX && p.getPocketIdentifier() < Constants.PLAYER_ONE_MANCALA_INDEX).mapToInt(Pocket::getQuantityOfStones).sum();
        Integer playerTwoStones = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier() > Constants.PLAYER_ONE_MANCALA_INDEX && p.getPocketIdentifier() < Constants.PLAYER_TWO_MANCALA_INDEX).mapToInt(Pocket::getQuantityOfStones).sum();
        if (playerOneStones.equals(0) || playerTwoStones.equals(0)) {
            Pocket playerOneMancala = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(Constants.PLAYER_ONE_MANCALA_INDEX)).findFirst().get();
            Pocket playerTwoMancala = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(Constants.PLAYER_TWO_MANCALA_INDEX)).findFirst().get();
            playerOneMancala.setQuantityOfStones(playerOneMancala.getQuantityOfStones() + playerOneStones);
            playerTwoMancala.setQuantityOfStones(playerTwoMancala.getQuantityOfStones() + playerTwoStones);

            game.getBoard().getPockets().forEach(pocket -> {
                if (pocket.getPocketIdentifier() != Constants.PLAYER_ONE_MANCALA_INDEX && pocket.getPocketIdentifier() != Constants.PLAYER_TWO_MANCALA_INDEX) {
                    pocket.setQuantityOfStones(0);
                }
            });

            return true;
        }
        return false;
    }

    private void notifyGame(GameDto gameDto) {
        websocket.convertAndSend(Constants.MESSAGE_PREFIX + Constants.GAME_STATUS, gameDto);
    }

}
