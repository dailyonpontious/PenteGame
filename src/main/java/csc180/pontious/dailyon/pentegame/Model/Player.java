/**
 * @author dpontious
 * @createdOn 1/21/2025 at 9:40 AM
 * @projectName PenteGame
 * @packageName csc180.pontious.dailyon.pentegame.Model;
 */
package csc180.pontious.dailyon.pentegame.Model;

public class Player {
    private String name;
    private String symbol; // "X" or "O"

    public Player(String name, String symbol) {
        this.name = (name == null || name.trim().isEmpty()) ? "Unnamed Player" : name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
