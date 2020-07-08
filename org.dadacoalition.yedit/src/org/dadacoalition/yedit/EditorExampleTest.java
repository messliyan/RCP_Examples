package org.dadacoalition.yedit;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
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

/*
 * example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class EditorExampleTest {


	public static void open(Display display) {
		
		try {
            //创建工程
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("TestYeditProject");
            if (!project.exists())
               project.create(null);
           if (!project.isOpen())
                project.open(null);
            
            //创建文件
            IFile java_file = project.getFile(new Path("/TestYedit.yml"));
            if (java_file.exists()) {
            	java_file.delete(true, null);
			}
            InputStream inputStreamJava = new ByteArrayInputStream("key : value".getBytes());
            if (!java_file.exists())
                java_file.create(inputStreamJava, false, null);
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, "myplugin", 101, "创建资源失败", e);
          
       }
try {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("TestYeditProject");
            
            IFile java_file = project.getFile(new Path("/TestYedit.yml"));
            IDE.openEditor(page, java_file,"org.dadacoalition.yedit.editor.YEdit");            
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, "myplugin", 102, "打开工作区内文件出错", e);
         
         }
	}
}
