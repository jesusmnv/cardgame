import java.awt.Image;

public class Diamond extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/diamond.png", cardWidth, cardHeight); // Update the path as needed
  }
}
