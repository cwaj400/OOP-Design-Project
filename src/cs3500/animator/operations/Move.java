package cs3500.animator.operations;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;
import cs3500.animator.util.Posn;

/**
 * Class representing an operation on a shape; moves a shape to a destination position.
 */
public class Move extends AOperation {
  private Posn destination;

  /**
   * Default constructor.
   *
   * @param fromTime    the time at which the move begins
   * @param toTime      the time at which the move  ends
   * @param destination the destination position of the shape
   */
  public Move(String name, int fromTime, int toTime, Posn destination) {
    super(name, fromTime, toTime);
    this.destination = destination;
  }

  @Override
  public void command(IShape shape) throws IllegalArgumentException {
    List<IOperation> opsListOnThisShape = new ArrayList<>();
    for (int i = 0; i < shape.getOperations().size(); i++) {
      if (shape.getOperations().get(i) instanceof Move) {
        opsListOnThisShape.add(shape.getOperations().get(i));
        if (this.fromTime > shape.getOperations().get(i).getFromTime()
                && this.fromTime < shape.getOperations().get(i).getToTime()) {
          throw new IllegalArgumentException("Cannot call the same animation while in progress.");
        }
      }
    }

    // checks that operation happens after shape appears
    if (this.fromTime < shape.getAppearTime()) {
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
      String result = "";
      result += "Shape " + this.opShapes.get(0).getName()
              + " moves from (" + this.opShapes.get(0).getPosn().getX() + ","
              + this.opShapes.get(0).getPosn().getY() + ")"
              + " to " + "(" + Double.toString(this.destination.getX()) + ","
              + Double.toString(this.destination.getY()) + ") "
              + "from t=" + Double.toString(100 * (this.fromTime / model.getTickRate())) + "ms"
              + " to t=" + Double.toString(100 * (this.toTime / model.getTickRate())) + "ms\n";

      return result;
    }
  }

  @Override
  public String printSVG() {
    String result = "";

    // move horizontal
    if (this.destination.getX() == this.opShapes.get(0).getPosn().getX()
            && this.destination.getY() != this.opShapes.get(0).getPosn().getY()) {
      result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
              + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
              " attributeName=\"" + this.opShapes.get(0).getSymbol() + "x\"" +
              " from=\"" + Integer.toString((int) this.opShapes.get(0).getPosn().getX()) +
              "\" to=\""
              + Integer.toString((int) this.destination.getX())
              + "\" fill=\"freeze\" />\n";
    }
    if (this.destination.getX() != this.opShapes.get(0).getPosn().getX()
            && this.destination.getY() == this.opShapes.get(0).getPosn().getY()) {
      result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
              + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\" " +
              "attributeName=\"" + this.opShapes.get(0).getSymbol() + "y\"" +
              " from=\"" + Integer.toString((int) this.opShapes.get(0).getPosn().getY())
              + "\" to=\""
              + Integer.toString((int) this.destination.getY())
              + "\" fill=\"freeze\" />\n";
    } else {
      result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
              + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
              " attributeName=\"" + this.opShapes.get(0).getSymbol() + "x\"" +
              " from=\"" + Integer.toString((int) this.opShapes.get(0).getPosn().getX()) +
              "\" to=\""
              + Integer.toString((int) this.destination.getX())
              + "\" fill=\"freeze\" />\n";
      result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
              + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\" " +
              "attributeName=\"" + this.opShapes.get(0).getSymbol() + "y\"" +
              " from=\"" + Integer.toString((int) this.opShapes.get(0).getPosn().getY()) +
              "\" to=\""
              + Integer.toString((int) this.destination.getY())
              + "\" fill=\"freeze\" />\n";
    }
    return result;
  }

  /**
   * Gets destination.
   *
   * @return dest.
   */
  public Posn getDestination() {
    return this.destination;
  }
}
