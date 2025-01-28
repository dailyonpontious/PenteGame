
package csc180.pontious.dailyon.pentegame.Controller;

import csc180.pontious.dailyon.pentegame.Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PenteController {
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;

    @FXML
    public Label lblTurn; // Label to show whose turn it is

    public void initializePlayers(String playerNameOne, String playerNameTwo) {

        String catOne = this.getClass().getResource("/concernedCat.png").toString();
        String catTwo = this.getClass().getResource("/huhCat.png").toString();

        playerOne = new Player(playerNameOne, catOne);
        playerTwo = new Player(playerNameTwo, catTwo);
        currentPlayer = playerOne;
        updateTurnIndicator();
    }

    public void handleMove(int row, int col) {
        if (isMoveValid(row, col)) {
            placePiece(row, col, currentPlayer.getSymbol());
            checkForWinOrTriaTesera(row, col);


            currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
            updateTurnIndicator();
        }
    }

    private void placePiece(int row, int col, String symbol) {
        // Code to update the board UI with "X" or "O"

    }

    private boolean isMoveValid(int row, int col) {
        return true;
    }

    private void updateTurnIndicator() {
        lblTurn.setText("It's " + currentPlayer.getName() + "'s turn!");
    }

    private void checkForWinOrTriaTesera(int row, int col) {
        // Implement win condition checks and tria/tesera detection
    }
}
