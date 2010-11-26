/* ControlPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class ControlPrims extends Primitives
{
    static String[] primlist
	= { "repeat", "2", "if", "2", "ifelse", "3", "stop", "0", "output",
	    "1", "dotimes", "2", "dolist", "2", "carefully", "2",
	    "errormessage", "0", "unwind-protect", "2", "error", "1",
	    "dispatch", "2", "run", "1", "loop", "1", "forever", "1",
	    "selectq", "2", "stopme", "0" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_repeat(objects[0], objects[1], lcontext);
	case 1:
	    return prim_if(objects[0], objects[1], lcontext);
	case 2:
	    return prim_ifelse(objects[0], objects[1], objects[2], lcontext);
	case 3:
	    return prim_stop(lcontext);
	case 4:
	    return prim_output(objects[0], lcontext);
	case 5:
	    return prim_dotimes(objects[0], objects[1], lcontext);
	case 6:
	    return prim_dolist(objects[0], objects[1], lcontext);
	case 7:
	    return prim_carefully(objects[0], objects[1], lcontext);
	case 8:
	    return ((LContext) lcontext).errormessage;
	case 9:
	    return prim_unwindprotect(objects[0], objects[1], lcontext);
	case 10:
	    return prim_error(objects[0], lcontext);
	case 11:
	    return prim_dispatch(objects[0], objects[1], lcontext);
	case 12:
	    return prim_run(objects[0], lcontext);
	case 13:
	    return prim_loop(objects[0], lcontext);
	case 14:
	    return prim_loop(objects[0], lcontext);
	case 15:
	    return prim_selectq(objects[0], objects[1], lcontext);
	case 16:
	    return prim_stopme(lcontext);
	default:
	    return null;
	}
    }
    
    Object prim_repeat(Object object, Object object_0_, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	Object[] objects = Logo.aList(object_0_, lcontext);
	for (int i_1_ = 0; i_1_ < i; i_1_++) {
	    Logo.runCommand(objects, lcontext);
	    if (((LContext) lcontext).ufunresult != null)
		return null;
	}
	return null;
    }
    
    Object prim_if(Object object, Object object_2_, LContext lcontext) {
	if (Logo.aBoolean(object, lcontext))
	    Logo.runCommand(Logo.aList(object_2_, lcontext), lcontext);
	return null;
    }
    
    Object prim_ifelse(Object object, Object object_3_, Object object_4_,
		       LContext lcontext) {
	boolean bool = Logo.aBoolean(object, lcontext);
	Object[] objects = Logo.aList(object_3_, lcontext);
	Object[] objects_5_ = Logo.aList(object_4_, lcontext);
	return (bool ? Logo.runList(objects, lcontext)
		: Logo.runList(objects_5_, lcontext));
    }
    
    Object prim_stop(LContext lcontext) {
	((LContext) lcontext).ufunresult = ((LContext) lcontext).juststop;
	return null;
    }
    
    Object prim_output(Object object, LContext lcontext) {
	((LContext) lcontext).ufunresult = object;
	return null;
    }
    
    Object prim_dotimes(Object object, Object object_6_, LContext lcontext) {
	MapList maplist = new MapList(Logo.aList(object, lcontext));
	Object[] objects = Logo.aList(object_6_, lcontext);
	Symbol symbol = Logo.aSymbol(maplist.next(), lcontext);
	int i = Logo.anInt(Logo.evalOneArg(maplist, lcontext), lcontext);
	Logo.checkListEmpty(maplist, lcontext);
	Object object_7_ = ((Symbol) symbol).value;
	do {
	    Object object_8_;
	    try {
		for (int i_9_ = 0; i_9_ < i; i_9_++) {
		    ((Symbol) symbol).value = new Double((double) i_9_);
		    Logo.runCommand(objects, lcontext);
		}
		if (((LContext) lcontext).ufunresult == null)
		    break;
		object_8_ = null;
	    } catch (Object object_10_) {
		((Symbol) symbol).value = object_7_;
		throw object_10_;
	    }
	    ((Symbol) symbol).value = object_7_;
	    return object_8_;
	} while (false);
	((Symbol) symbol).value = object_7_;
	return null;
    }
    
    Object prim_dolist(Object object, Object object_11_, LContext lcontext) {
	object_14_ = object_17_;
	break while_6_;
    }
    
    Object prim_carefully(Object object, Object object_19_,
			  LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	Object[] objects_20_ = Logo.aList(object_19_, lcontext);
	Object object_21_;
	try {
	    object_21_ = Logo.runList(objects, lcontext);
	} catch (Exception exception) {
	    ((LContext) lcontext).errormessage = exception.getMessage();
	    return Logo.runList(objects_20_, lcontext);
	}
	return object_21_;
    }
    
    Object prim_unwindprotect(Object object, Object object_22_,
			      LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	Object[] objects_23_ = Logo.aList(object_22_, lcontext);
	try {
	    Logo.runCommand(objects, lcontext);
	} catch (Object object_24_) {
	    Logo.runCommand(objects_23_, lcontext);
	    throw object_24_;
	}
	Logo.runCommand(objects_23_, lcontext);
	return null;
    }
    
    Object prim_error(Object object, LContext lcontext) {
	Logo.error(Logo.prs(object), lcontext);
	return null;
    }
    
    Object prim_dispatch(Object object, Object object_25_, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	Object[] objects = Logo.aList(object_25_, lcontext);
	Object[] objects_26_ = Logo.aList(objects[i], lcontext);
	return Logo.runList(objects_26_, lcontext);
    }
    
    Object prim_run(Object object, LContext lcontext) {
	return Logo.runList(Logo.aList(object, lcontext), lcontext);
    }
    
    Object prim_loop(Object object, LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	do
	    Logo.runCommand(objects, lcontext);
	while (((LContext) lcontext).ufunresult == null);
	return null;
    }
    
    Object prim_selectq(Object object, Object object_27_, LContext lcontext) {
	Object[] objects = Logo.aList(object_27_, lcontext);
	for (int i = 0; i < objects.length; i += 2) {
	    if (objects[i] instanceof DottedSymbol) {
		if (!Logo.getValue
			 (((DottedSymbol) (DottedSymbol) objects[i]).sym,
			  lcontext)
			 .equals(object))
		    continue;
	    } else if (!objects[i].equals(object))
		continue;
	    return Logo.runList((Object[]) objects[i + 1], lcontext);
	}
	return null;
    }
    
    Object prim_stopme(LContext lcontext) {
	Logo.error("", lcontext);
	return null;
    }
}
