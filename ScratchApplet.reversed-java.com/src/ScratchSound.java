// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class ScratchSound {

   int rate;
   byte[] samples;
   static final byte[] reversedMeow = new byte[]{(byte)33, (byte)0, (byte)58, (byte)0, (byte)21, (byte)0, (byte)-32, (byte)-1, (byte)-34, (byte)-1, (byte)5, (byte)0, (byte)-34, (byte)-1, (byte)-11, (byte)-1, (byte)-59, (byte)-1, (byte)-94, (byte)-1, (byte)-87, (byte)-1, (byte)-108, (byte)-1, (byte)109, (byte)-1, (byte)120, (byte)-1, (byte)91, (byte)-1, (byte)76, (byte)-1, (byte)66, (byte)-1, (byte)20, (byte)-1, (byte)-19, (byte)-2, (byte)-28, (byte)-2, (byte)-38, (byte)-2, (byte)-126, (byte)-2, (byte)57, (byte)-2, (byte)5, (byte)-2, (byte)41, (byte)-2, (byte)-115, (byte)-2, (byte)16, (byte)-1, (byte)70, (byte)-1, (byte)-47, (byte)-1, (byte)109, (byte)0, (byte)84, (byte)1, (byte)47, (byte)2, (byte)-56, (byte)2, (byte)-100, (byte)2, (byte)-92, (byte)2, (byte)-66, (byte)2, (byte)-13, (byte)2, (byte)33, (byte)3, (byte)109, (byte)3, (byte)-2, (byte)2, (byte)65, (byte)2, (byte)75, (byte)2, (byte)105, (byte)2, (byte)49, (byte)2, (byte)13, (byte)2, (byte)78, (byte)1, (byte)17, (byte)1, (byte)-86, (byte)0, (byte)112, (byte)0, (byte)-82, (byte)-1};
   static final byte[] reversedPop = new byte[]{(byte)-43, (byte)0, (byte)3, (byte)3, (byte)-67, (byte)7, (byte)114, (byte)13, (byte)-17, (byte)21, (byte)83, (byte)29, (byte)60, (byte)35, (byte)-101, (byte)36, (byte)-30, (byte)32, (byte)-85, (byte)22, (byte)115, (byte)6, (byte)85, (byte)-15, (byte)95, (byte)-38, (byte)96, (byte)-60, (byte)-115, (byte)-77, (byte)105, (byte)-87, (byte)-110, (byte)-88, (byte)-27, (byte)-79, (byte)71, (byte)-59, (byte)65, (byte)-31, (byte)-13, (byte)2, (byte)-19, (byte)38, (byte)-47, (byte)71, (byte)74, (byte)97, (byte)125, (byte)111, (byte)35, (byte)112, (byte)10, (byte)97, (byte)-50, (byte)68, (byte)-22, (byte)29, (byte)42, (byte)-13, (byte)15, (byte)-55, (byte)106, (byte)-89, (byte)-3, (byte)-111, (byte)37, (byte)-115, (byte)-18, (byte)-104, (byte)57, (byte)-75, (byte)-24, (byte)-35, (byte)-30, (byte)12, (byte)-99, (byte)58, (byte)68, (byte)96, (byte)112, (byte)118, (byte)-75, (byte)121, (byte)-94, (byte)104, (byte)-40, (byte)69, (byte)103, (byte)23, (byte)74, (byte)-28, (byte)108, (byte)-75, (byte)122, (byte)-110, (byte)-34, (byte)-127, (byte)107, (byte)-122};


   ScratchSound(int var1, byte[] var2) {
      this.rate = var1;
      this.samples = var2;
   }

   ScratchSound(int var1, int[] var2) {
      this.rate = var1;
      this.samples = new byte[2 * var2.length];
      int var3 = 0;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         this.samples[var3++] = (byte)(var2[var4] >> 8 & 255);
         this.samples[var3++] = (byte)(var2[var4] & 255);
      }

   }

   public String toString() {
      double var1 = (double)(100 * this.samples.length / (2 * this.rate)) / 100.0D;
      return "ScratchSound(" + var1 + ", " + this.rate + ")";
   }

   boolean isByteReversed() {
      return this.samples.length < 100?false:this.matches(reversedMeow) || this.matches(reversedPop);
   }

   boolean matches(byte[] var1) {
      for(int var2 = 0; var2 < 100; ++var2) {
         if(this.samples[var2] != var1[var2]) {
            return false;
         }
      }

      return true;
   }

   void reverseBytes() {
      for(int var1 = 0; var1 < this.samples.length - 1; var1 += 2) {
         byte var2 = this.samples[var1];
         this.samples[var1] = this.samples[var1 + 1];
         this.samples[var1 + 1] = var2;
      }

   }

}
