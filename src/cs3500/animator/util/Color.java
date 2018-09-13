package cs3500.animator.util;

/**
 * Represents a color.
 */
public class Color {
  private float redval;
  private float greenval;
  private float blueval;

  /**
   * Default constructor.
   *
   * @param redval   the red value in the RGB color code
   * @param greenval the green value in the RGB color code
   * @param blueval  the blue value in the RGB color code
   */
  public Color(float redval, float greenval, float blueval) {
    this.redval = redval;
    this.greenval = greenval;
    this.blueval = blueval;
  }

  /**
   * Getter for this color's red value.
   *
   * @return the red value of this color
   */
  public float getRedval() {
    return this.redval;
  }

  /**
   * Getter for this color's green value.
   *
   * @return the green value of this color
   */
  public float getGreenval() {
    return this.greenval;
  }

  /**
   * Getter for this color's blue value.
   *
   * @return the blue value of this color
   */
  public float getBlueval() {
    return this.blueval;
  }

  /**
   * Setter for this color's red value.
   *
   * @param val the new red value of this color
   */
  public void setRedval(float val) {
    this.redval = val;
  }

  /**
   * Setter for this color's green value.
   *
   * @param val the new green value of this color
   */
  public void setGreenval(float val) {
    this.greenval = val;
  }

  /**
   * Setter for this color's blue value.
   *
   * @param val the new blue value of this color
   */
  public void setBlueval(float val) {
    this.blueval = val;
  }

  /**
   * Creates an RGB representation of this color in a formatted string.
   *
   * @return a formatted RGB representation of this color
   */
  public String getRGB() {
    return "("
            + Float.toString(Math.round(255 * this.getRedval()))
            + ","
            + Float.toString(Math.round(255 * this.getGreenval()))
            + ","
            + Float.toString(Math.round(255 * this.getBlueval()))
            + ")";
  }
}
