package cs3500.animator.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.ExportSVGHandler;
import cs3500.animator.view.HybridView;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.LoopingHandler;
import cs3500.animator.view.PlayButtonHandler;
import cs3500.animator.view.RestartButtonHandler;
import cs3500.animator.view.SVGViewImpl;
import cs3500.animator.view.SlowDownAnimationHandler;
import cs3500.animator.view.SpeedUpAnimationHandler;
import cs3500.animator.view.TextualViewImpl;
import cs3500.animator.view.TypeOfView;
import cs3500.animator.view.VisualView;

/**
 * This interface represents the points of control of the operations offered by the Easy Animator.
 * Allows the user to run animations through manual input.
 */
public class IAnimationControllerImpl implements IAnimationController {
  private final IAnimationModel model;
  private IAnimationView view;
  private Appendable ap = System.out;
  private String inputFile = "";
  private int speed;
  private String typeOfView = "";
  // isSytemOut is a flag used to detect whether or not the appendable needs to be flushed. If true,
  // the appendable does NOT need to be flushed.
  private boolean isSystemOut = true;
  private boolean canUseAppendable = true;

  /**
   * Default constructor for controller.
   *
   * @param model the desired model that handles the animation
   * @param args  arguments in main method
   * @throws IllegalArgumentException if passed a null model
   */
  public IAnimationControllerImpl(IAnimationModel model, String[] args)
          throws IllegalArgumentException {
    Objects.requireNonNull(model);

    this.model = model;
    this.parseInput(args);
    model.setSpeed(speed);
    this.createView(typeOfView);

    if (this.view.getViewType().equals(TypeOfView.HYBRID)) {
      ((HybridView) view)
              .getPlayButton().addActionListener(new PlayButtonHandler((HybridView) this.view));
      ((HybridView) view)
              .getRestartButton().addActionListener(
              new RestartButtonHandler((HybridView) this.view));
      ((HybridView) view)
              .getLoopCheckBox().addActionListener(new LoopingHandler((HybridView) this.view));
      ((HybridView) view)
              .getSlowDownButton().addActionListener(
              new SlowDownAnimationHandler((HybridView) this.view));
      ((HybridView) view)
              .getSpeedUpButton().addActionListener(
              new SpeedUpAnimationHandler((HybridView) this.view));
      try {
        ((HybridView) view).getExportSVG().addActionListener(
                new ExportSVGHandler((HybridView) this.view, this.model, this.ap));
      } catch (IOException e) {
        throw new IllegalArgumentException("Unable to export SVG");
      }
    }
  }

  @Override
  public void runAnimation() {
    if (canUseAppendable) {
      view.makeVisible();
      try {
        ap.append(view.makeView(model));
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not create View!");
      }
    } else {
      view.makeView(model);
      view.makeVisible();
    }

    if (!isSystemOut) {
      try {
        ((FileWriter) ap).flush();
        ((FileWriter) ap).close();
      } catch (IOException e) {
        throw new IllegalStateException("Cannot close, data has been lost");
      }
    }
  }

  /**
   * Creates the desired view based on user input; can be one of the following:.
   * - visual.
   * - interactive.
   * - svg.
   * - text.
   *
   * @param view the view to be made
   */
  private void createView(String view) {
    switch (view.toLowerCase()) {
      case "visual":
        this.view = new VisualView();
        this.view.getAnimationPanel().setModel(model);
        break;
      case "interactive":
        this.view = new HybridView();
        this.view.getAnimationPanel().setModel(model);
        break;
      case "svg":
        this.view = new SVGViewImpl();
        break;
      case "text":
        this.view = new TextualViewImpl();
        break;
      default:
        throw new IllegalArgumentException(typeOfView + " is not a valid view type!");
    }
  }

  /**
   * The purpose of this method is to parse the user input and establish the types of things the
   * user specified. Such as which view it is, what speed it is etc.
   *
   * @param args given arguments in the main method.
   */
  private void parseInput(String[] args) {

    if (args.length > 0) {

      for (int i = 0; i < args.length; i += 2) {

        try {
          if ((args[i + 1].length() > 0)) {
            switch (args[i]) {

              // name of animation file.
              case "-if":
                if ((args[i + 1].endsWith(".txt")) || (args[i + 1].endsWith(".svg"))) {
                  inputFile = args[i + 1];
                } else {
                  throw new
                          IllegalArgumentException(args[i + 1].substring(args[i + 1].length() - 4)
                          + " is not a valid output file type");
                }
                break;

              // type of view.
              case "-iv":
                typeOfView = args[i + 1];
                break;

              // output location.
              case "-o":
                if (args[i + 1].equalsIgnoreCase("out")) {
                  ap = System.out;
                } else {
                  try {
                    ap = new FileWriter(args[i + 1]);
                    isSystemOut = false;
                  } catch (IOException e) {
                    throw new IllegalArgumentException("Error in output file creation");
                  }
                }
                break;

              //tick rate.
              case "-speed":
                try {
                  speed = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                  throw new IllegalArgumentException(args[i + 1] + " is not a number");
                }
                if (speed < 1) {
                  throw new IllegalArgumentException("The tick rate cannot be zero");
                }
                break;

              default:
                throw new IllegalArgumentException("There was a bad parameter" +
                        " specifier, try again");
            }
          }
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException("You must have values after" +
                  " the parameter specifiers.");
        }
      }
    }

    if (this.inputFile.equalsIgnoreCase("")) {
      throw new IllegalArgumentException("No input file path detected. Please specify" +
              " a pre-existing input file path.");
    }

  }


  @Override
  public String getInputFile() {
    return this.inputFile;
  }
}



