package org.eclipse.ui.examples.rcp.texteditor;

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

public class XMLEditorExampleTest {


	public static void open(Display display) {
		
		try {
            //创建工程
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("TestXMLEditorProject");
            if (!project.exists())
               project.create(null);
           if (!project.isOpen())
                project.open(null);
            
            //创建文件
            IFile java_file = project.getFile(new Path("/TestXMLEditor.XML"));
            if (java_file.exists()) {
            	java_file.delete(true, null);
			}
            InputStream inputStreamJava = new ByteArrayInputStream("<html>\\r\\n 	<body>这是一个html\\r\\n  </body>\\r\\n </html>".getBytes());
            if (!java_file.exists())
                java_file.create(inputStreamJava, false, null);
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, "myplugin", 101, "创建资源失败", e);
          
       }
try {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("TestXMLEditorProject");
            
            IFile java_file = project.getFile(new Path("/TestXMLEditor.XML"));
            IDE.openEditor(page, java_file, "org.eclipse.ui.examples.rcp.texteditor.editors.xml.XMLEditor");            
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, "myplugin", 102, "打开工作区内文件出错", e);
         
         }
////https://blog.csdn.net/soszou/article/details/11152031	
////开发区间外的编辑器不能指定编辑器打开 使用默认编辑器
//File file2 = new File("C:\\Users\\PCPC\\Desktop\\新建文本文档 (2).yml");
//IFileStore fileStore = EFS.getLocalFileSystem().getStore(new org.eclipse.core.runtime.Path(file2.getAbsolutePath()));
//IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//try {
//IDE.openEditorOnFileStore(page, fileStore);
//
//} catch (PartInitException e) {
//
//}
	}
}
