import java.awt.Image;

public class Circle extends Shape {
  @Override
  public Image getShapeImage(int cardWidth, int cardHeight) {
    return createImage("/shapes/circle.png", cardWidth, cardHeight); // Update the path as needed
  }
}
