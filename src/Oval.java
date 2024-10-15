import javax.swing.*;

public class Oval extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/oval.png", cardWidth, cardHeight); // Update the path as needed
  }
}