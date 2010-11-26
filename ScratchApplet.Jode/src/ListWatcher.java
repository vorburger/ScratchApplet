/* ListWatcher - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

class ListWatcher implements Drawable
{
    public static final Font LABEL_FONT = new Font("Arial Unicode MS", 1, 10);
    public static final Font LABEL_FONT_SMALL
	= new Font("Arial Unicode MS", 0, 10);
    public static final Font CELL_NUM_FONT
	= new Font("Arial Unicode MS", 0, 9);
    public static final int TOP_MARGIN = 23;
    public static final int BOTTOM_MARGIN = 20;
    public static final int LEFT_MARGIN = 5;
    public static final int RIGHT_MARGIN = 5;
    LContext lc;
    PlayerCanvas canvas;
    StretchyBox box;
    ListWatcherPane pane;
    ListWatcherScrollBar scrollBar;
    String listTitle = "listTitle";
    Object[] list = null;
    ArrayList highlightedIndices = new ArrayList();
    int mouseOffset = 0;
    int paneHeight = 0;
    int scroll = 0;
    int scrollForIndex = 0;
    int scrollBarHeight = 0;
    float scrollRatio = 0.0F;
    boolean isShowing = true;
    boolean useScrollForIndex = false;
    
    ListWatcher(LContext lcontext) {
	if (lcontext != null) {
	    ((ListWatcher) this).canvas = ((LContext) lcontext).canvas;
	    ((ListWatcher) this).lc = lcontext;
	}
	((ListWatcher) this).box = new StretchyBox();
	((ListWatcher) this).box.setFrameImage(Skin.listWatcherOuterFrame);
	((ListWatcher) this).pane = new ListWatcherPane(this);
    }
    
    public void setList(Object[] objects) {
	((ListWatcher) this).list = objects;
	((ListWatcher) this).pane.setList(((ListWatcher) this).list);
	((ListWatcher) this).paneHeight
	    = ((ListWatcherPane) ((ListWatcher) this).pane).totalHeight;
	initScrollBar();
    }
    
    public void initScrollBar() {
	((ListWatcher) this).scrollBarHeight
	    = ((StretchyBox) ((ListWatcher) this).box).h - 23 - 20;
	((ListWatcher) this).scrollRatio
	    = ((float) ((ListWatcher) this).paneHeight
	       / (float) ((ListWatcher) this).scrollBarHeight);
	if ((double) ((ListWatcher) this).scrollRatio < 1.0) {
	    ((ListWatcher) this).scrollRatio = 0.0F;
	    ((ListWatcher) this).scrollBar = null;
	} else {
	    if (((ListWatcher) this).scrollBar == null)
		((ListWatcher) this).scrollBar = new ListWatcherScrollBar();
	    ((StretchyBox)
	     ((ListWatcherScrollBar) ((ListWatcher) this).scrollBar).nubBox).h
		= (int) ((float) ((ListWatcher) this).scrollBarHeight
			 / ((ListWatcher) this).scrollRatio);
	    if (((StretchyBox) ((ListWatcherScrollBar)
				((ListWatcher) this).scrollBar).nubBox).h
		< 23) {
		((StretchyBox) ((ListWatcherScrollBar)
				((ListWatcher) this).scrollBar).nubBox).h
		    = 23;
		((ListWatcher) this).scrollRatio
		    = (float) ((double) (float) ((ListWatcher) this).paneHeight
			       / ((double) (float) (((ListWatcher) this)
						    .scrollBarHeight)
				  - 23.0));
	    }
	}
    }
    
    void show() {
	((ListWatcher) this).isShowing = true;
	inval();
    }
    
    void hide() {
	((ListWatcher) this).isShowing = false;
	inval();
    }
    
    public boolean isShowing() {
	return ((ListWatcher) this).isShowing;
    }
    
    public Rectangle rect() {
	return new Rectangle(((StretchyBox) ((ListWatcher) this).box).x,
			     ((StretchyBox) ((ListWatcher) this).box).y,
			     ((StretchyBox) ((ListWatcher) this).box).w,
			     ((StretchyBox) ((ListWatcher) this).box).h);
    }
    
    public Rectangle fullRect() {
	return rect();
    }
    
    void inval() {
	((ListWatcher) this).canvas.inval(rect());
    }
    
    public void paintBubble(Graphics graphics) {
	/* empty */
    }
    
    public void paint(Graphics graphics) {
	((ListWatcher) this).box.paint(graphics);
	graphics.setColor(Color.BLACK);
	graphics.setFont(LABEL_FONT);
	graphics.drawString
	    (((ListWatcher) this).listTitle,
	     (((StretchyBox) ((ListWatcher) this).box).x
	      + (((StretchyBox) ((ListWatcher) this).box).w
		 - WatcherReadout.stringWidth(((ListWatcher) this).listTitle,
					      LABEL_FONT, graphics)) / 2),
	     ((StretchyBox) ((ListWatcher) this).box).y + 23 - 8);
	if (((ListWatcher) this).scrollBar != null)
	    ((ListWatcher) this).scrollBar.paint
		(graphics,
		 (((StretchyBox) ((ListWatcher) this).box).x
		  + ((StretchyBox) ((ListWatcher) this).box).w),
		 ((StretchyBox) ((ListWatcher) this).box).y + 23,
		 ((ListWatcher) this).scrollBarHeight,
		 (((StretchyBox) ((ListWatcher) this).box).y + 23
		  + ((ListWatcher) this).scroll));
	Graphics graphics_0_ = graphics.create();
	((ListWatcher) this).pane.paint
	    (graphics_0_, ((StretchyBox) ((ListWatcher) this).box).x + 5,
	     (((ListWatcher) this).useScrollForIndex
	      ? ((ListWatcher) this).scrollForIndex
	      : (int) ((float) ((ListWatcher) this).scroll
		       * ((ListWatcher) this).scrollRatio)),
	     ((StretchyBox) ((ListWatcher) this).box).y + 23,
	     ((ListWatcher) this).scrollBarHeight);
	graphics.setColor(Color.BLACK);
	graphics.setFont(LABEL_FONT_SMALL);
	String string = "length: " + ((ListWatcher) this).list.length;
	graphics.drawString(string,
			    (((StretchyBox) ((ListWatcher) this).box).x
			     + (((StretchyBox) ((ListWatcher) this).box).w
				- WatcherReadout.stringWidth(string,
							     LABEL_FONT_SMALL,
							     graphics)) / 2),
			    (((StretchyBox) ((ListWatcher) this).box).y
			     + ((StretchyBox) ((ListWatcher) this).box).h
			     - 5));
    }
    
    boolean inScrollbar(int i, int i_1_) {
	if (((ListWatcher) this).scrollBar != null) {
	    if (i < (((StretchyBox) ((ListWatcher) this).box).x
		     + ((StretchyBox) ((ListWatcher) this).box).w - 20)
		|| i > (((StretchyBox) ((ListWatcher) this).box).x
			+ ((StretchyBox) ((ListWatcher) this).box).w))
		return false;
	    if (i_1_ < (((StretchyBox) ((ListWatcher) this).box).y + 23
			+ ((ListWatcher) this).scroll)
		|| i_1_ > (((StretchyBox) ((ListWatcher) this).box).y + 23
			   + ((ListWatcher) this).scroll
			   + ((StretchyBox) (((ListWatcherScrollBar)
					      ((ListWatcher) this).scrollBar)
					     .nubBox)).h))
		return false;
	    return true;
	}
	return false;
    }
    
    public void mouseDown(int i, int i_2_) {
	if (((ListWatcher) this).scrollBar != null)
	    ((ListWatcher) this).mouseOffset
		= (i_2_ - (((StretchyBox) ((ListWatcher) this).box).y + 23)
		   - ((ListWatcher) this).scroll);
    }
    
    public void dragTo(int i, int i_3_) {
	if (((ListWatcher) this).scrollBar != null)
	    setScroll(i_3_ - ((ListWatcher) this).mouseOffset
		      - (((StretchyBox) ((ListWatcher) this).box).y + 23));
    }
    
    void setScroll(int i) {
	if (((ListWatcher) this).scrollBar != null) {
	    if (i < 0)
		((ListWatcher) this).scroll = 0;
	    else if (i > (((ListWatcher) this).scrollBarHeight
			  - ((StretchyBox) (((ListWatcherScrollBar)
					     ((ListWatcher) this).scrollBar)
					    .nubBox)).h))
		((ListWatcher) this).scroll
		    = (((ListWatcher) this).scrollBarHeight
		       - ((StretchyBox) (((ListWatcherScrollBar)
					  ((ListWatcher) this).scrollBar)
					 .nubBox)).h);
	    else
		((ListWatcher) this).scroll = i;
	    ((ListWatcher) this).useScrollForIndex = false;
	    inval();
	}
    }
    
    void setScrollForHighlightIndex(int i) {
	if (((ListWatcher) this).scrollBar != null) {
	    ((ListWatcher) this).scrollForIndex
		= (((ListWatcher) this).pane.getYPositionAtIndex(i)
		   - ((ListWatcher) this).scrollBarHeight / 2);
	    ((ListWatcher) this).useScrollForIndex = true;
	    int i_4_ = (int) ((float) ((ListWatcher) this).scrollForIndex
			      / ((ListWatcher) this).scrollRatio);
	    if (i_4_ < 0)
		((ListWatcher) this).scroll = 0;
	    else if (i_4_ > (((ListWatcher) this).scrollBarHeight
			     - ((StretchyBox) (((ListWatcherScrollBar)
						((ListWatcher) this).scrollBar)
					       .nubBox)).h))
		((ListWatcher) this).scroll
		    = (((ListWatcher) this).scrollBarHeight
		       - ((StretchyBox) (((ListWatcherScrollBar)
					  ((ListWatcher) this).scrollBar)
					 .nubBox)).h);
	    else
		((ListWatcher) this).scroll = i_4_;
	    inval();
	}
    }
    
    void highlightIndex(int i) {
	if (i < 1 || i > ((ListWatcher) this).list.length)
	    ((ListWatcher) this).box
		.setFrameImage(Skin.listWatcherOuterFrameError);
	else {
	    setScrollForHighlightIndex(i);
	    ((ListWatcher) this).highlightedIndices.add(new Integer(i));
	    ((ListWatcher) this).box.setFrameImage(Skin.listWatcherOuterFrame);
	}
    }
    
    void clearHighlights() {
	((ListWatcher) this).highlightedIndices = new ArrayList();
	((ListWatcher) this).box.setFrameImage(Skin.listWatcherOuterFrame);
    }
}
