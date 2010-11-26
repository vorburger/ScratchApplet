// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Vector;

class Bubble extends StretchyBox
{

    Bubble()
    {
        pointLeft = false;
        fontSize = 13;
        font = new Font("Arial Unicode MS", 1, fontSize);
        wrapWidth = 135;
        renderContext = Skin.bubbleFrame.createGraphics().getFontRenderContext();
        setFrameImage(Skin.bubbleFrame);
        beThinkBubble(false);
    }

    void beThinkBubble(boolean flag)
    {
        if(flag)
        {
            leftPointer = Skin.thinkPointerL;
            rightPointer = Skin.thinkPointerR;
        } else
        {
            leftPointer = Skin.talkPointerL;
            rightPointer = Skin.talkPointerR;
        }
    }

    void beAskBubble()
    {
        renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
        setFrameImage(Skin.askBubbleFrame);
        leftPointer = Skin.askPointerL;
        rightPointer = Skin.askPointerR;
    }

    void setContents(String s)
    {
        contents = s;
        Vector vector = new Vector();
        int j;
        for(int i = 0; i < s.length(); i = j)
        {
            j = findLineEnd(s, i);
            vector.addElement(s.substring(i, j));
        }

        lines = new String[vector.size()];
        w = 65;
        for(int k = 0; k < lines.length; k++)
        {
            lines[k] = (String)vector.get(k);
            w = Math.max(w, widthOf(lines[k]) + 15);
        }

        xOffsets = new int[lines.length];
        for(int l = 0; l < lines.length; l++)
            xOffsets[l] = (w - widthOf(lines[l])) / 2;

        h = lines.length * (fontSize + 2) + 19;
    }

    int findLineEnd(String s, int i)
    {
        int j;
        for(j = i + 1; j < s.length() && widthOf(s.substring(i, j + 1)) < wrapWidth; j++);
        if(j == s.length())
            return j;
        if(widthOf(s.substring(i, j + 1)) < wrapWidth)
            return j + 1;
        int k = j + 1;
        for(; j > i + 1; j--)
            if(j < s.length() && s.charAt(j) == ' ')
                return j + 1;

        return k;
    }

    int widthOf(String s)
    {
        if(s.length() == 0)
            return 0;
        else
            return (int)(new TextLayout(s, font, renderContext)).getAdvance();
    }

    public Rectangle rect()
    {
        return new Rectangle(x, y, w, h + leftPointer.getHeight(null));
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(pointLeft)
            g.drawImage(leftPointer, x + 7, (y + h) - 3, null);
        else
            g.drawImage(rightPointer, ((x - 9) + w) - rightPointer.getWidth(null), (y + h) - 3, null);
        g.setColor(new Color(0, 0, 0));
        g.setFont(font);
        int i = y + fontSize + 8;
        for(int j = 0; j < lines.length; j++)
        {
            g.drawString(lines[j], x + xOffsets[j], i);
            i += fontSize + 2;
        }

    }

    boolean pointLeft;
    BufferedImage leftPointer;
    BufferedImage rightPointer;
    int fontSize;
    Font font;
    FontRenderContext renderContext;
    int wrapWidth;
    String contents;
    String lines[];
    int xOffsets[];
}
