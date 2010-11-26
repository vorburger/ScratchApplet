// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class ControlPrims extends Primitives {

   static String[] primlist = new String[]{"repeat", "2", "if", "2", "ifelse", "3", "stop", "0", "output", "1", "dotimes", "2", "dolist", "2", "carefully", "2", "errormessage", "0", "unwind-protect", "2", "error", "1", "dispatch", "2", "run", "1", "loop", "1", "forever", "1", "selectq", "2", "stopme", "0"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_repeat(var2[0], var2[1], var3);
      case 1:
         return this.prim_if(var2[0], var2[1], var3);
      case 2:
         return this.prim_ifelse(var2[0], var2[1], var2[2], var3);
      case 3:
         return this.prim_stop(var3);
      case 4:
         return this.prim_output(var2[0], var3);
      case 5:
         return this.prim_dotimes(var2[0], var2[1], var3);
      case 6:
         return this.prim_dolist(var2[0], var2[1], var3);
      case 7:
         return this.prim_carefully(var2[0], var2[1], var3);
      case 8:
         return var3.errormessage;
      case 9:
         return this.prim_unwindprotect(var2[0], var2[1], var3);
      case 10:
         return this.prim_error(var2[0], var3);
      case 11:
         return this.prim_dispatch(var2[0], var2[1], var3);
      case 12:
         return this.prim_run(var2[0], var3);
      case 13:
         return this.prim_loop(var2[0], var3);
      case 14:
         return this.prim_loop(var2[0], var3);
      case 15:
         return this.prim_selectq(var2[0], var2[1], var3);
      case 16:
         return this.prim_stopme(var3);
      default:
         return null;
      }
   }

   Object prim_repeat(Object var1, Object var2, LContext var3) {
      int var4 = Logo.anInt(var1, var3);
      Object[] var5 = Logo.aList(var2, var3);

      for(int var6 = 0; var6 < var4; ++var6) {
         Logo.runCommand(var5, var3);
         if(var3.ufunresult != null) {
            return null;
         }
      }

      return null;
   }

   Object prim_if(Object var1, Object var2, LContext var3) {
      if(Logo.aBoolean(var1, var3)) {
         Logo.runCommand(Logo.aList(var2, var3), var3);
      }

      return null;
   }

   Object prim_ifelse(Object var1, Object var2, Object var3, LContext var4) {
      boolean var5 = Logo.aBoolean(var1, var4);
      Object[] var6 = Logo.aList(var2, var4);
      Object[] var7 = Logo.aList(var3, var4);
      return var5?Logo.runList(var6, var4):Logo.runList(var7, var4);
   }

   Object prim_stop(LContext var1) {
      var1.ufunresult = var1.juststop;
      return null;
   }

   Object prim_output(Object var1, LContext var2) {
      var2.ufunresult = var1;
      return null;
   }

   Object prim_dotimes(Object var1, Object var2, LContext var3) {
      MapList var4 = new MapList(Logo.aList(var1, var3));
      Object[] var5 = Logo.aList(var2, var3);
      Symbol var6 = Logo.aSymbol(var4.next(), var3);
      int var7 = Logo.anInt(Logo.evalOneArg(var4, var3), var3);
      Logo.checkListEmpty(var4, var3);
      Object var8 = var6.value;

      try {
         for(int var9 = 0; var9 < var7; ++var9) {
            var6.value = new Double((double)var9);
            Logo.runCommand(var5, var3);
         }

         if(var3.ufunresult != null) {
            Object var13 = null;
            return var13;
         }
      } finally {
         var6.value = var8;
      }

      return null;
   }

   Object prim_dolist(Object var1, Object var2, LContext var3) {
      MapList var4 = new MapList(Logo.aList(var1, var3));
      Object[] var5 = Logo.aList(var2, var3);
      Symbol var6 = Logo.aSymbol(var4.next(), var3);
      Object[] var7 = Logo.aList(Logo.evalOneArg(var4, var3), var3);
      Logo.checkListEmpty(var4, var3);
      Object var8 = var6.value;

      try {
         for(int var9 = 0; var9 < var7.length; ++var9) {
            var6.value = var7[var9];
            Logo.runCommand(var5, var3);
            if(var3.ufunresult != null) {
               Object var10 = null;
               return var10;
            }
         }
      } finally {
         var6.value = var8;
      }

      return null;
   }

   Object prim_carefully(Object var1, Object var2, LContext var3) {
      Object[] var4 = Logo.aList(var1, var3);
      Object[] var5 = Logo.aList(var2, var3);

      try {
         return Logo.runList(var4, var3);
      } catch (Exception var7) {
         var3.errormessage = var7.getMessage();
         return Logo.runList(var5, var3);
      }
   }

   Object prim_unwindprotect(Object var1, Object var2, LContext var3) {
      Object[] var4 = Logo.aList(var1, var3);
      Object[] var5 = Logo.aList(var2, var3);

      try {
         Logo.runCommand(var4, var3);
      } finally {
         Logo.runCommand(var5, var3);
      }

      return null;
   }

   Object prim_error(Object var1, LContext var2) {
      Logo.error(Logo.prs(var1), var2);
      return null;
   }

   Object prim_dispatch(Object var1, Object var2, LContext var3) {
      int var4 = Logo.anInt(var1, var3);
      Object[] var5 = Logo.aList(var2, var3);
      Object[] var6 = Logo.aList(var5[var4], var3);
      return Logo.runList(var6, var3);
   }

   Object prim_run(Object var1, LContext var2) {
      return Logo.runList(Logo.aList(var1, var2), var2);
   }

   Object prim_loop(Object var1, LContext var2) {
      Object[] var3 = Logo.aList(var1, var2);

      do {
         Logo.runCommand(var3, var2);
      } while(var2.ufunresult == null);

      return null;
   }

   Object prim_selectq(Object var1, Object var2, LContext var3) {
      Object[] var4 = Logo.aList(var2, var3);
      int var5 = 0;

      while(true) {
         if(var5 >= var4.length) {
            return null;
         }

         if(var4[var5] instanceof DottedSymbol) {
            if(Logo.getValue(((DottedSymbol)var4[var5]).sym, var3).equals(var1)) {
               break;
            }
         } else if(var4[var5].equals(var1)) {
            break;
         }

         var5 += 2;
      }

      return Logo.runList((Object[])var4[var5 + 1], var3);
   }

   Object prim_stopme(LContext var1) {
      Logo.error("", var1);
      return null;
   }

}
