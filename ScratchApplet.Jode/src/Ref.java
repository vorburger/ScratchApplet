/* Ref - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class Ref
{
    int index;
    
    Ref(int i) {
	((Ref) this).index = i - 1;
    }
    
    public String toString() {
	return "Ref(" + ((Ref) this).index + ")";
    }
}
