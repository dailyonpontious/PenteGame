
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
        String imagePath = (currentPlayer == nameOne) ? "/concernedCat.png" : "/huhCat.webp";
        Image img = new Image(getClass().getResource(imagePath).toString());
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(30);
        imgView.setFitHeight(30);

        penteGrid.add(imgView, col, row);
    }

    private boolean isMoveValid(int row, int col) {

        for (Node node : penteGrid.getChildren()) { // This checks to see if there is already a piece there and says no if it finds one.
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
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
    void gridClick(MouseEvent event) {
        Node clickedNode = (Node) event.getPickResult().getIntersectedNode();

        if (clickedNode != penteGrid) {  // Ignore clicks outside the grid
            int col = GridPane.getColumnIndex(clickedNode) == null ? 0 : GridPane.getColumnIndex(clickedNode);
            int row = GridPane.getRowIndex(clickedNode) == null ? 0 : GridPane.getRowIndex(clickedNode);

            handleMove(row, col);
        }
    }
}
