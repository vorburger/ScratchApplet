class Ufun extends Primitives
{
  Object[] arglist;
  Object[] body;

  Ufun(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    this.arglist = paramArrayOfObject1; this.body = paramArrayOfObject2;
  }
  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    Object localObject1 = null;
    Object[] arrayOfObject1 = new Object[this.arglist.length];
    Symbol localSymbol = paramLContext.ufun; paramLContext.ufun = paramLContext.cfun;
    Object[] arrayOfObject2 = paramLContext.locals; paramLContext.locals = null;
    for (int i = 0; i < this.arglist.length; i++) {
      arrayOfObject1[i] = ((Symbol)this.arglist[i]).value;
      ((Symbol)this.arglist[i]).value = paramArrayOfObject[i];
    }
    try {
      Logo.runCommand(this.body, paramLContext);
      if ((paramLContext.ufunresult != null) && (paramLContext.ufunresult != paramLContext.juststop)) localObject1 = paramLContext.ufunresult; 
    }
    finally
    {
      paramLContext.ufun = localSymbol;
      for (int j = 0; j < this.arglist.length; j++)
        ((Symbol)this.arglist[j]).value = arrayOfObject1[j];
      if (paramLContext.locals != null)
        for (j = 0; j < paramLContext.locals.length; j += 2)
          ((Symbol)paramLContext.locals[j]).value = paramLContext.locals[(j + 1)];
      paramLContext.locals = arrayOfObject2;
      paramLContext.ufunresult = null;
    }
    return localObject1;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Ufun
 * JD-Core Version:    0.6.0
 */