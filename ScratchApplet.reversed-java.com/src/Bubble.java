// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Vector;

class Bubble extends StretchyBox {

   boolean pointLeft = false;
   BufferedImage leftPointer;
   BufferedImage rightPointer;
   int fontSize = 13;
   Font font;
   FontRenderContext renderContext;
   int wrapWidth;
   String contents;
   String[] lines;
   int[] xOffsets;


   Bubble() {
      this.font = new Font("Arial Unicode MS", 1, this.fontSize);
      this.wrapWidth = 135;
      this.renderContext = Skin.bubbleFrame.createGraphics().getFontRenderContext();
      this.setFrameImage(Skin.bubbleFrame);
      this.beThinkBubble(false);
   }

   void beThinkBubble(boolean var1) {
      if(var1) {
         this.leftPointer = Skin.thinkPointerL;
         this.rightPointer = Skin.thinkPointerR;
      } else {
         this.leftPointer = Skin.talkPointerL;
         this.rightPointer = Skin.talkPointerR;
      }

   }

   void beAskBubble() {
      this.renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
      this.setFrameImage(Skin.askBubbleFrame);
      this.leftPointer = Skin.askPointerL;
      this.rightPointer = Skin.askPointerR;
   }

   void setContents(String var1) {
      this.contents = var1;
      Vector var2 = new Vector();

      int var4;
      for(int var3 = 0; var3 < var1.length(); var3 = var4) {
         var4 = this.findLineEnd(var1, var3);
         var2.addElement(var1.substring(var3, var4));
      }

      this.lines = new String[var2.size()];
      this.w = 65;

      for(var4 = 0; var4 < this.lines.length; ++var4) {
         this.lines[var4] = (String)var2.get(var4);
         this.w = Math.max(this.w, this.widthOf(this.lines[var4]) + 15);
      }

      this.xOffsets = new int[this.lines.length];

      for(var4 = 0; var4 < this.lines.length; ++var4) {
         this.xOffsets[var4] = (this.w - this.widthOf(this.lines[var4])) / 2;
      }

      this.h = this.lines.length * (this.fontSize + 2) + 19;
   }

   int findLineEnd(String var1, int var2) {
      int var3;
      for(var3 = var2 + 1; var3 < var1.length() && this.widthOf(var1.substring(var2, var3 + 1)) < this.wrapWidth; ++var3) {
         ;
      }

      if(var3 == var1.length()) {
         return var3;
      } else if(this.widthOf(var1.substring(var2, var3 + 1)) < this.wrapWidth) {
         return var3 + 1;
      } else {
         int var4;
         for(var4 = var3 + 1; var3 > var2 + 1; --var3) {
            if(var3 < var1.length() && var1.charAt(var3) == 32) {
               return var3 + 1;
            }
         }

         return var4;
      }
   }

   int widthOf(String var1) {
      return var1.length() == 0?0:(int)(new TextLayout(var1, this.font, this.renderContext)).getAdvance();
   }

   public Rectangle rect() {
      return new Rectangle(this.x, this.y, this.w, this.h + this.leftPointer.getHeight((ImageObserver)null));
   }

   public void paint(Graphics var1) {
      super.paint(var1);
      if(this.pointLeft) {
         var1.drawImage(this.leftPointer, this.x + 7, this.y + this.h - 3, (ImageObserver)null);
      } else {
         var1.drawImage(this.rightPointer, this.x - 9 + this.w - this.rightPointer.getWidth((ImageObserver)null), this.y + this.h - 3, (ImageObserver)null);
      }

      var1.setColor(new Color(0, 0, 0));
      var1.setFont(this.font);
      int var2 = this.y + this.fontSize + 8;

      for(int var3 = 0; var3 < this.lines.length; ++var3) {
         var1.drawString(this.lines[var3], this.x + this.xOffsets[var3], var2);
         var2 += this.fontSize + 2;
      }

   }
}
