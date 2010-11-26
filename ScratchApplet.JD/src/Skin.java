import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

class Skin
{
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

  static synchronized void readSkin(Component paramComponent)
  {
    if (skinInitialized) return;
    askBubbleFrame = readImage("askBubbleFrame.gif", paramComponent);
    askPointerL = readImage("askBubblePointer.gif", paramComponent);
    askPointerR = flipImage(askPointerL);
    bubbleFrame = readImage("talkBubbleFrame.gif", paramComponent);
    goButton = readImage("goButton.gif", paramComponent);
    goButtonOver = readImage("goButtonOver.gif", paramComponent);
    promptCheckButton = readImage("promptCheckButton.png", paramComponent);
    sliderKnob = readImage("sliderKnob.gif", paramComponent);
    sliderSlot = readImage("sliderSlot.gif", paramComponent);
    stopButton = readImage("stopButton.gif", paramComponent);
    stopButtonOver = readImage("stopButtonOver.gif", paramComponent);
    talkPointerL = readImage("talkBubbleTalkPointer.gif", paramComponent);
    talkPointerR = flipImage(talkPointerL);
    thinkPointerL = readImage("talkBubbleThinkPointer.gif", paramComponent);
    thinkPointerR = flipImage(thinkPointerL);
    watcherOuterFrame = readImage("watcherOuterFrame.png", paramComponent);
    listWatcherOuterFrame = readImage("listWacherOuterFrame.png", paramComponent);
    listWatcherOuterFrameError = readImage("listWacherOuterFrameError.png", paramComponent);
    vScrollFrame = readImage("vScrollFrame.png", paramComponent);
    vScrollSlider = readImage("vScrollSlider.png", paramComponent);
    skinInitialized = true;
  }

  static BufferedImage readImage(String paramString, Component paramComponent) {
    Toolkit localToolkit = Toolkit.getDefaultToolkit();
    Image localImage = localToolkit.createImage(paramComponent.getClass().getResource("skin/" + paramString));
    MediaTracker localMediaTracker = new MediaTracker(paramComponent);
    localMediaTracker.addImage(localImage, 0);
    try {
      localMediaTracker.waitForID(0);
    }
    catch (InterruptedException localInterruptedException) {
    }
    int i = localImage.getWidth(null);
    int j = localImage.getHeight(null);
    BufferedImage localBufferedImage = new BufferedImage(i, j, 2);
    Graphics localGraphics = localBufferedImage.getGraphics();
    localGraphics.drawImage(localImage, 0, 0, i, j, null);
    localGraphics.dispose();
    return localBufferedImage;
  }

  static BufferedImage flipImage(BufferedImage paramBufferedImage) {
    int i = paramBufferedImage.getWidth(null);
    int j = paramBufferedImage.getHeight(null);
    BufferedImage localBufferedImage = new BufferedImage(i, j, 2);
    Graphics localGraphics = localBufferedImage.getGraphics();
    localGraphics.drawImage(paramBufferedImage, i, 0, 0, j, 0, 0, i, j, null);
    localGraphics.dispose();
    return localBufferedImage;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Skin
 * JD-Core Version:    0.6.0
 */