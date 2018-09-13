package cs3500.animator.shapes;

import java.util.ArrayList;

import cs3500.animator.util.Color;
import cs3500.animator.operations.IOperation;
import cs3500.animator.util.Posn;

/**
 * An abstract class representing different types of shapes. Contains multiple pieces of information
 * about each shape. Classes extending this class are examples of shapes.
 */
public abstract class AShape implements IShape {
  protected String name;
  protected Posn posn;
  protected Color color;
  protected int appearTime;
  protected int disappearTime;
  protected ArrayList<IOperation> operationsList;
  protected double width;
  protected double height;

  /**
   * Default constructor.
   *
   * @param name          the name of the shape
   * @param posn          the current position of the shape
   * @param color         the initial color of the shape
   * @param appearTime    the time that the shape appears in the animation
   * @param disappearTime the time that the shape disappears in the animation
   */
  protected AShape(String name,
                   Posn posn, Color color, int appearTime, int disappearTime,
                   double width, double height) {
    this.name = name;
    this.posn = posn;
    this.color = color;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.operationsList = new ArrayList<>();
    this.width = width;
    this.height = height;
  }

  /**
   * Moves a shape to a new position.
   *
   * @param destination the new position of the shape
   */
  protected void move(Posn destination) {
    this.posn.setX(destination.getX());
    this.posn.setY(destination.getY());
  }

  /**
   * Changes the width of a shape by a given factor.
   *
   * @param factor the change in width
   *
   * @throws IllegalArgumentException if the new width is not positive
   */
  protected abstract void changeWidth(double factor) throws IllegalArgumentException;

  /**
   * Changes the height of a shape by a given factor.
   *
   * @param factor the change in height
   *
   * @throws IllegalArgumentException if the new height is not positive
   */
  protected abstract void changeHeight(double factor) throws IllegalArgumentException;

  public String getName() {
    return this.name;
  }

  public Posn getPosn() {
    return this.posn;
  }

  public Color getColor() {
    return this.color;
  }

  public int getAppearTime() {
    return this.appearTime;
  }

  public int getDisappearTime() {
    return this.disappearTime;
  }

  public ArrayList<IOperation> getOperations() {
    return this.operationsList;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  abstract public String getDescription();

  @Override
  abstract public String printSVG();

  @Override
  abstract public IShape getStateAt(int t);
}
