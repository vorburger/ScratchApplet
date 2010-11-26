// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:29
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class Function {

   Primitives instance;
   int dispatchOffset;
   int nargs;
   boolean ipm;


   Function(Primitives var1, int var2, int var3) {
      this(var1, var2, var3, false);
   }

   Function(Primitives var1, int var2, int var3, boolean var4) {
      this.instance = var1;
      this.nargs = var2;
      this.dispatchOffset = var3;
      this.ipm = var4;
   }
}
