package cs3500.animator.shapes;

import cs3500.animator.operations.ChangeColor;
import cs3500.animator.util.Color;
import cs3500.animator.operations.IOperation;
import cs3500.animator.operations.Move;
import cs3500.animator.util.Posn;
import cs3500.animator.operations.Scale;

/**
 * Represents an oval shape.
 */
public class Oval extends AShape {
  private String type;

  /**
   * Default constructor.
   *
   * @param name          the name of the oval
   * @param posn          the current position of the oval
   * @param color         the color of the oval
   * @param appearTime    the time that the oval appears in the animation
   * @param disappearTime the time that the oval disappears in the animation
   * @param xradius       the radius in the x-direction of the oval
   * @param yradius       the radius in the y-direction of the oval
   */
  public Oval(String name, Posn posn, Color color, int appearTime,
              int disappearTime, double xradius, double yradius) {
    super(name, posn, color, appearTime, disappearTime, xradius, yradius);
    this.type = "oval";
  }

  @Override
  protected void changeWidth(double factor) throws IllegalArgumentException {
    if ((this.width * 2) + factor <= 0) {
      throw new IllegalArgumentException("Width must be positive.");
    } else {
      this.width += factor / 2;
    }
  }

  @Override
  protected void changeHeight(double factor) throws IllegalArgumentException {
    if ((this.height * 2) + factor <= 0) {
      throw new IllegalArgumentException("Height must be positive.");
    } else {
      this.height += factor / 2;
    }
  }

  @Override
  public double getWidth() {
    return this.width * 2;
  }

  @Override
  public double getHeight() {
    return this.height * 2;
  }
  //TODO: why multiply by 2

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public String getDescription() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Center: (");
    stringBuilder.append(Double.toString(this.posn.getX()));
    stringBuilder.append(",");
    stringBuilder.append(Double.toString(this.posn.getY()));
    stringBuilder.append("), ");
    stringBuilder.append("X radius: ");
    stringBuilder.append(Double.toString(this.width));
    stringBuilder.append(", Y radius: ");
    stringBuilder.append(Double.toString(this.height));
    stringBuilder.append(", Color: RGB(");
    stringBuilder.append(Double.toString(this.color.getRedval()));
    stringBuilder.append(", ");
    stringBuilder.append(Double.toString(this.color.getGreenval()));
    stringBuilder.append(", ");
    stringBuilder.append(Double.toString(this.color.getBlueval()));
    stringBuilder.append(")\n");

    return stringBuilder.toString();
  }


  @Override
  public String printSVG() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("<ellipse id=\"");
    stringBuilder.append(this.getName());
    stringBuilder.append("\" cx=\"");
    stringBuilder.append(Double.toString(this.getPosn().getX()));
    stringBuilder.append("\" cy=\"");
    stringBuilder.append(Double.toString(this.getPosn().getY()));
    stringBuilder.append("\" rx=\"");
    stringBuilder.append(Double.toString(this.getWidth() / 2));
    stringBuilder.append("\" ry=\"");
    stringBuilder.append(Double.toString(this.getHeight() / 2));
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

    stringBuilder.append("\n</ellipse>\n");

    return stringBuilder.toString();
  }


  @Override
  public IShape getStateAt(int time) {

    Oval r = new Oval(this.name, this.posn, this.color, this.appearTime,
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
        if (time >= o.getFromTime() && time < o.getToTime()) {
          r.width = this.tweenWidth((Scale) o, r.width, r.width +
                  (((Scale) o).getDeltay() / 2), time);
          r.height = this.tweenHeight((Scale) o, r.height, r.height +
                  (((Scale) o).getDeltay() / 2), time);
        }
        if (time >= o.getToTime()) {
          r.width = this.tweenWidth((Scale) o, r.width, r.width +
                  (((Scale) o).getDeltay() / 2), o.getToTime());
          r.height = this.tweenHeight((Scale) o, r.height, r.height +
                  (((Scale) o).getDeltay() / 2), o.getToTime());
        }
      }
    }
    return r;
  }

  @Override
  public String getSymbol() {
    return "c";
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
            + (afterPosn.getX() * ((double) (time - m.getFromTime()) / (m.getToTime()
            - m.getFromTime())))),

            (beforePosn.getY() * ((double) (m.getToTime() - time) / (m.getToTime()
                    - m.getFromTime()))
                    + (afterPosn.getY() * ((double) (time - m.getFromTime()) /
                    (m.getToTime() - m.getFromTime())))));
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
    return new Color(
            (beforeColor.getRedval() * ((float) (c.getToTime() - time) / (c.getToTime()
                    - c.getFromTime()))
                    + (afterColor.getRedval() * ((float) (time - c.getFromTime()) /
                    (c.getToTime() - c.getFromTime())))),

            (beforeColor.getGreenval() * ((float) (c.getToTime() - time) / (c.getToTime()
                    - c.getFromTime()))
                    + (afterColor.getGreenval() * ((float) (time - c.getFromTime())
                    / (c.getToTime() - c.getFromTime())))),

            (beforeColor.getBlueval() * ((float) (c.getToTime() - time) / (c.getToTime()
                    - c.getFromTime()))
                    + (afterColor.getBlueval() * ((float) (time - c.getFromTime())
                    / (c.getToTime() - c.getFromTime())))));
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
            + (afterWidth * ((double) (time - s.getFromTime()) / (s.getToTime()
            - s.getFromTime()))));
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
            + (afterHeight * ((double) (time - s.getFromTime()) / (s.getToTime()
            - s.getFromTime()))));
  }
}
