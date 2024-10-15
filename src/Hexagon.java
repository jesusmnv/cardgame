import java.awt.Image;

public class Hexagon extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/hexagon.png", cardWidth, cardHeight); // Update the path as needed
  }
}
