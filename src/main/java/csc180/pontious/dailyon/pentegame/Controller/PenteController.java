
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


    /// ///////////////////////////Cooked???///////////////////////////////////////
//    private String imagePath = (currentPlayer == nameOne)
//            ? "/conceredCat.png"
//            : "/huhCat.webp";
    private String getImagePath() {
        return (currentPlayer == nameOne) ? "/conceredCat.png" : "/huhCat.webp";
    }

    /// ///////////////////////////////////////////////////////////////////////

    public void initializePlayers(String playerNameOne, String playerNameTwo) {

        nameOne = new Player(playerNameOne);
        nameTwo = new Player(playerNameTwo);
        currentPlayer = nameOne;
        updateTurn();
    }

    public void handleMove(int row, int col) {
        if (isMoveValid(row, col)) {
            placePiece(row, col);
            if (checkWin(row, col)) { // Stop the game if someone wins
                return;
            }
            currentPlayer = (currentPlayer == nameOne) ? nameTwo : nameOne;
            updateTurn();
        }
    }


    private void placePiece(int row, int col) {
        String imagePath = getImagePath();
        Image img = new Image(getClass().getResource(imagePath).toString());
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
    void gridClick(MouseEvent event) {
        GridPane grid = (GridPane) event.getSource();
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // Calculate the row and column based on the grid layout
        int col = (int) (mouseX / grid.getWidth() * 19); // Assuming 19 columns for a Pente board
        int row = (int) (mouseY / grid.getHeight() * 19); // Assuming 19 rows for a Pente board

        // Ensure the row and column are within bounds
        col = Math.min(Math.max(col, 0), 18);
        row = Math.min(Math.max(row, 0), 18);

        // Handle the move with the calculated row and column
        handleMove(row, col);

    }
    /// ////////////////////////////Checking Win Methods////////////////////////
    private boolean checkWin(int row, int col) {
        if(checkDirection(row, col, 1, 0) ||
            checkDirection(row, col, 0, 1) ||
            checkDirection(row, col, 1, 1) ||
            checkDirection(row, col, 1, -1))   {
            lblTurn.setText(currentPlayer.getName() + " won!");
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

            if (row < 0 || row >= 19 || col < 0 || col >= 19) break; // Out of bounds

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
        String currentPlayerImagePath = (currentPlayer == nameOne) ? "conceredCat.png" : "huhCat.webp";
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
}
