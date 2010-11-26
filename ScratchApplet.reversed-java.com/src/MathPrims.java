// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:31
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class MathPrims extends Primitives {

   static String[] primlist = new String[]{"sum", "i2", "remainder", "2", "difference", "2", "diff", "2", "product", "i2", "quotient", "2", "greater?", "2", "less?", "2", "int", "1", "minus", "1", "round", "1", "sqrt", "1", "sin", "1", "cos", "1", "tan", "1", "abs", "1", "power", "2", "arctan", "1", "pi", "0", "exp", "1", "arctan2", "2", "ln", "1", "logand", "2", "logior", "2", "logxor", "2", "lsh", "2", "and", "i2", "or", "i2", "not", "1", "random", "1", "min", "i2", "max", "i2", "number?", "1", "+", "-2", "-", "-2", "*", "-3", "/", "-3", "<", "-1", ">", "-1", "=", "-1", "equal?", "i2", "%", "-3", "rand", "0", "strequ", "2", "arcsin", "1", "arccos", "1", "strcmp", "2"};
   static final double degtor = 57.29577951308232D;


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_sum(var2, var3);
      case 1:
         return this.prim_remainder(var2[0], var2[1], var3);
      case 2:
      case 3:
         return this.prim_diff(var2[0], var2[1], var3);
      case 4:
         return this.prim_product(var2, var3);
      case 5:
         return this.prim_quotient(var2[0], var2[1], var3);
      case 6:
         return this.prim_greaterp(var2[0], var2[1], var3);
      case 7:
         return this.prim_lessp(var2[0], var2[1], var3);
      case 8:
         return this.prim_int(var2[0], var3);
      case 9:
         return this.prim_minus(var2[0], var3);
      case 10:
         return this.prim_round(var2[0], var3);
      case 11:
         return this.prim_sqrt(var2[0], var3);
      case 12:
         return this.prim_sin(var2[0], var3);
      case 13:
         return this.prim_cos(var2[0], var3);
      case 14:
         return this.prim_tan(var2[0], var3);
      case 15:
         return this.prim_abs(var2[0], var3);
      case 16:
         return this.prim_power(var2[0], var2[1], var3);
      case 17:
         return this.prim_arctan(var2[0], var3);
      case 18:
         return this.prim_pi(var3);
      case 19:
         return this.prim_exp(var2[0], var3);
      case 20:
         return this.prim_arctan2(var2[0], var2[1], var3);
      case 21:
         return this.prim_ln(var2[0], var3);
      case 22:
         return this.prim_logand(var2[0], var2[1], var3);
      case 23:
         return this.prim_logior(var2[0], var2[1], var3);
      case 24:
         return this.prim_logxor(var2[0], var2[1], var3);
      case 25:
         return this.prim_lsh(var2[0], var2[1], var3);
      case 26:
         return this.prim_and(var2, var3);
      case 27:
         return this.prim_or(var2, var3);
      case 28:
         return this.prim_not(var2[0], var3);
      case 29:
         return this.prim_random(var2[0], var3);
      case 30:
         return this.prim_min(var2, var3);
      case 31:
         return this.prim_max(var2, var3);
      case 32:
         return this.prim_numberp(var2[0], var3);
      case 33:
         return this.prim_sum(var2, var3);
      case 34:
         return this.prim_diff(var2[0], var2[1], var3);
      case 35:
         return this.prim_product(var2, var3);
      case 36:
         return this.prim_quotient(var2[0], var2[1], var3);
      case 37:
         return this.prim_lessp(var2[0], var2[1], var3);
      case 38:
         return this.prim_greaterp(var2[0], var2[1], var3);
      case 39:
         return this.prim_equalp(var2, var3);
      case 40:
         return this.prim_equalp(var2, var3);
      case 41:
         return this.prim_remainder(var2[0], var2[1], var3);
      case 42:
         return new Double(Math.random());
      case 43:
         return this.prim_strequ(var2[0], var2[1], var3);
      case 44:
         return this.prim_arcsin(var2[0], var3);
      case 45:
         return this.prim_arccos(var2[0], var3);
      case 46:
         return this.prim_strcmp(var2[0], var2[1], var3);
      default:
         return null;
      }
   }

   Object prim_sum(Object[] var1, LContext var2) {
      double var3 = 0.0D;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         var3 += Logo.aDouble(var1[var5], var2);
      }

      return new Double(var3);
   }

   Object prim_remainder(Object var1, Object var2, LContext var3) {
      return new Double(Logo.aDouble(var1, var3) % Logo.aDouble(var2, var3));
   }

   Object prim_diff(Object var1, Object var2, LContext var3) {
      return new Double(Logo.aDouble(var1, var3) - Logo.aDouble(var2, var3));
   }

   Object prim_product(Object[] var1, LContext var2) {
      double var3 = 1.0D;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         var3 *= Logo.aDouble(var1[var5], var2);
      }

      return new Double(var3);
   }

   Object prim_quotient(Object var1, Object var2, LContext var3) {
      return new Double(Logo.aDouble(var1, var3) / Logo.aDouble(var2, var3));
   }

   Object prim_greaterp(Object var1, Object var2, LContext var3) {
      return new Boolean(Logo.aDouble(var1, var3) > Logo.aDouble(var2, var3));
   }

   Object prim_lessp(Object var1, Object var2, LContext var3) {
      return new Boolean(Logo.aDouble(var1, var3) < Logo.aDouble(var2, var3));
   }

   Object prim_int(Object var1, LContext var2) {
      return new Double((double)(new Double(Logo.aDouble(var1, var2))).intValue());
   }

   Object prim_minus(Object var1, LContext var2) {
      return new Double(0.0D - Logo.aDouble(var1, var2));
   }

   Object prim_round(Object var1, LContext var2) {
      return new Double((double)Math.round(Logo.aDouble(var1, var2)));
   }

   Object prim_sqrt(Object var1, LContext var2) {
      return new Double(Math.sqrt(Logo.aDouble(var1, var2)));
   }

   Object prim_sin(Object var1, LContext var2) {
      return new Double(Math.sin(Logo.aDouble(var1, var2) / 57.29577951308232D));
   }

   Object prim_cos(Object var1, LContext var2) {
      return new Double(Math.cos(Logo.aDouble(var1, var2) / 57.29577951308232D));
   }

   Object prim_tan(Object var1, LContext var2) {
      return new Double(Math.tan(Logo.aDouble(var1, var2) / 57.29577951308232D));
   }

   Object prim_abs(Object var1, LContext var2) {
      return new Double(Math.abs(Logo.aDouble(var1, var2)));
   }

   Object prim_power(Object var1, Object var2, LContext var3) {
      return new Double(Math.pow(Logo.aDouble(var1, var3), Logo.aDouble(var2, var3)));
   }

   Object prim_arcsin(Object var1, LContext var2) {
      return new Double(57.29577951308232D * Math.asin(Logo.aDouble(var1, var2)));
   }

   Object prim_arccos(Object var1, LContext var2) {
      return new Double(57.29577951308232D * Math.acos(Logo.aDouble(var1, var2)));
   }

   Object prim_arctan(Object var1, LContext var2) {
      return new Double(57.29577951308232D * Math.atan(Logo.aDouble(var1, var2)));
   }

   Object prim_pi(LContext var1) {
      return new Double(3.141592653589793D);
   }

   Object prim_exp(Object var1, LContext var2) {
      return new Double(Math.exp(Logo.aDouble(var1, var2)));
   }

   Object prim_arctan2(Object var1, Object var2, LContext var3) {
      return new Double(57.29577951308232D * Math.atan2(Logo.aDouble(var1, var3), Logo.aDouble(var2, var3)));
   }

   Object prim_ln(Object var1, LContext var2) {
      return new Double(Math.log(Logo.aDouble(var1, var2)));
   }

   Object prim_logand(Object var1, Object var2, LContext var3) {
      return new Double((double)(Logo.anInt(var1, var3) & Logo.anInt(var2, var3)));
   }

   Object prim_logior(Object var1, Object var2, LContext var3) {
      return new Double((double)(Logo.anInt(var1, var3) | Logo.anInt(var2, var3)));
   }

   Object prim_logxor(Object var1, Object var2, LContext var3) {
      return new Double((double)(Logo.anInt(var1, var3) ^ Logo.anInt(var2, var3)));
   }

   Object prim_lsh(Object var1, Object var2, LContext var3) {
      int var4 = Logo.anInt(var2, var3);
      int var5 = Logo.anInt(var1, var3);
      return var4 > 0?new Double((double)(var5 << var4)):new Double((double)(var5 >> -var4));
   }

   Object prim_and(Object[] var1, LContext var2) {
      boolean var3 = true;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3 &= Logo.aBoolean(var1[var4], var2);
      }

      return new Boolean(var3);
   }

   Object prim_or(Object[] var1, LContext var2) {
      boolean var3 = false;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3 |= Logo.aBoolean(var1[var4], var2);
      }

      return new Boolean(var3);
   }

   Object prim_not(Object var1, LContext var2) {
      return new Boolean(!Logo.aBoolean(var1, var2));
   }

   Object prim_random(Object var1, LContext var2) {
      return new Double(Math.floor(Math.random() * (double)Logo.anInt(var1, var2)));
   }

   Object prim_min(Object[] var1, LContext var2) {
      if(var1.length == 0) {
         Logo.error("Min needs at least one input", var2);
      }

      double var3 = Logo.aDouble(var1[0], var2);

      for(int var5 = 1; var5 < var1.length; ++var5) {
         var3 = Math.min(var3, Logo.aDouble(var1[var5], var2));
      }

      return new Double(var3);
   }

   Object prim_max(Object[] var1, LContext var2) {
      if(var1.length == 0) {
         Logo.error("Max needs at least one input", var2);
      }

      double var3 = Logo.aDouble(var1[0], var2);

      for(int var5 = 1; var5 < var1.length; ++var5) {
         var3 = Math.max(var3, Logo.aDouble(var1[var5], var2));
      }

      return new Double(var3);
   }

   Object prim_numberp(Object var1, LContext var2) {
      return new Boolean(var1 instanceof Number);
   }

   Object prim_equalp(Object[] var1, LContext var2) {
      if(var1.length == 0) {
         Logo.error("Equal needs at least one input", var2);
      }

      Object var3 = var1[0];

      for(int var4 = 1; var4 < var1.length; ++var4) {
         if(var3 != var1[var4] && !Logo.prs(var3).equals(Logo.prs(var1[var4]))) {
            return new Boolean(false);
         }
      }

      return new Boolean(true);
   }

   Object prim_strequ(Object var1, Object var2, LContext var3) {
      String var4 = this.convertToString(var1);
      String var5 = this.convertToString(var2);
      return var4 != null && var5 != null?new Boolean(var4.equalsIgnoreCase(var5)):new Boolean(false);
   }

   Object prim_strcmp(Object var1, Object var2, LContext var3) {
      String var4 = this.convertToString(var1);
      String var5 = this.convertToString(var2);
      return var4 != null && var5 != null?new Double((double)var4.compareToIgnoreCase(var5)):new Boolean(false);
   }

   String convertToString(Object var1) {
      return var1 instanceof String?(String)var1:(var1 instanceof Symbol?((Symbol)var1).pname:(var1 instanceof QuotedSymbol?((QuotedSymbol)var1).sym.pname:null));
   }

}
