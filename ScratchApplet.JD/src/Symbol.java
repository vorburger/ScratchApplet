class Symbol
{
  String pname;
  Function fcn;
  Object value;

  Symbol(String paramString)
  {
    this.pname = paramString;
  }
  public String toString() { return this.pname;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Symbol
 * JD-Core Version:    0.6.0
 */