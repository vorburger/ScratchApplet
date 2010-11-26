// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class DefiningPrims extends Primitives {

   static String[] primlist = new String[]{"make", "2", "define", "3", "let", "1", "thing", "1", "put", "3", "get", "2", "getp", "2", "plist", "1", "erplist", "1", "name?", "1", "defined?", "1", "clearname", "1", "quote", "1", "intern", "1"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_make(var2[0], var2[1], var3);
      case 1:
         return this.prim_define(var2[0], var2[1], var2[2], var3);
      case 2:
         return this.prim_let(var2[0], var3);
      case 3:
         return this.prim_thing(var2[0], var3);
      case 4:
         return this.prim_put(var2[0], var2[1], var2[2], var3);
      case 5:
         return this.prim_get(var2[0], var2[1], var3);
      case 6:
         return this.prim_get(var2[0], var2[1], var3);
      case 7:
         return this.prim_plist(var2[0], var3);
      case 8:
         return this.prim_erplist(var2[0], var3);
      case 9:
         return this.prim_namep(var2[0], var3);
      case 10:
         return this.prim_definedp(var2[0], var3);
      case 11:
         return this.prim_clearname(var2[0], var3);
      case 12:
         return this.prim_quote(var2[0], var3);
      case 13:
         return this.prim_intern(var2[0], var3);
      default:
         return null;
      }
   }

   Object prim_make(Object var1, Object var2, LContext var3) {
      Logo.setValue(Logo.aSymbol(var1, var3), var2, var3);
      return null;
   }

   Object prim_clearname(Object var1, LContext var2) {
      Logo.setValue(Logo.aSymbol(var1, var2), (Object)null, var2);
      return null;
   }

   Object prim_define(Object var1, Object var2, Object var3, LContext var4) {
      Symbol var5 = Logo.aSymbol(var1, var4);
      Object[] var6 = Logo.aList(var2, var4);
      Object[] var7 = Logo.aList(var3, var4);
      Ufun var8 = new Ufun(var6, var7);
      var5.fcn = new Function(var8, var6.length, 0);
      return null;
   }

   Object prim_let(Object var1, LContext var2) {
      Vector var3 = new Vector();
      if(var2.locals != null) {
         for(int var4 = 0; var4 < var2.locals.length; ++var4) {
            var3.addElement(var2.locals[var4]);
         }
      }

      MapList var6 = new MapList(Logo.aList(var1, var2));

      while(!var6.eof()) {
         Symbol var5 = Logo.aSymbol(var6.next(), var2);
         var3.addElement(var5);
         var3.addElement(var5.value);
         Logo.setValue(var5, Logo.evalOneArg(var6, var2), var2);
      }

      var2.locals = new Object[var3.size()];
      var3.copyInto(var2.locals);
      return null;
   }

   Object prim_thing(Object var1, LContext var2) {
      return Logo.getValue(Logo.aSymbol(var1, var2), var2);
   }

   Object prim_put(Object var1, Object var2, Object var3, LContext var4) {
      Hashtable var5 = (Hashtable)var4.props.get(var1);
      if(var5 == null) {
         var5 = new Hashtable();
         var4.props.put(var1, var5);
      }

      var5.put(var2, var3);
      return null;
   }

   Object prim_get(Object var1, Object var2, LContext var3) {
      Hashtable var4 = (Hashtable)var3.props.get(var1);
      if(var4 == null) {
         return new Object[0];
      } else {
         Object var5 = var4.get(var2);
         return var5 == null?new Object[0]:var5;
      }
   }

   Object prim_plist(Object var1, LContext var2) {
      Hashtable var3 = (Hashtable)var2.props.get(var1);
      if(var3 == null) {
         return new Object[0];
      } else {
         Vector var4 = new Vector();
         Enumeration var5 = var3.keys();

         while(var5.hasMoreElements()) {
            Object var6 = var5.nextElement();
            var4.add(var6);
            var4.add(var3.get(var6));
         }

         Object[] var7 = new Object[var4.size()];
         var4.copyInto(var7);
         return var7;
      }
   }

   Object prim_erplist(Object var1, LContext var2) {
      var2.props.remove(var1);
      return null;
   }

   Object prim_namep(Object var1, LContext var2) {
      return new Boolean(Logo.aSymbol(var1, var2).value != null);
   }

   Object prim_definedp(Object var1, LContext var2) {
      return new Boolean(Logo.aSymbol(var1, var2).fcn != null);
   }

   Object prim_quote(Object var1, LContext var2) {
      return var1 instanceof Object[]?var1:new QuotedSymbol(Logo.aSymbol(var1, var2));
   }

   Object prim_intern(Object var1, LContext var2) {
      return Logo.aSymbol(var1, var2);
   }

}
