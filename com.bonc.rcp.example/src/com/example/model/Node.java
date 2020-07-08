/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Node extends Element implements IPropertySource {
    final public static String PROP_LOCATION = "LOCATION";

    final public static String PROP_NAME = "NAME";

    final public static String PROP_VISIBLE = "VISIBLE";

    final public static String PROP_INPUTS = "INPUTS";

    final public static String PROP_OUTPUTS = "OUTPUTS";

    protected Point location = new Point(0, 0);

    protected String name = "Node";

    protected boolean visible = true;

    protected IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
            new TextPropertyDescriptor(PROP_NAME, "Name"),
            new ComboBoxPropertyDescriptor(PROP_VISIBLE, "Visible", new String[] { "true", "false" }) };

    protected List outputs = new ArrayList(5);

    protected List inputs = new ArrayList(5);

    public void addInput(Connection connection) {
        this.inputs.add(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

    public void addOutput(Connection connection) {
        this.outputs.add(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }

    public List getIncomingConnections() {
        return this.inputs;
    }

    public List getOutgoingConnections() {
        return this.outputs;
    }

    public void removeInput(Connection connection) {
        this.inputs.remove(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

    public void removeOutput(Connection connection) {
        this.outputs.remove(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (this.visible == visible) {
            return;
        }
        this.visible = visible;
        firePropertyChange(PROP_VISIBLE, null, Boolean.valueOf(visible));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name.equals(name)) {
            return;
        }
        this.name = name;
        firePropertyChange(PROP_NAME, null, name);
    }

    public void setLocation(Point p) {
        if (this.location.equals(p)) {
            return;
        }
        this.location = p;
        firePropertyChange(PROP_LOCATION, null, p);
    }

    public Point getLocation() {
        return location;
    }

    //------------------------------------------------------------------------
    // Abstract methods from IPropertySource

    public Object getEditableValue() {
        return this;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return descriptors;
    }

    public Object getPropertyValue(Object id) {
        if (PROP_NAME.equals(id))
            return getName();
        if (PROP_VISIBLE.equals(id))
            return isVisible() ? new Integer(0) : new Integer(1);
        return null;
    }

    public boolean isPropertySet(Object id) {
        return true;
    }

    public void resetPropertyValue(Object id) {

    }

    public void setPropertyValue(Object id, Object value) {
        if (PROP_NAME.equals(id))
            setName((String) value);
        if (PROP_VISIBLE.equals(id))
            setVisible(((Integer) value).intValue() == 0);
    }
}