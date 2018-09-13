package cs3500.animator.shapes;

import java.util.ArrayList;

import cs3500.animator.util.Color;
import cs3500.animator.operations.IOperation;
import cs3500.animator.util.Posn;

/**
 * Interface for IShape, extended by AShape. Represents a shape.
 */
public interface IShape {

  /**
   * Getter for the name of the shape.
   *
   * @return the name of this shape
   */
  String getName();

  /**
   * Getter for the type of the shape.
   *
   * @return the type of this shape
   */
  String getType();

  /**
   * Getter for the position of the shape.
   *
   * @return the position of this shape
   */
  Posn getPosn();

  /**
   * Getter for the color of the shape.
   *
   * @return the color of this shape
   */
  Color getColor();

  /**
   * Getter for the appearance time of the shape.
   *
   * @return the appearance time of this shape
   */
  int getAppearTime();

  /**
   * Getter for the disappear of the shape.
   *
   * @return the disappear time of this shape
   */
  int getDisappearTime();

  /**
   * Getter for the list of operations on the shape.
   *
   * @return the list of operations for this shape
   */
  ArrayList<IOperation> getOperations();

  /**
   * Creates a description of this shape, used in the printAnimationState() method in the
   * implementation class of the IAnimationModel interface.
   *
   * @return a string describing this shape
   */
  String getDescription();

  /**
   * Getter for the width of the shape.
   *
   * @return the width of this shape
   */
  double getWidth();

  /**
   * Getter for the height of the shape.
   *
   * @return the height of this shape
   */
  double getHeight();

  /**
   * Getter svg.
   *
   * @return svg.
   */
  String printSVG();

  /**
   * Return state at time.
   *
   * @param t time.
   *
   * @return state at time.
   */
  IShape getStateAt(int t);

  /**
   * Getting type of shape, either c or r, used for moving shape and formatting SVG.
   *
   * @return either "c" or "r". Helper for printing SVG.
   */
  String getSymbol();
}
