/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodeFigure extends RectangleFigure {
    private String name;

    private RectangleFigure rectangleFigure;

    private Label label;

    public NodeFigure() {
        this.rectangleFigure = new RectangleFigure();
        this.label = new Label();
        this.add(rectangleFigure);
        this.add(label);
    }

    public String getText() {
        return this.label.getText();
    }

    public Rectangle getTextBounds() {
        return this.label.getTextBounds();
    }

    public void setName(String name) {
        this.name = name;
        this.label.setText(name);
        this.repaint();
    }

    //------------------------------------------------------------------------
    // Overridden methods from Figure

    public void setBounds(Rectangle rect) {
        super.setBounds(rect);
        this.rectangleFigure.setBounds(rect);
        this.label.setBounds(rect);
    }
}