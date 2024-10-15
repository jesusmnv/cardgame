import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class CardMatchingGame extends JFrame {
  private JPanel gamePanel;
  private JComboBox<String> styleComboBox;
  private JButton resetButton, exitButton;
  private JLabel timerLabel, scoreLabel;
  private int score = 0;
  private Timer gameTimer;
  private int timeRemaining = 300; // 5 minutes = 300 seconds

  private ArrayList<Card> cards;
  private Card firstSelectedCard;
  private Card secondSelectedCard;
  private boolean isCheckingPair = false;

  public CardMatchingGame() {
    setTitle("Card Matching Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create top panel with combo box, timer, and score
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 4));

    // ComboBox to select style
    styleComboBox = new JComboBox<>(new String[] { "Numbers", "Letters", "Shapes" });
    styleComboBox.addActionListener(e -> resetGame());
    topPanel.add(styleComboBox);

    // Timer label
    timerLabel = new JLabel("Time: 5:00");
    topPanel.add(timerLabel);

    // Score label
    scoreLabel = new JLabel("Score: 0");
    topPanel.add(scoreLabel);

    // Reset and exit buttons
    resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> resetGame());
    topPanel.add(resetButton);

    exitButton = new JButton("Exit");
    exitButton.addActionListener(e -> System.exit(0));
    topPanel.add(exitButton);

    add(topPanel, BorderLayout.NORTH);

    // Create game panel
    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(4, 5, 5, 5)); // 4x5 grid
    add(gamePanel, BorderLayout.CENTER);

    // Start game with initial setup
    startGame();

    // Start the game timer
    startTimer();

    pack();
    setSize(600, 400);
    setVisible(true);
  }

  private void startGame() {
    gamePanel.removeAll(); // Clear previous cards
    cards = new ArrayList<>();

    // Get selected style from combo box
    String selectedStyle = (String) styleComboBox.getSelectedItem();

    // Create pairs of cards based on the selected style
    if (selectedStyle.equals("Shapes")) {
      String[] shapeTypes = { "Circle", "Square", "Triangle", "Star", "Hexagon", "Pentagon", "Diamond", "Heart", "Oval",
          "Rectangle" };
      for (String shapeType : shapeTypes) {
        Shape shape = ShapeFactory.getShape(shapeType);
        cards.add(new Card(shape));
        cards.add(new Card(shape)); // Add matching pair
      }
    } else if (selectedStyle.equals("Numbers")) {
      for (int i = 1; i <= 10; i++) {
        cards.add(new Card(i));
        cards.add(new Card(i)); // Add matching pair
      }
    } else if (selectedStyle.equals("Letters")) {
      char letter = 'A';
      for (int i = 0; i < 10; i++) {
        cards.add(new Card(letter));
        cards.add(new Card(letter)); // Add matching pair
        letter++;
      }
    }

    // Shuffle the cards
    Collections.shuffle(cards);

    // Add the cards to the game panel
    for (Card card : cards) {
      gamePanel.add(card);
      card.addActionListener(e -> handleCardSelection(card));
    }

    gamePanel.revalidate();
    gamePanel.repaint();
    score = 0;
    scoreLabel.setText("Score: " + score);
  }

  private void startTimer() {
    if (gameTimer != null) {
      gameTimer.cancel(); // Cancel the previous timer if game is reset
    }

    gameTimer = new Timer();
    gameTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        timeRemaining--;
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));

        if (timeRemaining == 0) {
          JOptionPane.showMessageDialog(null, "Time's up! Game over.");
          resetGame();
        }

        // Flash the screen every 10 seconds
        if (timeRemaining <= 0) {
          flashScreen();
        }
      }
    }, 1000, 1000); // Update every second
  }

  private void flashScreen() {
    Color originalColor = gamePanel.getBackground();
    gamePanel.setBackground(Color.RED);

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        gamePanel.setBackground(Color.YELLOW);
      }
    };

    new java.util.Timer().schedule(task, 500); // Flash red/yellow every 500ms
  }

  private void handleCardSelection(Card selectedCard) {
    if (isCheckingPair || selectedCard.isFlipped()) {
      return; // Ignore if already flipped or in the middle of checking a pair
    }

    selectedCard.flipUp();

    if (firstSelectedCard == null) {
      firstSelectedCard = selectedCard; // Store the first card
    } else if (secondSelectedCard == null) {
      secondSelectedCard = selectedCard; // Store the second card
      isCheckingPair = true;

      // Check if the two cards match
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          if (firstSelectedCard.matches(secondSelectedCard)) {
            score++;
            scoreLabel.setText("Score: " + score);
          } else {
            firstSelectedCard.flipDown();
            secondSelectedCard.flipDown();
            score--;
            scoreLabel.setText("Score: " + score);
          }
          firstSelectedCard = null;
          secondSelectedCard = null;
          isCheckingPair = false;
        }
      };

      new java.util.Timer().schedule(task, 100); // Wait 300 milisecons before checking
    }
  }

  private void resetGame() {
    timeRemaining = 300; // Reset timer to 5 minutes
    startGame(); // Restart the game
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(CardMatchingGame::new);
  }
}
