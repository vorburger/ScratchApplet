// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:38
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class WordListPrims extends Primitives {

   static String[] primlist = new String[]{"first", "1", "last", "1", "word", "i2", "butfirst", "1", "bf", "1", "butlast", "1", "bl", "1", "fput", "2", "lput", "2", "item", "2", "nth", "2", "empty?", "1", "count", "1", "word?", "1", "list?", "1", "member?", "2", "itempos", "2", "setitem", "3", "setnth", "3", "removeitem", "2", "removeitempos", "2", "sentence", "2", "se", "i2", "list", "i2", "makelist", "1", "copylist", "1", "parse", "1", "char", "1", "ascii", "1", "reverse", "1", "substring", "3", "ucase", "1", "insert", "3"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_first(var2[0], var3);
      case 1:
         return this.prim_last(var2[0], var3);
      case 2:
         return this.prim_word(var2, var3);
      case 3:
      case 4:
         return this.prim_butfirst(var2[0], var3);
      case 5:
      case 6:
         return this.prim_butlast(var2[0], var3);
      case 7:
         return this.prim_fput(var2[0], var2[1], var3);
      case 8:
         return this.prim_lput(var2[0], var2[1], var3);
      case 9:
         return this.prim_item(var2[0], var2[1], var3);
      case 10:
         return this.prim_nth(var2[0], var2[1], var3);
      case 11:
         return this.prim_emptyp(var2[0], var3);
      case 12:
         return this.prim_count(var2[0], var3);
      case 13:
         return this.prim_wordp(var2[0], var3);
      case 14:
         return this.prim_listp(var2[0], var3);
      case 15:
         return this.prim_memberp(var2[0], var2[1], var3);
      case 16:
         return this.prim_itempos(var2[0], var2[1], var3);
      case 17:
         return this.prim_setitem(var2[0], var2[1], var2[2], var3);
      case 18:
         return this.prim_setnth(var2[0], var2[1], var2[2], var3);
      case 19:
         return this.prim_removeitem(var2[0], var2[1], var3);
      case 20:
         return this.prim_removeitempos(var2[0], var2[1], var3);
      case 21:
      case 22:
         return this.prim_sentence(var2, var3);
      case 23:
         return this.prim_list(var2, var3);
      case 24:
         return this.prim_makelist(var2[0], var3);
      case 25:
         return this.prim_copylist(var2[0], var3);
      case 26:
         return this.prim_parse(var2[0], var3);
      case 27:
         return this.prim_char(var2[0], var3);
      case 28:
         return this.prim_ascii(var2[0], var3);
      case 29:
         return this.prim_reverse(var2[0], var3);
      case 30:
         return this.prim_substring(var2[0], var2[1], var2[2], var3);
      case 31:
         return this.prim_ucase(var2[0], var3);
      case 32:
         return this.prim_insert(var2[0], var2[1], var2[2], var3);
      default:
         return null;
      }
   }

   Object copyList(Object[] var1, int var2, int var3) {
      Object[] var4 = new Object[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = var1[var2++];
      }

      return var4;
   }

   Object addToList(Object[] var1, Object var2) {
      if(!(var2 instanceof Object[])) {
         return lput(var2, var1);
      } else {
         Object[] var3 = (Object[])var2;
         Object[] var4 = new Object[var1.length + var3.length];

         int var5;
         for(var5 = 0; var5 < var1.length; ++var5) {
            var4[var5] = var1[var5];
         }

         for(var5 = 0; var5 < var3.length; ++var5) {
            var4[var5 + var1.length] = var3[var5];
         }

         return var4;
      }
   }

   Object removeItem(Object[] var1, int var2) {
      Object[] var3 = new Object[var1.length - 1];
      int var4 = 0;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         if(var5 != var2 - 1) {
            var3[var4++] = var1[var5];
         }
      }

      return var3;
   }

   static Object lput(Object var0, Object[] var1) {
      Object[] var2 = new Object[var1.length + 1];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = var1[var3];
      }

      var2[var1.length] = var0;
      return var2;
   }

   static int memberp(Object var0, Object var1) {
      if(var1 instanceof Object[]) {
         Object[] var5 = (Object[])var1;

         for(int var6 = 0; var6 < var5.length; ++var6) {
            if(Logo.prs(var0).equals(Logo.prs(var5[var6]))) {
               return var6 + 1;
            }
         }

         return 0;
      } else if(var0 instanceof Object[]) {
         return 0;
      } else {
         String var2 = Logo.prs(var0);
         String var3 = Logo.prs(var1);

         for(int var4 = 0; var4 < var3.length(); ++var4) {
            if(var2.regionMatches(true, 0, var3, var4, var2.length())) {
               return var4 + 1;
            }
         }

         return 0;
      }
   }

   Object prim_first(Object var1, LContext var2) {
      return var1 instanceof Object[]?((Object[])var1)[0]:Logo.prs(var1).substring(0, 1);
   }

   Object prim_last(Object var1, LContext var2) {
      if(var1 instanceof Object[]) {
         Object[] var4 = (Object[])var1;
         return var4[var4.length - 1];
      } else {
         String var3 = Logo.prs(var1);
         return var3.substring(var3.length() - 1, var3.length());
      }
   }

   Object prim_word(Object[] var1, LContext var2) {
      String var3 = "";

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3 = var3 + Logo.prs(var1[var4]);
      }

      return var3;
   }

   Object prim_butfirst(Object var1, LContext var2) {
      if(var1 instanceof Object[]) {
         Object[] var4 = (Object[])var1;
         return this.copyList(var4, 1, var4.length - 1);
      } else {
         String var3 = Logo.prs(var1);
         return var3.substring(1, var3.length());
      }
   }

   Object prim_butlast(Object var1, LContext var2) {
      if(var1 instanceof Object[]) {
         Object[] var4 = (Object[])var1;
         return this.copyList(var4, 0, var4.length - 1);
      } else {
         String var3 = Logo.prs(var1);
         return var3.substring(0, var3.length() - 1);
      }
   }

   Object prim_fput(Object var1, Object var2, LContext var3) {
      Object[] var4 = (Object[])Logo.aList(var2, var3);
      Object[] var5 = new Object[var4.length + 1];
      var5[0] = var1;

      for(int var6 = 0; var6 < var4.length; ++var6) {
         var5[var6 + 1] = var4[var6];
      }

      return var5;
   }

   Object prim_lput(Object var1, Object var2, LContext var3) {
      return lput(var1, Logo.aList(var2, var3));
   }

   Object prim_item(Object var1, Object var2, LContext var3) {
      int var4 = Logo.anInt(var1, var3) - 1;
      return var2 instanceof Object[]?((Object[])var2)[var4]:Logo.prs(var2).substring(var4, var4 + 1);
   }

   Object prim_nth(Object var1, Object var2, LContext var3) {
      int var4 = Logo.anInt(var1, var3);
      return var2 instanceof Object[]?((Object[])var2)[var4]:Logo.prs(var2).substring(var4, var4 + 1);
   }

   Object prim_emptyp(Object var1, LContext var2) {
      return new Boolean(var1 instanceof Object[]?((Object[])var1).length == 0:Logo.prs(var1).length() == 0);
   }

   Object prim_count(Object var1, LContext var2) {
      return new Long(var1 instanceof Object[]?(long)((Object[])var1).length:(long)Logo.prs(var1).length());
   }

   Object prim_wordp(Object var1, LContext var2) {
      return new Boolean(!(var1 instanceof Object[]));
   }

   Object prim_listp(Object var1, LContext var2) {
      return new Boolean(var1 instanceof Object[]);
   }

   Object prim_memberp(Object var1, Object var2, LContext var3) {
      return new Boolean(memberp(var1, var2) != 0);
   }

   Object prim_itempos(Object var1, Object var2, LContext var3) {
      int var4 = memberp(var1, var2);
      if(var4 != 0) {
         return new Long((long)var4);
      } else {
         Logo.error(var3.cfun + " doesn\'t like " + Logo.prs(var1) + " as input", var3);
         return null;
      }
   }

   Object prim_setitem(Object var1, Object var2, Object var3, LContext var4) {
      ((Object[])Logo.aList(var2, var4))[Logo.anInt(var1, var4) - 1] = var3;
      return null;
   }

   Object prim_setnth(Object var1, Object var2, Object var3, LContext var4) {
      ((Object[])Logo.aList(var2, var4))[Logo.anInt(var1, var4)] = var3;
      return null;
   }

   Object prim_removeitem(Object var1, Object var2, LContext var3) {
      Object[] var4 = Logo.aList(var2, var3);
      return this.removeItem(var4, memberp(var1, var4));
   }

   Object prim_removeitempos(Object var1, Object var2, LContext var3) {
      return this.removeItem(Logo.aList(var2, var3), Logo.anInt(var1, var3));
   }

   Object prim_sentence(Object[] var1, LContext var2) {
      Object[] var3 = new Object[0];

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3 = (Object[])this.addToList(var3, var1[var4]);
      }

      return var3;
   }

   Object prim_list(Object[] var1, LContext var2) {
      Object[] var3 = new Object[var1.length];

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3[var4] = var1[var4];
      }

      return var3;
   }

   Object prim_makelist(Object var1, LContext var2) {
      int var3 = Logo.anInt(var1, var2);
      Object[] var4 = new Object[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = new Object[0];
      }

      return var4;
   }

   Object prim_copylist(Object var1, LContext var2) {
      Object[] var3 = Logo.aList(var1, var2);
      return this.copyList(var3, 0, var3.length);
   }

   Object prim_parse(Object var1, LContext var2) {
      return Logo.parse(Logo.aString(var1, var2), var2);
   }

   Object prim_char(Object var1, LContext var2) {
      char[] var3 = new char[]{(char)Logo.anInt(var1, var2)};
      return new String(var3);
   }

   Object prim_ascii(Object var1, LContext var2) {
      return new Long((long)Logo.aString(var1, var2).charAt(0));
   }

   Object prim_reverse(Object var1, LContext var2) {
      Object[] var3 = Logo.aList(var1, var2);
      Object[] var4 = new Object[var3.length];

      for(int var5 = 0; var5 < var3.length; ++var5) {
         var4[var5] = var3[var3.length - var5 - 1];
      }

      return var4;
   }

   Object prim_substring(Object var1, Object var2, Object var3, LContext var4) {
      String var5 = Logo.prs(var1);
      int var6 = Logo.anInt(var2, var4);
      int var7 = Logo.anInt(var3, var4);
      return var6 == -1?var5.substring(var5.length() - var7, var5.length()):(var7 == -1?var5.substring(var6, var5.length()):var5.substring(var6, var6 + var7));
   }

   Object prim_ucase(Object var1, LContext var2) {
      return Logo.prs(var1).toUpperCase();
   }

   Object prim_insert(Object var1, Object var2, Object var3, LContext var4) {
      Object[] var5 = Logo.aList(var1, var4);
      int var6 = Logo.anInt(var2, var4) - 1;
      if(0 > var6 || var6 > var5.length) {
         Logo.error(var4.cfun + " doesn\'t like " + Logo.prs(var2) + " as input", var4);
      }

      Object[] var7 = new Object[var5.length + 1];
      int var8 = 0;

      for(int var9 = 0; var9 < var5.length; ++var9) {
         if(var9 == var6) {
            var7[var8++] = var3;
         }

         var7[var8++] = var5[var9];
      }

      if(var6 == var5.length) {
         var7[var8] = var3;
      }

      return var7;
   }

}
