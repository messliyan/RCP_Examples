/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.commands;

import org.eclipse.gef.commands.Command;

import com.example.model.Node;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RenameNodeCommand extends Command {

    private Node node;

    private String newName;

    private String oldName;

    public void setName(String name) {
        this.newName = name;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void execute() {
        oldName = this.node.getName();
        this.node.setName(newName);
    }

    public void redo() {
        node.setName(newName);
    }

    public void undo() {
        node.setName(oldName);
    }

    public String getLabel() {
        return "Rename Node";
    }
}