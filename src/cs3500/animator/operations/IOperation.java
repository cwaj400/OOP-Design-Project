package cs3500.animator.operations;

import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;

/**
 * An interface representing the operations available to animation operations, which implement this
 * interface through extending the abstract class AOperation.
 */
public interface IOperation {
  //void apply(AShape shape);

  /**
   * Stores the specific operation to the given shape.
   *
   * @param shape the shape to be operated on
   *
   * @throws IllegalArgumentException if operation happens at invalid times
   */
  void command(IShape shape) throws IllegalArgumentException;

  /**
   * Gets description of operation on shape.
   *
   * @return a description of an operation on a shape
   *
   * @throws IllegalArgumentException if there is no shape added to the operation
   */
  String getDescription(IReadModel model) throws IllegalArgumentException;

  /**
   * Returns from time.
   *
   * @return time.
   */
  int getFromTime();

  /**
   * Returns to time.
   *
   * @return to time.
   */
  int getToTime();

  /**
   * Returns formatted svg.
   *
   * @return svg.
   */
  String printSVG();

  /**
   * Name of op.
   *
   * @return name of op.
   */
  String getName();
}
