class QuotedSymbol
{
  Symbol sym;

  QuotedSymbol(Symbol paramSymbol)
  {
    this.sym = paramSymbol; } 
  public String toString() { return "\"" + this.sym.toString();
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     QuotedSymbol
 * JD-Core Version:    0.6.0
 */