/* ADPCMDecoder - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class ADPCMDecoder
{
    static final int[] stepSizeTable
	= { 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34,
	    37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143,
	    157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449, 494,
	    544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552,
	    1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428,
	    4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487,
	    12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086,
	    29794, 32767 };
    static final int[] indices2bit = { -1, 2 };
    static final int[] indices3bit = { -1, -1, 2, 4 };
    static final int[] indices4bit = { -1, -1, -1, -1, 2, 4, 6, 8 };
    static final int[] indices5bit
	= { -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 4, 6, 8, 10, 13, 16 };
    DataInputStream in;
    int bitsPerSample;
    int currentByte;
    int bitPosition;
    int[] indexTable;
    int predicted;
    int index;
    
    ADPCMDecoder(byte[] is, int i) {
	this(new ByteArrayInputStream(is), i);
    }
    
    ADPCMDecoder(InputStream inputstream, int i) {
	((ADPCMDecoder) this).in = new DataInputStream(inputstream);
	((ADPCMDecoder) this).bitsPerSample = i;
	switch (((ADPCMDecoder) this).bitsPerSample) {
	case 2:
	    ((ADPCMDecoder) this).indexTable = indices2bit;
	    break;
	case 3:
	    ((ADPCMDecoder) this).indexTable = indices3bit;
	    break;
	case 4:
	    ((ADPCMDecoder) this).indexTable = indices4bit;
	    break;
	case 5:
	    ((ADPCMDecoder) this).indexTable = indices5bit;
	    break;
	}
    }
    
    int[] decode(int i) {
	int i_0_ = 1 << ((ADPCMDecoder) this).bitsPerSample - 1;
	int i_1_ = i_0_ - 1;
	int i_2_ = i_0_ >> 1;
	int[] is = new int[i];
	for (int i_3_ = 0; i_3_ < i; i_3_++) {
	    int i_4_ = nextBits();
	    int i_5_ = stepSizeTable[((ADPCMDecoder) this).index];
	    int i_6_ = 0;
	    for (int i_7_ = i_2_; i_7_ > 0; i_7_ >>= 1) {
		if ((i_4_ & i_7_) != 0)
		    i_6_ += i_5_;
		i_5_ >>= 1;
	    }
	    i_6_ += i_5_;
	    ADPCMDecoder adpcmdecoder_8_ = this;
	    ((ADPCMDecoder) adpcmdecoder_8_).predicted
		= (((ADPCMDecoder) adpcmdecoder_8_).predicted
		   + ((i_4_ & i_0_) != 0 ? -i_6_ : i_6_));
	    if (((ADPCMDecoder) this).predicted > 32767)
		((ADPCMDecoder) this).predicted = 32767;
	    if (((ADPCMDecoder) this).predicted < -32768)
		((ADPCMDecoder) this).predicted = -32768;
	    is[i_3_] = ((ADPCMDecoder) this).predicted;
	    ((ADPCMDecoder) this).index
		+= ((ADPCMDecoder) this).indexTable[i_4_ & i_1_];
	    if (((ADPCMDecoder) this).index < 0)
		((ADPCMDecoder) this).index = 0;
	    if (((ADPCMDecoder) this).index > 88)
		((ADPCMDecoder) this).index = 88;
	}
	return is;
    }
    
    int nextBits() {
	int i = 0;
	int i_9_ = ((ADPCMDecoder) this).bitsPerSample;
    while_8_:
	do {
	    do {
		int i_10_ = i_9_ - ((ADPCMDecoder) this).bitPosition;
		i = i + (i_10_ < 0
			 ? ((ADPCMDecoder) this).currentByte >> -i_10_
			 : ((ADPCMDecoder) this).currentByte << i_10_);
		if (i_10_ <= 0)
		    break while_8_;
		i_9_ -= ((ADPCMDecoder) this).bitPosition;
		try {
		    ((ADPCMDecoder) this).currentByte
			= ((ADPCMDecoder) this).in.readUnsignedByte();
		} catch (IOException ioexception) {
		    ((ADPCMDecoder) this).currentByte = -1;
		}
		((ADPCMDecoder) this).bitPosition = 8;
	    } while (((ADPCMDecoder) this).currentByte >= 0);
	    ((ADPCMDecoder) this).bitPosition = 0;
	    return i;
	} while (false);
	((ADPCMDecoder) this).bitPosition -= i_9_;
	((ADPCMDecoder) this).currentByte
	    = (((ADPCMDecoder) this).currentByte
	       & 255 >> 8 - ((ADPCMDecoder) this).bitPosition);
	return i;
    }
}
