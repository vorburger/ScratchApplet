// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerPrims.java

import java.awt.*;
import java.awt.image.BufferedImage;

class Skin
{

    Skin()
    {
    }

    static synchronized void readSkin(Component component)
    {
        if(skinInitialized)
        {
            return;
        } else
        {
            askBubbleFrame = readImage("askBubbleFrame.gif", component);
            askPointerL = readImage("askBubblePointer.gif", component);
            askPointerR = flipImage(askPointerL);
            bubbleFrame = readImage("talkBubbleFrame.gif", component);
            goButton = readImage("goButton.gif", component);
            goButtonOver = readImage("goButtonOver.gif", component);
            promptCheckButton = readImage("promptCheckButton.png", component);
            sliderKnob = readImage("sliderKnob.gif", component);
            sliderSlot = readImage("sliderSlot.gif", component);
            stopButton = readImage("stopButton.gif", component);
            stopButtonOver = readImage("stopButtonOver.gif", component);
            talkPointerL = readImage("talkBubbleTalkPointer.gif", component);
            talkPointerR = flipImage(talkPointerL);
            thinkPointerL = readImage("talkBubbleThinkPointer.gif", component);
            thinkPointerR = flipImage(thinkPointerL);
            watcherOuterFrame = readImage("watcherOuterFrame.png", component);
            listWatcherOuterFrame = readImage("listWacherOuterFrame.png", component);
            listWatcherOuterFrameError = readImage("listWacherOuterFrameError.png", component);
            vScrollFrame = readImage("vScrollFrame.png", component);
            vScrollSlider = readImage("vScrollSlider.png", component);
            skinInitialized = true;
            return;
        }
    }

    static BufferedImage readImage(String s, Component component)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(component.getClass().getResource("skin/" + s));
        MediaTracker mediatracker = new MediaTracker(component);
        mediatracker.addImage(image, 0);
        try
        {
            mediatracker.waitForID(0);
        }
        catch(InterruptedException interruptedexception) { }
        int i = image.getWidth(null);
        int j = image.getHeight(null);
        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics g = bufferedimage.getGraphics();
        g.drawImage(image, 0, 0, i, j, null);
        g.dispose();
        return bufferedimage;
    }

    static BufferedImage flipImage(BufferedImage bufferedimage)
    {
        int i = bufferedimage.getWidth(null);
        int j = bufferedimage.getHeight(null);
        BufferedImage bufferedimage1 = new BufferedImage(i, j, 2);
        Graphics g = bufferedimage1.getGraphics();
        g.drawImage(bufferedimage, i, 0, 0, j, 0, 0, i, j, null);
        g.dispose();
        return bufferedimage1;
    }

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
}
