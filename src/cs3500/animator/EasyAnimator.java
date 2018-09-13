package cs3500.animator;

import java.io.IOException;

import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.IAnimationControllerImpl;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IAnimationModelImpl;
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.util.TweenModelBuilder;


/**
 * Class that contains main method and runs the animation.
 */
public class EasyAnimator {
  /**
   * Entrance of show.
   *
   * @param args parameters, -iv and -if are required. -o is set to syst.out if not specified, tick
   *             set to 1 if not specified. -if is input file, it must be a pre-existing file:
   *             TextAnimationOutput.txt or SVGOutput.svg. -iv must be either text, visual or svg.
   *             -speed is tick.
   */
  public static void main(String[] args) {

    final AnimationFileReader ANIMATIONFILEREADER;
    final TweenModelBuilder<IAnimationModel> TWEENMODELBUILDER;

    ANIMATIONFILEREADER = new AnimationFileReader();
    TWEENMODELBUILDER = new IAnimationModelImpl.Builder();

    IAnimationModel model = TWEENMODELBUILDER.build();

    IAnimationController controller = new IAnimationControllerImpl(model, args);

    try {
      ANIMATIONFILEREADER.readFile(controller.getInputFile(), TWEENMODELBUILDER);
    } catch (IOException exception) {
      throw new IllegalArgumentException("File path does not exist, please specify a " +
              "pre-existing file");
    }

    controller.runAnimation();
  }
}
