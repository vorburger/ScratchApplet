/* Watcher - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.util.Hashtable;

class Watcher implements Drawable
{
    static final Color black = new Color(0, 0, 0);
    static final Font labelFont = new Font("Arial Unicode MS", 1, 10);
    static final int NORMAL_MODE = 1;
    static final int SLIDER_MODE = 2;
    static final int LARGE_MODE = 3;
    PlayerCanvas canvas;
    StretchyBox box;
    WatcherReadout readout;
    StretchyBox slider;
    String label = "x";
    int mode = 1;
    double sliderMin = 0.0;
    double sliderMax = 100.0;
    boolean isDiscrete = false;
    boolean isShowing = true;
    
    Watcher(LContext lcontext) {
	if (lcontext != null)
	    ((Watcher) this).canvas = ((LContext) lcontext).canvas;
	((Watcher) this).box = new StretchyBox();
	((Watcher) this).box.setFrameImage(Skin.watcherOuterFrame);
	((StretchyBox) ((Watcher) this).box).x = 50;
	((StretchyBox) ((Watcher) this).box).y = 50;
	((Watcher) this).readout = new WatcherReadout(false);
	((WatcherReadout) ((Watcher) this).readout).color
	    = new Color(74, 107, 214);
	((Watcher) this).slider = new StretchyBox();
	((Watcher) this).slider.setFrameImage(Skin.sliderSlot);
	((StretchyBox) ((Watcher) this).slider).h = 5;
    }
    
    void show() {
	((Watcher) this).isShowing = true;
	inval();
    }
    
    void hide() {
	((Watcher) this).isShowing = false;
	inval();
    }
    
    public boolean isShowing() {
	return ((Watcher) this).isShowing;
    }
    
    public Rectangle rect() {
	return new Rectangle(((StretchyBox) ((Watcher) this).box).x,
			     ((StretchyBox) ((Watcher) this).box).y,
			     ((StretchyBox) ((Watcher) this).box).w,
			     ((StretchyBox) ((Watcher) this).box).h);
    }
    
    public Rectangle fullRect() {
	return rect();
    }
    
    void inval() {
	((Watcher) this).canvas.inval(rect());
    }
    
    public void paint(Graphics graphics) {
	int i = labelWidth(graphics);
	((Watcher) this).readout.beLarge(((Watcher) this).mode == 3);
	((Watcher) this).readout.adjustWidth(graphics);
	((StretchyBox) ((Watcher) this).box).w
	    = i + ((WatcherReadout) ((Watcher) this).readout).w + 17;
	((StretchyBox) ((Watcher) this).box).h
	    = ((Watcher) this).mode == 1 ? 21 : 31;
	if (((Watcher) this).mode == 3) {
	    ((WatcherReadout) ((Watcher) this).readout).x
		= ((StretchyBox) ((Watcher) this).box).x;
	    ((WatcherReadout) ((Watcher) this).readout).y
		= ((StretchyBox) ((Watcher) this).box).y;
	    ((Watcher) this).readout.paint(graphics);
	} else {
	    ((Watcher) this).box.paint(graphics);
	    drawLabel(graphics);
	    ((WatcherReadout) ((Watcher) this).readout).x
		= ((StretchyBox) ((Watcher) this).box).x + i + 12;
	    ((WatcherReadout) ((Watcher) this).readout).y
		= ((StretchyBox) ((Watcher) this).box).y + 3;
	    ((Watcher) this).readout.paint(graphics);
	    if (((Watcher) this).mode == 2)
		drawSlider(graphics);
	}
    }
    
    public void paintBubble(Graphics graphics) {
	/* empty */
    }
    
    public void mouseDown(int i, int i_0_) {
	/* empty */
    }
    
    void drawLabel(Graphics graphics) {
	graphics.setColor(black);
	graphics.setFont(labelFont);
	graphics.drawString(((Watcher) this).label,
			    ((StretchyBox) ((Watcher) this).box).x + 6,
			    ((StretchyBox) ((Watcher) this).box).y + 14);
    }
    
    int labelWidth(Graphics graphics) {
	if (((Watcher) this).label.length() == 0)
	    return 0;
	TextLayout textlayout
	    = new TextLayout(((Watcher) this).label, labelFont,
			     ((Graphics2D) graphics).getFontRenderContext());
	return (int) textlayout.getBounds().getBounds().getWidth();
    }
    
    void drawSlider(Graphics graphics) {
	((StretchyBox) ((Watcher) this).slider).x
	    = ((StretchyBox) ((Watcher) this).box).x + 6;
	((StretchyBox) ((Watcher) this).slider).y
	    = ((StretchyBox) ((Watcher) this).box).y + 21;
	((StretchyBox) ((Watcher) this).slider).w
	    = ((StretchyBox) ((Watcher) this).box).w - 12;
	((Watcher) this).slider.paint(graphics);
	int i = (int) Math.round((double) (((StretchyBox)
					    ((Watcher) this).slider).w
					   - 8)
				 * ((getSliderValue()
				     - ((Watcher) this).sliderMin)
				    / (((Watcher) this).sliderMax
				       - ((Watcher) this).sliderMin)));
	i = Math.max(0,
		     Math.min(i,
			      ((StretchyBox) ((Watcher) this).slider).w - 8));
	graphics.drawImage(Skin.sliderKnob,
			   ((StretchyBox) ((Watcher) this).slider).x + i - 1,
			   ((StretchyBox) ((Watcher) this).slider).y - 3,
			   null);
    }
    
    boolean inSlider(int i, int i_1_) {
	if (((Watcher) this).mode != 2)
	    return false;
	if (i_1_ < ((StretchyBox) ((Watcher) this).slider).y - 1
	    || i_1_ > (((StretchyBox) ((Watcher) this).slider).y
		       + ((StretchyBox) ((Watcher) this).slider).h + 4))
	    return false;
	if (i < ((StretchyBox) ((Watcher) this).slider).x - 4
	    || i > (((StretchyBox) ((Watcher) this).slider).x
		    + ((StretchyBox) ((Watcher) this).slider).w + 5))
	    return false;
	return true;
    }
    
    public void dragTo(int i, int i_2_) {
	double d = (double) (i - ((StretchyBox) ((Watcher) this).box).x - 10);
	setSliderValue((d
			* (((Watcher) this).sliderMax
			   - ((Watcher) this).sliderMin)
			/ (double) (((StretchyBox) ((Watcher) this).slider).w
				    - 8)) + ((Watcher) this).sliderMin);
    }
    
    void click(int i, int i_3_) {
	double d = getSliderValue();
	int i_4_ = (((StretchyBox) ((Watcher) this).slider).x
		    + (int) Math.round((double) (((StretchyBox)
						  ((Watcher) this).slider).w
						 - 8)
				       * ((d - ((Watcher) this).sliderMin)
					  / (((Watcher) this).sliderMax
					     - ((Watcher) this).sliderMin)))
		    + 5);
	int i_5_ = i < i_4_ ? -1 : 1;
	if (((Watcher) this).isDiscrete)
	    setSliderValue(d + (double) i_5_);
	else
	    setSliderValue(d + (double) i_5_ * ((((Watcher) this).sliderMax
						 - ((Watcher) this).sliderMin)
						/ 100.0));
    }
    
    double getSliderValue() {
	String string = Logo.prs(getObjProp(this, "param"));
	Hashtable hashtable = getVarsTable();
	if (string == null || hashtable == null)
	    return ((((Watcher) this).sliderMin + ((Watcher) this).sliderMax)
		    / 2.0);
	Object object
	    = hashtable.get(Logo.aSymbol(string,
					 ((PlayerCanvas)
					  ((Watcher) this).canvas).lc));
	return (object instanceof Number ? ((Number) object).doubleValue()
		: ((((Watcher) this).sliderMin + ((Watcher) this).sliderMax)
		   / 2.0));
    }
    
    void setSliderValue(double d) {
	double d_6_ = ((Watcher) this).isDiscrete ? (double) Math.round(d) : d;
	d_6_ = Math.min(((Watcher) this).sliderMax,
			Math.max(((Watcher) this).sliderMin, d_6_));
	String string = Logo.prs(getObjProp(this, "param"));
	Hashtable hashtable = getVarsTable();
	if (string != null && hashtable != null) {
	    hashtable.put(Logo.aSymbol(string, ((PlayerCanvas)
						((Watcher) this).canvas).lc),
			  new Double(d_6_));
	    inval();
	}
    }
    
    Hashtable getVarsTable() {
	String string = Logo.prs(getObjProp(this, "op"));
	String string_7_ = Logo.prs(getObjProp(this, "param"));
	Object object = getObjProp(this, "target");
	if (object == null || !string.equals("getVar:"))
	    return null;
	Object object_8_ = getObjProp(object, "vars");
	if (object_8_ == null)
	    return null;
	return ((Hashtable)
		((LContext) ((PlayerCanvas) ((Watcher) this).canvas).lc)
		    .props.get(object_8_));
    }
    
    Object getObjProp(Object object, String string) {
	Hashtable hashtable
	    = ((Hashtable)
	       ((LContext) ((PlayerCanvas) ((Watcher) this).canvas).lc)
		   .props.get(object));
	if (hashtable == null)
	    return null;
	return hashtable.get(Logo.aSymbol(string,
					  ((PlayerCanvas)
					   ((Watcher) this).canvas).lc));
    }
}
