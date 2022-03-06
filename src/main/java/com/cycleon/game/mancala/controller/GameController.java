package com.cycleon.game.mancala.controller;


import com.cycleon.game.mancala.config.Constants;
import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.dto.MoveRequestDto;
import com.cycleon.game.mancala.model.Game;
import com.cycleon.game.mancala.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(value = "api/game")
@Slf4j
public class GameController {

    private GameService gameService;

    @Autowired
    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<GameDto> getGameData(@RequestParam Integer id) {
        GameDto gameDto = gameService.getGameInstance(id);
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> createGame() {
        return ResponseEntity.ok(gameService.createNewGame(Constants.DEFAULT_STONES));
    }

    @PostMapping("/move")
    public ResponseEntity<GameDto> move(@RequestBody MoveRequestDto moveRequestDto) {
        if (moveRequestDto.getPocketId().equals(null) || moveRequestDto.getPocketId() < 1 || moveRequestDto.getPocketId().equals(Constants.PLAYER_ONE_MANCALA_INDEX) || moveRequestDto.getPocketId().equals(Constants.PLAYER_TWO_MANCALA_INDEX) || moveRequestDto.getPocketId() > Constants.PLAYER_TWO_MANCALA_INDEX)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pocket Index is Invalid.");

        Game game = gameService.move(moveRequestDto.getGameId(), moveRequestDto.getPocketId());
        GameDto gameDTO = gameService.gameToDTO(game);
        return ResponseEntity.ok(gameDTO);
    }

}
