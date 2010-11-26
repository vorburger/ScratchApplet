// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;

class Watcher
    implements Drawable
{

    Watcher(LContext lcontext)
    {
        label = "x";
        mode = 1;
        sliderMin = 0.0D;
        sliderMax = 100D;
        isDiscrete = false;
        isShowing = true;
        if(lcontext != null)
            canvas = lcontext.canvas;
        box = new StretchyBox();
        box.setFrameImage(Skin.watcherOuterFrame);
        box.x = 50;
        box.y = 50;
        readout = new WatcherReadout(false);
        readout.color = new Color(74, 107, 214);
        slider = new StretchyBox();
        slider.setFrameImage(Skin.sliderSlot);
        slider.h = 5;
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

    public void paint(Graphics g)
    {
        int i = labelWidth(g);
        readout.beLarge(mode == 3);
        readout.adjustWidth(g);
        box.w = i + readout.w + 17;
        box.h = mode != 1 ? 31 : 21;
        if(mode == 3)
        {
            readout.x = box.x;
            readout.y = box.y;
            readout.paint(g);
            return;
        }
        box.paint(g);
        drawLabel(g);
        readout.x = box.x + i + 12;
        readout.y = box.y + 3;
        readout.paint(g);
        if(mode == 2)
            drawSlider(g);
    }

    public void paintBubble(Graphics g)
    {
    }

    public void mouseDown(int i, int j)
    {
    }

    void drawLabel(Graphics g)
    {
        g.setColor(black);
        g.setFont(labelFont);
        g.drawString(label, box.x + 6, box.y + 14);
    }

    int labelWidth(Graphics g)
    {
        if(label.length() == 0)
        {
            return 0;
        } else
        {
            TextLayout textlayout = new TextLayout(label, labelFont, ((Graphics2D)g).getFontRenderContext());
            return (int)textlayout.getBounds().getBounds().getWidth();
        }
    }

    void drawSlider(Graphics g)
    {
        slider.x = box.x + 6;
        slider.y = box.y + 21;
        slider.w = box.w - 12;
        slider.paint(g);
        int i = (int)Math.round((double)(slider.w - 8) * ((getSliderValue() - sliderMin) / (sliderMax - sliderMin)));
        i = Math.max(0, Math.min(i, slider.w - 8));
        g.drawImage(Skin.sliderKnob, (slider.x + i) - 1, slider.y - 3, null);
    }

    boolean inSlider(int i, int j)
    {
        if(mode != 2)
            return false;
        if(j < slider.y - 1 || j > slider.y + slider.h + 4)
            return false;
        return i >= slider.x - 4 && i <= slider.x + slider.w + 5;
    }

    public void dragTo(int i, int j)
    {
        double d = i - box.x - 10;
        setSliderValue((d * (sliderMax - sliderMin)) / (double)(slider.w - 8) + sliderMin);
    }

    void click(int i, int j)
    {
        double d = getSliderValue();
        int k = slider.x + (int)Math.round((double)(slider.w - 8) * ((d - sliderMin) / (sliderMax - sliderMin))) + 5;
        int l = i >= k ? 1 : -1;
        if(isDiscrete)
            setSliderValue(d + (double)l);
        else
            setSliderValue(d + (double)l * ((sliderMax - sliderMin) / 100D));
    }

    double getSliderValue()
    {
        String s = Logo.prs(getObjProp(this, "param"));
        Hashtable hashtable = getVarsTable();
        if(s == null || hashtable == null)
        {
            return (sliderMin + sliderMax) / 2D;
        } else
        {
            Object obj = hashtable.get(Logo.aSymbol(s, canvas.lc));
            return (obj instanceof Number) ? ((Number)obj).doubleValue() : (sliderMin + sliderMax) / 2D;
        }
    }

    void setSliderValue(double d)
    {
        double d1 = isDiscrete ? Math.round(d) : d;
        d1 = Math.min(sliderMax, Math.max(sliderMin, d1));
        String s = Logo.prs(getObjProp(this, "param"));
        Hashtable hashtable = getVarsTable();
        if(s == null || hashtable == null)
        {
            return;
        } else
        {
            hashtable.put(Logo.aSymbol(s, canvas.lc), new Double(d1));
            inval();
            return;
        }
    }

    Hashtable getVarsTable()
    {
        String s = Logo.prs(getObjProp(this, "op"));
        String s1 = Logo.prs(getObjProp(this, "param"));
        Object obj = getObjProp(this, "target");
        if(obj == null || !s.equals("getVar:"))
            return null;
        Object obj1 = getObjProp(obj, "vars");
        if(obj1 == null)
            return null;
        else
            return (Hashtable)canvas.lc.props.get(obj1);
    }

    Object getObjProp(Object obj, String s)
    {
        Hashtable hashtable = (Hashtable)canvas.lc.props.get(obj);
        if(hashtable == null)
            return null;
        else
            return hashtable.get(Logo.aSymbol(s, canvas.lc));
    }

    static final Color black = new Color(0, 0, 0);
    static final Font labelFont = new Font("Arial Unicode MS", 1, 10);
    static final int NORMAL_MODE = 1;
    static final int SLIDER_MODE = 2;
    static final int LARGE_MODE = 3;
    PlayerCanvas canvas;
    StretchyBox box;
    WatcherReadout readout;
    StretchyBox slider;
    String label;
    int mode;
    double sliderMin;
    double sliderMax;
    boolean isDiscrete;
    boolean isShowing;

}
