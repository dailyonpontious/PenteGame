
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
    private String imagePath = (currentPlayer == nameOne)
            ? "/conceredCat.png"
            : "/huhCat.webp";


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
            checkWin(row, col);


            currentPlayer = (currentPlayer == nameOne) ? nameTwo : nameOne;
            updateTurn();
        }
    }

    private void placePiece(int row, int col) {
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

    private void checkWin(int row, int col) {

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
        int col = (int) (mouseX / grid.getWidth() * 19); // Assuming 19 columns for a Pente board
        int row = (int) (mouseY / grid.getHeight() * 19); // Assuming 19 rows for a Pente board

        // Ensure the row and column are within bounds
        col = Math.min(Math.max(col, 0), 18);
        row = Math.min(Math.max(row, 0), 18);

        // Handle the move with the calculated row and column
        handleMove(row, col);

    }
}
