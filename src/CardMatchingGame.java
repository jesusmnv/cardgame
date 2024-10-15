import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class CardMatchingGame extends Frame {
  private Panel gamePanel;
  private Choice styleChoice;
  private Button resetButton, exitButton;
  private Label timerLabel, scoreLabel;
  private int score = 0;
  private Timer gameTimer;
  private int timeRemaining = 300; // 5 minutes = 300 seconds

  private ArrayList<Card> cards;
  private Card firstSelectedCard;
  private Card secondSelectedCard;
  private boolean isCheckingPair = false;

  public CardMatchingGame() {
    setTitle("Card Matching Game");
    setLayout(new BorderLayout());

    // Create top panel with choice, timer, and score
    Panel topPanel = new Panel();
    topPanel.setLayout(new GridLayout(1, 5));

    // Choice to select style
    styleChoice = new Choice();
    styleChoice.add("Numbers");
    styleChoice.add("Letters");
    styleChoice.add("Shapes");
    styleChoice.addItemListener(e -> resetGame());
    topPanel.add(styleChoice);

    // Timer label
    timerLabel = new Label("Time: 5:00");
    topPanel.add(timerLabel);

    // Score label
    scoreLabel = new Label("Score: 0");
    topPanel.add(scoreLabel);

    // Reset and exit buttons
    resetButton = new Button("Reset");
    resetButton.addActionListener(e -> resetGame());
    topPanel.add(resetButton);

    exitButton = new Button("Exit");
    exitButton.addActionListener(e -> System.exit(0));
    topPanel.add(exitButton);

    add(topPanel, BorderLayout.NORTH);

    // Create game panel
    gamePanel = new Panel();
    gamePanel.setLayout(new GridLayout(4, 5, 5, 5)); // 4x5 grid
    add(gamePanel, BorderLayout.CENTER);

    // Start game with initial setup
    startGame();

    // Start the game timer
    startTimer();

    setSize(600, 400);
    setVisible(true);

    // Close operation
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
  }

  private void startGame() {
    gamePanel.removeAll(); // Clear previous cards
    cards = new ArrayList<>();

    // Get selected style from choice
    String selectedStyle = styleChoice.getSelectedItem();

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
      card.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          handleCardSelection(card);
        }
      });
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
          System.out.println("Time's up! Game over."); // Change to your preferred way to display messages
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

    new Timer().schedule(task, 500); // Flash red/yellow every 500ms
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

      new Timer().schedule(task, 100); // Wait 100 milliseconds before checking
    }
  }

  private void resetGame() {
    timeRemaining = 300; // Reset timer to 5 minutes
    startGame(); // Restart the game
  }

  public static void main(String[] args) {
    new CardMatchingGame();
  }
}
