// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:30
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

class ListWatcherCell {

   Color CELL_COLOR = new Color(218, 77, 17);
   String contents;
   AttributedCharacterIterator ci;
   AttributedString as;
   LineBreakMeasurer lineMeasurer;
   FontRenderContext renderContext;
   BufferedImage contentsImage;
   int w;
   int h;
   int x;
   int y;


   ListWatcherCell(String var1, int var2) {
      this.contents = var1;
      this.w = var2;
      this.x = 0;
      this.y = 0;
      this.as = new AttributedString(var1);
      this.as.addAttribute(TextAttribute.FONT, ListWatcher.LABEL_FONT);
      this.ci = this.as.getIterator();
      Graphics2D var3 = Skin.bubbleFrame.createGraphics();
      this.renderContext = ((Graphics2D)var3).getFontRenderContext();
      this.h = 4;
      this.lineMeasurer = new LineBreakMeasurer(this.ci, this.renderContext);
      this.lineMeasurer.setPosition(this.ci.getBeginIndex());

      while(this.lineMeasurer.getPosition() < this.ci.getEndIndex()) {
         TextLayout var4 = this.lineMeasurer.nextLayout((float)(this.w - 10));
         this.h = (int)((float)this.h + var4.getAscent());
         this.h = (int)((float)this.h + var4.getDescent() + var4.getLeading());
      }

      this.contentsImage = new BufferedImage(this.w, this.h, 6);
      Graphics var8 = this.contentsImage.getGraphics();
      var8.setColor(Color.WHITE);
      Graphics2D var5 = (Graphics2D)var8;
      int var6 = this.y + 2;
      this.lineMeasurer.setPosition(this.ci.getBeginIndex());

      while(this.lineMeasurer.getPosition() < this.ci.getEndIndex()) {
         TextLayout var7 = this.lineMeasurer.nextLayout((float)(this.w - 10));
         var6 = (int)((float)var6 + var7.getAscent());
         var7.draw(var5, (float)(this.x + 6), (float)var6);
         var6 = (int)((float)var6 + var7.getDescent() + var7.getLeading());
      }

   }

   public void paint(Graphics var1) {
      var1.setColor(Color.WHITE);
      var1.fillRect(this.x + 2, this.y, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + this.h - 1, this.w - 4, 1);
      var1.fillRect(this.x, this.y + 2, 1, this.h - 4);
      var1.fillRect(this.x + this.w - 1, this.y + 2, 1, this.h - 4);
      var1.fillRect(this.x + 1, this.y + 1, 1, 1);
      var1.fillRect(this.x + this.w - 2, this.y + 1, 1, 1);
      var1.fillRect(this.x + 1, this.y + this.h - 2, 1, 1);
      var1.fillRect(this.x + this.w - 2, this.y + this.h - 2, 1, 1);
      var1.setColor(WatcherReadout.darker(WatcherReadout.darker(this.CELL_COLOR)));
      var1.fillRect(this.x + 2, this.y + 1, this.w - 4, 1);
      var1.fillRect(this.x + 1, this.y + 2, 1, this.h - 4);
      var1.setColor(WatcherReadout.darker(this.CELL_COLOR));
      var1.fillRect(this.x + 2, this.y + 2, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + this.h - 2, this.w - 4, 1);
      var1.fillRect(this.x + 2, this.y + 2, 1, this.h - 3);
      var1.fillRect(this.x + this.w - 2, this.y + 2, 1, this.h - 4);
      var1.setColor(this.CELL_COLOR);
      var1.fillRect(this.x + 3, this.y + 3, this.w - 5, this.h - 5);
      var1.drawImage(this.contentsImage, this.x, this.y, new Color(0, 0, 0, 1), (ImageObserver)null);
   }
}
