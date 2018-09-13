package cs3500.animator.operations;

import java.util.ArrayList;

import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;

/**
 * An abstract class representing the abstraction of instances of operations that shapes can have.
 */
public abstract class AOperation implements IOperation {
  protected String name;
  protected int fromTime;
  protected int toTime;
  protected ArrayList<IShape> opShapes;

  /**
   * Default constructor.
   *
   * @param fromTime the time at which the operation begins
   * @param toTime   the time at which the operation ends
   */
  protected AOperation(String name, int fromTime, int toTime) {
    if (this.fromTime < 0 || this.toTime < 0) {
      throw new IllegalArgumentException("Invalid times.");
    } else if (this.fromTime > this.toTime) {
      throw new IllegalArgumentException("Invalid times.");
    }
    else {
      this.name = name;
      this.fromTime = fromTime;
      this.toTime = toTime;
      this.opShapes = new ArrayList<>();
    }
  }

  @Override
  public int getFromTime() {
    return fromTime;
  }

  @Override
  public int getToTime() {
    return toTime;
  }

  @Override
  public abstract void command(IShape shape) throws IllegalArgumentException;

  @Override
  public abstract String getDescription(IReadModel model) throws IllegalArgumentException;

  @Override
  public abstract String printSVG();

  @Override
  public String getName() {
    return this.name;
  }
}

