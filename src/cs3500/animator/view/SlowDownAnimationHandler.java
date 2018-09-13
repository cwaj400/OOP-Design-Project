package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener class that slows the animation down.
 */
public class SlowDownAnimationHandler implements ActionListener {
  HybridView view;

  /**
   * Default constructor.
   *
   * @param view the hybrid view to be passed in.
   */
  public SlowDownAnimationHandler(HybridView view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.view.setDecreaseOn();
  }
}
