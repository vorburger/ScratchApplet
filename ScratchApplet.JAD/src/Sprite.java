// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class Sprite
    implements Drawable
{

    Sprite(LContext lcontext)
    {
        isShowing = true;
        isDraggable = false;
        alpha = 1.0D;
        scale = 1.0D;
        rotationDegrees = 90D;
        penColor = new Color(0, 0, 255);
        penSize = 1;
        filterChanged = false;
        imageFilter = new ImageFilter();
        setPenColor(penColor);
        if(lcontext != null)
            canvas = lcontext.canvas;
    }

    int screenX()
    {
        return 241 + (int)(x - (double)offsetX);
    }

    int screenY()
    {
        return 206 + (int)(-y - (double)offsetY);
    }

    void setscreenX(double d)
    {
        x = (d + (double)offsetX) - 241D;
    }

    void setscreenY(double d)
    {
        y = -((d + (double)offsetY) - 206D);
    }

    public void mouseDown(int i, int j)
    {
    }

    void setStageOffset()
    {
        x = y = 0.0D;
        offsetX = costume.getWidth(null) / 2;
        offsetY = costume.getHeight(null) / 2;
    }

    public void dragTo(int i, int j)
    {
        inval();
        setscreenX(i);
        setscreenY(j);
        updateBubble();
        inval();
    }

    boolean containsPoint(int i, int j)
    {
        BufferedImage bufferedimage = outImage();
        int k = screenX();
        int l = screenY();
        int i1 = bufferedimage.getWidth(null);
        int j1 = bufferedimage.getHeight(null);
        if(i < k || i >= k + i1 || j < l || j >= l + j1)
        {
            return false;
        } else
        {
            int k1 = bufferedimage.getRGB(i - k, j - l);
            return (k1 & 0xff000000) != 0;
        }
    }

    boolean touchingSprite(Object obj, LContext lcontext)
    {
        if(!(obj instanceof Sprite))
        {
            Logo.error("argument must be a Sprite", lcontext);
            return false;
        }
        Sprite sprite = (Sprite)obj;
        Rectangle rectangle = rect().intersection(sprite.rect());
        if(rectangle.width <= 0 || rectangle.height <= 0)
            return false;
        BufferedImage bufferedimage = outImage();
        BufferedImage bufferedimage1 = sprite.outImage();
        int i = rectangle.x - screenX();
        int j = rectangle.y - screenY();
        int k = rectangle.x - sprite.screenX();
        int l = rectangle.y - sprite.screenY();
        for(int i1 = j; i1 < j + rectangle.height; i1++)
        {
            int j1 = k;
            for(int k1 = i; k1 < i + rectangle.width; k1++)
            {
                int l1 = bufferedimage.getRGB(k1, i1);
                int i2 = bufferedimage1.getRGB(j1, l);
                if((l1 & 0xff000000) != 0 && (i2 & 0xff000000) != 0)
                    return true;
                j1++;
            }

            l++;
        }

        return false;
    }

    boolean touchingColor(Object obj, LContext lcontext)
    {
        if(!(obj instanceof Color))
        {
            Logo.error("argument of touchingColor? must be a Color", lcontext);
            return false;
        }
        int i = ((Color)obj).getRGB() | 0xff000000;
        Rectangle rectangle = rect();
        BufferedImage bufferedimage = outImage();
        BufferedImage bufferedimage1 = lcontext.canvas.drawAreaWithoutSprite(rectangle, this);
        for(int j = 0; j < rectangle.height; j++)
        {
            for(int k = 0; k < rectangle.width; k++)
                if((bufferedimage.getRGB(k, j) & 0xff000000) != 0 && colorsMatch(bufferedimage1.getRGB(k, j), i))
                {
                    bufferedimage1.flush();
                    return true;
                }

        }

        bufferedimage1.flush();
        return false;
    }

    boolean colorTouchingColor(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof Color) || !(obj1 instanceof Color))
        {
            Logo.error("the arguments of colorTouchingColor? must be Colors", lcontext);
            return false;
        }
        int i = ((Color)obj).getRGB() | 0xff000000;
        int j = ((Color)obj1).getRGB() | 0xff000000;
        Rectangle rectangle = rect();
        BufferedImage bufferedimage = outImage();
        BufferedImage bufferedimage1 = lcontext.canvas.drawAreaWithoutSprite(rectangle, this);
        for(int k = 0; k < rectangle.height; k++)
        {
            for(int l = 0; l < rectangle.width; l++)
                if(colorsMatch(bufferedimage.getRGB(l, k), i) && colorsMatch(bufferedimage1.getRGB(l, k), j))
                {
                    bufferedimage1.flush();
                    return true;
                }

        }

        bufferedimage1.flush();
        return false;
    }

    boolean colorsMatch(int i, int j)
    {
        if((i & 0xff000000) != (j & 0xff000000))
            return false;
        if((i >> 16 & 0xf8) != (j >> 16 & 0xf8))
            return false;
        if((i >> 8 & 0xf8) != (j >> 8 & 0xf8))
            return false;
        if((i & 0xffff00) == 0 && (j & 0xffff00) == 0 && (i & 0xff) <= 8 && (j & 0xff) <= 8)
            return true;
        return (i & 0xf8) == (j & 0xf8);
    }

    void setalpha(Object obj, LContext lcontext)
    {
        double d = Logo.aDouble(obj, lcontext);
        if(d < 0.0D)
            d = -d;
        if(d > 1.0D)
            d = 1.0D;
        alpha = d;
        inval();
    }

    void setcostume(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof BufferedImage))
            return;
        rotationX = Logo.anInt(obj1, lcontext);
        rotationY = Logo.anInt(obj2, lcontext);
        if(costume != null)
            inval();
        costume = (BufferedImage)obj;
        rotateAndScale();
        inval();
    }

    void costumeChanged()
    {
        inval();
        rotateAndScale();
        inval();
    }

    void setscale(Object obj, LContext lcontext)
    {
        double d = Logo.aDouble(obj, lcontext);
        double d1 = Math.min(costume.getWidth(null), 10);
        double d2 = Math.min(costume.getHeight(null), 10);
        double d3 = Math.max(d1 / 480D, d2 / 360D);
        double d4 = Math.min(480D / (double)costume.getWidth(null), 360D / (double)costume.getHeight(null));
        scale = Math.min(Math.max(d, d3), d4);
        costumeChanged();
    }

    void rotateAndScale()
    {
        filterChanged = true;
        double d = rotationstyle != 0 ? 90D : rotationDegrees;
        if(rotatedCostume != null && rotatedCostume != costume)
            rotatedCostume.flush();
        if(scale == 1.0D && rotationDegrees == 90D)
        {
            rotatedCostume = costume;
            offsetX = rotationX;
            offsetY = rotationY;
            return;
        }
        int i = costume.getWidth(null);
        int j = costume.getHeight(null);
        double d1 = Math.toRadians(d - 90D);
        AffineTransform affinetransform = AffineTransform.getRotateInstance(d1, i / 2, j / 2);
        affinetransform.scale(scale, scale);
        AffineTransformOp affinetransformop = new AffineTransformOp(affinetransform, 2);
        java.awt.geom.Rectangle2D.Float float1 = (java.awt.geom.Rectangle2D.Float)affinetransformop.getBounds2D(costume);
        float f = -float1.x;
        float f1 = -float1.y;
        affinetransform = AffineTransform.getRotateInstance(d1, (float)(i / 2) + f, (float)(j / 2) + f1);
        affinetransform.translate(f, f1);
        affinetransform.scale(scale, scale);
        affinetransformop = new AffineTransformOp(affinetransform, 2);
        rotatedCostume = affinetransformop.filter(costume, null);
        affinetransform = AffineTransform.getRotateInstance(d1, 0.0D, 0.0D);
        affinetransform.scale(scale, scale);
        Point2D point2d = affinetransform.transform(new java.awt.geom.Point2D.Double(rotationX - i / 2, rotationY - j / 2), null);
        offsetX = (int)(point2d.getX() + (double)(rotatedCostume.getWidth(null) / 2));
        offsetY = (int)(point2d.getY() + (double)(rotatedCostume.getHeight(null) / 2));
        if(rotationstyle == 1)
        {
            double d2 = rotationDegrees >= 0.0D ? rotationDegrees : rotationDegrees + 360D;
            if(d2 <= 180D)
                return;
            int k = rotatedCostume.getWidth(null);
            AffineTransform affinetransform1 = AffineTransform.getScaleInstance(-1D, 1.0D);
            affinetransform1.translate(-k, 0.0D);
            AffineTransformOp affinetransformop1 = new AffineTransformOp(affinetransform1, 2);
            rotatedCostume = affinetransformop1.filter(rotatedCostume, null);
            offsetX = (int)((double)(k / 2) - scale * (double)(rotationX - i / 2));
            offsetY = (int)(scale * (double)rotationY);
        }
    }

    void show()
    {
        isShowing = true;
        inval();
    }

    void hide()
    {
        isShowing = false;
        inval();
    }

    public boolean isShowing()
    {
        return isShowing;
    }

    public boolean isVisible()
    {
        return isShowing && alpha > 0.0D;
    }

    public Rectangle rect()
    {
        BufferedImage bufferedimage = outImage();
        if(bufferedimage == null)
            return new Rectangle(screenX(), screenY(), 600, 600);
        else
            return new Rectangle(screenX(), screenY(), bufferedimage.getWidth(null), bufferedimage.getHeight(null));
    }

    public Rectangle fullRect()
    {
        Rectangle rectangle = rect();
        if(bubble != null)
            rectangle = rectangle.union(bubble.rect());
        return rectangle;
    }

    void inval()
    {
        canvas.inval(fullRect());
    }

    public void paint(Graphics g)
    {
        Graphics2D graphics2d = (Graphics2D)g;
        if(filterChanged)
            applyFilters();
        if(alpha != 1.0D)
        {
            java.awt.Composite composite = graphics2d.getComposite();
            graphics2d.setComposite(AlphaComposite.getInstance(3, (float)alpha));
            graphics2d.drawImage(outImage(), screenX(), screenY(), null);
            graphics2d.setComposite(composite);
        } else
        {
            graphics2d.drawImage(outImage(), screenX(), screenY(), null);
        }
    }

    public void paintBubble(Graphics g)
    {
        if(bubble != null)
            bubble.paint(g);
    }

    void talkbubble(Object obj, boolean flag, boolean flag1, LContext lcontext)
    {
        String s = (obj instanceof String) ? (String)obj : Logo.prs(obj);
        inval();
        bubble = null;
        if(s.length() == 0)
            return;
        bubble = new Bubble();
        if(flag)
            bubble.beAskBubble();
        if(!flag1)
            bubble.beThinkBubble(true);
        bubble.setContents(s);
        if(rotationDegrees >= 0.0D && rotationDegrees <= 180D)
            bubble.pointLeft = true;
        updateBubble();
    }

    void updateBubble()
    {
        int i = 3;
        int j = 482 - i;
        if(bubble == null)
            return;
        inval();
        Rectangle rectangle = rect();
        boolean flag = bubble.pointLeft;
        int ai[] = bubbleInsets();
        if(flag && ((rectangle.x + rectangle.width) - ai[1]) + bubble.w + i > j)
            flag = false;
        if(!flag && (rectangle.x + ai[0]) - bubble.w - i < 0)
            flag = true;
        if(flag)
        {
            bubble.pointLeft = true;
            bubble.x = ((rectangle.x + rectangle.width) - ai[1]) + i;
        } else
        {
            bubble.pointLeft = false;
            bubble.x = (rectangle.x + ai[0]) - bubble.w - i;
        }
        if(bubble.x + bubble.w > j)
            bubble.x = j - bubble.w;
        if(bubble.x < i)
            bubble.x = i;
        bubble.y = Math.max(rectangle.y - bubble.h - 12, 25 + i);
        if(bubble.y + bubble.h > 387)
            bubble.y = 387 - bubble.h;
        inval();
    }

    int[] bubbleInsets()
    {
        BufferedImage bufferedimage = outImage();
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int k = i;
        int l = i;
        int i1 = -1;
        for(int j1 = 0; j1 < j; j1++)
        {
            boolean flag = false;
            for(int l1 = 0; l1 < Math.max(k, l); l1++)
            {
                int k1 = bufferedimage.getRGB(l1, j1) & 0xff000000;
                if(k1 != 0 && l1 < k)
                {
                    k = l1;
                    flag = true;
                }
                k1 = bufferedimage.getRGB(i - l1 - 1, j1) & 0xff000000;
                if(k1 != 0 && l1 < l)
                {
                    l = l1;
                    flag = true;
                }
            }

            if(i1 < 0)
            {
                if(flag)
                    i1 = j1;
                continue;
            }
            if(j1 >= i1 + 10)
                break;
        }

        int ai[] = new int[2];
        ai[0] = k;
        ai[1] = l;
        return ai;
    }

    void setPenDown(boolean flag)
    {
        if(flag == penDown)
            return;
        if(flag)
            lastPenX = lastPenY = 0xfff0bdc0;
        canvas.updatePenTrailForSprite(this);
        penDown = flag;
    }

    void setPenColor(Color color1)
    {
        float af[] = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        penColor = color1;
        penHue = 200D * (double)af[0];
        float f = af[1];
        float f1 = af[2];
        if((double)f1 == 1.0D)
            penShade = 50D + 50D * (1.0D - (double)f);
        else
            penShade = 50D * (double)f1;
    }

    void setPenHue(double d)
    {
        penHue = d % 200D;
        if(penHue < 0.0D)
            penHue = 200D + penHue;
        setPenShade(penShade);
    }

    void setPenShade(double d)
    {
        penShade = d % 200D;
        if(penShade < 0.0D)
            penShade = 200D + penShade;
        float f = (float)(penShade <= 100D ? penShade : 200D - penShade);
        if((double)f <= 50D)
        {
            float f1 = (f + 10F) / 60F;
            penColor = new Color(Color.HSBtoRGB((float)(penHue / 200D), 1.0F, f1));
        } else
        {
            float f2 = ((100F - f) + 10F) / 60F;
            penColor = new Color(Color.HSBtoRGB((float)(penHue / 200D), f2, 1.0F));
        }
    }

    BufferedImage outImage()
    {
        if(filteredCostume != null)
            return filteredCostume;
        if(rotatedCostume != null)
            return rotatedCostume;
        else
            return costume;
    }

    void applyFilters()
    {
        if(!filtersActive())
        {
            filteredCostume = null;
            filterChanged = false;
            return;
        }
        imageFilter.setSourceImage(rotatedCostume == null ? costume : rotatedCostume);
        if(color != 0.0D)
            imageFilter.applyHueShift((int)color);
        if(brightness != 0.0D)
            imageFilter.applyBrightnessShift((int)brightness);
        if(whirl != 0.0D)
            imageFilter.applyWhirl(whirl);
        if(fisheye != 0.0D)
            imageFilter.applyFisheye(fisheye);
        if(Math.abs(pixelate) >= 5D)
            imageFilter.applyPixelate(pixelate);
        if(Math.abs(mosaic) >= 5D)
            imageFilter.applyMosaic(mosaic);
        filteredCostume = imageFilter.filteredImage;
        filterChanged = false;
    }

    boolean filtersActive()
    {
        return color != 0.0D || brightness != 0.0D || fisheye != 0.0D || whirl != 0.0D || Math.abs(mosaic) >= 5D || Math.abs(pixelate) >= 5D;
    }

    static final int originX = 241;
    static final int originY = 206;
    PlayerCanvas canvas;
    BufferedImage costume;
    BufferedImage rotatedCostume;
    BufferedImage filteredCostume;
    BufferedImage tempImage;
    double x;
    double y;
    boolean isShowing;
    boolean isDraggable;
    double alpha;
    double scale;
    double rotationDegrees;
    int rotationstyle;
    int rotationX;
    int rotationY;
    int offsetX;
    int offsetY;
    Bubble bubble;
    boolean penDown;
    int lastPenX;
    int lastPenY;
    Color penColor;
    int penSize;
    double penHue;
    double penShade;
    boolean filterChanged;
    double color;
    double brightness;
    double fisheye;
    double whirl;
    double mosaic;
    double pixelate;
    ImageFilter imageFilter;
}
