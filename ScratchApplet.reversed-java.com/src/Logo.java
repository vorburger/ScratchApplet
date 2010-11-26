// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:31
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.HashSet;

public class Logo {

   static long starttime = System.currentTimeMillis();


   static String runToplevel(Object[] var0, LContext var1) {
      var1.iline = new MapList(var0);
      var1.timeToStop = false;

      try {
         evLine(var1);
      } catch (LogoError var3) {
         if(var3.getMessage() != null) {
            return var3.getMessage();
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         return var4.toString();
      } catch (Error var5) {
         return var5.toString();
      }

      return null;
   }

   static void evLine(LContext var0) {
      while(!var0.iline.eof() && var0.ufunresult == null) {
         Object var1;
         if((var1 = eval(var0)) != null) {
            error("You don\'t say what to do with " + prs(var1), var0);
         }
      }

   }

   static Object eval(LContext var0) {
      Object var1;
      for(var1 = evalToken(var0); infixNext(var0.iline, var0); var1 = evalInfix(var1, var0)) {
         if(var1 instanceof Nothing) {
            error(var0.iline.peek() + " needs more inputs", var0);
         }
      }

      return var1;
   }

   static Object evalToken(LContext var0) {
      Object var1 = var0.iline.next();
      return var1 instanceof QuotedSymbol?((QuotedSymbol)var1).sym:(var1 instanceof DottedSymbol?getValue(((DottedSymbol)var1).sym, var0):(var1 instanceof Symbol?evalSym((Symbol)var1, (Object[])null, var0):(var1 instanceof String?evalSym(intern((String)var1, var0), (Object[])null, var0):var1)));
   }

   static Object evalSym(Symbol var0, Object[] var1, LContext var2) {
      if(var2.timeToStop) {
         error("Stopped!!!", var2);
      }

      if(var0.fcn == null) {
         error("I don\'t know how to " + var0, var2);
      }

      Symbol var3 = var2.cfun;
      var2.cfun = var0;
      int var4 = var2.priority;
      var2.priority = 0;
      Object var5 = null;

      try {
         Function var6 = var0.fcn;
         int var7 = var6.nargs;
         if(var1 == null) {
            var1 = evalArgs(var7, var2);
         }

         var5 = var6.instance.dispatch(var6.dispatchOffset, var1, var2);
      } catch (RuntimeException var11) {
         errorHandler(var0, var1, var11, var2);
      } finally {
         var2.cfun = var3;
         var2.priority = var4;
      }

      if(var2.mustOutput && var5 == null) {
         error(var0 + " didn\'t output to " + var2.cfun, var2);
      }

      return var5;
   }

   static Object[] evalArgs(int var0, LContext var1) {
      boolean var2 = var1.mustOutput;
      var1.mustOutput = true;
      Object[] var3 = new Object[var0];

      try {
         for(int var4 = 0; var4 < var0; ++var4) {
            if(var1.iline.eof()) {
               error(var1.cfun + " needs more inputs", var1);
            }

            var3[var4] = eval(var1);
            if(var3[var4] instanceof Nothing) {
               error(var1.cfun + " needs more inputs", var1);
            }
         }
      } finally {
         var1.mustOutput = var2;
      }

      return var3;
   }

   static void runCommand(Object[] var0, LContext var1) {
      boolean var2 = var1.mustOutput;
      var1.mustOutput = false;

      try {
         runList(var0, var1);
      } finally {
         var1.mustOutput = var2;
      }

   }

   static Object runList(Object[] var0, LContext var1) {
      MapList var2 = var1.iline;
      var1.iline = new MapList(var0);
      Object var3 = null;

      try {
         if(var1.mustOutput) {
            var3 = eval(var1);
         } else {
            evLine(var1);
         }

         checkListEmpty(var1.iline, var1);
      } finally {
         var1.iline = var2;
      }

      return var3;
   }

   static Object evalOneArg(MapList var0, LContext var1) {
      boolean var2 = var1.mustOutput;
      var1.mustOutput = true;
      MapList var3 = var1.iline;
      var1.iline = var0;

      Object var4;
      try {
         var4 = eval(var1);
      } finally {
         var1.iline = var3;
         var1.mustOutput = var2;
      }

      return var4;
   }

   static boolean infixNext(MapList var0, LContext var1) {
      Object var2 = null;
      Function var3 = null;
      boolean var10000;
      if(!var0.eof() && (var2 = var0.peek()) instanceof Symbol) {
         var3 = ((Symbol)var2).fcn;
         if(((Symbol)var2).fcn != null && var3.nargs < var1.priority) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   static Object evalInfix(Object var0, LContext var1) {
      Symbol var2 = (Symbol)var1.iline.next();
      Function var3 = var2.fcn;
      Symbol var4 = var1.cfun;
      var1.cfun = var2;
      int var5 = var1.priority;
      var1.priority = var3.nargs;
      Object var6 = null;
      Object[] var7 = new Object[]{var0, null};

      try {
         Object[] var8 = evalArgs(1, var1);
         var7[1] = var8[0];
         var6 = var3.instance.dispatch(var3.dispatchOffset, var7, var1);
      } catch (RuntimeException var12) {
         errorHandler(var2, var7, var12, var1);
      } finally {
         var1.cfun = var4;
         var1.priority = var5;
      }

      if(var1.mustOutput && var6 == null) {
         error(var2 + " didn\'t output to " + var1.cfun, var1);
      }

      return var6;
   }

   static Symbol intern(String var0, LContext var1) {
      String var2;
      if(var0.length() == 0) {
         var2 = var0;
      } else if(var0.charAt(0) == 124) {
         var2 = var0 = var0.substring(1);
      } else {
         var2 = var0;
      }

      Symbol var3 = (Symbol)var1.oblist.get(var2);
      if(var3 == null) {
         var1.oblist.put(var2, var3 = new Symbol(var0));
      }

      return var3;
   }

   static Object[] parse(String var0, LContext var1) {
      TokenStream var2 = new TokenStream(var0);
      return var2.readList(var1);
   }

   static String prs(Object var0) {
      return prs(var0, 10);
   }

   static String prs(Object var0, int var1) {
      return prs(var0, var1, new HashSet());
   }

   static String prs(Object var0, int var1, HashSet var2) {
      if(var0 instanceof Number && var1 == 16) {
         return Long.toString(((Number)var0).longValue(), 16).toUpperCase();
      } else if(var0 instanceof Number && var1 == 8) {
         return Long.toString(((Number)var0).longValue(), 8);
      } else if(var0 instanceof Number && isInt((Number)var0)) {
         return Long.toString(((Number)var0).longValue(), 10);
      } else if(var0 instanceof Object[]) {
         Object[] var3 = (Object[])var0;
         if(var3.length > 0 && var2.contains(var0)) {
            return "...";
         } else {
            if(var3.length > 0) {
               var2.add(var0);
            }

            String var4 = "";

            for(int var5 = 0; var5 < var3.length; ++var5) {
               if(var3[var5] instanceof Object[]) {
                  var4 = var4 + "[";
               }

               var4 = var4 + prs(var3[var5], var1, var2);
               if(var3[var5] instanceof Object[]) {
                  var4 = var4 + "]";
               }

               if(var5 != var3.length - 1) {
                  var4 = var4 + " ";
               }
            }

            return var4;
         }
      } else {
         return var0.toString();
      }
   }

   static boolean isInt(Number var0) {
      return var0.doubleValue() == (new Integer(var0.intValue())).doubleValue();
   }

   static boolean aValidNumber(String var0) {
      if(var0.length() == 1 && "0123456789".indexOf(var0.charAt(0)) == -1) {
         return false;
      } else if("eE.+-0123456789".indexOf(var0.charAt(0)) == -1) {
         return false;
      } else {
         for(int var1 = 1; var1 < var0.length(); ++var1) {
            if("eE.0123456789".indexOf(var0.charAt(var1)) == -1) {
               return false;
            }
         }

         return true;
      }
   }

   static Object getValue(Symbol var0, LContext var1) {
      Object var2 = var0.value;
      if(var0.value != null) {
         return var2;
      } else {
         error(var0 + " has no value", var1);
         return null;
      }
   }

   static void setValue(Symbol var0, Object var1, LContext var2) {
      var0.value = var1;
   }

   static double aDouble(Object var0, LContext var1) {
      if(var0 instanceof Double) {
         return ((Double)var0).doubleValue();
      } else {
         String var2 = prs(var0);
         if(var2.length() > 0 && aValidNumber(var2)) {
            return Double.valueOf(var2).doubleValue();
         } else {
            error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
            return 0.0D;
         }
      }
   }

   static int anInt(Object var0, LContext var1) {
      if(var0 instanceof Double) {
         return ((Double)var0).intValue();
      } else {
         String var2 = prs(var0);
         if(aValidNumber(var2)) {
            return Double.valueOf(var2).intValue();
         } else {
            error(var1.cfun + " doesn\'t like " + var2 + " as input", var1);
            return 0;
         }
      }
   }

   static long aLong(Object var0, LContext var1) {
      if(var0 instanceof Double) {
         return ((Double)var0).longValue();
      } else {
         String var2 = prs(var0);
         if(aValidNumber(var2)) {
            return Double.valueOf(var2).longValue();
         } else {
            error(var1.cfun + " doesn\'t like " + var2 + " as input", var1);
            return 0L;
         }
      }
   }

   static boolean aBoolean(Object var0, LContext var1) {
      if(var0 instanceof Boolean) {
         return ((Boolean)var0).booleanValue();
      } else if(var0 instanceof Symbol) {
         return ((Symbol)var0).pname.equals("true");
      } else {
         error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
         return false;
      }
   }

   static Object[] aList2Double(Object var0, LContext var1) {
      if(var0 instanceof Object[]) {
         if(((Object[])var0).length == 2 && ((Object[])var0)[0] instanceof Double && ((Object[])var0)[1] instanceof Double) {
            return (Object[])var0;
         }

         error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
      }

      return null;
   }

   static Object[] aList(Object var0, LContext var1) {
      if(var0 instanceof Object[]) {
         return (Object[])var0;
      } else {
         error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
         return null;
      }
   }

   static Symbol aSymbol(Object var0, LContext var1) {
      if(var0 instanceof Symbol) {
         return (Symbol)var0;
      } else if(var0 instanceof String) {
         return intern((String)var0, var1);
      } else if(var0 instanceof Number) {
         String var2 = String.valueOf(((Number)var0).longValue());
         return intern(var2, var1);
      } else {
         error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
         return null;
      }
   }

   static String aString(Object var0, LContext var1) {
      if(var0 instanceof String) {
         return (String)var0;
      } else if(var0 instanceof Symbol) {
         return ((Symbol)var0).toString();
      } else {
         error(var1.cfun + " doesn\'t like " + prs(var0) + " as input", var1);
         return null;
      }
   }

   static void setupPrims(String[] var0, LContext var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         setupPrims(var0[var2], var1);
      }

   }

   static void setupPrims(String var0, LContext var1) {
      try {
         Class var2 = Class.forName(var0);
         Primitives var3 = (Primitives)var2.newInstance();
         String[] var4 = var3.primlist();

         for(int var5 = 0; var5 < var4.length; var5 += 2) {
            String var6 = var4[var5 + 1];
            boolean var7 = var6.startsWith("i");
            if(var7) {
               var6 = var6.substring(1);
            }

            Symbol var8 = intern(var4[var5], var1);
            var8.fcn = new Function(var3, Integer.parseInt(var6), var5 / 2, var7);
         }
      } catch (Exception var9) {
         System.out.println(var9.toString());
      }

   }

   static void checkListEmpty(MapList var0, LContext var1) {
      if(!var0.eof() && var1.ufunresult == null) {
         error("You don\'t say what to do with " + prs(var0.next()), var1);
      }

   }

   static void errorHandler(Symbol var0, Object[] var1, RuntimeException var2, LContext var3) {
      if(!(var2 instanceof ArrayIndexOutOfBoundsException) && !(var2 instanceof StringIndexOutOfBoundsException) && !(var2 instanceof NegativeArraySizeException)) {
         throw var2;
      } else {
         error(var0 + " doesn\'t like " + prs(var1[0]) + " as input", var3);
      }
   }

   static void error(String var0, LContext var1) {
      if(var0.equals("")) {
         throw new LogoError((String)null);
      } else {
         var0 = var0 + (var1.ufun == null?"":" in " + var1.ufun);
         throw new LogoError(var0);
      }
   }

   static void readAllFunctions(String var0, LContext var1) {
      TokenStream var2 = new TokenStream(var0);

      while(true) {
         switch(findKeyWord(var2)) {
         case 0:
            return;
         case 1:
            doDefine(var2, var1);
            break;
         case 2:
            doTo(var2, var1);
         }
      }
   }

   static int findKeyWord(TokenStream var0) {
      while(!var0.eof()) {
         if(var0.startsWith("define ")) {
            return 1;
         }

         if(var0.startsWith("to ")) {
            return 2;
         }

         var0.skipToNextLine();
      }

      return 0;
   }

   static void doDefine(TokenStream var0, LContext var1) {
      var0.readToken(var1);
      Symbol var2 = aSymbol(var0.readToken(var1), var1);
      Object[] var3 = aList(var0.readToken(var1), var1);
      Object[] var4 = aList(var0.readToken(var1), var1);
      Ufun var5 = new Ufun(var3, var4);
      var2.fcn = new Function(var5, var3.length, 0);
   }

   static void doTo(TokenStream var0, LContext var1) {
      Object[] var2 = parse(var0.nextLine(), var1);
      Object[] var3 = parse(readBody(var0, var1), var1);
      Object[] var4 = getArglistFromTitle(var2);
      Symbol var5 = aSymbol(var2[1], var1);
      Ufun var6 = new Ufun(var4, var3);
      var5.fcn = new Function(var6, var4.length, 0);
   }

   static String readBody(TokenStream var0, LContext var1) {
      String var2;
      String var3;
      for(var2 = ""; !var0.eof(); var2 = var2 + " " + var3) {
         var3 = var0.nextLine();
         if(var3.startsWith("end") && "end".equals(((Symbol)parse(var3, var1)[0]).pname)) {
            return var2;
         }
      }

      return var2;
   }

   static Object[] getArglistFromTitle(Object[] var0) {
      Object[] var1 = new Object[var0.length - 2];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = ((DottedSymbol)var0[var2 + 2]).sym;
      }

      return var1;
   }

}
