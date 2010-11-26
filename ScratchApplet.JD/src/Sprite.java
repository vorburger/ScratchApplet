import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class Sprite
  implements Drawable
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
  double alpha = 1.0D;
  double scale = 1.0D;
  double rotationDegrees = 90.0D;
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

  Sprite(LContext paramLContext) {
    setPenColor(this.penColor);
    if (paramLContext != null) this.canvas = paramLContext.canvas; 
  }

  int screenX() {
    return 241 + (int)(this.x - this.offsetX); } 
  int screenY() { return 206 + (int)(-this.y - this.offsetY); } 
  void setscreenX(double paramDouble) { this.x = (paramDouble + this.offsetX - 241.0D); } 
  void setscreenY(double paramDouble) { this.y = (-(paramDouble + this.offsetY - 206.0D)); }

  public void mouseDown(int paramInt1, int paramInt2) {
  }

  void setStageOffset() {
    this.x = (this.y = 0.0D);
    this.offsetX = (this.costume.getWidth(null) / 2);
    this.offsetY = (this.costume.getHeight(null) / 2);
  }

  public void dragTo(int paramInt1, int paramInt2) {
    inval();
    setscreenX(paramInt1);
    setscreenY(paramInt2);
    updateBubble();
    inval();
  }

  boolean containsPoint(int paramInt1, int paramInt2) {
    BufferedImage localBufferedImage = outImage();
    int i = screenX();
    int j = screenY();
    int k = localBufferedImage.getWidth(null);
    int m = localBufferedImage.getHeight(null);
    if ((paramInt1 < i) || (paramInt1 >= i + k) || (paramInt2 < j) || (paramInt2 >= j + m))
      return false;
    int n = localBufferedImage.getRGB(paramInt1 - i, paramInt2 - j);
    return (n & 0xFF000000) != 0;
  }

  boolean touchingSprite(Object paramObject, LContext paramLContext) {
    if (!(paramObject instanceof Sprite)) {
      Logo.error("argument must be a Sprite", paramLContext);
      return false;
    }
    Sprite localSprite = (Sprite)paramObject;
    Rectangle localRectangle = rect().intersection(localSprite.rect());
    if ((localRectangle.width <= 0) || (localRectangle.height <= 0)) return false;

    BufferedImage localBufferedImage1 = outImage();
    BufferedImage localBufferedImage2 = localSprite.outImage();

    int i = localRectangle.x - screenX();
    int j = localRectangle.y - screenY();
    int k = localRectangle.x - localSprite.screenX();
    int m = localRectangle.y - localSprite.screenY();
    for (int n = j; n < j + localRectangle.height; n++) {
      int i1 = k;
      for (int i2 = i; i2 < i + localRectangle.width; i2++) {
        int i3 = localBufferedImage1.getRGB(i2, n);
        int i4 = localBufferedImage2.getRGB(i1, m);
        if (((i3 & 0xFF000000) != 0) && ((i4 & 0xFF000000) != 0)) return true;
        i1++;
      }
      m++;
    }
    return false;
  }

  boolean touchingColor(Object paramObject, LContext paramLContext) {
    if (!(paramObject instanceof Color)) {
      Logo.error("argument of touchingColor? must be a Color", paramLContext);
      return false;
    }
    int i = ((Color)paramObject).getRGB() | 0xFF000000;
    Rectangle localRectangle = rect();
    BufferedImage localBufferedImage1 = outImage();
    BufferedImage localBufferedImage2 = paramLContext.canvas.drawAreaWithoutSprite(localRectangle, this);

    for (int j = 0; j < localRectangle.height; j++) {
      for (int k = 0; k < localRectangle.width; k++) {
        if (((localBufferedImage1.getRGB(k, j) & 0xFF000000) == 0) || 
          (!colorsMatch(localBufferedImage2.getRGB(k, j), i))) continue;
        localBufferedImage2.flush();
        return true;
      }

    }

    localBufferedImage2.flush();
    return false;
  }

  boolean colorTouchingColor(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if ((!(paramObject1 instanceof Color)) || (!(paramObject2 instanceof Color))) {
      Logo.error("the arguments of colorTouchingColor? must be Colors", paramLContext);
      return false;
    }
    int i = ((Color)paramObject1).getRGB() | 0xFF000000;
    int j = ((Color)paramObject2).getRGB() | 0xFF000000;
    Rectangle localRectangle = rect();
    BufferedImage localBufferedImage1 = outImage();
    BufferedImage localBufferedImage2 = paramLContext.canvas.drawAreaWithoutSprite(localRectangle, this);

    for (int k = 0; k < localRectangle.height; k++) {
      for (int m = 0; m < localRectangle.width; m++) {
        if ((colorsMatch(localBufferedImage1.getRGB(m, k), i)) && (colorsMatch(localBufferedImage2.getRGB(m, k), j))) {
          localBufferedImage2.flush();
          return true;
        }
      }
    }
    localBufferedImage2.flush();
    return false;
  }

  boolean colorsMatch(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xFF000000) != (paramInt2 & 0xFF000000)) return false;
    if ((paramInt1 >> 16 & 0xF8) != (paramInt2 >> 16 & 0xF8)) return false;
    if ((paramInt1 >> 8 & 0xF8) != (paramInt2 >> 8 & 0xF8)) return false;
    if (((paramInt1 & 0xFFFF00) == 0) && ((paramInt2 & 0xFFFF00) == 0) && 
      ((paramInt1 & 0xFF) <= 8) && ((paramInt2 & 0xFF) <= 8)) return true;

    return (paramInt1 & 0xF8) == (paramInt2 & 0xF8);
  }

  void setalpha(Object paramObject, LContext paramLContext)
  {
    double d = Logo.aDouble(paramObject, paramLContext);
    if (d < 0.0D) d = -d;
    if (d > 1.0D) d = 1.0D;
    this.alpha = d;
    inval();
  }

  void setcostume(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof BufferedImage)) return;
    this.rotationX = Logo.anInt(paramObject2, paramLContext);
    this.rotationY = Logo.anInt(paramObject3, paramLContext);
    if (this.costume != null) inval();
    this.costume = ((BufferedImage)paramObject1);
    rotateAndScale();
    inval();
  }

  void costumeChanged() {
    inval();
    rotateAndScale();
    inval();
  }

  void setscale(Object paramObject, LContext paramLContext) {
    double d1 = Logo.aDouble(paramObject, paramLContext);
    double d2 = Math.min(this.costume.getWidth(null), 10);
    double d3 = Math.min(this.costume.getHeight(null), 10);
    double d4 = Math.max(d2 / 480.0D, d3 / 360.0D);
    double d5 = Math.min(480.0D / this.costume.getWidth(null), 360.0D / this.costume.getHeight(null));
    this.scale = Math.min(Math.max(d1, d4), d5);
    costumeChanged();
  }

  void rotateAndScale()
  {
    this.filterChanged = true;
    double d1 = this.rotationstyle == 0 ? this.rotationDegrees : 90.0D;

    if ((this.rotatedCostume != null) && (this.rotatedCostume != this.costume)) this.rotatedCostume.flush();
    if ((this.scale == 1.0D) && (this.rotationDegrees == 90.0D)) {
      this.rotatedCostume = this.costume;
      this.offsetX = this.rotationX;
      this.offsetY = this.rotationY;
      return;
    }

    int i = this.costume.getWidth(null);
    int j = this.costume.getHeight(null);

    double d2 = Math.toRadians(d1 - 90.0D);
    AffineTransform localAffineTransform = AffineTransform.getRotateInstance(d2, i / 2, j / 2);
    localAffineTransform.scale(this.scale, this.scale);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 2);

    Rectangle2D.Float localFloat = (Rectangle2D.Float)localAffineTransformOp.getBounds2D(this.costume);
    float f1 = -localFloat.x;
    float f2 = -localFloat.y;
    localAffineTransform = AffineTransform.getRotateInstance(d2, i / 2 + f1, j / 2 + f2);
    localAffineTransform.translate(f1, f2);
    localAffineTransform.scale(this.scale, this.scale);
    localAffineTransformOp = new AffineTransformOp(localAffineTransform, 2);
    this.rotatedCostume = localAffineTransformOp.filter(this.costume, null);

    localAffineTransform = AffineTransform.getRotateInstance(d2, 0.0D, 0.0D);
    localAffineTransform.scale(this.scale, this.scale);
    Point2D localPoint2D = localAffineTransform.transform(new Point2D.Double(this.rotationX - i / 2, this.rotationY - j / 2), null);

    this.offsetX = (int)(localPoint2D.getX() + this.rotatedCostume.getWidth(null) / 2);
    this.offsetY = (int)(localPoint2D.getY() + this.rotatedCostume.getHeight(null) / 2);

    if (this.rotationstyle == 1) {
      double d3 = this.rotationDegrees < 0.0D ? this.rotationDegrees + 360.0D : this.rotationDegrees;
      if (d3 <= 180.0D) return;

      int k = this.rotatedCostume.getWidth(null);
      localAffineTransform = AffineTransform.getScaleInstance(-1.0D, 1.0D);
      localAffineTransform.translate(-k, 0.0D);
      localAffineTransformOp = new AffineTransformOp(localAffineTransform, 2);
      this.rotatedCostume = localAffineTransformOp.filter(this.rotatedCostume, null);

      this.offsetX = (int)(k / 2 - this.scale * (this.rotationX - i / 2));
      this.offsetY = (int)(this.scale * this.rotationY);
    }
  }

  void show() {
    this.isShowing = true; inval(); } 
  void hide() { this.isShowing = false; inval(); } 
  public boolean isShowing() { return this.isShowing; } 
  public boolean isVisible() { return (this.isShowing) && (this.alpha > 0.0D); }

  public Rectangle rect() {
    BufferedImage localBufferedImage = outImage();
    if (localBufferedImage == null) {
      return new Rectangle(screenX(), screenY(), 600, 600);
    }
    return new Rectangle(screenX(), screenY(), localBufferedImage.getWidth(null), localBufferedImage.getHeight(null));
  }

  public Rectangle fullRect() {
    Rectangle localRectangle = rect();
    if (this.bubble != null) localRectangle = localRectangle.union(this.bubble.rect());
    return localRectangle;
  }
  void inval() {
    this.canvas.inval(fullRect());
  }
  public void paint(Graphics paramGraphics) {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    if (this.filterChanged) applyFilters();
    if (this.alpha != 1.0D) {
      Composite localComposite = localGraphics2D.getComposite();
      localGraphics2D.setComposite(AlphaComposite.getInstance(3, (float)this.alpha));
      localGraphics2D.drawImage(outImage(), screenX(), screenY(), null);
      localGraphics2D.setComposite(localComposite);
    } else {
      localGraphics2D.drawImage(outImage(), screenX(), screenY(), null);
    }
  }

  public void paintBubble(Graphics paramGraphics) {
    if (this.bubble != null) this.bubble.paint(paramGraphics); 
  }

  void talkbubble(Object paramObject, boolean paramBoolean1, boolean paramBoolean2, LContext paramLContext)
  {
    String str = (paramObject instanceof String) ? (String)paramObject : Logo.prs(paramObject);
    inval();
    this.bubble = null;
    if (str.length() == 0) return;
    this.bubble = new Bubble();
    if (paramBoolean1) this.bubble.beAskBubble();
    if (!paramBoolean2) this.bubble.beThinkBubble(true);
    this.bubble.setContents(str);
    if ((this.rotationDegrees >= 0.0D) && (this.rotationDegrees <= 180.0D)) this.bubble.pointLeft = true;
    updateBubble();
  }

  void updateBubble() {
    int i = 3;
    int j = 482 - i;
    if (this.bubble == null) return;
    inval();

    Rectangle localRectangle = rect();
    boolean bool = this.bubble.pointLeft;
    int[] arrayOfInt = bubbleInsets();
    if ((bool) && (localRectangle.x + localRectangle.width - arrayOfInt[1] + this.bubble.w + i > j)) bool = false;
    if ((!bool) && (localRectangle.x + arrayOfInt[0] - this.bubble.w - i < 0)) bool = true;

    if (bool) {
      this.bubble.pointLeft = true;
      this.bubble.x = (localRectangle.x + localRectangle.width - arrayOfInt[1] + i);
    } else {
      this.bubble.pointLeft = false;
      this.bubble.x = (localRectangle.x + arrayOfInt[0] - this.bubble.w - i);
    }

    if (this.bubble.x + this.bubble.w > j) this.bubble.x = (j - this.bubble.w);
    if (this.bubble.x < i) this.bubble.x = i;
    this.bubble.y = Math.max(localRectangle.y - this.bubble.h - 12, 25 + i);
    if (this.bubble.y + this.bubble.h > 387) {
      this.bubble.y = (387 - this.bubble.h);
    }
    inval();
  }

  int[] bubbleInsets() {
    BufferedImage localBufferedImage = outImage();
    int i = localBufferedImage.getWidth();
    int j = localBufferedImage.getHeight();
    int k = i;
    int m = i;
    int n = -1;

    for (int i1 = 0; i1 < j; i1++) {
      int i2 = 0;

      for (int i4 = 0; i4 < Math.max(k, m); i4++) {
        int i3 = localBufferedImage.getRGB(i4, i1) & 0xFF000000;
        if ((i3 != 0) && (i4 < k)) { k = i4; i2 = 1; }
        i3 = localBufferedImage.getRGB(i - i4 - 1, i1) & 0xFF000000;
        if ((i3 == 0) || (i4 >= m)) continue; m = i4; i2 = 1;
      }
      if (n < 0) {
        if (i2 == 0) continue; n = i1;
      } else {
        if (i1 >= n + 10) break;
      }
    }
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = k;
    arrayOfInt[1] = m;
    return arrayOfInt;
  }

  void setPenDown(boolean paramBoolean) {
    if (paramBoolean == this.penDown) return;
    if (paramBoolean) this.lastPenX = (this.lastPenY = -1000000);
    this.canvas.updatePenTrailForSprite(this);
    this.penDown = paramBoolean;
  }

  void setPenColor(Color paramColor) {
    float[] arrayOfFloat = Color.RGBtoHSB(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), null);
    this.penColor = paramColor;
    this.penHue = (200.0D * arrayOfFloat[0]);
    float f1 = arrayOfFloat[1];
    float f2 = arrayOfFloat[2];
    if (f2 == 1.0D)
      this.penShade = (50.0D + 50.0D * (1.0D - f1));
    else
      this.penShade = (50.0D * f2);
  }

  void setPenHue(double paramDouble)
  {
    this.penHue = (paramDouble % 200.0D);
    if (this.penHue < 0.0D) this.penHue = (200.0D + this.penHue);
    setPenShade(this.penShade);
  }

  void setPenShade(double paramDouble) {
    this.penShade = (paramDouble % 200.0D);
    if (this.penShade < 0.0D) this.penShade = (200.0D + this.penShade);
    float f1 = (float)(this.penShade > 100.0D ? 200.0D - this.penShade : this.penShade);
    float f2;
    if (f1 <= 50.0D) {
      f2 = (f1 + 10.0F) / 60.0F;
      this.penColor = new Color(Color.HSBtoRGB((float)(this.penHue / 200.0D), 1.0F, f2));
    } else {
      f2 = (100.0F - f1 + 10.0F) / 60.0F;
      this.penColor = new Color(Color.HSBtoRGB((float)(this.penHue / 200.0D), f2, 1.0F));
    }
  }

  BufferedImage outImage() {
    if (this.filteredCostume != null) return this.filteredCostume;
    if (this.rotatedCostume != null) return this.rotatedCostume;
    return this.costume;
  }

  void applyFilters() {
    if (!filtersActive()) {
      this.filteredCostume = null;
      this.filterChanged = false;
      return;
    }
    this.imageFilter.setSourceImage(this.rotatedCostume != null ? this.rotatedCostume : this.costume);
    if (this.color != 0.0D) this.imageFilter.applyHueShift((int)this.color);
    if (this.brightness != 0.0D) this.imageFilter.applyBrightnessShift((int)this.brightness);
    if (this.whirl != 0.0D) this.imageFilter.applyWhirl(this.whirl);
    if (this.fisheye != 0.0D) this.imageFilter.applyFisheye(this.fisheye);
    if (Math.abs(this.pixelate) >= 5.0D) this.imageFilter.applyPixelate(this.pixelate);
    if (Math.abs(this.mosaic) >= 5.0D) this.imageFilter.applyMosaic(this.mosaic);
    this.filteredCostume = this.imageFilter.filteredImage;
    this.filterChanged = false;
  }

  boolean filtersActive()
  {
    return (this.color != 0.0D) || (this.brightness != 0.0D) || (this.fisheye != 0.0D) || (this.whirl != 0.0D) || (Math.abs(this.mosaic) >= 5.0D) || (Math.abs(this.pixelate) >= 5.0D);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Sprite
 * JD-Core Version:    0.6.0
 */