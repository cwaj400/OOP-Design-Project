package cs3500.animator.model;

import cs3500.animator.operations.IOperation;
import cs3500.animator.shapes.IShape;

/**
 * Allows the user to edit the data. Generally only used in tests so we can try different adding
 * shapes and operations to the model.
 */
public interface IWriteModel {
  /**
   * Creates an initial shape object in the animation, from where it can perform different actions
   * based on user input.
   *
   * @param shape the shape to be added
   */
  void createShape(IShape shape);

  /**
   * Removes a shape from the animation.
   *
   * @param shape the shape to be removed
   * @throws IllegalArgumentException if there is no shape to remove
   */
  void removeShape(IShape shape) throws IllegalArgumentException;

  /**
   * Creates operation in list in model.
   *
   * @param operation operation.
   */
  void createOperation(IOperation operation);

  /**
   * Sets speed, which is an int in model.
   *
   * @param speed speed.
   */
  void setSpeed(int speed);
}
