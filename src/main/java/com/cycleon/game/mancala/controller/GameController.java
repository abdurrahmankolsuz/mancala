package com.cycleon.game.mancala.controller;


import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.dto.MoveRequestDto;
import com.cycleon.game.mancala.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        GameDto gameDto = gameService.getGame(id);
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> createGame() {
        return ResponseEntity.ok(gameService.createNewGame());
    }

    @PostMapping("/move")
    public ResponseEntity<GameDto> move(@RequestBody MoveRequestDto moveRequestDto) {
        GameDto gameDto = gameService.move(moveRequestDto.getGameId(), moveRequestDto.getPocketId());
        return ResponseEntity.ok(gameDto);
    }

}
