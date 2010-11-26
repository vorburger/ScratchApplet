// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:37
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.image.ImageObserver;
import java.util.Hashtable;

class Watcher implements Drawable {

   static final Color black = new Color(0, 0, 0);
   static final Font labelFont = new Font("Arial Unicode MS", 1, 10);
   static final int NORMAL_MODE = 1;
   static final int SLIDER_MODE = 2;
   static final int LARGE_MODE = 3;
   PlayerCanvas canvas;
   StretchyBox box;
   WatcherReadout readout;
   StretchyBox slider;
   String label = "x";
   int mode = 1;
   double sliderMin = 0.0D;
   double sliderMax = 100.0D;
   boolean isDiscrete = false;
   boolean isShowing = true;


   Watcher(LContext var1) {
      if(var1 != null) {
         this.canvas = var1.canvas;
      }

      this.box = new StretchyBox();
      this.box.setFrameImage(Skin.watcherOuterFrame);
      this.box.x = 50;
      this.box.y = 50;
      this.readout = new WatcherReadout(false);
      this.readout.color = new Color(74, 107, 214);
      this.slider = new StretchyBox();
      this.slider.setFrameImage(Skin.sliderSlot);
      this.slider.h = 5;
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

   public void paint(Graphics var1) {
      int var2 = this.labelWidth(var1);
      this.readout.beLarge(this.mode == 3);
      this.readout.adjustWidth(var1);
      this.box.w = var2 + this.readout.w + 17;
      this.box.h = this.mode == 1?21:31;
      if(this.mode == 3) {
         this.readout.x = this.box.x;
         this.readout.y = this.box.y;
         this.readout.paint(var1);
      } else {
         this.box.paint(var1);
         this.drawLabel(var1);
         this.readout.x = this.box.x + var2 + 12;
         this.readout.y = this.box.y + 3;
         this.readout.paint(var1);
         if(this.mode == 2) {
            this.drawSlider(var1);
         }

      }
   }

   public void paintBubble(Graphics var1) {
   }

   public void mouseDown(int var1, int var2) {
   }

   void drawLabel(Graphics var1) {
      var1.setColor(black);
      var1.setFont(labelFont);
      var1.drawString(this.label, this.box.x + 6, this.box.y + 14);
   }

   int labelWidth(Graphics var1) {
      if(this.label.length() == 0) {
         return 0;
      } else {
         TextLayout var2 = new TextLayout(this.label, labelFont, ((Graphics2D)var1).getFontRenderContext());
         return (int)var2.getBounds().getBounds().getWidth();
      }
   }

   void drawSlider(Graphics var1) {
      this.slider.x = this.box.x + 6;
      this.slider.y = this.box.y + 21;
      this.slider.w = this.box.w - 12;
      this.slider.paint(var1);
      int var2 = (int)Math.round((double)(this.slider.w - 8) * ((this.getSliderValue() - this.sliderMin) / (this.sliderMax - this.sliderMin)));
      var2 = Math.max(0, Math.min(var2, this.slider.w - 8));
      var1.drawImage(Skin.sliderKnob, this.slider.x + var2 - 1, this.slider.y - 3, (ImageObserver)null);
   }

   boolean inSlider(int var1, int var2) {
      return this.mode != 2?false:(var2 >= this.slider.y - 1 && var2 <= this.slider.y + this.slider.h + 4?var1 >= this.slider.x - 4 && var1 <= this.slider.x + this.slider.w + 5:false);
   }

   public void dragTo(int var1, int var2) {
      double var3 = (double)(var1 - this.box.x - 10);
      this.setSliderValue(var3 * (this.sliderMax - this.sliderMin) / (double)(this.slider.w - 8) + this.sliderMin);
   }

   void click(int var1, int var2) {
      double var3 = this.getSliderValue();
      int var5 = this.slider.x + (int)Math.round((double)(this.slider.w - 8) * ((var3 - this.sliderMin) / (this.sliderMax - this.sliderMin))) + 5;
      int var6 = var1 < var5?-1:1;
      if(this.isDiscrete) {
         this.setSliderValue(var3 + (double)var6);
      } else {
         this.setSliderValue(var3 + (double)var6 * ((this.sliderMax - this.sliderMin) / 100.0D));
      }

   }

   double getSliderValue() {
      String var1 = Logo.prs(this.getObjProp(this, "param"));
      Hashtable var2 = this.getVarsTable();
      if(var1 != null && var2 != null) {
         Object var3 = var2.get(Logo.aSymbol(var1, this.canvas.lc));
         return var3 instanceof Number?((Number)var3).doubleValue():(this.sliderMin + this.sliderMax) / 2.0D;
      } else {
         return (this.sliderMin + this.sliderMax) / 2.0D;
      }
   }

   void setSliderValue(double var1) {
      double var3 = this.isDiscrete?(double)Math.round(var1):var1;
      var3 = Math.min(this.sliderMax, Math.max(this.sliderMin, var3));
      String var5 = Logo.prs(this.getObjProp(this, "param"));
      Hashtable var6 = this.getVarsTable();
      if(var5 != null && var6 != null) {
         var6.put(Logo.aSymbol(var5, this.canvas.lc), new Double(var3));
         this.inval();
      }
   }

   Hashtable getVarsTable() {
      String var1 = Logo.prs(this.getObjProp(this, "op"));
      String var2 = Logo.prs(this.getObjProp(this, "param"));
      Object var3 = this.getObjProp(this, "target");
      if(var3 != null && var1.equals("getVar:")) {
         Object var4 = this.getObjProp(var3, "vars");
         return var4 == null?null:(Hashtable)this.canvas.lc.props.get(var4);
      } else {
         return null;
      }
   }

   Object getObjProp(Object var1, String var2) {
      Hashtable var3 = (Hashtable)this.canvas.lc.props.get(var1);
      return var3 == null?null:var3.get(Logo.aSymbol(var2, this.canvas.lc));
   }

}
