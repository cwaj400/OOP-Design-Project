package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import cs3500.animator.model.IReadModel;

/**
 * Action listener class that creates an SVG file for the animation to be exported.
 */
public class ExportSVGHandler implements ActionListener {
  private HybridView view;
  private Appendable ap;
  private IReadModel model;

  /**
   * Default constructor.
   *
   * @param view  the view passed into this handler
   * @param model the model passed into this handler
   * @param ap    the appendable to be written to
   * @throws IOException due to illegal creation of a file
   */
  public ExportSVGHandler(HybridView view, IReadModel model, Appendable ap) throws IOException {
    this.view = view;
    this.model = model;
    this.ap = ap;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String path = JOptionPane.showInputDialog(view, "Type in a new SVG file path name." +
            " Please exclude the .svg at the end");
    JOptionPane.showMessageDialog(view, path + ".svg exported!");
    try {
      this.ap = new FileWriter(path + ".svg");
      ap.append(new SVGViewImpl().makeView(model));
      ((FileWriter) ap).close();
    } catch (IOException e1) {
      throw new IllegalStateException("Sorry, could not export as SVG!");
    }
  }
}
