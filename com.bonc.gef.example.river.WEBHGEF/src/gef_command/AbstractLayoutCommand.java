package gef_command;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
//λ���ƶ�
public abstract class AbstractLayoutCommand extends Command {
	public abstract void setConstraint(Rectangle rect);
	public abstract void setModel(Object model);
}