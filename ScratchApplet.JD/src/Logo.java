import java.io.PrintStream;
import java.util.HashSet;
import java.util.Hashtable;

public class Logo
{
  static long starttime = System.currentTimeMillis();

  static String runToplevel(Object[] paramArrayOfObject, LContext paramLContext) {
    paramLContext.iline = new MapList(paramArrayOfObject);
    paramLContext.timeToStop = false;
    try { evLine(paramLContext); } catch (LogoError localLogoError) {
      if (localLogoError.getMessage() != null) return localLogoError.getMessage(); 
    } catch (Exception localException) {
      localException.printStackTrace(); return localException.toString(); } catch (Error localError) {
      return localError.toString();
    }return null;
  }

  static void evLine(LContext paramLContext)
  {
    while ((!paramLContext.iline.eof()) && (paramLContext.ufunresult == null))
    {
      Object localObject;
      if ((localObject = eval(paramLContext)) == null) continue; error("You don't say what to do with " + prs(localObject), paramLContext);
    }
  }

  static Object eval(LContext paramLContext) {
    Object localObject = evalToken(paramLContext);
    while (infixNext(paramLContext.iline, paramLContext)) {
      if ((localObject instanceof Nothing)) error(paramLContext.iline.peek() + " needs more inputs", paramLContext);
      localObject = evalInfix(localObject, paramLContext);
    }
    return localObject;
  }

  static Object evalToken(LContext paramLContext) {
    Object localObject = paramLContext.iline.next();
    if ((localObject instanceof QuotedSymbol)) return ((QuotedSymbol)localObject).sym;
    if ((localObject instanceof DottedSymbol)) return getValue(((DottedSymbol)localObject).sym, paramLContext);
    if ((localObject instanceof Symbol)) return evalSym((Symbol)localObject, null, paramLContext);
    if ((localObject instanceof String)) return evalSym(intern((String)localObject, paramLContext), null, paramLContext);
    return localObject;
  }

  static Object evalSym(Symbol paramSymbol, Object[] paramArrayOfObject, LContext paramLContext) {
    if (paramLContext.timeToStop) error("Stopped!!!", paramLContext);
    if (paramSymbol.fcn == null) error("I don't know how to " + paramSymbol, paramLContext);
    Symbol localSymbol = paramLContext.cfun; paramLContext.cfun = paramSymbol;
    int i = paramLContext.priority; paramLContext.priority = 0;
    Object localObject1 = null;
    try { Function localFunction = paramSymbol.fcn;
      int j = localFunction.nargs;
      if (paramArrayOfObject == null) paramArrayOfObject = evalArgs(j, paramLContext);
      localObject1 = localFunction.instance.dispatch(localFunction.dispatchOffset, paramArrayOfObject, paramLContext);
    } catch (RuntimeException localRuntimeException) {
      errorHandler(paramSymbol, paramArrayOfObject, localRuntimeException, paramLContext); } finally {
      paramLContext.cfun = localSymbol; paramLContext.priority = i;
    }if ((paramLContext.mustOutput) && (localObject1 == null)) error(paramSymbol + " didn't output to " + paramLContext.cfun, paramLContext);
    return localObject1;
  }

  static Object[] evalArgs(int paramInt, LContext paramLContext) {
    boolean bool = paramLContext.mustOutput; paramLContext.mustOutput = true;
    Object[] arrayOfObject = new Object[paramInt];
    try {
      for (int i = 0; i < paramInt; i++) {
        if (paramLContext.iline.eof()) error(paramLContext.cfun + " needs more inputs", paramLContext);
        arrayOfObject[i] = eval(paramLContext);
        if (!(arrayOfObject[i] instanceof Nothing)) continue; error(paramLContext.cfun + " needs more inputs", paramLContext);
      }
    } finally {
      paramLContext.mustOutput = bool;
    }return arrayOfObject;
  }

  static void runCommand(Object[] paramArrayOfObject, LContext paramLContext) {
    boolean bool = paramLContext.mustOutput; paramLContext.mustOutput = false;
    try { runList(paramArrayOfObject, paramLContext); } finally {
      paramLContext.mustOutput = bool;
    }
  }

  static Object runList(Object[] paramArrayOfObject, LContext paramLContext) {
    MapList localMapList = paramLContext.iline;
    paramLContext.iline = new MapList(paramArrayOfObject);
    Object localObject1 = null;
    try { if (paramLContext.mustOutput) localObject1 = eval(paramLContext); else evLine(paramLContext);
      checkListEmpty(paramLContext.iline, paramLContext); } finally {
      paramLContext.iline = localMapList;
    }return localObject1;
  }

  static Object evalOneArg(MapList paramMapList, LContext paramLContext) {
    boolean bool = paramLContext.mustOutput; paramLContext.mustOutput = true;
    MapList localMapList = paramLContext.iline; paramLContext.iline = paramMapList;
    try { Object localObject1 = eval(paramLContext);
      return localObject1; } finally { paramLContext.iline = localMapList; paramLContext.mustOutput = bool; } throw localObject2;
  }

  static boolean infixNext(MapList paramMapList, LContext paramLContext) {
    Object localObject = null; Function localFunction = null;
    return (!paramMapList.eof()) && (((localObject = paramMapList.peek()) instanceof Symbol)) && ((localFunction = ((Symbol)localObject).fcn) != null) && (localFunction.nargs < paramLContext.priority);
  }

  static Object evalInfix(Object paramObject, LContext paramLContext)
  {
    Symbol localSymbol1 = (Symbol)paramLContext.iline.next();
    Function localFunction = localSymbol1.fcn;
    Symbol localSymbol2 = paramLContext.cfun; paramLContext.cfun = localSymbol1;
    int i = paramLContext.priority; paramLContext.priority = localFunction.nargs;
    Object localObject1 = null;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = paramObject;
    try { Object[] arrayOfObject2 = evalArgs(1, paramLContext);
      arrayOfObject1[1] = arrayOfObject2[0];
      localObject1 = localFunction.instance.dispatch(localFunction.dispatchOffset, arrayOfObject1, paramLContext);
    } catch (RuntimeException localRuntimeException) {
      errorHandler(localSymbol1, arrayOfObject1, localRuntimeException, paramLContext); } finally {
      paramLContext.cfun = localSymbol2; paramLContext.priority = i;
    }if ((paramLContext.mustOutput) && (localObject1 == null)) error(localSymbol1 + " didn't output to " + paramLContext.cfun, paramLContext);
    return localObject1;
  }

  static Symbol intern(String paramString, LContext paramLContext)
  {
    String str;
    if (paramString.length() == 0) str = paramString;
    else if (paramString.charAt(0) == '|') str = paramString = paramString.substring(1); else
      str = paramString;
    Symbol localSymbol = (Symbol)paramLContext.oblist.get(str);
    if (localSymbol == null) paramLContext.oblist.put(str, localSymbol = new Symbol(paramString));
    return localSymbol;
  }

  static Object[] parse(String paramString, LContext paramLContext) {
    TokenStream localTokenStream = new TokenStream(paramString);
    return localTokenStream.readList(paramLContext);
  }
  static String prs(Object paramObject) {
    return prs(paramObject, 10);
  }
  static String prs(Object paramObject, int paramInt) { return prs(paramObject, paramInt, new HashSet()); }

  static String prs(Object paramObject, int paramInt, HashSet paramHashSet) {
    if (((paramObject instanceof Number)) && (paramInt == 16))
      return Long.toString(((Number)paramObject).longValue(), 16).toUpperCase();
    if (((paramObject instanceof Number)) && (paramInt == 8))
      return Long.toString(((Number)paramObject).longValue(), 8);
    if (((paramObject instanceof Number)) && (isInt((Number)paramObject)))
      return Long.toString(((Number)paramObject).longValue(), 10);
    if ((paramObject instanceof Object[])) {
      Object[] arrayOfObject = (Object[])paramObject;
      if ((arrayOfObject.length > 0) && (paramHashSet.contains(paramObject))) return "...";
      if (arrayOfObject.length > 0) paramHashSet.add(paramObject);
      String str = "";
      for (int i = 0; i < arrayOfObject.length; i++) {
        if ((arrayOfObject[i] instanceof Object[])) str = str + "[";
        str = str + prs(arrayOfObject[i], paramInt, paramHashSet);
        if ((arrayOfObject[i] instanceof Object[])) str = str + "]";
        if (i == arrayOfObject.length - 1) continue; str = str + " ";
      }
      return str;
    }
    return paramObject.toString();
  }

  static boolean isInt(Number paramNumber) {
    return paramNumber.doubleValue() == new Integer(paramNumber.intValue()).doubleValue();
  }

  static boolean aValidNumber(String paramString) {
    if ((paramString.length() == 1) && ("0123456789".indexOf(paramString.charAt(0)) == -1))
      return false;
    if ("eE.+-0123456789".indexOf(paramString.charAt(0)) == -1) return false;
    for (int i = 1; i < paramString.length(); i++)
      if ("eE.0123456789".indexOf(paramString.charAt(i)) == -1) return false;
    return true;
  }

  static Object getValue(Symbol paramSymbol, LContext paramLContext)
  {
    Object localObject;
    if ((localObject = paramSymbol.value) != null) return localObject;
    error(paramSymbol + " has no value", paramLContext);
    return null;
  }
  static void setValue(Symbol paramSymbol, Object paramObject, LContext paramLContext) {
    paramSymbol.value = paramObject;
  }
  static double aDouble(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Double)) return ((Double)paramObject).doubleValue();
    String str = prs(paramObject);
    if ((str.length() > 0) && (aValidNumber(str))) return Double.valueOf(str).doubleValue();
    error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    return 0.0D;
  }

  static int anInt(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Double)) return ((Double)paramObject).intValue();
    String str = prs(paramObject);

    if (aValidNumber(str)) return Double.valueOf(str).intValue();
    error(paramLContext.cfun + " doesn't like " + str + " as input", paramLContext);
    return 0;
  }

  static long aLong(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Double)) return ((Double)paramObject).longValue();
    String str = prs(paramObject);
    if (aValidNumber(str)) return Double.valueOf(str).longValue();
    error(paramLContext.cfun + " doesn't like " + str + " as input", paramLContext);
    return 0L;
  }

  static boolean aBoolean(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Boolean)) return ((Boolean)paramObject).booleanValue();
    if ((paramObject instanceof Symbol)) return ((Symbol)paramObject).pname.equals("true");
    error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    return false;
  }

  static Object[] aList2Double(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Object[])) {
      if ((((Object[])paramObject).length == 2) && 
        ((((Object[])paramObject)[0] instanceof Double)) && ((((Object[])paramObject)[1] instanceof Double)))
      {
        return (Object[])paramObject;
      }error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    }
    return null;
  }

  static Object[] aList(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Object[])) return (Object[])paramObject;
    error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    return null;
  }

  static Symbol aSymbol(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Symbol)) return (Symbol)paramObject;
    if ((paramObject instanceof String)) return intern((String)paramObject, paramLContext);
    if ((paramObject instanceof Number)) {
      String str = String.valueOf(((Number)paramObject).longValue());
      return intern(str, paramLContext);
    }
    error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    return null;
  }

  static String aString(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof String)) return (String)paramObject;
    if ((paramObject instanceof Symbol)) return ((Symbol)paramObject).toString();
    error(paramLContext.cfun + " doesn't like " + prs(paramObject) + " as input", paramLContext);
    return null;
  }

  static void setupPrims(String[] paramArrayOfString, LContext paramLContext) {
    for (int i = 0; i < paramArrayOfString.length; i++) setupPrims(paramArrayOfString[i], paramLContext); 
  }

  static void setupPrims(String paramString, LContext paramLContext)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      Primitives localPrimitives = (Primitives)localClass.newInstance();
      String[] arrayOfString = localPrimitives.primlist();
      for (int i = 0; i < arrayOfString.length; i += 2) {
        String str = arrayOfString[(i + 1)];
        boolean bool = str.startsWith("i");
        if (bool) str = str.substring(1);
        Symbol localSymbol = intern(arrayOfString[i], paramLContext);
        localSymbol.fcn = new Function(localPrimitives, Integer.parseInt(str), i / 2, bool);
      }
    } catch (Exception localException) {
      System.out.println(localException.toString());
    }
  }

  static void checkListEmpty(MapList paramMapList, LContext paramLContext) {
    if ((!paramMapList.eof()) && (paramLContext.ufunresult == null)) error("You don't say what to do with " + prs(paramMapList.next()), paramLContext); 
  }

  static void errorHandler(Symbol paramSymbol, Object[] paramArrayOfObject, RuntimeException paramRuntimeException, LContext paramLContext)
  {
    if (((paramRuntimeException instanceof ArrayIndexOutOfBoundsException)) || ((paramRuntimeException instanceof StringIndexOutOfBoundsException)) || ((paramRuntimeException instanceof NegativeArraySizeException)))
    {
      error(paramSymbol + " doesn't like " + prs(paramArrayOfObject[0]) + " as input", paramLContext);
    } else throw paramRuntimeException; 
  }

  static void error(String paramString, LContext paramLContext)
  {
    if (paramString.equals("")) throw new LogoError(null);
    paramString = paramString + (paramLContext.ufun == null ? "" : new StringBuffer().append(" in ").append(paramLContext.ufun).toString());
    throw new LogoError(paramString);
  }

  static void readAllFunctions(String paramString, LContext paramLContext)
  {
    TokenStream localTokenStream = new TokenStream(paramString);
    while (true)
      switch (findKeyWord(localTokenStream)) { case 0:
        return;
      case 1:
        doDefine(localTokenStream, paramLContext); break;
      case 2:
        doTo(localTokenStream, paramLContext); }
  }

  static int findKeyWord(TokenStream paramTokenStream)
  {
    while (true)
    {
      if (paramTokenStream.eof()) return 0;
      if (paramTokenStream.startsWith("define ")) return 1;
      if (paramTokenStream.startsWith("to ")) return 2;
      paramTokenStream.skipToNextLine();
    }
  }

  static void doDefine(TokenStream paramTokenStream, LContext paramLContext) {
    paramTokenStream.readToken(paramLContext);
    Symbol localSymbol = aSymbol(paramTokenStream.readToken(paramLContext), paramLContext);
    Object[] arrayOfObject1 = aList(paramTokenStream.readToken(paramLContext), paramLContext);
    Object[] arrayOfObject2 = aList(paramTokenStream.readToken(paramLContext), paramLContext);
    Ufun localUfun = new Ufun(arrayOfObject1, arrayOfObject2);
    localSymbol.fcn = new Function(localUfun, arrayOfObject1.length, 0);
  }

  static void doTo(TokenStream paramTokenStream, LContext paramLContext) {
    Object[] arrayOfObject1 = parse(paramTokenStream.nextLine(), paramLContext);
    Object[] arrayOfObject2 = parse(readBody(paramTokenStream, paramLContext), paramLContext);
    Object[] arrayOfObject3 = getArglistFromTitle(arrayOfObject1);
    Symbol localSymbol = aSymbol(arrayOfObject1[1], paramLContext);
    Ufun localUfun = new Ufun(arrayOfObject3, arrayOfObject2);
    localSymbol.fcn = new Function(localUfun, arrayOfObject3.length, 0);
  }

  static String readBody(TokenStream paramTokenStream, LContext paramLContext) {
    String str1 = "";
    while (true) {
      if (paramTokenStream.eof()) return str1;
      String str2 = paramTokenStream.nextLine();
      if ((str2.startsWith("end")) && ("end".equals(((Symbol)parse(str2, paramLContext)[0]).pname)))
        return str1;
      str1 = str1 + " " + str2;
    }
  }

  static Object[] getArglistFromTitle(Object[] paramArrayOfObject) {
    Object[] arrayOfObject = new Object[paramArrayOfObject.length - 2];
    for (int i = 0; i < arrayOfObject.length; i++)
      arrayOfObject[i] = ((DottedSymbol)paramArrayOfObject[(i + 2)]).sym;
    return arrayOfObject;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Logo
 * JD-Core Version:    0.6.0
 */