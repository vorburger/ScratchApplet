// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:35
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

class Sprite implements Drawable {

   static final int originX = 241;
   static final int originY = 206;
   PlayerCanvas canvas;
   BufferedImage costume;
   BufferedImage rotatedCostume;
   BufferedImage filteredCostume;
   BufferedImage tempImage;
   double x;
   double y;
   boolean isShowing = true;
   boolean isDraggable = false;
   double alpha = 1.0D;
   double scale = 1.0D;
   double rotationDegrees = 90.0D;
   int rotationstyle;
   int rotationX;
   int rotationY;
   int offsetX;
   int offsetY;
   Bubble bubble;
   boolean penDown;
   int lastPenX;
   int lastPenY;
   Color penColor = new Color(0, 0, 255);
   int penSize = 1;
   double penHue;
   double penShade;
   boolean filterChanged = false;
   double color;
   double brightness;
   double fisheye;
   double whirl;
   double mosaic;
   double pixelate;
   ImageFilter imageFilter = new ImageFilter();


   Sprite(LContext var1) {
      this.setPenColor(this.penColor);
      if(var1 != null) {
         this.canvas = var1.canvas;
      }

   }

   int screenX() {
      return 241 + (int)(this.x - (double)this.offsetX);
   }

   int screenY() {
      return 206 + (int)(-this.y - (double)this.offsetY);
   }

   void setscreenX(double var1) {
      this.x = var1 + (double)this.offsetX - 241.0D;
   }

   void setscreenY(double var1) {
      this.y = -(var1 + (double)this.offsetY - 206.0D);
   }

   public void mouseDown(int var1, int var2) {
   }

   void setStageOffset() {
      this.x = this.y = 0.0D;
      this.offsetX = this.costume.getWidth((ImageObserver)null) / 2;
      this.offsetY = this.costume.getHeight((ImageObserver)null) / 2;
   }

   public void dragTo(int var1, int var2) {
      this.inval();
      this.setscreenX((double)var1);
      this.setscreenY((double)var2);
      this.updateBubble();
      this.inval();
   }

   boolean containsPoint(int var1, int var2) {
      BufferedImage var3 = this.outImage();
      int var4 = this.screenX();
      int var5 = this.screenY();
      int var6 = var3.getWidth((ImageObserver)null);
      int var7 = var3.getHeight((ImageObserver)null);
      if(var1 >= var4 && var1 < var4 + var6 && var2 >= var5 && var2 < var5 + var7) {
         int var8 = var3.getRGB(var1 - var4, var2 - var5);
         return (var8 & -16777216) != 0;
      } else {
         return false;
      }
   }

   boolean touchingSprite(Object var1, LContext var2) {
      if(!(var1 instanceof Sprite)) {
         Logo.error("argument must be a Sprite", var2);
         return false;
      } else {
         Sprite var3 = (Sprite)var1;
         Rectangle var4 = this.rect().intersection(var3.rect());
         if(var4.width > 0 && var4.height > 0) {
            BufferedImage var5 = this.outImage();
            BufferedImage var6 = var3.outImage();
            int var7 = var4.x - this.screenX();
            int var8 = var4.y - this.screenY();
            int var9 = var4.x - var3.screenX();
            int var10 = var4.y - var3.screenY();

            for(int var11 = var8; var11 < var8 + var4.height; ++var11) {
               int var12 = var9;

               for(int var13 = var7; var13 < var7 + var4.width; ++var13) {
                  int var14 = var5.getRGB(var13, var11);
                  int var15 = var6.getRGB(var12, var10);
                  if((var14 & -16777216) != 0 && (var15 & -16777216) != 0) {
                     return true;
                  }

                  ++var12;
               }

               ++var10;
            }

            return false;
         } else {
            return false;
         }
      }
   }

   boolean touchingColor(Object var1, LContext var2) {
      if(!(var1 instanceof Color)) {
         Logo.error("argument of touchingColor? must be a Color", var2);
         return false;
      } else {
         int var3 = ((Color)var1).getRGB() | -16777216;
         Rectangle var4 = this.rect();
         BufferedImage var5 = this.outImage();
         BufferedImage var6 = var2.canvas.drawAreaWithoutSprite(var4, this);

         for(int var7 = 0; var7 < var4.height; ++var7) {
            for(int var8 = 0; var8 < var4.width; ++var8) {
               if((var5.getRGB(var8, var7) & -16777216) != 0 && this.colorsMatch(var6.getRGB(var8, var7), var3)) {
                  var6.flush();
                  return true;
               }
            }
         }

         var6.flush();
         return false;
      }
   }

   boolean colorTouchingColor(Object var1, Object var2, LContext var3) {
      if(var1 instanceof Color && var2 instanceof Color) {
         int var4 = ((Color)var1).getRGB() | -16777216;
         int var5 = ((Color)var2).getRGB() | -16777216;
         Rectangle var6 = this.rect();
         BufferedImage var7 = this.outImage();
         BufferedImage var8 = var3.canvas.drawAreaWithoutSprite(var6, this);

         for(int var9 = 0; var9 < var6.height; ++var9) {
            for(int var10 = 0; var10 < var6.width; ++var10) {
               if(this.colorsMatch(var7.getRGB(var10, var9), var4) && this.colorsMatch(var8.getRGB(var10, var9), var5)) {
                  var8.flush();
                  return true;
               }
            }
         }

         var8.flush();
         return false;
      } else {
         Logo.error("the arguments of colorTouchingColor? must be Colors", var3);
         return false;
      }
   }

   boolean colorsMatch(int var1, int var2) {
      return (var1 & -16777216) != (var2 & -16777216)?false:((var1 >> 16 & 248) != (var2 >> 16 & 248)?false:((var1 >> 8 & 248) != (var2 >> 8 & 248)?false:((var1 & 16776960) == 0 && (var2 & 16776960) == 0 && (var1 & 255) <= 8 && (var2 & 255) <= 8?true:(var1 & 248) == (var2 & 248))));
   }

   void setalpha(Object var1, LContext var2) {
      double var3 = Logo.aDouble(var1, var2);
      if(var3 < 0.0D) {
         var3 = -var3;
      }

      if(var3 > 1.0D) {
         var3 = 1.0D;
      }

      this.alpha = var3;
      this.inval();
   }

   void setcostume(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof BufferedImage) {
         this.rotationX = Logo.anInt(var2, var4);
         this.rotationY = Logo.anInt(var3, var4);
         if(this.costume != null) {
            this.inval();
         }

         this.costume = (BufferedImage)var1;
         this.rotateAndScale();
         this.inval();
      }
   }

   void costumeChanged() {
      this.inval();
      this.rotateAndScale();
      this.inval();
   }

   void setscale(Object var1, LContext var2) {
      double var3 = Logo.aDouble(var1, var2);
      double var5 = (double)Math.min(this.costume.getWidth((ImageObserver)null), 10);
      double var7 = (double)Math.min(this.costume.getHeight((ImageObserver)null), 10);
      double var9 = Math.max(var5 / 480.0D, var7 / 360.0D);
      double var11 = Math.min(480.0D / (double)this.costume.getWidth((ImageObserver)null), 360.0D / (double)this.costume.getHeight((ImageObserver)null));
      this.scale = Math.min(Math.max(var3, var9), var11);
      this.costumeChanged();
   }

   void rotateAndScale() {
      this.filterChanged = true;
      double var1 = this.rotationstyle == 0?this.rotationDegrees:90.0D;
      if(this.rotatedCostume != null && this.rotatedCostume != this.costume) {
         this.rotatedCostume.flush();
      }

      if(this.scale == 1.0D && this.rotationDegrees == 90.0D) {
         this.rotatedCostume = this.costume;
         this.offsetX = this.rotationX;
         this.offsetY = this.rotationY;
      } else {
         int var3 = this.costume.getWidth((ImageObserver)null);
         int var4 = this.costume.getHeight((ImageObserver)null);
         double var5 = Math.toRadians(var1 - 90.0D);
         AffineTransform var7 = AffineTransform.getRotateInstance(var5, (double)(var3 / 2), (double)(var4 / 2));
         var7.scale(this.scale, this.scale);
         AffineTransformOp var8 = new AffineTransformOp(var7, 2);
         Float var9 = (Float)var8.getBounds2D(this.costume);
         float var10 = -var9.x;
         float var11 = -var9.y;
         var7 = AffineTransform.getRotateInstance(var5, (double)((float)(var3 / 2) + var10), (double)((float)(var4 / 2) + var11));
         var7.translate((double)var10, (double)var11);
         var7.scale(this.scale, this.scale);
         var8 = new AffineTransformOp(var7, 2);
         this.rotatedCostume = var8.filter(this.costume, (BufferedImage)null);
         var7 = AffineTransform.getRotateInstance(var5, 0.0D, 0.0D);
         var7.scale(this.scale, this.scale);
         Point2D var12 = var7.transform(new Double((double)(this.rotationX - var3 / 2), (double)(this.rotationY - var4 / 2)), (Point2D)null);
         this.offsetX = (int)(var12.getX() + (double)(this.rotatedCostume.getWidth((ImageObserver)null) / 2));
         this.offsetY = (int)(var12.getY() + (double)(this.rotatedCostume.getHeight((ImageObserver)null) / 2));
         if(this.rotationstyle == 1) {
            double var13 = this.rotationDegrees < 0.0D?this.rotationDegrees + 360.0D:this.rotationDegrees;
            if(var13 <= 180.0D) {
               return;
            }

            int var15 = this.rotatedCostume.getWidth((ImageObserver)null);
            var7 = AffineTransform.getScaleInstance(-1.0D, 1.0D);
            var7.translate((double)(-var15), 0.0D);
            var8 = new AffineTransformOp(var7, 2);
            this.rotatedCostume = var8.filter(this.rotatedCostume, (BufferedImage)null);
            this.offsetX = (int)((double)(var15 / 2) - this.scale * (double)(this.rotationX - var3 / 2));
            this.offsetY = (int)(this.scale * (double)this.rotationY);
         }

      }
   }

   void show() {
      this.isShowing = true;
      this.inval();
   }

   void hide() {
      this.isShowing = false;
      this.inval();
   }

   public boolean isShowing() {
      return this.isShowing;
   }

   public boolean isVisible() {
      return this.isShowing && this.alpha > 0.0D;
   }

   public Rectangle rect() {
      BufferedImage var1 = this.outImage();
      return var1 == null?new Rectangle(this.screenX(), this.screenY(), 600, 600):new Rectangle(this.screenX(), this.screenY(), var1.getWidth((ImageObserver)null), var1.getHeight((ImageObserver)null));
   }

   public Rectangle fullRect() {
      Rectangle var1 = this.rect();
      if(this.bubble != null) {
         var1 = var1.union(this.bubble.rect());
      }

      return var1;
   }

   void inval() {
      this.canvas.inval(this.fullRect());
   }

   public void paint(Graphics var1) {
      Graphics2D var2 = (Graphics2D)var1;
      if(this.filterChanged) {
         this.applyFilters();
      }

      if(this.alpha != 1.0D) {
         Composite var3 = var2.getComposite();
         var2.setComposite(AlphaComposite.getInstance(3, (float)this.alpha));
         var2.drawImage(this.outImage(), this.screenX(), this.screenY(), (ImageObserver)null);
         var2.setComposite(var3);
      } else {
         var2.drawImage(this.outImage(), this.screenX(), this.screenY(), (ImageObserver)null);
      }

   }

   public void paintBubble(Graphics var1) {
      if(this.bubble != null) {
         this.bubble.paint(var1);
      }

   }

   void talkbubble(Object var1, boolean var2, boolean var3, LContext var4) {
      String var5 = var1 instanceof String?(String)var1:Logo.prs(var1);
      this.inval();
      this.bubble = null;
      if(var5.length() != 0) {
         this.bubble = new Bubble();
         if(var2) {
            this.bubble.beAskBubble();
         }

         if(!var3) {
            this.bubble.beThinkBubble(true);
         }

         this.bubble.setContents(var5);
         if(this.rotationDegrees >= 0.0D && this.rotationDegrees <= 180.0D) {
            this.bubble.pointLeft = true;
         }

         this.updateBubble();
      }
   }

   void updateBubble() {
      byte var1 = 3;
      int var2 = 482 - var1;
      if(this.bubble != null) {
         this.inval();
         Rectangle var3 = this.rect();
         boolean var4 = this.bubble.pointLeft;
         int[] var5 = this.bubbleInsets();
         if(var4 && var3.x + var3.width - var5[1] + this.bubble.w + var1 > var2) {
            var4 = false;
         }

         if(!var4 && var3.x + var5[0] - this.bubble.w - var1 < 0) {
            var4 = true;
         }

         if(var4) {
            this.bubble.pointLeft = true;
            this.bubble.x = var3.x + var3.width - var5[1] + var1;
         } else {
            this.bubble.pointLeft = false;
            this.bubble.x = var3.x + var5[0] - this.bubble.w - var1;
         }

         if(this.bubble.x + this.bubble.w > var2) {
            this.bubble.x = var2 - this.bubble.w;
         }

         if(this.bubble.x < var1) {
            this.bubble.x = var1;
         }

         this.bubble.y = Math.max(var3.y - this.bubble.h - 12, 25 + var1);
         if(this.bubble.y + this.bubble.h > 387) {
            this.bubble.y = 387 - this.bubble.h;
         }

         this.inval();
      }
   }

   int[] bubbleInsets() {
      BufferedImage var1 = this.outImage();
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int var4 = var2;
      int var5 = var2;
      int var6 = -1;

      for(int var7 = 0; var7 < var3; ++var7) {
         boolean var8 = false;

         for(int var10 = 0; var10 < Math.max(var4, var5); ++var10) {
            int var9 = var1.getRGB(var10, var7) & -16777216;
            if(var9 != 0 && var10 < var4) {
               var4 = var10;
               var8 = true;
            }

            var9 = var1.getRGB(var2 - var10 - 1, var7) & -16777216;
            if(var9 != 0 && var10 < var5) {
               var5 = var10;
               var8 = true;
            }
         }

         if(var6 < 0) {
            if(var8) {
               var6 = var7;
            }
         } else if(var7 >= var6 + 10) {
            break;
         }
      }

      int[] var11 = new int[]{var4, var5};
      return var11;
   }

   void setPenDown(boolean var1) {
      if(var1 != this.penDown) {
         if(var1) {
            this.lastPenX = this.lastPenY = -1000000;
         }

         this.canvas.updatePenTrailForSprite(this);
         this.penDown = var1;
      }
   }

   void setPenColor(Color var1) {
      float[] var2 = Color.RGBtoHSB(var1.getRed(), var1.getGreen(), var1.getBlue(), (float[])null);
      this.penColor = var1;
      this.penHue = 200.0D * (double)var2[0];
      float var3 = var2[1];
      float var4 = var2[2];
      if((double)var4 == 1.0D) {
         this.penShade = 50.0D + 50.0D * (1.0D - (double)var3);
      } else {
         this.penShade = 50.0D * (double)var4;
      }

   }

   void setPenHue(double var1) {
      this.penHue = var1 % 200.0D;
      if(this.penHue < 0.0D) {
         this.penHue += 200.0D;
      }

      this.setPenShade(this.penShade);
   }

   void setPenShade(double var1) {
      this.penShade = var1 % 200.0D;
      if(this.penShade < 0.0D) {
         this.penShade += 200.0D;
      }

      float var3 = (float)(this.penShade > 100.0D?200.0D - this.penShade:this.penShade);
      float var4;
      if((double)var3 <= 50.0D) {
         var4 = (var3 + 10.0F) / 60.0F;
         this.penColor = new Color(Color.HSBtoRGB((float)(this.penHue / 200.0D), 1.0F, var4));
      } else {
         var4 = (100.0F - var3 + 10.0F) / 60.0F;
         this.penColor = new Color(Color.HSBtoRGB((float)(this.penHue / 200.0D), var4, 1.0F));
      }

   }

   BufferedImage outImage() {
      return this.filteredCostume != null?this.filteredCostume:(this.rotatedCostume != null?this.rotatedCostume:this.costume);
   }

   void applyFilters() {
      if(!this.filtersActive()) {
         this.filteredCostume = null;
         this.filterChanged = false;
      } else {
         this.imageFilter.setSourceImage(this.rotatedCostume != null?this.rotatedCostume:this.costume);
         if(this.color != 0.0D) {
            this.imageFilter.applyHueShift((int)this.color);
         }

         if(this.brightness != 0.0D) {
            this.imageFilter.applyBrightnessShift((int)this.brightness);
         }

         if(this.whirl != 0.0D) {
            this.imageFilter.applyWhirl(this.whirl);
         }

         if(this.fisheye != 0.0D) {
            this.imageFilter.applyFisheye(this.fisheye);
         }

         if(Math.abs(this.pixelate) >= 5.0D) {
            this.imageFilter.applyPixelate(this.pixelate);
         }

         if(Math.abs(this.mosaic) >= 5.0D) {
            this.imageFilter.applyMosaic(this.mosaic);
         }

         this.filteredCostume = this.imageFilter.filteredImage;
         this.filterChanged = false;
      }
   }

   boolean filtersActive() {
      return this.color != 0.0D || this.brightness != 0.0D || this.fisheye != 0.0D || this.whirl != 0.0D || Math.abs(this.mosaic) >= 5.0D || Math.abs(this.pixelate) >= 5.0D;
   }
}
