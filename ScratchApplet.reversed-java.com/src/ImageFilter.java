// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:29
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

class ImageFilter {

   BufferedImage filteredImage;
   BufferedImage tempImage;


   void setSourceImage(BufferedImage var1) {
      if(this.filteredImage == null || this.filteredImage.getWidth((ImageObserver)null) != var1.getWidth((ImageObserver)null) || this.filteredImage.getHeight((ImageObserver)null) != var1.getHeight((ImageObserver)null)) {
         if(this.filteredImage != null) {
            this.filteredImage.flush();
         }

         this.filteredImage = new BufferedImage(var1.getWidth((ImageObserver)null), var1.getHeight((ImageObserver)null), 2);
      }

      this.filteredImage.getRaster().setDataElements(0, 0, var1.getData());
   }

   BufferedImage makeOutputImage(BufferedImage var1) {
      if(this.tempImage == null || this.tempImage.getWidth((ImageObserver)null) != var1.getWidth((ImageObserver)null) || this.tempImage.getHeight((ImageObserver)null) != var1.getHeight((ImageObserver)null)) {
         if(this.tempImage != null) {
            this.tempImage.flush();
         }

         this.tempImage = new BufferedImage(var1.getWidth((ImageObserver)null), var1.getHeight((ImageObserver)null), 2);
      }

      return this.tempImage;
   }

   void applyHueShift(int var1) {
      BufferedImage var2 = this.filteredImage;
      BufferedImage var3 = this.makeOutputImage(var2);
      int var15 = var2.getWidth();
      int var16 = var2.getHeight();

      for(int var17 = 0; var17 < var15; ++var17) {
         for(int var18 = 0; var18 < var16; ++var18) {
            int var5;
            int var4 = var5 = var2.getRGB(var17, var18);
            int var6 = var4 & -16777216;
            if(var6 != 0) {
               int var7 = var4 >> 16 & 255;
               int var8 = var4 >> 8 & 255;
               int var9 = var4 & 255;
               int var10 = var7 < var8?var7:var8;
               if(var9 < var10) {
                  var10 = var9;
               }

               int var11 = var7 > var8?var7:var8;
               if(var9 > var11) {
                  var11 = var9;
               }

               int var14 = var11 * 1000 / 255;
               if(var14 < 150) {
                  var14 = 150;
               }

               int var13 = var11 == 0?0:(var11 - var10) * 1000 / var11;
               if(var13 < 150) {
                  var13 = 150;
               }

               int var12 = this.rgb2hue(var7, var8, var9, var10, var11) + 180 * var1 / 100;
               var5 = var6 | this.hsv2rgb(var12, var13, var14);
            }

            var3.setRGB(var17, var18, var5);
         }
      }

      this.tempImage = this.filteredImage;
      this.filteredImage = var3;
   }

   void applyBrightnessShift(int var1) {
      BufferedImage var2 = this.filteredImage;
      BufferedImage var3 = this.makeOutputImage(var2);
      int var15 = var2.getWidth();
      int var16 = var2.getHeight();

      for(int var17 = 0; var17 < var15; ++var17) {
         for(int var18 = 0; var18 < var16; ++var18) {
            int var5;
            int var4 = var5 = var2.getRGB(var17, var18);
            int var6 = var4 & -16777216;
            if(var6 != 0) {
               int var7 = var4 >> 16 & 255;
               int var8 = var4 >> 8 & 255;
               int var9 = var4 & 255;
               int var10 = var7 < var8?var7:var8;
               if(var9 < var10) {
                  var10 = var9;
               }

               int var11 = var7 > var8?var7:var8;
               if(var9 > var11) {
                  var11 = var9;
               }

               int var12 = this.rgb2hue(var7, var8, var9, var10, var11);
               int var13 = var11 == 0?0:(var11 - var10) * 1000 / var11;
               int var14 = var11 * 1000 / 255 + 10 * var1;
               if(var14 > 1000) {
                  var14 = 1000;
               }

               if(var14 < 0) {
                  var14 = 0;
               }

               var5 = var6 | this.hsv2rgb(var12, var13, var14);
            }

            var3.setRGB(var17, var18, var5);
         }
      }

      this.tempImage = this.filteredImage;
      this.filteredImage = var3;
   }

   int rgb2hue(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var5 - var4;
      return var6 == 0?0:(var1 == var5?60 * (var2 - var3) / var6:(var2 == var5?120 + 60 * (var3 - var1) / var6:240 + 60 * (var1 - var2) / var6));
   }

   int hsv2rgb(int var1, int var2, int var3) {
      int var4 = var1 % 360;
      if(var4 < 0) {
         var4 += 360;
      }

      int var5 = var4 / 60;
      int var6 = var4 % 60;
      int var7 = (1000 - var2) * var3 / 3922;
      int var8 = (1000 - var2 * var6 / 60) * var3 / 3922;
      int var9 = (1000 - var2 * (60 - var6) / 60) * var3 / 3922;
      int var10 = var3 * 1000 / 3922;
      switch(var5) {
      case 0:
         return var10 << 16 | var9 << 8 | var7;
      case 1:
         return var8 << 16 | var10 << 8 | var7;
      case 2:
         return var7 << 16 | var10 << 8 | var9;
      case 3:
         return var7 << 16 | var8 << 8 | var10;
      case 4:
         return var9 << 16 | var7 << 8 | var10;
      case 5:
         return var10 << 16 | var7 << 8 | var8;
      default:
         return 0;
      }
   }

   void applyFisheye(double var1) {
      BufferedImage var3 = this.filteredImage;
      BufferedImage var4 = this.makeOutputImage(var3);
      int var5 = var3.getWidth();
      int var6 = var3.getHeight();
      double var7 = (double)(var5 / 2);
      double var9 = (double)(var6 / 2);
      double var11 = (var1 + 100.0D) / 100.0D;

      for(int var25 = 0; var25 < var5; ++var25) {
         for(int var26 = 0; var26 < var6; ++var26) {
            double var13 = ((double)var25 - var7) / var7;
            double var15 = ((double)var26 - var9) / var9;
            double var17 = Math.pow(Math.sqrt(var13 * var13 + var15 * var15), var11);
            double var21;
            double var23;
            if(var17 <= 1.0D) {
               double var19 = Math.atan2(var15, var13);
               var21 = var7 + var17 * Math.cos(var19) * var7;
               var23 = var9 + var17 * Math.sin(var19) * var9;
            } else {
               var21 = (double)var25;
               var23 = (double)var26;
            }

            var4.setRGB(var25, var26, this.interpolate(var3, var21, var23));
         }
      }

      this.tempImage = this.filteredImage;
      this.filteredImage = var4;
   }

   int interpolate(BufferedImage var1, double var2, double var4) {
      int var6 = (int)Math.round(var2);
      if(var6 < 0) {
         var6 = 0;
      }

      if(var6 >= var1.getWidth((ImageObserver)null)) {
         var6 = var1.getWidth((ImageObserver)null) - 1;
      }

      int var7 = (int)Math.round(var4);
      if(var7 < 0) {
         var7 = 0;
      }

      if(var7 >= var1.getHeight((ImageObserver)null)) {
         var7 = var1.getHeight((ImageObserver)null) - 1;
      }

      return var1.getRGB(var6, var7);
   }

   void applyWhirl(double var1) {
      BufferedImage var3 = this.filteredImage;
      BufferedImage var4 = this.makeOutputImage(var3);
      double var31 = Math.toRadians(-var1);
      int var33 = var3.getWidth();
      int var34 = var3.getHeight();
      double var35 = (double)(var33 / 2);
      double var37 = (double)(var34 / 2);
      double var5;
      double var9;
      double var11;
      if(var35 < var37) {
         var5 = var35;
         var9 = var37 / var35;
         var11 = 1.0D;
      } else {
         var5 = var37;
         var9 = 1.0D;
         if(var37 < var35) {
            var11 = var35 / var37;
         } else {
            var11 = 1.0D;
         }
      }

      double var7 = var5 * var5;

      for(int var39 = 0; var39 < var33; ++var39) {
         for(int var40 = 0; var40 < var34; ++var40) {
            double var13 = var9 * ((double)var39 - var35);
            double var15 = var11 * ((double)var40 - var37);
            double var17 = var13 * var13 + var15 * var15;
            if(var17 < var7) {
               double var19 = 1.0D - Math.sqrt(var17) / var5;
               double var21 = var31 * var19 * var19;
               double var23 = Math.sin(var21);
               double var25 = Math.cos(var21);
               double var27 = (var25 * var13 - var23 * var15) / var9 + var35;
               double var29 = (var23 * var13 + var25 * var15) / var11 + var37;
               var4.setRGB(var39, var40, var3.getRGB((int)var27, (int)var29));
            } else {
               var4.setRGB(var39, var40, var3.getRGB(var39, var40));
            }
         }
      }

      this.tempImage = this.filteredImage;
      this.filteredImage = var4;
   }

   void applyMosaic(double var1) {
      BufferedImage var3 = this.filteredImage;
      int var4 = (int)(Math.abs(var1) + 10.0D) / 10;
      var4 = Math.min(var4, Math.min(var3.getWidth((ImageObserver)null), var3.getHeight((ImageObserver)null)) - 1);
      if(var4 > 1) {
         AffineTransform var5 = AffineTransform.getScaleInstance(1.0D / (double)var4, 1.0D / (double)var4);
         AffineTransformOp var6 = new AffineTransformOp(var5, 1);
         BufferedImage var7 = var6.filter(var3, (BufferedImage)null);
         int var8 = var4 * var7.getWidth((ImageObserver)null);
         int var9 = var4 * var7.getHeight((ImageObserver)null);
         BufferedImage var10 = new BufferedImage(var8, var9, 2);
         var10.getRaster();
         Graphics var11 = var10.getGraphics();
         int var12 = var7.getWidth((ImageObserver)null);
         int var13 = var7.getHeight((ImageObserver)null);

         for(int var14 = 0; var14 < var10.getHeight((ImageObserver)null); var14 += var13) {
            for(int var15 = 0; var15 < var10.getWidth((ImageObserver)null); var15 += var12) {
               var11.drawImage(var7, var15, var14, (ImageObserver)null);
            }
         }

         var11.dispose();
         var7.flush();
         if(this.filteredImage != null) {
            this.filteredImage.flush();
         }

         var5 = AffineTransform.getScaleInstance((double)var3.getWidth((ImageObserver)null) / (double)var10.getWidth((ImageObserver)null), (double)var3.getHeight((ImageObserver)null) / (double)var10.getHeight((ImageObserver)null));
         var6 = new AffineTransformOp(var5, 1);
         this.filteredImage = var6.filter(var10, (BufferedImage)null);
         var10.flush();
      }
   }

   void applyPixelate(double var1) {
      BufferedImage var3 = this.filteredImage;
      double var4 = (Math.abs(var1) + 10.0D) / 10.0D;
      var4 = Math.min(var4, (double)Math.min(var3.getWidth((ImageObserver)null), var3.getHeight((ImageObserver)null)));
      if(var4 > 1.0D) {
         AffineTransform var6 = AffineTransform.getScaleInstance(1.0D / var4, 1.0D / var4);
         AffineTransformOp var7 = new AffineTransformOp(var6, 2);
         BufferedImage var8 = var7.filter(var3, (BufferedImage)null);
         var6 = AffineTransform.getScaleInstance(var4, var4);
         var7 = new AffineTransformOp(var6, 1);
         this.filteredImage = var7.filter(var8, this.filteredImage);
         var8.flush();
      }
   }
}
