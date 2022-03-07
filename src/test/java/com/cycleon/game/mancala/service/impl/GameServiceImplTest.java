package com.cycleon.game.mancala.service.impl;

import com.cycleon.game.mancala.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest(classes = {GameController.class, GameServiceImpl.class})
@AutoConfigureMockMvc
@EnableWebMvc
class GameServiceImplTest {

    @Autowired
    private GameServiceImpl gameService;


    GameServiceImplTest() throws Exception {
    }


}
