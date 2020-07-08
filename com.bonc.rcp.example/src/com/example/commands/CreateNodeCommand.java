/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.example.model.Diagram;
import com.example.model.Node;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateNodeCommand extends Command {
    protected Diagram diagram;

    protected Node node;

    protected Point location;

    // setters

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void execute() {
        if (this.location != null) {
            this.node.setLocation(this.location);
        }
        this.diagram.addNode(this.node);
    }

    public String getLabel() {
        return "Create Node";
    }

    public void redo() {
        this.execute();
    }

    public void undo() {
        diagram.removeNode(node);
    }
}