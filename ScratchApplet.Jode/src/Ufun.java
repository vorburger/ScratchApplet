/* Ufun - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Ufun extends Primitives
{
    Object[] arglist;
    Object[] body;
    
    Ufun(Object[] objects, Object[] objects_0_) {
	((Ufun) this).arglist = objects;
	((Ufun) this).body = objects_0_;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	Object object = null;
	Object[] objects_1_ = new Object[((Ufun) this).arglist.length];
	Symbol symbol = ((LContext) lcontext).ufun;
	((LContext) lcontext).ufun = ((LContext) lcontext).cfun;
	Object[] objects_2_ = ((LContext) lcontext).locals;
	((LContext) lcontext).locals = null;
	for (int i_3_ = 0; i_3_ < ((Ufun) this).arglist.length; i_3_++) {
	    objects_1_[i_3_]
		= ((Symbol) (Symbol) ((Ufun) this).arglist[i_3_]).value;
	    ((Symbol) (Symbol) ((Ufun) this).arglist[i_3_]).value
		= objects[i_3_];
	}
	try {
	    Logo.runCommand(((Ufun) this).body, lcontext);
	    if (((LContext) lcontext).ufunresult != null
		&& (((LContext) lcontext).ufunresult
		    != ((LContext) lcontext).juststop))
		object = ((LContext) lcontext).ufunresult;
	} finally {
	    ((LContext) lcontext).ufun = symbol;
	    for (int i_4_ = 0; i_4_ < ((Ufun) this).arglist.length; i_4_++)
		((Symbol) (Symbol) ((Ufun) this).arglist[i_4_]).value
		    = objects_1_[i_4_];
	    if (((LContext) lcontext).locals != null) {
		for (int i_5_ = 0; i_5_ < ((LContext) lcontext).locals.length;
		     i_5_ += 2)
		    ((Symbol) (Symbol) ((LContext) lcontext).locals[i_5_])
			.value
			= ((LContext) lcontext).locals[i_5_ + 1];
	    }
	    ((LContext) lcontext).locals = objects_2_;
	    ((LContext) lcontext).ufunresult = null;
	}
	return object;
    }
}
