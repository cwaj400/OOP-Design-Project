package cs3500.animator.util;

/**
 * A class referring to a specific position in the animation.
 */
public class Posn {
  private double x;
  private double y;

  /**
   * Default constructor; sets the given x and y coordinates to be user inputted.
   *
   * @param x the x-coordinate of the position
   * @param y the y-coordinate of the position
   */
  public Posn(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Posn copy() {
    return new Posn(this.getX(), this.getY());
  }

  /**
   * Getter for the x-coordinate of this position.
   *
   * @return the x-coordinate
   */
  public double getX() {
    return this.x;
  }

  /**
   * Getter for the y-coordinate of this position.
   *
   * @return the y-coordinate
   */
  public double getY() {
    return this.y;
  }

  /**
   * Sets the x-coordinate of this position to the given value.
   *
   * @param x the new x-coordinate of this position
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Sets the y-coordinate of this position to the given value.
   *
   * @param y the new y-coordinate of this position
   */
  public void setY(double y) {
    this.y = y;
  }
}
