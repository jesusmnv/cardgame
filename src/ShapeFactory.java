public class ShapeFactory {
  public static Shape getShape(String shapeType) {
    switch (shapeType) {
      case "Circle":
        return new Circle();
      case "Square":
        return new Square();
      case "Triangle":
        return new Triangle();
      case "Star":
        return new Star();
      case "Hexagon":
        return new Hexagon(); // You can define similar logic for Hexagon and other shapes
      case "Pentagon":
        return new Pentagon();
      case "Diamond":
        return new Diamond();
      case "Heart":
        return new Rings();
      case "Oval":
        return new Oval();
      case "Rectangle":
        return new Rectangle();
      default:
        throw new IllegalArgumentException("Unknown shape type: " + shapeType);
    }
  }
}
