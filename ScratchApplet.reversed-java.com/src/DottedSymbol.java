// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class DottedSymbol {

   Symbol sym;


   DottedSymbol(Symbol var1) {
      this.sym = var1;
   }

   public String toString() {
      return ":" + this.sym.toString();
   }
}
