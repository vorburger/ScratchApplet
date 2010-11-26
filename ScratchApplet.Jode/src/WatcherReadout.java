/* WatcherReadout - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;

class WatcherReadout
{
    int x;
    int y;
    int w = 40;
    int h = 14;
    Color color = new Color(100, 60, 20);
    String contents = "123";
    boolean isLarge = false;
    static final Font smallFont = new Font("Arial Unicode MS", 1, 10);
    static final Font bigFont = new Font("Arial Unicode MS", 1, 14);
    
    WatcherReadout(boolean bool) {
	beLarge(bool);
    }
    
    void beLarge(boolean bool) {
	if (((WatcherReadout) this).isLarge != bool) {
	    ((WatcherReadout) this).isLarge = bool;
	    ((WatcherReadout) this).w
		= ((WatcherReadout) this).isLarge ? 50 : 40;
	    ((WatcherReadout) this).h
		= ((WatcherReadout) this).isLarge ? 23 : 14;
	}
    }
    
    void adjustWidth(Graphics graphics) {
	Font font = ((WatcherReadout) this).isLarge ? bigFont : smallFont;
	((WatcherReadout) this).w
	    = Math.max(((WatcherReadout) this).w,
		       stringWidth(((WatcherReadout) this).contents, font,
				   graphics) + 12);
    }
    
    void paint(Graphics graphics) {
	graphics.setColor(Color.WHITE);
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  ((WatcherReadout) this).y,
			  ((WatcherReadout) this).w - 4, 1);
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  (((WatcherReadout) this).y
			   + ((WatcherReadout) this).h - 1),
			  ((WatcherReadout) this).w - 4, 1);
	graphics.fillRect(((WatcherReadout) this).x,
			  ((WatcherReadout) this).y + 2, 1,
			  ((WatcherReadout) this).h - 4);
	graphics.fillRect((((WatcherReadout) this).x
			   + ((WatcherReadout) this).w - 1),
			  ((WatcherReadout) this).y + 2, 1,
			  ((WatcherReadout) this).h - 4);
	graphics.fillRect(((WatcherReadout) this).x + 1,
			  ((WatcherReadout) this).y + 1, 1, 1);
	graphics.fillRect((((WatcherReadout) this).x
			   + ((WatcherReadout) this).w - 2),
			  ((WatcherReadout) this).y + 1, 1, 1);
	graphics.fillRect(((WatcherReadout) this).x + 1,
			  (((WatcherReadout) this).y
			   + ((WatcherReadout) this).h - 2),
			  1, 1);
	graphics.fillRect((((WatcherReadout) this).x
			   + ((WatcherReadout) this).w - 2),
			  (((WatcherReadout) this).y
			   + ((WatcherReadout) this).h - 2),
			  1, 1);
	graphics.setColor(darker(darker(((WatcherReadout) this).color)));
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  ((WatcherReadout) this).y + 1,
			  ((WatcherReadout) this).w - 4, 1);
	graphics.fillRect(((WatcherReadout) this).x + 1,
			  ((WatcherReadout) this).y + 2, 1,
			  ((WatcherReadout) this).h - 4);
	graphics.setColor(darker(((WatcherReadout) this).color));
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  ((WatcherReadout) this).y + 2,
			  ((WatcherReadout) this).w - 4, 1);
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  (((WatcherReadout) this).y
			   + ((WatcherReadout) this).h - 2),
			  ((WatcherReadout) this).w - 4, 1);
	graphics.fillRect(((WatcherReadout) this).x + 2,
			  ((WatcherReadout) this).y + 2, 1,
			  ((WatcherReadout) this).h - 3);
	graphics.fillRect((((WatcherReadout) this).x
			   + ((WatcherReadout) this).w - 2),
			  ((WatcherReadout) this).y + 2, 1,
			  ((WatcherReadout) this).h - 4);
	graphics.setColor(((WatcherReadout) this).color);
	graphics.fillRect(((WatcherReadout) this).x + 3,
			  ((WatcherReadout) this).y + 3,
			  ((WatcherReadout) this).w - 5,
			  ((WatcherReadout) this).h - 5);
	Font font = ((WatcherReadout) this).isLarge ? bigFont : smallFont;
	int i = ((WatcherReadout) this).isLarge ? 17 : 11;
	graphics.setColor(Color.WHITE);
	graphics.setFont(font);
	graphics.drawString(((WatcherReadout) this).contents,
			    (((WatcherReadout) this).x
			     + (((WatcherReadout) this).w
				- stringWidth(((WatcherReadout) this).contents,
					      font, graphics)) / 2
			     - 1),
			    ((WatcherReadout) this).y + i);
    }
    
    public static int stringWidth(String string, Font font,
				  Graphics graphics) {
	if (string.length() == 0)
	    return 0;
	TextLayout textlayout
	    = new TextLayout(string, font,
			     ((Graphics2D) graphics).getFontRenderContext());
	return (int) textlayout.getBounds().getBounds().getWidth();
    }
    
    public static Color darker(Color color) {
	double d = 0.8333;
	return new Color((int) (d * (double) color.getRed()),
			 (int) (d * (double) color.getGreen()),
			 (int) (d * (double) color.getBlue()));
    }
}
