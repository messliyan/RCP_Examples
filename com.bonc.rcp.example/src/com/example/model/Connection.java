/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.model;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Connection extends Element {

    private Node source;

    private Node target;

    public void setSource(Node source) {
        this.source = source;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Node getTarget() {
        return this.target;
    }

    public Node getSource() {
        return this.source;
    }

    /**
     * @param source
     * @param target
     */
    public Connection(Node source, Node target) {
        super();
        this.source = source;
        this.target = target;
        source.addOutput(this);
        target.addInput(this);
    }

}