package cs3500.animator.view;

import java.awt.Color;

import java.awt.Graphics;

import javax.swing.JPanel;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.shapes.IShape;

/**
 * This panel represents the region where the animation will occur. An instance of a
 * JPanel in Java Swing.
 */
public class AnimationPanel extends JPanel {
  private IAnimationModel model;
  private int time;
  private int pauseCounter;
  private int loopCounter;


  /**
   * Animation panel creator. Default constructor.
   */
  public AnimationPanel() {
    super();
    this.setBackground(Color.WHITE);
    this.time = 0;
    this.pauseCounter = 0;
    this.loopCounter = 0;

  }

  /**
   * Model setter.
   *
   * @param m model.
   */
  public void setModel(IAnimationModel m) {
    this.model = m;
  }

  /**
   * Getter for pause counter.
   *
   * @return the current pause count
   */
  public int getPauseCounter() {
    return this.pauseCounter;
  }

  /**
   * Getter for the loop counter.
   *
   * @return the current loop count
   */
  public int getLoopCounter() {
    return this.loopCounter;
  }

  /**
   * Increases the pauseCounter variable. Used to pause and play the animation.
   */
  public void setPauseCounter() {
    this.pauseCounter++;
  }

  /**
   * Increases the loopCounter variable. Used to toggle looping in the animation.
   */
  public void setLoopCounter() {
    this.loopCounter++;
  }

  /**
   * Setter for time.
   *
   * @param time the time to change to
   */
  public void setTime(int time) {
    this.time = time;
  }

  /**
   * Getter for the model.
   *
   * @return the model set up
   */
  public IAnimationModel getModel() {
    return this.model;
  }

  /**
   * Override of the paintComponent method. Updates the frame to hold shapes at correct positions at
   * each tick.
   *
   * @param g graphic
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (IShape s : model.getShapes()) {
      if (s.getType().equals("oval")) {
        if (time >= s.getAppearTime() && time < s.getDisappearTime()) {
          s = s.getStateAt(time);

          g.setColor(new Color(s.getColor().getRedval(), s.getColor().getGreenval(),
                  s.getColor().getBlueval()));

          g.fillOval((int) s.getPosn().getX(), (int) s.getPosn().getY(), (int)
                  s.getWidth(), (int) s.getHeight());
        }
      }

      if (s.getType().equals("rectangle")) {
        if (time >= s.getAppearTime() && time < s.getDisappearTime()) {
          s = s.getStateAt(time);

          g.setColor(new Color(s.getColor().getRedval(), s.getColor().getGreenval(),
                  s.getColor().getBlueval()));
          g.fillRect((int) s.getPosn().getX(), (int) s.getPosn().getY(),
                  (int) s.getWidth(), (int) s.getHeight());
        }
      }
    }

    if (this.loopCounter % 2 != 0 && time > model.getEndTime()) {
      this.time = 0;
    }

    if (this.pauseCounter % 2 == 0) {
      this.time++;
    }
  }

  /**
   * Returns the current time.
   *
   * @return current time
   */
  public int getTime() {
    return this.time;
  }

  /**
   * Increase time by factor of 50. Useful for future fast forward.
   */
  public void increaseTime() {
    this.time = time + (this.model.getTickRate() / 10);
  }

  /**
   * Decrease time by factor of -5. Useful for future rewind function.
   */
  public void decreaseTime() {
    this.time = time - 5;
  }
}
