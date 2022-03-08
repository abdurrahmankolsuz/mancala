package com.cycleon.game.mancala.service.impl;

import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.model.Player;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameServiceImplTest {
    @Autowired
    private GameServiceImpl gameService;


    public GameServiceImplTest() throws Exception {
    }

    @Test
    @DisplayName("Get Game Instance")
    public void getGameInstance() {
        GameDto createGameInstance = gameService.createNewGame();
        GameDto collectGameCreated = gameService.getGame(createGameInstance.getId());
        BDDAssertions.then(createGameInstance.getId()).isEqualTo(collectGameCreated.getId());
    }


    @Test
    @DisplayName("Moving Right")
    public void moveRight() {
        GameDto createGameInstance = gameService.createNewGame();
        GameDto gameDto = gameService.move(createGameInstance.getId(), 8);
        BDDAssertions.then(gameDto.getPlayerTurn()).isEqualTo(Player.PLAYER_TWO);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(0).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(1).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(2).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(3).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(4).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(5).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(6).getQuantityOfStones()).isEqualTo(0);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(7).getQuantityOfStones()).isEqualTo(0);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(8).getQuantityOfStones()).isEqualTo(5);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(9).getQuantityOfStones()).isEqualTo(5);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(10).getQuantityOfStones()).isEqualTo(5);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(11).getQuantityOfStones()).isEqualTo(5);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(12).getQuantityOfStones()).isEqualTo(4);
        BDDAssertions.then(gameDto.getBoard().getPockets().get(13).getQuantityOfStones()).isEqualTo(0);



    }
}
