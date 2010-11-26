class MathPrims extends Primitives
{
  static String[] primlist = { "sum", "i2", "remainder", "2", "difference", "2", "diff", "2", "product", "i2", "quotient", "2", "greater?", "2", "less?", "2", "int", "1", "minus", "1", "round", "1", "sqrt", "1", "sin", "1", "cos", "1", "tan", "1", "abs", "1", "power", "2", "arctan", "1", "pi", "0", "exp", "1", "arctan2", "2", "ln", "1", "logand", "2", "logior", "2", "logxor", "2", "lsh", "2", "and", "i2", "or", "i2", "not", "1", "random", "1", "min", "i2", "max", "i2", "number?", "1", "+", "-2", "-", "-2", "*", "-3", "/", "-3", "<", "-1", ">", "-1", "=", "-1", "equal?", "i2", "%", "-3", "rand", "0", "strequ", "2", "arcsin", "1", "arccos", "1", "strcmp", "2" };
  static final double degtor = 57.295779513082323D;

  public String[] primlist()
  {
    return primlist;
  }

  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    switch (paramInt) { case 0:
      return prim_sum(paramArrayOfObject, paramLContext);
    case 1:
      return prim_remainder(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 2:
    case 3:
      return prim_diff(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 4:
      return prim_product(paramArrayOfObject, paramLContext);
    case 5:
      return prim_quotient(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 6:
      return prim_greaterp(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 7:
      return prim_lessp(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 8:
      return prim_int(paramArrayOfObject[0], paramLContext);
    case 9:
      return prim_minus(paramArrayOfObject[0], paramLContext);
    case 10:
      return prim_round(paramArrayOfObject[0], paramLContext);
    case 11:
      return prim_sqrt(paramArrayOfObject[0], paramLContext);
    case 12:
      return prim_sin(paramArrayOfObject[0], paramLContext);
    case 13:
      return prim_cos(paramArrayOfObject[0], paramLContext);
    case 14:
      return prim_tan(paramArrayOfObject[0], paramLContext);
    case 15:
      return prim_abs(paramArrayOfObject[0], paramLContext);
    case 16:
      return prim_power(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 17:
      return prim_arctan(paramArrayOfObject[0], paramLContext);
    case 18:
      return prim_pi(paramLContext);
    case 19:
      return prim_exp(paramArrayOfObject[0], paramLContext);
    case 20:
      return prim_arctan2(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 21:
      return prim_ln(paramArrayOfObject[0], paramLContext);
    case 22:
      return prim_logand(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 23:
      return prim_logior(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 24:
      return prim_logxor(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 25:
      return prim_lsh(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 26:
      return prim_and(paramArrayOfObject, paramLContext);
    case 27:
      return prim_or(paramArrayOfObject, paramLContext);
    case 28:
      return prim_not(paramArrayOfObject[0], paramLContext);
    case 29:
      return prim_random(paramArrayOfObject[0], paramLContext);
    case 30:
      return prim_min(paramArrayOfObject, paramLContext);
    case 31:
      return prim_max(paramArrayOfObject, paramLContext);
    case 32:
      return prim_numberp(paramArrayOfObject[0], paramLContext);
    case 33:
      return prim_sum(paramArrayOfObject, paramLContext);
    case 34:
      return prim_diff(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 35:
      return prim_product(paramArrayOfObject, paramLContext);
    case 36:
      return prim_quotient(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 37:
      return prim_lessp(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 38:
      return prim_greaterp(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 39:
      return prim_equalp(paramArrayOfObject, paramLContext);
    case 40:
      return prim_equalp(paramArrayOfObject, paramLContext);
    case 41:
      return prim_remainder(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 42:
      return new Double(Math.random());
    case 43:
      return prim_strequ(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 44:
      return prim_arcsin(paramArrayOfObject[0], paramLContext);
    case 45:
      return prim_arccos(paramArrayOfObject[0], paramLContext);
    case 46:
      return prim_strcmp(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    }
    return null;
  }

  Object prim_sum(Object[] paramArrayOfObject, LContext paramLContext) {
    double d = 0.0D;
    for (int i = 0; i < paramArrayOfObject.length; i++) d += Logo.aDouble(paramArrayOfObject[i], paramLContext);
    return new Double(d);
  }

  Object prim_remainder(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.aDouble(paramObject1, paramLContext) % Logo.aDouble(paramObject2, paramLContext));
  }

  Object prim_diff(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.aDouble(paramObject1, paramLContext) - Logo.aDouble(paramObject2, paramLContext));
  }

  Object prim_product(Object[] paramArrayOfObject, LContext paramLContext) {
    double d = 1.0D;
    for (int i = 0; i < paramArrayOfObject.length; i++) d *= Logo.aDouble(paramArrayOfObject[i], paramLContext);
    return new Double(d);
  }

  Object prim_quotient(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.aDouble(paramObject1, paramLContext) / Logo.aDouble(paramObject2, paramLContext));
  }
  Object prim_greaterp(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Boolean(Logo.aDouble(paramObject1, paramLContext) > Logo.aDouble(paramObject2, paramLContext));
  }

  Object prim_lessp(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Boolean(Logo.aDouble(paramObject1, paramLContext) < Logo.aDouble(paramObject2, paramLContext));
  }

  Object prim_int(Object paramObject, LContext paramLContext) {
    return new Double(new Double(Logo.aDouble(paramObject, paramLContext)).intValue());
  }

  Object prim_minus(Object paramObject, LContext paramLContext) {
    return new Double(0.0D - Logo.aDouble(paramObject, paramLContext));
  }

  Object prim_round(Object paramObject, LContext paramLContext) {
    return new Double(Math.round(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_sqrt(Object paramObject, LContext paramLContext) {
    return new Double(Math.sqrt(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_sin(Object paramObject, LContext paramLContext) {
    return new Double(Math.sin(Logo.aDouble(paramObject, paramLContext) / 57.295779513082323D));
  }

  Object prim_cos(Object paramObject, LContext paramLContext) {
    return new Double(Math.cos(Logo.aDouble(paramObject, paramLContext) / 57.295779513082323D));
  }

  Object prim_tan(Object paramObject, LContext paramLContext) {
    return new Double(Math.tan(Logo.aDouble(paramObject, paramLContext) / 57.295779513082323D));
  }

  Object prim_abs(Object paramObject, LContext paramLContext) {
    return new Double(Math.abs(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_power(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Math.pow(Logo.aDouble(paramObject1, paramLContext), Logo.aDouble(paramObject2, paramLContext)));
  }

  Object prim_arcsin(Object paramObject, LContext paramLContext) {
    return new Double(57.295779513082323D * Math.asin(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_arccos(Object paramObject, LContext paramLContext) {
    return new Double(57.295779513082323D * Math.acos(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_arctan(Object paramObject, LContext paramLContext) {
    return new Double(57.295779513082323D * Math.atan(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_pi(LContext paramLContext) {
    return new Double(3.141592653589793D);
  }

  Object prim_exp(Object paramObject, LContext paramLContext) {
    return new Double(Math.exp(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_arctan2(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(57.295779513082323D * Math.atan2(Logo.aDouble(paramObject1, paramLContext), Logo.aDouble(paramObject2, paramLContext)));
  }

  Object prim_ln(Object paramObject, LContext paramLContext) {
    return new Double(Math.log(Logo.aDouble(paramObject, paramLContext)));
  }

  Object prim_logand(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.anInt(paramObject1, paramLContext) & Logo.anInt(paramObject2, paramLContext));
  }

  Object prim_logior(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.anInt(paramObject1, paramLContext) | Logo.anInt(paramObject2, paramLContext));
  }

  Object prim_logxor(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Double(Logo.anInt(paramObject1, paramLContext) ^ Logo.anInt(paramObject2, paramLContext));
  }

  Object prim_lsh(Object paramObject1, Object paramObject2, LContext paramLContext) {
    int i = Logo.anInt(paramObject2, paramLContext); int j = Logo.anInt(paramObject1, paramLContext);
    return i > 0 ? new Double(j << i) : new Double(j >> -i);
  }

  Object prim_and(Object[] paramArrayOfObject, LContext paramLContext) {
    boolean bool = true;
    for (int i = 0; i < paramArrayOfObject.length; i++) bool &= Logo.aBoolean(paramArrayOfObject[i], paramLContext);
    return new Boolean(bool);
  }

  Object prim_or(Object[] paramArrayOfObject, LContext paramLContext) {
    boolean bool = false;
    for (int i = 0; i < paramArrayOfObject.length; i++) bool |= Logo.aBoolean(paramArrayOfObject[i], paramLContext);
    return new Boolean(bool);
  }

  Object prim_not(Object paramObject, LContext paramLContext) {
    return new Boolean(!Logo.aBoolean(paramObject, paramLContext));
  }

  Object prim_random(Object paramObject, LContext paramLContext) {
    return new Double(Math.floor(Math.random() * Logo.anInt(paramObject, paramLContext)));
  }

  Object prim_min(Object[] paramArrayOfObject, LContext paramLContext) {
    if (paramArrayOfObject.length == 0) Logo.error("Min needs at least one input", paramLContext);
    double d = Logo.aDouble(paramArrayOfObject[0], paramLContext);
    for (int i = 1; i < paramArrayOfObject.length; i++) d = Math.min(d, Logo.aDouble(paramArrayOfObject[i], paramLContext));
    return new Double(d);
  }

  Object prim_max(Object[] paramArrayOfObject, LContext paramLContext) {
    if (paramArrayOfObject.length == 0) Logo.error("Max needs at least one input", paramLContext);
    double d = Logo.aDouble(paramArrayOfObject[0], paramLContext);
    for (int i = 1; i < paramArrayOfObject.length; i++) d = Math.max(d, Logo.aDouble(paramArrayOfObject[i], paramLContext));
    return new Double(d);
  }

  Object prim_numberp(Object paramObject, LContext paramLContext) {
    return new Boolean(paramObject instanceof Number);
  }

  Object prim_equalp(Object[] paramArrayOfObject, LContext paramLContext)
  {
    if (paramArrayOfObject.length == 0) Logo.error("Equal needs at least one input", paramLContext);
    Object localObject = paramArrayOfObject[0];
    for (int i = 1; i < paramArrayOfObject.length; i++) {
      if ((localObject != paramArrayOfObject[i]) && 
        (!Logo.prs(localObject).equals(Logo.prs(paramArrayOfObject[i])))) return new Boolean(false);
    }
    return new Boolean(true);
  }

  Object prim_strequ(Object paramObject1, Object paramObject2, LContext paramLContext) {
    String str1 = convertToString(paramObject1);
    String str2 = convertToString(paramObject2);
    if ((str1 == null) || (str2 == null)) return new Boolean(false);
    return new Boolean(str1.equalsIgnoreCase(str2));
  }

  Object prim_strcmp(Object paramObject1, Object paramObject2, LContext paramLContext) {
    String str1 = convertToString(paramObject1);
    String str2 = convertToString(paramObject2);
    if ((str1 == null) || (str2 == null)) return new Boolean(false);
    return new Double(str1.compareToIgnoreCase(str2));
  }

  String convertToString(Object paramObject) {
    if ((paramObject instanceof String)) return (String)paramObject;
    if ((paramObject instanceof Symbol)) return ((Symbol)paramObject).pname;
    if ((paramObject instanceof QuotedSymbol)) return ((QuotedSymbol)paramObject).sym.pname;
    return null;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     MathPrims
 * JD-Core Version:    0.6.0
 */