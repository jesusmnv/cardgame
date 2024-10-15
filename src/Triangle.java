import java.awt.Image;

public class Triangle extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/triangle.png", cardWidth, cardHeight); // Update the path as needed
  }
}
