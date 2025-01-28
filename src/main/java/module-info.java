module csc180.pontious.dailyon.pentegame {
    requires javafx.controls;
    requires javafx.fxml;

    opens csc180.pontious.dailyon.pentegame.Controller to javafx.fxml;
    exports csc180.pontious.dailyon.pentegame;
}