/* ListWatcherCell - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

class ListWatcherCell
{
    Color CELL_COLOR = new Color(218, 77, 17);
    String contents;
    AttributedCharacterIterator ci;
    AttributedString as;
    LineBreakMeasurer lineMeasurer;
    FontRenderContext renderContext;
    BufferedImage contentsImage;
    int w;
    int h;
    int x;
    int y;
    
    ListWatcherCell(String string, int i) {
	((ListWatcherCell) this).contents = string;
	((ListWatcherCell) this).w = i;
	((ListWatcherCell) this).x = 0;
	((ListWatcherCell) this).y = 0;
	((ListWatcherCell) this).as = new AttributedString(string);
	((ListWatcherCell) this).as.addAttribute(TextAttribute.FONT,
						 ListWatcher.LABEL_FONT);
	((ListWatcherCell) this).ci
	    = ((ListWatcherCell) this).as.getIterator();
	Graphics2D graphics2d = Skin.bubbleFrame.createGraphics();
	((ListWatcherCell) this).renderContext
	    = graphics2d.getFontRenderContext();
	((ListWatcherCell) this).h = 4;
	((ListWatcherCell) this).lineMeasurer
	    = new LineBreakMeasurer(((ListWatcherCell) this).ci,
				    ((ListWatcherCell) this).renderContext);
	((ListWatcherCell) this).lineMeasurer
	    .setPosition(((ListWatcherCell) this).ci.getBeginIndex());
	while (((ListWatcherCell) this).lineMeasurer.getPosition()
	       < ((ListWatcherCell) this).ci.getEndIndex()) {
	    TextLayout textlayout
		= ((ListWatcherCell) this).lineMeasurer
		      .nextLayout((float) (((ListWatcherCell) this).w - 10));
	    ((ListWatcherCell) this).h += textlayout.getAscent();
	    ((ListWatcherCell) this).h
		+= textlayout.getDescent() + textlayout.getLeading();
	}
	((ListWatcherCell) this).contentsImage
	    = new BufferedImage(((ListWatcherCell) this).w,
				((ListWatcherCell) this).h, 6);
	Graphics graphics
	    = ((ListWatcherCell) this).contentsImage.getGraphics();
	graphics.setColor(Color.WHITE);
	Graphics2D graphics2d_0_ = (Graphics2D) graphics;
	int i_1_ = ((ListWatcherCell) this).y + 2;
	((ListWatcherCell) this).lineMeasurer
	    .setPosition(((ListWatcherCell) this).ci.getBeginIndex());
	while (((ListWatcherCell) this).lineMeasurer.getPosition()
	       < ((ListWatcherCell) this).ci.getEndIndex()) {
	    TextLayout textlayout
		= ((ListWatcherCell) this).lineMeasurer
		      .nextLayout((float) (((ListWatcherCell) this).w - 10));
	    i_1_ += textlayout.getAscent();
	    textlayout.draw(graphics2d_0_,
			    (float) (((ListWatcherCell) this).x + 6),
			    (float) i_1_);
	    i_1_ += textlayout.getDescent() + textlayout.getLeading();
	}
    }
    
    public void paint(Graphics graphics) {
	graphics.setColor(Color.WHITE);
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  ((ListWatcherCell) this).y,
			  ((ListWatcherCell) this).w - 4, 1);
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  (((ListWatcherCell) this).y
			   + ((ListWatcherCell) this).h - 1),
			  ((ListWatcherCell) this).w - 4, 1);
	graphics.fillRect(((ListWatcherCell) this).x,
			  ((ListWatcherCell) this).y + 2, 1,
			  ((ListWatcherCell) this).h - 4);
	graphics.fillRect((((ListWatcherCell) this).x
			   + ((ListWatcherCell) this).w - 1),
			  ((ListWatcherCell) this).y + 2, 1,
			  ((ListWatcherCell) this).h - 4);
	graphics.fillRect(((ListWatcherCell) this).x + 1,
			  ((ListWatcherCell) this).y + 1, 1, 1);
	graphics.fillRect((((ListWatcherCell) this).x
			   + ((ListWatcherCell) this).w - 2),
			  ((ListWatcherCell) this).y + 1, 1, 1);
	graphics.fillRect(((ListWatcherCell) this).x + 1,
			  (((ListWatcherCell) this).y
			   + ((ListWatcherCell) this).h - 2),
			  1, 1);
	graphics.fillRect((((ListWatcherCell) this).x
			   + ((ListWatcherCell) this).w - 2),
			  (((ListWatcherCell) this).y
			   + ((ListWatcherCell) this).h - 2),
			  1, 1);
	graphics.setColor
	    (WatcherReadout.darker(WatcherReadout.darker(((ListWatcherCell)
							  this).CELL_COLOR)));
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  ((ListWatcherCell) this).y + 1,
			  ((ListWatcherCell) this).w - 4, 1);
	graphics.fillRect(((ListWatcherCell) this).x + 1,
			  ((ListWatcherCell) this).y + 2, 1,
			  ((ListWatcherCell) this).h - 4);
	graphics.setColor(WatcherReadout
			      .darker(((ListWatcherCell) this).CELL_COLOR));
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  ((ListWatcherCell) this).y + 2,
			  ((ListWatcherCell) this).w - 4, 1);
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  (((ListWatcherCell) this).y
			   + ((ListWatcherCell) this).h - 2),
			  ((ListWatcherCell) this).w - 4, 1);
	graphics.fillRect(((ListWatcherCell) this).x + 2,
			  ((ListWatcherCell) this).y + 2, 1,
			  ((ListWatcherCell) this).h - 3);
	graphics.fillRect((((ListWatcherCell) this).x
			   + ((ListWatcherCell) this).w - 2),
			  ((ListWatcherCell) this).y + 2, 1,
			  ((ListWatcherCell) this).h - 4);
	graphics.setColor(((ListWatcherCell) this).CELL_COLOR);
	graphics.fillRect(((ListWatcherCell) this).x + 3,
			  ((ListWatcherCell) this).y + 3,
			  ((ListWatcherCell) this).w - 5,
			  ((ListWatcherCell) this).h - 5);
	graphics.drawImage(((ListWatcherCell) this).contentsImage,
			   ((ListWatcherCell) this).x,
			   ((ListWatcherCell) this).y, new Color(0, 0, 0, 1),
			   null);
    }
}
