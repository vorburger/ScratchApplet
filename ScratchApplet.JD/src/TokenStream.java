import java.util.Vector;

class TokenStream
{
  String str;
  int offset = 0;

  TokenStream(String paramString) {
    this.str = paramString;
    skipSpace();
  }

  Object[] readList(LContext paramLContext) {
    Vector localVector = new Vector();
    Object localObject;
    while ((!eof()) && ((localObject = readToken(paramLContext)) != null)) localVector.addElement(localObject);
    Object[] arrayOfObject = new Object[localVector.size()];
    localVector.copyInto(arrayOfObject);
    return arrayOfObject;
  }

  Object readToken(LContext paramLContext) {
    String str1 = next();
    try {
      if ((str1.length() > 2) && (str1.charAt(0) == '0') && (str1.charAt(1) == 'x'))
        return new Double(Long.parseLong(str1.substring(2), 16));  } catch (NumberFormatException localNumberFormatException1) {
    }
    try {
      if ((str1.length() > 1) && (str1.charAt(0) == '$'))
        return new Double(Long.parseLong(str1.substring(1), 16));  } catch (NumberFormatException localNumberFormatException2) {
    }
    try {
      if ((str1.length() > 1) && (str1.charAt(0) == '0'))
        return new Double(Long.parseLong(str1.substring(1), 8));  } catch (NumberFormatException localNumberFormatException3) {
    }
    if (str1.equals("]")) return null;
    if (Logo.aValidNumber(str1)) try {
        return Double.valueOf(str1);
      } catch (NumberFormatException localNumberFormatException4) {
      } if (str1.charAt(0) == '"')
      return new QuotedSymbol(Logo.intern(str1.substring(1), paramLContext));
    if (str1.charAt(0) == ':')
      return new DottedSymbol(Logo.intern(str1.substring(1), paramLContext));
    if (str1.equals("[")) return readList(paramLContext);
    if (str1.charAt(0) == '|')
      return str1.substring(1);
    return Logo.intern(str1, paramLContext);
  }
  boolean startsWith(String paramString) {
    return this.str.startsWith(paramString, this.offset);
  }
  void skipToNextLine() {
    while ((!eof()) && ("\n\r".indexOf(this.str.charAt(this.offset)) == -1)) this.offset += 1;
    skipSpace();
  }

  void skipSpace() {
    while ((!eof()) && (" ;,\t\r\n".indexOf(this.str.charAt(this.offset)) != -1)) {
      if (peekChar().equals(";"))
        while ((!eof()) && ("\n\r".indexOf(this.str.charAt(this.offset)) == -1))
          this.offset += 1;
      this.offset += 1;
    }
  }

  String nextLine() {
    String str1 = "";
    while ((!eof()) && (";\n\r".indexOf(peekChar()) == -1)) str1 = str1 + nextChar();
    skipSpace();
    return str1;
  }

  String next() {
    String str1 = "";
    if (!delim(peekChar()))
    {
      while ((!eof()) && 
        (!delim(peekChar()))) {
        if (peekChar().equals("|")) {
          str1 = str1 + "|" + getVbarString();
          skipSpace();
          return str1;
        }
        str1 = str1 + nextChar();
      }
    }
    str1 = nextChar();
    skipSpace();
    return str1;
  }

  String getVbarString() {
    StringBuffer localStringBuffer = new StringBuffer();
    nextChar();

    while (!eof()) {
      if (peekChar().equals("|")) { nextChar(); break; }
      localStringBuffer.append(nextChar());
    }
    return localStringBuffer.toString();
  }

  boolean delim(String paramString) {
    int i = paramString.charAt(0);
    return "()[] ,\t\r\n".indexOf(i) != -1;
  }
  String peekChar() {
    return String.valueOf(this.str.charAt(this.offset)); } 
  String nextChar() { return String.valueOf(this.str.charAt(this.offset++)); } 
  boolean eof() { return this.str.length() == this.offset;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     TokenStream
 * JD-Core Version:    0.6.0
 */