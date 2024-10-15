import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardFactory {

  public static List<Card> createCards(String selectedStyle) {
    List<Card> cards = new ArrayList<>();

    if (selectedStyle.equals("Shapes")) {
      String[] shapeTypes = { "Circle", "Square", "Triangle", "Star", "Hexagon",
          "Pentagon", "Diamond", "Rings", "Oval", "Rectangle" };

      for (String shapeType : shapeTypes) {
        Shape shape = ShapeFactory.getShape(shapeType);
        cards.add(new Card(shape));
        cards.add(new Card(shape)); // Add matching pair
      }

    } else if (selectedStyle.equals("Numbers")) {
      for (int i = 1; i <= 10; i++) {
        cards.add(new Card(i));
        cards.add(new Card(i)); // Add matching pair
      }
    } else if (selectedStyle.equals("Letters")) {
      char letter = 'A';
      for (int i = 0; i < 10; i++) {
        cards.add(new Card(letter));
        cards.add(new Card(letter)); // Add matching pair
        letter++;
      }
    }

    Collections.shuffle(cards); // Shuffle the cards before returning
    return cards;
  }
}
