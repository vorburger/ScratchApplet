// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

class PlayerPrims extends Primitives {

   static Synthesizer midiSynth;
   static boolean midiSynthInitialized = false;
   static int[] sensorValues = new int[]{0, 0, 0, 1000, 1000, 1000, 1000, 1000, 0, 0, 0, 0, 0, 0, 0, 0};
   static final String[] primlist = new String[]{"redrawall", "0", "redraw", "0", "drawrect", "4", "stage", "0", "setstage", "1", "sprites", "0", "setsprites", "1", "readprojfile", "1", "readprojurl", "0", "applet?", "0", "string?", "1", "sprite?", "1", "color?", "1", "mouseX", "0", "mouseY", "0", "mouseIsDown", "0", "mouseClick", "0", "keystroke", "0", "keydown?", "1", "clearkeys", "0", "midisetinstrument", "2", "midinoteon", "3", "midinoteoff", "2", "midicontrol", "3", "updatePenTrails", "0", "soundLevel", "0", "jpegDecode", "1", "memTotal", "0", "memFree", "0", "gc", "0", "clearall", "0", "requestFocus", "0", "setMessage", "1", "getSensorValue", "1", "autostart?", "0", "quoted?", "1", "unquote", "1"};
   static final String[] primclasses = new String[]{"SystemPrims", "MathPrims", "ControlPrims", "DefiningPrims", "WordListPrims", "FilePrims", "PlayerPrims", "SpritePrims"};


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         var3.canvas.redraw_all();
         return null;
      case 1:
         var3.canvas.redraw_invalid();
         return null;
      case 2:
         return this.prim_drawrect(var2[0], var2[1], var2[2], var2[3], var3);
      case 3:
         return var3.canvas.stage == null?new Object[0]:var3.canvas.stage;
      case 4:
         return this.prim_setstage(var2[0], var3);
      case 5:
         return var3.canvas.sprites;
      case 6:
         var3.canvas.sprites = (Object[])var2[0];
         return null;
      case 7:
         return this.prim_readprojfile(var2[0], var3);
      case 8:
         return this.prim_readprojurl(var3);
      case 9:
         return new Boolean(true);
      case 10:
         return new Boolean(var2[0] instanceof String || var2[0] instanceof Symbol);
      case 11:
         return new Boolean(var2[0] instanceof Sprite);
      case 12:
         return new Boolean(var2[0] instanceof Color);
      case 13:
         return new Double((double)var3.canvas.mouseX);
      case 14:
         return new Double((double)var3.canvas.mouseY);
      case 15:
         return new Boolean(var3.canvas.mouseIsDown);
      case 16:
         return this.prim_mouseclick(var3);
      case 17:
         return this.prim_keystroke(var3);
      case 18:
         return this.prim_keydown(var2[0], var3);
      case 19:
         var3.canvas.clearkeys();
         return null;
      case 20:
         return this.prim_midisetinstrument(var2[0], var2[1], var3);
      case 21:
         return this.prim_midinoteon(var2[0], var2[1], var2[2], var3);
      case 22:
         return this.prim_midinoteoff(var2[0], var2[1], var3);
      case 23:
         return this.prim_midicontrol(var2[0], var2[1], var2[2], var3);
      case 24:
         var3.canvas.updatePenTrails();
         return null;
      case 25:
         return new Double((double)var3.canvas.soundLevel());
      case 26:
         return this.prim_jpegDecode(var2[0], var3);
      case 27:
         return new Double((double)Runtime.getRuntime().totalMemory());
      case 28:
         return new Double((double)Runtime.getRuntime().freeMemory());
      case 29:
         Runtime.getRuntime().gc();
         return null;
      case 30:
         var3.canvas.clearall(var3);
         return null;
      case 31:
         var3.canvas.requestFocus();
         return null;
      case 32:
         var3.canvas.setMessage(Logo.aString(var2[0], var3));
         return null;
      case 33:
         return this.prim_getSensorValue(var2[0], var3);
      case 34:
         return new Boolean(var3.autostart);
      case 35:
         return new Boolean(var2[0] instanceof QuotedSymbol);
      case 36:
         return ((QuotedSymbol)var2[0]).sym;
      default:
         return null;
      }
   }

   static synchronized LContext startup(String var0, String var1, Container var2, boolean var3) {
      LContext var4 = new LContext();
      Logo.setupPrims(primclasses, var4);
      var4.codeBase = var0;
      var4.projectURL = var1;
      var4.autostart = var3;
      PlayerCanvas var5 = new PlayerCanvas();
      var5.lc = var4;
      var4.canvas = var5;
      Skin.readSkin(var5);
      SoundPlayer.startPlayer();
      var2.add(var5, "Center");
      LogoCommandRunner.startLogoThread("load \"startup startup", var4);
      return var4;
   }

   static synchronized void shutdown(LContext var0) {
      if(var0 != null) {
         LogoCommandRunner.stopLogoThread(var0);
         var0.canvas.clearall(var0);
      }

      SoundPlayer.stopSoundsForApplet(var0);
      sensorValues = new int[16];

      for(int var1 = 3; var1 < 8; ++var1) {
         sensorValues[var1] = 1000;
      }

   }

   static void stopMIDINotes() {
      if(midiSynth != null) {
         MidiChannel[] var0 = midiSynth.getChannels();

         for(int var1 = 0; var1 < var0.length; ++var1) {
            if(var0[var1] != null) {
               var0[var1].allNotesOff();
               var0[var1].programChange(0);
            }
         }

      }
   }

   Object prim_drawrect(Object var1, Object var2, Object var3, Object var4, LContext var5) {
      int var6 = Logo.anInt(var1, var5);
      int var7 = Logo.anInt(var2, var5);
      int var8 = Logo.anInt(var3, var5);
      int var9 = Logo.anInt(var4, var5);
      var5.canvas.drawRect(var6, var7, var8, var9);
      return null;
   }

   Object prim_setstage(Object var1, LContext var2) {
      if(!(var1 instanceof Sprite)) {
         return null;
      } else {
         Sprite var3 = (Sprite)var1;
         var3.x = var3.y = 0.0D;
         var2.canvas.stage = var3;
         return null;
      }
   }

   Object prim_readprojfile(Object var1, LContext var2) {
      Object[] var5 = new Object[0];
      var2.canvas.startLoading();

      FileInputStream var3;
      try {
         var3 = new FileInputStream(var1.toString());
      } catch (FileNotFoundException var9) {
         Logo.error("File not found: " + var1, var2);
         return var5;
      }

      var2.canvas.loadingProgress(0.5D);

      Object[][] var6;
      try {
         ObjReader var4 = new ObjReader(var3);
         var6 = var4.readObjects(var2);
         var3.close();
      } catch (IOException var8) {
         var8.printStackTrace();
         return var5;
      }

      var2.canvas.stopLoading();
      return var6;
   }

   Object prim_readprojurl(LContext var1) {
      Object[] var2 = new Object[0];
      if(var1.projectURL == null) {
         return var2;
      } else {
         URL var3;
         try {
            var3 = new URL(var1.projectURL);
         } catch (MalformedURLException var13) {
            Logo.error("Bad project URL: " + var1.projectURL, var1);
            return var2;
         }

         Object[][] var4;
         try {
            var1.canvas.startLoading();
            URLConnection var5 = var3.openConnection();
            var5.connect();
            int var6 = var5.getContentLength();
            byte[] var7 = new byte[var6];
            InputStream var8 = var5.getInputStream();
            int var9 = 0;
            int var10 = 0;

            while(var9 >= 0 && var10 < var6) {
               var9 = var8.read(var7, var10, var6 - var10);
               if(var9 > 0) {
                  var10 += var9;
                  var1.canvas.loadingProgress((double)var10 / (double)var6);
               } else {
                  try {
                     Thread.sleep(100L);
                  } catch (InterruptedException var12) {
                     ;
                  }
               }
            }

            var8.close();
            ObjReader var11 = new ObjReader(new ByteArrayInputStream(var7));
            var4 = var11.readObjects(var1);
         } catch (IOException var14) {
            Logo.error("Problem reading project from URL: " + var1.projectURL, var1);
            return var2;
         }

         var1.canvas.stopLoading();
         return var4;
      }
   }

   Object prim_mouseclick(LContext var1) {
      return var1.canvas.mouseclicks.isEmpty()?new Object[0]:var1.canvas.mouseclicks.remove(0);
   }

   Object prim_keystroke(LContext var1) {
      return var1.canvas.keystrokes.isEmpty()?new Object[0]:var1.canvas.keystrokes.remove(0);
   }

   Object prim_keydown(Object var1, LContext var2) {
      int var3 = Logo.anInt(var1, var2);
      return var3 > 255?new Boolean(false):new Boolean(var2.canvas.keydown[var3]);
   }

   Object prim_midisetinstrument(Object var1, Object var2, LContext var3) {
      this.init_midi(var3);
      if(midiSynth == null) {
         return null;
      } else {
         int var4 = Logo.anInt(var1, var3) - 1 & 15;
         int var5 = Logo.anInt(var2, var3) - 1 & 127;
         midiSynth.getChannels()[var4].programChange(var5);
         return null;
      }
   }

   Object prim_midinoteon(Object var1, Object var2, Object var3, LContext var4) {
      this.init_midi(var4);
      if(midiSynth == null) {
         return null;
      } else {
         int var5 = Logo.anInt(var1, var4) - 1 & 15;
         int var6 = Logo.anInt(var2, var4) & 127;
         int var7 = Logo.anInt(var3, var4) & 127;
         midiSynth.getChannels()[var5].noteOn(var6, var7);
         return null;
      }
   }

   Object prim_midinoteoff(Object var1, Object var2, LContext var3) {
      this.init_midi(var3);
      if(midiSynth == null) {
         return null;
      } else {
         int var4 = Logo.anInt(var1, var3) - 1 & 15;
         int var5 = Logo.anInt(var2, var3) & 127;
         midiSynth.getChannels()[var4].noteOff(var5);
         return null;
      }
   }

   Object prim_midicontrol(Object var1, Object var2, Object var3, LContext var4) {
      this.init_midi(var4);
      if(midiSynth == null) {
         return null;
      } else {
         int var5 = Logo.anInt(var1, var4) - 1 & 15;
         int var6 = Logo.anInt(var2, var4) & 127;
         int var7 = Logo.anInt(var3, var4) & 127;
         midiSynth.getChannels()[var5].controlChange(var6, var7);
         return null;
      }
   }

   void init_midi(LContext var1) {
      if(!midiSynthInitialized) {
         midiSynthInitialized = true;

         try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();
            if(midiSynth.getDefaultSoundbank() == null) {
               var1.canvas.setMessage("Reading sound bank from server. Please wait...");
               URL var2 = new URL(var1.codeBase + "soundbank.gm");
               Soundbank var3 = MidiSystem.getSoundbank(var2);
               if(var3 != null) {
                  midiSynth.loadAllInstruments(var3);
                  var1.canvas.setMessage("");
               } else {
                  midiSynth.close();
                  midiSynth = null;
               }
            }
         } catch (MidiUnavailableException var4) {
            var4.printStackTrace();
            midiSynth = null;
         } catch (MalformedURLException var5) {
            var5.printStackTrace();
            midiSynth = null;
         } catch (InvalidMidiDataException var6) {
            var6.printStackTrace();
            midiSynth = null;
         } catch (IOException var7) {
            var7.printStackTrace();
            midiSynth = null;
         } catch (AccessControlException var8) {
            var8.printStackTrace();
            midiSynth = null;
         }

         if(midiSynth != null) {
            MidiChannel[] var9 = midiSynth.getChannels();

            for(int var10 = 0; var10 < var9.length; ++var10) {
               if(var9[var10] != null) {
                  var9[var10].programChange(0);
               }
            }
         } else {
            var1.canvas.setMessage("No soundbank; note & drum commands disabled.");
         }

      }
   }

   Object prim_jpegDecode(Object var1, LContext var2) {
      if(!(var1 instanceof byte[])) {
         return null;
      } else {
         byte[] var3 = (byte[])var1;
         Toolkit var4 = Toolkit.getDefaultToolkit();
         Image var5 = var4.createImage(var3);
         MediaTracker var6 = new MediaTracker(var2.canvas);
         var6.addImage(var5, 0);

         try {
            var6.waitForID(0);
         } catch (InterruptedException var11) {
            ;
         }

         if(var5 == null) {
            return null;
         } else {
            int var7 = var5.getWidth((ImageObserver)null);
            int var8 = var5.getHeight((ImageObserver)null);
            BufferedImage var9 = new BufferedImage(var7, var8, 2);
            Graphics var10 = var9.getGraphics();
            var10.drawImage(var5, 0, 0, var7, var8, (ImageObserver)null);
            var10.dispose();
            return var9;
         }
      }
   }

   Object prim_getSensorValue(Object var1, LContext var2) {
      int var3 = Logo.anInt(var1, var2) - 1;
      return var3 >= 0 && var3 <= 15?new Double((double)sensorValues[var3] / 10.0D):new Double(123.0D);
   }

}
