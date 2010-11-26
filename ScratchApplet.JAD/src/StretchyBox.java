// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.image.BufferedImage;

class StretchyBox
    implements Drawable
{

    StretchyBox()
    {
        w = 100;
        h = 75;
    }

    void setFrameImage(BufferedImage bufferedimage)
    {
        int i = bufferedimage.getWidth(null);
        int j = bufferedimage.getHeight(null);
        int k = i / 2;
        int l = j / 2;
        cornerTL = bufferedimage.getSubimage(0, 0, k, l);
        cornerTR = bufferedimage.getSubimage(i - k, 0, k, l);
        cornerBL = bufferedimage.getSubimage(0, j - l, k, l);
        cornerBR = bufferedimage.getSubimage(i - k, j - l, k, l);
        edgeTop = bufferedimage.getSubimage(k, 0, 1, l);
        edgeBottom = bufferedimage.getSubimage(k, j - l, 1, l);
        edgeLeft = bufferedimage.getSubimage(0, l, k, 1);
        edgeRight = bufferedimage.getSubimage(i - k, l, k, 1);
        fillColor = new Color(bufferedimage.getRGB(k, l));
    }

    public boolean isShowing()
    {
        return true;
    }

    public Rectangle rect()
    {
        return new Rectangle(x, y, w, h);
    }

    public Rectangle fullRect()
    {
        return rect();
    }

    public void paint(Graphics g)
    {
        if(cornerTL == null)
        {
            g.setColor(new Color(100, 100, 250));
            g.fillRect(x, y, w, h);
            return;
        }
        int i = cornerTL.getWidth(null);
        int j = cornerTL.getHeight(null);
        g.setColor(fillColor);
        g.fillRect(x + i, y + j, w - 2 * i, h - 2 * j);
        for(int k = x + i; k < (x + w) - i; k++)
        {
            g.drawImage(edgeTop, k, y, null);
            g.drawImage(edgeBottom, k, (y + h) - j, null);
        }

        for(int l = y + j; l < (y + h) - j; l++)
        {
            g.drawImage(edgeLeft, x, l, null);
            g.drawImage(edgeRight, (x + w) - i, l, null);
        }

        g.drawImage(cornerTL, x, y, null);
        g.drawImage(cornerTR, (x + w) - i, y, null);
        g.drawImage(cornerBL, x, (y + h) - j, null);
        g.drawImage(cornerBR, (x + w) - i, (y + h) - j, null);
    }

    public void paintBubble(Graphics g)
    {
    }

    public void dragTo(int i, int j)
    {
    }

    public void mouseDown(int i, int j)
    {
    }

    int x;
    int y;
    int w;
    int h;
    BufferedImage cornerTL;
    BufferedImage cornerTR;
    BufferedImage cornerBL;
    BufferedImage cornerBR;
    BufferedImage edgeTop;
    BufferedImage edgeBottom;
    BufferedImage edgeLeft;
    BufferedImage edgeRight;
    Color fillColor;
}
