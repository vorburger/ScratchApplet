// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:37
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.Vector;

class TokenStream {

   String str;
   int offset = 0;


   TokenStream(String var1) {
      this.str = var1;
      this.skipSpace();
   }

   Object[] readList(LContext var1) {
      Vector var2 = new Vector();

      Object var3;
      while(!this.eof() && (var3 = this.readToken(var1)) != null) {
         var2.addElement(var3);
      }

      Object[] var4 = new Object[var2.size()];
      var2.copyInto(var4);
      return var4;
   }

   Object readToken(LContext var1) {
      String var2 = this.next();

      try {
         if(var2.length() > 2 && var2.charAt(0) == 48 && var2.charAt(1) == 120) {
            return new Double((double)Long.parseLong(var2.substring(2), 16));
         }
      } catch (NumberFormatException var7) {
         ;
      }

      try {
         if(var2.length() > 1 && var2.charAt(0) == 36) {
            return new Double((double)Long.parseLong(var2.substring(1), 16));
         }
      } catch (NumberFormatException var6) {
         ;
      }

      try {
         if(var2.length() > 1 && var2.charAt(0) == 48) {
            return new Double((double)Long.parseLong(var2.substring(1), 8));
         }
      } catch (NumberFormatException var5) {
         ;
      }

      if(var2.equals("]")) {
         return null;
      } else {
         if(Logo.aValidNumber(var2)) {
            try {
               Double var3 = Double.valueOf(var2);
               return var3;
            } catch (NumberFormatException var8) {
               ;
            }
         }

         return var2.charAt(0) == 34?new QuotedSymbol(Logo.intern(var2.substring(1), var1)):(var2.charAt(0) == 58?new DottedSymbol(Logo.intern(var2.substring(1), var1)):(var2.equals("[")?this.readList(var1):(var2.charAt(0) == 124?var2.substring(1):Logo.intern(var2, var1))));
      }
   }

   boolean startsWith(String var1) {
      return this.str.startsWith(var1, this.offset);
   }

   void skipToNextLine() {
      while(!this.eof() && "\n\r".indexOf(this.str.charAt(this.offset)) == -1) {
         ++this.offset;
      }

      this.skipSpace();
   }

   void skipSpace() {
      while(!this.eof() && " ;,\t\r\n".indexOf(this.str.charAt(this.offset)) != -1) {
         if(this.peekChar().equals(";")) {
            while(!this.eof() && "\n\r".indexOf(this.str.charAt(this.offset)) == -1) {
               ++this.offset;
            }
         } else {
            ++this.offset;
         }
      }

   }

   String nextLine() {
      String var1;
      for(var1 = ""; !this.eof() && ";\n\r".indexOf(this.peekChar()) == -1; var1 = var1 + this.nextChar()) {
         ;
      }

      this.skipSpace();
      return var1;
   }

   String next() {
      String var1 = "";
      if(!this.delim(this.peekChar())) {
         while(!this.eof() && !this.delim(this.peekChar())) {
            if(this.peekChar().equals("|")) {
               var1 = var1 + "|" + this.getVbarString();
               this.skipSpace();
               return var1;
            }

            var1 = var1 + this.nextChar();
         }
      } else {
         var1 = this.nextChar();
      }

      this.skipSpace();
      return var1;
   }

   String getVbarString() {
      StringBuffer var1 = new StringBuffer();
      this.nextChar();

      while(!this.eof()) {
         if(this.peekChar().equals("|")) {
            this.nextChar();
            break;
         }

         var1.append(this.nextChar());
      }

      return var1.toString();
   }

   boolean delim(String var1) {
      char var2 = var1.charAt(0);
      return "()[] ,\t\r\n".indexOf(var2) != -1;
   }

   String peekChar() {
      return String.valueOf(this.str.charAt(this.offset));
   }

   String nextChar() {
      return String.valueOf(this.str.charAt(this.offset++));
   }

   boolean eof() {
      return this.str.length() == this.offset;
   }
}
