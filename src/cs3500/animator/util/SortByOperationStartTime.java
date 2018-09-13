package cs3500.animator.util;

import java.util.Comparator;

import cs3500.animator.operations.IOperation;

/**
 * Class that overrides the compare method; used to compare operation start times.
 */
public class SortByOperationStartTime implements Comparator<IOperation> {

  @Override
  public int compare(IOperation o1, IOperation o2) {
    return o1.getFromTime() - o2.getFromTime();
  }
}
