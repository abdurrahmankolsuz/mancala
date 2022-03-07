package com.cycleon.game.mancala;

import com.cycleon.game.mancala.controller.GameController;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MancalaGameApplicationTests {
    @Autowired
    GameController gameController;

    public MancalaGameApplicationTests() {
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(gameController).isNotNull();
    }
}
