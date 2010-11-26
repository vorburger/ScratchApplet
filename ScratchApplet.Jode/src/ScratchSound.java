/* ScratchSound - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class ScratchSound
{
    int rate;
    byte[] samples;
    static final byte[] reversedMeow
	= { 33, 0, 58, 0, 21, 0, -32, -1, -34, -1, 5, 0, -34, -1, -11, -1, -59,
	    -1, -94, -1, -87, -1, -108, -1, 109, -1, 120, -1, 91, -1, 76, -1,
	    66, -1, 20, -1, -19, -2, -28, -2, -38, -2, -126, -2, 57, -2, 5, -2,
	    41, -2, -115, -2, 16, -1, 70, -1, -47, -1, 109, 0, 84, 1, 47, 2,
	    -56, 2, -100, 2, -92, 2, -66, 2, -13, 2, 33, 3, 109, 3, -2, 2, 65,
	    2, 75, 2, 105, 2, 49, 2, 13, 2, 78, 1, 17, 1, -86, 0, 112, 0, -82,
	    -1 };
    static final byte[] reversedPop
	= { -43, 0, 3, 3, -67, 7, 114, 13, -17, 21, 83, 29, 60, 35, -101, 36,
	    -30, 32, -85, 22, 115, 6, 85, -15, 95, -38, 96, -60, -115, -77,
	    105, -87, -110, -88, -27, -79, 71, -59, 65, -31, -13, 2, -19, 38,
	    -47, 71, 74, 97, 125, 111, 35, 112, 10, 97, -50, 68, -22, 29, 42,
	    -13, 15, -55, 106, -89, -3, -111, 37, -115, -18, -104, 57, -75,
	    -24, -35, -30, 12, -99, 58, 68, 96, 112, 118, -75, 121, -94, 104,
	    -40, 69, 103, 23, 74, -28, 108, -75, 122, -110, -34, -127, 107,
	    -122 };
    
    ScratchSound(int i, byte[] is) {
	((ScratchSound) this).rate = i;
	((ScratchSound) this).samples = is;
    }
    
    ScratchSound(int i, int[] is) {
	((ScratchSound) this).rate = i;
	((ScratchSound) this).samples = new byte[2 * is.length];
	int i_0_ = 0;
	for (int i_1_ = 0; i_1_ < is.length; i_1_++) {
	    ((ScratchSound) this).samples[i_0_++]
		= (byte) (is[i_1_] >> 8 & 0xff);
	    ((ScratchSound) this).samples[i_0_++] = (byte) (is[i_1_] & 0xff);
	}
    }
    
    public String toString() {
	double d = ((double) (100 * ((ScratchSound) this).samples.length
			      / (2 * ((ScratchSound) this).rate))
		    / 100.0);
	return "ScratchSound(" + d + ", " + ((ScratchSound) this).rate + ")";
    }
    
    boolean isByteReversed() {
	if (((ScratchSound) this).samples.length < 100)
	    return false;
	return matches(reversedMeow) || matches(reversedPop);
    }
    
    boolean matches(byte[] is) {
	for (int i = 0; i < 100; i++) {
	    if (((ScratchSound) this).samples[i] != is[i])
		return false;
	}
	return true;
    }
    
    void reverseBytes() {
	for (int i = 0; i < ((ScratchSound) this).samples.length - 1; i += 2) {
	    byte i_2_ = ((ScratchSound) this).samples[i];
	    ((ScratchSound) this).samples[i]
		= ((ScratchSound) this).samples[i + 1];
	    ((ScratchSound) this).samples[i + 1] = i_2_;
	}
    }
}
