/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.example.figures.NodeFigure;
import com.example.model.Node;
import com.example.policies.NodeDirectEditPolicy;
import com.example.policies.NodeEditPolicy;
import com.example.policies.NodeGraphicalNodeEditPolicy;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {

    protected DirectEditManager manager;

    public void performRequest(Request req) {
        if (req.getType().equals(RequestConstants.REQ_DIRECT_EDIT)) {
            if (manager == null) {
                NodeFigure figure = (NodeFigure) getFigure();
                manager = new NodeDirectEditManager(this, TextCellEditor.class, new NodeCellEditorLocator(figure));
            }
            manager.show();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Node.PROP_LOCATION))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Node.PROP_NAME))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Node.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(Node.PROP_OUTPUTS))
            refreshSourceConnections();
    }

    protected IFigure createFigure() {
        return new NodeFigure();
    }

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NodeDirectEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalNodeEditPolicy());
    }

    public void activate() {
        if (isActive()) {
            return;
        }
        super.activate();
        ((Node) getModel()).addPropertyChangeListener(this);
    }

    public void deactivate() {
        if (!isActive()) {
            return;
        }
        super.deactivate();
        ((Node) getModel()).removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
        Node node = (Node) getModel();
        Point loc = node.getLocation();
        Dimension size = new Dimension(150, 40);
        Rectangle rectangle = new Rectangle(loc, size);
        ((NodeFigure) this.getFigure()).setName(((Node) this.getModel()).getName());
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    }

    //------------------------------------------------------------------------
    // Abstract methods from NodeEditPart

    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    protected List getModelSourceConnections() {
        return ((Node) this.getModel()).getOutgoingConnections();
    }

    protected List getModelTargetConnections() {
        return ((Node) this.getModel()).getIncomingConnections();
    }

}