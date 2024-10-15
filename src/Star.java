import java.awt.Image;

public class Star extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/star.png", cardWidth, cardHeight); // Update the path as needed
  }
}
