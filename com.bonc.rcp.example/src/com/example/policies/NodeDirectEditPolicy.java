/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.example.commands.RenameNodeCommand;
import com.example.figures.NodeFigure;
import com.example.model.Node;


/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodeDirectEditPolicy extends DirectEditPolicy{

    protected Command getDirectEditCommand(DirectEditRequest request) {
        RenameNodeCommand cmd = new RenameNodeCommand();
        cmd.setNode((Node) getHost().getModel());
        cmd.setName((String) request.getCellEditor().getValue());
        return cmd;
    }
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String) request.getCellEditor().getValue();
        ((NodeFigure) getHostFigure()).setName(value);
    }
}
