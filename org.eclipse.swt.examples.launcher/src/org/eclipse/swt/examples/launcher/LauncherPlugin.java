/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
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


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * 修改的麻烦点 1、扩展点和参数的对应 希望可以直接自己生成
 * 				2、判断view和editor的语句很多 还不统一 	希望可以封装为一个判断方法
 * 				3、希望可以新建接口 让查件去实现接口 规格更加统一 不怕出错
 * The main plugin class to be used in the desktop.
 */
public class LauncherPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static LauncherPlugin plugin;
	private static ResourceBundle resourceBundle;

	public static final String
		LAUNCH_ITEMS_POINT_ID = "org.eclipse.swt.examples.launcher.launchItems",
		LAUNCH_ITEMS_XML_CATEGORY = "category",
		LAUNCH_ITEMS_XML_ITEM = "item",
		LAUNCH_ITEMS_XML_ITEM_ICON = "icon",
		LAUNCH_ITEMS_XML_ITEM_DESCRIPTION = "description",
		LAUNCH_ITEMS_XML_PROGRAM = "program",
		LAUNCH_ITEMS_XML_PROGRAM_PLUGIN = "pluginId",
		LAUNCH_ITEMS_XML_PROGRAM_CLASS = "mainClass",
		LAUNCH_ITEMS_XML_VIEW = "view",
		LAUNCH_ITEMS_XML_VIEW_VIEWID = "viewId",
		LAUNCH_ITEMS_XML_ATTRIB_ID = "id",
		LAUNCH_ITEMS_XML_ATTRIB_NAME = "name",
		LAUNCH_ITEMS_XML_ATTRIB_ENABLED = "enabled",
		LAUNCH_ITEMS_XML_ATTRIB_CATEGORY = "category",
		LAUNCH_ITEMS_XML_VALUE_TRUE = "true",
		LAUNCH_ITEMS_XML_VALUE_FALSE = "false",	
		LAUNCH_ITEMS_XML_EDITOR_EDITOR = "editor",
		LAUNCH_ITEMS_XML_EDITOR_EDITORID = "editorId",
		LAUNCH_ITEMS_XML_EDITOR_EDITORINPUT = "editorInputclass";
	static final int
		liClosedFolder = 0,
		liOpenFolder = 1,
		liGenericExample = 2;
	static final String[] imageLocations = {
		"icons/closedFolder.gif",
		"icons/openFolder.gif",
		"icons/generic_example.gif" };
	static Image images[];

	/**
	 * Constructs the LauncherPlugin.
	 */
	public LauncherPlugin() {
		super();
		plugin = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		resourceBundle = Platform.getResourceBundle(getBundle());
	}
	
	/**
	 * Clean up
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		freeResources();
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static LauncherPlugin getDefault() {
		return plugin;
	}

	/**
	 * Loads the resources
	 */
	public static void initResources() {
		if (images == null) {
			images = new Image[imageLocations.length];
				
			for (int i = 0; i < imageLocations.length; ++i) {
				images[i] = getImageFromPlugin(plugin.getBundle(), imageLocations[i]);
				if (images[i] == null) {
					freeResources();
					logError(getResourceString("error.CouldNotLoadResources"), null);
					throw new IllegalStateException();
				}
			}
		}	
	}

	/**
	 * Frees the resources
	 */
	public static void freeResources() {
		if (images != null) {
			for (Image image : images) {
				if (image != null) image.dispose();
			}
			images = null;
		}
	}

	/**
	 * Log an error to the ILog for this plugin
	 * 
	 * @param message the localized error message text
	 * @param exception the associated exception, or null
	 */
	public static void logError(String message, Throwable exception) {
		plugin.getLog().log(new Status(
			IStatus.ERROR, plugin.getBundle().getSymbolicName(), 0, message, exception));
	}

	/**
	 * Returns a string from the resource bundle.
	 * We don't want to crash because of a missing String.
	 * Returns the key if not found.
	 */
	public static String getResourceString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}			
	}

	/**
	 * Returns a string from the resource bundle and binds it
	 * with the given arguments. If the key is not found,
	 * return the key.
	 */
	public static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}

	/**
	 * Constructs a list of available programs from registered extensions.
	 * 
	 * @return an ItemTreeNode representing the root of a tree of items (the root is not to be displayed)
	 */
	public static ItemTreeNode getLaunchItemTree() {
		ItemTreeNode categoryTree =
			new ItemTreeNode(new ItemDescriptor("<<Root>>", "<<Root>>", null, null, null,null,null, null, null, null));

		// get the platform's public plugin registry
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		// retrieve all configuration elements registered at our launchItems extension-point
		IConfigurationElement[] configurationElements =
			extensionRegistry.getConfigurationElementsFor(LAUNCH_ITEMS_POINT_ID);//所有实现此插件的扩展点集合
			
		
		if (configurationElements == null || configurationElements.length == 0) {
			logError(getResourceString("error.CouldNotFindRegisteredExtensions"), null);
			return categoryTree;
		}
		
		/* Collect all launch categories -- coalesce those with same ID */
		HashMap<String, ItemTreeNode> idMap = new HashMap<>();
		//遍历贡献者插件id 创建它的category集合（去掉重复的类型 ）和描述符（icon为空）
		for (IConfigurationElement ce: configurationElements) {
			final String ceName = ce.getName();
			final String attribId = getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_ID, null);
			
			if (idMap.containsKey(attribId)) continue;
			if (ceName.equalsIgnoreCase(LAUNCH_ITEMS_XML_CATEGORY)) {
				final String attribName = getItemName(ce); 
				ItemDescriptor theDescriptor = new ItemDescriptor(attribId, attribName,
					getItemDescription(ce), null, null, null,null, null,null, ce);
				idMap.put(attribId, new ItemTreeNode(theDescriptor));
				 ItemTreeNode theNode = idMap.get(attribId);
				addItemByCategory(ce, categoryTree, theNode, idMap);
			}
		}
		
		/* Generate launch category hierarchy */
//		Set<String> tempIdSet = new HashSet<>(); // used to prevent duplicates from being entered into the tree
//		//遍历贡献者插件id 创建它的category节点 可以和上一个合并
//		for (IConfigurationElement ce : configurationElements) {
//			final String ceName = ce.getName();
//			final String attribId = getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_ID, null);
//			
//			if (tempIdSet.contains(attribId)) continue;
//			if (ceName.equalsIgnoreCase(LAUNCH_ITEMS_XML_CATEGORY)) {
//				final ItemTreeNode theNode = idMap.get(attribId);
//				addItemByCategory(ce, categoryTree, theNode, idMap);
//				tempIdSet.add(attribId);
//			}
//		}
		
		/* Generate program tree */
		for (IConfigurationElement ce : configurationElements) {
			final String ceName = ce.getName();
			final String attribId = getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_ID, null);

			if (idMap.containsKey(attribId)) continue;
			if (ceName.equalsIgnoreCase(LAUNCH_ITEMS_XML_CATEGORY)) {
				
				// ignore	category节点忽略
			} else if (ceName.equalsIgnoreCase(LAUNCH_ITEMS_XML_ITEM)) {
				
				//得到enabled属性 默认为true
				final String enabled = getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_ENABLED, 
					LAUNCH_ITEMS_XML_VALUE_TRUE);
				
				if (enabled.equalsIgnoreCase(LAUNCH_ITEMS_XML_VALUE_FALSE)) continue;
				
				//创建item节点的描述符
				ItemDescriptor theDescriptor = createItemDescriptor(ce, attribId);				
			
				if (theDescriptor != null) {
					final ItemTreeNode theNode = new ItemTreeNode(theDescriptor);
					addItemByCategory(ce, categoryTree, theNode, idMap);
					idMap.put(attribId, theNode);
				}
			}
		}
		
		return categoryTree;
	}

				
	/**
	 * Adds an item to the category tree.
	 */
	private static void addItemByCategory(IConfigurationElement ce, ItemTreeNode root,
		ItemTreeNode theNode, HashMap<String, ItemTreeNode> idMap) {
		final String attribCategory = getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_CATEGORY, null);
				
		// locate the parent node
		ItemTreeNode parentNode = null;
		if (attribCategory != null) {
			parentNode = idMap.get(attribCategory);
		}
		if (parentNode == null) parentNode = root;
				
		// add the item
		parentNode.addSortedNode(theNode);
	}

	/**
	 * Creates an ItemDescriptor from an XML definition.
	 * 
	 * @param ce the IConfigurationElement describing the item
	 * @param attribId the attribute id
	 * @return a new ItemDescriptor, or null if an error occurs
	 */
	private static ItemDescriptor createItemDescriptor(IConfigurationElement ce, String attribId) {
		final String attribName = getItemName(ce);
		final Image  attribIcon = getItemIcon(ce);
		final String attribDescription = getItemDescription(ce);

		IConfigurationElement viewCE = getItemElement(ce, LAUNCH_ITEMS_XML_VIEW);
		IConfigurationElement programCE = getItemElement(ce, LAUNCH_ITEMS_XML_PROGRAM);
		IConfigurationElement editorCE = getItemElement(ce, LAUNCH_ITEMS_XML_EDITOR_EDITOR);
		//view节点是否为空
		if (viewCE != null) {
			//Item is a view
			final String attribView = getItemAttribute(viewCE, LAUNCH_ITEMS_XML_VIEW_VIEWID, null);		
			if (attribView == null) {
				logError(getResourceString("error.IncompleteViewLaunchItem",
					new Object[] { attribId } ), null);
				return null;
			}
			return new ItemDescriptor(attribId, attribName, attribDescription,
					attribIcon, attribView, null, null,null,null, viewCE);
		} else
		if (programCE != null) {
				final String attribPluginId = getItemAttribute(programCE, LAUNCH_ITEMS_XML_PROGRAM_PLUGIN, null);
				final String attribClass    = getItemAttribute(programCE, LAUNCH_ITEMS_XML_PROGRAM_CLASS, null);
				if (attribClass == null || attribPluginId == null) {
					logError(getResourceString("error.IncompleteProgramLaunchItem",
					new Object[] { attribId } ), null);
					return null;
				}
				return new ItemDescriptor(attribId, attribName, attribDescription,
					attribIcon, null, attribClass,null,null, attribPluginId, programCE);
		} else	if(editorCE != null){
			final String attribEditorId = getItemAttribute(editorCE, LAUNCH_ITEMS_XML_EDITOR_EDITORID, null);
			final String attribInputclass    = getItemAttribute(editorCE, LAUNCH_ITEMS_XML_EDITOR_EDITORINPUT, null);
				
			if (attribEditorId == null || attribInputclass == null) {
				logError(getResourceString("error.IncompleteEditorLaunchItem",
				new Object[] { attribId } ), null);
				return null;
			}
			return new ItemDescriptor(attribId, attribName, attribDescription,		
				attribIcon, null, null,attribEditorId, attribInputclass,null, editorCE);
		}else {
				
				logError(getResourceString("error.IncompleteLaunchItem",
					new Object[] { attribId } ), null);
				return null;
		}
	
	}

	/**
	 * Returns the first instance of a particular child XML element.
	 * 
	 * @param ce the IConfigurationElement parent
	 * @param element the name of the element to fetch
	 * @return the element's IConfigurationElement, or null if not found
	 */
	private static IConfigurationElement getItemElement(IConfigurationElement ce, String element) {
		IConfigurationElement[] elementCEs = ce.getChildren(element);
		return (elementCEs != null && elementCEs.length != 0) ? elementCEs[0] : null;
	}

	/**
	 * Returns the value of an XML attribute for an item.
	 * 
	 * @param ce the IConfigurationElement describing the item
	 * @param attribute the attribute to fetch
	 * @param defaultValue the value to return if the attribute is not found
	 * @return the attribute value
	 */
	private static String getItemAttribute(IConfigurationElement ce, String attribute, String defaultValue) {
		String value = ce.getAttribute(attribute);
		return (value != null) ? value : defaultValue;
	}

	/**
	 * Returns the description string given the IConfigurationElement for an item.
	 * 
	 * @param ce the IConfigurationElement describing the item
	 * @return a newline-delimited string that describes this item, or null if none
	 */
	private static String getItemDescription(IConfigurationElement ce) {
		String description = getItemAttribute(ce, LAUNCH_ITEMS_XML_ITEM_DESCRIPTION, "");
		return (description.length() == 0) ? null : description;
	}

	/**
	 * Returns the name of an item.
	 * 
	 * @param ce the IConfigurationElement describing the item
	 * @return the attribute value
	 */
	private static String getItemName(IConfigurationElement ce) {
		return getItemAttribute(ce, LAUNCH_ITEMS_XML_ATTRIB_NAME,
			getResourceString("launchitem.Missing.name"));
	}


	/**
	 * Returns the icon for an item.
	 * 
	 * @param ce the IConfigurationElement describing the item
	 * @return an icon
	 */
	private static Image getItemIcon(IConfigurationElement ce) {
		String iconPath = getItemAttribute(ce, LAUNCH_ITEMS_XML_ITEM_ICON, "");
		if (iconPath.length() != 0) {
			String symbolicName = ce.getDeclaringExtension().getContributor().getName();
			Bundle bundle = Platform.getBundle(symbolicName);
			Image icon = getImageFromPlugin(bundle, iconPath);
			if (icon != null) {
				Image[] newImages = new Image[images.length + 1];
				System.arraycopy(images, 0, newImages, 0, images.length);
				newImages[images.length] = icon;
				images = newImages;
				return icon;
			}
		}
		return images[liGenericExample];
	}

	/**
	 * Gets an image from a path relative to the plugin install directory.
	 *
	 * @param bundle the plugin descriptor for the plugin with the image
	 * @param iconPath the path relative to the install directory
	 * @return the image, or null if not found
	 */
	private static Image getImageFromPlugin(Bundle bundle, String iconPath) {
		InputStream is = null;
		try {
			URL installUrl = bundle.getEntry("/");
			URL url = new URL(installUrl, iconPath);
			is = url.openConnection().getInputStream();
			ImageData source = new ImageData(is);
			ImageData mask = source.getTransparencyMask();
			Image image = new Image(null, source, mask);
			return image;
		} catch (Throwable ex) {
			return null;
		} finally {
			try {
				if (is != null) is.close();
			} catch (IOException e) {
			}
		}
	}
}
