/* ObjReader - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

class ObjReader
{
    DataInputStream s;
    Object[][] objTable;
    static final int ObjectRefID = 99;
    static final Object[] empty = new Object[0];
    static final Canvas canvas = new Canvas();
    static final byte[] macRomanToISOLatin
	= { -60, -59, -57, -55, -47, -42, -36, -31, -32, -30, -28, -29, -27,
	    -25, -23, -24, -22, -21, -19, -20, -18, -17, -15, -13, -14, -12,
	    -10, -11, -6, -7, -5, -4, -122, -80, -94, -93, -89, -107, -74, -33,
	    -82, -87, -103, -76, -88, -128, -58, -40, -127, -79, -118, -115,
	    -91, -75, -114, -113, -112, -102, -99, -86, -70, -98, -26, -8, -65,
	    -95, -84, -90, -125, -83, -78, -85, -69, -123, -96, -64, -61, -43,
	    -116, -100, -106, -105, -109, -108, -111, -110, -9, -77, -1, -97,
	    -71, -92, -117, -101, -68, -67, -121, -73, -126, -124, -119, -62,
	    -54, -63, -53, -56, -51, -50, -49, -52, -45, -44, -66, -46, -38,
	    -37, -39, -48, -120, -104, -81, -41, -35, -34, -72, -16, -3, -2 };
    static final byte[] squeakColors
	= { -1, -1, -1, 0, 0, 0, -1, -1, -1, -128, -128, -128, -1, 0, 0, 0, -1,
	    0, 0, 0, -1, 0, -1, -1, -1, -1, 0, -1, 0, -1, 32, 32, 32, 64, 64,
	    64, 96, 96, 96, -97, -97, -97, -65, -65, -65, -33, -33, -33, 8, 8,
	    8, 16, 16, 16, 24, 24, 24, 40, 40, 40, 48, 48, 48, 56, 56, 56, 72,
	    72, 72, 80, 80, 80, 88, 88, 88, 104, 104, 104, 112, 112, 112, 120,
	    120, 120, -121, -121, -121, -113, -113, -113, -105, -105, -105,
	    -89, -89, -89, -81, -81, -81, -73, -73, -73, -57, -57, -57, -49,
	    -49, -49, -41, -41, -41, -25, -25, -25, -17, -17, -17, -9, -9, -9,
	    0, 0, 0, 0, 51, 0, 0, 102, 0, 0, -103, 0, 0, -52, 0, 0, -1, 0, 0,
	    0, 51, 0, 51, 51, 0, 102, 51, 0, -103, 51, 0, -52, 51, 0, -1, 51,
	    0, 0, 102, 0, 51, 102, 0, 102, 102, 0, -103, 102, 0, -52, 102, 0,
	    -1, 102, 0, 0, -103, 0, 51, -103, 0, 102, -103, 0, -103, -103, 0,
	    -52, -103, 0, -1, -103, 0, 0, -52, 0, 51, -52, 0, 102, -52, 0,
	    -103, -52, 0, -52, -52, 0, -1, -52, 0, 0, -1, 0, 51, -1, 0, 102,
	    -1, 0, -103, -1, 0, -52, -1, 0, -1, -1, 51, 0, 0, 51, 51, 0, 51,
	    102, 0, 51, -103, 0, 51, -52, 0, 51, -1, 0, 51, 0, 51, 51, 51, 51,
	    51, 102, 51, 51, -103, 51, 51, -52, 51, 51, -1, 51, 51, 0, 102, 51,
	    51, 102, 51, 102, 102, 51, -103, 102, 51, -52, 102, 51, -1, 102,
	    51, 0, -103, 51, 51, -103, 51, 102, -103, 51, -103, -103, 51, -52,
	    -103, 51, -1, -103, 51, 0, -52, 51, 51, -52, 51, 102, -52, 51,
	    -103, -52, 51, -52, -52, 51, -1, -52, 51, 0, -1, 51, 51, -1, 51,
	    102, -1, 51, -103, -1, 51, -52, -1, 51, -1, -1, 102, 0, 0, 102, 51,
	    0, 102, 102, 0, 102, -103, 0, 102, -52, 0, 102, -1, 0, 102, 0, 51,
	    102, 51, 51, 102, 102, 51, 102, -103, 51, 102, -52, 51, 102, -1,
	    51, 102, 0, 102, 102, 51, 102, 102, 102, 102, 102, -103, 102, 102,
	    -52, 102, 102, -1, 102, 102, 0, -103, 102, 51, -103, 102, 102,
	    -103, 102, -103, -103, 102, -52, -103, 102, -1, -103, 102, 0, -52,
	    102, 51, -52, 102, 102, -52, 102, -103, -52, 102, -52, -52, 102,
	    -1, -52, 102, 0, -1, 102, 51, -1, 102, 102, -1, 102, -103, -1, 102,
	    -52, -1, 102, -1, -1, -103, 0, 0, -103, 51, 0, -103, 102, 0, -103,
	    -103, 0, -103, -52, 0, -103, -1, 0, -103, 0, 51, -103, 51, 51,
	    -103, 102, 51, -103, -103, 51, -103, -52, 51, -103, -1, 51, -103,
	    0, 102, -103, 51, 102, -103, 102, 102, -103, -103, 102, -103, -52,
	    102, -103, -1, 102, -103, 0, -103, -103, 51, -103, -103, 102, -103,
	    -103, -103, -103, -103, -52, -103, -103, -1, -103, -103, 0, -52,
	    -103, 51, -52, -103, 102, -52, -103, -103, -52, -103, -52, -52,
	    -103, -1, -52, -103, 0, -1, -103, 51, -1, -103, 102, -1, -103,
	    -103, -1, -103, -52, -1, -103, -1, -1, -52, 0, 0, -52, 51, 0, -52,
	    102, 0, -52, -103, 0, -52, -52, 0, -52, -1, 0, -52, 0, 51, -52, 51,
	    51, -52, 102, 51, -52, -103, 51, -52, -52, 51, -52, -1, 51, -52, 0,
	    102, -52, 51, 102, -52, 102, 102, -52, -103, 102, -52, -52, 102,
	    -52, -1, 102, -52, 0, -103, -52, 51, -103, -52, 102, -103, -52,
	    -103, -103, -52, -52, -103, -52, -1, -103, -52, 0, -52, -52, 51,
	    -52, -52, 102, -52, -52, -103, -52, -52, -52, -52, -52, -1, -52,
	    -52, 0, -1, -52, 51, -1, -52, 102, -1, -52, -103, -1, -52, -52, -1,
	    -52, -1, -1, -1, 0, 0, -1, 51, 0, -1, 102, 0, -1, -103, 0, -1, -52,
	    0, -1, -1, 0, -1, 0, 51, -1, 51, 51, -1, 102, 51, -1, -103, 51, -1,
	    -52, 51, -1, -1, 51, -1, 0, 102, -1, 51, 102, -1, 102, 102, -1,
	    -103, 102, -1, -52, 102, -1, -1, 102, -1, 0, -103, -1, 51, -103,
	    -1, 102, -103, -1, -103, -103, -1, -52, -103, -1, -1, -103, -1, 0,
	    -52, -1, 51, -52, -1, 102, -52, -1, -103, -52, -1, -52, -52, -1,
	    -1, -52, -1, 0, -1, -1, 51, -1, -1, 102, -1, -1, -103, -1, -1, -52,
	    -1, -1, -1, -1 };
    
    ObjReader(InputStream inputstream) {
	((ObjReader) this).s = new DataInputStream(inputstream);
    }
    
    Object[][] readObjects(LContext lcontext) throws IOException {
	readInfo();
	readObjTable(lcontext);
	return ((ObjReader) this).objTable;
    }
    
    Hashtable readInfo() throws IOException {
	byte[] is = new byte[10];
	((ObjReader) this).s.read(is);
	if (!"ScratchV01".equals(new String(is))
	    && !"ScratchV02".equals(new String(is)))
	    throw new IOException();
	int i = ((ObjReader) this).s.readInt();
	readObjTable(null);
	Object[] objects = (Object[]) ((ObjReader) this).objTable[0][0];
	Hashtable hashtable = new Hashtable(objects.length);
	for (int i_0_ = 0; i_0_ < objects.length - 1; i_0_ += 2)
	    hashtable.put(objects[i_0_], objects[i_0_ + 1]);
	return hashtable;
    }
    
    void readObjTable(LContext lcontext) throws IOException {
	byte[] is = new byte[4];
	((ObjReader) this).s.read(is);
	if (!"ObjS".equals(new String(is))
	    || ((ObjReader) this).s.readByte() != 1)
	    throw new IOException();
	((ObjReader) this).s.read(is);
	if (!"Stch".equals(new String(is))
	    || ((ObjReader) this).s.readByte() != 1)
	    throw new IOException();
	int i = ((ObjReader) this).s.readInt();
	((ObjReader) this).objTable = new Object[i][];
	for (int i_1_ = 0; i_1_ < i; i_1_++)
	    ((ObjReader) this).objTable[i_1_] = readObj();
	createSpritesAndWatchers(lcontext);
	buildImagesAndSounds();
	fixSounds();
	resolveReferences();
	uncompressMedia();
    }
    
    Object[] readObj() throws IOException {
	int i = ((ObjReader) this).s.readUnsignedByte();
	Object[] objects;
	if (i < 99) {
	    objects = new Object[2];
	    objects[0] = readFixedFormat(i);
	    objects[1] = new Integer(i);
	} else {
	    int i_2_ = ((ObjReader) this).s.readUnsignedByte();
	    int i_3_ = ((ObjReader) this).s.readUnsignedByte();
	    objects = new Object[3 + i_3_];
	    objects[0] = empty;
	    objects[1] = new Integer(i);
	    objects[2] = new Integer(i_2_);
	    for (int i_4_ = 3; i_4_ < objects.length; i_4_++)
		objects[i_4_] = readField();
	}
	return objects;
    }
    
    Object readField() throws IOException {
	int i = ((ObjReader) this).s.readUnsignedByte();
	if (i == 99) {
	    int i_5_ = ((ObjReader) this).s.readUnsignedByte() << 16;
	    i_5_ += ((ObjReader) this).s.readUnsignedByte() << 8;
	    i_5_ += ((ObjReader) this).s.readUnsignedByte();
	    return new Ref(i_5_);
	}
	return readFixedFormat(i);
    }
    
    Object readFixedFormat(int i) throws IOException {
	switch (i) {
	case 1:
	    return empty;
	case 2:
	    return Boolean.TRUE;
	case 3:
	    return Boolean.FALSE;
	case 4:
	    return new Integer(((ObjReader) this).s.readInt());
	case 5:
	    return new Integer(((ObjReader) this).s.readShort());
	case 6:
	case 7: {
	    double d = 0.0;
	    double d_6_ = 1.0;
	    int i_7_ = ((ObjReader) this).s.readShort();
	    for (int i_8_ = 0; i_8_ < i_7_; i_8_++) {
		int i_9_ = ((ObjReader) this).s.readUnsignedByte();
		d += d_6_ * (double) i_9_;
		d_6_ *= 256.0;
	    }
	    if (i == 7)
		d = -d;
	    return new Double(d);
	}
	case 8:
	    return new Double(((ObjReader) this).s.readDouble());
	case 9:
	case 10: {
	    int i_10_ = ((ObjReader) this).s.readInt();
	    byte[] is;
	    ((ObjReader) this).s.read(is = new byte[i_10_]);
	    for (int i_11_ = 0; i_11_ < i_10_; i_11_++) {
		if (is[i_11_] < 0)
		    is[i_11_] = macRomanToISOLatin[is[i_11_] + 128];
	    }
	    String string;
	    try {
		string = new String(is, "ISO-8859-1");
	    } catch (UnsupportedEncodingException unsupportedencodingexception) {
		return new String(is);
	    }
	    return string;
	}
	case 11: {
	    int i_12_ = ((ObjReader) this).s.readInt();
	    byte[] is;
	    ((ObjReader) this).s.read(is = new byte[i_12_]);
	    return is;
	}
	case 12: {
	    int i_13_ = ((ObjReader) this).s.readInt();
	    byte[] is;
	    ((ObjReader) this).s.read(is = new byte[2 * i_13_]);
	    return is;
	}
	case 13: {
	    int i_14_ = ((ObjReader) this).s.readInt();
	    int[] is = new int[i_14_];
	    for (int i_15_ = 0; i_15_ < is.length; i_15_++)
		is[i_15_] = ((ObjReader) this).s.readInt();
	    return is;
	}
	case 14: {
	    int i_16_ = ((ObjReader) this).s.readInt();
	    byte[] is;
	    ((ObjReader) this).s.read(is = new byte[i_16_]);
	    String string;
	    try {
		string = new String(is, "UTF-8");
	    } catch (UnsupportedEncodingException unsupportedencodingexception) {
		return new String(is);
	    }
	    return string;
	}
	case 20:
	case 21:
	case 22:
	case 23: {
	    int i_17_ = ((ObjReader) this).s.readInt();
	    Object[] objects = new Object[i_17_];
	    for (int i_18_ = 0; i_18_ < objects.length; i_18_++)
		objects[i_18_] = readField();
	    return objects;
	}
	case 24:
	case 25: {
	    int i_19_ = ((ObjReader) this).s.readInt();
	    Object[] objects = new Object[2 * i_19_];
	    for (int i_20_ = 0; i_20_ < objects.length; i_20_++)
		objects[i_20_] = readField();
	    return objects;
	}
	case 30:
	case 31: {
	    int i_21_ = ((ObjReader) this).s.readInt();
	    int i_22_ = 255;
	    if (i == 31)
		i_22_ = ((ObjReader) this).s.readUnsignedByte();
	    return new Color(i_21_ >> 22 & 0xff, i_21_ >> 12 & 0xff,
			     i_21_ >> 2 & 0xff, i_22_);
	}
	case 32: {
	    Object[] objects = new Object[2];
	    objects[0] = readField();
	    objects[1] = readField();
	    return objects;
	}
	case 33: {
	    Object[] objects = new Object[4];
	    objects[0] = readField();
	    objects[1] = readField();
	    objects[2] = readField();
	    objects[3] = readField();
	    return objects;
	}
	case 34:
	case 35: {
	    Object[] objects = new Object[6];
	    for (int i_23_ = 0; i_23_ < 5; i_23_++)
		objects[i_23_] = readField();
	    if (i == 35)
		objects[5] = readField();
	    return objects;
	}
	default:
	    System.out.println("Unknown fixed-format class " + i);
	    throw new IOException();
	}
    }
    
    void createSpritesAndWatchers(LContext lcontext) {
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    Object[] objects = ((ObjReader) this).objTable[i];
	    int i_24_ = ((Number) objects[1]).intValue();
	    if (i_24_ == 124 || i_24_ == 125)
		objects[0] = new Sprite(lcontext);
	    if (i_24_ == 155) {
		objects[0] = new Watcher(lcontext);
		if (((Number) objects[2]).intValue() > 3) {
		    Number number = (Number) objects[23];
		    Number number_25_ = (Number) objects[24];
		    if (floatWithoutFraction(number)
			|| floatWithoutFraction(number_25_))
			objects[24]
			    = new Double(number_25_.doubleValue() + 1.0E-8);
		}
	    }
	}
    }
    
    boolean floatWithoutFraction(Object object) {
	if (!(object instanceof Double))
	    return false;
	double d = ((Double) object).doubleValue();
	return (double) Math.round(d) == d;
    }
    
    void resolveReferences() throws IOException {
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    int i_26_
		= ((Number) ((ObjReader) this).objTable[i][1]).intValue();
	    if (i_26_ >= 20 && i_26_ <= 29) {
		Object[] objects
		    = (Object[]) ((ObjReader) this).objTable[i][0];
		for (int i_27_ = 0; i_27_ < objects.length; i_27_++) {
		    Object object = objects[i_27_];
		    if (object instanceof Ref)
			objects[i_27_] = deRef((Ref) object);
		}
	    }
	    if (i_26_ > 99) {
		for (int i_28_ = 3;
		     i_28_ < ((ObjReader) this).objTable[i].length; i_28_++) {
		    Object object = ((ObjReader) this).objTable[i][i_28_];
		    if (object instanceof Ref)
			((ObjReader) this).objTable[i][i_28_]
			    = deRef((Ref) object);
		}
	    }
	}
    }
    
    Object deRef(Ref ref) {
	Object[] objects = ((ObjReader) this).objTable[((Ref) ref).index];
	return (objects[0] == null || objects[0] == empty ? (Object) objects
		: objects[0]);
    }
    
    void buildImagesAndSounds() throws IOException {
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    int i_29_
		= ((Number) ((ObjReader) this).objTable[i][1]).intValue();
	    if (i_29_ == 34 || i_29_ == 35) {
		Object[] objects
		    = (Object[]) ((ObjReader) this).objTable[i][0];
		int i_30_ = ((Number) objects[0]).intValue();
		int i_31_ = ((Number) objects[1]).intValue();
		int i_32_ = ((Number) objects[2]).intValue();
		int[] is = decodePixels(((ObjReader) this).objTable
					[((Ref) (Ref) objects[4]).index][0]);
		MemoryImageSource memoryimagesource = null;
		((ObjReader) this).objTable[i][0] = empty;
		if (i_32_ <= 8) {
		    IndexColorModel indexcolormodel;
		    if (objects[5] != null) {
			Object[] objects_33_
			    = (Object[]) (((ObjReader) this).objTable
					  [((Ref) (Ref) objects[5]).index][0]);
			indexcolormodel = customColorMap(i_32_, objects_33_);
		    } else
			indexcolormodel = squeakColorMap(i_32_);
		    memoryimagesource
			= new MemoryImageSource(i_30_, i_31_, indexcolormodel,
						rasterToByteRaster(is, i_30_,
								   i_31_,
								   i_32_),
						0, i_30_);
		}
		if (i_32_ == 16)
		    memoryimagesource
			= new MemoryImageSource(i_30_, i_31_,
						raster16to32(is, i_30_, i_31_),
						0, i_30_);
		if (i_32_ == 32)
		    memoryimagesource
			= new MemoryImageSource(i_30_, i_31_,
						rasterAddAlphaTo32(is), 0,
						i_30_);
		if (memoryimagesource != null) {
		    int[] is_34_ = new int[i_30_ * i_31_];
		    PixelGrabber pixelgrabber
			= new PixelGrabber(memoryimagesource, 0, 0, i_30_,
					   i_31_, is_34_, 0, i_30_);
		    try {
			pixelgrabber.grabPixels();
		    } catch (InterruptedException interruptedexception) {
			/* empty */
		    }
		    BufferedImage bufferedimage
			= new BufferedImage(i_30_, i_31_, 2);
		    bufferedimage.getRaster().setDataElements(0, 0, i_30_,
							      i_31_, is_34_);
		    ((ObjReader) this).objTable[i][0] = bufferedimage;
		}
	    }
	    if (i_29_ == 109) {
		Object[] objects
		    = (((ObjReader) this).objTable
		       [(((Ref) (Ref) ((ObjReader) this).objTable[i][6])
			 .index)]);
		((ObjReader) this).objTable[i][0]
		    = new ScratchSound(((Number)
					((ObjReader) this).objTable[i][7])
					   .intValue(),
				       (byte[]) objects[0]);
	    }
	}
    }
    
    int[] decodePixels(Object object) throws IOException {
	if (object instanceof int[])
	    return (int[]) object;
	DataInputStream datainputstream
	    = new DataInputStream(new ByteArrayInputStream((byte[]) object));
	int i = decodeInt(datainputstream);
	int[] is = new int[i];
	int i_35_ = 0;
	while (datainputstream.available() > 0 & i_35_ < i) {
	    int i_36_ = decodeInt(datainputstream);
	    int i_37_ = i_36_ >> 2;
	    int i_38_ = i_36_ & 0x3;
	    switch (i_38_) {
	    case 0:
		i_35_ += i_37_;
		break;
	    case 1: {
		int i_39_ = datainputstream.readUnsignedByte();
		int i_40_ = i_39_ << 24 | i_39_ << 16 | i_39_ << 8 | i_39_;
		for (int i_41_ = 0; i_41_ < i_37_; i_41_++)
		    is[i_35_++] = i_40_;
		break;
	    }
	    case 2: {
		int i_42_ = datainputstream.readInt();
		for (int i_43_ = 0; i_43_ < i_37_; i_43_++)
		    is[i_35_++] = i_42_;
		break;
	    }
	    case 3:
		for (int i_44_ = 0; i_44_ < i_37_; i_44_++) {
		    int i_45_ = datainputstream.readUnsignedByte() << 24;
		    i_45_ |= datainputstream.readUnsignedByte() << 16;
		    i_45_ |= datainputstream.readUnsignedByte() << 8;
		    i_45_ |= datainputstream.readUnsignedByte();
		    is[i_35_++] = i_45_;
		}
		break;
	    }
	}
	return is;
    }
    
    int decodeInt(DataInputStream datainputstream) throws IOException {
	int i = datainputstream.readUnsignedByte();
	if (i <= 223)
	    return i;
	if (i <= 254)
	    return (i - 224) * 256 + datainputstream.readUnsignedByte();
	return datainputstream.readInt();
    }
    
    int[] rasterAddAlphaTo32(int[] is) {
	for (int i = 0; i < is.length; i++) {
	    int i_46_ = is[i];
	    if (i_46_ != 0)
		is[i] = ~0xffffff | i_46_;
	}
	return is;
    }
    
    int[] raster16to32(int[] is, int i, int i_47_) {
	int[] is_48_ = new int[i * i_47_];
	int i_49_ = (i + 1) / 2;
	for (int i_50_ = 0; i_50_ < i_47_; i_50_++) {
	    int i_51_ = 16;
	    for (int i_52_ = 0; i_52_ < i; i_52_++) {
		int i_53_ = is[i_50_ * i_49_ + i_52_ / 2] >> i_51_ & 0xffff;
		int i_54_ = (i_53_ >> 10 & 0x1f) << 3;
		int i_55_ = (i_53_ >> 5 & 0x1f) << 3;
		int i_56_ = (i_53_ & 0x1f) << 3;
		int i_57_ = (i_54_ + i_55_ + i_56_ == 0 ? 0
			     : ~0xffffff | i_54_ << 16 | i_55_ << 8 | i_56_);
		is_48_[i_50_ * i + i_52_] = i_57_;
		i_51_ = i_51_ == 16 ? 0 : 16;
	    }
	}
	return is_48_;
    }
    
    byte[] rasterToByteRaster(int[] is, int i, int i_58_, int i_59_) {
	byte[] is_60_ = new byte[i * i_58_];
	int i_61_ = is.length / i_58_;
	int i_62_ = (1 << i_59_) - 1;
	int i_63_ = 32 / i_59_;
	for (int i_64_ = 0; i_64_ < i_58_; i_64_++) {
	    for (int i_65_ = 0; i_65_ < i; i_65_++) {
		int i_66_ = is[i_64_ * i_61_ + i_65_ / i_63_];
		int i_67_ = i_59_ * (i_63_ - i_65_ % i_63_ - 1);
		is_60_[i_64_ * i + i_65_] = (byte) (i_66_ >> i_67_ & i_62_);
	    }
	}
	return is_60_;
    }
    
    IndexColorModel squeakColorMap(int i) {
	int i_68_ = i == 1 ? -1 : 0;
	return new IndexColorModel(i, 256, squeakColors, 0, false, i_68_);
    }
    
    IndexColorModel customColorMap(int i, Object[] objects) {
	byte[] is = new byte[4 * objects.length];
	int i_69_ = 0;
	for (int i_70_ = 0; i_70_ < objects.length; i_70_++) {
	    Color color = (Color) (((ObjReader) this).objTable
				   [((Ref) (Ref) objects[i_70_]).index][0]);
	    is[i_69_++] = (byte) color.getRed();
	    is[i_69_++] = (byte) color.getGreen();
	    is[i_69_++] = (byte) color.getBlue();
	    is[i_69_++] = (byte) color.getAlpha();
	}
	return new IndexColorModel(i, objects.length, is, 0, true, 0);
    }
    
    void fixSounds() {
	boolean bool = false;
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    int i_71_
		= ((Number) ((ObjReader) this).objTable[i][1]).intValue();
	    if (i_71_ == 109
		&& ((ScratchSound) ((ObjReader) this).objTable[i][0])
		       .isByteReversed())
		bool = true;
	}
	if (bool) {
	    for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
		int i_72_
		    = ((Number) ((ObjReader) this).objTable[i][1]).intValue();
		if (i_72_ == 109)
		    ((ScratchSound) ((ObjReader) this).objTable[i][0])
			.reverseBytes();
	    }
	}
    }
    
    void uncompressMedia() {
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    Object[] objects = ((ObjReader) this).objTable[i];
	    int i_73_ = ((Number) objects[1]).intValue();
	    int i_74_ = -1;
	    if (i_73_ >= 100)
		i_74_ = ((Number) objects[2]).intValue();
	    if (i_73_ == 162 && i_74_ >= 4) {
		if (objects[7] instanceof byte[]) {
		    BufferedImage bufferedimage
			= jpegDecode((byte[]) objects[7]);
		    if (bufferedimage != null) {
			if (objects[4] instanceof Image)
			    ((Image) objects[4]).flush();
			objects[4] = bufferedimage;
			objects[7] = empty;
		    }
		}
		if (objects[8] instanceof BufferedImage) {
		    objects[4] = objects[8];
		    objects[8] = empty;
		}
	    }
	    if (i_73_ == 164 && i_74_ >= 2 && objects[9] instanceof byte[]) {
		int i_75_ = ((Number) objects[7]).intValue();
		int i_76_ = ((Number) objects[8]).intValue();
		byte[] is = (byte[]) objects[9];
		int i_77_ = (is.length * 8 + (i_76_ - 1)) / i_76_;
		int[] is_78_ = new ADPCMDecoder(is, i_76_).decode(i_77_);
		objects[4] = new ScratchSound(i_75_, is_78_);
		objects[7] = objects[8] = objects[9] = empty;
	    }
	}
    }
    
    void canonicalizeMedia() {
	Vector vector = new Vector(100);
	Vector vector_79_ = new Vector(100);
	for (int i = 0; i < ((ObjReader) this).objTable.length; i++) {
	    Object[] objects = ((ObjReader) this).objTable[i];
	    int i_80_ = ((Number) objects[1]).intValue();
	    if (i_80_ == 162) {
		BufferedImage bufferedimage = (BufferedImage) objects[4];
	    }
	    if (i_80_ == 164) {
		ScratchSound scratchsound = (ScratchSound) objects[4];
	    }
	}
    }
    
    BufferedImage jpegDecode(byte[] is) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image = toolkit.createImage(is);
	MediaTracker mediatracker = new MediaTracker(canvas);
	mediatracker.addImage(image, 0);
	try {
	    mediatracker.waitForID(0);
	} catch (InterruptedException interruptedexception) {
	    /* empty */
	}
	if (image == null)
	    return null;
	int i = image.getWidth(null);
	int i_81_ = image.getHeight(null);
	BufferedImage bufferedimage = new BufferedImage(i, i_81_, 2);
	Graphics graphics = bufferedimage.getGraphics();
	graphics.drawImage(image, 0, 0, i, i_81_, null);
	graphics.dispose();
	image.flush();
	return bufferedimage;
    }
    
    void printit(Object object) {
	if (object instanceof Object[] && ((Object[]) object).length == 0)
	    System.out.print(" []");
	else if (object instanceof BufferedImage) {
	    BufferedImage bufferedimage = (BufferedImage) object;
	    System.out.print(" BufferedImage_" + object.hashCode() + "("
			     + bufferedimage.getWidth(null) + "x"
			     + bufferedimage.getHeight(null) + ")");
	} else
	    System.out.print(" " + object);
    }
}
