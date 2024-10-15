import javax.swing.*;

public class Square extends Shape {
  @Override
  public Icon getShapeIcon(int cardWidth, int cardHeight) {
    return createIcon("/shapes/square.png", cardWidth, cardHeight); // Update the path as needed
  }
}