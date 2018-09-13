package cs3500.animator.operations;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;

/**
 * Class representing an operation on a shape; changes the width of a shape.
 */
public class Scale extends AOperation {
  private float deltax;
  private float deltay;

  /**
   * Default constructor.
   *
   * @param fromTime the time at which the width change begins
   * @param toTime   the time at which the width change ends
   * @param deltax   the factor by which the width changes
   * @param deltay   the factor by which the height changes
   */
  public Scale(String name, int fromTime, int toTime, float deltax, float deltay) {
    super(name, fromTime, toTime);
    this.deltax = deltax;
    this.deltay = deltay;
  }

  /**
   * Gets the change in x.
   *
   * @return the change in x.
   */
  public float getDeltax() {
    return this.deltax;
  }

  /**
   * Returns the change in y.
   *
   * @return the change in y.
   */
  public float getDeltay() {
    return this.deltay;
  }

  @Override
  public void command(IShape shape) {
    List<IOperation> opsListOnThisShape = new ArrayList<>();
    for (int i = 0; i < shape.getOperations().size(); i++) {
      if (shape.getOperations().get(i) instanceof Scale) {
        opsListOnThisShape.add(shape.getOperations().get(i));
        if (this.fromTime > shape.getOperations().get(i).getToTime()
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
  public String getDescription(IReadModel model) {
    String result = "";
    if (this.opShapes.isEmpty()) {
      throw new IllegalArgumentException("This operation has not been used");
    }
    if (this.opShapes.get(0).getWidth() + this.deltax <= 0
            || this.opShapes.get(0).getHeight() + this.deltay <= 0) {
      throw new IllegalArgumentException("Illegal change in shape dimensions.");
    } else {
      double w = this.opShapes.get(0).getWidth() + this.deltax;
      double h = this.opShapes.get(0).getHeight() + this.deltay;
      result += "Shape " + this.opShapes.get(0).getName()
              + " scales from Width: " + Double.toString(this.opShapes.get(0).getWidth())
              + ", Height: " + Double.toString(this.opShapes.get(0).getHeight())
              + " to Width: " + Double.toString(w)
              + ", Height: " + Double.toString(h)
              + " from t=" + Double.toString(this.fromTime / (double) model.getTickRate()) + "s"
              + " to t=" + Double.toString(this.toTime / (double) model.getTickRate()) + "s\n";
      return result;
    }
  }

  @Override
  public String printSVG() {
    String result = "";

    if (this.opShapes.get(0) instanceof Rectangle) {
      if (deltax == 0 && deltay != 0) {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"height\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getHeight()) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getHeight() + deltay)
                + "\" fill=\"freeze\" />\n";
      }
      if (deltax != 0 && deltay == 0) {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"width\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getWidth()) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getWidth() + deltax)
                + "\" fill=\"freeze\" />\n";
      } else {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"width\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getWidth()) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getWidth() + deltax)
                + "\" fill=\"freeze\" />\n";
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"height\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getHeight()) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getHeight() + deltay)
                + "\" fill=\"freeze\" />\n";
      }
    }

    if (this.opShapes.get(0) instanceof Oval) {
      if (deltax == 0 && deltay != 0) {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"ry\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getHeight() / 2) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getHeight() / 2 + deltay / 2)
                + "\" fill=\"freeze\" />\n";
      }
      if (deltax != 0 && deltay == 0) {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"rx\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getWidth() / 2) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getWidth() / 2 + deltax / 2)
                + "\" fill=\"freeze\" />\n";
      } else {
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\" " +
                "attributeName=\"rx\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getWidth() / 2) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getWidth() / 2 + deltax / 2)
                + "\" fill=\"freeze\" />\n";
        result += "<animate attributeType=\"xml\" begin=\"" + Integer.toString(100 * this.fromTime)
                + "ms\" dur=\"" + Integer.toString(100 * (this.toTime - this.fromTime)) + "ms\"" +
                " attributeName=\"ry\"" +
                " from=\"" + Double.toString(this.opShapes.get(0).getHeight() / 2) + "\" to=\""
                + Double.toString(this.opShapes.get(0).getHeight() / 2 + deltay / 2)
                + "\" fill=\"freeze\" />\n";
      }
    }
    return result;
  }
}
