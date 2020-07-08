/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SashForm example snippet: create a sash form with three children
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet109 {
	static Display display;
	public static void main(String[] args) {
		display = new Display();
		open(display);
		beforedispose();
	}
	public static void beforedispose() {
		display.dispose();
	}

	public static void open(Display display) {
	Shell shell = new Shell(display);
	shell.setText("Snippet 109");
	shell.setLayout (new FillLayout());

	SashForm form = new SashForm(shell,SWT.HORIZONTAL);
	form.setLayout(new FillLayout());

	Composite child1 = new Composite(form,SWT.NONE);
	child1.setLayout(new FillLayout());
	new Label(child1,SWT.NONE).setText("Label in pane 1");

	Composite child2 = new Composite(form,SWT.NONE);
	child2.setLayout(new FillLayout());
	new Button(child2,SWT.PUSH).setText("Button in pane2");

	Composite child3 = new Composite(form,SWT.NONE);
	child3.setLayout(new FillLayout());
	new Label(child3,SWT.PUSH).setText("Label in pane3");

	form.setWeights(new int[] {30,40,30});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}

}
}
