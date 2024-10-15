import java.awt.Image;

public class Rectangle extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/rectangle.png", cardWidth, cardHeight); // Update the path as needed
  }
}
