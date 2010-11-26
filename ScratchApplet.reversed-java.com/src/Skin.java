// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:35
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

class Skin {

   static boolean skinInitialized;
   static BufferedImage askBubbleFrame;
   static BufferedImage askPointerL;
   static BufferedImage askPointerR;
   static BufferedImage bubbleFrame;
   static BufferedImage goButton;
   static BufferedImage goButtonOver;
   static BufferedImage promptCheckButton;
   static BufferedImage sliderKnob;
   static BufferedImage sliderSlot;
   static BufferedImage stopButton;
   static BufferedImage stopButtonOver;
   static BufferedImage talkPointerL;
   static BufferedImage talkPointerR;
   static BufferedImage thinkPointerL;
   static BufferedImage thinkPointerR;
   static BufferedImage watcherOuterFrame;
   static BufferedImage listWatcherOuterFrame;
   static BufferedImage listWatcherOuterFrameError;
   static BufferedImage vScrollFrame;
   static BufferedImage vScrollSlider;


   static synchronized void readSkin(Component var0) {
      if(!skinInitialized) {
         askBubbleFrame = readImage("askBubbleFrame.gif", var0);
         askPointerL = readImage("askBubblePointer.gif", var0);
         askPointerR = flipImage(askPointerL);
         bubbleFrame = readImage("talkBubbleFrame.gif", var0);
         goButton = readImage("goButton.gif", var0);
         goButtonOver = readImage("goButtonOver.gif", var0);
         promptCheckButton = readImage("promptCheckButton.png", var0);
         sliderKnob = readImage("sliderKnob.gif", var0);
         sliderSlot = readImage("sliderSlot.gif", var0);
         stopButton = readImage("stopButton.gif", var0);
         stopButtonOver = readImage("stopButtonOver.gif", var0);
         talkPointerL = readImage("talkBubbleTalkPointer.gif", var0);
         talkPointerR = flipImage(talkPointerL);
         thinkPointerL = readImage("talkBubbleThinkPointer.gif", var0);
         thinkPointerR = flipImage(thinkPointerL);
         watcherOuterFrame = readImage("watcherOuterFrame.png", var0);
         listWatcherOuterFrame = readImage("listWacherOuterFrame.png", var0);
         listWatcherOuterFrameError = readImage("listWacherOuterFrameError.png", var0);
         vScrollFrame = readImage("vScrollFrame.png", var0);
         vScrollSlider = readImage("vScrollSlider.png", var0);
         skinInitialized = true;
      }
   }

   static BufferedImage readImage(String var0, Component var1) {
      Toolkit var2 = Toolkit.getDefaultToolkit();
      Image var3 = var2.createImage(var1.getClass().getResource("skin/" + var0));
      MediaTracker var4 = new MediaTracker(var1);
      var4.addImage(var3, 0);

      try {
         var4.waitForID(0);
      } catch (InterruptedException var9) {
         ;
      }

      int var5 = var3.getWidth((ImageObserver)null);
      int var6 = var3.getHeight((ImageObserver)null);
      BufferedImage var7 = new BufferedImage(var5, var6, 2);
      Graphics var8 = var7.getGraphics();
      var8.drawImage(var3, 0, 0, var5, var6, (ImageObserver)null);
      var8.dispose();
      return var7;
   }

   static BufferedImage flipImage(BufferedImage var0) {
      int var1 = var0.getWidth((ImageObserver)null);
      int var2 = var0.getHeight((ImageObserver)null);
      BufferedImage var3 = new BufferedImage(var1, var2, 2);
      Graphics var4 = var3.getGraphics();
      var4.drawImage(var0, var1, 0, 0, var2, 0, 0, var1, var2, (ImageObserver)null);
      var4.dispose();
      return var3;
   }
}
