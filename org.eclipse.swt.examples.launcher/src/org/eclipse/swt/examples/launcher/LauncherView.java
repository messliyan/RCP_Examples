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
package org.eclipse.swt.examples.launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


/**
 * 希望运行按钮点击事件和双击事件可以走同一个判断
 * 以后东西多了 希望用switch代替if
 * Launcher uses <code>org.eclipse.swt</code> 
 * to launch the other registered examples.
 * 
 * @see ViewPart
 */
public class LauncherView extends ViewPart {
	private Shell workbenchShell;
	
	private Tree launchTree;
	private Text descriptionText;
	private Button runButton;

	/**
	 * Constructs a LauncherView.
	 */
	public LauncherView() {
		LauncherPlugin.initResources();
	}

	/**
	 * Creates the example.
	 * 
	 * @see ViewPart#createPartControl
	 */
	@Override
	public void createPartControl(Composite parent) {
		workbenchShell = getSite().getShell();
		parent.setLayout(new SplitLayout());
				
		Group launchGroup = new Group(parent, SWT.NONE);
		launchGroup.setText(LauncherPlugin.getResourceString("view.launchGroup.text"));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		launchGroup.setLayout(gridLayout);

		launchTree = new Tree(launchGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gridData.horizontalSpan = 2;
		launchTree.setLayoutData(gridData);
		launchTree.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				final ItemDescriptor item = getSelectedItem();
				setDescriptionByItem(item);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				final ItemDescriptor item = getSelectedItem();
				setDescriptionByItem(item);
				if (item.getMainType() == null && item.getView() == null&&item.getEditor() == null) {
					// Category selected, so just expand/colapse the node
					TreeItem treeItem = (TreeItem) event.item;
					boolean expanded = treeItem.getExpanded();
					if (treeItem != null) treeItem.setExpanded(!expanded);
					treeItem.setImage(LauncherPlugin.images[expanded ? LauncherPlugin.liClosedFolder : LauncherPlugin.liOpenFolder]);
				} else {
					launchItem(getSelectedItem());
				}
			}
		});
		launchTree.addTreeListener(new TreeListener() {
			@Override
			public void treeCollapsed(TreeEvent event) {
				final TreeItem item = (TreeItem) event.item;
				if (item == null) return;
				item.setImage(LauncherPlugin.images[LauncherPlugin.liClosedFolder]);
			}
			@Override
			public void treeExpanded(TreeEvent event) {
				final TreeItem item = (TreeItem) event.item;
				if (item == null) return;
				item.setImage(LauncherPlugin.images[LauncherPlugin.liOpenFolder]);
			}
		});

		runButton = new Button(launchGroup, SWT.PUSH);
		runButton.setText(LauncherPlugin.getResourceString("view.launchButton.text"));
		runButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				launchItem(getSelectedItem());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		Group descriptionGroup = new Group(parent, SWT.NONE);
		descriptionGroup.setText(LauncherPlugin.getResourceString("view.descriptionGroup.text"));
		descriptionGroup.setLayout(new FillLayout());
		
		descriptionText = new Text(descriptionGroup, SWT.MULTI | SWT.BORDER |
			SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);

		setDescriptionByItem(null);
		setItemDescriptors(LauncherPlugin.getLaunchItemTree());
	}

	/**
	 * Called when we must grab focus.
	 * 
	 * @see org.eclipse.ui.part.ViewPart#setFocus
	 */
	@Override
	public void setFocus()  {
		launchTree.setFocus();
		runButton.getShell().setDefaultButton(runButton);
	}

	/**
	 * Called when the View is to be disposed
	 */	
	@Override
	public void dispose() {
		workbenchShell = null;
		launchTree = null;
		descriptionText = null;
		runButton = null;		
		super.dispose();
	}

	/**
	 * Installs a new launch list.
	 * 初始化节点
	 * @param newRoot the new tree of launch items for the UI
	 */
	public void setItemDescriptors(final ItemTreeNode newRoot) {
		if (workbenchShell == null) return;
		workbenchShell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				if ((launchTree == null) || (launchTree.isDisposed())) return;
				launchTree.removeAll();
			
				for (ItemTreeNode node = newRoot.getFirstChild(); node != null;
					node = node.getNextSibling()) {
					doNode(node, new TreeItem(launchTree, SWT.NONE)); // top-level TreeItem
				}
			}
			
		
			private void addGroup(TreeItem parent, ItemTreeNode node) {
				for (;node != null; node = node.getNextSibling()) {
					doNode(node, new TreeItem(parent, SWT.NONE)); // TreeItem at depth > 0
				}
			}
			//创建节点的图片和文字和Data
			private void doNode(ItemTreeNode node, TreeItem treeItem) {
				final ItemDescriptor item = node.getDescriptor();
				treeItem.setText(item.getName());
				treeItem.setData(item);
				if (node.getDescriptor().isFolder()) {
					treeItem.setExpanded(false);
					treeItem.setImage(LauncherPlugin.images[LauncherPlugin.liClosedFolder]);
				} else {
					treeItem.setImage(node.getDescriptor().getIcon());
				}
				addGroup(treeItem, node.getFirstChild());
			}
		});
	}

	/**
	 * Runs the specified launch item.
	 * 
	 * @param itemDescriptor the launch item to execute
	 */
	private void launchItem(ItemDescriptor itemDescriptor) {
		/* Case 1: The launch item is a view */
		String pluginViewId = itemDescriptor.getView ();
		if (pluginViewId != null) {
			final IWorkbenchPart workbenchPart = this;
			final IWorkbenchPartSite workbenchPartSite = workbenchPart.getSite();
			final IWorkbenchPage workbenchPage = workbenchPartSite.getPage();
			try {
				workbenchPage.showView(pluginViewId);
			} catch (PartInitException e) {
				LauncherPlugin.logError(LauncherPlugin.getResourceString("run.error.Invocation"), e);
			}
			return;
		}
		/* Case 3: The launch item is a standalone editor 启动器是一个编辑器*/ 
		String pluginEditorId = itemDescriptor.getEditor();
		if (pluginEditorId != null) {
			try {
			//需要有一个空构造函数 然后设置其参数
			IEditorInput editorInputinstance = (IEditorInput)itemDescriptor.createEdtorInputItem();
			IWorkbenchPage page =PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();		
				page.openEditor(editorInputinstance, pluginEditorId, true);
			} catch (PartInitException e) {
				LauncherPlugin.logError(LauncherPlugin.getResourceString("run.error.Invocation"), e);
			} catch (Exception e) {
				LauncherPlugin.logError(LauncherPlugin.getResourceString("run.error.CouldNoteEitorInputclass"), e);
			}	
			return;
		}
		
		/* Case 2: The launch item is a standalone program */
		if (workbenchShell == null) return;
		try {
			Object instance = itemDescriptor.createItemInstance();
			if (instance != null) {
				Display display = workbenchShell.getDisplay();
				Method openMethod = instance.getClass().getDeclaredMethod("open", new Class[] {Display.class});
				openMethod.invoke(instance, display);
			}
		} catch (NoSuchMethodException e) {
			LauncherPlugin.logError(LauncherPlugin.getResourceString("run.error.DoesNotImplementMethod"), null);
			open( itemDescriptor,workbenchShell );
		} catch (Exception e) {
			LauncherPlugin.logError(LauncherPlugin.getResourceString("run.error.CouldNotInstantiateClass"), e);
		}		
	}
	//打开open方法
	private static void open(ItemDescriptor itemDescriptor,Shell workbenchShell ) {
		Method method = null;
		Class<?> clazz = null;
		
		try {
			Object instance = itemDescriptor.createItemInstance();
			clazz=instance.getClass();
			method = clazz.getMethod("open",new Class[] {Display.class});
	
		} catch (NoSuchMethodException | CoreException e) {
			System.out.println("   Did not sttic open(display)");
		}
		if (method != null) {
			try {
				method.invoke(clazz, workbenchShell.getDisplay());
			} catch (IllegalAccessException e3) {
				System.out.println("   Failed to launch (illegal access)");
			} catch (IllegalArgumentException e3) {
				System.out.println("   Failed to launch (illegal argument to open)");
			} catch (InvocationTargetException e3) {
				System.out.println("   Exception in Snippet: " + e3.getTargetException());
			}
		}
	}
	/**
	 * Obtains the selected launch item.
	 * 
	 * @return the currently selected ItemDescriptor
	 */
	private ItemDescriptor getSelectedItem() {
		final TreeItem[] selections = launchTree.getSelection();
		if (selections.length == 0) return null;
		final ItemDescriptor itemDescriptor = (ItemDescriptor) selections[0].getData();
		return itemDescriptor;
	}
	
	/**
	 * Sets the currently visible description text to reflect that of a particular ItemDescriptor.
	 *设置描述内容和运行按钮是否可用
	 * @param itemDescriptor the launch item whose description is to be displayed, or null if none
	 */
	private void setDescriptionByItem(ItemDescriptor itemDescriptor) {
		String description;
		if (itemDescriptor == null) {
			description = LauncherPlugin.getResourceString("launchitem.Null.description");
			if (runButton != null) runButton.setEnabled(false);
		} else {
			description = itemDescriptor.getDescription();
			if (description == null)
				description = LauncherPlugin.getResourceString("launchitem.Missing.description");
			if (runButton != null) {
				runButton.setEnabled(itemDescriptor.getView() != null || itemDescriptor.getMainType() != null ||itemDescriptor.getEditor() != null);
			}
		}
		descriptionText.setText(description);
	}
}
