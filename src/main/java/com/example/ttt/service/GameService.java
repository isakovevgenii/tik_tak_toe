package com.example.ttt.service;

import com.example.ttt.entity.enums.TikTakToeMark;
import com.example.ttt.entity.exception.*;
import com.example.ttt.entity.model.Game;
import com.example.ttt.entity.model.GameUpdating;
import com.example.ttt.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Service returning all games from the database
     *
     * @return Returns the game list
     */
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> gameList = gameRepository.findAll();
        return ResponseEntity.ok(gameList);
    }

    /**
     * Saves/creates the game
     *
     * @param game A game to save/create
     * @return Returns a saved game
     */
    public ResponseEntity<Game> saveGame(Game game) {
        Game gameResult = gameRepository.save(game);
        return ResponseEntity.ok(gameResult);
    }

    /**
     * Service responsible for the logic of updating the game when the player moves
     *
     * @param gameUpdating Data for game update
     * @return Returns an updated game or an exception with a description
     */
    public synchronized ResponseEntity<Game> updateGame(GameUpdating gameUpdating) {
        Game game = gameRepository.findById(gameUpdating.getGameId()).orElseThrow(() ->
                new ResourceNotFoundException("Game not found for this id :: " + gameUpdating.getGameId()));

        if (isFinishedCheck(game)) throw new GameEndingException();
        if (!scaleCheck(game, gameUpdating)) throw new ScaleException();
        if (!accessMoveChecking(game, gameUpdating)) throw new AccessMoveException();

        updateGameInDatabase(game, gameUpdating);
        return ResponseEntity.ok(game);
    }

    /**
     * Checks if the game is over
     *
     * @param game A game to check
     * @return Returns a boolean flag with information about the end of the game
     */
    public boolean isFinishedCheck(Game game) {
        return game.isFinished();
    }

    /**
     * Checks the validity of the coordinates from the information to update the game
     *
     * @param game         A game that will be updated
     * @param gameUpdating Update information
     * @return Boolean flag about the validity of coordinates for the scale of this game
     */
    public boolean scaleCheck(Game game, GameUpdating gameUpdating) {
        return (game.getScale() > gameUpdating.getHorizontal()) &&
                (game.getScale() > gameUpdating.getVertical());
    }

    /**
     * Checking the availability of the cell to move
     *
     * @param game         A game that will be updated
     * @param gameUpdating Update information
     * @return Boolean flag about the availability of a cell for a move
     */
    public boolean accessMoveChecking(Game game, GameUpdating gameUpdating) {
        String[][] gameArrangement = convertStringToArray(game.getArrangement());
        return (gameArrangement[gameUpdating.getHorizontal()][gameUpdating.getVertical()].equals("E"));
    }

    /**
     * Translating the game arrangement from a string to a two-dimensional array
     *
     * @param string Arrangement in string type view
     * @return Arrangement in two-dimensional string array
     */
    public String[][] convertStringToArray(String string) {
        String[][] array = Arrays.stream(string.substring(2, string.length() - 2).split("\\],\\["))
                .map(e -> Arrays.stream(e.split("\\s*,\\s*"))
                        .toArray(String[]::new)).toArray(String[][]::new);
        System.out.println(array);
        return array;
    }

    /**
     * Translating the game arrangement from a two-dimensional array to a string
     *
     * @param stringArray Arrangement in two-dimensional string array
     * @return Arrangement in string type view
     */
    public String convertArrayToString(String[][] stringArray) {
        return Arrays.deepToString(stringArray).replaceAll("\\s+", "");
    }

    /**
     * Updating the arrangement of a two-dimensional array
     *
     * @param game         A game that will be updated
     * @param gameUpdating Update information
     * @return Updated two-dimensional array
     */
    private String[][] updateArrangement(Game game, GameUpdating gameUpdating) {
        String[][] gameArrangement = convertStringToArray(game.getArrangement());
        gameArrangement[gameUpdating.getHorizontal()][gameUpdating.getVertical()] = symbolSelection(game, gameUpdating);
        return gameArrangement;
    }

    /**
     * Selecting a symbol for the player. The first player moves "X", the second player moves "O".
     *
     * @param game         A game that will be updated
     * @param gameUpdating Update information
     * @return Symbol for the player
     */
    public String symbolSelection(Game game, GameUpdating gameUpdating) {
        String playerName = gameUpdating.getPlayerName();
        if (game.getPlayerName1().equals(playerName)) return TikTakToeMark.X.toString();
        if (game.getPlayerName2().equals(playerName)) return TikTakToeMark.O.toString();
        throw new NonexistentGamerException();
    }

    /**
     * Checking the end of the game for the victory of one or another player
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status
     */
    private boolean gameFinishCheck(int scale, String[][] gameArrangement) {
        if (checkColumnsForFinish(scale, gameArrangement)) return true;
        if (checkRowsForFinish(scale, gameArrangement)) return true;
        if (checkDiagonals1ForFinish(scale, gameArrangement)) return true;
        return checkDiagonals2ForFinish(scale, gameArrangement);
    }

    /**
     * Checking the end of the game for the victory of one or another player by columns
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status by columns
     */
    public boolean checkColumnsForFinish(int scale, String[][] gameArrangement) {
        for (int i = 0; i < scale; i++) {
            boolean win = true;
            for (int j = 0; j < scale - 1; j++) {
                win = win &&
                        !gameArrangement[i][j].equals(TikTakToeMark.E.toString()) &&
                        gameArrangement[i][j].equals(gameArrangement[i][j + 1]);
                if (!win) break;
            }
            if (win) return true;
        }
        return false;
    }

    /**
     * Checking the end of the game for the victory of one or another player by rows
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status by rows
     */
    public boolean checkRowsForFinish(int scale, String[][] gameArrangement) {
        for (int j = 0; j < scale; j++) {
            boolean win = true;
            for (int i = 0; i < scale - 1; i++) {
                win = win &&
                        !gameArrangement[i][j].equals(TikTakToeMark.E.toString()) &&
                        gameArrangement[i][j].equals(gameArrangement[i + 1][j]);
                if (!win) break;
            }
            if (win) return true;
        }
        return false;
    }

    /**
     * Checking the end of the game for the victory of one or another player by diagonals
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status by diagonals
     */
    public boolean checkDiagonals1ForFinish(int scale, String[][] gameArrangement) {
        boolean win = true;
        for (int i = 0; i < scale - 1; i++) {
            win = win &&
                    !gameArrangement[i][i].equals(TikTakToeMark.E.toString()) &&
                    gameArrangement[i][i].equals(gameArrangement[i + 1][i + 1]);
            if (!win) break;
        }
        return win;
    }

    /**
     * Checking the end of the game for the victory of one or another player by diagonals
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status by diagonals
     */
    public boolean checkDiagonals2ForFinish(int scale, String[][] gameArrangement) {
        boolean win = true;
        for (int i = 0; i < scale - 1; i++) {
            int j = scale - 1 - i;
            win = win &&
                    !gameArrangement[i][j].equals(TikTakToeMark.E.toString()) &&
                    gameArrangement[i][j].equals(gameArrangement[i + 1][j - 1]);
            if (!win) break;
        }
        return win;
    }

    /**
     * Checking the end of the game for the victory of one or another player by unavailable cells
     *
     * @param scale           Scale of the game
     * @param gameArrangement Field arrangement
     * @return Boolean function that shows the end game status by unavailable cells
     */
    public boolean gameFinishCheckDueUnavailableCells(int scale, String[][] gameArrangement) {
        boolean gameOver = true;
        for (int i = 0; i < scale - 1; i++) {
            for (int j = 0; j < scale - 1; j++) {
                if (gameArrangement[i][j].equals("E")) {
                    gameOver = false;
                    break;
                }
            }
        }
        return gameOver;
    }

    /**
     * Deleting the old game record in the database and saving the new updated version
     *
     * @param game         A game that will be updated
     * @param gameUpdating Update information
     */
    private void updateGameInDatabase(Game game, GameUpdating gameUpdating) {
        String[][] updateGameArrangement = updateArrangement(game, gameUpdating);

        game.setArrangement(convertArrayToString(updateGameArrangement));
        game.setFinished(gameFinishCheck(game.getScale(), updateGameArrangement) ||
                gameFinishCheckDueUnavailableCells(game.getScale(), updateGameArrangement));

        gameRepository.deleteById(game.getId());
        gameRepository.save(game);
    }
}
