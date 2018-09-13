package cs3500.animator.view;


import cs3500.animator.model.IAnimationModelImpl;
import cs3500.animator.model.IReadModel;
import cs3500.animator.shapes.IShape;

/**
 * The implementation of the SVG-based view. Creates a visual in .svg file format.
 */
public class SVGViewImpl implements IAnimationView {

  @Override
  public TypeOfView getViewType() {
    return TypeOfView.SVG;
  }

  @Override
  public AnimationPanel getAnimationPanel() {
    throw new IllegalArgumentException("There is not Panel here!");
  }


  @Override
  public String makeView(IReadModel model) {

    StringBuilder stringBuilder = new StringBuilder();


    stringBuilder.append("<svg width=\"");
    stringBuilder.append(Integer.toString(IAnimationModelImpl.WIDTH));
    stringBuilder.append("\" height=\"");
    stringBuilder.append(Integer.toString(IAnimationModelImpl.HEIGHT));
    stringBuilder.append("\" version=\"1.1\"\n");
    stringBuilder.append("xmlns=\"http://www.w3.org/2000/svg\">\n");

    for (IShape s : model.getShapes()) {
      System.out.println(s.getName());
      stringBuilder.append(s.printSVG());
      stringBuilder.append("\n");
    }

    stringBuilder.append("</svg>");

    return stringBuilder.toString();
  }

  @Override
  public void makeVisible() {
    // not applicable to this view type
  }
}