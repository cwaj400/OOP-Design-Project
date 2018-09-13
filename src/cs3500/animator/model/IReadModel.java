package cs3500.animator.model;

import java.util.ArrayList;

import cs3500.animator.shapes.IShape;

/**
 * interface model, used so a user cannot edit the data.
 */
public interface IReadModel {

  /**
   * This method first describes the shapes that are part of the animation and their details. Next
   * it describes how these shapes will move as the animation proceeds from start to finish.
   *
   * @return the state of the animation as a string
   */
  String printAnimationState();

  /**
   * Returns a copy of all the shapes in the animation.
   *
   * @return a copy of the shapes in the animation
   */
  ArrayList<IShape> getShapes();

  /**
   * Returns tick in model.
   *
   * @return tick.
   */
  int getTickRate();

  /**
   * Returns the end time of the animation, by checking for the last operation or shape exit times.
   *
   * @return the end time of the animation
   */
  int getEndTime();
}
