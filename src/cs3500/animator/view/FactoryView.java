//package cs3500.animator.view;
//
//import java.io.IOException;
//
//import cs3500.animator.model.IAnimationModel;
//
///**
// * Factory.
// */
//public class FactoryView {
//  IAnimationModel model;
//
//  /**
//   * Makes factory.
//   * @param model model.
//   */
//  public FactoryView(IAnimationModel model) {
//    this.model = model;
//  }
//
//
//  /**
//   * Factory view.
//   * @param viewType type.
//   * @return view.
//   */
//  public IAnimationView makeView(String viewType) {
//    IAnimationView view = null;
//      switch (viewType.toLowerCase()) {
//        case "visual":
//          break;
//        case "svg":
//          this.view = new TextualViewImpl();
//          try {
//            ap.append(this.view.makeView(model));
//          } catch (IOException e) {
//            throw new IllegalStateException("Cannot create Text View!");
//          }
//        default:
//          throw new IllegalArgumentException(typeOfView + " is not a valid view type!");
//      }
//    }
//}
