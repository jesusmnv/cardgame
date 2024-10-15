import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class Card extends JButton {
  private Object content; // Content can be Shape, Number, or Letter
  private boolean isFlipped = false;
  private boolean isMatched = false;
  private boolean revealed; // State of the card

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
    setHorizontalAlignment(SwingConstants.CENTER); // Center text or icon
    flipDown(); // Initially, the card should be flipped down
  }

  public void flipUp() {
    if (content instanceof Shape) {
      Shape shape = (Shape) content;
      setIcon(shape.getShapeIcon(getWidth(), getHeight())); // Pass the card's current size
      setText(""); // Clear text when shape is shown
    } else {
      setIcon(null); // No icon for text-based content
      setText(content.toString()); // Display the content (number or letter)
      setForeground(Color.BLACK); // Set color for number or letter
    }
    isFlipped = true;
    setBackground(Color.WHITE); // Change background when card is flipped
  }

  public void flipDown() {
    setIcon(null); // Remove any shape icon
    setText(""); // Clear text
    setBackground(Color.LIGHT_GRAY); // Reset background color
    isFlipped = false;
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
