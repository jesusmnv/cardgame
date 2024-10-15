import javax.swing.*;

public class Pentagon extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/pentagon.png", cardWidth, cardHeight); // Update the path as needed
  }
}