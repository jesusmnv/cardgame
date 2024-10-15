import java.awt.*;
import java.awt.event.*;

public class GameFrame extends Frame { // Create a Frame class to hold your game panel
  public GameFrame() {
    setTitle("Card Matching Game");
    setSize(800, 600); // Set the size of the frame
    setLayout(new BorderLayout());

    // Create and add the GamePanel
    GamePanel gamePanel = new GamePanel();
    add(gamePanel, BorderLayout.CENTER);

    // Add a WindowListener to handle closing the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0); // Close the application
      }
    });

    setVisible(true); // Make the frame visible
  }

  public static void main(String[] args) {
    new GameFrame(); // Start the application
  }
}
