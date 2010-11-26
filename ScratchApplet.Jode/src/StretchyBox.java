/* StretchyBox - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class StretchyBox implements Drawable
{
    int x;
    int y;
    int w = 100;
    int h = 75;
    BufferedImage cornerTL;
    BufferedImage cornerTR;
    BufferedImage cornerBL;
    BufferedImage cornerBR;
    BufferedImage edgeTop;
    BufferedImage edgeBottom;
    BufferedImage edgeLeft;
    BufferedImage edgeRight;
    Color fillColor;
    
    void setFrameImage(BufferedImage bufferedimage) {
	int i = bufferedimage.getWidth(null);
	int i_0_ = bufferedimage.getHeight(null);
	int i_1_ = i / 2;
	int i_2_ = i_0_ / 2;
	((StretchyBox) this).cornerTL
	    = bufferedimage.getSubimage(0, 0, i_1_, i_2_);
	((StretchyBox) this).cornerTR
	    = bufferedimage.getSubimage(i - i_1_, 0, i_1_, i_2_);
	((StretchyBox) this).cornerBL
	    = bufferedimage.getSubimage(0, i_0_ - i_2_, i_1_, i_2_);
	((StretchyBox) this).cornerBR
	    = bufferedimage.getSubimage(i - i_1_, i_0_ - i_2_, i_1_, i_2_);
	((StretchyBox) this).edgeTop
	    = bufferedimage.getSubimage(i_1_, 0, 1, i_2_);
	((StretchyBox) this).edgeBottom
	    = bufferedimage.getSubimage(i_1_, i_0_ - i_2_, 1, i_2_);
	((StretchyBox) this).edgeLeft
	    = bufferedimage.getSubimage(0, i_2_, i_1_, 1);
	((StretchyBox) this).edgeRight
	    = bufferedimage.getSubimage(i - i_1_, i_2_, i_1_, 1);
	((StretchyBox) this).fillColor
	    = new Color(bufferedimage.getRGB(i_1_, i_2_));
    }
    
    public boolean isShowing() {
	return true;
    }
    
    public Rectangle rect() {
	return new Rectangle(((StretchyBox) this).x, ((StretchyBox) this).y,
			     ((StretchyBox) this).w, ((StretchyBox) this).h);
    }
    
    public Rectangle fullRect() {
	return rect();
    }
    
    public void paint(Graphics graphics) {
	if (((StretchyBox) this).cornerTL == null) {
	    graphics.setColor(new Color(100, 100, 250));
	    graphics.fillRect(((StretchyBox) this).x, ((StretchyBox) this).y,
			      ((StretchyBox) this).w, ((StretchyBox) this).h);
	} else {
	    int i = ((StretchyBox) this).cornerTL.getWidth(null);
	    int i_3_ = ((StretchyBox) this).cornerTL.getHeight(null);
	    graphics.setColor(((StretchyBox) this).fillColor);
	    graphics.fillRect(((StretchyBox) this).x + i,
			      ((StretchyBox) this).y + i_3_,
			      ((StretchyBox) this).w - 2 * i,
			      ((StretchyBox) this).h - 2 * i_3_);
	    for (int i_4_ = ((StretchyBox) this).x + i;
		 i_4_ < ((StretchyBox) this).x + ((StretchyBox) this).w - i;
		 i_4_++) {
		graphics.drawImage(((StretchyBox) this).edgeTop, i_4_,
				   ((StretchyBox) this).y, null);
		graphics.drawImage(((StretchyBox) this).edgeBottom, i_4_,
				   (((StretchyBox) this).y
				    + ((StretchyBox) this).h - i_3_),
				   null);
	    }
	    for (int i_5_ = ((StretchyBox) this).y + i_3_;
		 i_5_ < ((StretchyBox) this).y + ((StretchyBox) this).h - i_3_;
		 i_5_++) {
		graphics.drawImage(((StretchyBox) this).edgeLeft,
				   ((StretchyBox) this).x, i_5_, null);
		graphics.drawImage(((StretchyBox) this).edgeRight,
				   (((StretchyBox) this).x
				    + ((StretchyBox) this).w - i),
				   i_5_, null);
	    }
	    graphics.drawImage(((StretchyBox) this).cornerTL,
			       ((StretchyBox) this).x, ((StretchyBox) this).y,
			       null);
	    graphics.drawImage(((StretchyBox) this).cornerTR,
			       (((StretchyBox) this).x + ((StretchyBox) this).w
				- i),
			       ((StretchyBox) this).y, null);
	    graphics.drawImage(((StretchyBox) this).cornerBL,
			       ((StretchyBox) this).x,
			       (((StretchyBox) this).y + ((StretchyBox) this).h
				- i_3_),
			       null);
	    graphics.drawImage(((StretchyBox) this).cornerBR,
			       (((StretchyBox) this).x + ((StretchyBox) this).w
				- i),
			       (((StretchyBox) this).y + ((StretchyBox) this).h
				- i_3_),
			       null);
	}
    }
    
    public void paintBubble(Graphics graphics) {
	/* empty */
    }
    
    public void dragTo(int i, int i_6_) {
	/* empty */
    }
    
    public void mouseDown(int i, int i_7_) {
	/* empty */
    }
}
