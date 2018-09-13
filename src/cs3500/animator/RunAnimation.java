//package cs3500.animator;
//
//import cs3500.animator.operations.ChangeColor;
//import cs3500.animator.util.Color;
//import cs3500.animator.model.IAnimationModel;
//import cs3500.animator.model.IAnimationModelImpl;
//import cs3500.animator.operations.IOperation;
//import cs3500.animator.operations.Move;
//import cs3500.animator.shapes.Oval;
//import cs3500.animator.util.Posn;
//import cs3500.animator.shapes.Rectangle;
//import cs3500.animator.operations.Scale;
//import cs3500.animator.view.HybridView;
//
///**
// * We elected to leave this class in to demonstrate how we tested the Visual view. It was a case
// of looking and inspecting.
// */
//public class RunAnimation {
//
//  /**
//   * Main, we did not use the args.
//   *
//   * @param args We did not have to use the args here as we passed in an IWriteModel so we could
//   *             edit and change the data inside.
//   */
//
//  public static void main(String[] args) {
//    IVisualView view3 = new HybridView();
//    IAnimationModel model = new IAnimationModelImpl();
//    model.setSpeed(20);
//    ((HybridView) view3).getAnimationPanel().setModel(model);
//
//    model.createShape(new Rectangle("R1", new Posn(100, 100),
//            new Color(1f, 0f, 0f),
//            0, 30, 50, 50));
//    model.createShape(new Oval("R", new Posn(100, 100), new
//            Color(0.3f, 0.5f, 0.9f), 0, 60,
//            30, 30));
//
//    IOperation move = new Move("R", 2, 5, new Posn(300, 300));
//    IOperation move2 = new Move("R", 21, 25, new Posn(400, 400));
//    IOperation scale = new Scale("R", 2, 10, 20, 30);
//    IOperation cc = new ChangeColor("R", 15, 20, new Color(0.3f,
//            1f, 0.6f));
//
//    IOperation move1 = new Move("R1", 2, 5, new Posn(100, 50));
//    IOperation move21 = new Move("R1", 21, 25, new Posn(100, 350));
//    IOperation scale1 = new Scale("R1", 2, 10, 30, -10);
//    IOperation cc1 = new ChangeColor("R1", 15, 20, new Color(1f,
//            0f, 0f));
//
//
//    model.createOperation(scale1);
//    model.createOperation(move1);
//    model.createOperation(cc1);
//    model.createOperation(move21);
//
//    model.createOperation(scale);
//    model.createOperation(move);
//    model.createOperation(cc);
//    model.createOperation(move2);
//
//    ((HybridView) view3).makeVisible();
//    ((HybridView) view3).makeView(model);
//  }
//}
