/* Function - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Function
{
    Primitives instance;
    int dispatchOffset;
    int nargs;
    boolean ipm;
    
    Function(Primitives primitives, int i, int i_0_) {
	this(primitives, i, i_0_, false);
    }
    
    Function(Primitives primitives, int i, int i_1_, boolean bool) {
	((Function) this).instance = primitives;
	((Function) this).nargs = i;
	((Function) this).dispatchOffset = i_1_;
	((Function) this).ipm = bool;
    }
}
