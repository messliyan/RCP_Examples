/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.example.model.Connection;
import com.example.model.Node;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateConnectionCommand extends Command {

    protected Connection connection;

    protected Node source;

    protected Node target;

    public void setSource(Node source) {
        this.source = source;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    //------------------------------------------------------------------------
    // Overridden from Command

    public void execute() {
        connection = new Connection(source, target);
    }

    public String getLabel() {
        return "Create Connection";
    }

    public void redo() {
        this.source.addOutput(this.connection);
        this.target.addInput(this.connection);
    }

    public void undo() {
        this.source.removeOutput(this.connection);
        this.target.removeInput(this.connection);
    }

    public boolean canExecute() {
        if (source.equals(target))
            return false;
        // Check for existence of connection already
        List connections = this.source.getOutgoingConnections();
        for (int i = 0; i < connections.size(); i++) {
            if (((Connection) connections.get(i)).getTarget().equals(target))
                return false;
        }
        return true;
    }
}