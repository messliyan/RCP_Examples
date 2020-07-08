/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.example.model.Node;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodeTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

    public NodeTreeEditPart(Object model) {
        super(model);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        refreshVisuals();
    }

    public void activate() {
        super.activate();
        ((Node) getModel()).addPropertyChangeListener(this);
    }

    public void deactivate() {
        super.deactivate();
        ((Node) getModel()).removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
        setWidgetText(((Node) getModel()).getName());
    }

}