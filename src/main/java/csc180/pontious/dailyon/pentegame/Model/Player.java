/**
 * @author dpontious
 * @createdOn 1/21/2025 at 9:40 AM
 * @projectName PenteGame
 * @packageName csc180.pontious.dailyon.pentegame.Model;
 */
package csc180.pontious.dailyon.pentegame.Model;

public class Player {
    private String name;

    public Player() { // Default Constructor
    }

    public Player(String name) {
        this.name = (name == null || name.trim().isEmpty()) ? "Unnamed Player" : name;
    }

    public String getName() {
        return name;
    }

}
