import javax.swing.JFrame;

public class GameFrame extends JFrame {
  public GameFrame() {
    setTitle("Card Matching Game");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center window on screen
    add(new GamePanel()); // Add the game panel to the frame
  }

  public static void main(String[] args) {
    GameFrame frame = new GameFrame();
    frame.setVisible(true); // Show the game window
  }
}
