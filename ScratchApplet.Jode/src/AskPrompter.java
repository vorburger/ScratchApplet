/* AskPrompter - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;

class AskPrompter extends StretchyBox
{
    int fontSize = 11;
    Font font = new Font("Arial Unicode MS", 1, ((AskPrompter) this).fontSize);
    Font answerFont = new Font("Arial Unicode MS", 0, 13);
    FontRenderContext renderContext;
    String promptString = "";
    String answerString = "";
    int lineX;
    int lineY;
    
    AskPrompter(String string) {
	((AskPrompter) this).renderContext
	    = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
	setFrameImage(Skin.askBubbleFrame);
	setPrompt(string);
    }
    
    void setPrompt(String string) {
	int i = string.length() == 0 ? 1 : 2;
	((AskPrompter) this).promptString = string;
	((AskPrompter) this).x = 15;
	((AskPrompter) this).w = 452;
	((AskPrompter) this).h = i * (((AskPrompter) this).fontSize + 4) + 22;
	((AskPrompter) this).y = 378 - ((AskPrompter) this).h;
    }
    
    public Rectangle rect() {
	return new Rectangle(((AskPrompter) this).x, ((AskPrompter) this).y,
			     ((AskPrompter) this).w, ((AskPrompter) this).h);
    }
    
    public void paint(Graphics graphics) {
	super.paint(graphics);
	Graphics2D graphics2d = (Graphics2D) graphics;
	((AskPrompter) this).lineX = ((AskPrompter) this).x + 12;
	((AskPrompter) this).lineY
	    = ((AskPrompter) this).y + ((AskPrompter) this).fontSize + 14;
	graphics2d.setColor(new Color(0, 0, 0));
	graphics2d.setFont(((AskPrompter) this).font);
	if (((AskPrompter) this).promptString.length() > 0) {
	    graphics2d.drawString(((AskPrompter) this).promptString,
				  ((AskPrompter) this).lineX - 2,
				  ((AskPrompter) this).lineY - 8);
	    ((AskPrompter) this).lineY += ((AskPrompter) this).fontSize + 4;
	}
	graphics2d.setStroke(new BasicStroke(2.0F));
	graphics2d.setColor(new Color(213, 213, 213));
	graphics2d.drawLine
	    (((AskPrompter) this).lineX - 4,
	     ((AskPrompter) this).lineY - ((AskPrompter) this).fontSize - 5,
	     ((AskPrompter) this).lineX - 4 + ((AskPrompter) this).w - 38,
	     ((AskPrompter) this).lineY - ((AskPrompter) this).fontSize - 5);
	graphics2d.drawLine(((AskPrompter) this).lineX - 4,
			    (((AskPrompter) this).lineY
			     - ((AskPrompter) this).fontSize - 5),
			    ((AskPrompter) this).lineX - 4,
			    (((AskPrompter) this).lineY
			     - ((AskPrompter) this).fontSize - 5
			     + ((AskPrompter) this).fontSize + 9));
	graphics2d.setColor(new Color(242, 242, 242));
	graphics2d.fillRect(((AskPrompter) this).lineX - 3,
			    (((AskPrompter) this).lineY
			     - ((AskPrompter) this).fontSize - 4),
			    ((AskPrompter) this).w - 39,
			    ((AskPrompter) this).fontSize + 8);
	Shape shape = graphics.getClip();
	graphics.setClip(((AskPrompter) this).lineX - 3,
			 (((AskPrompter) this).lineY
			  - ((AskPrompter) this).fontSize - 4),
			 ((AskPrompter) this).w - 39,
			 ((AskPrompter) this).fontSize + 8);
	graphics2d.setColor(new Color(0, 0, 0));
	graphics2d.setFont(((AskPrompter) this).answerFont);
	graphics2d.drawString(((AskPrompter) this).answerString,
			      ((AskPrompter) this).lineX,
			      ((AskPrompter) this).lineY - 1);
	graphics.setClip(shape);
	graphics2d.drawImage(Skin.promptCheckButton, null,
			     (((AskPrompter) this).lineX
			      + ((AskPrompter) this).w - 38),
			     (((AskPrompter) this).lineY
			      - ((AskPrompter) this).fontSize - 6));
    }
    
    public boolean mouseDown(int i, int i_0_, PlayerCanvas playercanvas) {
	if (i > ((AskPrompter) this).lineX + ((AskPrompter) this).w - 38
	    && i < (((AskPrompter) this).lineX + ((AskPrompter) this).w - 38
		    + 20)
	    && i_0_ > (((AskPrompter) this).lineY
		       - ((AskPrompter) this).fontSize - 6)
	    && i_0_ < (((AskPrompter) this).lineY
		       - ((AskPrompter) this).fontSize - 6 + 20)) {
	    playercanvas.hideAskPrompt();
	    return true;
	}
	return false;
    }
    
    public void handleKeystroke(int i, PlayerCanvas playercanvas) {
	playercanvas.inval(rect());
	if (i == 3 || i == 10)
	    playercanvas.hideAskPrompt();
	else if (i == 8 || i == 127) {
	    if (((AskPrompter) this).answerString.length() > 0)
		((AskPrompter) this).answerString
		    = (((AskPrompter) this).answerString.substring
		       (0, ((AskPrompter) this).answerString.length() - 1));
	} else {
	    char[] cs = new char[1];
	    cs[0] = (char) i;
	    if (i >= 32) {
		StringBuffer stringbuffer = new StringBuffer();
		AskPrompter askprompter_1_ = this;
		((AskPrompter) askprompter_1_).answerString
		    = stringbuffer.append
			  (((AskPrompter) askprompter_1_).answerString).append
			  (new String(cs)).toString();
	    }
	}
    }
}
