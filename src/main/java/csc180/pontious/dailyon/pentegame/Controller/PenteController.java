/**
 * @author dpontious
 * @createdOn 1/21/2025 at 9:39 AM
 * @projectName PenteGame
 * @packageName csc180.pontious.dailyon.pentegame.Controller;
 */
package csc180.pontious.dailyon.pentegame.Controller;

import csc180.pontious.dailyon.pentegame.Model.Player;
import javafx.fxml.FXML;

import java.awt.*;


public class PenteController {
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;

    @FXML
    private Label turnIndicatorLabel; // Label to show whose turn it is

    public void initializePlayers(String name1, String name2) {
        playerOne = new Player(name1, "X");
        playerTwo = new Player(name2, "O");
        currentPlayer = playerOne; // Player One starts
        updateTurnIndicator();
    }

    public void handleMove(int row, int col) {
        if (isMoveValid(row, col)) {
            placePiece(row, col, currentPlayer.getSymbol());
            checkForWinOrTriaTesera(row, col);

            // Switch turns
            currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
            updateTurnIndicator();
        }
    }

    private void placePiece(int row, int col, String symbol) {
        // Code to update the board UI with "X" or "O"
    }

    private boolean isMoveValid(int row, int col) {
        // Check if the move is legal
        return true;
    }

    private void updateTurnIndicator() {
        turnIndicatorLabel.setText("It's " + currentPlayer.getName() + "'s turn!");
    }

    private void checkForWinOrTriaTesera(int row, int col) {
        // Implement win condition checks and tria/tesera detection
    }
}
