import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;

public class Card extends Button {
  private Object content; // Content can be Shape, Number, or Letter
  private boolean isFlipped = false;
  private boolean isMatched = false;
  private boolean revealed = false; // State of the card
  private Image shapeImage; // To hold shape icon

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
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    if (isFlipped) {
      if (shapeImage != null) {
        g.drawImage(shapeImage, 0, 0, getWidth(), getHeight(), this);
      } else {
        g.setColor(Color.BLACK);
        String text = content.toString();
        g.drawString(text, (getWidth() - g.getFontMetrics().stringWidth(text)) / 2,
            (getHeight() + g.getFontMetrics().getAscent()) / 2);
      }
    }
  }

  public void flipUp() {
    if (content instanceof Shape) {
      Shape shape = (Shape) content;
      shapeImage = shape.getShapeImage(getWidth(), getHeight()); // Get the shape icon
    } else {
      shapeImage = null; // No icon for text-based content
    }
    isFlipped = true;
    setBackground(Color.WHITE); // Change background when card is flipped
    repaint(); // Refresh the card to show the content
  }

  public void flipDown() {
    shapeImage = null; // Remove any shape icon
    isFlipped = false;
    setBackground(Color.LIGHT_GRAY); // Reset background color
    repaint(); // Refresh the card to hide the content
  }

  // Method to reveal the card (permanently showing its content)
  public void reveal() {
    revealed = true;
    flipUp(); // When revealed, flip the card up
  }

  // Method to check if the card is revealed
  public boolean isRevealed() {
    return revealed;
  }

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
