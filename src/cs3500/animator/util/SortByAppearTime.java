package cs3500.animator.util;

import java.util.Comparator;

import cs3500.animator.shapes.IShape;

/**
 * Class that overrides the compare method; used to compare shape appearance times.
 */
public class SortByAppearTime implements Comparator<IShape> {

  @Override
  public int compare(IShape a1, IShape a2) {
    return a1.getAppearTime() - a2.getAppearTime();
  }
}