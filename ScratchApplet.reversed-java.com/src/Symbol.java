// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:36
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class Symbol {

   String pname;
   Function fcn;
   Object value;


   Symbol(String var1) {
      this.pname = var1;
   }

   public String toString() {
      return this.pname;
   }
}
