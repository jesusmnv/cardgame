import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends Panel {
  private List<Card> cards; // List to hold the cards
  private Button resetButton;
  private Button exitButton;
  private Label timerLabel;
  private Label scoreLabel; // New Label for score
  private Choice styleComboBox;
  private int score = 0;
  private int revealedCount = 0; // Count of revealed cards
  private Timer timer; // Timer for the countdown
  private int timeLeft = 300; // 5 minutes in seconds
  private boolean gameActive = false;
  private Panel animatedPanel; // New Panel for animated graphic

  public GamePanel() {
    setLayout(new BorderLayout());
    setBackground(Color.LIGHT_GRAY);

    // Initialize components
    resetButton = new Button("Reset");
    exitButton = new Button("Exit");
    timerLabel = new Label("Time Left: 5:00");
    scoreLabel = new Label("Score: 0"); // Initialize score label
    styleComboBox = new Choice();
    styleComboBox.add("Numbers");
    styleComboBox.add("Letters");
    styleComboBox.add("Shapes");

    // Panel for buttons, timer, and score
    Panel controlPanel = new Panel();
    controlPanel.add(styleComboBox);
    controlPanel.add(resetButton);
    controlPanel.add(exitButton);
    controlPanel.add(timerLabel);
    controlPanel.add(scoreLabel); // Add score label to the control panel

    add(controlPanel, BorderLayout.NORTH);

    // Add animated graphic panel
    CustomPanel animatedPanel = new CustomPanel("Animated Graphic"); // Use the custom panel
    add(animatedPanel, BorderLayout.CENTER); // Place it in the center animatedPanel = new Panel();
    animatedPanel.setPreferredSize(new Dimension(200, 200)); // Set size for the animated panel

    // Add MouseListener to handle card clicks
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e.getX(), e.getY());
      }
    });

    // Add action listeners to buttons
    resetButton.addActionListener(e -> resetGame());
    exitButton.addActionListener(e -> System.exit(0));
    styleComboBox.addItemListener(e -> setupGame());

    setupGame(); // Initialize the game
  }

  private void setupGame() {
    cards = CardFactory.createCards(styleComboBox.getSelectedItem());
    revealedCount = 0;
    score = 0;
    scoreLabel.setText("Score: " + score); // Reset score label

    // Reset the timer
    if (timer != null) {
      timer.cancel();
    }
    timeLeft = 300; // Reset time to 5 minutes
    timerLabel.setForeground(Color.BLACK);
    startTimer();

    repaint(); // Refresh the panel
  }

  private void startTimer() {
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (timeLeft > 0) {
          timeLeft--;
          int minutes = timeLeft / 60;
          int seconds = timeLeft % 60;
          timerLabel.setText(String.format("Time Left: %d:%02d", minutes, seconds));

          // Flash the background when 10 seconds are left
          if (timeLeft <= 10) {
            setBackground(getBackground() == Color.RED ? Color.YELLOW : Color.RED);
          }
        } else {
          timer.cancel();
          // Show message in a simple dialog
          Dialog dialog = new Dialog((Frame) null, "Game Over", true);
          dialog.add(new Label("Time's up! Your score: " + score));
          dialog.setSize(200, 100);
          dialog.setLayout(new FlowLayout());
          Button okButton = new Button("OK");
          okButton.addActionListener(e -> dialog.dispose());
          dialog.add(okButton);
          dialog.setVisible(true);

          resetGame();
        }
        repaint();
      }
    }, 1000, 1000); // Update every second
  }

  private void handleCardClick(int x, int y) {
    // Check which card is clicked and reveal it
    for (Card card : cards) {
      if (card.getBounds().contains(x, y) && !card.isRevealed()) {
        card.reveal(); // Reveal the card
        revealedCount++;
        score++; // Increase score for each revealed card
        scoreLabel.setText("Score: " + score); // Update score label
        repaint();
        break; // Exit after handling the first click
      }
    }
  }

  private void resetGame() {
    setupGame(); // Resets the game
    timerLabel.setText("Time Left: 5:00");
    setBackground(Color.LIGHT_GRAY); // Reset background color
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    int cardWidth = 200;
    int cardHeight = 250;
    int x = 50, y = 100; // Starting positions for the cards

    // Draw all cards
    for (Card card : cards) {
      card.setBounds(x, y, cardWidth, cardHeight);
      card.paint(g); // Use the card's draw method to display it
      x += cardWidth + 10; // Adjust x position for next card

      // Move to the next row after reaching the end of the panel
      if (x + cardWidth > getWidth()) {
        x = 50;
        y += cardHeight + 10;
      }
    }
  }
}
