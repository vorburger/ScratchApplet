// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:35
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.Iterator;
import java.util.Vector;

class SoundPlayer implements Runnable {

   static Vector activeSounds = new Vector();
   static Thread sndThread;


   static synchronized Object startSound(Object var0, Sprite var1, LContext var2) {
      if(!(var0 instanceof ScratchSound)) {
         Logo.error("argument of startSound must be a ScratchSound", var2);
         return new Object[0];
      } else {
         Object[] var3 = activeSounds.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            PlayingSound var5 = (PlayingSound)var3[var4];
            if(var5.snd == (ScratchSound)var0 && var5.sprite == var1) {
               var5.closeLine();
               activeSounds.remove(var5);
            }
         }

         PlayingSound var6 = new PlayingSound((ScratchSound)var0, var1);
         var6.startPlaying(var2);
         activeSounds.add(var6);
         return var6;
      }
   }

   static synchronized boolean isSoundPlaying(Object var0) {
      return !(var0 instanceof PlayingSound)?false:((PlayingSound)var0).isPlaying();
   }

   static synchronized void stopSound(Object var0) {
      if(var0 instanceof PlayingSound) {
         ((PlayingSound)var0).closeLine();
         activeSounds.remove(var0);
      }
   }

   static synchronized void stopSoundsForApplet(LContext var0) {
      PlayerPrims.stopMIDINotes();
      Vector var1 = new Vector();
      Iterator var2 = activeSounds.iterator();

      while(var2.hasNext()) {
         PlayingSound var3 = (PlayingSound)var2.next();
         if(var3.owner == var0) {
            var3.closeLine();
         } else {
            var1.addElement(var3);
         }
      }

      activeSounds = var1;
   }

   static synchronized void updateActiveSounds() {
      Vector var0 = new Vector();
      Iterator var1 = activeSounds.iterator();

      while(var1.hasNext()) {
         PlayingSound var2 = (PlayingSound)var1.next();
         if(var2.isPlaying()) {
            var2.writeSomeSamples();
            var0.addElement(var2);
         } else {
            var2.closeLine();
         }
      }

      activeSounds = var0;
   }

   static synchronized void startPlayer() {
      sndThread = new Thread(new SoundPlayer(), "SoundPlayer");
      sndThread.setPriority(10);
      sndThread.start();
   }

   public void run() {
      Thread var1 = Thread.currentThread();

      while(sndThread == var1) {
         updateActiveSounds();

         try {
            Thread.sleep(10L);
         } catch (InterruptedException var3) {
            ;
         }
      }

   }

}
