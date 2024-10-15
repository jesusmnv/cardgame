import javax.swing.*;

public class Rectangle extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/rectangle.png", cardWidth, cardHeight); // Update the path as needed
  }
}