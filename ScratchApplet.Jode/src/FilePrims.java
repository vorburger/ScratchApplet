/* FilePrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

class FilePrims extends Primitives
{
    String readtext;
    int textoffset;
    static String[] primlist
	= { "filetostring", "1", "resourcetostring", "1", "load", "1",
	    "stringtofile", "2", "file?", "1", "setread", "1", "readline", "0",
	    "eot?", "0", "lineback", "0", "filenamefrompath", "1",
	    "dirnamefrompath", "1", "dir", "1" };
    /*synthetic*/ static Class class$FilePrims;
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_filetostring(objects[0], lcontext);
	case 1:
	    return prim_resourcetostring(objects[0], lcontext);
	case 2:
	    return prim_load(objects[0], lcontext);
	case 3:
	    return prim_stringtofile(objects[0], objects[1], lcontext);
	case 4:
	    return prim_filep(objects[0], lcontext);
	case 5:
	    return prim_setread(objects[0], lcontext);
	case 6:
	    return prim_readline(lcontext);
	case 7:
	    return prim_eot(lcontext);
	case 8:
	    return prim_lineback(lcontext);
	case 9:
	    return prim_filenamefrompath(objects[0], lcontext);
	case 10:
	    return prim_dirnamefrompath(objects[0], lcontext);
	case 11:
	    return prim_dir(objects[0], lcontext);
	default:
	    return null;
	}
    }
    
    Object prim_filetostring(Object object, LContext lcontext) {
	String string = Logo.prs(object);
	return fileToString(string, lcontext);
    }
    
    Object prim_resourcetostring(Object object, LContext lcontext) {
	String string = Logo.prs(object);
	return resourceToString(string, lcontext);
    }
    
    Object prim_load(Object object, LContext lcontext) {
	String string = Logo.prs(object);
	String string_0_ = System.getProperty("file.separator");
	if (lcontext != null) {
	    /* empty */
	}
	String string_1_ = resourceToString(string + ".logo", lcontext);
	Logo.readAllFunctions(string_1_, lcontext);
	return null;
    }
    
    String resourceToString(String string, LContext lcontext) {
	InputStream inputstream
	    = (class$FilePrims == null ? class$FilePrims = class$("FilePrims")
	       : class$FilePrims)
		  .getResourceAsStream(string);
	BufferedReader bufferedreader
	    = new BufferedReader(new InputStreamReader(inputstream));
	StringWriter stringwriter = new StringWriter();
	PrintWriter printwriter
	    = new PrintWriter(new BufferedWriter(stringwriter), true);
	String string_2_;
	try {
	    String string_3_;
	    while ((string_3_ = bufferedreader.readLine()) != null)
		printwriter.println(string_3_);
	    String string_4_ = stringwriter.toString();
	    string_2_ = string_4_;
	} catch (IOException ioexception) {
	    Logo.error("Can't open file " + string, lcontext);
	    return null;
	}
	return string_2_;
    }
    
    String fileToString(String string, LContext lcontext) {
	byte[] is = null;
	Object object = null;
	try {
	    File file = new File(string);
	    int i = (int) file.length();
	    FileInputStream fileinputstream = new FileInputStream(file);
	    DataInputStream datainputstream
		= new DataInputStream(fileinputstream);
	    is = new byte[i];
	    datainputstream.readFully(is);
	    fileinputstream.close();
	} catch (IOException ioexception) {
	    Logo.error("Can't open file " + string, lcontext);
	}
	return new String(is);
    }
    
    Object prim_stringtofile(Object object, Object object_5_,
			     LContext lcontext) {
	String string = Logo.prs(object);
	String string_6_ = Logo.prs(object_5_);
	try {
	    FileWriter filewriter = new FileWriter(string);
	    filewriter.write(string_6_, 0, string_6_.length());
	    filewriter.close();
	} catch (IOException ioexception) {
	    Logo.error("Can't write file " + string, lcontext);
	}
	return null;
    }
    
    Object prim_filep(Object object, LContext lcontext) {
	String string = Logo.prs(object);
	return new Boolean(new File(string).exists());
    }
    
    Object prim_setread(Object object, LContext lcontext) {
	((FilePrims) this).readtext = Logo.prs(object);
	((FilePrims) this).textoffset = 0;
	return null;
    }
    
    Object prim_readline(LContext lcontext) {
	String string = "";
	int i = ((FilePrims) this).readtext
		    .indexOf("\n", ((FilePrims) this).textoffset);
	if (i == -1) {
	    if (((FilePrims) this).textoffset
		< ((FilePrims) this).readtext.length()) {
		string
		    = ((FilePrims) this).readtext.substring((((FilePrims) this)
							     .textoffset),
							    ((FilePrims) this)
								.readtext
								.length());
		((FilePrims) this).textoffset
		    = ((FilePrims) this).readtext.length();
	    }
	} else {
	    string = ((FilePrims) this).readtext
			 .substring(((FilePrims) this).textoffset, i);
	    ((FilePrims) this).textoffset = i + 1;
	}
	if (string.length() == 0)
	    return string;
	if (string.charAt(string.length() - 1) == '\r')
	    string = string.substring(0, string.length() - 1);
	return string;
    }
    
    Object prim_eot(LContext lcontext) {
	return new Boolean(((FilePrims) this).textoffset
			   >= ((FilePrims) this).readtext.length());
    }
    
    Object prim_lineback(LContext lcontext) {
	int i = ((FilePrims) this).readtext
		    .lastIndexOf("\n", ((FilePrims) this).textoffset - 2);
	if (i < 0)
	    ((FilePrims) this).textoffset = 0;
	else
	    ((FilePrims) this).textoffset = i + 1;
	return null;
    }
    
    Object prim_filenamefrompath(Object object, LContext lcontext) {
	return new File(Logo.prs(object)).getName();
    }
    
    Object prim_dirnamefrompath(Object object, LContext lcontext) {
	File file = new File(Logo.prs(object));
	if (file.isDirectory())
	    return file.getPath();
	return file.getParent();
    }
    
    Object prim_dir(Object object, LContext lcontext) {
	String[] strings = new File(Logo.prs(object)).list();
	if (strings == null)
	    return new Object[0];
	return strings;
    }
    
    /*synthetic*/ static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
}
