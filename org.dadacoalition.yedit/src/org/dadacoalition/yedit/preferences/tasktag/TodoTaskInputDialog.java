/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Sergey Prigogin (Google)
 *******************************************************************************/
package org.dadacoalition.yedit.preferences.tasktag;

import java.util.ArrayList;
import java.util.List;

import org.dadacoalition.yedit.editor.TaskTagPreference;
import org.dadacoalition.yedit.preferences.PreferenceConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog to enter a new task tag.
 */
public class TodoTaskInputDialog extends StatusDialog {
    
    private class CompilerTodoTaskInputAdapter implements IDialogFieldListener {

        public void dialogFieldChanged(DialogField field) {
            doValidation();
        }           
    }
    
    private final StringDialogField fNameDialogField;
    private final ComboDialogField fPriorityDialogField;
    
    private final List<String> fExistingNames;
        
    public TodoTaskInputDialog(Shell parent, TaskTagPreference task, List<TaskTagPreference> existingEntries) {
        super(parent);
        
        fExistingNames = new ArrayList<String>(existingEntries.size());
        for (int i = 0; i < existingEntries.size(); i++) {
            TaskTagPreference curr = existingEntries.get(i);
            if (!curr.equals(task)) {
                fExistingNames.add(curr.tag);
            }
        }
        
        if (task == null) {
            setTitle("New"); 
        } else {
            setTitle("Edit"); 
        }

        CompilerTodoTaskInputAdapter adapter = new CompilerTodoTaskInputAdapter();

        fNameDialogField = new StringDialogField();
        fNameDialogField.setLabelText("Name"); 
        fNameDialogField.setDialogFieldListener(adapter);
        
        fNameDialogField.setText((task != null) ? task.tag : ""); //$NON-NLS-1$
        
        String[] items = new String[] {
            "High", 
            "Normal", 
            "Low"
        };
        
        fPriorityDialogField = new ComboDialogField(SWT.READ_ONLY);
        fPriorityDialogField.setLabelText("Priority"); 
        fPriorityDialogField.setItems(items);
        if (task != null) {
            if (PreferenceConstants.TASK_PRIORITY_HIGH.equals(task.severity)) {
                fPriorityDialogField.selectItem(0);
            } else if (PreferenceConstants.TASK_PRIORITY_NORMAL.equals(task.severity)) {
                fPriorityDialogField.selectItem(1);
            } else {
                fPriorityDialogField.selectItem(2);
            }
        } else {
            fPriorityDialogField.selectItem(1);
        }
    }
    
    public TaskTagPreference getResult() {
        TaskTagPreference task = new TaskTagPreference();
        task.tag = fNameDialogField.getText().trim();
        switch (fPriorityDialogField.getSelectionIndex()) {
        case 0:
            task.severity = PreferenceConstants.TASK_PRIORITY_HIGH;
            break;
        case 1:
            task.severity = PreferenceConstants.TASK_PRIORITY_NORMAL;
            break;
        default:
            task.severity = PreferenceConstants.TASK_PRIORITY_LOW;
            break;              
        }
        return task;
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        
        Composite inner = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.numColumns = 2;
        inner.setLayout(layout);
        
        fNameDialogField.doFillIntoGrid(inner, 2);
        fPriorityDialogField.doFillIntoGrid(inner, 2);
        
        LayoutUtil.setHorizontalGrabbing(fNameDialogField.getTextControl(null), true);
        LayoutUtil.setWidthHint(fNameDialogField.getTextControl(null), convertWidthInCharsToPixels(45));
        
        fNameDialogField.postSetFocusOnDialogField(parent.getDisplay());
        
        applyDialogFont(composite);     
        
        //PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, ICHelpContextIds.TODO_TASK_INPUT_DIALOG);
        
        return composite;
    }
        
    private void doValidation() {
        StatusInfo status = new StatusInfo();
        String newText = fNameDialogField.getText();
        if (newText.isEmpty()) {
            status.setError("Enter task tag name."); 
        } else {
            if (newText.indexOf(',') != -1) {
                status.setError("Name cannot contain a comma."); 
            } else if (fExistingNames.contains(newText)) {
                status.setError("An entry with the same name already exists."); 
            } else if (Character.isWhitespace(newText.charAt(0)) || Character.isWhitespace(newText.charAt(newText.length() - 1))) {
                status.setError("Name cannot begin or end with a whitespace."); 
            }
        }
        updateStatus(status);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        //PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, ICHelpContextIds.TODO_TASK_INPUT_DIALOG);
    }
}

