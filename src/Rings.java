import javax.swing.*;

public class Rings extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/rings.png", cardWidth, cardHeight); // Update the path as needed
  }
}