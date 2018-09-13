import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cs3500.animator.operations.AOperation;
import cs3500.animator.operations.ChangeColor;
import cs3500.animator.util.Color;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IAnimationModelImpl;
import cs3500.animator.operations.IOperation;
import cs3500.animator.shapes.IShape;
import cs3500.animator.operations.Move;
import cs3500.animator.shapes.Oval;
import cs3500.animator.util.Posn;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.operations.Scale;
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

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the methods offered by each type of view in the animator.
 */
public class AnimatorViewTests {
  private IAnimationModel model;
  private IAnimationView textview;
  private IAnimationView svgview;
  private HybridView hybridview;

  private Color red;

  private IShape rect1;
  private IShape rect2;
  private IShape oval1;

  /**
   * This sets up the model and speed for further tests. The standard speed is set to 2 as opposed
   * to the default of 25.
   */
  @Before
  public void setup() {
    Color green;
    Color blue;
    this.model = new IAnimationModelImpl();
    model.setSpeed(2);
    this.textview = new TextualViewImpl();
    this.svgview = new SVGViewImpl();
    this.hybridview = new HybridView();

    this.red = new Color(255, 0, 0);
    blue = new Color(0, 0, 255);
    green = new Color(0, 255, 0);

    this.rect1 = new Rectangle("R1", new Posn(0, 0), this.red,
            0, 50, 25, 15);
    this.rect2 = new Rectangle("R2", new Posn(0, 0), blue,
            10, 60, 10, 15);
    this.oval1 = new Oval("C", new Posn(50, 50), green,
            0, 100, 10, 10);
  }

  @Test
  public void testTextualView() {
    model.createShape(rect1);
    model.createShape(rect2);
    model.createShape(oval1);

    IOperation moveRect1 = new Move("R1", 10, 15, new Posn(10, 10));
    AOperation changeColorRect2 = new ChangeColor("R2", 20, 30, red);
    AOperation scaleOval1 = new Scale("C", 40, 45, 3, 0);

    model.createOperation(moveRect1);
    model.createOperation(changeColorRect2);
    model.createOperation(scaleOval1);

    assertEquals(textview.makeView(this.model),
            "Shapes:\n" +
                    "Name: R1\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: RGB(65025, 0, 0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=25.0s\n" +
                    "\n" +
                    "Name: R2\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: RGB(0, 0, 65025)\n" +
                    "Appears at t=5.0s\n" +
                    "Disappears at t=30.0s\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color:" +
                    " RGB(0.0, 255.0, 0.0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=50.0s\n" +
                    "\n" +
                    "Shape R1 moves from (0.0,0.0) to (10.0,10.0) from t=500.0ms to t=700.0ms\n" +
                    "Shape R2 changes color from (0.0,0.0,65025.0) to (65025.0,0.0,0.0) from" +
                    " t=10.0s" +
                    " to t=15.0s\n" +
                    "Shape C scales from Width: 20.0, Height: 20.0 to Width: 23.0, Height: 20.0" +
                    " from t=20.0s to t=22.5s\n");
  }


  @Test
  public void testSVGView() {
    model.createShape(rect1);
    model.createShape(rect2);
    model.createShape(oval1);

    IOperation moveRect1 = new Move("R1", 10, 15, new Posn(10, 10));
    AOperation changeColorRect2 = new ChangeColor("R2", 20, 30, red);
    AOperation scaleOval1 = new Scale("C", 40, 45, 3, 0);

    model.createOperation(moveRect1);
    model.createOperation(changeColorRect2);
    model.createOperation(scaleOval1);

    assertEquals(svgview.makeView(model),
            "<svg width=\"800\" height=\"800\" version=\"1.1\"\n" +
                    "xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"25.0\" height=\"15.0\"" +
                    " fill=\"RGB(65025,0,0)\" visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"500ms\" " +
                    "attributeName=\"x\" from=\"0\" to=\"10\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"500ms\"" +
                    " attributeName=\"y\" from=\"0\" to=\"10\" fill=\"freeze\" />\n" +
                    "\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"R2\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"15.0\" " +
                    "fill=\"RGB(0,0,65025)\" visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"1000ms\"" +
                    " attributeName=\"fill\" from=\"RGB(0,0,65025)\" to=\"RGB(65025,0,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "\n" +
                    "</rect>\n" +
                    "\n" +
                    "<ellipse id=\"C\" cx=\"50.0\" cy=\"50.0\" rx=\"10.0\" ry=\"10.0\"" +
                    " fill=\"RGB(0,65025,0)\" visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"500ms\" " +
                    "attributeName=\"rx\" from=\"10.0\" to=\"11.5\" fill=\"freeze\" />\n" +
                    "\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "</svg>");

  }

  @Test
  public void testViewType() {
    assertEquals(hybridview.getViewType(), TypeOfView.HYBRID);
    assertEquals(svgview.getViewType(), TypeOfView.SVG);
    assertEquals(textview.getViewType(), TypeOfView.TEXT);
    IAnimationView visualview = new VisualView();
    assertEquals(visualview.getViewType(), TypeOfView.VISUAL);
  }


  @Test
  public void testPlayButtonListener() {
    model.createShape(rect1);
    model.createShape(rect2);
    model.createShape(oval1);

    IOperation moveRect1 = new Move("R1", 10, 15, new Posn(10, 10));
    AOperation changeColorRect2 = new ChangeColor("R2", 20, 30, red);
    AOperation scaleOval1 = new Scale("C", 40, 45, 3, 0);

    model.createOperation(moveRect1);
    model.createOperation(changeColorRect2);
    model.createOperation(scaleOval1);

    ActionListener l1 = new PlayButtonHandler(this.hybridview);
    ActionEvent one = new ActionEvent(hybridview, ActionEvent.ACTION_FIRST, "playbutton");
    hybridview.getPlayButton().addActionListener(l1);

    assertEquals(hybridview.getAnimationPanel().getPauseCounter(), 0);
    l1.actionPerformed(one);
    assertEquals(hybridview.getAnimationPanel().getPauseCounter(), 1);
    l1.actionPerformed(one);
    assertEquals(hybridview.getAnimationPanel().getPauseCounter(), 2);
  }

  @Test
  public void testRestartButtonHandler() {
    model.createShape(rect1);
    model.createShape(rect2);
    model.createShape(oval1);

    IOperation moveRect1 = new Move("R1", 10, 15, new Posn(10, 10));
    AOperation changeColorRect2 = new ChangeColor("R2", 20, 30, red);
    AOperation scaleOval1 = new Scale("C", 40, 45, 3, 0);

    model.createOperation(moveRect1);
    model.createOperation(changeColorRect2);
    model.createOperation(scaleOval1);

    ActionListener l1 = new RestartButtonHandler(this.hybridview);
    ActionEvent one = new ActionEvent(hybridview, ActionEvent.ACTION_FIRST, "restart" +
            " button");
    hybridview.getRestartButton().addActionListener(l1);

    l1.actionPerformed(one);
    assertEquals(hybridview.getAnimationPanel().getTime(), 0);
  }


  @Test
  public void testLoopingHandler() {
    ActionListener l1 = new LoopingHandler(this.hybridview);
    ActionEvent one = new ActionEvent(hybridview, ActionEvent.ACTION_FIRST, "looping " +
            "button");
    hybridview.getPlayButton().addActionListener(l1);
    hybridview.makeView(model);

    assertEquals(hybridview.getAnimationPanel().getLoopCounter(), 0);
    l1.actionPerformed(one);
    assertEquals(hybridview.getAnimationPanel().getLoopCounter(), 1);
    l1.actionPerformed(one);
    assertEquals(hybridview.getAnimationPanel().getLoopCounter(), 2);
  }

  @Test
  public void testSlowDownAnimationHandler() {
    ActionListener l1 = new SlowDownAnimationHandler(this.hybridview);
    ActionEvent one = new ActionEvent(hybridview, ActionEvent.ACTION_FIRST, "slow down " +
            "button");
    hybridview.getSlowDownButton().addActionListener(l1);
    hybridview.makeView(model);

    assertEquals(hybridview.getModel().getTickRate(), 2);
    l1.actionPerformed(one);
  }

  @Test
  public void testSpeedUpAnimationHandler() {
    ActionListener l1 = new SpeedUpAnimationHandler(this.hybridview);
    ActionEvent one = new ActionEvent(hybridview, ActionEvent.ACTION_FIRST, "speed up" +
            " button");
    hybridview.getSpeedUpButton().addActionListener(l1);
    hybridview.makeView(model);

    assertEquals(hybridview.getModel().getTickRate(), 2);
    l1.actionPerformed(one);
  }

  @Test
  public void testSetPlayButtonTitle() {
    assertEquals(hybridview.getPlayButtonTitle(), "Press to pause");
    hybridview.setPlayButtonTitle("hello");
    assertEquals(hybridview.getPlayButtonTitle(), "hello");
  }
}













