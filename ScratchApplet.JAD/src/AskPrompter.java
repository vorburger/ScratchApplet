// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

class AskPrompter extends StretchyBox
{

    AskPrompter(String s)
    {
        fontSize = 11;
        font = new Font("Arial Unicode MS", 1, fontSize);
        answerFont = new Font("Arial Unicode MS", 0, 13);
        promptString = "";
        answerString = "";
        renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
        setFrameImage(Skin.askBubbleFrame);
        setPrompt(s);
    }

    void setPrompt(String s)
    {
        byte byte0 = ((byte)(s.length() != 0 ? 2 : 1));
        promptString = s;
        x = 15;
        w = 452;
        h = byte0 * (fontSize + 4) + 22;
        y = 378 - h;
    }

    public Rectangle rect()
    {
        return new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D graphics2d = (Graphics2D)g;
        lineX = x + 12;
        lineY = y + fontSize + 14;
        graphics2d.setColor(new Color(0, 0, 0));
        graphics2d.setFont(font);
        if(promptString.length() > 0)
        {
            graphics2d.drawString(promptString, lineX - 2, lineY - 8);
            lineY += fontSize + 4;
        }
        graphics2d.setStroke(new BasicStroke(2.0F));
        graphics2d.setColor(new Color(213, 213, 213));
        graphics2d.drawLine(lineX - 4, lineY - fontSize - 5, ((lineX - 4) + w) - 38, lineY - fontSize - 5);
        graphics2d.drawLine(lineX - 4, lineY - fontSize - 5, lineX - 4, (lineY - fontSize - 5) + fontSize + 9);
        graphics2d.setColor(new Color(242, 242, 242));
        graphics2d.fillRect(lineX - 3, lineY - fontSize - 4, w - 39, fontSize + 8);
        java.awt.Shape shape = g.getClip();
        g.setClip(lineX - 3, lineY - fontSize - 4, w - 39, fontSize + 8);
        graphics2d.setColor(new Color(0, 0, 0));
        graphics2d.setFont(answerFont);
        graphics2d.drawString(answerString, lineX, lineY - 1);
        g.setClip(shape);
        graphics2d.drawImage(Skin.promptCheckButton, null, (lineX + w) - 38, lineY - fontSize - 6);
    }

    public boolean mouseDown(int i, int j, PlayerCanvas playercanvas)
    {
        if(i > (lineX + w) - 38 && i < ((lineX + w) - 38) + 20 && j > lineY - fontSize - 6 && j < (lineY - fontSize - 6) + 20)
        {
            playercanvas.hideAskPrompt();
            return true;
        } else
        {
            return false;
        }
    }

    public void handleKeystroke(int i, PlayerCanvas playercanvas)
    {
        playercanvas.inval(rect());
        if(i == 3 || i == 10)
        {
            playercanvas.hideAskPrompt();
            return;
        }
        if(i == 8 || i == 127)
        {
            if(answerString.length() > 0)
                answerString = answerString.substring(0, answerString.length() - 1);
            return;
        }
        char ac[] = new char[1];
        ac[0] = (char)i;
        if(i >= 32)
            answerString += new String(ac);
    }

    int fontSize;
    Font font;
    Font answerFont;
    FontRenderContext renderContext;
    String promptString;
    String answerString;
    int lineX;
    int lineY;
}
