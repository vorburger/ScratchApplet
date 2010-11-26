// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.font.*;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

class ListWatcherCell
{

    ListWatcherCell(String s, int i)
    {
        CELL_COLOR = new Color(218, 77, 17);
        contents = s;
        w = i;
        x = 0;
        y = 0;
        as = new AttributedString(s);
        as.addAttribute(TextAttribute.FONT, ListWatcher.LABEL_FONT);
        ci = as.getIterator();
        Graphics2D graphics2d = Skin.bubbleFrame.createGraphics();
        renderContext = ((Graphics2D)graphics2d).getFontRenderContext();
        h = 4;
        lineMeasurer = new LineBreakMeasurer(ci, renderContext);
        lineMeasurer.setPosition(ci.getBeginIndex());
        while(lineMeasurer.getPosition() < ci.getEndIndex()) 
        {
            TextLayout textlayout = lineMeasurer.nextLayout(w - 10);
            h += textlayout.getAscent();
            h += textlayout.getDescent() + textlayout.getLeading();
        }
        contentsImage = new BufferedImage(w, h, 6);
        Graphics g = contentsImage.getGraphics();
        g.setColor(Color.WHITE);
        Graphics2D graphics2d1 = (Graphics2D)g;
        int j = y + 2;
        lineMeasurer.setPosition(ci.getBeginIndex());
        while(lineMeasurer.getPosition() < ci.getEndIndex()) 
        {
            TextLayout textlayout1 = lineMeasurer.nextLayout(w - 10);
            j = (int)((float)j + textlayout1.getAscent());
            textlayout1.draw(graphics2d1, x + 6, j);
            j = (int)((float)j + (textlayout1.getDescent() + textlayout1.getLeading()));
        }
    }

    public void paint(Graphics g)
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
        g.setColor(WatcherReadout.darker(WatcherReadout.darker(CELL_COLOR)));
        g.fillRect(x + 2, y + 1, w - 4, 1);
        g.fillRect(x + 1, y + 2, 1, h - 4);
        g.setColor(WatcherReadout.darker(CELL_COLOR));
        g.fillRect(x + 2, y + 2, w - 4, 1);
        g.fillRect(x + 2, (y + h) - 2, w - 4, 1);
        g.fillRect(x + 2, y + 2, 1, h - 3);
        g.fillRect((x + w) - 2, y + 2, 1, h - 4);
        g.setColor(CELL_COLOR);
        g.fillRect(x + 3, y + 3, w - 5, h - 5);
        g.drawImage(contentsImage, x, y, new Color(0, 0, 0, 1), null);
    }

    Color CELL_COLOR;
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
}
