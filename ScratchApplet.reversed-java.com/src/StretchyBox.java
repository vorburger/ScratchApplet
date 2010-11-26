// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:36
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

class StretchyBox implements Drawable {

   int x;
   int y;
   int w = 100;
   int h = 75;
   BufferedImage cornerTL;
   BufferedImage cornerTR;
   BufferedImage cornerBL;
   BufferedImage cornerBR;
   BufferedImage edgeTop;
   BufferedImage edgeBottom;
   BufferedImage edgeLeft;
   BufferedImage edgeRight;
   Color fillColor;


   void setFrameImage(BufferedImage var1) {
      int var2 = var1.getWidth((ImageObserver)null);
      int var3 = var1.getHeight((ImageObserver)null);
      int var4 = var2 / 2;
      int var5 = var3 / 2;
      this.cornerTL = var1.getSubimage(0, 0, var4, var5);
      this.cornerTR = var1.getSubimage(var2 - var4, 0, var4, var5);
      this.cornerBL = var1.getSubimage(0, var3 - var5, var4, var5);
      this.cornerBR = var1.getSubimage(var2 - var4, var3 - var5, var4, var5);
      this.edgeTop = var1.getSubimage(var4, 0, 1, var5);
      this.edgeBottom = var1.getSubimage(var4, var3 - var5, 1, var5);
      this.edgeLeft = var1.getSubimage(0, var5, var4, 1);
      this.edgeRight = var1.getSubimage(var2 - var4, var5, var4, 1);
      this.fillColor = new Color(var1.getRGB(var4, var5));
   }

   public boolean isShowing() {
      return true;
   }

   public Rectangle rect() {
      return new Rectangle(this.x, this.y, this.w, this.h);
   }

   public Rectangle fullRect() {
      return this.rect();
   }

   public void paint(Graphics var1) {
      if(this.cornerTL == null) {
         var1.setColor(new Color(100, 100, 250));
         var1.fillRect(this.x, this.y, this.w, this.h);
      } else {
         int var2 = this.cornerTL.getWidth((ImageObserver)null);
         int var3 = this.cornerTL.getHeight((ImageObserver)null);
         var1.setColor(this.fillColor);
         var1.fillRect(this.x + var2, this.y + var3, this.w - 2 * var2, this.h - 2 * var3);

         int var4;
         for(var4 = this.x + var2; var4 < this.x + this.w - var2; ++var4) {
            var1.drawImage(this.edgeTop, var4, this.y, (ImageObserver)null);
            var1.drawImage(this.edgeBottom, var4, this.y + this.h - var3, (ImageObserver)null);
         }

         for(var4 = this.y + var3; var4 < this.y + this.h - var3; ++var4) {
            var1.drawImage(this.edgeLeft, this.x, var4, (ImageObserver)null);
            var1.drawImage(this.edgeRight, this.x + this.w - var2, var4, (ImageObserver)null);
         }

         var1.drawImage(this.cornerTL, this.x, this.y, (ImageObserver)null);
         var1.drawImage(this.cornerTR, this.x + this.w - var2, this.y, (ImageObserver)null);
         var1.drawImage(this.cornerBL, this.x, this.y + this.h - var3, (ImageObserver)null);
         var1.drawImage(this.cornerBR, this.x + this.w - var2, this.y + this.h - var3, (ImageObserver)null);
      }
   }

   public void paintBubble(Graphics var1) {
   }

   public void dragTo(int var1, int var2) {
   }

   public void mouseDown(int var1, int var2) {
   }
}
