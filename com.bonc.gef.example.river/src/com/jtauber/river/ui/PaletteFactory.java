package com.jtauber.river.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;

import com.jtauber.river.model.Node;

public class PaletteFactory {

   private static PaletteContainer createControlGroup(PaletteRoot root) {
      PaletteGroup controlGroup = new PaletteGroup("Control Group");

      List entries = new ArrayList();
      ToolEntry tool = new SelectionToolEntry();
      entries.add(tool);
      root.setDefaultEntry(tool);

      tool = new ConnectionCreationToolEntry(
         "Connection",
         "Create a connection",
         null,
         null, /* small icon */
         null /* large icon */
      );
      entries.add(tool);


      controlGroup.addAll(entries);
      return controlGroup;
   }

   private static PaletteContainer createComponentsDrawer() {

      PaletteDrawer drawer = new PaletteDrawer("Components", null);

      List entries = new ArrayList();
      ToolEntry tool = new CombinedTemplateCreationEntry(
         "Node",
         "Create a new Node",
         Node.class,
         new SimpleFactory(Node.class),
         null, /* small icon */
         null /* large icon */
      );
      entries.add(tool);

      drawer.addAll(entries);
      return drawer;
   }

   private static List createCategories(PaletteRoot root) {
      List categories = new ArrayList();

      categories.add(createControlGroup(root));
      categories.add(createComponentsDrawer());

      return categories;
   }

   public static PaletteRoot createPalette() {
      PaletteRoot paletteRoot = new PaletteRoot();
      paletteRoot.addAll(createCategories(paletteRoot));
      return paletteRoot;
   }

}