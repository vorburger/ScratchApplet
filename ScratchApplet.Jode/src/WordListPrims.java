/* WordListPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class WordListPrims extends Primitives
{
    static String[] primlist
	= { "first", "1", "last", "1", "word", "i2", "butfirst", "1", "bf",
	    "1", "butlast", "1", "bl", "1", "fput", "2", "lput", "2", "item",
	    "2", "nth", "2", "empty?", "1", "count", "1", "word?", "1",
	    "list?", "1", "member?", "2", "itempos", "2", "setitem", "3",
	    "setnth", "3", "removeitem", "2", "removeitempos", "2", "sentence",
	    "2", "se", "i2", "list", "i2", "makelist", "1", "copylist", "1",
	    "parse", "1", "char", "1", "ascii", "1", "reverse", "1",
	    "substring", "3", "ucase", "1", "insert", "3" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    return prim_first(objects[0], lcontext);
	case 1:
	    return prim_last(objects[0], lcontext);
	case 2:
	    return prim_word(objects, lcontext);
	case 3:
	case 4:
	    return prim_butfirst(objects[0], lcontext);
	case 5:
	case 6:
	    return prim_butlast(objects[0], lcontext);
	case 7:
	    return prim_fput(objects[0], objects[1], lcontext);
	case 8:
	    return prim_lput(objects[0], objects[1], lcontext);
	case 9:
	    return prim_item(objects[0], objects[1], lcontext);
	case 10:
	    return prim_nth(objects[0], objects[1], lcontext);
	case 11:
	    return prim_emptyp(objects[0], lcontext);
	case 12:
	    return prim_count(objects[0], lcontext);
	case 13:
	    return prim_wordp(objects[0], lcontext);
	case 14:
	    return prim_listp(objects[0], lcontext);
	case 15:
	    return prim_memberp(objects[0], objects[1], lcontext);
	case 16:
	    return prim_itempos(objects[0], objects[1], lcontext);
	case 17:
	    return prim_setitem(objects[0], objects[1], objects[2], lcontext);
	case 18:
	    return prim_setnth(objects[0], objects[1], objects[2], lcontext);
	case 19:
	    return prim_removeitem(objects[0], objects[1], lcontext);
	case 20:
	    return prim_removeitempos(objects[0], objects[1], lcontext);
	case 21:
	case 22:
	    return prim_sentence(objects, lcontext);
	case 23:
	    return prim_list(objects, lcontext);
	case 24:
	    return prim_makelist(objects[0], lcontext);
	case 25:
	    return prim_copylist(objects[0], lcontext);
	case 26:
	    return prim_parse(objects[0], lcontext);
	case 27:
	    return prim_char(objects[0], lcontext);
	case 28:
	    return prim_ascii(objects[0], lcontext);
	case 29:
	    return prim_reverse(objects[0], lcontext);
	case 30:
	    return prim_substring(objects[0], objects[1], objects[2],
				  lcontext);
	case 31:
	    return prim_ucase(objects[0], lcontext);
	case 32:
	    return prim_insert(objects[0], objects[1], objects[2], lcontext);
	default:
	    return null;
	}
    }
    
    Object copyList(Object[] objects, int i, int i_0_) {
	Object[] objects_1_ = new Object[i_0_];
	for (int i_2_ = 0; i_2_ < i_0_; i_2_++)
	    objects_1_[i_2_] = objects[i++];
	return objects_1_;
    }
    
    Object addToList(Object[] objects, Object object) {
	if (!(object instanceof Object[]))
	    return lput(object, objects);
	Object[] objects_3_ = (Object[]) object;
	Object[] objects_4_ = new Object[objects.length + objects_3_.length];
	for (int i = 0; i < objects.length; i++)
	    objects_4_[i] = objects[i];
	for (int i = 0; i < objects_3_.length; i++)
	    objects_4_[i + objects.length] = objects_3_[i];
	return objects_4_;
    }
    
    Object removeItem(Object[] objects, int i) {
	Object[] objects_5_ = new Object[objects.length - 1];
	int i_6_ = 0;
	for (int i_7_ = 0; i_7_ < objects.length; i_7_++) {
	    if (i_7_ != i - 1)
		objects_5_[i_6_++] = objects[i_7_];
	}
	return objects_5_;
    }
    
    static Object lput(Object object, Object[] objects) {
	Object[] objects_8_ = new Object[objects.length + 1];
	for (int i = 0; i < objects.length; i++)
	    objects_8_[i] = objects[i];
	objects_8_[objects.length] = object;
	return objects_8_;
    }
    
    static int memberp(Object object, Object object_9_) {
	if (object_9_ instanceof Object[]) {
	    Object[] objects = (Object[]) object_9_;
	    for (int i = 0; i < objects.length; i++) {
		if (Logo.prs(object).equals(Logo.prs(objects[i])))
		    return i + 1;
	    }
	    return 0;
	}
	if (object instanceof Object[])
	    return 0;
	String string = Logo.prs(object);
	String string_10_ = Logo.prs(object_9_);
	for (int i = 0; i < string_10_.length(); i++) {
	    if (string.regionMatches(true, 0, string_10_, i, string.length()))
		return i + 1;
	}
	return 0;
    }
    
    Object prim_first(Object object, LContext lcontext) {
	if (object instanceof Object[])
	    return ((Object[]) object)[0];
	return Logo.prs(object).substring(0, 1);
    }
    
    Object prim_last(Object object, LContext lcontext) {
	if (object instanceof Object[]) {
	    Object[] objects = (Object[]) object;
	    return objects[objects.length - 1];
	}
	String string = Logo.prs(object);
	return string.substring(string.length() - 1, string.length());
    }
    
    Object prim_word(Object[] objects, LContext lcontext) {
	String string = "";
	for (int i = 0; i < objects.length; i++)
	    string += Logo.prs(objects[i]);
	return string;
    }
    
    Object prim_butfirst(Object object, LContext lcontext) {
	if (object instanceof Object[]) {
	    Object[] objects = (Object[]) object;
	    return copyList(objects, 1, objects.length - 1);
	}
	String string = Logo.prs(object);
	return string.substring(1, string.length());
    }
    
    Object prim_butlast(Object object, LContext lcontext) {
	if (object instanceof Object[]) {
	    Object[] objects = (Object[]) object;
	    return copyList(objects, 0, objects.length - 1);
	}
	String string = Logo.prs(object);
	return string.substring(0, string.length() - 1);
    }
    
    Object prim_fput(Object object, Object object_11_, LContext lcontext) {
	Object[] objects = Logo.aList(object_11_, lcontext);
	Object[] objects_12_ = new Object[objects.length + 1];
	objects_12_[0] = object;
	for (int i = 0; i < objects.length; i++)
	    objects_12_[i + 1] = objects[i];
	return objects_12_;
    }
    
    Object prim_lput(Object object, Object object_13_, LContext lcontext) {
	return lput(object, Logo.aList(object_13_, lcontext));
    }
    
    Object prim_item(Object object, Object object_14_, LContext lcontext) {
	int i = Logo.anInt(object, lcontext) - 1;
	return (object_14_ instanceof Object[]
		? (Object) ((Object[]) object_14_)[i]
		: Logo.prs(object_14_).substring(i, i + 1));
    }
    
    Object prim_nth(Object object, Object object_15_, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	return (object_15_ instanceof Object[]
		? (Object) ((Object[]) object_15_)[i]
		: Logo.prs(object_15_).substring(i, i + 1));
    }
    
    Object prim_emptyp(Object object, LContext lcontext) {
	return new Boolean(object instanceof Object[]
			   ? ((Object[]) object).length == 0
			   : Logo.prs(object).length() == 0);
    }
    
    Object prim_count(Object object, LContext lcontext) {
	return new Long(object instanceof Object[]
			? (long) ((Object[]) object).length
			: (long) Logo.prs(object).length());
    }
    
    Object prim_wordp(Object object, LContext lcontext) {
	return new Boolean(!(object instanceof Object[]));
    }
    
    Object prim_listp(Object object, LContext lcontext) {
	return new Boolean(object instanceof Object[]);
    }
    
    Object prim_memberp(Object object, Object object_16_, LContext lcontext) {
	return new Boolean(memberp(object, object_16_) != 0);
    }
    
    Object prim_itempos(Object object, Object object_17_, LContext lcontext) {
	int i = memberp(object, object_17_);
	if (i != 0)
	    return new Long((long) i);
	Logo.error((((LContext) lcontext).cfun + " doesn't like "
		    + Logo.prs(object) + " as input"),
		   lcontext);
	return null;
    }
    
    Object prim_setitem(Object object, Object object_18_, Object object_19_,
			LContext lcontext) {
	Logo.aList(object_18_, lcontext)[Logo.anInt(object, lcontext) - 1]
	    = object_19_;
	return null;
    }
    
    Object prim_setnth(Object object, Object object_20_, Object object_21_,
		       LContext lcontext) {
	Logo.aList(object_20_, lcontext)[Logo.anInt(object, lcontext)]
	    = object_21_;
	return null;
    }
    
    Object prim_removeitem(Object object, Object object_22_,
			   LContext lcontext) {
	Object[] objects = Logo.aList(object_22_, lcontext);
	return removeItem(objects, memberp(object, objects));
    }
    
    Object prim_removeitempos(Object object, Object object_23_,
			      LContext lcontext) {
	return removeItem(Logo.aList(object_23_, lcontext),
			  Logo.anInt(object, lcontext));
    }
    
    Object prim_sentence(Object[] objects, LContext lcontext) {
	Object[] objects_24_ = new Object[0];
	for (int i = 0; i < objects.length; i++)
	    objects_24_ = (Object[]) addToList(objects_24_, objects[i]);
	return objects_24_;
    }
    
    Object prim_list(Object[] objects, LContext lcontext) {
	Object[] objects_25_ = new Object[objects.length];
	for (int i = 0; i < objects.length; i++)
	    objects_25_[i] = objects[i];
	return objects_25_;
    }
    
    Object prim_makelist(Object object, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	Object[] objects = new Object[i];
	for (int i_26_ = 0; i_26_ < i; i_26_++)
	    objects[i_26_] = new Object[0];
	return objects;
    }
    
    Object prim_copylist(Object object, LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	return copyList(objects, 0, objects.length);
    }
    
    Object prim_parse(Object object, LContext lcontext) {
	return Logo.parse(Logo.aString(object, lcontext), lcontext);
    }
    
    Object prim_char(Object object, LContext lcontext) {
	char[] cs = new char[1];
	cs[0] = (char) Logo.anInt(object, lcontext);
	return new String(cs);
    }
    
    Object prim_ascii(Object object, LContext lcontext) {
	return new Long((long) Logo.aString(object, lcontext).charAt(0));
    }
    
    Object prim_reverse(Object object, LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	Object[] objects_27_ = new Object[objects.length];
	for (int i = 0; i < objects.length; i++)
	    objects_27_[i] = objects[objects.length - i - 1];
	return objects_27_;
    }
    
    Object prim_substring(Object object, Object object_28_, Object object_29_,
			  LContext lcontext) {
	String string = Logo.prs(object);
	int i = Logo.anInt(object_28_, lcontext);
	int i_30_ = Logo.anInt(object_29_, lcontext);
	if (i == -1)
	    return string.substring(string.length() - i_30_, string.length());
	if (i_30_ == -1)
	    return string.substring(i, string.length());
	return string.substring(i, i + i_30_);
    }
    
    Object prim_ucase(Object object, LContext lcontext) {
	return Logo.prs(object).toUpperCase();
    }
    
    Object prim_insert(Object object, Object object_31_, Object object_32_,
		       LContext lcontext) {
	Object[] objects = Logo.aList(object, lcontext);
	int i = Logo.anInt(object_31_, lcontext) - 1;
	if (0 > i || i > objects.length)
	    Logo.error((((LContext) lcontext).cfun + " doesn't like "
			+ Logo.prs(object_31_) + " as input"),
		       lcontext);
	Object[] objects_33_ = new Object[objects.length + 1];
	int i_34_ = 0;
	for (int i_35_ = 0; i_35_ < objects.length; i_35_++) {
	    if (i_35_ == i)
		objects_33_[i_34_++] = object_32_;
	    objects_33_[i_34_++] = objects[i_35_];
	}
	if (i == objects.length)
	    objects_33_[i_34_] = object_32_;
	return objects_33_;
    }
}
