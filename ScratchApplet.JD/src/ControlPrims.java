class ControlPrims extends Primitives
{
  static String[] primlist = { "repeat", "2", "if", "2", "ifelse", "3", "stop", "0", "output", "1", "dotimes", "2", "dolist", "2", "carefully", "2", "errormessage", "0", "unwind-protect", "2", "error", "1", "dispatch", "2", "run", "1", "loop", "1", "forever", "1", "selectq", "2", "stopme", "0" };

  public String[] primlist()
  {
    return primlist;
  }
  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    switch (paramInt) { case 0:
      return prim_repeat(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 1:
      return prim_if(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 2:
      return prim_ifelse(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext);
    case 3:
      return prim_stop(paramLContext);
    case 4:
      return prim_output(paramArrayOfObject[0], paramLContext);
    case 5:
      return prim_dotimes(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 6:
      return prim_dolist(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 7:
      return prim_carefully(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 8:
      return paramLContext.errormessage;
    case 9:
      return prim_unwindprotect(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 10:
      return prim_error(paramArrayOfObject[0], paramLContext);
    case 11:
      return prim_dispatch(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 12:
      return prim_run(paramArrayOfObject[0], paramLContext);
    case 13:
      return prim_loop(paramArrayOfObject[0], paramLContext);
    case 14:
      return prim_loop(paramArrayOfObject[0], paramLContext);
    case 15:
      return prim_selectq(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 16:
      return prim_stopme(paramLContext);
    }
    return null;
  }

  Object prim_repeat(Object paramObject1, Object paramObject2, LContext paramLContext) {
    int i = Logo.anInt(paramObject1, paramLContext);
    Object[] arrayOfObject = Logo.aList(paramObject2, paramLContext);
    for (int j = 0; j < i; j++) { Logo.runCommand(arrayOfObject, paramLContext); if (paramLContext.ufunresult != null) return null; 
    }
    return null;
  }

  Object prim_if(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (Logo.aBoolean(paramObject1, paramLContext)) Logo.runCommand(Logo.aList(paramObject2, paramLContext), paramLContext);
    return null;
  }

  Object prim_ifelse(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    boolean bool = Logo.aBoolean(paramObject1, paramLContext);
    Object[] arrayOfObject1 = Logo.aList(paramObject2, paramLContext);
    Object[] arrayOfObject2 = Logo.aList(paramObject3, paramLContext);
    return bool ? Logo.runList(arrayOfObject1, paramLContext) : Logo.runList(arrayOfObject2, paramLContext);
  }
  Object prim_stop(LContext paramLContext) {
    paramLContext.ufunresult = paramLContext.juststop; return null; } 
  Object prim_output(Object paramObject, LContext paramLContext) { paramLContext.ufunresult = paramObject; return null; }

  Object prim_dotimes(Object paramObject1, Object paramObject2, LContext paramLContext) {
    MapList localMapList = new MapList(Logo.aList(paramObject1, paramLContext));
    Object[] arrayOfObject = Logo.aList(paramObject2, paramLContext);
    Symbol localSymbol = Logo.aSymbol(localMapList.next(), paramLContext);
    int i = Logo.anInt(Logo.evalOneArg(localMapList, paramLContext), paramLContext);
    Logo.checkListEmpty(localMapList, paramLContext);
    Object localObject1 = localSymbol.value;
    try {
      for (int j = 0; j < i; j++) {
        localSymbol.value = new Double(j);
        Logo.runCommand(arrayOfObject, paramLContext);
      }if (paramLContext.ufunresult != null) { Object localObject2 = null;
        return localObject2; }  } finally {
      localSymbol.value = localObject1;
    }return null;
  }

  Object prim_dolist(Object paramObject1, Object paramObject2, LContext paramLContext) {
    MapList localMapList = new MapList(Logo.aList(paramObject1, paramLContext));
    Object[] arrayOfObject1 = Logo.aList(paramObject2, paramLContext);
    Symbol localSymbol = Logo.aSymbol(localMapList.next(), paramLContext);
    Object[] arrayOfObject2 = Logo.aList(Logo.evalOneArg(localMapList, paramLContext), paramLContext);
    Logo.checkListEmpty(localMapList, paramLContext);
    Object localObject1 = localSymbol.value;
    try {
      for (int i = 0; i < arrayOfObject2.length; i++) {
        localSymbol.value = arrayOfObject2[i]; Logo.runCommand(arrayOfObject1, paramLContext);
        if (paramLContext.ufunresult == null) continue; Object localObject2 = null;
        return localObject2;
      } } finally { localSymbol.value = localObject1; }
    return null;
  }

  Object prim_carefully(Object paramObject1, Object paramObject2, LContext paramLContext) {
    Object[] arrayOfObject1 = Logo.aList(paramObject1, paramLContext);
    Object[] arrayOfObject2 = Logo.aList(paramObject2, paramLContext);
    try { return Logo.runList(arrayOfObject1, paramLContext);
    } catch (Exception localException) {
      paramLContext.errormessage = localException.getMessage();
    }return Logo.runList(arrayOfObject2, paramLContext);
  }

  Object prim_unwindprotect(Object paramObject1, Object paramObject2, LContext paramLContext)
  {
    Object[] arrayOfObject1 = Logo.aList(paramObject1, paramLContext);
    Object[] arrayOfObject2 = Logo.aList(paramObject2, paramLContext);
    try { Logo.runCommand(arrayOfObject1, paramLContext); } finally {
      Logo.runCommand(arrayOfObject2, paramLContext);
    }return null;
  }

  Object prim_error(Object paramObject, LContext paramLContext) {
    Logo.error(Logo.prs(paramObject), paramLContext);
    return null;
  }

  Object prim_dispatch(Object paramObject1, Object paramObject2, LContext paramLContext) {
    int i = Logo.anInt(paramObject1, paramLContext);
    Object[] arrayOfObject1 = Logo.aList(paramObject2, paramLContext);
    Object[] arrayOfObject2 = Logo.aList(arrayOfObject1[i], paramLContext);
    return Logo.runList(arrayOfObject2, paramLContext);
  }

  Object prim_run(Object paramObject, LContext paramLContext) {
    return Logo.runList(Logo.aList(paramObject, paramLContext), paramLContext);
  }

  Object prim_loop(Object paramObject, LContext paramLContext) {
    Object[] arrayOfObject = Logo.aList(paramObject, paramLContext);
    do Logo.runCommand(arrayOfObject, paramLContext); while (paramLContext.ufunresult == null); return null;
  }

  Object prim_selectq(Object paramObject1, Object paramObject2, LContext paramLContext)
  {
    Object[] arrayOfObject = Logo.aList(paramObject2, paramLContext);
    for (int i = 0; i < arrayOfObject.length; i += 2)
      if ((arrayOfObject[i] instanceof DottedSymbol) ? Logo.getValue(((DottedSymbol)arrayOfObject[i]).sym, paramLContext).equals(paramObject1) : arrayOfObject[i].equals(paramObject1))
      {
        return Logo.runList((Object[])arrayOfObject[(i + 1)], paramLContext);
      }
    return null;
  }

  Object prim_stopme(LContext paramLContext) {
    Logo.error("", paramLContext);
    return null;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ControlPrims
 * JD-Core Version:    0.6.0
 */