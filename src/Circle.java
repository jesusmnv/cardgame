import javax.swing.*;

public class Circle extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/circle.png", cardWidth, cardHeight); // Update the path as needed
  }
}