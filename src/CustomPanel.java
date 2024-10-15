import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;

public class CustomPanel extends Panel {
  private String borderText;

  public CustomPanel(String borderText) {
    this.borderText = borderText;
    setPreferredSize(new Dimension(200, 200)); // Set size for the panel
    setBackground(Color.LIGHT_GRAY); // Set a background color
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    drawBorder(g);
  }

  private void drawBorder(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Draw border rectangle
    g.drawString(borderText, 10, 20); // Draw the border text
  }

  public void setBorderText(String text) {
    this.borderText = text;
    repaint(); // Repaint to update the border text
  }
}
