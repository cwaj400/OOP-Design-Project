package cs3500.animator.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.WindowConstants;
import javax.swing.JScrollPane;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.BorderLayout;

import cs3500.animator.model.IAnimationModelImpl;
import cs3500.animator.model.IReadModel;

import java.awt.Dimension;

/**
 * Implementation of the visual view animation. Creates a visual of the animation in action.
 */
public class VisualView extends JFrame implements IAnimationView, ActionListener {
  private AnimationPanel animationPanel;

  /**
   * Visual view.
   */
  public VisualView() {
    super();
    this.animationPanel = new AnimationPanel();
    this.setTitle("Animation created by Kabir and Will!");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    animationPanel.setPreferredSize(new Dimension(IAnimationModelImpl.WIDTH,
            IAnimationModelImpl.HEIGHT));
    this.add(animationPanel, BorderLayout.CENTER);

    JScrollPane scrollBar = new JScrollPane(this.animationPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    this.add(scrollBar);
    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public AnimationPanel getAnimationPanel() {
    return this.animationPanel;
  }

  @Override
  public TypeOfView getViewType() {
    return TypeOfView.VISUAL;
  }

  @Override
  public String makeView(IReadModel model) {
    Timer timer = new Timer(1000 / model.getTickRate(), this);
    timer.start();
    return "";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}
