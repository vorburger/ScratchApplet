/* MapList - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class MapList
{
    Object[] tokens;
    int offset = 0;
    
    MapList(Object[] objects) {
	((MapList) this).tokens = objects;
    }
    
    Object next() {
	return ((MapList) this).tokens[((MapList) this).offset++];
    }
    
    Object peek() {
	return ((MapList) this).tokens[((MapList) this).offset];
    }
    
    boolean eof() {
	return ((MapList) this).offset == ((MapList) this).tokens.length;
    }
}
