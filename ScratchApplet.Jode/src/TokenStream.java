/* TokenStream - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Vector;

class TokenStream
{
    String str;
    int offset = 0;
    
    TokenStream(String string) {
	((TokenStream) this).str = string;
	skipSpace();
    }
    
    Object[] readList(LContext lcontext) {
	Vector vector = new Vector();
	Object object;
	while (!eof() && (object = readToken(lcontext)) != null)
	    vector.addElement(object);
	Object[] objects = new Object[vector.size()];
	vector.copyInto(objects);
	return objects;
    }
    
    Object readToken(LContext lcontext) {
	String string = next();
	do {
	    Double var_double;
	    try {
		if (string.length() <= 2 || string.charAt(0) != '0'
		    || string.charAt(1) != 'x')
		    break;
		var_double
		    = new Double((double) Long.parseLong(string.substring(2),
							 16));
	    } catch (NumberFormatException numberformatexception) {
		break;
	    }
	    return var_double;
	} while (false);
	do {
	    Double var_double;
	    try {
		if (string.length() <= 1 || string.charAt(0) != '$')
		    break;
		var_double
		    = new Double((double) Long.parseLong(string.substring(1),
							 16));
	    } catch (NumberFormatException numberformatexception) {
		break;
	    }
	    return var_double;
	} while (false);
	do {
	    Double var_double;
	    try {
		if (string.length() <= 1 || string.charAt(0) != '0')
		    break;
		var_double
		    = new Double((double) Long.parseLong(string.substring(1),
							 8));
	    } catch (NumberFormatException numberformatexception) {
		break;
	    }
	    return var_double;
	} while (false);
	if (string.equals("]"))
	    return null;
	do {
	    if (Logo.aValidNumber(string)) {
		Double var_double;
		try {
		    Double var_double_0_ = Double.valueOf(string);
		    var_double = var_double_0_;
		} catch (NumberFormatException numberformatexception) {
		    break;
		}
		return var_double;
	    }
	} while (false);
	if (string.charAt(0) == '\"')
	    return new QuotedSymbol(Logo.intern(string.substring(1),
						lcontext));
	if (string.charAt(0) == ':')
	    return new DottedSymbol(Logo.intern(string.substring(1),
						lcontext));
	if (string.equals("["))
	    return readList(lcontext);
	if (string.charAt(0) == '|')
	    return string.substring(1);
	return Logo.intern(string, lcontext);
    }
    
    boolean startsWith(String string) {
	return ((TokenStream) this).str
		   .startsWith(string, ((TokenStream) this).offset);
    }
    
    void skipToNextLine() {
	for (/**/;
	     (!eof()
	      && ("\n\r".indexOf(((TokenStream) this).str
				     .charAt(((TokenStream) this).offset))
		  == -1));
	     ((TokenStream) this).offset++) {
	    /* empty */
	}
	skipSpace();
    }
    
    void skipSpace() {
	while (!eof()
	       && " ;,\t\r\n".indexOf(((TokenStream) this).str.charAt
				      (((TokenStream) this).offset)) != -1) {
	    if (peekChar().equals(";")) {
		for (/**/;
		     (!eof()
		      && "\n\r".indexOf(((TokenStream) this).str.charAt
					(((TokenStream) this).offset)) == -1);
		     ((TokenStream) this).offset++) {
		    /* empty */
		}
	    } else
		((TokenStream) this).offset++;
	}
    }
    
    String nextLine() {
	String string = "";
	while (!eof() && ";\n\r".indexOf(peekChar()) == -1)
	    string += nextChar();
	skipSpace();
	return string;
    }
    
    String next() {
	String string = "";
	if (!delim(peekChar())) {
	    while (!eof() && !delim(peekChar())) {
		if (peekChar().equals("|")) {
		    string += "|" + getVbarString();
		    skipSpace();
		    return string;
		}
		string += nextChar();
	    }
	} else
	    string = nextChar();
	skipSpace();
	return string;
    }
    
    String getVbarString() {
	StringBuffer stringbuffer = new StringBuffer();
	nextChar();
	while (!eof()) {
	    if (peekChar().equals("|")) {
		nextChar();
		break;
	    }
	    stringbuffer.append(nextChar());
	}
	return stringbuffer.toString();
    }
    
    boolean delim(String string) {
	char c = string.charAt(0);
	return "()[] ,\t\r\n".indexOf(c) != -1;
    }
    
    String peekChar() {
	return String.valueOf(((TokenStream) this).str
				  .charAt(((TokenStream) this).offset));
    }
    
    String nextChar() {
	return String.valueOf(((TokenStream) this).str
				  .charAt(((TokenStream) this).offset++));
    }
    
    boolean eof() {
	return (((TokenStream) this).str.length()
		== ((TokenStream) this).offset);
    }
}
