/* DefiningPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class DefiningPrims extends Primitives
{
    static String[] primlist
	= { "make", "2", "define", "3", "let", "1", "thing", "1", "put", "3",
	    "get", "2", "getp", "2", "plist", "1", "erplist", "1", "name?",
	    "1", "defined?", "1", "clearname", "1", "quote", "1", "intern",
	    "1" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_make(objects[0], objects[1], lcontext);
	case 1:
	    return prim_define(objects[0], objects[1], objects[2], lcontext);
	case 2:
	    return prim_let(objects[0], lcontext);
	case 3:
	    return prim_thing(objects[0], lcontext);
	case 4:
	    return prim_put(objects[0], objects[1], objects[2], lcontext);
	case 5:
	    return prim_get(objects[0], objects[1], lcontext);
	case 6:
	    return prim_get(objects[0], objects[1], lcontext);
	case 7:
	    return prim_plist(objects[0], lcontext);
	case 8:
	    return prim_erplist(objects[0], lcontext);
	case 9:
	    return prim_namep(objects[0], lcontext);
	case 10:
	    return prim_definedp(objects[0], lcontext);
	case 11:
	    return prim_clearname(objects[0], lcontext);
	case 12:
	    return prim_quote(objects[0], lcontext);
	case 13:
	    return prim_intern(objects[0], lcontext);
	default:
	    return null;
	}
    }
    
    Object prim_make(Object object, Object object_0_, LContext lcontext) {
	Logo.setValue(Logo.aSymbol(object, lcontext), object_0_, lcontext);
	return null;
    }
    
    Object prim_clearname(Object object, LContext lcontext) {
	Logo.setValue(Logo.aSymbol(object, lcontext), null, lcontext);
	return null;
    }
    
    Object prim_define(Object object, Object object_1_, Object object_2_,
		       LContext lcontext) {
	Symbol symbol = Logo.aSymbol(object, lcontext);
	Object[] objects = Logo.aList(object_1_, lcontext);
	Object[] objects_3_ = Logo.aList(object_2_, lcontext);
	Ufun ufun = new Ufun(objects, objects_3_);
	((Symbol) symbol).fcn = new Function(ufun, objects.length, 0);
	return null;
    }
    
    Object prim_let(Object object, LContext lcontext) {
	Vector vector = new Vector();
	if (((LContext) lcontext).locals != null) {
	    for (int i = 0; i < ((LContext) lcontext).locals.length; i++)
		vector.addElement(((LContext) lcontext).locals[i]);
	}
	MapList maplist = new MapList(Logo.aList(object, lcontext));
	while (!maplist.eof()) {
	    Symbol symbol = Logo.aSymbol(maplist.next(), lcontext);
	    vector.addElement(symbol);
	    vector.addElement(((Symbol) symbol).value);
	    Logo.setValue(symbol, Logo.evalOneArg(maplist, lcontext),
			  lcontext);
	}
	((LContext) lcontext).locals = new Object[vector.size()];
	vector.copyInto(((LContext) lcontext).locals);
	return null;
    }
    
    Object prim_thing(Object object, LContext lcontext) {
	return Logo.getValue(Logo.aSymbol(object, lcontext), lcontext);
    }
    
    Object prim_put(Object object, Object object_4_, Object object_5_,
		    LContext lcontext) {
	Hashtable hashtable
	    = (Hashtable) ((LContext) lcontext).props.get(object);
	if (hashtable == null) {
	    hashtable = new Hashtable();
	    ((LContext) lcontext).props.put(object, hashtable);
	}
	hashtable.put(object_4_, object_5_);
	return null;
    }
    
    Object prim_get(Object object, Object object_6_, LContext lcontext) {
	Hashtable hashtable
	    = (Hashtable) ((LContext) lcontext).props.get(object);
	if (hashtable == null)
	    return new Object[0];
	Object object_7_ = hashtable.get(object_6_);
	if (object_7_ == null)
	    return new Object[0];
	return object_7_;
    }
    
    Object prim_plist(Object object, LContext lcontext) {
	Hashtable hashtable
	    = (Hashtable) ((LContext) lcontext).props.get(object);
	if (hashtable == null)
	    return new Object[0];
	Vector vector = new Vector();
	Enumeration enumeration = hashtable.keys();
	while (enumeration.hasMoreElements()) {
	    Object object_8_ = enumeration.nextElement();
	    vector.add(object_8_);
	    vector.add(hashtable.get(object_8_));
	}
	Object[] objects = new Object[vector.size()];
	vector.copyInto(objects);
	return objects;
    }
    
    Object prim_erplist(Object object, LContext lcontext) {
	((LContext) lcontext).props.remove(object);
	return null;
    }
    
    Object prim_namep(Object object, LContext lcontext) {
	return new Boolean(((Symbol) Logo.aSymbol(object, lcontext)).value
			   != null);
    }
    
    Object prim_definedp(Object object, LContext lcontext) {
	return new Boolean(((Symbol) Logo.aSymbol(object, lcontext)).fcn
			   != null);
    }
    
    Object prim_quote(Object object, LContext lcontext) {
	if (object instanceof Object[])
	    return object;
	return new QuotedSymbol(Logo.aSymbol(object, lcontext));
    }
    
    Object prim_intern(Object object, LContext lcontext) {
	return Logo.aSymbol(object, lcontext);
    }
}
