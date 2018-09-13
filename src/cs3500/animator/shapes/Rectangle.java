package cs3500.animator.shapes;

import cs3500.animator.operations.ChangeColor;
import cs3500.animator.util.Color;
import cs3500.animator.operations.IOperation;
import cs3500.animator.operations.Move;
import cs3500.animator.util.Posn;
import cs3500.animator.operations.Scale;

/**
 * Represents a rectangle shape.
 */
public class Rectangle extends AShape {
  private String type;

  /**
   * Default constructor for a rectangle.
   *
   * @param name          the name of the rectangle
   * @param posn          the current position of the rectangle
   * @param color         the initial color of the rectangle
   * @param appearTime    the time that the rectangle appears in the animation
   * @param disappearTime the time that the rectangle disappears in the animation
   * @param width         the width of a rectangle
   * @param height        the height of a rectangle
   */
  public Rectangle(String name, Posn posn, Color color,
                   int appearTime, int disappearTime, double width, double height) {
    super(name, posn, color, appearTime, disappearTime, width, height);
    this.type = "rectangle";
  }

  @Override
  protected void changeWidth(double factor) throws IllegalArgumentException {
    if (this.width + factor <= 0) {
      throw new IllegalArgumentException("Width must be positive.");
    } else {
      this.width += factor;
    }
  }

  @Override
  protected void changeHeight(double factor) throws IllegalArgumentException {
    if (this.height + factor <= 0) {
      throw new IllegalArgumentException("Height must be positive.");
    } else {
      this.height += factor;
    }
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public String getDescription() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Corner: (");
    stringBuilder.append(Double.toString(this.posn.getX()));
    stringBuilder.append(",");
    stringBuilder.append(Double.toString(this.posn.getY()));
    stringBuilder.append("), ");
    stringBuilder.append("Width: ");
    stringBuilder.append(Double.toString(this.width));
    stringBuilder.append(", Height: ");
    stringBuilder.append(Double.toString(this.height));
    stringBuilder.append(", Color: RGB(");
    stringBuilder.append(Integer.toString(Math.round(255 * this.color.getRedval())));
    stringBuilder.append(", ");
    stringBuilder.append(Integer.toString(Math.round(255 * this.color.getGreenval())));
    stringBuilder.append(", ");
    stringBuilder.append(Integer.toString(Math.round(255 * this.color.getBlueval())));
    stringBuilder.append(")\n");

    return stringBuilder.toString();
  }


  @Override
  public String printSVG() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("<rect id=\"");
    stringBuilder.append(this.getName());
    stringBuilder.append("\" x=\"");
    stringBuilder.append(Double.toString(this.getPosn().getX()));
    stringBuilder.append("\" y=\"");
    stringBuilder.append(Double.toString(this.getPosn().getY()));
    stringBuilder.append("\" width=\"");
    stringBuilder.append(Double.toString(this.getWidth()));
    stringBuilder.append("\" height=\"");
    stringBuilder.append(Double.toString(this.getHeight()));
    stringBuilder.append("\" fill=\"RGB(");

    stringBuilder.append(Integer.toString(Math.round(this.getColor().getRedval() * 255)));
    stringBuilder.append(",");
    stringBuilder.append(Integer.toString(Math.round(this.getColor().getGreenval() * 255)));
    stringBuilder.append(",");
    stringBuilder.append(Integer.toString(Math.round(this.getColor().getBlueval() * 255)));
    stringBuilder.append(")\" visibility=\"visible\" >\n");

    for (IOperation o : this.getOperations()) {
      stringBuilder.append(o.printSVG());
    }
    stringBuilder.append("\n</rect>\n");
    return stringBuilder.toString();
  }


  @Override
  public IShape getStateAt(int time) {
    Rectangle r = new Rectangle(this.name, this.posn, this.color, this.appearTime,
            this.disappearTime, this.width, this.height);


    for (IOperation o : this.operationsList) {

      if (o instanceof Move) {
        if (time >= o.getFromTime() && time < o.getToTime()) {
          r.posn = r.tweenPosn((Move) o, r.posn, ((Move) o).getDestination(), time);
        }
        if (time >= o.getToTime()) {
          r.posn = r.tweenPosn((Move) o, r.posn, ((Move) o).getDestination(), o.getToTime());
        }
      }

      if (o instanceof ChangeColor) {
        if (time >= o.getFromTime() && time < o.getToTime()) {
          r.color = r.tweenColor((ChangeColor) o, r.color, ((ChangeColor) o).getColor(), time);
        }
        if (time >= o.getToTime()) {
          r.color = r.tweenColor((ChangeColor) o, r.color, ((ChangeColor) o).getColor(),
                  o.getToTime());
        }
      }

      if (o instanceof Scale) {
        if (time > o.getFromTime() && time < o.getToTime()) {
          r.width = r.tweenWidth((Scale) o, r.width, r.width + ((Scale) o).getDeltay(),
                  time);
          r.height = r.tweenHeight((Scale) o, r.height, r.height +
                  ((Scale) o).getDeltay(), time);
        }
        if (time >= o.getToTime()) {
          r.width = r.tweenWidth((Scale) o, r.width, r.width +
                  ((Scale) o).getDeltay(), o.getToTime());
          r.height = r.tweenHeight((Scale) o, r.height, r.height +
                  ((Scale) o).getDeltay(), o.getToTime());
        }
      }
    }

    return r;
  }

  @Override
  public String getSymbol() {
    return "";
  }

  /**
   * Tween method for a shape's position. Creates a new position based on the input time and
   * destination of the shape after the move operation has been called.
   *
   * @param m          the move operation
   * @param beforePosn the position before the move has occurred
   * @param afterPosn  the destination of the shape
   * @param time       current time
   *
   * @return designated position at time t
   */
  private Posn tweenPosn(Move m, Posn beforePosn, Posn afterPosn, int time) {

    return new Posn((beforePosn.getX() * ((double) (m.getToTime() - time) /
            (m.getToTime() - m.getFromTime()))
            + (afterPosn.getX() * ((double) (time - m.getFromTime()) /
            (m.getToTime() - m.getFromTime())))),

            (beforePosn.getY() * ((double) (m.getToTime() - time) /
                    (m.getToTime() - m.getFromTime()))
                    + (afterPosn.getY() * ((double) (time - m.getFromTime())
                    / (m.getToTime() - m.getFromTime())))));
  }

  /**
   * Tween method for the color of a shape. Creates a new color based on the input time and desired
   * resultant color after the changeColor operation has been called.
   *
   * @param c           the changeColor operation
   * @param beforeColor the color before the operation
   * @param afterColor  the color after the operation
   * @param time        current time
   *
   * @return designated color at time t
   */
  private Color tweenColor(ChangeColor c, Color beforeColor, Color afterColor, int time) {

    return new Color((beforeColor.getRedval() + ((afterColor.getRedval() - beforeColor.getRedval())
            * (time
            - c.getFromTime()) / (c.getToTime() - c.getFromTime()))),

            (beforeColor.getGreenval() + ((afterColor.getGreenval() - beforeColor.getGreenval())
                    * (time
                    - c.getFromTime()) / (c.getToTime() - c.getFromTime()))),
            (beforeColor.getBlueval() + ((afterColor.getBlueval() - beforeColor.getBlueval())
                    * (time
                    - c.getFromTime()) / (c.getToTime() - c.getFromTime()))));
  }

  /**
   * Tween method for the width of a shape. Creates a new width based on the input time and desired
   * width after the scale operation has been called.
   *
   * @param s           the scale operation
   * @param beforeWidth the width before the operation
   * @param afterWidth  the width after the operation
   * @param time        the current time
   *
   * @return designated width at time t
   */
  private double tweenWidth(Scale s, double beforeWidth, double afterWidth, int time) {
    return (beforeWidth * ((double) (s.getToTime() - time) / (s.getToTime() - s.getFromTime()))
            + (afterWidth * ((double) (time - s.getFromTime()) /
            (s.getToTime() - s.getFromTime()))));
  }

  /**
   * Tween method for the height of a shape. Creates a new height based on the input time and
   * desired height after the scale operation has been called.
   *
   * @param s            the scale operation
   * @param beforeHeight the width before the operation
   * @param afterHeight  the width after the operation
   * @param time         the current time
   *
   * @return designated width at time t
   */
  private double tweenHeight(Scale s, double beforeHeight, double afterHeight, int time) {
    return (beforeHeight * ((double) (s.getToTime() - time) / (s.getToTime() - s.getFromTime()))
            + (afterHeight * ((double) (time - s.getFromTime()) /
            (s.getToTime() - s.getFromTime()))));
  }
}