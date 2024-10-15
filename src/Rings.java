import java.awt.Image;

public class Rings extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/rings.png", cardWidth, cardHeight); // Update the path as needed
  }
}
