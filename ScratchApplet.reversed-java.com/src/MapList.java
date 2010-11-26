// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:31
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class MapList {

   Object[] tokens;
   int offset = 0;


   MapList(Object[] var1) {
      this.tokens = var1;
   }

   Object next() {
      return this.tokens[this.offset++];
   }

   Object peek() {
      return this.tokens[this.offset];
   }

   boolean eof() {
      return this.offset == this.tokens.length;
   }
}
