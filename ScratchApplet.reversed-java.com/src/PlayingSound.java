// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;

class PlayingSound {

   LContext owner;
   ScratchSound snd;
   Sprite sprite;
   SourceDataLine line;
   int i;
   static Class class$javax$sound$sampled$SourceDataLine;


   PlayingSound(ScratchSound var1, Sprite var2) {
      this.snd = var1;
      this.sprite = var2;
   }

   void startPlaying(LContext var1) {
      this.owner = var1;
      this.i = 0;
      if(this.line == null) {
         this.openLine();
      }

      this.writeSomeSamples();
   }

   boolean isPlaying() {
      return this.line == null?false:this.line.getFramePosition() < this.snd.samples.length / 2;
   }

   void writeSomeSamples() {
      if(this.line != null) {
         int var1 = Math.min(this.line.available(), this.snd.samples.length - this.i);
         if(var1 > 0) {
            if(!this.line.isRunning()) {
               this.line.start();
            }

            int var2 = this.line.write(this.snd.samples, this.i, var1);
            this.i += var2;
         }

      }
   }

   void openLine() {
      try {
         AudioFormat var1 = new AudioFormat((float)this.snd.rate, 16, 1, true, true);
         this.line = (SourceDataLine)AudioSystem.getLine(new Info(class$javax$sound$sampled$SourceDataLine == null?(class$javax$sound$sampled$SourceDataLine = class$("javax.sound.sampled.SourceDataLine")):class$javax$sound$sampled$SourceDataLine, var1));
         this.line.open(var1);
      } catch (LineUnavailableException var2) {
         this.line = null;
         return;
      }

      this.line.start();
   }

   void closeLine() {
      if(this.line != null) {
         this.line.close();
         this.line = null;
      }

   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
