// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:36
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.Vector;

class SystemPrims extends Primitives {

   static String[] primlist = new String[]{"resett", "0", "%timer", "0", "wait", "1", "mwait", "1", "eq", "2", "(", "0", ")", "0", "true", "0", "false", "0", "hexw", "2", "tab", "0", "classof", "1", "class", "1", "string", "1", "print", "1", "prs", "1", "ignore", "1"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_resett(var3);
      case 1:
         return this.prim_timer(var3);
      case 2:
         return this.prim_wait(var2[0], var3);
      case 3:
         return this.prim_mwait(var2[0], var3);
      case 4:
         return this.prim_eq(var2[0], var2[1], var3);
      case 5:
         return this.prim_parleft(var3);
      case 6:
         return this.prim_parright(var3);
      case 7:
         return new Boolean(true);
      case 8:
         return new Boolean(false);
      case 9:
         return this.prim_hexw(var2[0], var2[1], var3);
      case 10:
         return "\t";
      case 11:
         return var2[0].getClass();
      case 12:
         return this.prim_class(var2[0], var3);
      case 13:
         return this.prstring(var2[0]);
      case 14:
         var3.tyo.println(Logo.prs(var2[0]));
         return null;
      case 15:
         var3.tyo.print(Logo.prs(var2[0]));
         return null;
      case 16:
         return null;
      default:
         return null;
      }
   }

   Object prim_resett(LContext var1) {
      Logo.starttime = System.currentTimeMillis();
      return null;
   }

   Object prim_timer(LContext var1) {
      return new Double((double)(System.currentTimeMillis() - Logo.starttime));
   }

   Object prim_wait(Object var1, LContext var2) {
      int var3 = (int)(100.0D * Logo.aDouble(var1, var2));
      this.sleepForMSecs(var3, var2);
      return null;
   }

   Object prim_mwait(Object var1, LContext var2) {
      int var3 = Logo.anInt(var1, var2);
      this.sleepForMSecs(var3, var2);
      return null;
   }

   void sleepForMSecs(int var1, LContext var2) {
      if(var1 > 0) {
         long var3 = System.currentTimeMillis();

         for(long var5 = System.currentTimeMillis() - var3; var5 >= 0L && var5 < (long)var1; var5 = System.currentTimeMillis() - var3) {
            if(var2.timeToStop) {
               Logo.error("Stopped!!!", var2);
            }

            try {
               Thread.sleep(Math.min((long)var1 - var5, 10L));
            } catch (InterruptedException var8) {
               ;
            }
         }

      }
   }

   Object prim_eq(Object var1, Object var2, LContext var3) {
      return new Boolean(var1.equals(var2));
   }

   Object prim_parright(LContext var1) {
      Logo.error("Missing \"(\"", var1);
      return null;
   }

   Object prim_parleft(LContext var1) {
      if(this.ipmnext(var1.iline)) {
         return this.ipmcall(var1);
      } else {
         Object var2 = Logo.eval(var1);
         Object var3 = var1.iline.next();
         if(var3 instanceof Symbol && ((Symbol)var3).pname.equals(")")) {
            return var2;
         } else {
            Logo.error("Missing \")\"", var1);
            return null;
         }
      }
   }

   boolean ipmnext(MapList var1) {
      try {
         return ((Symbol)var1.peek()).fcn.ipm;
      } catch (Exception var3) {
         return false;
      }
   }

   Object ipmcall(LContext var1) {
      Vector var2 = new Vector();
      var1.cfun = (Symbol)var1.iline.next();

      while(!this.finIpm(var1.iline)) {
         var2.addElement(Logo.evalOneArg(var1.iline, var1));
      }

      Object[] var3 = new Object[var2.size()];
      var2.copyInto(var3);
      return Logo.evalSym(var1.cfun, var3, var1);
   }

   boolean finIpm(MapList var1) {
      if(var1.eof()) {
         return true;
      } else {
         Object var2 = var1.peek();
         if(var2 instanceof Symbol && ((Symbol)var2).pname.equals(")")) {
            var1.next();
            return true;
         } else {
            return false;
         }
      }
   }

   Object prim_hexw(Object var1, Object var2, LContext var3) {
      Logo.anInt(var1, var3);
      String var4 = Logo.prs(var1, 16);
      int var5 = Logo.anInt(var2, var3);
      String var6 = "00000000".substring(8 - var5 + var4.length());
      return var6 + var4;
   }

   Object prim_class(Object var1, LContext var2) {
      try {
         return Class.forName(Logo.prs(var1));
      } catch (Exception var4) {
         ;
      } catch (Error var5) {
         ;
      }

      return "";
   }

   String prstring(Object var1) {
      if(var1 instanceof Number && Logo.isInt((Number)var1)) {
         return Long.toString(((Number)var1).longValue(), 10);
      } else if(var1 instanceof String) {
         return "|" + (String)var1 + "|";
      } else if(var1 instanceof Object[]) {
         String var2 = "";
         Object[] var3 = (Object[])var1;

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if(var3[var4] instanceof Object[]) {
               var2 = var2 + "[";
            }

            var2 = var2 + this.prstring(var3[var4]);
            if(var3[var4] instanceof Object[]) {
               var2 = var2 + "]";
            }

            if(var4 != var3.length - 1) {
               var2 = var2 + " ";
            }
         }

         return var2;
      } else {
         return var1.toString();
      }
   }

}
