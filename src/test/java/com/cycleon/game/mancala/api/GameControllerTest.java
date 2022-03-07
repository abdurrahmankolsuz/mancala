package com.cycleon.game.mancala.api;

import com.cycleon.game.mancala.config.Constants;
import com.cycleon.game.mancala.controller.GameController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GameController.class)
@AutoConfigureMockMvc
@EnableWebMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    GameControllerTest() throws Exception {
    }

    @Test
    @DisplayName("Creating Game Test Case")
    public void createGame() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post(Constants.APP_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
