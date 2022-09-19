package com.example.ttt.controller;

import com.example.ttt.entity.model.Game;
import com.example.ttt.entity.model.GameUpdating;
import com.example.ttt.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GameControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void getAllGameTest() throws Exception {

        Game mockGame1 = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                false
        );

        Game mockGame2 = new Game(
                2,
                3,
                "[[X,E,E],[X,E,E],[E,E,E]]",
                "player3",
                "player4",
                false
        );

        List<Game> gameList = new ArrayList<>();
        gameList.add(mockGame1);
        gameList.add(mockGame2);

        String expected = "" +
                "[{\n" +
                "    \"id\": 1,\n" +
                "    \"arrangement\": \"[[E,E,E],[E,E,E],[E,E,E]]\",\n" +
                "    \"playerName1\": \"player1\",\n" +
                "    \"playerName2\": \"player2\",\n" +
                "    \"finished\": false,\n" +
                "    \"scale\" : 3\n" +
                "}," +
                "{\n" +
                "    \"id\": 2,\n" +
                "    \"arrangement\": \"[[X,E,E],[X,E,E],[E,E,E]]\",\n" +
                "    \"playerName1\": \"player3\",\n" +
                "    \"playerName2\": \"player4\",\n" +
                "    \"finished\": false,\n" +
                "    \"scale\" : 3\n" +
                "}]" +
                "";

        Mockito.when(
                gameService.getAllGames()).thenReturn(ResponseEntity.ok(gameList));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/game/all")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    public void postNewGameTest() throws Exception {

        Game mockGame = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                false
        );

        String exampleGameJson = "" +
                "{\n" +
                "    \"id\": 1,\n" +
                "    \"arrangement\": \"[[E,E,E],[E,E,E],[E,E,E]]\",\n" +
                "    \"playerName1\": \"player1\",\n" +
                "    \"playerName2\": \"player2\",\n" +
                "    \"isFinished\": false,\n" +
                "    \"scale\" : 3\n" +
                "}";

        String expected = "" +
                "{\n" +
                "    \"id\": 1,\n" +
                "    \"arrangement\": \"[[E,E,E],[E,E,E],[E,E,E]]\",\n" +
                "    \"playerName1\": \"player1\",\n" +
                "    \"playerName2\": \"player2\",\n" +
                "    \"finished\": false,\n" +
                "    \"scale\" : 3\n" +
                "}";

        Mockito.when(
                gameService.saveGame(
                        Mockito.any(Game.class))).thenReturn(ResponseEntity.ok(mockGame));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/game/add")
                .accept(MediaType.APPLICATION_JSON).content(exampleGameJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void updateGameTest() throws Exception {

        Game mockGame = new Game(
                1,
                3,
                "[[E,E,E],[E,X,E],[E,E,E]]",
                "player1",
                "player2",
                false
        );

        String exampleGameUpdatingJson = "" +
                "{\n" +
                "    \"gameId\": 1,\n" +
                "    \"playerName\": \"player1\",\n" +
                "    \"vertical\": \"1\",\n" +
                "    \"horizontal\": \"1\"\n" +
                "}" +
                "";

        String expected = "" +
                "{\n" +
                "    \"id\": 1,\n" +
                "    \"arrangement\": \"[[E,E,E],[E,X,E],[E,E,E]]\",\n" +
                "    \"playerName1\": \"player1\",\n" +
                "    \"playerName2\": \"player2\",\n" +
                "    \"finished\": false,\n" +
                "    \"scale\" : 3\n" +
                "}";

        Mockito.when(
                gameService.updateGame(
                        Mockito.any(GameUpdating.class))).thenReturn(ResponseEntity.ok(mockGame));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/game/update")
                .accept(MediaType.APPLICATION_JSON).content(exampleGameUpdatingJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}
