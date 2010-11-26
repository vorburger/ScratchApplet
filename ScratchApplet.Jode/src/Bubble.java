/* Bubble - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Vector;

class Bubble extends StretchyBox
{
    boolean pointLeft = false;
    BufferedImage leftPointer;
    BufferedImage rightPointer;
    int fontSize = 13;
    Font font = new Font("Arial Unicode MS", 1, ((Bubble) this).fontSize);
    FontRenderContext renderContext;
    int wrapWidth = 135;
    String contents;
    String[] lines;
    int[] xOffsets;
    
    Bubble() {
	((Bubble) this).renderContext
	    = Skin.bubbleFrame.createGraphics().getFontRenderContext();
	setFrameImage(Skin.bubbleFrame);
	beThinkBubble(false);
    }
    
    void beThinkBubble(boolean bool) {
	if (bool) {
	    ((Bubble) this).leftPointer = Skin.thinkPointerL;
	    ((Bubble) this).rightPointer = Skin.thinkPointerR;
	} else {
	    ((Bubble) this).leftPointer = Skin.talkPointerL;
	    ((Bubble) this).rightPointer = Skin.talkPointerR;
	}
    }
    
    void beAskBubble() {
	((Bubble) this).renderContext
	    = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
	setFrameImage(Skin.askBubbleFrame);
	((Bubble) this).leftPointer = Skin.askPointerL;
	((Bubble) this).rightPointer = Skin.askPointerR;
    }
    
    void setContents(String string) {
	((Bubble) this).contents = string;
	Vector vector = new Vector();
	int i;
	for (int i_0_ = 0; i_0_ < string.length(); i_0_ = i) {
	    i = findLineEnd(string, i_0_);
	    vector.addElement(string.substring(i_0_, i));
	}
	((Bubble) this).lines = new String[vector.size()];
	((Bubble) this).w = 65;
	for (i = 0; i < ((Bubble) this).lines.length; i++) {
	    ((Bubble) this).lines[i] = (String) vector.get(i);
	    ((Bubble) this).w
		= Math.max(((Bubble) this).w,
			   widthOf(((Bubble) this).lines[i]) + 15);
	}
	((Bubble) this).xOffsets = new int[((Bubble) this).lines.length];
	for (i = 0; i < ((Bubble) this).lines.length; i++)
	    ((Bubble) this).xOffsets[i]
		= (((Bubble) this).w - widthOf(((Bubble) this).lines[i])) / 2;
	((Bubble) this).h
	    = (((Bubble) this).lines.length * (((Bubble) this).fontSize + 2)
	       + 19);
    }
    
    int findLineEnd(String string, int i) {
	int i_1_;
	for (i_1_ = i + 1;
	     i_1_ < string.length() && (widthOf(string.substring(i, i_1_ + 1))
					< ((Bubble) this).wrapWidth);
	     i_1_++) {
	    /* empty */
	}
	if (i_1_ == string.length())
	    return i_1_;
	if (widthOf(string.substring(i, i_1_ + 1)) < ((Bubble) this).wrapWidth)
	    return i_1_ + 1;
	int i_2_ = i_1_ + 1;
	for (/**/; i_1_ > i + 1; i_1_--) {
	    if (i_1_ < string.length() && string.charAt(i_1_) == ' ')
		return i_1_ + 1;
	}
	return i_2_;
    }
    
    int widthOf(String string) {
	if (string.length() == 0)
	    return 0;
	return (int) new TextLayout
			 (string, ((Bubble) this).font,
			  ((Bubble) this).renderContext)
			 .getAdvance();
    }
    
    public Rectangle rect() {
	return new Rectangle(((Bubble) this).x, ((Bubble) this).y,
			     ((Bubble) this).w,
			     (((Bubble) this).h
			      + ((Bubble) this).leftPointer.getHeight(null)));
    }
    
    public void paint(Graphics graphics) {
	super.paint(graphics);
	if (((Bubble) this).pointLeft)
	    graphics.drawImage(((Bubble) this).leftPointer,
			       ((Bubble) this).x + 7,
			       ((Bubble) this).y + ((Bubble) this).h - 3,
			       null);
	else
	    graphics.drawImage(((Bubble) this).rightPointer,
			       (((Bubble) this).x - 9 + ((Bubble) this).w
				- ((Bubble) this).rightPointer.getWidth(null)),
			       ((Bubble) this).y + ((Bubble) this).h - 3,
			       null);
	graphics.setColor(new Color(0, 0, 0));
	graphics.setFont(((Bubble) this).font);
	int i = ((Bubble) this).y + ((Bubble) this).fontSize + 8;
	for (int i_3_ = 0; i_3_ < ((Bubble) this).lines.length; i_3_++) {
	    graphics.drawString(((Bubble) this).lines[i_3_],
				(((Bubble) this).x
				 + ((Bubble) this).xOffsets[i_3_]),
				i);
	    i += ((Bubble) this).fontSize + 2;
	}
    }
}
