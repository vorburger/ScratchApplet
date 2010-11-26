/* MathPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class MathPrims extends Primitives
{
    static String[] primlist
	= { "sum", "i2", "remainder", "2", "difference", "2", "diff", "2",
	    "product", "i2", "quotient", "2", "greater?", "2", "less?", "2",
	    "int", "1", "minus", "1", "round", "1", "sqrt", "1", "sin", "1",
	    "cos", "1", "tan", "1", "abs", "1", "power", "2", "arctan", "1",
	    "pi", "0", "exp", "1", "arctan2", "2", "ln", "1", "logand", "2",
	    "logior", "2", "logxor", "2", "lsh", "2", "and", "i2", "or", "i2",
	    "not", "1", "random", "1", "min", "i2", "max", "i2", "number?",
	    "1", "+", "-2", "-", "-2", "*", "-3", "/", "-3", "<", "-1", ">",
	    "-1", "=", "-1", "equal?", "i2", "%", "-3", "rand", "0", "strequ",
	    "2", "arcsin", "1", "arccos", "1", "strcmp", "2" };
    static final double degtor = 57.29577951308232;
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_sum(objects, lcontext);
	case 1:
	    return prim_remainder(objects[0], objects[1], lcontext);
	case 2:
	case 3:
	    return prim_diff(objects[0], objects[1], lcontext);
	case 4:
	    return prim_product(objects, lcontext);
	case 5:
	    return prim_quotient(objects[0], objects[1], lcontext);
	case 6:
	    return prim_greaterp(objects[0], objects[1], lcontext);
	case 7:
	    return prim_lessp(objects[0], objects[1], lcontext);
	case 8:
	    return prim_int(objects[0], lcontext);
	case 9:
	    return prim_minus(objects[0], lcontext);
	case 10:
	    return prim_round(objects[0], lcontext);
	case 11:
	    return prim_sqrt(objects[0], lcontext);
	case 12:
	    return prim_sin(objects[0], lcontext);
	case 13:
	    return prim_cos(objects[0], lcontext);
	case 14:
	    return prim_tan(objects[0], lcontext);
	case 15:
	    return prim_abs(objects[0], lcontext);
	case 16:
	    return prim_power(objects[0], objects[1], lcontext);
	case 17:
	    return prim_arctan(objects[0], lcontext);
	case 18:
	    return prim_pi(lcontext);
	case 19:
	    return prim_exp(objects[0], lcontext);
	case 20:
	    return prim_arctan2(objects[0], objects[1], lcontext);
	case 21:
	    return prim_ln(objects[0], lcontext);
	case 22:
	    return prim_logand(objects[0], objects[1], lcontext);
	case 23:
	    return prim_logior(objects[0], objects[1], lcontext);
	case 24:
	    return prim_logxor(objects[0], objects[1], lcontext);
	case 25:
	    return prim_lsh(objects[0], objects[1], lcontext);
	case 26:
	    return prim_and(objects, lcontext);
	case 27:
	    return prim_or(objects, lcontext);
	case 28:
	    return prim_not(objects[0], lcontext);
	case 29:
	    return prim_random(objects[0], lcontext);
	case 30:
	    return prim_min(objects, lcontext);
	case 31:
	    return prim_max(objects, lcontext);
	case 32:
	    return prim_numberp(objects[0], lcontext);
	case 33:
	    return prim_sum(objects, lcontext);
	case 34:
	    return prim_diff(objects[0], objects[1], lcontext);
	case 35:
	    return prim_product(objects, lcontext);
	case 36:
	    return prim_quotient(objects[0], objects[1], lcontext);
	case 37:
	    return prim_lessp(objects[0], objects[1], lcontext);
	case 38:
	    return prim_greaterp(objects[0], objects[1], lcontext);
	case 39:
	    return prim_equalp(objects, lcontext);
	case 40:
	    return prim_equalp(objects, lcontext);
	case 41:
	    return prim_remainder(objects[0], objects[1], lcontext);
	case 42:
	    return new Double(Math.random());
	case 43:
	    return prim_strequ(objects[0], objects[1], lcontext);
	case 44:
	    return prim_arcsin(objects[0], lcontext);
	case 45:
	    return prim_arccos(objects[0], lcontext);
	case 46:
	    return prim_strcmp(objects[0], objects[1], lcontext);
	default:
	    return null;
	}
    }
    
    Object prim_sum(Object[] objects, LContext lcontext) {
	double d = 0.0;
	for (int i = 0; i < objects.length; i++)
	    d += Logo.aDouble(objects[i], lcontext);
	return new Double(d);
    }
    
    Object prim_remainder(Object object, Object object_0_, LContext lcontext) {
	return new Double(Logo.aDouble(object, lcontext)
			  % Logo.aDouble(object_0_, lcontext));
    }
    
    Object prim_diff(Object object, Object object_1_, LContext lcontext) {
	return new Double(Logo.aDouble(object, lcontext)
			  - Logo.aDouble(object_1_, lcontext));
    }
    
    Object prim_product(Object[] objects, LContext lcontext) {
	double d = 1.0;
	for (int i = 0; i < objects.length; i++)
	    d *= Logo.aDouble(objects[i], lcontext);
	return new Double(d);
    }
    
    Object prim_quotient(Object object, Object object_2_, LContext lcontext) {
	return new Double(Logo.aDouble(object, lcontext)
			  / Logo.aDouble(object_2_, lcontext));
    }
    
    Object prim_greaterp(Object object, Object object_3_, LContext lcontext) {
	return new Boolean(Logo.aDouble(object, lcontext)
			   > Logo.aDouble(object_3_, lcontext));
    }
    
    Object prim_lessp(Object object, Object object_4_, LContext lcontext) {
	return new Boolean(Logo.aDouble(object, lcontext)
			   < Logo.aDouble(object_4_, lcontext));
    }
    
    Object prim_int(Object object, LContext lcontext) {
	return new Double((double) new Double
				       (Logo.aDouble(object, lcontext))
				       .intValue());
    }
    
    Object prim_minus(Object object, LContext lcontext) {
	return new Double(0.0 - Logo.aDouble(object, lcontext));
    }
    
    Object prim_round(Object object, LContext lcontext) {
	return new Double((double) Math.round(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_sqrt(Object object, LContext lcontext) {
	return new Double(Math.sqrt(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_sin(Object object, LContext lcontext) {
	return new Double(Math.sin(Logo.aDouble(object, lcontext)
				   / 57.29577951308232));
    }
    
    Object prim_cos(Object object, LContext lcontext) {
	return new Double(Math.cos(Logo.aDouble(object, lcontext)
				   / 57.29577951308232));
    }
    
    Object prim_tan(Object object, LContext lcontext) {
	return new Double(Math.tan(Logo.aDouble(object, lcontext)
				   / 57.29577951308232));
    }
    
    Object prim_abs(Object object, LContext lcontext) {
	return new Double(Math.abs(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_power(Object object, Object object_5_, LContext lcontext) {
	return new Double(Math.pow(Logo.aDouble(object, lcontext),
				   Logo.aDouble(object_5_, lcontext)));
    }
    
    Object prim_arcsin(Object object, LContext lcontext) {
	return new Double(57.29577951308232
			  * Math.asin(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_arccos(Object object, LContext lcontext) {
	return new Double(57.29577951308232
			  * Math.acos(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_arctan(Object object, LContext lcontext) {
	return new Double(57.29577951308232
			  * Math.atan(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_pi(LContext lcontext) {
	return new Double(3.141592653589793);
    }
    
    Object prim_exp(Object object, LContext lcontext) {
	return new Double(Math.exp(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_arctan2(Object object, Object object_6_, LContext lcontext) {
	return new Double(57.29577951308232
			  * Math.atan2(Logo.aDouble(object, lcontext),
				       Logo.aDouble(object_6_, lcontext)));
    }
    
    Object prim_ln(Object object, LContext lcontext) {
	return new Double(Math.log(Logo.aDouble(object, lcontext)));
    }
    
    Object prim_logand(Object object, Object object_7_, LContext lcontext) {
	return new Double((double) (Logo.anInt(object, lcontext)
				    & Logo.anInt(object_7_, lcontext)));
    }
    
    Object prim_logior(Object object, Object object_8_, LContext lcontext) {
	return new Double((double) (Logo.anInt(object, lcontext)
				    | Logo.anInt(object_8_, lcontext)));
    }
    
    Object prim_logxor(Object object, Object object_9_, LContext lcontext) {
	return new Double((double) (Logo.anInt(object, lcontext)
				    ^ Logo.anInt(object_9_, lcontext)));
    }
    
    Object prim_lsh(Object object, Object object_10_, LContext lcontext) {
	int i = Logo.anInt(object_10_, lcontext);
	int i_11_ = Logo.anInt(object, lcontext);
	return (i > 0 ? new Double((double) (i_11_ << i))
		: new Double((double) (i_11_ >> -i)));
    }
    
    Object prim_and(Object[] objects, LContext lcontext) {
	boolean bool = true;
	for (int i = 0; i < objects.length; i++)
	    bool &= Logo.aBoolean(objects[i], lcontext);
	return new Boolean(bool);
    }
    
    Object prim_or(Object[] objects, LContext lcontext) {
	boolean bool = false;
	for (int i = 0; i < objects.length; i++)
	    bool |= Logo.aBoolean(objects[i], lcontext);
	return new Boolean(bool);
    }
    
    Object prim_not(Object object, LContext lcontext) {
	return new Boolean(!Logo.aBoolean(object, lcontext));
    }
    
    Object prim_random(Object object, LContext lcontext) {
	return new Double(Math.floor(Math.random()
				     * (double) Logo.anInt(object, lcontext)));
    }
    
    Object prim_min(Object[] objects, LContext lcontext) {
	if (objects.length == 0)
	    Logo.error("Min needs at least one input", lcontext);
	double d = Logo.aDouble(objects[0], lcontext);
	for (int i = 1; i < objects.length; i++)
	    d = Math.min(d, Logo.aDouble(objects[i], lcontext));
	return new Double(d);
    }
    
    Object prim_max(Object[] objects, LContext lcontext) {
	if (objects.length == 0)
	    Logo.error("Max needs at least one input", lcontext);
	double d = Logo.aDouble(objects[0], lcontext);
	for (int i = 1; i < objects.length; i++)
	    d = Math.max(d, Logo.aDouble(objects[i], lcontext));
	return new Double(d);
    }
    
    Object prim_numberp(Object object, LContext lcontext) {
	return new Boolean(object instanceof Number);
    }
    
    Object prim_equalp(Object[] objects, LContext lcontext) {
	if (objects.length == 0)
	    Logo.error("Equal needs at least one input", lcontext);
	Object object = objects[0];
	for (int i = 1; i < objects.length; i++) {
	    if (object != objects[i]
		&& !Logo.prs(object).equals(Logo.prs(objects[i])))
		return new Boolean(false);
	}
	return new Boolean(true);
    }
    
    Object prim_strequ(Object object, Object object_12_, LContext lcontext) {
	String string = convertToString(object);
	String string_13_ = convertToString(object_12_);
	if (string == null || string_13_ == null)
	    return new Boolean(false);
	return new Boolean(string.equalsIgnoreCase(string_13_));
    }
    
    Object prim_strcmp(Object object, Object object_14_, LContext lcontext) {
	String string = convertToString(object);
	String string_15_ = convertToString(object_14_);
	if (string == null || string_15_ == null)
	    return new Boolean(false);
	return new Double((double) string.compareToIgnoreCase(string_15_));
    }
    
    String convertToString(Object object) {
	if (object instanceof String)
	    return (String) object;
	if (object instanceof Symbol)
	    return ((Symbol) (Symbol) object).pname;
	if (object instanceof QuotedSymbol)
	    return ((Symbol) ((QuotedSymbol) (QuotedSymbol) object).sym).pname;
	return null;
    }
}
