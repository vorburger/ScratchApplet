// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:33
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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

class ObjReader {

   DataInputStream s;
   Object[][] objTable;
   static final int ObjectRefID = 99;
   static final Object[] empty = new Object[0];
   static final Canvas canvas = new Canvas();
   static final byte[] macRomanToISOLatin = new byte[]{(byte)-60, (byte)-59, (byte)-57, (byte)-55, (byte)-47, (byte)-42, (byte)-36, (byte)-31, (byte)-32, (byte)-30, (byte)-28, (byte)-29, (byte)-27, (byte)-25, (byte)-23, (byte)-24, (byte)-22, (byte)-21, (byte)-19, (byte)-20, (byte)-18, (byte)-17, (byte)-15, (byte)-13, (byte)-14, (byte)-12, (byte)-10, (byte)-11, (byte)-6, (byte)-7, (byte)-5, (byte)-4, (byte)-122, (byte)-80, (byte)-94, (byte)-93, (byte)-89, (byte)-107, (byte)-74, (byte)-33, (byte)-82, (byte)-87, (byte)-103, (byte)-76, (byte)-88, (byte)-128, (byte)-58, (byte)-40, (byte)-127, (byte)-79, (byte)-118, (byte)-115, (byte)-91, (byte)-75, (byte)-114, (byte)-113, (byte)-112, (byte)-102, (byte)-99, (byte)-86, (byte)-70, (byte)-98, (byte)-26, (byte)-8, (byte)-65, (byte)-95, (byte)-84, (byte)-90, (byte)-125, (byte)-83, (byte)-78, (byte)-85, (byte)-69, (byte)-123, (byte)-96, (byte)-64, (byte)-61, (byte)-43, (byte)-116, (byte)-100, (byte)-106, (byte)-105, (byte)-109, (byte)-108, (byte)-111, (byte)-110, (byte)-9, (byte)-77, (byte)-1, (byte)-97, (byte)-71, (byte)-92, (byte)-117, (byte)-101, (byte)-68, (byte)-67, (byte)-121, (byte)-73, (byte)-126, (byte)-124, (byte)-119, (byte)-62, (byte)-54, (byte)-63, (byte)-53, (byte)-56, (byte)-51, (byte)-50, (byte)-49, (byte)-52, (byte)-45, (byte)-44, (byte)-66, (byte)-46, (byte)-38, (byte)-37, (byte)-39, (byte)-48, (byte)-120, (byte)-104, (byte)-81, (byte)-41, (byte)-35, (byte)-34, (byte)-72, (byte)-16, (byte)-3, (byte)-2};
   static final byte[] squeakColors = new byte[]{(byte)-1, (byte)-1, (byte)-1, (byte)0, (byte)0, (byte)0, (byte)-1, (byte)-1, (byte)-1, (byte)-128, (byte)-128, (byte)-128, (byte)-1, (byte)0, (byte)0, (byte)0, (byte)-1, (byte)0, (byte)0, (byte)0, (byte)-1, (byte)0, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)0, (byte)-1, (byte)0, (byte)-1, (byte)32, (byte)32, (byte)32, (byte)64, (byte)64, (byte)64, (byte)96, (byte)96, (byte)96, (byte)-97, (byte)-97, (byte)-97, (byte)-65, (byte)-65, (byte)-65, (byte)-33, (byte)-33, (byte)-33, (byte)8, (byte)8, (byte)8, (byte)16, (byte)16, (byte)16, (byte)24, (byte)24, (byte)24, (byte)40, (byte)40, (byte)40, (byte)48, (byte)48, (byte)48, (byte)56, (byte)56, (byte)56, (byte)72, (byte)72, (byte)72, (byte)80, (byte)80, (byte)80, (byte)88, (byte)88, (byte)88, (byte)104, (byte)104, (byte)104, (byte)112, (byte)112, (byte)112, (byte)120, (byte)120, (byte)120, (byte)-121, (byte)-121, (byte)-121, (byte)-113, (byte)-113, (byte)-113, (byte)-105, (byte)-105, (byte)-105, (byte)-89, (byte)-89, (byte)-89, (byte)-81, (byte)-81, (byte)-81, (byte)-73, (byte)-73, (byte)-73, (byte)-57, (byte)-57, (byte)-57, (byte)-49, (byte)-49, (byte)-49, (byte)-41, (byte)-41, (byte)-41, (byte)-25, (byte)-25, (byte)-25, (byte)-17, (byte)-17, (byte)-17, (byte)-9, (byte)-9, (byte)-9, (byte)0, (byte)0, (byte)0, (byte)0, (byte)51, (byte)0, (byte)0, (byte)102, (byte)0, (byte)0, (byte)-103, (byte)0, (byte)0, (byte)-52, (byte)0, (byte)0, (byte)-1, (byte)0, (byte)0, (byte)0, (byte)51, (byte)0, (byte)51, (byte)51, (byte)0, (byte)102, (byte)51, (byte)0, (byte)-103, (byte)51, (byte)0, (byte)-52, (byte)51, (byte)0, (byte)-1, (byte)51, (byte)0, (byte)0, (byte)102, (byte)0, (byte)51, (byte)102, (byte)0, (byte)102, (byte)102, (byte)0, (byte)-103, (byte)102, (byte)0, (byte)-52, (byte)102, (byte)0, (byte)-1, (byte)102, (byte)0, (byte)0, (byte)-103, (byte)0, (byte)51, (byte)-103, (byte)0, (byte)102, (byte)-103, (byte)0, (byte)-103, (byte)-103, (byte)0, (byte)-52, (byte)-103, (byte)0, (byte)-1, (byte)-103, (byte)0, (byte)0, (byte)-52, (byte)0, (byte)51, (byte)-52, (byte)0, (byte)102, (byte)-52, (byte)0, (byte)-103, (byte)-52, (byte)0, (byte)-52, (byte)-52, (byte)0, (byte)-1, (byte)-52, (byte)0, (byte)0, (byte)-1, (byte)0, (byte)51, (byte)-1, (byte)0, (byte)102, (byte)-1, (byte)0, (byte)-103, (byte)-1, (byte)0, (byte)-52, (byte)-1, (byte)0, (byte)-1, (byte)-1, (byte)51, (byte)0, (byte)0, (byte)51, (byte)51, (byte)0, (byte)51, (byte)102, (byte)0, (byte)51, (byte)-103, (byte)0, (byte)51, (byte)-52, (byte)0, (byte)51, (byte)-1, (byte)0, (byte)51, (byte)0, (byte)51, (byte)51, (byte)51, (byte)51, (byte)51, (byte)102, (byte)51, (byte)51, (byte)-103, (byte)51, (byte)51, (byte)-52, (byte)51, (byte)51, (byte)-1, (byte)51, (byte)51, (byte)0, (byte)102, (byte)51, (byte)51, (byte)102, (byte)51, (byte)102, (byte)102, (byte)51, (byte)-103, (byte)102, (byte)51, (byte)-52, (byte)102, (byte)51, (byte)-1, (byte)102, (byte)51, (byte)0, (byte)-103, (byte)51, (byte)51, (byte)-103, (byte)51, (byte)102, (byte)-103, (byte)51, (byte)-103, (byte)-103, (byte)51, (byte)-52, (byte)-103, (byte)51, (byte)-1, (byte)-103, (byte)51, (byte)0, (byte)-52, (byte)51, (byte)51, (byte)-52, (byte)51, (byte)102, (byte)-52, (byte)51, (byte)-103, (byte)-52, (byte)51, (byte)-52, (byte)-52, (byte)51, (byte)-1, (byte)-52, (byte)51, (byte)0, (byte)-1, (byte)51, (byte)51, (byte)-1, (byte)51, (byte)102, (byte)-1, (byte)51, (byte)-103, (byte)-1, (byte)51, (byte)-52, (byte)-1, (byte)51, (byte)-1, (byte)-1, (byte)102, (byte)0, (byte)0, (byte)102, (byte)51, (byte)0, (byte)102, (byte)102, (byte)0, (byte)102, (byte)-103, (byte)0, (byte)102, (byte)-52, (byte)0, (byte)102, (byte)-1, (byte)0, (byte)102, (byte)0, (byte)51, (byte)102, (byte)51, (byte)51, (byte)102, (byte)102, (byte)51, (byte)102, (byte)-103, (byte)51, (byte)102, (byte)-52, (byte)51, (byte)102, (byte)-1, (byte)51, (byte)102, (byte)0, (byte)102, (byte)102, (byte)51, (byte)102, (byte)102, (byte)102, (byte)102, (byte)102, (byte)-103, (byte)102, (byte)102, (byte)-52, (byte)102, (byte)102, (byte)-1, (byte)102, (byte)102, (byte)0, (byte)-103, (byte)102, (byte)51, (byte)-103, (byte)102, (byte)102, (byte)-103, (byte)102, (byte)-103, (byte)-103, (byte)102, (byte)-52, (byte)-103, (byte)102, (byte)-1, (byte)-103, (byte)102, (byte)0, (byte)-52, (byte)102, (byte)51, (byte)-52, (byte)102, (byte)102, (byte)-52, (byte)102, (byte)-103, (byte)-52, (byte)102, (byte)-52, (byte)-52, (byte)102, (byte)-1, (byte)-52, (byte)102, (byte)0, (byte)-1, (byte)102, (byte)51, (byte)-1, (byte)102, (byte)102, (byte)-1, (byte)102, (byte)-103, (byte)-1, (byte)102, (byte)-52, (byte)-1, (byte)102, (byte)-1, (byte)-1, (byte)-103, (byte)0, (byte)0, (byte)-103, (byte)51, (byte)0, (byte)-103, (byte)102, (byte)0, (byte)-103, (byte)-103, (byte)0, (byte)-103, (byte)-52, (byte)0, (byte)-103, (byte)-1, (byte)0, (byte)-103, (byte)0, (byte)51, (byte)-103, (byte)51, (byte)51, (byte)-103, (byte)102, (byte)51, (byte)-103, (byte)-103, (byte)51, (byte)-103, (byte)-52, (byte)51, (byte)-103, (byte)-1, (byte)51, (byte)-103, (byte)0, (byte)102, (byte)-103, (byte)51, (byte)102, (byte)-103, (byte)102, (byte)102, (byte)-103, (byte)-103, (byte)102, (byte)-103, (byte)-52, (byte)102, (byte)-103, (byte)-1, (byte)102, (byte)-103, (byte)0, (byte)-103, (byte)-103, (byte)51, (byte)-103, (byte)-103, (byte)102, (byte)-103, (byte)-103, (byte)-103, (byte)-103, (byte)-103, (byte)-52, (byte)-103, (byte)-103, (byte)-1, (byte)-103, (byte)-103, (byte)0, (byte)-52, (byte)-103, (byte)51, (byte)-52, (byte)-103, (byte)102, (byte)-52, (byte)-103, (byte)-103, (byte)-52, (byte)-103, (byte)-52, (byte)-52, (byte)-103, (byte)-1, (byte)-52, (byte)-103, (byte)0, (byte)-1, (byte)-103, (byte)51, (byte)-1, (byte)-103, (byte)102, (byte)-1, (byte)-103, (byte)-103, (byte)-1, (byte)-103, (byte)-52, (byte)-1, (byte)-103, (byte)-1, (byte)-1, (byte)-52, (byte)0, (byte)0, (byte)-52, (byte)51, (byte)0, (byte)-52, (byte)102, (byte)0, (byte)-52, (byte)-103, (byte)0, (byte)-52, (byte)-52, (byte)0, (byte)-52, (byte)-1, (byte)0, (byte)-52, (byte)0, (byte)51, (byte)-52, (byte)51, (byte)51, (byte)-52, (byte)102, (byte)51, (byte)-52, (byte)-103, (byte)51, (byte)-52, (byte)-52, (byte)51, (byte)-52, (byte)-1, (byte)51, (byte)-52, (byte)0, (byte)102, (byte)-52, (byte)51, (byte)102, (byte)-52, (byte)102, (byte)102, (byte)-52, (byte)-103, (byte)102, (byte)-52, (byte)-52, (byte)102, (byte)-52, (byte)-1, (byte)102, (byte)-52, (byte)0, (byte)-103, (byte)-52, (byte)51, (byte)-103, (byte)-52, (byte)102, (byte)-103, (byte)-52, (byte)-103, (byte)-103, (byte)-52, (byte)-52, (byte)-103, (byte)-52, (byte)-1, (byte)-103, (byte)-52, (byte)0, (byte)-52, (byte)-52, (byte)51, (byte)-52, (byte)-52, (byte)102, (byte)-52, (byte)-52, (byte)-103, (byte)-52, (byte)-52, (byte)-52, (byte)-52, (byte)-52, (byte)-1, (byte)-52, (byte)-52, (byte)0, (byte)-1, (byte)-52, (byte)51, (byte)-1, (byte)-52, (byte)102, (byte)-1, (byte)-52, (byte)-103, (byte)-1, (byte)-52, (byte)-52, (byte)-1, (byte)-52, (byte)-1, (byte)-1, (byte)-1, (byte)0, (byte)0, (byte)-1, (byte)51, (byte)0, (byte)-1, (byte)102, (byte)0, (byte)-1, (byte)-103, (byte)0, (byte)-1, (byte)-52, (byte)0, (byte)-1, (byte)-1, (byte)0, (byte)-1, (byte)0, (byte)51, (byte)-1, (byte)51, (byte)51, (byte)-1, (byte)102, (byte)51, (byte)-1, (byte)-103, (byte)51, (byte)-1, (byte)-52, (byte)51, (byte)-1, (byte)-1, (byte)51, (byte)-1, (byte)0, (byte)102, (byte)-1, (byte)51, (byte)102, (byte)-1, (byte)102, (byte)102, (byte)-1, (byte)-103, (byte)102, (byte)-1, (byte)-52, (byte)102, (byte)-1, (byte)-1, (byte)102, (byte)-1, (byte)0, (byte)-103, (byte)-1, (byte)51, (byte)-103, (byte)-1, (byte)102, (byte)-103, (byte)-1, (byte)-103, (byte)-103, (byte)-1, (byte)-52, (byte)-103, (byte)-1, (byte)-1, (byte)-103, (byte)-1, (byte)0, (byte)-52, (byte)-1, (byte)51, (byte)-52, (byte)-1, (byte)102, (byte)-52, (byte)-1, (byte)-103, (byte)-52, (byte)-1, (byte)-52, (byte)-52, (byte)-1, (byte)-1, (byte)-52, (byte)-1, (byte)0, (byte)-1, (byte)-1, (byte)51, (byte)-1, (byte)-1, (byte)102, (byte)-1, (byte)-1, (byte)-103, (byte)-1, (byte)-1, (byte)-52, (byte)-1, (byte)-1, (byte)-1, (byte)-1};


   ObjReader(InputStream var1) {
      this.s = new DataInputStream(var1);
   }

   Object[][] readObjects(LContext var1) throws IOException {
      this.readInfo();
      this.readObjTable(var1);
      return this.objTable;
   }

   Hashtable readInfo() throws IOException {
      byte[] var1 = new byte[10];
      this.s.read(var1);
      if(!"ScratchV01".equals(new String(var1)) && !"ScratchV02".equals(new String(var1))) {
         throw new IOException();
      } else {
         int var2 = this.s.readInt();
         this.readObjTable((LContext)null);
         Object[] var3 = (Object[])this.objTable[0][0];
         Hashtable var4 = new Hashtable(var3.length);

         for(int var5 = 0; var5 < var3.length - 1; var5 += 2) {
            var4.put(var3[var5], var3[var5 + 1]);
         }

         return var4;
      }
   }

   void readObjTable(LContext var1) throws IOException {
      byte[] var2 = new byte[4];
      this.s.read(var2);
      if("ObjS".equals(new String(var2)) && this.s.readByte() == 1) {
         this.s.read(var2);
         if("Stch".equals(new String(var2)) && this.s.readByte() == 1) {
            int var3 = this.s.readInt();
            this.objTable = new Object[var3][];

            for(int var4 = 0; var4 < var3; ++var4) {
               this.objTable[var4] = this.readObj();
            }

            this.createSpritesAndWatchers(var1);
            this.buildImagesAndSounds();
            this.fixSounds();
            this.resolveReferences();
            this.uncompressMedia();
         } else {
            throw new IOException();
         }
      } else {
         throw new IOException();
      }
   }

   Object[] readObj() throws IOException {
      int var2 = this.s.readUnsignedByte();
      Object[] var1;
      if(var2 < 99) {
         var1 = new Object[]{this.readFixedFormat(var2), new Integer(var2)};
      } else {
         int var3 = this.s.readUnsignedByte();
         int var4 = this.s.readUnsignedByte();
         var1 = new Object[3 + var4];
         var1[0] = empty;
         var1[1] = new Integer(var2);
         var1[2] = new Integer(var3);

         for(int var5 = 3; var5 < var1.length; ++var5) {
            var1[var5] = this.readField();
         }
      }

      return var1;
   }

   Object readField() throws IOException {
      int var1 = this.s.readUnsignedByte();
      if(var1 == 99) {
         int var2 = this.s.readUnsignedByte() << 16;
         var2 += this.s.readUnsignedByte() << 8;
         var2 += this.s.readUnsignedByte();
         return new Ref(var2);
      } else {
         return this.readFixedFormat(var1);
      }
   }

   Object readFixedFormat(int var1) throws IOException {
      int var2;
      byte[] var5;
      Object[] var6;
      int var12;
      int var18;
      switch(var1) {
      case 1:
         return empty;
      case 2:
         return Boolean.TRUE;
      case 3:
         return Boolean.FALSE;
      case 4:
         return new Integer(this.s.readInt());
      case 5:
         return new Integer(this.s.readShort());
      case 6:
      case 7:
         double var7 = 0.0D;
         double var9 = 1.0D;
         short var19 = this.s.readShort();

         for(var18 = 0; var18 < var19; ++var18) {
            var12 = this.s.readUnsignedByte();
            var7 += var9 * (double)var12;
            var9 *= 256.0D;
         }

         if(var1 == 7) {
            var7 = -var7;
         }

         return new Double(var7);
      case 8:
         return new Double(this.s.readDouble());
      case 9:
      case 10:
         var2 = this.s.readInt();
         this.s.read(var5 = new byte[var2]);

         for(var18 = 0; var18 < var2; ++var18) {
            if(var5[var18] < 0) {
               var5[var18] = macRomanToISOLatin[var5[var18] + 128];
            }
         }

         try {
            return new String(var5, "ISO-8859-1");
         } catch (UnsupportedEncodingException var17) {
            return new String(var5);
         }
      case 11:
         var2 = this.s.readInt();
         this.s.read(var5 = new byte[var2]);
         return var5;
      case 12:
         var2 = this.s.readInt();
         this.s.read(var5 = new byte[2 * var2]);
         return var5;
      case 13:
         var2 = this.s.readInt();
         int[] var11 = new int[var2];

         for(var12 = 0; var12 < var11.length; ++var12) {
            var11[var12] = this.s.readInt();
         }

         return var11;
      case 14:
         var2 = this.s.readInt();
         this.s.read(var5 = new byte[var2]);

         try {
            return new String(var5, "UTF-8");
         } catch (UnsupportedEncodingException var16) {
            return new String(var5);
         }
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 26:
      case 27:
      case 28:
      case 29:
      default:
         System.out.println("Unknown fixed-format class " + var1);
         throw new IOException();
      case 20:
      case 21:
      case 22:
      case 23:
         var2 = this.s.readInt();
         var6 = new Object[var2];

         for(var12 = 0; var12 < var6.length; ++var12) {
            var6[var12] = this.readField();
         }

         return var6;
      case 24:
      case 25:
         var2 = this.s.readInt();
         var6 = new Object[2 * var2];

         for(var12 = 0; var12 < var6.length; ++var12) {
            var6[var12] = this.readField();
         }

         return var6;
      case 30:
      case 31:
         var12 = this.s.readInt();
         int var13 = 255;
         if(var1 == 31) {
            var13 = this.s.readUnsignedByte();
         }

         return new Color(var12 >> 22 & 255, var12 >> 12 & 255, var12 >> 2 & 255, var13);
      case 32:
         var6 = new Object[]{this.readField(), this.readField()};
         return var6;
      case 33:
         var6 = new Object[]{this.readField(), this.readField(), this.readField(), this.readField()};
         return var6;
      case 34:
      case 35:
         Object[] var14 = new Object[6];

         for(int var15 = 0; var15 < 5; ++var15) {
            var14[var15] = this.readField();
         }

         if(var1 == 35) {
            var14[5] = this.readField();
         }

         return var14;
      }
   }

   void createSpritesAndWatchers(LContext var1) {
      for(int var2 = 0; var2 < this.objTable.length; ++var2) {
         Object[] var3 = this.objTable[var2];
         int var4 = ((Number)var3[1]).intValue();
         if(var4 == 124 || var4 == 125) {
            var3[0] = new Sprite(var1);
         }

         if(var4 == 155) {
            var3[0] = new Watcher(var1);
            if(((Number)var3[2]).intValue() > 3) {
               Number var5 = (Number)var3[23];
               Number var6 = (Number)var3[24];
               if(this.floatWithoutFraction(var5) || this.floatWithoutFraction(var6)) {
                  var3[24] = new Double(var6.doubleValue() + 1.0E-8D);
               }
            }
         }
      }

   }

   boolean floatWithoutFraction(Object var1) {
      if(!(var1 instanceof Double)) {
         return false;
      } else {
         double var2 = ((Double)var1).doubleValue();
         return (double)Math.round(var2) == var2;
      }
   }

   void resolveReferences() throws IOException {
      for(int var1 = 0; var1 < this.objTable.length; ++var1) {
         int var2 = ((Number)this.objTable[var1][1]).intValue();
         if(var2 >= 20 && var2 <= 29) {
            Object[] var3 = (Object[])this.objTable[var1][0];

            for(int var4 = 0; var4 < var3.length; ++var4) {
               Object var5 = var3[var4];
               if(var5 instanceof Ref) {
                  var3[var4] = this.deRef((Ref)var5);
               }
            }
         }

         if(var2 > 99) {
            for(int var7 = 3; var7 < this.objTable[var1].length; ++var7) {
               Object var6 = this.objTable[var1][var7];
               if(var6 instanceof Ref) {
                  this.objTable[var1][var7] = this.deRef((Ref)var6);
               }
            }
         }
      }

   }

   Object deRef(Ref var1) {
      Object[] var2 = this.objTable[var1.index];
      return var2[0] != null && var2[0] != empty?var2[0]:var2;
   }

   void buildImagesAndSounds() throws IOException {
      for(int var1 = 0; var1 < this.objTable.length; ++var1) {
         int var2 = ((Number)this.objTable[var1][1]).intValue();
         Object[] var3;
         if(var2 == 34 || var2 == 35) {
            var3 = (Object[])this.objTable[var1][0];
            int var4 = ((Number)var3[0]).intValue();
            int var5 = ((Number)var3[1]).intValue();
            int var6 = ((Number)var3[2]).intValue();
            int[] var7 = this.decodePixels(this.objTable[((Ref)var3[4]).index][0]);
            MemoryImageSource var8 = null;
            this.objTable[var1][0] = empty;
            if(var6 <= 8) {
               IndexColorModel var9;
               if(var3[5] != null) {
                  Object[] var10 = (Object[])this.objTable[((Ref)var3[5]).index][0];
                  var9 = this.customColorMap(var6, var10);
               } else {
                  var9 = this.squeakColorMap(var6);
               }

               var8 = new MemoryImageSource(var4, var5, var9, this.rasterToByteRaster(var7, var4, var5, var6), 0, var4);
            }

            if(var6 == 16) {
               var8 = new MemoryImageSource(var4, var5, this.raster16to32(var7, var4, var5), 0, var4);
            }

            if(var6 == 32) {
               var8 = new MemoryImageSource(var4, var5, this.rasterAddAlphaTo32(var7), 0, var4);
            }

            if(var8 != null) {
               int[] var13 = new int[var4 * var5];
               PixelGrabber var14 = new PixelGrabber(var8, 0, 0, var4, var5, var13, 0, var4);

               try {
                  var14.grabPixels();
               } catch (InterruptedException var12) {
                  ;
               }

               BufferedImage var11 = new BufferedImage(var4, var5, 2);
               var11.getRaster().setDataElements(0, 0, var4, var5, var13);
               this.objTable[var1][0] = var11;
            }
         }

         if(var2 == 109) {
            var3 = this.objTable[((Ref)this.objTable[var1][6]).index];
            this.objTable[var1][0] = new ScratchSound(((Number)this.objTable[var1][7]).intValue(), (byte[])var3[0]);
         }
      }

   }

   int[] decodePixels(Object var1) throws IOException {
      if(var1 instanceof int[]) {
         return (int[])var1;
      } else {
         DataInputStream var2 = new DataInputStream(new ByteArrayInputStream((byte[])var1));
         int var3 = this.decodeInt(var2);
         int[] var4 = new int[var3];
         int var5 = 0;

         label52:
         while(var2.available() > 0 & var5 < var3) {
            int var6 = this.decodeInt(var2);
            int var7 = var6 >> 2;
            int var8 = var6 & 3;
            int var10;
            int var11;
            switch(var8) {
            case 0:
               var5 += var7;
               break;
            case 1:
               int var9 = var2.readUnsignedByte();
               var10 = var9 << 24 | var9 << 16 | var9 << 8 | var9;
               var11 = 0;

               while(true) {
                  if(var11 >= var7) {
                     continue label52;
                  }

                  var4[var5++] = var10;
                  ++var11;
               }
            case 2:
               var10 = var2.readInt();
               var11 = 0;

               while(true) {
                  if(var11 >= var7) {
                     continue label52;
                  }

                  var4[var5++] = var10;
                  ++var11;
               }
            case 3:
               for(var11 = 0; var11 < var7; ++var11) {
                  var10 = var2.readUnsignedByte() << 24;
                  var10 |= var2.readUnsignedByte() << 16;
                  var10 |= var2.readUnsignedByte() << 8;
                  var10 |= var2.readUnsignedByte();
                  var4[var5++] = var10;
               }
            }
         }

         return var4;
      }
   }

   int decodeInt(DataInputStream var1) throws IOException {
      int var2 = var1.readUnsignedByte();
      return var2 <= 223?var2:(var2 <= 254?(var2 - 224) * 256 + var1.readUnsignedByte():var1.readInt());
   }

   int[] rasterAddAlphaTo32(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         int var3 = var1[var2];
         if(var3 != 0) {
            var1[var2] = -16777216 | var3;
         }
      }

      return var1;
   }

   int[] raster16to32(int[] var1, int var2, int var3) {
      int[] var10 = new int[var2 * var3];
      int var11 = (var2 + 1) / 2;

      for(int var12 = 0; var12 < var3; ++var12) {
         int var4 = 16;

         for(int var13 = 0; var13 < var2; ++var13) {
            int var5 = var1[var12 * var11 + var13 / 2] >> var4 & '\uffff';
            int var6 = (var5 >> 10 & 31) << 3;
            int var7 = (var5 >> 5 & 31) << 3;
            int var8 = (var5 & 31) << 3;
            int var9 = var6 + var7 + var8 == 0?0:-16777216 | var6 << 16 | var7 << 8 | var8;
            var10[var12 * var2 + var13] = var9;
            var4 = var4 == 16?0:16;
         }
      }

      return var10;
   }

   byte[] rasterToByteRaster(int[] var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[var2 * var3];
      int var6 = var1.length / var3;
      int var7 = (1 << var4) - 1;
      int var8 = 32 / var4;

      for(int var9 = 0; var9 < var3; ++var9) {
         for(int var10 = 0; var10 < var2; ++var10) {
            int var11 = var1[var9 * var6 + var10 / var8];
            int var12 = var4 * (var8 - var10 % var8 - 1);
            var5[var9 * var2 + var10] = (byte)(var11 >> var12 & var7);
         }
      }

      return var5;
   }

   IndexColorModel squeakColorMap(int var1) {
      int var2 = var1 == 1?-1:0;
      return new IndexColorModel(var1, 256, squeakColors, 0, false, var2);
   }

   IndexColorModel customColorMap(int var1, Object[] var2) {
      byte[] var3 = new byte[4 * var2.length];
      int var4 = 0;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         Color var6 = (Color)this.objTable[((Ref)var2[var5]).index][0];
         var3[var4++] = (byte)var6.getRed();
         var3[var4++] = (byte)var6.getGreen();
         var3[var4++] = (byte)var6.getBlue();
         var3[var4++] = (byte)var6.getAlpha();
      }

      return new IndexColorModel(var1, var2.length, var3, 0, true, 0);
   }

   void fixSounds() {
      boolean var1 = false;

      int var2;
      int var3;
      for(var2 = 0; var2 < this.objTable.length; ++var2) {
         var3 = ((Number)this.objTable[var2][1]).intValue();
         if(var3 == 109 && ((ScratchSound)this.objTable[var2][0]).isByteReversed()) {
            var1 = true;
         }
      }

      if(var1) {
         for(var2 = 0; var2 < this.objTable.length; ++var2) {
            var3 = ((Number)this.objTable[var2][1]).intValue();
            if(var3 == 109) {
               ((ScratchSound)this.objTable[var2][0]).reverseBytes();
            }
         }

      }
   }

   void uncompressMedia() {
      for(int var1 = 0; var1 < this.objTable.length; ++var1) {
         Object[] var2 = this.objTable[var1];
         int var3 = ((Number)var2[1]).intValue();
         int var4 = -1;
         if(var3 >= 100) {
            var4 = ((Number)var2[2]).intValue();
         }

         if(var3 == 162 && var4 >= 4) {
            if(var2[7] instanceof byte[]) {
               BufferedImage var5 = this.jpegDecode((byte[])var2[7]);
               if(var5 != null) {
                  if(var2[4] instanceof Image) {
                     ((Image)var2[4]).flush();
                  }

                  var2[4] = var5;
                  var2[7] = empty;
               }
            }

            if(var2[8] instanceof BufferedImage) {
               var2[4] = var2[8];
               var2[8] = empty;
            }
         }

         if(var3 == 164 && var4 >= 2 && var2[9] instanceof byte[]) {
            int var10 = ((Number)var2[7]).intValue();
            int var6 = ((Number)var2[8]).intValue();
            byte[] var7 = (byte[])var2[9];
            int var8 = (var7.length * 8 + (var6 - 1)) / var6;
            int[] var9 = (new ADPCMDecoder(var7, var6)).decode(var8);
            var2[4] = new ScratchSound(var10, var9);
            var2[7] = var2[8] = var2[9] = empty;
         }
      }

   }

   void canonicalizeMedia() {
      new Vector(100);
      new Vector(100);

      for(int var3 = 0; var3 < this.objTable.length; ++var3) {
         Object[] var4 = this.objTable[var3];
         int var5 = ((Number)var4[1]).intValue();
         if(var5 == 162) {
            BufferedImage var6 = (BufferedImage)var4[4];
         }

         if(var5 == 164) {
            ScratchSound var7 = (ScratchSound)var4[4];
         }
      }

   }

   BufferedImage jpegDecode(byte[] var1) {
      Toolkit var2 = Toolkit.getDefaultToolkit();
      Image var3 = var2.createImage(var1);
      MediaTracker var4 = new MediaTracker(canvas);
      var4.addImage(var3, 0);

      try {
         var4.waitForID(0);
      } catch (InterruptedException var9) {
         ;
      }

      if(var3 == null) {
         return null;
      } else {
         int var5 = var3.getWidth((ImageObserver)null);
         int var6 = var3.getHeight((ImageObserver)null);
         BufferedImage var7 = new BufferedImage(var5, var6, 2);
         Graphics var8 = var7.getGraphics();
         var8.drawImage(var3, 0, 0, var5, var6, (ImageObserver)null);
         var8.dispose();
         var3.flush();
         return var7;
      }
   }

   void printit(Object var1) {
      if(var1 instanceof Object[] && ((Object[])var1).length == 0) {
         System.out.print(" []");
      } else if(var1 instanceof BufferedImage) {
         BufferedImage var2 = (BufferedImage)var1;
         System.out.print(" BufferedImage_" + var1.hashCode() + "(" + var2.getWidth((ImageObserver)null) + "x" + var2.getHeight((ImageObserver)null) + ")");
      } else {
         System.out.print(" " + var1);
      }
   }

}
