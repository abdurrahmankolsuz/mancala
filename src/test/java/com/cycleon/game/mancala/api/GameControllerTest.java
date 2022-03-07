package com.cycleon.game.mancala.api;

import com.cycleon.game.mancala.config.Constants;
import com.cycleon.game.mancala.dto.GameDto;
import com.cycleon.game.mancala.service.impl.GameServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameServiceImpl gameService;

    GameControllerTest() throws Exception {
    }

    @Test
    @Order(1)
    @DisplayName("Creating Game Controller Test Case")
    public void createGame() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post(Constants.APP_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Order(2)
    @DisplayName("Get Game Controller Test Case")
    public void getGame() throws Exception {
        GameDto gameDto = gameService.createNewGame();
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get(Constants.APP_PREFIX + "?id=" + gameDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Order(3)
    @DisplayName("Move Test Case")
    public void move() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get(Constants.APP_PREFIX + "/move")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
