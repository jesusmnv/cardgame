import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class GamePanel extends JPanel {
    private List<Card> cards; // List to hold the cards
    private JButton resetButton;
    private JButton exitButton;
    private JLabel timerLabel;
    private JLabel scoreLabel; // New JLabel for score
    private JComboBox<String> styleComboBox;
    private int score = 0;
    private int revealedCount = 0; // Count of revealed cards
    private Timer timer; // Timer for the countdown
    private int timeLeft = 300; // 5 minutes in seconds
    private boolean gameActive = false;
    private JPanel animatedPanel; // New JPanel for animated graphic

    public GamePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Initialize components
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");
        timerLabel = new JLabel("Time Left: 5:00");
        scoreLabel = new JLabel("Score: 0"); // Initialize score label
        styleComboBox = new JComboBox<>(new String[] { "Numbers", "Letters", "Shapes" });

        // Panel for buttons, timer, and score
        JPanel controlPanel = new JPanel();
        controlPanel.add(styleComboBox);
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);
        controlPanel.add(timerLabel);
        controlPanel.add(scoreLabel); // Add score label to the control panel

        add(controlPanel, BorderLayout.NORTH);

        // Add animated graphic panel
        animatedPanel = new JPanel();
        animatedPanel.setPreferredSize(new Dimension(200, 200)); // Set size for the animated panel
        animatedPanel.setBorder(BorderFactory.createTitledBorder("Animated Graphic"));
        add(animatedPanel, BorderLayout.CENTER); // Place it in the center

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
        styleComboBox.addActionListener(e -> setupGame());

        setupGame(); // Initialize the game
    }

    private void setupGame() {
        cards = CardFactory.createCards((String) styleComboBox.getSelectedItem());
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
                    JOptionPane.showMessageDialog(null, "Time's up! Your score: " + score);
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cardWidth = 200;
        int cardHeight = 250;
        int x = 50, y = 100; // Starting positions for the cards

        // Draw all cards
        for (Card card : cards) {
            card.setBounds(x, y, cardWidth, cardHeight);
            card.add(card); // Use the card's draw method to display it
            x += cardWidth + 10; // Adjust x position for next card

            // Move to the next row after reaching the end of the panel
            if (x + cardWidth > getWidth()) {
                x = 50;
                y += cardHeight + 10;
            }
        }
    }
}
