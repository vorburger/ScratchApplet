// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:30
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Graphics;

class ListWatcherScrollBar {

   public static final int SCROLLBAR_WIDTH = 20;
   StretchyBox frameBox = new StretchyBox();
   StretchyBox nubBox;


   ListWatcherScrollBar() {
      this.frameBox.setFrameImage(Skin.vScrollFrame);
      this.nubBox = new StretchyBox();
      this.nubBox.setFrameImage(Skin.vScrollSlider);
   }

   public void paint(Graphics var1, int var2, int var3, int var4, int var5) {
      this.frameBox.x = var2 - 20;
      this.frameBox.y = var3;
      this.frameBox.w = 16;
      this.frameBox.h = var4 + 5;
      this.frameBox.paint(var1);
      this.nubBox.x = var2 - 20 + 2;
      this.nubBox.y = var5 + 2;
      this.nubBox.w = 12;
      if(this.nubBox.h < 23) {
         this.nubBox.h = 23;
      }

      this.nubBox.paint(var1);
   }
}
