package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener class that implements the looping toggler mechanism.
 */
public class LoopingHandler implements ActionListener {
  HybridView view;

  /**
   * Default constructor.
   *
   * @param view the hybrid view to be used
   */
  public LoopingHandler(HybridView view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.view.getAnimationPanel().setLoopCounter();
  }
}
