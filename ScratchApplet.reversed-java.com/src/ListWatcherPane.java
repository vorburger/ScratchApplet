// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:30
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

class ListWatcherPane {

   static final int CELL_MARGIN = 20;
   static final int CELL_WIDTH = 42;
   static final int CELL_HEIGHT = 21;
   Object[] list;
   ArrayList cells = new ArrayList();
   ListWatcher ownerListWatcher;
   int w = 0;
   int maxIndexWidth = 0;
   int totalHeight = 0;


   ListWatcherPane(ListWatcher var1) {
      this.ownerListWatcher = var1;
   }

   public void setList(Object[] var1) {
      this.list = var1;
      this.cells = new ArrayList();
      this.totalHeight = 0;
      this.maxIndexWidth = this.maxIndexWidth(Skin.bubbleFrame.createGraphics());

      for(int var2 = 0; var2 < var1.length; ++var2) {
         ListWatcherCell var3 = new ListWatcherCell(this.list[var2].toString(), this.w - 20 - 5 - this.maxIndexWidth - 5);
         this.cells.add(var3);
         this.totalHeight += var3.h;
      }

   }

   public void paint(Graphics var1, int var2, int var3, int var4, int var5) {
      int var6 = var4 - var3;
      if(var6 <= var4 && var3 != 0 && this.totalHeight >= var5) {
         if(var6 < var4 - (this.totalHeight - var5)) {
            var6 = var4 - (this.totalHeight - var5);
         }
      } else {
         var6 = var4;
      }

      var1.setClip(var2, var4, this.w - 20 - 5, var5);
      var1.setFont(ListWatcher.CELL_NUM_FONT);
      if(this.list != null) {
         for(int var7 = 0; var7 < this.cells.size(); ++var7) {
            ListWatcherCell var8 = (ListWatcherCell)this.cells.get(var7);
            var8.x = var2 + this.maxIndexWidth + 3;
            var8.y = var6 + 2;
            var8.paint(var1);
            if(this.ownerListWatcher.highlightedIndices.contains(new Integer(var7 + 1))) {
               var1.setColor(Color.WHITE);
            } else {
               var1.setColor(new Color(60, 60, 60));
            }

            String var9 = Integer.toString(var7 + 1);
            var1.drawString(var9, var2 + (this.maxIndexWidth - WatcherReadout.stringWidth(var9, ListWatcher.CELL_NUM_FONT, var1)) / 2, var6 + (int)((float)var8.h / 2.0F) + 5);
            var6 += var8.h;
         }

      }
   }

   public int getYPositionAtIndex(int var1) {
      if(this.cells.size() <= 0) {
         return 0;
      } else {
         int var2 = 0;

         for(int var3 = 0; var3 < var1; ++var3) {
            var2 += ((ListWatcherCell)this.cells.get(var3)).h;
         }

         return var2;
      }
   }

   int maxIndexWidth(Graphics var1) {
      double var2 = 0.0D;

      for(int var4 = 1; var4 < this.list.length + 1; ++var4) {
         var2 = Math.max(var2, (double)WatcherReadout.stringWidth(Integer.toString(var4), ListWatcher.LABEL_FONT, var1));
      }

      return (int)var2;
   }
}
