import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
import cs3500.animator.util.SortByAppearTime;
import cs3500.animator.util.SortByOperationStartTime;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the public methods offered by the AnimatorModel interface.
 */
public class AnimatorModelTests {
  private IAnimationModel model;

  private Color red;
  private Color blue;
  private Color green;

  private IShape rect1;
  private IShape rect2;
  private IShape oval1;

  /**
   * Represents the test setup.
   */
  @Before
  public void setup() {
    this.model = new IAnimationModelImpl();
    model.setSpeed(model.getTickRate());

    this.red = new Color(1, 0, 0);
    this.blue = new Color( 0, 0, 1);
    this.green = new Color( 0, 1, 0);

    this.rect1 = new Rectangle("R1", new Posn(0, 0), this.red,
            0, 50, 25, 15);
    this.rect2 = new Rectangle("R2", new Posn(0, 0), this.blue,
            10, 60, 10, 15);
    this.oval1 = new Oval("C", new Posn(50, 50), this.green,
            20, 100, 10, 10);
  }

  // tests creating a shape
  @Test
  public void testCreateShape() {
    this.model.createShape(rect1);
    assertEquals(this.model.printAnimationState(),
            "Shapes:\nName: R1\nType: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n\n");
    this.model.createShape(rect2);
    assertEquals(this.model.printAnimationState(),
            "Shapes:\nName: R1\nType: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n\n" +
                    "Name: R2\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: (0, 0, 255)" +
                    "\nAppears at t=10\n" +
                    "Disappears at t=60\n\n");

    this.model.createShape(oval1);
    assertEquals(this.model.printAnimationState(),
            "Shapes:\nName: R1\nType: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n\n" +
                    "Name: R2\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: (0, 0, 255)" +
                    "\nAppears at t=10\n" +
                    "Disappears at t=60\n\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n"
                    + "Appears at t=20\n" +
                    "Disappears at t=100\n\n");

  }

  // tests removing a shape
  @Test
  public void testRemoveShape() {
    this.model.createShape(rect1);
    assertEquals(this.model.printAnimationState(), "Shapes:\nName: R1\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n\n");
    this.model.removeShape(rect1);
    assertEquals(this.model.printAnimationState(), "\nNo more shapes in the animation.");
    this.model.createShape(rect2);
    assertEquals(this.model.printAnimationState(), "Shapes:\nName: R2\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: (0, 0, 255)\n" +
            "Appears at t=10\n" +
            "Disappears at t=60\n\n");
    this.model.createShape(oval1);
    assertEquals(this.model.printAnimationState(), "Shapes:\nName: R2\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: (0, 0, 255)\n" +
            "Appears at t=10\n" +
            "Disappears at t=60\n\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n" +
            "Appears at t=20\n" +
            "Disappears at t=100\n\n");
    this.model.removeShape(rect2);
    assertEquals(this.model.printAnimationState(), "Shapes:\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n" +
            "Appears at t=20\n" +
            "Disappears at t=100\n\n");
  }

  // tests the error message if there are no shapes in the animation
  @Test
  public void testNoShapesInAnimation() {
    assertEquals(this.model.printAnimationState(), "\nNo more shapes in the animation.");
    this.model.createShape(rect1);
    assertEquals(this.model.printAnimationState(), "Shapes:\nName: R1\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n\n");
    this.model.removeShape(rect1);
    assertEquals(this.model.printAnimationState(), "\nNo more shapes in the animation.");
  }

  // tests removing a shape that is not in the animation throws an exception
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveShapeNotInAnimation() {
    this.model.removeShape(rect2);
    this.model.removeShape(rect1);
    this.model.removeShape(oval1);
    this.model.createShape(rect1);
    this.model.removeShape(oval1);
  }

  // tests printing the animation state method
  @Test
  public void testPrintAnimationState() {
    model.createShape(rect1);
    model.createShape(rect2);
    model.createShape(oval1);
    model.setSpeed(2);

    IOperation moveRect1 = new Move("move",10, 16, new Posn(10, 10));
    IOperation changeColorRect2 = new ChangeColor("change color", 20, 30,
            red);
    IOperation scaleOval1 = new Scale("scale", 40, 44, 3, 0);

    moveRect1.command(rect1);
    changeColorRect2.command(rect2);
    scaleOval1.command(oval1);

    assertEquals(model.printAnimationState(),
            "Shapes:\n" +
                    "Name: R1\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n" +
                    "\n" +
                    "Name: R2\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 10.0, Height: 15.0, Color: (0, 0, 255)\n" +
                    "Appears at t=10\n" +
                    "Disappears at t=60\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color:" +
                    " (0.0, 1.0, 0.0)\n" +
                    "Appears at t=20\n" +
                    "Disappears at t=100\n" +
                    "\n" +
                    "Shape R1 moves from (0.0,0.0) to (10.0,10.0) from t=500.0ms to t=800.0ms\n" +
                    "Shape R2 changes color from (0.0,0.0,1.0) to (1.0,0.0,0.0) from t=10.0s" +
                    " to t=15.0s\n" +
                    "Shape C scales from Width: 20.0, Height: 20.0 to Width: 23.0, Height: 20.0" +
                    " from t=20.0s to t=22.0s\n");

  }

  // tests the intent on moving a shape
  @Test
  public void testMoveCommand() {
    IOperation moveRect1 = new Move("R1", 10, 16, new Posn(10, 10));
    IOperation moveOval1 = new Move("move", 30, 50, new Posn(50, 50));

    model.createShape(rect1);
    model.setSpeed(2);
    assertEquals(model.printAnimationState(), "Shapes:\nName: R1\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n\n");
    model.createOperation(moveRect1);
    assertEquals(model.printAnimationState(),
            "Shapes:\nName: R1\nType: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n\n" +
                    "Shape R1 moves from (0.0,0.0) to (10.0,10.0) from t=500.0ms to t=800.0ms\n");
    model.removeShape(rect1);
    model.createShape(oval1);
    moveOval1.command(oval1);
    assertEquals(model.printAnimationState(),
            "Shapes:\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n"
                    + "Appears at t=20\n" +
                    "Disappears at t=100\n\n" +
                    "Shape C moves from (50.0,50.0) to (50.0,50.0) from t=1500.0ms" +
                    " to t=2500.0ms\n");

  }

  // tests the intent on changing a color
  @Test
  public void testChangeColorCommand() {
    IOperation cc1 = new ChangeColor("cc", 10, 16, blue);
    IOperation cc2 = new ChangeColor("cc", 24, 30, green);

    model.createShape(rect1);
    model.setSpeed(2);
    assertEquals(model.printAnimationState(), "Shapes:\nName: R1\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n\n");
    cc1.command(rect1);
    assertEquals(model.printAnimationState(),
            "Shapes:\n" +
                    "Name: R1\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n" +
                    "\n" +
                    "Shape R1 changes color from (1.0,0.0,0.0) to (0.0,0.0,1.0) from" +
                    " t=5.0s to t=8.0s\n");
    model.removeShape(rect1);
    model.createShape(oval1);
    cc2.command(oval1);
    assertEquals(model.printAnimationState(),
            "Shapes:\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n"
                    + "Appears at t=20\n" +
                    "Disappears at t=100\n\n" +
                    "Shape C changes color from (0.0,1.0,0.0) to (0.0,1.0,0.0) from t=12.0s to" +
                    " t=15.0s\n");
  }

  // tests the intent on scaling a shape
  @Test
  public void testScaleCommand() {
    IOperation scale1 = new Scale("scale", 10, 15, 10, 10);
    IOperation scale2 = new Scale("scale", 25, 30, 1, -2);

    model.createShape(rect1);
    model.setSpeed(2);
    assertEquals(model.printAnimationState(), "Shapes:\nName: R1\nType: rectangle\n" +
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n\n");
    scale1.command(rect1);
    assertEquals(model.printAnimationState(),
            "Shapes:\nName: R1\nType: rectangle\n" +
                    "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n" +
                    "Appears at t=0\n" +
                    "Disappears at t=50\n\n" +
                    "Shape R1 scales from Width: 25.0, Height: 15.0 to Width: 35.0," +
                    " Height: 25.0 " +
                    "from t=5.0s to t=7.5s\n");
    model.removeShape(rect1);
    model.createShape(oval1);
    scale2.command(oval1);
    assertEquals(model.printAnimationState(),
            "Shapes:\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, Color: (0.0, 1.0, 0.0)\n"
                    + "Appears at t=20\n" +
                    "Disappears at t=100\n\n" +
                    "Shape C scales from Width: 20.0, Height: 20.0 to Width: 21.0, Height:" +
                    " 18.0 from t=12.5s to t=15.0s\n");
  }


  // tests the get description method in the move class
  @Test
  public void testGetDescriptionMove() {
    IOperation move1 = new Move("move", 10, 15, new Posn(10, 10));
    move1.command(rect1);
    model.setSpeed(2);
    assertEquals(move1.getDescription(this.model),
            "Shape R1 moves from (0.0,0.0) to (10.0,10.0) from t=500.0ms to t=700.0ms\n");
  }

  // tests the get description method in the change color class
  @Test
  public void testGetDescriptionChangeColor() {
    IOperation cc1 = new ChangeColor("move", 10, 15, blue);
    cc1.command(rect1);
    model.setSpeed(2);
    assertEquals(cc1.getDescription(this.model),
            "Shape R1 changes color from (1.0,0.0,0.0) to (0.0,0.0,1.0) from t=5.0s " +
                    "to t=7.0s\n");
  }

  // tests the get description method in the scale class
  @Test
  public void testGetDescriptionScale() {
    IOperation scale = new Scale("scale",10, 15, 10, 10);
    scale.command(rect1);
    model.setSpeed(2);
    assertEquals(scale.getDescription(this.model),
            "Shape R1 scales from Width: 25.0, Height: 15.0 to " +
                    "Width: 35.0, Height: 25.0 from t=5.0s to t=7.5s\n");
  }

  // when an operation has not been used, throw an exception
  @Test(expected = IllegalArgumentException.class)
  public void testNoShapesPerOperationError() {
    IOperation move1 = new Move("move", 10, 15, new Posn(10, 10));
    IOperation cc1 = new ChangeColor("cc", 10, 15, blue);
    IOperation scale = new Scale("scale", 10, 15, 10, 10);

    move1.getDescription(this.model);
    cc1.getDescription(this.model);
    scale.getDescription(this.model);
  }

  // tests altering the shape to an illegal dimension throws an exception
  @Test(expected = IllegalArgumentException.class)
  public void testChangeShapeDimensionsToNonPositiveDimensionsError() {
    IOperation scale = new Scale("scale", 10, 15, -25, -15);
    scale.command(rect1);
    scale.getDescription(this.model);
  }

  // tests invalid start times throw an exception
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartTimesForOperations() {
    IOperation scale = new Scale("scale", 200, 205, -25,
            -15);
    IOperation move1 = new Move("move", 15, 20, new Posn(10, 10));
    IOperation cc1 = new ChangeColor("cc", 20, 15, blue);

    scale.command(rect1);
    move1.command(oval1);
    cc1.command(rect2);
  }

  // tests the getX getter in the posn class
  @Test
  public void testGetX() {
    Posn p1 = new Posn(0, 0);
    Posn p2 = new Posn(-1, 2);
    Posn p3 = new Posn(1, 0);
    assertEquals(p1.getX(), 0.0);
    assertEquals(p2.getX(), -1.0);
    assertEquals(p3.getX(), 1.0);
  }

  // tests the getY getter in the posn class
  @Test
  public void testGetY() {
    Posn p1 = new Posn(0, 0);
    Posn p2 = new Posn(-1, 2);
    Posn p3 = new Posn(1, -1);
    assertEquals(p1.getY(), 0.0);
    assertEquals(p2.getY(), 2.0);
    assertEquals(p3.getY(), -1.0);
  }

  // tests the setX setter in the posn class
  @Test
  public void testSetX() {
    Posn p1 = new Posn(0, 0);
    p1.setX(1.0);
    assertEquals(p1.getX(), 1.0);
  }

  // tests the setY setter in the posn class
  @Test
  public void testSetY() {
    Posn p1 = new Posn(0, 0);
    p1.setY(1.0);
    assertEquals(p1.getY(), 1.0);
  }

  // tests the getRGB method in the color class
  @Test
  public void testGetRGB() {
    assertEquals(blue.getRGB(), "(0.0,0.0,1.0)");
    assertEquals(red.getRGB(), "(1.0,0.0,0.0)");
    assertEquals(green.getRGB(), "(0.0,1.0,0.0)");
  }

  // tests the getHeight getter in the abstract shape class
  @Test
  public void testGetHeightShape() {
    assertEquals(rect1.getHeight(), 15.0);
    assertEquals(rect2.getHeight(), 15.0);
    assertEquals(oval1.getHeight(), 20.0);
  }

  // tests the getWidth getter in the abstract shape class
  @Test
  public void testGetWidthShape() {
    assertEquals(rect1.getWidth(), 25.0);
    assertEquals(rect2.getWidth(), 10.0);
    assertEquals(oval1.getWidth(), 20.0);
  }

  // tests the compareTo override
  @Test
  public void testCompareInSortByAppearTime() {
    SortByAppearTime s = new SortByAppearTime();
    assertEquals(s.compare(rect1, oval1), -20);
    assertEquals(s.compare(oval1, rect2), 10);
  }

  // tests the compareTo override
  @Test
  public void testCompareInSortByOperationsStartTime() {
    SortByOperationStartTime s = new SortByOperationStartTime();
    IOperation scale = new Scale("scale", 10, 15, -25, -15);
    IOperation move1 = new Move("move", 20, 15, new Posn(10, 10));
    IOperation cc1 = new ChangeColor("cc", 0, 15, blue);

    assertEquals(s.compare(scale, move1), -10);
    assertEquals(s.compare(scale, cc1), 10);
  }

  // tests getName method
  @Test
  public void testGetName() {
    assertEquals(rect1.getName(), "R1");
    assertEquals(oval1.getName(), "C");
  }

  @Test
  public void testGetType() {
    assertEquals(rect1.getType(), "rectangle");
    assertEquals(oval1.getType(), "oval");
  }

  @Test
  public void testGetPosn() {
    assertEquals(rect1.getPosn().getX(),0.0);
    assertEquals(rect1.getPosn().getY(), 0.0);
    assertEquals(oval1.getPosn().getY(), 50.0);
    assertEquals(oval1.getPosn().getX(), 50.0);
  }

  @Test
  public void testGetColor() {
    assertEquals(rect1.getColor(), this.red);
    assertEquals(oval1.getColor(), this.green);
  }

  @Test
  public void testAppearTime() {
    assertEquals(rect1.getAppearTime(), 0);
    assertEquals(oval1.getAppearTime(), 20);
  }

  @Test
  public void testDisappearTime() {
    assertEquals(rect1.getDisappearTime(), 50);
    assertEquals(oval1.getDisappearTime(), 100);
  }

  @Test
  public void testGetWidth() {
    assertEquals(rect1.getWidth(), 25.0);
    assertEquals(oval1.getWidth(), 20.0);
  }

  @Test
  public void testGetHeight() {
    assertEquals(rect1.getHeight(), 15.0);
    assertEquals(oval1.getHeight(), 20.0);
  }

  @Test
  public void testGetOperations() {
    this.model.createShape(rect1);
    IOperation m = new Move("R1", 10, 10, new Posn(100, 0));
    IOperation s = new Scale("R1", 20, 30, 10, 10);
    this.model.createOperation(m);
    this.model.createOperation(s);
    ArrayList<IOperation> ops = new ArrayList<>();
    ops.add(m);
    ops.add(s);
    assertEquals(rect1.getOperations(), ops);
  }


  @Test
  public void testGetDescription() {
    assertEquals(rect1.getDescription(),
            "Corner: (0.0,0.0), Width: 25.0, Height: 15.0, Color: (255, 0, 0)\n");
    assertEquals(oval1.getDescription(),
            "Center: (50.0,50.0), X radius: 10.0, Y radius: 10.0, " +
                    "Color: (0.0, 1.0, 0.0)\n");
  }

  @Test
  public void testPrintSVG() {
    assertEquals(rect1.printSVG(),
            "<rect id=\"R1\" x=\"0.0\" y=\"0.0\" width=\"25.0\"" +
                    " height=\"15.0\" fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
            "\n" +
            "</rect>\n");
    assertEquals(oval1.printSVG(),"<ellipse id=\"C\" cx=\"50.0\" cy=\"50.0\" rx=\"10.0\" " +
            "ry=\"10.0\" fill=\"rgb(0,255,0)\" visibility=\"visible\" >\n" +
            "\n" +
            "</ellipse>\n");
  }

  @Test
  public void testPosnTween() {
    IShape r = new Rectangle("R", new Posn(0, 0), red,
            0, 100, 20, 30);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    model.createShape(r);
    model.createOperation(m);
    assertEquals(r.getStateAt(1).getPosn().getX(), 0.0);
    assertEquals(r.getStateAt(1).getPosn().getY(), 0.0);
    assertEquals(r.getStateAt(30).getPosn().getX(), 100.0);
    assertEquals(r.getStateAt(30).getPosn().getY(), 100.0);
    assertEquals(r.getStateAt(10).getPosn().getX(), 0.0);
    assertEquals(r.getStateAt(11).getPosn().getX(), 50.0);
    assertEquals(r.getStateAt(11).getPosn().getY(), 50.0);
  }

  @Test
  public void testColorTween() {
    IShape r = new Rectangle("R", new Posn(0, 0), red,
            0, 100, 20, 30);
    IOperation m = new ChangeColor("R", 10, 12, blue);
    model.createShape(r);
    model.createOperation(m);
    assertEquals(r.getStateAt(1).getColor().getRedval(), 1.0, 0.00001);
    assertEquals(r.getStateAt(1).getColor().getGreenval(), 0.0, 0.00001);
    assertEquals(r.getStateAt(1).getColor().getBlueval(), 0.0, 0.00001);
    assertEquals(r.getStateAt(11).getColor().getRedval(), 0.5, 0.00001);
    assertEquals(r.getStateAt(11).getColor().getGreenval(), 0.0, 0.00001);
    assertEquals(r.getStateAt(11).getColor().getBlueval(), 0.5, 0.00001);
  }

  @Test
  public void testScaleTween() {
    IShape r = new Rectangle("R", new Posn(0, 0), red,
            0, 100, 20, 30);
    IOperation m = new Scale("R", 10, 12, 10, 10);
    model.createShape(r);
    model.createOperation(m);

    assertEquals(r.getStateAt(1).getWidth(), 20, 0.0001);
    assertEquals(r.getStateAt(1).getHeight(), 30, 0.0001);
    assertEquals(r.getStateAt(11).getWidth(), 25, 0.0001);
    assertEquals(r.getStateAt(11).getHeight(), 35, 0.0001);
    assertEquals(r.getStateAt(13).getWidth(), 30, 0.0001);
    assertEquals(r.getStateAt(13).getHeight(), 40, 0.0001);
  }

  @Test
  public void testGetStartTime() {
    IOperation s = new Scale("R", 10, 12, 10, 10);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    IOperation c = new ChangeColor("R", 10, 12, blue);

    assertEquals(s.getFromTime(), 10);
    assertEquals(m.getFromTime(), 10);
    assertEquals(c.getFromTime(), 10);
  }

  @Test
  public void testGetEndTime() {
    IOperation s = new Scale("R", 10, 12, 10, 10);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    IOperation c = new ChangeColor("R", 10, 12, blue);

    assertEquals(s.getToTime(), 12);
    assertEquals(m.getToTime(), 12);
    assertEquals(c.getToTime(), 12);
  }

  @Test
  public void testGetNameOps() {
    IOperation s = new Scale("R", 10, 12, 10, 10);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    IOperation c = new ChangeColor("R", 10, 12, blue);

    assertEquals(s.getName(), "R");
    assertEquals(m.getName(), "R");
    assertEquals(c.getName(), "R");
  }

  @Test
  public void testGetDescriptionOps() {
    IOperation s = new Scale("R", 10, 12, 10, 10);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    IOperation c = new ChangeColor("R", 10, 12, blue);

    IShape r = new Rectangle("R", new Posn(0, 0), red,
            0, 100, 20, 30);
    model.createShape(r);

    model.createOperation(s);
    model.createOperation(m);
    model.createOperation(c);
    model.setSpeed(2);

    assertEquals(s.getDescription(model),
            "Shape R scales from Width: 20.0, Height: 30.0 to Width: 30.0," +
                    " Height: 40.0 from t=5.0s to t=6.0s\n");
    assertEquals(m.getDescription(model),
            "Shape R moves from (0.0,0.0) to (100.0,100.0) from t=500.0ms to t=600.0ms\n");
    assertEquals(c.getDescription(model),
            "Shape R changes color from (1.0,0.0,0.0) to (0.0,0.0,1.0) from t=5.0s to " +
                    "t=6.0s\n");
  }

  @Test
  public void testOpsSVG() {
    IOperation s = new Scale("R", 10, 12, 10, 10);
    IOperation m = new Move("R", 10, 12, new Posn(100, 100));
    IOperation c = new ChangeColor("R", 10, 12, blue);

    IShape r = new Rectangle("R", new Posn(0, 0), red,
            0, 100, 20, 30);
    model.createShape(r);

    model.createOperation(s);
    model.createOperation(m);
    model.createOperation(c);
    model.setSpeed(2);

    assertEquals(s.printSVG(), "<animate attributeType=\"xml\" begin=\"1000ms\"" +
            " dur=\"200ms\" attributeName=\"width\" from=\"20.0\" to=\"30.0\" fill=\"freeze\" /" +
            ">\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"200ms\" attributeName=" +
            "\"height\" from=\"30.0\" to=\"40.0\" fill=\"freeze\" />\n");
    assertEquals(m.printSVG(), "<animate attributeType=\"xml\" begin=\"1000ms\" dur=" +
            "\"200ms\" attributeName=\"x\" from=\"0\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"200ms\" attributeName=\"y\"" +
            " from=\"0\" to=\"100\" fill=\"freeze\" />\n");
    assertEquals(c.printSVG(), "<animate attributeType=\"xml\" begin=\"1000ms\" dur=" +
            "\"200ms\" attributeName=\"fill\" from=\"(255,0,0)\" to=\"(0,0,255)\" fill=\"f" +
            "reeze\" />\n");
  }

  @Test
  public void testGetTickRate() {
    assertEquals(this.model.getTickRate(), 25);
  }

  @Test
  public void testSetSpeed() {
    assertEquals(this.model.getTickRate(), 25);
    model.setSpeed(100);
    assertEquals(this.model.getTickRate(), 100);
  }
}
