package com.example.ttt.service;

import com.example.ttt.entity.enums.TikTakToeMark;
import com.example.ttt.entity.model.Game;
import com.example.ttt.entity.model.GameUpdating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameServiceTest {

    @Autowired
    GameService gameService;

    @Test
    void isFinishedCheckFalseTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                false
        );
        Assertions.assertFalse(gameService.isFinishedCheck(game));
    }

    @Test
    void isFinishedCheckTrueTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                true
        );
        Assertions.assertTrue(gameService.isFinishedCheck(game));
    }

    @Test
    void scaleCheckFalseTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                true
        );

        GameUpdating gameUpdating = new GameUpdating(
                1,
                "player1",
                3,
                3
        );

        Assertions.assertFalse(gameService.scaleCheck(game, gameUpdating));
    }

    @Test
    void scaleCheckTrueTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                true
        );

        GameUpdating gameUpdating = new GameUpdating(
                1,
                "player1",
                2,
                2
        );

        Assertions.assertTrue(gameService.scaleCheck(game, gameUpdating));
    }

    @Test
    void convertStringToArrayTest() {
        String stringArrangement = "[[E,E,E],[E,E,E],[E,E,E]]";

        String[][] arrayArrangement = new String[3][3];
        arrayArrangement[0][0] = "E";
        arrayArrangement[0][1] = "E";
        arrayArrangement[0][2] = "E";
        arrayArrangement[1][0] = "E";
        arrayArrangement[1][1] = "E";
        arrayArrangement[1][2] = "E";
        arrayArrangement[2][0] = "E";
        arrayArrangement[2][1] = "E";
        arrayArrangement[2][2] = "E";

        String[][] arrayArrangementResult = gameService.convertStringToArray(stringArrangement);
        Assertions.assertEquals(arrayArrangement[0][0], arrayArrangementResult[0][0]);
        Assertions.assertEquals(arrayArrangement[0][1], arrayArrangementResult[0][1]);
        Assertions.assertEquals(arrayArrangement[0][2], arrayArrangementResult[0][2]);
        Assertions.assertEquals(arrayArrangement[1][0], arrayArrangementResult[1][0]);
        Assertions.assertEquals(arrayArrangement[1][1], arrayArrangementResult[1][1]);
        Assertions.assertEquals(arrayArrangement[1][2], arrayArrangementResult[1][2]);
        Assertions.assertEquals(arrayArrangement[2][0], arrayArrangementResult[2][0]);
        Assertions.assertEquals(arrayArrangement[2][1], arrayArrangementResult[2][1]);
        Assertions.assertEquals(arrayArrangement[2][2], arrayArrangementResult[2][2]);
    }

    @Test
    void convertArrayToStringTest() {
        String stringArrangement = "[[E,E,E],[E,E,E],[E,E,E]]";

        String[][] arrayArrangement = new String[3][3];
        arrayArrangement[0][0] = "E";
        arrayArrangement[0][1] = "E";
        arrayArrangement[0][2] = "E";
        arrayArrangement[1][0] = "E";
        arrayArrangement[1][1] = "E";
        arrayArrangement[1][2] = "E";
        arrayArrangement[2][0] = "E";
        arrayArrangement[2][1] = "E";
        arrayArrangement[2][2] = "E";

        String stringArrangementResult = gameService.convertArrayToString(arrayArrangement);
        Assertions.assertEquals(stringArrangementResult, stringArrangement);
    }

    @Test
    void symbolSelectionXTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                true
        );

        GameUpdating gameUpdating = new GameUpdating(
                1,
                "player1",
                3,
                3
        );

        Assertions.assertEquals(TikTakToeMark.X.toString(), gameService.symbolSelection(game, gameUpdating));
    }

    @Test
    void symbolSelectionOTest() {
        Game game = new Game(
                1,
                3,
                "[[E,E,E],[E,E,E],[E,E,E]]",
                "player1",
                "player2",
                true
        );

        GameUpdating gameUpdating = new GameUpdating(
                1,
                "player2",
                3,
                3
        );

        Assertions.assertEquals(TikTakToeMark.O.toString(), gameService.symbolSelection(game, gameUpdating));
    }

    @Test
    void checkColumnsForFinishTrueTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "X";
        gameArrangement[0][2] = "X";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "E";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "E";

        Assertions.assertTrue(gameService.checkColumnsForFinish(scale, gameArrangement));
    }

    @Test
    void checkColumnsForFinishFalseTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "X";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "E";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "E";

        Assertions.assertFalse(gameService.checkColumnsForFinish(scale, gameArrangement));
    }

    @Test
    void checkRowsForFinishTrueTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "E";
        gameArrangement[1][0] = "X";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "X";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "E";

        Assertions.assertTrue(gameService.checkRowsForFinish(scale, gameArrangement));
    }

    @Test
    void checkRowsForFinishFalseTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "E";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "X";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "E";

        Assertions.assertFalse(gameService.checkRowsForFinish(scale, gameArrangement));
    }

    @Test
    void checkDiagonals1ForFinishTrueTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "E";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "X";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "E";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "X";

        Assertions.assertTrue(gameService.checkDiagonals1ForFinish(scale, gameArrangement));
    }

    @Test
    void checkDiagonals1ForFinishFalseTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "E";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "E";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "X";

        Assertions.assertFalse(gameService.checkDiagonals1ForFinish(scale, gameArrangement));
    }

    @Test
    void checkDiagonals2ForFinishTrueTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "E";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "X";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "X";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "X";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "E";

        Assertions.assertTrue(gameService.checkDiagonals2ForFinish(scale, gameArrangement));
    }

    @Test
    void checkDiagonals2ForFinishFalseTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "E";
        gameArrangement[1][0] = "E";
        gameArrangement[1][1] = "E";
        gameArrangement[1][2] = "E";
        gameArrangement[2][0] = "E";
        gameArrangement[2][1] = "E";
        gameArrangement[2][2] = "X";

        Assertions.assertFalse(gameService.checkDiagonals2ForFinish(scale, gameArrangement));
    }

    @Test
    void gameFinishCheckDueUnavailableCellsTrueTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "O";
        gameArrangement[0][2] = "X";
        gameArrangement[1][0] = "X";
        gameArrangement[1][1] = "X";
        gameArrangement[1][2] = "X";
        gameArrangement[2][0] = "X";
        gameArrangement[2][1] = "O";
        gameArrangement[2][2] = "O";

        Assertions.assertTrue(gameService.gameFinishCheckDueUnavailableCells(scale, gameArrangement));
    }

    @Test
    void gameFinishCheckDueUnavailableCellsFalseTest() {
        int scale = 3;
        String[][] gameArrangement = new String[3][3];
        gameArrangement[0][0] = "X";
        gameArrangement[0][1] = "E";
        gameArrangement[0][2] = "O";
        gameArrangement[1][0] = "X";
        gameArrangement[1][1] = "X";
        gameArrangement[1][2] = "O";
        gameArrangement[2][0] = "X";
        gameArrangement[2][1] = "O";
        gameArrangement[2][2] = "X";

        Assertions.assertFalse(gameService.gameFinishCheckDueUnavailableCells(scale, gameArrangement));
    }
}
