package cs3500.animator.operations;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;
import cs3500.animator.util.Color;

/**
 * Class representing an operation on a shape; changes the color of a shape.
 */
public class ChangeColor extends AOperation {
  private Color color;

  /**
   * Default constructor.
   *
   * @param fromTime the time at which the color change begins
   * @param toTime   the time at which the color change ends
   * @param color    the new color of the shape
   */
  public ChangeColor(String name, int fromTime, int toTime, Color color) {
    super(name, fromTime, toTime);
    this.color = color;
  }

  /**
   * Getter. Returns color.
   *
   * @return color.
   */
  public Color getColor() {
    return this.color;
  }

  @Override
  public void command(IShape shape) throws IllegalArgumentException {
    List<IOperation> opsListOnThisShape = new ArrayList<>();
    for (int i = 0; i < shape.getOperations().size(); i++) {
      if (shape.getOperations().get(i) instanceof ChangeColor) {
        opsListOnThisShape.add(shape.getOperations().get(i));
        if (this.fromTime > shape.getOperations().get(i).getFromTime()
                && this.fromTime < shape.getOperations().get(i).getToTime()) {
          throw new IllegalArgumentException("Cannot call the same animation while in progress.");
        }
      }
    }

    // checks that operation happens after shape appears
    if (this.fromTime <= shape.getAppearTime()) {
      throw new IllegalArgumentException("Cannot animate shape before it appears.");
    }
    // checks that operation happens before shape disappears
    else if (this.fromTime > shape.getDisappearTime()) {
      throw new IllegalArgumentException("Cannot animate shape after it disappears.");
    }
    // adds shape to this operations list of shapes
    // adds this operation to the shape's list of operations
    else {
      this.opShapes.add(shape);
      shape.getOperations().add(this);
    }
  }

  @Override
  public String getDescription(IReadModel model) throws IllegalArgumentException {
    if (this.opShapes.isEmpty()) {
      throw new IllegalArgumentException("This operation has not been used");
    } else {


      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append("Shape ");
      stringBuilder.append(this.opShapes.get(0).getName());
      stringBuilder.append(" changes color from ");
      stringBuilder.append(this.opShapes.get(0).getColor().getRGB());
      stringBuilder.append(" to ");
      stringBuilder.append(this.color.getRGB());
      stringBuilder.append(" from t=");
      stringBuilder.append(Double.toString(this.fromTime / model.getTickRate()));
      stringBuilder.append("s");
      stringBuilder.append(" to t=");
      stringBuilder.append(Double.toString(this.toTime / model.getTickRate()));
      stringBuilder.append("s\n");

      return stringBuilder.toString();
    }
  }


  @Override
  public String printSVG() {
    String result = "";
    result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
            + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
            " attributeName=\"fill\"" +
            " from=\"RGB("
            + Integer.toString(Math.round(255 * this.opShapes.get(0).getColor().getRedval()))
            + ","
            + Integer.toString(Math.round(255 * this.opShapes.get(0).getColor().getGreenval()))
            + ","
            + Integer.toString(Math.round(255 * this.opShapes.get(0).getColor().getBlueval()))
            + ")\" to=\""
            + "RGB("
            + Integer.toString(Math.round(255 * this.color.getRedval())) + ","
            + Integer.toString(Math.round(255 * this.color.getGreenval())) + ","
            + Integer.toString(Math.round(255 * this.color.getBlueval())) + ")"
            + "\" fill=\"freeze\" />\n";
    return result;
  }
}
