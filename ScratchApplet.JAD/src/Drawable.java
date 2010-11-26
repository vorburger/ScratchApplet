// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.Graphics;
import java.awt.Rectangle;

interface Drawable
{

    public abstract boolean isShowing();

    public abstract Rectangle rect();

    public abstract Rectangle fullRect();

    public abstract void paint(Graphics g);

    public abstract void paintBubble(Graphics g);

    public abstract void dragTo(int i, int j);

    public abstract void mouseDown(int i, int j);
}
