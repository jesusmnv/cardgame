import javax.swing.*;

public class Triangle extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/triangle.png", cardWidth, cardHeight); // Update the path as needed
  }
}