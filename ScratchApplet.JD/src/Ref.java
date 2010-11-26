class Ref
{
  int index;

  Ref(int paramInt)
  {
    this.index = (paramInt - 1);
  }

  public String toString() {
    return "Ref(" + this.index + ")";
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Ref
 * JD-Core Version:    0.6.0
 */