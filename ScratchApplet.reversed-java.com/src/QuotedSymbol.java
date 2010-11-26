// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class QuotedSymbol {

   Symbol sym;


   QuotedSymbol(Symbol var1) {
      this.sym = var1;
   }

   public String toString() {
      return "\"" + this.sym.toString();
   }
}
