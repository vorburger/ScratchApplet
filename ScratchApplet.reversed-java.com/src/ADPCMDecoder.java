// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:27
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class ADPCMDecoder {

   static final int[] stepSizeTable = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767};
   static final int[] indices2bit = new int[]{-1, 2};
   static final int[] indices3bit = new int[]{-1, -1, 2, 4};
   static final int[] indices4bit = new int[]{-1, -1, -1, -1, 2, 4, 6, 8};
   static final int[] indices5bit = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 4, 6, 8, 10, 13, 16};
   DataInputStream in;
   int bitsPerSample;
   int currentByte;
   int bitPosition;
   int[] indexTable;
   int predicted;
   int index;


   ADPCMDecoder(byte[] var1, int var2) {
      this(new ByteArrayInputStream(var1), var2);
   }

   ADPCMDecoder(InputStream var1, int var2) {
      this.in = new DataInputStream(var1);
      this.bitsPerSample = var2;
      switch(this.bitsPerSample) {
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

   int[] decode(int var1) {
      int var2 = 1 << this.bitsPerSample - 1;
      int var3 = var2 - 1;
      int var4 = var2 >> 1;
      int[] var9 = new int[var1];

      for(int var10 = 0; var10 < var1; ++var10) {
         int var5 = this.nextBits();
         int var6 = stepSizeTable[this.index];
         int var7 = 0;

         for(int var8 = var4; var8 > 0; var8 >>= 1) {
            if((var5 & var8) != 0) {
               var7 += var6;
            }

            var6 >>= 1;
         }

         var7 += var6;
         this.predicted += (var5 & var2) != 0?-var7:var7;
         if(this.predicted > 32767) {
            this.predicted = 32767;
         }

         if(this.predicted < -32768) {
            this.predicted = -32768;
         }

         var9[var10] = this.predicted;
         this.index += this.indexTable[var5 & var3];
         if(this.index < 0) {
            this.index = 0;
         }

         if(this.index > 88) {
            this.index = 88;
         }
      }

      return var9;
   }

   int nextBits() {
      int var1 = 0;
      int var2 = this.bitsPerSample;

      do {
         int var3 = var2 - this.bitPosition;
         var1 += var3 < 0?this.currentByte >> -var3:this.currentByte << var3;
         if(var3 <= 0) {
            this.bitPosition -= var2;
            this.currentByte &= 255 >> 8 - this.bitPosition;
            return var1;
         }

         var2 -= this.bitPosition;

         try {
            this.currentByte = this.in.readUnsignedByte();
         } catch (IOException var5) {
            this.currentByte = -1;
         }

         this.bitPosition = 8;
      } while(this.currentByte >= 0);

      this.bitPosition = 0;
      return var1;
   }

}
