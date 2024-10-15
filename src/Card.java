import java.awt.*;
import java.awt.event.*;

// Card class represents a single card in the game
public class Card extends Button {
  private Object content; // Content can be Shape, Number, or Letter
  private boolean isFlipped = false;
  private boolean isMatched = false;
  private boolean revealed = false; // State of the card (whether permanently revealed)

  // Constructor for shapes
  public Card(Shape shape) {
    this.content = shape;
    initCard();
  }

  // Constructor for numbers
  public Card(int number) {
    this.content = number;
    initCard();
  }

  // Constructor for letters
  public Card(char letter) {
    this.content = letter;
    initCard();
  }

  private void initCard() {
    setPreferredSize(new Dimension(100, 100)); // Set card size
    setFont(new Font("Arial", Font.BOLD, 36)); // Font for text-based content
    setBackground(Color.LIGHT_GRAY); // Background color for the card
    flipDown(); // Initially, the card should be flipped down

    // Add a listener to handle click events for flipping cards
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!isFlipped) {
          flipUp(); // Flip up the card if it's not already flipped
        } else {
          flipDown(); // Flip down the card if it is flipped
        }
      }
    });
  }

  public void flipUp() {
    if (!revealed) { // Ensure not flipping if the card is permanently revealed
      isFlipped = true;
      setBackground(Color.WHITE); // Change background when card is flipped
      repaint(); // Repaint to show the content
    }
  }

  public void flipDown() {
    if (!revealed) { // Ensure not flipping down if the card is permanently revealed
      isFlipped = false;
      setBackground(Color.LIGHT_GRAY); // Reset background color
      setLabel(""); // Clear label to hide content
      repaint(); // Repaint to clear content
    }
  }

  // Method to reveal the card permanently (showing its content)
  public void reveal() {
    revealed = true;
    flipUp(); // When revealed, the card is flipped up and stays up
    setLabel(content.toString()); // Set label to display the content
  }

  // Method to check if the card is permanently revealed
  public boolean isRevealed() {
    return revealed;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    if (isFlipped || revealed) {
      // Draw the content based on its type
      if (content instanceof Shape) {
        ((Shape) content).drawShape(g, getWidth(), getHeight(), this);
      } else if (content instanceof Integer || content instanceof Character) {
        g.setFont(getFont());
        g.setColor(Color.BLACK);
        String text = content.toString();
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - fm.getDescent());
      }
    }
  }

  // Other methods related to card state and matching
  public boolean isFlipped() {
    return isFlipped;
  }

  public boolean isMatched() {
    return isMatched;
  }

  public void setMatched(boolean matched) {
    this.isMatched = matched;
  }

  // Check if the content of two cards matches
  public boolean matches(Card otherCard) {
    if (this.content == null || otherCard.content == null) {
      return false;
    }
    return this.content.equals(otherCard.content);
  }
}
