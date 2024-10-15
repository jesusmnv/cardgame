import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Shape {
  protected BufferedImage image;

  // Method to get the shape icon as an Image
  public abstract Image getShapeImage(int width, int height);

  // Create an Image from the given path and resize it
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
}
