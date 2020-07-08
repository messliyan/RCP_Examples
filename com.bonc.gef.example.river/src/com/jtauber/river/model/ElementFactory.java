package com.jtauber.river.model;

import org.eclipse.gef.requests.CreationFactory;

public class ElementFactory implements CreationFactory {

   private Object template;

   public ElementFactory(Object template) {
      this.template = template;
   }

   /**
    * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
    */
   public Object getNewObject() {
      try {
         return ((Class) template).newInstance();
      }
      catch (Exception e) {
         return null;
      }
   }

   /**
    * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
    */
   public Object getObjectType() {
      return template;
   }
}