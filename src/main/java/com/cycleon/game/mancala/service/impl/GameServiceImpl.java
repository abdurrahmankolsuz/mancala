package com.cycleon.game.mancala.service.impl;

import com.cycleon.game.mancala.config.Constants;
import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.dto.PocketDto;
import com.cycleon.game.mancala.mapper.GameMapper;
import com.cycleon.game.mancala.model.Board;
import com.cycleon.game.mancala.model.Game;
import com.cycleon.game.mancala.model.PlayerTurn;
import com.cycleon.game.mancala.model.Pocket;
import com.cycleon.game.mancala.repository.GameRepository;
import com.cycleon.game.mancala.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameDto createNewGame(Integer numberOfSeeds) {
        Game game = new Game();
        Board board = new Board();

        List<Pocket> pockets = new ArrayList<>();
        for (int index = Constants.POCKET_FIRST_INDEX; index <= Constants.POCKET_LAST_INDEX; index++) {
            Pocket pocket = new Pocket();
            pocket.setPocketIdentifier(index);
            if (index == Constants.PLAYER_ONE_MANCALA_INDEX || index == Constants.PLAYER_TWO_MANCALA_INDEX)
                pocket.setQuantityOfStones(Constants.EMPTY_STONE);
            else
                pocket.setQuantityOfStones(Constants.DEFAULT_STONES);
            pocket.setBoard(board);
            pockets.add(pocket);
        }
        board.setPockets(pockets);
        game.setBoard(board);
        gameRepository.saveAndFlush(game);
        log.info("Game: {} registered successfully!", game.getId());
        return gameMapper.toDTO(game);
    }

    @Override
    public GameDto getGameInstance(Integer id) {
        if (gameRepository.findById(id).isPresent())
            return gameMapper.toDTO(gameRepository.findById(id).get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.");
    }

    public Game getGame(Integer id) {
        if (gameRepository.findById(id).isPresent())
            return gameRepository.findById(id).get();

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.");
    }

    @Override
    public Game move(Integer id, Integer pocketIndex) {

        Game game = this.getGame(id);
        Board board = game.getBoard();

        if (pocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) || pocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX))
            return game;

        if (game.getPlayerTurn() == null) {
            if (pocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX)
                game.setPlayerTurn(PlayerTurn.PLAYER_SOUTH);
            else
                game.setPlayerTurn(PlayerTurn.PLAYER_NORTH);
        }

        if (game.getPlayerTurn().equals(PlayerTurn.PLAYER_SOUTH) && pocketIndex > Constants.PLAYER_ONE_MANCALA_INDEX ||
                game.getPlayerTurn().equals(PlayerTurn.PLAYER_NORTH) && pocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX)
            return game;


        Pocket selectedPocket = board.getPockets().stream().filter(p -> p.getPocketIdentifier().equals(pocketIndex)).findFirst().get();
        Integer stonesInPocket = selectedPocket.getQuantityOfStones();

        if (stonesInPocket.equals(Constants.EMPTY_STONE))
            return game;

        selectedPocket.setQuantityOfStones(Constants.EMPTY_STONE);

        game.setCurrentPocketIndex(pocketIndex);

        for (int i = 0; i < stonesInPocket - 1; i++) {
            moveRight(game, false);
        }

        moveRight(game, true);

        Integer currentPocketIndex = game.getCurrentPocketIndex();

        if (!currentPocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) && !currentPocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX))
            game.setPlayerTurn(nextTurn(game.getPlayerTurn()));

        game.setIsOver(checkGameOver(game));
        gameRepository.saveAndFlush(game);

        return game;
    }

    @Override
    public GameDto gameToDTO(Game game) {
        GameDto gameDTO = gameMapper.toDTO(game);
        gameDTO.getBoard().setPockets(gameDTO.getBoard().getPockets().stream().sorted(Comparator.comparing(PocketDto::getPocketIdentifier)).collect(Collectors.toList()));
        return gameDTO;
    }

    private void moveRight(Game game, Boolean isLastStone) {
        Integer currentPocketIndex = game.getCurrentPocketIndex() % Constants.POCKET_LAST_INDEX + 1;
        PlayerTurn playerTurn = game.getPlayerTurn();
        if (currentPocketIndex.equals(Constants.PLAYER_ONE_MANCALA_INDEX) && playerTurn.equals(PlayerTurn.PLAYER_NORTH) ||
                currentPocketIndex.equals(Constants.PLAYER_TWO_MANCALA_INDEX) && playerTurn.equals(PlayerTurn.PLAYER_SOUTH)) {
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
            Integer oppositeSeeds = oppositePocket.getQuantityOfStones();
            oppositePocket.setQuantityOfStones(Constants.EMPTY_STONE);
            Integer pocketHouseIndex = currentPocketIndex < Constants.PLAYER_ONE_MANCALA_INDEX ? Constants.PLAYER_ONE_MANCALA_INDEX : Constants.PLAYER_TWO_MANCALA_INDEX;
            Pocket pocketHouse = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(pocketHouseIndex)).findFirst().get();
            pocketHouse.setQuantityOfStones(pocketHouse.getQuantityOfStones() + oppositeSeeds + 1);
            return;
        }
        targetPocket.setQuantityOfStones(targetPocket.getQuantityOfStones() + 1);
    }

    private PlayerTurn nextTurn(PlayerTurn currentTurn) {
        if (currentTurn.equals(PlayerTurn.PLAYER_NORTH))
            return PlayerTurn.PLAYER_SOUTH;
        return PlayerTurn.PLAYER_NORTH;
    }

    private boolean checkGameOver(Game game) {
        Integer playerOneStones = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier() >= Constants.POCKET_FIRST_INDEX && p.getPocketIdentifier() < Constants.PLAYER_ONE_MANCALA_INDEX).mapToInt(Pocket::getQuantityOfStones).sum();
        Integer playerTwoStones = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier() > Constants.PLAYER_ONE_MANCALA_INDEX && p.getPocketIdentifier() < Constants.PLAYER_TWO_MANCALA_INDEX).mapToInt(Pocket::getQuantityOfStones).sum();
        if (playerOneStones.equals(0) || playerTwoStones.equals(0)) {
            Pocket houseSouth = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(Constants.PLAYER_ONE_MANCALA_INDEX)).findFirst().get();
            Pocket houseNorth = game.getBoard().getPockets().stream().filter(p -> p.getPocketIdentifier().equals(Constants.PLAYER_TWO_MANCALA_INDEX)).findFirst().get();
            houseSouth.setQuantityOfStones(houseSouth.getQuantityOfStones() + playerOneStones);
            houseNorth.setQuantityOfStones(houseNorth.getQuantityOfStones() + playerTwoStones);

            game.getBoard().getPockets().forEach(pocket -> {
                if (pocket.getPocketIdentifier() != Constants.PLAYER_ONE_MANCALA_INDEX && pocket.getPocketIdentifier() != Constants.PLAYER_TWO_MANCALA_INDEX) {
                    pocket.setQuantityOfStones(0);
                }
            });

            return true;
        }
        return false;
    }
}
