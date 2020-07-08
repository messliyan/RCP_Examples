package gef_figure;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class EmployeeFigure extends Figure {
	private Label labelName = new Label();
	private Label labelFirstName = new Label();
	public static final int EMPLOYE_FIGURE_DEFWIDTH = 250;
	public static final int EMPLOYE_FIGURE_DEFHEIGHT = 150;
	
	public EmployeeFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		labelFirstName.setForegroundColor(ColorConstants.black);
		add(labelFirstName, ToolbarLayout.ALIGN_CENTER);
		labelName.setForegroundColor(ColorConstants.darkGray);
		add(labelName, ToolbarLayout.ALIGN_CENTER);
		setForegroundColor(ColorConstants.darkGray);
		setBackgroundColor(ColorConstants.lightGray);
		setBorder(new LineBorder(1)); setOpaque(true);
	}
	
	public void setName(String text) {
		labelName.setText(text);
	}
	
	public void setFirstName(String text) {
		labelFirstName.setText(text);
	}
	
	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect);
	}

}