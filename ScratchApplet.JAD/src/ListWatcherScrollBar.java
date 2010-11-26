// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.Graphics;

class ListWatcherScrollBar
{

    ListWatcherScrollBar()
    {
        frameBox = new StretchyBox();
        frameBox.setFrameImage(Skin.vScrollFrame);
        nubBox = new StretchyBox();
        nubBox.setFrameImage(Skin.vScrollSlider);
    }

    public void paint(Graphics g, int i, int j, int k, int l)
    {
        frameBox.x = i - 20;
        frameBox.y = j;
        frameBox.w = 16;
        frameBox.h = k + 5;
        frameBox.paint(g);
        nubBox.x = (i - 20) + 2;
        nubBox.y = l + 2;
        nubBox.w = 12;
        if(nubBox.h < 23)
            nubBox.h = 23;
        nubBox.paint(g);
    }

    public static final int SCROLLBAR_WIDTH = 20;
    StretchyBox frameBox;
    StretchyBox nubBox;
}
