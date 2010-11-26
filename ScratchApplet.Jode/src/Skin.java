/* Skin - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
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
    
    static synchronized void readSkin(Component component) {
	if (!skinInitialized) {
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
	    listWatcherOuterFrame
		= readImage("listWacherOuterFrame.png", component);
	    listWatcherOuterFrameError
		= readImage("listWacherOuterFrameError.png", component);
	    vScrollFrame = readImage("vScrollFrame.png", component);
	    vScrollSlider = readImage("vScrollSlider.png", component);
	    skinInitialized = true;
	}
    }
    
    static BufferedImage readImage(String string, Component component) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image = toolkit.createImage(component.getClass()
					      .getResource("skin/" + string));
	MediaTracker mediatracker = new MediaTracker(component);
	mediatracker.addImage(image, 0);
	try {
	    mediatracker.waitForID(0);
	} catch (InterruptedException interruptedexception) {
	    /* empty */
	}
	int i = image.getWidth(null);
	int i_0_ = image.getHeight(null);
	BufferedImage bufferedimage = new BufferedImage(i, i_0_, 2);
	Graphics graphics = bufferedimage.getGraphics();
	graphics.drawImage(image, 0, 0, i, i_0_, null);
	graphics.dispose();
	return bufferedimage;
    }
    
    static BufferedImage flipImage(BufferedImage bufferedimage) {
	int i = bufferedimage.getWidth(null);
	int i_1_ = bufferedimage.getHeight(null);
	BufferedImage bufferedimage_2_ = new BufferedImage(i, i_1_, 2);
	Graphics graphics = bufferedimage_2_.getGraphics();
	graphics.drawImage(bufferedimage, i, 0, 0, i_1_, 0, 0, i, i_1_, null);
	graphics.dispose();
	return bufferedimage_2_;
    }
}
