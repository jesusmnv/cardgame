import java.awt.Image;

public class Pentagon extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/pentagon.png", cardWidth, cardHeight); // Update the path as needed
  }
}
