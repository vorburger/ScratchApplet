// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class ListWatcherPane
{

    ListWatcherPane(ListWatcher listwatcher)
    {
        w = 0;
        maxIndexWidth = 0;
        totalHeight = 0;
        cells = new ArrayList();
        ownerListWatcher = listwatcher;
    }

    public void setList(Object aobj[])
    {
        list = aobj;
        cells = new ArrayList();
        totalHeight = 0;
        maxIndexWidth = maxIndexWidth(Skin.bubbleFrame.createGraphics());
        for(int i = 0; i < aobj.length; i++)
        {
            ListWatcherCell listwatchercell = new ListWatcherCell(list[i].toString(), w - 20 - 5 - maxIndexWidth - 5);
            cells.add(listwatchercell);
            totalHeight += listwatchercell.h;
        }

    }

    public void paint(Graphics g, int i, int j, int k, int l)
    {
        int i1 = k - j;
        if(i1 > k || j == 0 || totalHeight < l)
            i1 = k;
        else
        if(i1 < k - (totalHeight - l))
            i1 = k - (totalHeight - l);
        g.setClip(i, k, w - 20 - 5, l);
        g.setFont(ListWatcher.CELL_NUM_FONT);
        if(list == null)
            return;
        for(int j1 = 0; j1 < cells.size(); j1++)
        {
            ListWatcherCell listwatchercell = (ListWatcherCell)cells.get(j1);
            listwatchercell.x = i + maxIndexWidth + 3;
            listwatchercell.y = i1 + 2;
            listwatchercell.paint(g);
            if(ownerListWatcher.highlightedIndices.contains(new Integer(j1 + 1)))
                g.setColor(Color.WHITE);
            else
                g.setColor(new Color(60, 60, 60));
            String s = Integer.toString(j1 + 1);
            g.drawString(s, i + (maxIndexWidth - WatcherReadout.stringWidth(s, ListWatcher.CELL_NUM_FONT, g)) / 2, i1 + (int)((float)listwatchercell.h / 2.0F) + 5);
            i1 += listwatchercell.h;
        }

    }

    public int getYPositionAtIndex(int i)
    {
        if(cells.size() > 0)
        {
            int j = 0;
            for(int k = 0; k < i; k++)
                j += ((ListWatcherCell)cells.get(k)).h;

            return j;
        } else
        {
            return 0;
        }
    }

    int maxIndexWidth(Graphics g)
    {
        double d = 0.0D;
        for(int i = 1; i < list.length + 1; i++)
            d = Math.max(d, WatcherReadout.stringWidth(Integer.toString(i), ListWatcher.LABEL_FONT, g));

        return (int)d;
    }

    static final int CELL_MARGIN = 20;
    static final int CELL_WIDTH = 42;
    static final int CELL_HEIGHT = 21;
    Object list[];
    ArrayList cells;
    ListWatcher ownerListWatcher;
    int w;
    int maxIndexWidth;
    int totalHeight;
}
