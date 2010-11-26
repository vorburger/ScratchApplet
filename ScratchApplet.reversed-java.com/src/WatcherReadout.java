// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:37
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;

class WatcherReadout {

   int x;
   int y;
   int w = 40;
   int h = 14;
   Color color = new Color(100, 60, 20);
   String contents = "123";
   boolean isLarge = false;
   static final Font smallFont = new Font("Arial Unicode MS", 1, 10);
   static final Font bigFont = new Font("Arial Unicode MS", 1, 14);


   WatcherReadout(boolean var1) {
      this.beLarge(var1);
   }

   void beLarge(boolean var1) {
      if(this.isLarge != var1) {
         this.isLarge = var1;
         this.w = this.isLarge?50:40;
         this.h = this.isLarge?23:14;
      }
   }

   void adjustWidth(Graphics var1) {
      Font var2 = this.isLarge?bigFont:smallFont;
      this.w = Math.max(this.w, stringWidth(this.contents, var2, var1) + 12);
   }

   void paint(Graphics var1) {
      var1.setColor(Color.WHITE);
      var1.fillRect(this.x + 2, this.y, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + this.h - 1, this.w - 4, 1);
      var1.fillRect(this.x, this.y + 2, 1, this.h - 4);
      var1.fillRect(this.x + this.w - 1, this.y + 2, 1, this.h - 4);
      var1.fillRect(this.x + 1, this.y + 1, 1, 1);
      var1.fillRect(this.x + this.w - 2, this.y + 1, 1, 1);
      var1.fillRect(this.x + 1, this.y + this.h - 2, 1, 1);
      var1.fillRect(this.x + this.w - 2, this.y + this.h - 2, 1, 1);
      var1.setColor(darker(darker(this.color)));
      var1.fillRect(this.x + 2, this.y + 1, this.w - 4, 1);
      var1.fillRect(this.x + 1, this.y + 2, 1, this.h - 4);
      var1.setColor(darker(this.color));
      var1.fillRect(this.x + 2, this.y + 2, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + this.h - 2, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + 2, 1, this.h - 3);
      var1.fillRect(this.x + this.w - 2, this.y + 2, 1, this.h - 4);
      var1.setColor(this.color);
      var1.fillRect(this.x + 3, this.y + 3, this.w - 5, this.h - 5);
      Font var2 = this.isLarge?bigFont:smallFont;
      int var3 = this.isLarge?17:11;
      var1.setColor(Color.WHITE);
      var1.setFont(var2);
      var1.drawString(this.contents, this.x + (this.w - stringWidth(this.contents, var2, var1)) / 2 - 1, this.y + var3);
   }

   public static int stringWidth(String var0, Font var1, Graphics var2) {
      if(var0.length() == 0) {
         return 0;
      } else {
         TextLayout var3 = new TextLayout(var0, var1, ((Graphics2D)var2).getFontRenderContext());
         return (int)var3.getBounds().getBounds().getWidth();
      }
   }

   public static Color darker(Color var0) {
      double var1 = 0.8333D;
      return new Color((int)(var1 * (double)var0.getRed()), (int)(var1 * (double)var0.getGreen()), (int)(var1 * (double)var0.getBlue()));
   }

}
