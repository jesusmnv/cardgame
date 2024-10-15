import javax.swing.*;

public class Star extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/star.png", cardWidth, cardHeight); // Update the path as needed
  }
}