// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:30
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

class ListWatcher implements Drawable {

   public static final Font LABEL_FONT = new Font("Arial Unicode MS", 1, 10);
   public static final Font LABEL_FONT_SMALL = new Font("Arial Unicode MS", 0, 10);
   public static final Font CELL_NUM_FONT = new Font("Arial Unicode MS", 0, 9);
   public static final int TOP_MARGIN = 23;
   public static final int BOTTOM_MARGIN = 20;
   public static final int LEFT_MARGIN = 5;
   public static final int RIGHT_MARGIN = 5;
   LContext lc;
   PlayerCanvas canvas;
   StretchyBox box;
   ListWatcherPane pane;
   ListWatcherScrollBar scrollBar;
   String listTitle = "listTitle";
   Object[] list = null;
   ArrayList highlightedIndices = new ArrayList();
   int mouseOffset = 0;
   int paneHeight = 0;
   int scroll = 0;
   int scrollForIndex = 0;
   int scrollBarHeight = 0;
   float scrollRatio = 0.0F;
   boolean isShowing = true;
   boolean useScrollForIndex = false;


   ListWatcher(LContext var1) {
      if(var1 != null) {
         this.canvas = var1.canvas;
         this.lc = var1;
      }

      this.box = new StretchyBox();
      this.box.setFrameImage(Skin.listWatcherOuterFrame);
      this.pane = new ListWatcherPane(this);
   }

   public void setList(Object[] var1) {
      this.list = var1;
      this.pane.setList(this.list);
      this.paneHeight = this.pane.totalHeight;
      this.initScrollBar();
   }

   public void initScrollBar() {
      this.scrollBarHeight = this.box.h - 23 - 20;
      this.scrollRatio = (float)this.paneHeight / (float)this.scrollBarHeight;
      if((double)this.scrollRatio < 1.0D) {
         this.scrollRatio = 0.0F;
         this.scrollBar = null;
      } else {
         if(this.scrollBar == null) {
            this.scrollBar = new ListWatcherScrollBar();
         }

         this.scrollBar.nubBox.h = (int)((float)this.scrollBarHeight / this.scrollRatio);
         if(this.scrollBar.nubBox.h < 23) {
            this.scrollBar.nubBox.h = 23;
            this.scrollRatio = (float)((double)((float)this.paneHeight) / ((double)((float)this.scrollBarHeight) - 23.0D));
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

   public Rectangle rect() {
      return new Rectangle(this.box.x, this.box.y, this.box.w, this.box.h);
   }

   public Rectangle fullRect() {
      return this.rect();
   }

   void inval() {
      this.canvas.inval(this.rect());
   }

   public void paintBubble(Graphics var1) {
   }

   public void paint(Graphics var1) {
      this.box.paint(var1);
      var1.setColor(Color.BLACK);
      var1.setFont(LABEL_FONT);
      var1.drawString(this.listTitle, this.box.x + (this.box.w - WatcherReadout.stringWidth(this.listTitle, LABEL_FONT, var1)) / 2, this.box.y + 23 - 8);
      if(this.scrollBar != null) {
         this.scrollBar.paint(var1, this.box.x + this.box.w, this.box.y + 23, this.scrollBarHeight, this.box.y + 23 + this.scroll);
      }

      Graphics var2 = var1.create();
      this.pane.paint(var2, this.box.x + 5, this.useScrollForIndex?this.scrollForIndex:(int)((float)this.scroll * this.scrollRatio), this.box.y + 23, this.scrollBarHeight);
      var1.setColor(Color.BLACK);
      var1.setFont(LABEL_FONT_SMALL);
      String var3 = "length: " + this.list.length;
      var1.drawString(var3, this.box.x + (this.box.w - WatcherReadout.stringWidth(var3, LABEL_FONT_SMALL, var1)) / 2, this.box.y + this.box.h - 5);
   }

   boolean inScrollbar(int var1, int var2) {
      return this.scrollBar != null?(var1 >= this.box.x + this.box.w - 20 && var1 <= this.box.x + this.box.w?var2 >= this.box.y + 23 + this.scroll && var2 <= this.box.y + 23 + this.scroll + this.scrollBar.nubBox.h:false):false;
   }

   public void mouseDown(int var1, int var2) {
      if(this.scrollBar != null) {
         this.mouseOffset = var2 - (this.box.y + 23) - this.scroll;
      }

   }

   public void dragTo(int var1, int var2) {
      if(this.scrollBar != null) {
         this.setScroll(var2 - this.mouseOffset - (this.box.y + 23));
      }

   }

   void setScroll(int var1) {
      if(this.scrollBar != null) {
         if(var1 < 0) {
            this.scroll = 0;
         } else if(var1 > this.scrollBarHeight - this.scrollBar.nubBox.h) {
            this.scroll = this.scrollBarHeight - this.scrollBar.nubBox.h;
         } else {
            this.scroll = var1;
         }

         this.useScrollForIndex = false;
         this.inval();
      }

   }

   void setScrollForHighlightIndex(int var1) {
      if(this.scrollBar != null) {
         this.scrollForIndex = this.pane.getYPositionAtIndex(var1) - this.scrollBarHeight / 2;
         this.useScrollForIndex = true;
         int var2 = (int)((float)this.scrollForIndex / this.scrollRatio);
         if(var2 < 0) {
            this.scroll = 0;
         } else if(var2 > this.scrollBarHeight - this.scrollBar.nubBox.h) {
            this.scroll = this.scrollBarHeight - this.scrollBar.nubBox.h;
         } else {
            this.scroll = var2;
         }

         this.inval();
      }

   }

   void highlightIndex(int var1) {
      if(var1 >= 1 && var1 <= this.list.length) {
         this.setScrollForHighlightIndex(var1);
         this.highlightedIndices.add(new Integer(var1));
         this.box.setFrameImage(Skin.listWatcherOuterFrame);
      } else {
         this.box.setFrameImage(Skin.listWatcherOuterFrameError);
      }

   }

   void clearHighlights() {
      this.highlightedIndices = new ArrayList();
      this.box.setFrameImage(Skin.listWatcherOuterFrame);
   }

}
