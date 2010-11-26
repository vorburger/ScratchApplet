/* Sprite - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class Sprite implements Drawable
{
    static final int originX = 241;
    static final int originY = 206;
    PlayerCanvas canvas;
    BufferedImage costume;
    BufferedImage rotatedCostume;
    BufferedImage filteredCostume;
    BufferedImage tempImage;
    double x;
    double y;
    boolean isShowing = true;
    boolean isDraggable = false;
    double alpha = 1.0;
    double scale = 1.0;
    double rotationDegrees = 90.0;
    int rotationstyle;
    int rotationX;
    int rotationY;
    int offsetX;
    int offsetY;
    Bubble bubble;
    boolean penDown;
    int lastPenX;
    int lastPenY;
    Color penColor = new Color(0, 0, 255);
    int penSize = 1;
    double penHue;
    double penShade;
    boolean filterChanged = false;
    double color;
    double brightness;
    double fisheye;
    double whirl;
    double mosaic;
    double pixelate;
    ImageFilter imageFilter = new ImageFilter();
    
    Sprite(LContext lcontext) {
	setPenColor(((Sprite) this).penColor);
	if (lcontext != null)
	    ((Sprite) this).canvas = ((LContext) lcontext).canvas;
    }
    
    int screenX() {
	return 241 + (int) (((Sprite) this).x
			    - (double) ((Sprite) this).offsetX);
    }
    
    int screenY() {
	return 206 + (int) (-((Sprite) this).y
			    - (double) ((Sprite) this).offsetY);
    }
    
    void setscreenX(double d) {
	((Sprite) this).x = d + (double) ((Sprite) this).offsetX - 241.0;
    }
    
    void setscreenY(double d) {
	((Sprite) this).y = -(d + (double) ((Sprite) this).offsetY - 206.0);
    }
    
    public void mouseDown(int i, int i_0_) {
	/* empty */
    }
    
    void setStageOffset() {
	((Sprite) this).x = ((Sprite) this).y = 0.0;
	((Sprite) this).offsetX = ((Sprite) this).costume.getWidth(null) / 2;
	((Sprite) this).offsetY = ((Sprite) this).costume.getHeight(null) / 2;
    }
    
    public void dragTo(int i, int i_1_) {
	inval();
	setscreenX((double) i);
	setscreenY((double) i_1_);
	updateBubble();
	inval();
    }
    
    boolean containsPoint(int i, int i_2_) {
	BufferedImage bufferedimage = outImage();
	int i_3_ = screenX();
	int i_4_ = screenY();
	int i_5_ = bufferedimage.getWidth(null);
	int i_6_ = bufferedimage.getHeight(null);
	if (i < i_3_ || i >= i_3_ + i_5_ || i_2_ < i_4_ || i_2_ >= i_4_ + i_6_)
	    return false;
	int i_7_ = bufferedimage.getRGB(i - i_3_, i_2_ - i_4_);
	return (i_7_ & ~0xffffff) != 0;
    }
    
    boolean touchingSprite(Object object, LContext lcontext) {
	if (!(object instanceof Sprite)) {
	    Logo.error("argument must be a Sprite", lcontext);
	    return false;
	}
	Sprite sprite_8_ = (Sprite) object;
	Rectangle rectangle = rect().intersection(sprite_8_.rect());
	if (rectangle.width <= 0 || rectangle.height <= 0)
	    return false;
	BufferedImage bufferedimage = outImage();
	BufferedImage bufferedimage_9_ = sprite_8_.outImage();
	int i = rectangle.x - screenX();
	int i_10_ = rectangle.y - screenY();
	int i_11_ = rectangle.x - sprite_8_.screenX();
	int i_12_ = rectangle.y - sprite_8_.screenY();
	for (int i_13_ = i_10_; i_13_ < i_10_ + rectangle.height; i_13_++) {
	    int i_14_ = i_11_;
	    for (int i_15_ = i; i_15_ < i + rectangle.width; i_15_++) {
		int i_16_ = bufferedimage.getRGB(i_15_, i_13_);
		int i_17_ = bufferedimage_9_.getRGB(i_14_, i_12_);
		if ((i_16_ & ~0xffffff) != 0 && (i_17_ & ~0xffffff) != 0)
		    return true;
		i_14_++;
	    }
	    i_12_++;
	}
	return false;
    }
    
    boolean touchingColor(Object object, LContext lcontext) {
	if (!(object instanceof Color)) {
	    Logo.error("argument of touchingColor? must be a Color", lcontext);
	    return false;
	}
	int i = ((Color) object).getRGB() | ~0xffffff;
	Rectangle rectangle = rect();
	BufferedImage bufferedimage = outImage();
	BufferedImage bufferedimage_18_
	    = ((LContext) lcontext).canvas.drawAreaWithoutSprite(rectangle,
								 this);
	for (int i_19_ = 0; i_19_ < rectangle.height; i_19_++) {
	    for (int i_20_ = 0; i_20_ < rectangle.width; i_20_++) {
		if ((bufferedimage.getRGB(i_20_, i_19_) & ~0xffffff) != 0
		    && colorsMatch(bufferedimage_18_.getRGB(i_20_, i_19_),
				   i)) {
		    bufferedimage_18_.flush();
		    return true;
		}
	    }
	}
	bufferedimage_18_.flush();
	return false;
    }
    
    boolean colorTouchingColor(Object object, Object object_21_,
			       LContext lcontext) {
	if (!(object instanceof Color) || !(object_21_ instanceof Color)) {
	    Logo.error("the arguments of colorTouchingColor? must be Colors",
		       lcontext);
	    return false;
	}
	int i = ((Color) object).getRGB() | ~0xffffff;
	int i_22_ = ((Color) object_21_).getRGB() | ~0xffffff;
	Rectangle rectangle = rect();
	BufferedImage bufferedimage = outImage();
	BufferedImage bufferedimage_23_
	    = ((LContext) lcontext).canvas.drawAreaWithoutSprite(rectangle,
								 this);
	for (int i_24_ = 0; i_24_ < rectangle.height; i_24_++) {
	    for (int i_25_ = 0; i_25_ < rectangle.width; i_25_++) {
		if (colorsMatch(bufferedimage.getRGB(i_25_, i_24_), i)
		    && colorsMatch(bufferedimage_23_.getRGB(i_25_, i_24_),
				   i_22_)) {
		    bufferedimage_23_.flush();
		    return true;
		}
	    }
	}
	bufferedimage_23_.flush();
	return false;
    }
    
    boolean colorsMatch(int i, int i_26_) {
	if ((i & ~0xffffff) != (i_26_ & ~0xffffff))
	    return false;
	if ((i >> 16 & 0xf8) != (i_26_ >> 16 & 0xf8))
	    return false;
	if ((i >> 8 & 0xf8) != (i_26_ >> 8 & 0xf8))
	    return false;
	if ((i & 0xffff00) == 0 && (i_26_ & 0xffff00) == 0 && (i & 0xff) <= 8
	    && (i_26_ & 0xff) <= 8)
	    return true;
	if ((i & 0xf8) != (i_26_ & 0xf8))
	    return false;
	return true;
    }
    
    void setalpha(Object object, LContext lcontext) {
	double d = Logo.aDouble(object, lcontext);
	if (d < 0.0)
	    d = -d;
	if (d > 1.0)
	    d = 1.0;
	((Sprite) this).alpha = d;
	inval();
    }
    
    void setcostume(Object object, Object object_27_, Object object_28_,
		    LContext lcontext) {
	if (object instanceof BufferedImage) {
	    ((Sprite) this).rotationX = Logo.anInt(object_27_, lcontext);
	    ((Sprite) this).rotationY = Logo.anInt(object_28_, lcontext);
	    if (((Sprite) this).costume != null)
		inval();
	    ((Sprite) this).costume = (BufferedImage) object;
	    rotateAndScale();
	    inval();
	}
    }
    
    void costumeChanged() {
	inval();
	rotateAndScale();
	inval();
    }
    
    void setscale(Object object, LContext lcontext) {
	double d = Logo.aDouble(object, lcontext);
	double d_29_
	    = (double) Math.min(((Sprite) this).costume.getWidth(null), 10);
	double d_30_
	    = (double) Math.min(((Sprite) this).costume.getHeight(null), 10);
	double d_31_ = Math.max(d_29_ / 480.0, d_30_ / 360.0);
	double d_32_
	    = Math.min(480.0 / (double) ((Sprite) this).costume.getWidth(null),
		       (360.0
			/ (double) ((Sprite) this).costume.getHeight(null)));
	((Sprite) this).scale = Math.min(Math.max(d, d_31_), d_32_);
	costumeChanged();
    }
    
    void rotateAndScale() {
	((Sprite) this).filterChanged = true;
	double d = (((Sprite) this).rotationstyle == 0
		    ? ((Sprite) this).rotationDegrees : 90.0);
	if (((Sprite) this).rotatedCostume != null
	    && ((Sprite) this).rotatedCostume != ((Sprite) this).costume)
	    ((Sprite) this).rotatedCostume.flush();
	if (((Sprite) this).scale == 1.0
	    && ((Sprite) this).rotationDegrees == 90.0) {
	    ((Sprite) this).rotatedCostume = ((Sprite) this).costume;
	    ((Sprite) this).offsetX = ((Sprite) this).rotationX;
	    ((Sprite) this).offsetY = ((Sprite) this).rotationY;
	} else {
	    int i = ((Sprite) this).costume.getWidth(null);
	    int i_33_ = ((Sprite) this).costume.getHeight(null);
	    double d_34_ = Math.toRadians(d - 90.0);
	    AffineTransform affinetransform
		= AffineTransform.getRotateInstance(d_34_, (double) (i / 2),
						    (double) (i_33_ / 2));
	    affinetransform.scale(((Sprite) this).scale,
				  ((Sprite) this).scale);
	    AffineTransformOp affinetransformop
		= new AffineTransformOp(affinetransform, 2);
	    Rectangle2D.Float var_float
		= ((Rectangle2D.Float)
		   affinetransformop.getBounds2D(((Sprite) this).costume));
	    float f = -var_float.x;
	    float f_35_ = -var_float.y;
	    affinetransform
		= AffineTransform.getRotateInstance(d_34_,
						    (double) ((float) (i / 2)
							      + f),
						    (double) ((float) (i_33_
								       / 2)
							      + f_35_));
	    affinetransform.translate((double) f, (double) f_35_);
	    affinetransform.scale(((Sprite) this).scale,
				  ((Sprite) this).scale);
	    affinetransformop = new AffineTransformOp(affinetransform, 2);
	    ((Sprite) this).rotatedCostume
		= affinetransformop.filter(((Sprite) this).costume, null);
	    affinetransform
		= AffineTransform.getRotateInstance(d_34_, 0.0, 0.0);
	    affinetransform.scale(((Sprite) this).scale,
				  ((Sprite) this).scale);
	    Point2D point2d
		= (affinetransform.transform
		   (new Point2D.Double((double) (((Sprite) this).rotationX
						 - i / 2),
				       (double) (((Sprite) this).rotationY
						 - i_33_ / 2)),
		    null));
	    ((Sprite) this).offsetX
		= (int) (point2d.getX()
			 + (double) (((Sprite) this).rotatedCostume
					 .getWidth(null)
				     / 2));
	    ((Sprite) this).offsetY
		= (int) (point2d.getY()
			 + (double) (((Sprite) this).rotatedCostume
					 .getHeight(null)
				     / 2));
	    if (((Sprite) this).rotationstyle == 1) {
		double d_36_ = (((Sprite) this).rotationDegrees < 0.0
				? ((Sprite) this).rotationDegrees + 360.0
				: ((Sprite) this).rotationDegrees);
		if (!(d_36_ <= 180.0)) {
		    int i_37_ = ((Sprite) this).rotatedCostume.getWidth(null);
		    affinetransform
			= AffineTransform.getScaleInstance(-1.0, 1.0);
		    affinetransform.translate((double) -i_37_, 0.0);
		    affinetransformop
			= new AffineTransformOp(affinetransform, 2);
		    ((Sprite) this).rotatedCostume
			= affinetransformop
			      .filter(((Sprite) this).rotatedCostume, null);
		    ((Sprite) this).offsetX
			= (int) ((double) (i_37_ / 2)
				 - (((Sprite) this).scale
				    * (double) (((Sprite) this).rotationX
						- i / 2)));
		    ((Sprite) this).offsetY
			= (int) (((Sprite) this).scale
				 * (double) ((Sprite) this).rotationY);
		}
	    }
	}
    }
    
    void show() {
	((Sprite) this).isShowing = true;
	inval();
    }
    
    void hide() {
	((Sprite) this).isShowing = false;
	inval();
    }
    
    public boolean isShowing() {
	return ((Sprite) this).isShowing;
    }
    
    public boolean isVisible() {
	return ((Sprite) this).isShowing && ((Sprite) this).alpha > 0.0;
    }
    
    public Rectangle rect() {
	BufferedImage bufferedimage = outImage();
	if (bufferedimage == null)
	    return new Rectangle(screenX(), screenY(), 600, 600);
	return new Rectangle(screenX(), screenY(),
			     bufferedimage.getWidth(null),
			     bufferedimage.getHeight(null));
    }
    
    public Rectangle fullRect() {
	Rectangle rectangle = rect();
	if (((Sprite) this).bubble != null)
	    rectangle = rectangle.union(((Sprite) this).bubble.rect());
	return rectangle;
    }
    
    void inval() {
	((Sprite) this).canvas.inval(fullRect());
    }
    
    public void paint(Graphics graphics) {
	Graphics2D graphics2d = (Graphics2D) graphics;
	if (((Sprite) this).filterChanged)
	    applyFilters();
	if (((Sprite) this).alpha != 1.0) {
	    Composite composite = graphics2d.getComposite();
	    graphics2d.setComposite
		(AlphaComposite.getInstance(3, (float) ((Sprite) this).alpha));
	    graphics2d.drawImage(outImage(), screenX(), screenY(), null);
	    graphics2d.setComposite(composite);
	} else
	    graphics2d.drawImage(outImage(), screenX(), screenY(), null);
    }
    
    public void paintBubble(Graphics graphics) {
	if (((Sprite) this).bubble != null)
	    ((Sprite) this).bubble.paint(graphics);
    }
    
    void talkbubble(Object object, boolean bool, boolean bool_38_,
		    LContext lcontext) {
	String string
	    = object instanceof String ? (String) object : Logo.prs(object);
	inval();
	((Sprite) this).bubble = null;
	if (string.length() != 0) {
	    ((Sprite) this).bubble = new Bubble();
	    if (bool)
		((Sprite) this).bubble.beAskBubble();
	    if (!bool_38_)
		((Sprite) this).bubble.beThinkBubble(true);
	    ((Sprite) this).bubble.setContents(string);
	    if (((Sprite) this).rotationDegrees >= 0.0
		&& ((Sprite) this).rotationDegrees <= 180.0)
		((Bubble) ((Sprite) this).bubble).pointLeft = true;
	    updateBubble();
	}
    }
    
    void updateBubble() {
	int i = 3;
	int i_39_ = 482 - i;
	if (((Sprite) this).bubble != null) {
	    inval();
	    Rectangle rectangle = rect();
	    boolean bool = ((Bubble) ((Sprite) this).bubble).pointLeft;
	    int[] is = bubbleInsets();
	    if (bool && (rectangle.x + rectangle.width - is[1]
			 + ((Bubble) ((Sprite) this).bubble).w + i) > i_39_)
		bool = false;
	    if (!bool && (rectangle.x + is[0]
			  - ((Bubble) ((Sprite) this).bubble).w - i) < 0)
		bool = true;
	    if (bool) {
		((Bubble) ((Sprite) this).bubble).pointLeft = true;
		((Bubble) ((Sprite) this).bubble).x
		    = rectangle.x + rectangle.width - is[1] + i;
	    } else {
		((Bubble) ((Sprite) this).bubble).pointLeft = false;
		((Bubble) ((Sprite) this).bubble).x
		    = (rectangle.x + is[0]
		       - ((Bubble) ((Sprite) this).bubble).w - i);
	    }
	    if ((((Bubble) ((Sprite) this).bubble).x
		 + ((Bubble) ((Sprite) this).bubble).w)
		> i_39_)
		((Bubble) ((Sprite) this).bubble).x
		    = i_39_ - ((Bubble) ((Sprite) this).bubble).w;
	    if (((Bubble) ((Sprite) this).bubble).x < i)
		((Bubble) ((Sprite) this).bubble).x = i;
	    ((Bubble) ((Sprite) this).bubble).y
		= Math.max((rectangle.y - ((Bubble) ((Sprite) this).bubble).h
			    - 12),
			   25 + i);
	    if ((((Bubble) ((Sprite) this).bubble).y
		 + ((Bubble) ((Sprite) this).bubble).h)
		> 387)
		((Bubble) ((Sprite) this).bubble).y
		    = 387 - ((Bubble) ((Sprite) this).bubble).h;
	    inval();
	}
    }
    
    int[] bubbleInsets() {
	BufferedImage bufferedimage = outImage();
	int i = bufferedimage.getWidth();
	int i_40_ = bufferedimage.getHeight();
	int i_41_ = i;
	int i_42_ = i;
	int i_43_ = -1;
	for (int i_44_ = 0; i_44_ < i_40_; i_44_++) {
	    boolean bool = false;
	    for (int i_45_ = 0; i_45_ < Math.max(i_41_, i_42_); i_45_++) {
		int i_46_ = bufferedimage.getRGB(i_45_, i_44_) & ~0xffffff;
		if (i_46_ != 0 && i_45_ < i_41_) {
		    i_41_ = i_45_;
		    bool = true;
		}
		i_46_ = bufferedimage.getRGB(i - i_45_ - 1, i_44_) & ~0xffffff;
		if (i_46_ != 0 && i_45_ < i_42_) {
		    i_42_ = i_45_;
		    bool = true;
		}
	    }
	    if (i_43_ < 0) {
		if (bool)
		    i_43_ = i_44_;
	    } else if (i_44_ >= i_43_ + 10)
		break;
	}
	int[] is = new int[2];
	is[0] = i_41_;
	is[1] = i_42_;
	return is;
    }
    
    void setPenDown(boolean bool) {
	if (bool != ((Sprite) this).penDown) {
	    if (bool)
		((Sprite) this).lastPenX = ((Sprite) this).lastPenY = -1000000;
	    ((Sprite) this).canvas.updatePenTrailForSprite(this);
	    ((Sprite) this).penDown = bool;
	}
    }
    
    void setPenColor(Color color) {
	float[] fs = Color.RGBtoHSB(color.getRed(), color.getGreen(),
				    color.getBlue(), null);
	((Sprite) this).penColor = color;
	((Sprite) this).penHue = 200.0 * (double) fs[0];
	float f = fs[1];
	float f_47_ = fs[2];
	if ((double) f_47_ == 1.0)
	    ((Sprite) this).penShade = 50.0 + 50.0 * (1.0 - (double) f);
	else
	    ((Sprite) this).penShade = 50.0 * (double) f_47_;
    }
    
    void setPenHue(double d) {
	((Sprite) this).penHue = d % 200.0;
	if (((Sprite) this).penHue < 0.0)
	    ((Sprite) this).penHue = 200.0 + ((Sprite) this).penHue;
	setPenShade(((Sprite) this).penShade);
    }
    
    void setPenShade(double d) {
	((Sprite) this).penShade = d % 200.0;
	if (((Sprite) this).penShade < 0.0)
	    ((Sprite) this).penShade = 200.0 + ((Sprite) this).penShade;
	float f = (float) (((Sprite) this).penShade > 100.0
			   ? 200.0 - ((Sprite) this).penShade
			   : ((Sprite) this).penShade);
	if ((double) f <= 50.0) {
	    float f_48_ = (f + 10.0F) / 60.0F;
	    ((Sprite) this).penColor
		= new Color(Color.HSBtoRGB((float) (((Sprite) this).penHue
						    / 200.0),
					   1.0F, f_48_));
	} else {
	    float f_49_ = (100.0F - f + 10.0F) / 60.0F;
	    ((Sprite) this).penColor
		= new Color(Color.HSBtoRGB((float) (((Sprite) this).penHue
						    / 200.0),
					   f_49_, 1.0F));
	}
    }
    
    BufferedImage outImage() {
	if (((Sprite) this).filteredCostume != null)
	    return ((Sprite) this).filteredCostume;
	if (((Sprite) this).rotatedCostume != null)
	    return ((Sprite) this).rotatedCostume;
	return ((Sprite) this).costume;
    }
    
    void applyFilters() {
	if (!filtersActive()) {
	    ((Sprite) this).filteredCostume = null;
	    ((Sprite) this).filterChanged = false;
	} else {
	    ((Sprite) this).imageFilter.setSourceImage
		(((Sprite) this).rotatedCostume != null
		 ? ((Sprite) this).rotatedCostume : ((Sprite) this).costume);
	    if (((Sprite) this).color != 0.0)
		((Sprite) this).imageFilter
		    .applyHueShift((int) ((Sprite) this).color);
	    if (((Sprite) this).brightness != 0.0)
		((Sprite) this).imageFilter
		    .applyBrightnessShift((int) ((Sprite) this).brightness);
	    if (((Sprite) this).whirl != 0.0)
		((Sprite) this).imageFilter.applyWhirl(((Sprite) this).whirl);
	    if (((Sprite) this).fisheye != 0.0)
		((Sprite) this).imageFilter
		    .applyFisheye(((Sprite) this).fisheye);
	    if (Math.abs(((Sprite) this).pixelate) >= 5.0)
		((Sprite) this).imageFilter
		    .applyPixelate(((Sprite) this).pixelate);
	    if (Math.abs(((Sprite) this).mosaic) >= 5.0)
		((Sprite) this).imageFilter
		    .applyMosaic(((Sprite) this).mosaic);
	    ((Sprite) this).filteredCostume
		= ((ImageFilter) ((Sprite) this).imageFilter).filteredImage;
	    ((Sprite) this).filterChanged = false;
	}
    }
    
    boolean filtersActive() {
	if (((Sprite) this).color != 0.0 || ((Sprite) this).brightness != 0.0
	    || ((Sprite) this).fisheye != 0.0 || ((Sprite) this).whirl != 0.0
	    || Math.abs(((Sprite) this).mosaic) >= 5.0
	    || Math.abs(((Sprite) this).pixelate) >= 5.0)
	    return true;
	return false;
    }
}
