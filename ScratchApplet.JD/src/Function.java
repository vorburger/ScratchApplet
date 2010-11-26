class Function
{
  Primitives instance;
  int dispatchOffset;
  int nargs;
  boolean ipm;

  Function(Primitives paramPrimitives, int paramInt1, int paramInt2)
  {
    this(paramPrimitives, paramInt1, paramInt2, false);
  }
  Function(Primitives paramPrimitives, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.instance = paramPrimitives;
    this.nargs = paramInt1;
    this.dispatchOffset = paramInt2;
    this.ipm = paramBoolean;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Function
 * JD-Core Version:    0.6.0
 */