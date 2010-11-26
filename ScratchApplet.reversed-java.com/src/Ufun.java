// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:37
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class Ufun extends Primitives {

   Object[] arglist;
   Object[] body;


   Ufun(Object[] var1, Object[] var2) {
      this.arglist = var1;
      this.body = var2;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      Object var4 = null;
      Object[] var5 = new Object[this.arglist.length];
      Symbol var6 = var3.ufun;
      var3.ufun = var3.cfun;
      Object[] var7 = var3.locals;
      var3.locals = null;

      for(int var8 = 0; var8 < this.arglist.length; ++var8) {
         var5[var8] = ((Symbol)this.arglist[var8]).value;
         ((Symbol)this.arglist[var8]).value = var2[var8];
      }

      try {
         Logo.runCommand(this.body, var3);
         if(var3.ufunresult != null && var3.ufunresult != var3.juststop) {
            var4 = var3.ufunresult;
         }
      } finally {
         var3.ufun = var6;

         int var11;
         for(var11 = 0; var11 < this.arglist.length; ++var11) {
            ((Symbol)this.arglist[var11]).value = var5[var11];
         }

         if(var3.locals != null) {
            for(var11 = 0; var11 < var3.locals.length; var11 += 2) {
               ((Symbol)var3.locals[var11]).value = var3.locals[var11 + 1];
            }
         }

         var3.locals = var7;
         var3.ufunresult = null;
      }

      return var4;
   }
}
