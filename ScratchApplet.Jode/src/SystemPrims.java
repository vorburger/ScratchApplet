/* SystemPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Vector;

class SystemPrims extends Primitives
{
    static String[] primlist
	= { "resett", "0", "%timer", "0", "wait", "1", "mwait", "1", "eq", "2",
	    "(", "0", ")", "0", "true", "0", "false", "0", "hexw", "2", "tab",
	    "0", "classof", "1", "class", "1", "string", "1", "print", "1",
	    "prs", "1", "ignore", "1" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_resett(lcontext);
	case 1:
	    return prim_timer(lcontext);
	case 2:
	    return prim_wait(objects[0], lcontext);
	case 3:
	    return prim_mwait(objects[0], lcontext);
	case 4:
	    return prim_eq(objects[0], objects[1], lcontext);
	case 5:
	    return prim_parleft(lcontext);
	case 6:
	    return prim_parright(lcontext);
	case 7:
	    return new Boolean(true);
	case 8:
	    return new Boolean(false);
	case 9:
	    return prim_hexw(objects[0], objects[1], lcontext);
	case 10:
	    return "\t";
	case 11:
	    return objects[0].getClass();
	case 12:
	    return prim_class(objects[0], lcontext);
	case 13:
	    return prstring(objects[0]);
	case 14:
	    ((LContext) lcontext).tyo.println(Logo.prs(objects[0]));
	    return null;
	case 15:
	    ((LContext) lcontext).tyo.print(Logo.prs(objects[0]));
	    return null;
	case 16:
	    return null;
	default:
	    return null;
	}
    }
    
    Object prim_resett(LContext lcontext) {
	Logo.starttime = System.currentTimeMillis();
	return null;
    }
    
    Object prim_timer(LContext lcontext) {
	return new Double((double) (System.currentTimeMillis()
				    - Logo.starttime));
    }
    
    Object prim_wait(Object object, LContext lcontext) {
	int i = (int) (100.0 * Logo.aDouble(object, lcontext));
	sleepForMSecs(i, lcontext);
	return null;
    }
    
    Object prim_mwait(Object object, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	sleepForMSecs(i, lcontext);
	return null;
    }
    
    void sleepForMSecs(int i, LContext lcontext) {
	if (i > 0) {
	    long l = System.currentTimeMillis();
	    for (long l_0_ = System.currentTimeMillis() - l;
		 l_0_ >= 0L && l_0_ < (long) i;
		 l_0_ = System.currentTimeMillis() - l) {
		if (((LContext) lcontext).timeToStop)
		    Logo.error("Stopped!!!", lcontext);
		try {
		    Thread.sleep(Math.min((long) i - l_0_, 10L));
		} catch (InterruptedException interruptedexception) {
		    /* empty */
		}
	    }
	}
    }
    
    Object prim_eq(Object object, Object object_1_, LContext lcontext) {
	return new Boolean(object.equals(object_1_));
    }
    
    Object prim_parright(LContext lcontext) {
	Logo.error("Missing \"(\"", lcontext);
	return null;
    }
    
    Object prim_parleft(LContext lcontext) {
	if (ipmnext(((LContext) lcontext).iline))
	    return ipmcall(lcontext);
	Object object = Logo.eval(lcontext);
	Object object_2_ = ((LContext) lcontext).iline.next();
	if (object_2_ instanceof Symbol
	    && ((Symbol) (Symbol) object_2_).pname.equals(")"))
	    return object;
	Logo.error("Missing \")\"", lcontext);
	return null;
    }
    
    boolean ipmnext(MapList maplist) {
	boolean bool;
	try {
	    bool = ((Function) ((Symbol) (Symbol) maplist.peek()).fcn).ipm;
	} catch (Exception exception) {
	    return false;
	}
	return bool;
    }
    
    Object ipmcall(LContext lcontext) {
	Vector vector = new Vector();
	((LContext) lcontext).cfun
	    = (Symbol) ((LContext) lcontext).iline.next();
	while (!finIpm(((LContext) lcontext).iline))
	    vector.addElement(Logo.evalOneArg(((LContext) lcontext).iline,
					      lcontext));
	Object[] objects = new Object[vector.size()];
	vector.copyInto(objects);
	return Logo.evalSym(((LContext) lcontext).cfun, objects, lcontext);
    }
    
    boolean finIpm(MapList maplist) {
	if (maplist.eof())
	    return true;
	Object object = maplist.peek();
	if (object instanceof Symbol
	    && ((Symbol) (Symbol) object).pname.equals(")")) {
	    maplist.next();
	    return true;
	}
	return false;
    }
    
    Object prim_hexw(Object object, Object object_3_, LContext lcontext) {
	Logo.anInt(object, lcontext);
	String string = Logo.prs(object, 16);
	int i = Logo.anInt(object_3_, lcontext);
	String string_4_ = "00000000".substring(8 - i + string.length());
	return string_4_ + string;
    }
    
    Object prim_class(Object object, LContext lcontext) {
	do {
	    Class var_class;
	    try {
		var_class = Class.forName(Logo.prs(object));
	    } catch (Exception exception) {
		break;
	    } catch (Error error) {
		break;
	    }
	    return var_class;
	} while (false);
	return "";
    }
    
    String prstring(Object object) {
	if (object instanceof Number && Logo.isInt((Number) object))
	    return Long.toString(((Number) object).longValue(), 10);
	if (object instanceof String)
	    return "|" + (String) object + "|";
	if (object instanceof Object[]) {
	    String string = "";
	    Object[] objects = (Object[]) object;
	    for (int i = 0; i < objects.length; i++) {
		if (objects[i] instanceof Object[])
		    string += "[";
		string += prstring(objects[i]);
		if (objects[i] instanceof Object[])
		    string += "]";
		if (i != objects.length - 1)
		    string += " ";
	    }
	    return string;
	}
	return object.toString();
    }
}
