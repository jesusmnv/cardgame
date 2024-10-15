import java.awt.Image;

public class Oval extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/oval.png", cardWidth, cardHeight); // Update the path as needed
  }
}
