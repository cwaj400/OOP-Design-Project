package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener class that speeds up the animation.
 */
public class SpeedUpAnimationHandler implements ActionListener {
  HybridView view;

  /**
   * Default constructor.
   *
   * @param view the hybrid view to be passed in
   */
  public SpeedUpAnimationHandler(HybridView view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.view.setIncreaseOn();
  }
}
