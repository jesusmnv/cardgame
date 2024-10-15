import javax.swing.*;

public class Diamond extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/diamond.png", cardWidth, cardHeight); // Update the path as needed
  }
}