/* QuotedSymbol - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class QuotedSymbol
{
    Symbol sym;
    
    QuotedSymbol(Symbol symbol) {
	((QuotedSymbol) this).sym = symbol;
    }
    
    public String toString() {
	return "\"" + ((QuotedSymbol) this).sym.toString();
    }
}
