/**
 * @author dpontious
 * @createdOn 1/21/2025 at 9:40 AM
 * @projectName PenteGame
 * @packageName csc180.pontious.dailyon.pentegame.Model;
 */
package csc180.pontious.dailyon.pentegame.Model;

public class Player {
    private String name;
    private String imagePath;
    private int caputureCount;
/// ////////////////Getters and Setters///////////////////////////////////
    public int getCaptureCount() {
        return caputureCount;
    }
    public void setCaputureCount(int caputureCount) {
        this.caputureCount = caputureCount;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    /// /////////////////////////////////////////////////
    /// ////////////////Constructors/////////////////
    public Player() { // Default Constructor

    }

    public Player(String name, String imagePath) {
        this.name = (name == null || name.trim().isEmpty()) ? "Unnamed Player" : name;
        this.imagePath = imagePath;
        this.caputureCount = 0;
    }

    public void incrementCaptureCount() {
        caputureCount++;
    }


}
