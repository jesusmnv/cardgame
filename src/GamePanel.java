import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends Panel {
  private List<Card> cards; // List to hold the cards
  private List<Card> flippedCards; // New list to hold permanently flipped cards
  private Button resetButton;
  private Button exitButton;
  private Label timerLabel;
  private Label scoreLabel;
  private int score = 0;
  private Timer timer;
  private int timeLeft = 300; // 5 minutes in seconds

  public GamePanel() {
    setLayout(new BorderLayout());
    setBackground(Color.LIGHT_GRAY);

    // Initialize components
    resetButton = new Button("Reset");
    exitButton = new Button("Exit");
    timerLabel = new Label("Time Left: 5:00");
    scoreLabel = new Label("Score: 0");

    // Initialize the list for flipped cards
    flippedCards = new ArrayList<>();

    // Panel for buttons, timer, and score
    Panel controlPanel = new Panel();
    controlPanel.add(resetButton);
    controlPanel.add(exitButton);
    controlPanel.add(timerLabel);
    controlPanel.add(scoreLabel);

    add(controlPanel, BorderLayout.NORTH);

    // Add action listeners to buttons
    resetButton.addActionListener(e -> resetGame());
    exitButton.addActionListener(e -> System.exit(0));

    setupGame(); // Initialize the game
  }

  private void setupGame() {
    cards = CardFactory.createCards("Numbers"); // Replace with your logic for styles
    score = 0;
    scoreLabel.setText("Score: " + score); // Reset score label

    // Reset the timer
    if (timer != null) {
      timer.cancel();
    }
    timeLeft = 300; // Reset time to 5 minutes
    startTimer();

    // Clear previous cards from the panel
    removeAll(); // Clear previous cards

    // Add the cards to the panel
    for (Card card : cards) {
      card.setPreferredSize(new Dimension(100, 150)); // Set size for each card
      add(card); // Add the card to the panel

      // Add a mouse listener to each card
      card.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(card);
        }
      });
    }

    revalidate(); // Revalidate the panel to reflect the changes
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
        } else {
          timer.cancel();
          showMessage("Time's up! Your score: " + score);
          resetGame();
        }
        repaint();
      }
    }, 1000, 1000); // Update every second
  }

  private void handleCardClick(Card clickedCard) {
    // Check if the card is already permanently flipped or revealed
    if (flippedCards.contains(clickedCard) || clickedCard.isRevealed()) {
      return; // Ignore if already revealed
    }

    clickedCard.reveal(); // Reveal the clicked card
    score++; // Increase score for each revealed card
    scoreLabel.setText("Score: " + score); // Update score label
    repaint();

    // Check for pairs
    if (cards.stream().filter(Card::isRevealed).count() == 2) {
      checkForMatch();
    }
  }

  private void checkForMatch() {
    // Find the two revealed cards
    Card[] revealedCards = cards.stream().filter(Card::isRevealed).toArray(Card[]::new);

    if (revealedCards.length == 2) {
      // Check if the two cards match
      if (revealedCards[0].matches(revealedCards[1])) {
        // They match, add to permanently flipped cards
        flippedCards.add(revealedCards[0]);
        flippedCards.add(revealedCards[1]);
        score += 10; // Increase score for a match
      } else {
        // They do not match, reset them after a short delay
        TimerTask task = new TimerTask() {
          @Override
          public void run() {
            revealedCards[0].flipDown();
            revealedCards[1].flipDown();
            score -= 2; // Decrease score for an incorrect match
            scoreLabel.setText("Score: " + score); // Update score label
            repaint();
          }
        };
        new Timer().schedule(task, 1000); // Delay for visibility
      }
    }
  }

  private void resetGame() {
    setupGame(); // Resets the game
    timerLabel.setText("Time Left: 5:00");
    setBackground(Color.LIGHT_GRAY); // Reset background color
  }

  private void showMessage(String message) {
    Dialog dialog = new Dialog((Frame) null, "Message", true);
    dialog.setLayout(new BorderLayout());
    dialog.add(new Label(message), BorderLayout.CENTER);
    Button okButton = new Button("OK");
    okButton.addActionListener(e -> dialog.dispose());
    dialog.add(okButton, BorderLayout.SOUTH);
    dialog.setSize(300, 150);
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }
}
