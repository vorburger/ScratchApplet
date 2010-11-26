class MapList
{
  Object[] tokens;
  int offset = 0;

  MapList(Object[] paramArrayOfObject) {
    this.tokens = paramArrayOfObject;
  }
  Object next() {
    return this.tokens[(this.offset++)]; } 
  Object peek() { return this.tokens[this.offset]; } 
  boolean eof() { return this.offset == this.tokens.length;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     MapList
 * JD-Core Version:    0.6.0
 */