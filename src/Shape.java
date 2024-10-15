import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Shape {
  protected BufferedImage image;

  // Abstract method for drawing the shape, replacing Icon
  public abstract void drawShape(Graphics g, int width, int height, Component component);

  // Method to load and scale an image, replacing ImageIcon
  protected Image createImage(String imagePath, int width, int height) {
    try {
      image = ImageIO.read(getClass().getResourceAsStream(imagePath));
      // Resize the image to fit the card size
      return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    } catch (IOException e) {
      e.printStackTrace();
      return null; // Handle error appropriately
    }
  }

  // Common method to draw the loaded image onto a component
  protected void drawImage(Graphics g, Image image, int width, int height, Component component) {
    if (image != null) {
      g.drawImage(image, 0, 0, width, height, component); // Draw the image at the specified size
    }
  }
}
