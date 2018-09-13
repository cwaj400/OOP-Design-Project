package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener class that enables the play and pause functionality.
 */
public class PlayButtonHandler implements ActionListener {
  HybridView view;

  /**
   * Default constructor.
   *
   * @param view the hybrid view to be passed in
   */
  public PlayButtonHandler(HybridView view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (view.getPlayButtonTitle().equalsIgnoreCase("Press to pause")) {
      view.setPlayButtonTitle("Press to play");
    } else {
      view.setPlayButtonTitle("Press to pause");
    }
    this.view.getAnimationPanel().setPauseCounter();
  }
}
