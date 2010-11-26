/* DottedSymbol - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class DottedSymbol
{
    Symbol sym;
    
    DottedSymbol(Symbol symbol) {
	((DottedSymbol) this).sym = symbol;
    }
    
    public String toString() {
	return ":" + ((DottedSymbol) this).sym.toString();
    }
}
