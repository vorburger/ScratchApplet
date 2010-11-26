// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

class WatcherReadout
{

    WatcherReadout(boolean flag)
    {
        w = 40;
        h = 14;
        color = new Color(100, 60, 20);
        contents = "123";
        isLarge = false;
        beLarge(flag);
    }

    void beLarge(boolean flag)
    {
        if(isLarge == flag)
        {
            return;
        } else
        {
            isLarge = flag;
            w = isLarge ? 50 : 40;
            h = isLarge ? 23 : 14;
            return;
        }
    }

    void adjustWidth(Graphics g)
    {
        Font font = isLarge ? bigFont : smallFont;
        w = Math.max(w, stringWidth(contents, font, g) + 12);
    }

    void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x + 2, y, w - 4, 1);
        g.fillRect(x + 2, (y + h) - 1, w - 4, 1);
        g.fillRect(x, y + 2, 1, h - 4);
        g.fillRect((x + w) - 1, y + 2, 1, h - 4);
        g.fillRect(x + 1, y + 1, 1, 1);
        g.fillRect((x + w) - 2, y + 1, 1, 1);
        g.fillRect(x + 1, (y + h) - 2, 1, 1);
        g.fillRect((x + w) - 2, (y + h) - 2, 1, 1);
        g.setColor(darker(darker(color)));
        g.fillRect(x + 2, y + 1, w - 4, 1);
        g.fillRect(x + 1, y + 2, 1, h - 4);
        g.setColor(darker(color));
        g.fillRect(x + 2, y + 2, w - 4, 1);
        g.fillRect(x + 2, (y + h) - 2, w - 4, 1);
        g.fillRect(x + 2, y + 2, 1, h - 3);
        g.fillRect((x + w) - 2, y + 2, 1, h - 4);
        g.setColor(color);
        g.fillRect(x + 3, y + 3, w - 5, h - 5);
        Font font = isLarge ? bigFont : smallFont;
        byte byte0 = ((byte)(isLarge ? 17 : 11));
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(contents, (x + (w - stringWidth(contents, font, g)) / 2) - 1, y + byte0);
    }

    public static int stringWidth(String s, Font font, Graphics g)
    {
        if(s.length() == 0)
        {
            return 0;
        } else
        {
            TextLayout textlayout = new TextLayout(s, font, ((Graphics2D)g).getFontRenderContext());
            return (int)textlayout.getBounds().getBounds().getWidth();
        }
    }

    public static Color darker(Color color1)
    {
        double d = 0.83330000000000004D;
        return new Color((int)(d * (double)color1.getRed()), (int)(d * (double)color1.getGreen()), (int)(d * (double)color1.getBlue()));
    }

    int x;
    int y;
    int w;
    int h;
    Color color;
    String contents;
    boolean isLarge;
    static final Font smallFont = new Font("Arial Unicode MS", 1, 10);
    static final Font bigFont = new Font("Arial Unicode MS", 1, 14);

}
