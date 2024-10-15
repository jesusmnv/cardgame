import java.awt.Image;

public class Square extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/square.png", cardWidth, cardHeight); // Update the path as needed
  }
}
