/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.example.commands.CreateConnectionCommand;
import com.example.commands.ReconnectSourceCommand;
import com.example.model.Connection;
import com.example.model.Node;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        CreateConnectionCommand command = (CreateConnectionCommand) request.getStartCommand();
        command.setTarget((Node) getHost().getModel());
        return command;
    }

    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        CreateConnectionCommand command = new CreateConnectionCommand();
        command.setSource((Node) getHost().getModel());
        request.setStartCommand(command);
        return command;
    }

    protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectSourceCommand cmd = new ReconnectSourceCommand();
		cmd.setConnection((Connection)request.getConnectionEditPart().getModel());
		cmd.setSource((Node)getHost().getModel());
		return cmd;
    }

    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        return null;
    }
}