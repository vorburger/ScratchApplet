/* Symbol - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Symbol
{
    String pname;
    Function fcn;
    Object value;
    
    Symbol(String string) {
	((Symbol) this).pname = string;
    }
    
    public String toString() {
	return ((Symbol) this).pname;
    }
}
