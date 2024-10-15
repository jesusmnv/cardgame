import javax.swing.*;

public class Hexagon extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/hexagon.png", cardWidth, cardHeight); // Update the path as needed
  }
}