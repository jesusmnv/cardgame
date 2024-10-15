import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Shape {
  protected BufferedImage image;

  public abstract Icon getShapeIcon(int width, int height);

  protected Icon createIcon(String imagePath, int width, int height) {
    try {
      image = ImageIO.read(getClass().getResourceAsStream(imagePath));
      // Resize the image to fit the card size
      Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      return new ImageIcon(scaledImage);
    } catch (IOException e) {
      e.printStackTrace();
      return null; // Handle error appropriately
    }
  }
}
