/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Diagram extends Element {
    public static String PROP_NODE = "NODE";

    protected List nodes = new ArrayList();

    public void addNode(Node node) {
        nodes.add(node);
        fireStructureChange(PROP_NODE, nodes);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
        fireStructureChange(PROP_NODE, nodes);
    }

    public List getNodes() {
        return nodes;
    }

    public InputStream getAsStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(this);
        out.close();
        InputStream istream = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return istream;
    }

    public static Diagram makeFromStream(InputStream istream) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(istream);
        Diagram diagram = (Diagram) ois.readObject();
        ois.close();
        return diagram;
    }

}