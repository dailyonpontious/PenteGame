
package csc180.pontious.dailyon.pentegame.Controller;

import csc180.pontious.dailyon.pentegame.Model.Player;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class PenteController {
    private Player nameOne;
    private Player nameTwo;
    private Player currentPlayer;

    @FXML
    private TextField txtPlayerOne;

    @FXML
    private TextField txtPlayerTwo;
    @FXML
    private GridPane penteGrid;

    @FXML
    public Label lblTurn;
    @FXML
    public Button btnStart;



    /// ///////////////////////////////////////////////////////////////////////

    public void initializePlayers(String playerNameOne, String playerNameTwo) {

        nameOne = new Player(playerNameOne, "/conceredCat.png");
        nameTwo = new Player(playerNameTwo, "/better_Huh.png");
        currentPlayer = nameOne;
        updateTurn();
    }

    public void handleMove(int row, int col) {
        if (isMoveValid(row, col)) {
            placePiece(row, col);
            checkCapture(row, col);
            if (checkWin(row, col)) { // Stop the game if someone wins
                return;
            }
            currentPlayer = (currentPlayer == nameOne) ? nameTwo : nameOne;
            updateTurn();
        }
    }

    private void placePiece(int row, int col) {
        Image img = new Image(getClass().getResource(currentPlayer.getImagePath()).toString());
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(30);
        imgView.setFitHeight(30);

        penteGrid.add(imgView, col, row);
    }

    private boolean isMoveValid(int row, int col) {

        for (Node node : penteGrid.getChildren()) { // This checks to see if there is already a piece there and says no if it finds one.
            Integer existingRow = GridPane.getRowIndex(node);
            Integer existingCol = GridPane.getColumnIndex(node);

            if (existingRow != null && existingCol != null
                    && existingRow == row && existingCol == col && node instanceof ImageView) {
                return false;
            }
        }
        return true;
    }

    private void updateTurn() {
        lblTurn.setText("It's " + currentPlayer.getName() + "'s turn!");
    }



    @FXML
    private void startGame(ActionEvent event) {
        String playerOne = txtPlayerOne.getText();
        String playerTwo = txtPlayerTwo.getText();

        if (playerOne.isEmpty()) playerOne = "Player 1"; //for null or empty names
        if (playerTwo.isEmpty()) playerTwo = "Player 2"; //for null or empty names

        initializePlayers(playerOne, playerTwo);
    }

    @FXML
    void gridClick(MouseEvent event) { // CHATGPT IS SO GOATED FOR THIS!!!!!!!
        GridPane grid = (GridPane) event.getSource();
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // Calculate the row and column based on the grid layout
        int col = (int) (mouseX / grid.getWidth() * 19);
        int row = (int) (mouseY / grid.getHeight() * 19);
        // Ensure the row and column are within bounds
        col = Math.min(Math.max(col, 0), 18);
        row = Math.min(Math.max(row, 0), 18);

        // Handle the move with the calculated row and column
        handleMove(row, col);

    }
    /// ////////////////////////////Checking Win Methods////////////////////////
    private boolean checkWin(int row, int col) {
        if(checkDirection(row, col, 1, 0) || // Checks Horizontally
                checkDirection(row, col, 0, 1) || // Checks Vertically
                checkDirection(row, col, 1, 1) || // Checks Diagonal \
                checkDirection(row, col, 1, -1))   // Checks Diagonal /
        {
            lblTurn.setText(currentPlayer.getName() + " won!");
            return true;
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol) {
        int count = 1;

        count += countInDirection(row, col, dRow, dCol);
        count += countInDirection(row, col, -dRow, -dCol);

        return count >= 5;

    }
    private int countInDirection(int row, int col, int dRow, int dCol) {
        int count = 0;
        while (true) {
            row += dRow;
            col += dCol;

            if (row < 0 || row >= 19 || col < 0 || col >= 19) break; // Shows out of bounds

            ImageView piece = getPieceAt(row, col);
            if (piece != null && CurrentPlayer(piece)) {
                count++;
                System.out.println("Found piece at (" + row + ", " + col + ")"); // Debugging
            } else {
                break;
            }
        }
        return count;
    }
    private boolean CurrentPlayer(ImageView piece){
        String currentPlayerImagePath = (currentPlayer == nameOne) ? "/conceredCat.png" : "/better_Huh.png";
        return piece.getImage().getUrl().endsWith(currentPlayerImagePath);
    }


    private ImageView getPieceAt(int row, int col) {
        for (Node node : penteGrid.getChildren()) {
            Integer existingRow = GridPane.getRowIndex(node);
            Integer existingCol = GridPane.getColumnIndex(node);
            if (existingRow != null && existingCol != null && existingRow == row && existingCol == col) {
                if (node instanceof ImageView) {
                    return (ImageView) node;
                }
            }
        }
        return null;
    }
    /// ///////////////////////////////////////////////////////////////////////
    /// ////////////////Piece Taker/////////////////////////////////////////////
    private void checkCapture(int row, int col) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };

        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];

            int enemyRow = row + dRow;
            int enemyCol = col + dCol;
            int endRow = row + 2 * dRow;
            int endCol = col + 2 * dCol;

            if (isValidPosition(enemyRow, enemyCol) && isValidPosition(endRow, endCol)) {
                ImageView enemyPiece = getPieceAt(enemyRow, enemyCol);
                ImageView endPiece = getPieceAt(endRow, endCol);

                // Check if middle piece is opponent's and end piece is current player's
                if (enemyPiece != null && endPiece != null &&
                        !CurrentPlayer(enemyPiece) && CurrentPlayer(endPiece)) {

                    penteGrid.getChildren().remove(enemyPiece); // Remove only the middle captured piece

                    currentPlayer.incrementCaptureCount(); // Increase capture count

                    // Check if player wins by capturing 5 pairs (10 pieces)
                    if (currentPlayer.getCaptureCount() >= 5) {
                        lblTurn.setText(currentPlayer.getName() + " wins by capturing 5 pairs!");
                    }
                }
            }
        }
    }

    // Ensure row and column are within bounds
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 19 && col >= 0 && col < 19;
    }

}



