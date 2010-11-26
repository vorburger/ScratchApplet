import java.io.PrintStream;
import java.util.Vector;

class SystemPrims extends Primitives
{
  static String[] primlist = { "resett", "0", "%timer", "0", "wait", "1", "mwait", "1", "eq", "2", "(", "0", ")", "0", "true", "0", "false", "0", "hexw", "2", "tab", "0", "classof", "1", "class", "1", "string", "1", "print", "1", "prs", "1", "ignore", "1" };

  public String[] primlist()
  {
    return primlist;
  }
  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    switch (paramInt) { case 0:
      return prim_resett(paramLContext);
    case 1:
      return prim_timer(paramLContext);
    case 2:
      return prim_wait(paramArrayOfObject[0], paramLContext);
    case 3:
      return prim_mwait(paramArrayOfObject[0], paramLContext);
    case 4:
      return prim_eq(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 5:
      return prim_parleft(paramLContext);
    case 6:
      return prim_parright(paramLContext);
    case 7:
      return new Boolean(true);
    case 8:
      return new Boolean(false);
    case 9:
      return prim_hexw(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 10:
      return "\t";
    case 11:
      return paramArrayOfObject[0].getClass();
    case 12:
      return prim_class(paramArrayOfObject[0], paramLContext);
    case 13:
      return prstring(paramArrayOfObject[0]);
    case 14:
      paramLContext.tyo.println(Logo.prs(paramArrayOfObject[0])); return null;
    case 15:
      paramLContext.tyo.print(Logo.prs(paramArrayOfObject[0])); return null;
    case 16:
      return null;
    }
    return null;
  }

  Object prim_resett(LContext paramLContext) {
    Logo.starttime = System.currentTimeMillis();
    return null;
  }

  Object prim_timer(LContext paramLContext) {
    return new Double(System.currentTimeMillis() - Logo.starttime);
  }

  Object prim_wait(Object paramObject, LContext paramLContext) {
    int i = (int)(100.0D * Logo.aDouble(paramObject, paramLContext));
    sleepForMSecs(i, paramLContext);
    return null;
  }

  Object prim_mwait(Object paramObject, LContext paramLContext) {
    int i = Logo.anInt(paramObject, paramLContext);
    sleepForMSecs(i, paramLContext);
    return null;
  }

  void sleepForMSecs(int paramInt, LContext paramLContext) {
    if (paramInt <= 0) return;
    long l1 = System.currentTimeMillis();
    long l2 = System.currentTimeMillis() - l1;
    while ((l2 >= 0L) && (l2 < paramInt)) {
      if (paramLContext.timeToStop) Logo.error("Stopped!!!", paramLContext); try {
        Thread.sleep(Math.min(paramInt - l2, 10L)); } catch (InterruptedException localInterruptedException) {
      }l2 = System.currentTimeMillis() - l1;
    }
  }

  Object prim_eq(Object paramObject1, Object paramObject2, LContext paramLContext) {
    return new Boolean(paramObject1.equals(paramObject2));
  }

  Object prim_parright(LContext paramLContext) {
    Logo.error("Missing \"(\"", paramLContext);
    return null;
  }

  Object prim_parleft(LContext paramLContext) {
    if (ipmnext(paramLContext.iline)) return ipmcall(paramLContext);
    Object localObject1 = Logo.eval(paramLContext); Object localObject2 = paramLContext.iline.next();
    if (((localObject2 instanceof Symbol)) && (((Symbol)localObject2).pname.equals(")")))
    {
      return localObject1;
    }Logo.error("Missing \")\"", paramLContext);
    return null;
  }
  boolean ipmnext(MapList paramMapList) {
    try {
      return ((Symbol)paramMapList.peek()).fcn.ipm; } catch (Exception localException) {
    }return false;
  }

  Object ipmcall(LContext paramLContext) {
    Vector localVector = new Vector();
    paramLContext.cfun = ((Symbol)paramLContext.iline.next());
    while (!finIpm(paramLContext.iline))
      localVector.addElement(Logo.evalOneArg(paramLContext.iline, paramLContext));
    Object[] arrayOfObject = new Object[localVector.size()];
    localVector.copyInto(arrayOfObject);
    return Logo.evalSym(paramLContext.cfun, arrayOfObject, paramLContext);
  }

  boolean finIpm(MapList paramMapList) {
    if (paramMapList.eof()) return true;
    Object localObject = paramMapList.peek();
    if (((localObject instanceof Symbol)) && (((Symbol)localObject).pname.equals(")"))) {
      paramMapList.next();
      return true;
    }
    return false;
  }

  Object prim_hexw(Object paramObject1, Object paramObject2, LContext paramLContext) {
    Logo.anInt(paramObject1, paramLContext);
    String str1 = Logo.prs(paramObject1, 16);
    int i = Logo.anInt(paramObject2, paramLContext);
    String str2 = "00000000".substring(8 - i + str1.length());
    return str2 + str1;
  }
  Object prim_class(Object paramObject, LContext paramLContext) {
    try { return Class.forName(Logo.prs(paramObject)); } catch (Exception localException) {
    } catch (Error localError) {
    }
    return "";
  }

  String prstring(Object paramObject) {
    if (((paramObject instanceof Number)) && (Logo.isInt((Number)paramObject))) {
      return Long.toString(((Number)paramObject).longValue(), 10);
    }
    if ((paramObject instanceof String)) return "|" + (String)paramObject + "|";
    if ((paramObject instanceof Object[])) {
      String str = "";
      Object[] arrayOfObject = (Object[])paramObject;
      for (int i = 0; i < arrayOfObject.length; i++) {
        if ((arrayOfObject[i] instanceof Object[])) str = str + "[";
        str = str + prstring(arrayOfObject[i]);
        if ((arrayOfObject[i] instanceof Object[])) str = str + "]";
        if (i == arrayOfObject.length - 1) continue; str = str + " ";
      }
      return str;
    }
    return paramObject.toString();
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     SystemPrims
 * JD-Core Version:    0.6.0
 */