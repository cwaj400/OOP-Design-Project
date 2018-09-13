package cs3500.animator.view;

import java.util.ArrayList;
import java.util.Collections;

import cs3500.animator.operations.IOperation;
import cs3500.animator.model.IReadModel;
import cs3500.animator.util.SortByAppearTime;
import cs3500.animator.util.SortByOperationStartTime;

/**
 * The implementation of the textual view. Creates a textual output describing the shapes and
 * their specific animations.
 */
public class TextualViewImpl implements IAnimationView {

  @Override
  public TypeOfView getViewType() {
    return TypeOfView.TEXT;
  }

  @Override
  public AnimationPanel getAnimationPanel() {
    throw new IllegalArgumentException("There is not Panel here!");
  }

  @Override
  public String makeView(IReadModel model) {
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<IOperation> allOps = new ArrayList<>();
    Collections.sort(model.getShapes(), new SortByAppearTime());

    if (model.getShapes().isEmpty()) {
      stringBuilder.append("\nNo more shapes in the animation.");
    } else {

      stringBuilder.append("Shapes:\n");

      for (int i = 0; i < model.getShapes().size(); i++) {
        for (IOperation o : model.getShapes().get(i).getOperations()) {
          allOps.add(o);
        }
        stringBuilder.append("Name: ");
        stringBuilder.append(model.getShapes().get(i).getName());
        stringBuilder.append("\nType: ");
        stringBuilder.append(model.getShapes().get(i).getType());
        stringBuilder.append("\n");
        stringBuilder.append(model.getShapes().get(i).getDescription());
        stringBuilder.append("Appears at t=");
        stringBuilder.append(Float.toString(model.getShapes().get(i).getAppearTime()
                / model.getTickRate()));
        stringBuilder.append("s\n");
        stringBuilder.append("Disappears at t=");
        stringBuilder.append(Float.toString(model.getShapes().get(i).getDisappearTime()
                / model.getTickRate()));
        stringBuilder.append("s\n\n");

      }
      for (int k = 0; k < allOps.size(); k++) {
        Collections.sort(allOps, new SortByOperationStartTime());
        stringBuilder.append(allOps.get(k).getDescription(model));
      }
    }
    return stringBuilder.toString();
  }

  @Override
  public void makeVisible() {
    // method does not apply to this view
  }

}
