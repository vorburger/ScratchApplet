// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImageOp;

class AskPrompter extends StretchyBox {

   int fontSize = 11;
   Font font;
   Font answerFont;
   FontRenderContext renderContext;
   String promptString;
   String answerString;
   int lineX;
   int lineY;


   AskPrompter(String var1) {
      this.font = new Font("Arial Unicode MS", 1, this.fontSize);
      this.answerFont = new Font("Arial Unicode MS", 0, 13);
      this.promptString = "";
      this.answerString = "";
      this.renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
      this.setFrameImage(Skin.askBubbleFrame);
      this.setPrompt(var1);
   }

   void setPrompt(String var1) {
      int var2 = var1.length() == 0?1:2;
      this.promptString = var1;
      this.x = 15;
      this.w = 452;
      this.h = var2 * (this.fontSize + 4) + 22;
      this.y = 378 - this.h;
   }

   public Rectangle rect() {
      return new Rectangle(this.x, this.y, this.w, this.h);
   }

   public void paint(Graphics var1) {
      super.paint(var1);
      Graphics2D var2 = (Graphics2D)var1;
      this.lineX = this.x + 12;
      this.lineY = this.y + this.fontSize + 14;
      var2.setColor(new Color(0, 0, 0));
      var2.setFont(this.font);
      if(this.promptString.length() > 0) {
         var2.drawString(this.promptString, this.lineX - 2, this.lineY - 8);
         this.lineY += this.fontSize + 4;
      }

      var2.setStroke(new BasicStroke(2.0F));
      var2.setColor(new Color(213, 213, 213));
      var2.drawLine(this.lineX - 4, this.lineY - this.fontSize - 5, this.lineX - 4 + this.w - 38, this.lineY - this.fontSize - 5);
      var2.drawLine(this.lineX - 4, this.lineY - this.fontSize - 5, this.lineX - 4, this.lineY - this.fontSize - 5 + this.fontSize + 9);
      var2.setColor(new Color(242, 242, 242));
      var2.fillRect(this.lineX - 3, this.lineY - this.fontSize - 4, this.w - 39, this.fontSize + 8);
      Shape var3 = var1.getClip();
      var1.setClip(this.lineX - 3, this.lineY - this.fontSize - 4, this.w - 39, this.fontSize + 8);
      var2.setColor(new Color(0, 0, 0));
      var2.setFont(this.answerFont);
      var2.drawString(this.answerString, this.lineX, this.lineY - 1);
      var1.setClip(var3);
      var2.drawImage(Skin.promptCheckButton, (BufferedImageOp)null, this.lineX + this.w - 38, this.lineY - this.fontSize - 6);
   }

   public boolean mouseDown(int var1, int var2, PlayerCanvas var3) {
      if(var1 > this.lineX + this.w - 38 && var1 < this.lineX + this.w - 38 + 20 && var2 > this.lineY - this.fontSize - 6 && var2 < this.lineY - this.fontSize - 6 + 20) {
         var3.hideAskPrompt();
         return true;
      } else {
         return false;
      }
   }

   public void handleKeystroke(int var1, PlayerCanvas var2) {
      var2.inval(this.rect());
      if(var1 != 3 && var1 != 10) {
         if(var1 != 8 && var1 != 127) {
            char[] var3 = new char[]{(char)var1};
            if(var1 >= 32) {
               this.answerString = this.answerString + new String(var3);
            }

         } else {
            if(this.answerString.length() > 0) {
               this.answerString = this.answerString.substring(0, this.answerString.length() - 1);
            }

         }
      } else {
         var2.hideAskPrompt();
      }
   }
}
