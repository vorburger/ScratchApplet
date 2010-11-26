// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:36
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.text.DecimalFormat;
import java.util.Hashtable;

class SpritePrims extends Primitives {

   static final String[] primlist = new String[]{"who", "0", "talkto", "1", "xpos", "0", "ypos", "0", "setx", "1", "sety", "1", "%left", "0", "%top", "0", "%right", "0", "%bottom", "0", "%setleft", "1", "%settop", "1", "%w", "0", "%h", "0", "costume", "0", "setcostume", "3", "%scale", "0", "setscale", "1", "heading", "0", "setheading", "1", "rotationstyle", "0", "setrotationstyle", "1", "show", "0", "hide", "0", "changed", "0", "containsPoint?", "2", "alpha", "0", "setalpha", "1", "color", "0", "setcolor", "1", "brightness", "0", "setbrightness", "1", "fisheye", "0", "setfisheye", "1", "whirl", "0", "setwhirl", "1", "mosaic", "0", "setmosaic", "1", "pixelate", "0", "setpixelate", "1", "beep", "0", "startSound", "1", "isSoundPlaying?", "1", "stopSound", "1", "stopAllSounds", "0", "setPenDown", "1", "setPenColor", "1", "penSize", "0", "setPenSize", "1", "penHue", "0", "setPenHue", "1", "penShade", "0", "setPenShade", "1", "clearPenTrails", "0", "stampCostume", "0", "newcolor", "3", "touchingSprite?", "1", "touchingColor?", "1", "colorTouchingColor?", "2", "isShowing", "1", "talkbubble", "1", "thinkbubble", "1", "updateBubble", "0", "watcher?", "1", "setWatcherXY", "3", "setWatcherColorAndLabel", "3", "setWatcherSliderMinMax", "3", "setWatcherMode", "2", "setWatcherText", "2", "isDraggable", "0", "setDraggable", "1", "showWatcher", "1", "hideWatcher", "1", "listContents", "1", "hasKey", "2", "listWatcher?", "1", "newListWatcher", "0", "setListWatcherXY", "3", "setListWatcherWidthHeight", "3", "setListWatcherLabel", "2", "setListWatcherList", "2", "highlightListWatcherIndex", "2", "clearListWatcherHighlights", "1", "askbubble", "1", "showAskPrompt", "1", "hideAskPrompt", "0", "askPromptShowing?", "0", "lastAnswer", "0", "isVisible", "1", "newVarWatcher", "1", "watcherX", "1", "watcherY", "1"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      Sprite var4 = var3.who;
      switch(var1) {
      case 0:
         return var4 == null?new Object[0]:var4;
      case 1:
         var3.who = var2[0] instanceof Sprite?(Sprite)var2[0]:null;
         return null;
      case 2:
         return new Double(var4 == null?0.0D:var4.x);
      case 3:
         return new Double(var4 == null?0.0D:var4.y);
      case 4:
         var4.x = Logo.aDouble(var2[0], var3);
         return null;
      case 5:
         var4.y = Logo.aDouble(var2[0], var3);
         return null;
      case 6:
         return new Double((double)var4.screenX());
      case 7:
         return new Double((double)var4.screenY());
      case 8:
         return new Double((double)(var4.screenX() + var4.outImage().getWidth((ImageObserver)null)));
      case 9:
         return new Double((double)(var4.screenY() + var4.outImage().getHeight((ImageObserver)null)));
      case 10:
         var4.setscreenX(Logo.aDouble(var2[0], var3));
         return null;
      case 11:
         var4.setscreenY(Logo.aDouble(var2[0], var3));
         return null;
      case 12:
         return new Double((double)var4.outImage().getWidth((ImageObserver)null));
      case 13:
         return new Double((double)var4.outImage().getHeight((ImageObserver)null));
      case 14:
         return var4.costume;
      case 15:
         var4.setcostume(var2[0], var2[1], var2[2], var3);
         var4.costumeChanged();
         return null;
      case 16:
         return new Double(var4.scale);
      case 17:
         var4.setscale(var2[0], var3);
         return null;
      case 18:
         return this.prim_heading(var3);
      case 19:
         var4.rotationDegrees = Logo.aDouble(var2[0], var3) % 360.0D;
         var4.costumeChanged();
         return null;
      case 20:
         return new Double((double)var4.rotationstyle);
      case 21:
         var4.rotationstyle = Logo.anInt(var2[0], var3);
         var4.costumeChanged();
         return null;
      case 22:
         var4.show();
         return null;
      case 23:
         var4.hide();
         return null;
      case 24:
         var4.inval();
         return null;
      case 25:
         return this.prim_containsPoint(var2[0], var2[1], var3);
      case 26:
         return new Double(var4.alpha);
      case 27:
         var4.setalpha(var2[0], var3);
         return null;
      case 28:
         return new Double(var4.color);
      case 29:
         var4.color = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 30:
         return new Double(var4.brightness);
      case 31:
         var4.brightness = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 32:
         return new Double(var4.fisheye);
      case 33:
         var4.fisheye = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 34:
         return new Double(var4.whirl);
      case 35:
         var4.whirl = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 36:
         return new Double(var4.mosaic);
      case 37:
         var4.mosaic = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 38:
         return new Double(var4.pixelate);
      case 39:
         var4.pixelate = Logo.aDouble(var2[0], var3);
         var4.filterChanged = true;
         return null;
      case 40:
         Toolkit.getDefaultToolkit().beep();
         return null;
      case 41:
         return SoundPlayer.startSound(var2[0], var4, var3);
      case 42:
         return new Boolean(SoundPlayer.isSoundPlaying(var2[0]));
      case 43:
         SoundPlayer.stopSound(var2[0]);
         return null;
      case 44:
         SoundPlayer.stopSoundsForApplet(var3);
         return null;
      case 45:
         var4.setPenDown(Logo.aBoolean(var2[0], var3));
         return null;
      case 46:
         if(var2[0] instanceof Color) {
            var4.setPenColor((Color)var2[0]);
         }

         return null;
      case 47:
         return new Double((double)var4.penSize);
      case 48:
         var4.penSize = Logo.anInt(var2[0], var3);
         return null;
      case 49:
         return new Double(var4.penHue);
      case 50:
         var4.setPenHue(Logo.aDouble(var2[0], var3));
         return null;
      case 51:
         return new Double(var4.penShade);
      case 52:
         var4.setPenShade(Logo.aDouble(var2[0], var3));
         return null;
      case 53:
         var3.canvas.clearPenTrails();
         return null;
      case 54:
         var3.canvas.stampCostume(var4);
         return null;
      case 55:
         return this.prim_newcolor(var2[0], var2[1], var2[2], var3);
      case 56:
         return new Boolean(var4.touchingSprite(var2[0], var3));
      case 57:
         return new Boolean(var4.touchingColor(var2[0], var3));
      case 58:
         return new Boolean(var4.colorTouchingColor(var2[0], var2[1], var3));
      case 59:
         return new Boolean(var2[0] instanceof Sprite && ((Sprite)var2[0]).isShowing());
      case 60:
         var4.talkbubble(var2[0], false, true, var3);
         return null;
      case 61:
         var4.talkbubble(var2[0], false, false, var3);
         return null;
      case 62:
         var4.updateBubble();
         return null;
      case 63:
         return new Boolean(var2[0] instanceof Watcher);
      case 64:
         this.prim_setWatcherXY(var2[0], var2[1], var2[2], var3);
         return null;
      case 65:
         this.prim_setWatcherColorAndLabel(var2[0], var2[1], var2[2], var3);
         return null;
      case 66:
         this.prim_setWatcherSliderMinMax(var2[0], var2[1], var2[2], var3);
         return null;
      case 67:
         this.prim_setWatcherMode(var2[0], var2[1], var3);
         return null;
      case 68:
         this.prim_setWatcherText(var2[0], var2[1], var3);
         return null;
      case 69:
         return new Boolean(var4.isDraggable);
      case 70:
         var4.isDraggable = Logo.aBoolean(var2[0], var3);
         return null;
      case 71:
         ((Watcher)var2[0]).show();
         return null;
      case 72:
         ((Watcher)var2[0]).hide();
         return null;
      case 73:
         return this.prim_listContents(var2[0], var3);
      case 74:
         return this.prim_hasKey(var2[0], var2[1], var3);
      case 75:
         return new Boolean(var2[0] instanceof ListWatcher);
      case 76:
         return this.prim_newListWatcher(var3);
      case 77:
         this.prim_setListWatcherXY(var2[0], var2[1], var2[2], var3);
         return null;
      case 78:
         this.prim_setListWatcherWidthHeight(var2[0], var2[1], var2[2], var3);
         return null;
      case 79:
         this.prim_setListWatcherLabel(var2[0], var2[1], var3);
         return null;
      case 80:
         this.prim_setListWatcherList(var2[0], var2[1], var3);
         return null;
      case 81:
         this.prim_highlightListWatcherIndex(var2[0], var2[1], var3);
         return null;
      case 82:
         this.prim_clearListWatcherHighlights(var2[0], var3);
         return null;
      case 83:
         var4.talkbubble(var2[0], true, true, var3);
         return null;
      case 84:
         var3.canvas.showAskPrompt(Logo.aString(var2[0], var3));
         return null;
      case 85:
         var3.canvas.hideAskPrompt();
         return null;
      case 86:
         return new Boolean(var3.canvas.askPromptShowing());
      case 87:
         return var3.canvas.lastAnswer;
      case 88:
         return new Boolean(var2[0] instanceof Sprite && ((Sprite)var2[0]).isVisible());
      case 89:
         return this.prim_newVarWatcher(var2[0], var3);
      case 90:
         return this.prim_watcherX(var2[0], var3);
      case 91:
         return this.prim_watcherY(var2[0], var3);
      default:
         return null;
      }
   }

   Object prim_heading(LContext var1) {
      double var2 = var1.who.rotationDegrees % 360.0D;
      if(var2 > 180.0D) {
         var2 -= 360.0D;
      }

      return new Double(var2);
   }

   Object prim_containsPoint(Object var1, Object var2, LContext var3) {
      if(var3.who == null) {
         return new Boolean(false);
      } else {
         int var4 = Logo.anInt(var1, var3) + 241;
         int var5 = 206 - Logo.anInt(var2, var3);
         return new Boolean(var3.who.containsPoint(var4, var5));
      }
   }

   Color prim_newcolor(Object var1, Object var2, Object var3, LContext var4) {
      int var5 = Logo.anInt(var1, var4);
      int var6 = Logo.anInt(var2, var4);
      int var7 = Logo.anInt(var3, var4);
      return new Color(var5, var6, var7);
   }

   void prim_setWatcherXY(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof Watcher) {
         Watcher var5 = (Watcher)var1;
         var5.inval();
         var5.box.x = Logo.anInt(var2, var4);
         var5.box.y = Logo.anInt(var3, var4);
         var5.inval();
      }
   }

   void prim_setWatcherColorAndLabel(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof Watcher) {
         Watcher var5 = (Watcher)var1;
         var5.inval();
         var5.readout.color = (Color)var2;
         var5.label = (String)var3;
         var5.inval();
      }
   }

   void prim_setWatcherSliderMinMax(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof Watcher) {
         Watcher var5 = (Watcher)var1;
         var5.sliderMin = Logo.aDouble(var2, var4);
         var5.sliderMax = Logo.aDouble(var3, var4);
         var5.isDiscrete = (double)Math.round(var5.sliderMin) == var5.sliderMin && (double)Math.round(var5.sliderMax) == var5.sliderMax;
      }
   }

   void prim_setWatcherMode(Object var1, Object var2, LContext var3) {
      if(var1 instanceof Watcher) {
         Watcher var4 = (Watcher)var1;
         int var5 = Logo.anInt(var2, var3);
         var4.inval();
         var4.mode = Math.max(0, Math.min(var5, 3));
         var4.inval();
      }
   }

   void prim_setWatcherText(Object var1, Object var2, LContext var3) {
      if(var1 instanceof Watcher) {
         Watcher var4 = (Watcher)var1;
         String var5 = Logo.prs(var2);
         if(var2 instanceof Double) {
            double var6 = ((Double)var2).doubleValue();
            double var8 = Math.abs(var6);
            DecimalFormat var10 = new DecimalFormat("0.#");
            if(var8 < 1.0D) {
               var10 = new DecimalFormat("0.######");
            }

            if(var8 < 1.0E-5D || var8 >= 1000000.0D) {
               var10 = new DecimalFormat("0.###E0");
            }

            if(var8 == 0.0D) {
               var10 = new DecimalFormat("0.#");
            }

            var5 = var10.format(var6);
         }

         if(!var5.equals(var4.readout.contents)) {
            var4.inval();
            var4.readout.contents = var5;
            var4.inval();
         }
      }
   }

   Object prim_listContents(Object var1, LContext var2) {
      if(!(var1 instanceof Object[])) {
         Logo.error("argument must be a list", var2);
         return "";
      } else {
         Object[] var3 = (Object[])var1;
         if(var3.length == 0) {
            return "";
         } else {
            StringBuffer var4 = new StringBuffer(1000);
            boolean var5 = false;

            int var6;
            Object var7;
            for(var6 = 0; var6 < var3.length; ++var6) {
               var7 = var3[var6];
               if(!(var7 instanceof String)) {
                  var7 = Logo.prs(var7);
               }

               if(((String)var7).length() > 1) {
                  var5 = true;
               }
            }

            for(var6 = 0; var6 < var3.length; ++var6) {
               var7 = var3[var6];
               if(!(var7 instanceof String)) {
                  var7 = Logo.prs(var7);
               }

               var4.append(var7);
               if(var5) {
                  var4.append(" ");
               }
            }

            if(var5) {
               var4.deleteCharAt(var4.length() - 1);
            }

            return var4.toString();
         }
      }
   }

   Object prim_hasKey(Object var1, Object var2, LContext var3) {
      Hashtable var4 = (Hashtable)var3.props.get(var1);
      return var4 == null?new Boolean(false):new Boolean(var4.containsKey(var2));
   }

   Object prim_newListWatcher(LContext var1) {
      ListWatcher var2 = new ListWatcher(var1);
      Object[] var3 = new Object[var1.canvas.sprites.length + 1];

      for(int var4 = 0; var4 < var1.canvas.sprites.length; ++var4) {
         var3[var4] = var1.canvas.sprites[var4];
      }

      var3[var3.length - 1] = var2;
      var1.canvas.sprites = var3;
      return var2;
   }

   void prim_setListWatcherXY(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof ListWatcher) {
         ListWatcher var5 = (ListWatcher)var1;
         var5.inval();
         var5.box.x = Logo.anInt(var2, var4);
         var5.box.y = Logo.anInt(var3, var4);
         var5.inval();
      }
   }

   void prim_setListWatcherWidthHeight(Object var1, Object var2, Object var3, LContext var4) {
      if(var1 instanceof ListWatcher) {
         ListWatcher var5 = (ListWatcher)var1;
         var5.inval();
         var5.box.w = Logo.anInt(var2, var4);
         var5.box.h = Logo.anInt(var3, var4);
         var5.scrollBarHeight = var5.box.h - 23 - 20;
         var5.pane.w = var5.box.w;
         var5.inval();
      }
   }

   void prim_setListWatcherLabel(Object var1, Object var2, LContext var3) {
      if(var1 instanceof ListWatcher) {
         ListWatcher var4 = (ListWatcher)var1;
         var4.inval();
         var4.listTitle = Logo.aString(var2, var3);
         var4.inval();
      }
   }

   void prim_setListWatcherList(Object var1, Object var2, LContext var3) {
      if(var1 instanceof ListWatcher) {
         if(var2 instanceof Object[]) {
            ListWatcher var4 = (ListWatcher)var1;
            var4.setList((Object[])var2);
            var4.inval();
         }
      }
   }

   void prim_highlightListWatcherIndex(Object var1, Object var2, LContext var3) {
      if(var1 instanceof ListWatcher) {
         ListWatcher var4 = (ListWatcher)var1;
         var4.highlightIndex(Logo.anInt(var2, var3));
         var4.inval();
      }
   }

   void prim_clearListWatcherHighlights(Object var1, LContext var2) {
      if(var1 instanceof ListWatcher) {
         ListWatcher var3 = (ListWatcher)var1;
         var3.clearHighlights();
         var3.inval();
      }
   }

   Watcher prim_newVarWatcher(Object var1, LContext var2) {
      return new Watcher(var2);
   }

   Object prim_watcherX(Object var1, LContext var2) {
      return var1 instanceof Watcher?new Double((double)((Watcher)var1).box.x):(var1 instanceof ListWatcher?new Double((double)((ListWatcher)var1).box.x):new Double(0.0D));
   }

   Object prim_watcherY(Object var1, LContext var2) {
      return var1 instanceof Watcher?new Double((double)((Watcher)var1).box.y):(var1 instanceof ListWatcher?new Double((double)((ListWatcher)var1).box.y):new Double(0.0D));
   }

}
