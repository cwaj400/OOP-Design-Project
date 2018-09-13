package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.operations.ChangeColor;
import cs3500.animator.operations.IOperation;
import cs3500.animator.operations.Move;
import cs3500.animator.operations.Scale;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.util.Color;
import cs3500.animator.util.Posn;
import cs3500.animator.util.SortByAppearTime;
import cs3500.animator.util.SortByOperationStartTime;
import cs3500.animator.util.TweenModelBuilder;

/**
 * The implementation of the AnimatorModel interface. Consists of the model of the Easy Animator.
 * Implements the public methods as specified in the IAnimationModel interface.
 */
public class IAnimationModelImpl implements IAnimationModel {
  private List<IShape> shapeList;
  private List<IOperation> operationsList;
  public final static int WIDTH = 800;
  public final static int HEIGHT = 800;
  private int tickRate;

  /**
   * IAnimationModel default constructor. Default speed is 1 tick/second.
   */
  public IAnimationModelImpl() {
    this.tickRate = 1;
    this.shapeList = new ArrayList<>();
    this.operationsList = new ArrayList<>();
  }


  /**
   * Builder given to us to create the model for the animation. We did not need to use all
   * the given parameters.
   */
  public static final class Builder implements TweenModelBuilder<IAnimationModel> {
    IAnimationModelImpl model = new IAnimationModelImpl();

    @Override
    public TweenModelBuilder<IAnimationModel> addOval(String name, float cx, float cy,
                                                      float xRadius, float yRadius, float red,
                                                      float green, float blue, int startOfLife,
                                                      int endOfLife) {
      model.createShape(new Oval(name, new Posn(cx, cy), new Color(red, green, blue), startOfLife,
              endOfLife, xRadius, yRadius));
      return this;
    }


    @Override
    public TweenModelBuilder<IAnimationModel> addRectangle(String name, float lx, float ly,
                                                           float width, float height, float red,
                                                           float green, float blue, int startOfLife,
                                                           int endOfLife) {
      model.createShape(new Rectangle(name, new Posn(lx, ly), new Color(red, green, blue),
              startOfLife, endOfLife, width, height));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addMove(String name, float moveFromX, float moveFromY,
                                                      float moveToX, float moveToY, int startTime,
                                                      int endTime) {
      model.createOperation(new Move(name, startTime, endTime, new Posn(moveToX, moveToY)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addColorChange(String name, float oldR, float oldG,
                                                             float oldB, float newR, float newG,
                                                             float newB, int startTime,
                                                             int endTime) {
      model.createOperation(new ChangeColor(name, startTime, endTime, new Color(newR, newG, newB)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addScaleToChange(String name, float fromSx,
                                                               float fromSy, float toSx,
                                                               float toSy, int startTime,
                                                               int endTime) {
      model.createOperation(new Scale(name, startTime, endTime, toSx, toSy));
      return this;
    }

    @Override
    public IAnimationModel build() {
      return model;
    }
  }


  @Override
  public void createShape(IShape shape) {
    this.shapeList.add(shape);
  }

  @Override
  public void removeShape(IShape shape) throws IllegalArgumentException {
    if (shapeList.contains(shape)) {
      this.shapeList.remove(shape);
    } else {
      throw new IllegalArgumentException("Shape is not in this animation.");
    }
  }

  @Override
  public void createOperation(IOperation operation) {
    this.operationsList.add(operation);
    for (IShape s : this.getShapes()) {
      if (operation.getName().equals(s.getName())) {
        operation.command(s);
      }
    }
  }

  @Override
  public void setSpeed(int speed) {
    this.tickRate = speed;
  }

  @Override
  public String printAnimationState() {
    String result = "";
    ArrayList<IOperation> allOps = new ArrayList<>();
    Collections.sort(this.shapeList, new SortByAppearTime());

    if (this.shapeList.isEmpty()) {
      result += "\nNo more shapes in the animation.";
    } else {

      result += "Shapes:\n";

      for (int i = 0; i < this.shapeList.size(); i++) {
        for (IOperation o : shapeList.get(i).getOperations()) {
          allOps.add(o);
        }
        result += "Name: "
                + this.shapeList.get(i).getName()
                + "\nType: "
                + this.shapeList.get(i).getType() + "\n"
                + shapeList.get(i).getDescription()
                + "Appears at t=" + Integer.toString(shapeList.get(i).getAppearTime()) + "\n"
                + "Disappears at t=" + Integer.toString(shapeList.get(i).getDisappearTime())
                + "\n\n";
      }

      for (int k = 0; k < allOps.size(); k++) {
        Collections.sort(allOps, new SortByOperationStartTime());
        result += allOps.get(k).getDescription(this);
      }
    }
    return result;
  }

  @Override
  public ArrayList<IShape> getShapes() {
    return new ArrayList<IShape>(shapeList);
  }

  @Override
  public int getTickRate() {
    return this.tickRate;
  }

  @Override
  public int getEndTime() {
    ArrayList<Integer> shapeTimes = new ArrayList<>();
    ArrayList<IOperation> allOps = new ArrayList<>();
    ArrayList<Integer> opsTimes = new ArrayList<>();

    for (IShape s : this.shapeList) {
      shapeTimes.add(s.getDisappearTime());
    }
    int shapesEndTime = Collections.max(shapeTimes);


    for (int i = 0; i < this.shapeList.size(); i++) {
      for (IOperation o : shapeList.get(i).getOperations()) {
        allOps.add(o);
      }
    }

    for (IOperation op : allOps) {
      opsTimes.add(op.getToTime());
    }
    int opsEndTime = Collections.max(opsTimes);
    return Math.max(shapesEndTime, opsEndTime);
  }
}
