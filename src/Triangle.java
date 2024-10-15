import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

public class Triangle extends Shape {
  @Override
  public void drawShape(Graphics g, int width, int height, Component component) {
    Image image = createImage("/shapes/triangle.png", width, height);
    drawImage(g, image, width, height, component); // Draw the shape's image on the component
  }
}
