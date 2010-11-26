// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.util.ArrayList;

class ListWatcher
    implements Drawable
{

    ListWatcher(LContext lcontext)
    {
        listTitle = "listTitle";
        list = null;
        highlightedIndices = new ArrayList();
        mouseOffset = 0;
        paneHeight = 0;
        scroll = 0;
        scrollForIndex = 0;
        scrollBarHeight = 0;
        scrollRatio = 0.0F;
        isShowing = true;
        useScrollForIndex = false;
        if(lcontext != null)
        {
            canvas = lcontext.canvas;
            lc = lcontext;
        }
        box = new StretchyBox();
        box.setFrameImage(Skin.listWatcherOuterFrame);
        pane = new ListWatcherPane(this);
    }

    public void setList(Object aobj[])
    {
        list = aobj;
        pane.setList(list);
        paneHeight = pane.totalHeight;
        initScrollBar();
    }

    public void initScrollBar()
    {
        scrollBarHeight = box.h - 23 - 20;
        scrollRatio = (float)paneHeight / (float)scrollBarHeight;
        if((double)scrollRatio < 1.0D)
        {
            scrollRatio = 0.0F;
            scrollBar = null;
            return;
        }
        if(scrollBar == null)
            scrollBar = new ListWatcherScrollBar();
        scrollBar.nubBox.h = (int)((float)scrollBarHeight / scrollRatio);
        if(scrollBar.nubBox.h < 23)
        {
            scrollBar.nubBox.h = 23;
            scrollRatio = (float)((double)(float)paneHeight / ((double)(float)scrollBarHeight - 23D));
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

    public Rectangle rect()
    {
        return new Rectangle(box.x, box.y, box.w, box.h);
    }

    public Rectangle fullRect()
    {
        return rect();
    }

    void inval()
    {
        canvas.inval(rect());
    }

    public void paintBubble(Graphics g)
    {
    }

    public void paint(Graphics g)
    {
        box.paint(g);
        g.setColor(Color.BLACK);
        g.setFont(LABEL_FONT);
        g.drawString(listTitle, box.x + (box.w - WatcherReadout.stringWidth(listTitle, LABEL_FONT, g)) / 2, (box.y + 23) - 8);
        if(scrollBar != null)
            scrollBar.paint(g, box.x + box.w, box.y + 23, scrollBarHeight, box.y + 23 + scroll);
        Graphics g1 = g.create();
        pane.paint(g1, box.x + 5, useScrollForIndex ? scrollForIndex : (int)((float)scroll * scrollRatio), box.y + 23, scrollBarHeight);
        g.setColor(Color.BLACK);
        g.setFont(LABEL_FONT_SMALL);
        String s = "length: " + list.length;
        g.drawString(s, box.x + (box.w - WatcherReadout.stringWidth(s, LABEL_FONT_SMALL, g)) / 2, (box.y + box.h) - 5);
    }

    boolean inScrollbar(int i, int j)
    {
        if(scrollBar != null)
        {
            if(i < (box.x + box.w) - 20 || i > box.x + box.w)
                return false;
            return j >= box.y + 23 + scroll && j <= box.y + 23 + scroll + scrollBar.nubBox.h;
        } else
        {
            return false;
        }
    }

    public void mouseDown(int i, int j)
    {
        if(scrollBar != null)
            mouseOffset = j - (box.y + 23) - scroll;
    }

    public void dragTo(int i, int j)
    {
        if(scrollBar != null)
            setScroll(j - mouseOffset - (box.y + 23));
    }

    void setScroll(int i)
    {
        if(scrollBar != null)
        {
            if(i < 0)
                scroll = 0;
            else
            if(i > scrollBarHeight - scrollBar.nubBox.h)
                scroll = scrollBarHeight - scrollBar.nubBox.h;
            else
                scroll = i;
            useScrollForIndex = false;
            inval();
        }
    }

    void setScrollForHighlightIndex(int i)
    {
        if(scrollBar != null)
        {
            scrollForIndex = pane.getYPositionAtIndex(i) - scrollBarHeight / 2;
            useScrollForIndex = true;
            int j = (int)((float)scrollForIndex / scrollRatio);
            if(j < 0)
                scroll = 0;
            else
            if(j > scrollBarHeight - scrollBar.nubBox.h)
                scroll = scrollBarHeight - scrollBar.nubBox.h;
            else
                scroll = j;
            inval();
        }
    }

    void highlightIndex(int i)
    {
        if(i < 1 || i > list.length)
        {
            box.setFrameImage(Skin.listWatcherOuterFrameError);
        } else
        {
            setScrollForHighlightIndex(i);
            highlightedIndices.add(new Integer(i));
            box.setFrameImage(Skin.listWatcherOuterFrame);
        }
    }

    void clearHighlights()
    {
        highlightedIndices = new ArrayList();
        box.setFrameImage(Skin.listWatcherOuterFrame);
    }

    public static final Font LABEL_FONT = new Font("Arial Unicode MS", 1, 10);
    public static final Font LABEL_FONT_SMALL = new Font("Arial Unicode MS", 0, 10);
    public static final Font CELL_NUM_FONT = new Font("Arial Unicode MS", 0, 9);
    public static final int TOP_MARGIN = 23;
    public static final int BOTTOM_MARGIN = 20;
    public static final int LEFT_MARGIN = 5;
    public static final int RIGHT_MARGIN = 5;
    LContext lc;
    PlayerCanvas canvas;
    StretchyBox box;
    ListWatcherPane pane;
    ListWatcherScrollBar scrollBar;
    String listTitle;
    Object list[];
    ArrayList highlightedIndices;
    int mouseOffset;
    int paneHeight;
    int scroll;
    int scrollForIndex;
    int scrollBarHeight;
    float scrollRatio;
    boolean isShowing;
    boolean useScrollForIndex;

}
