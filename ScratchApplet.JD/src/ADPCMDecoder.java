import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class ADPCMDecoder
{
  static final int[] stepSizeTable = { 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767 };

  static final int[] indices2bit = { -1, 2 };
  static final int[] indices3bit = { -1, -1, 2, 4 };
  static final int[] indices4bit = { -1, -1, -1, -1, 2, 4, 6, 8 };
  static final int[] indices5bit = { -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 4, 6, 8, 10, 13, 16 };
  DataInputStream in;
  int bitsPerSample;
  int currentByte;
  int bitPosition;
  int[] indexTable;
  int predicted;
  int index;

  ADPCMDecoder(byte[] paramArrayOfByte, int paramInt)
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramInt);
  }

  ADPCMDecoder(InputStream paramInputStream, int paramInt) {
    this.in = new DataInputStream(paramInputStream);
    this.bitsPerSample = paramInt;
    switch (this.bitsPerSample) {
    case 2:
      this.indexTable = indices2bit;
      break;
    case 3:
      this.indexTable = indices3bit;
      break;
    case 4:
      this.indexTable = indices4bit;
      break;
    case 5:
      this.indexTable = indices5bit;
    }
  }

  int[] decode(int paramInt)
  {
    int i = 1 << this.bitsPerSample - 1;
    int j = i - 1;
    int k = i >> 1;

    int[] arrayOfInt = new int[paramInt];

    for (int i3 = 0; i3 < paramInt; i3++) {
      int m = nextBits();
      int n = stepSizeTable[this.index];
      int i1 = 0;
      for (int i2 = k; i2 > 0; i2 >>= 1) {
        if ((m & i2) != 0) i1 += n;
        n >>= 1;
      }
      i1 += n;

      this.predicted += ((m & i) != 0 ? -i1 : i1);
      if (this.predicted > 32767) this.predicted = 32767;
      if (this.predicted < -32768) this.predicted = -32768;
      arrayOfInt[i3] = this.predicted;

      this.index += this.indexTable[(m & j)];
      if (this.index < 0) this.index = 0;
      if (this.index <= 88) continue; this.index = 88;
    }
    return arrayOfInt;
  }

  int nextBits() {
    int i = 0;
    int j = this.bitsPerSample;
    while (true) {
      int k = j - this.bitPosition;
      i += (k < 0 ? this.currentByte >> -k : this.currentByte << k);
      if (k <= 0) break;
      j -= this.bitPosition;
      try {
        this.currentByte = this.in.readUnsignedByte();
      } catch (IOException localIOException) {
        this.currentByte = -1;
      }
      this.bitPosition = 8;
      if (this.currentByte < 0) {
        this.bitPosition = 0;
        return i;
      }
    }
    this.bitPosition -= j;
    this.currentByte &= 255 >> 8 - this.bitPosition;
    return i;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ADPCMDecoder
 * JD-Core Version:    0.6.0
 */