import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

class ListWatcher
  implements Drawable
{
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
  String listTitle = "listTitle";
  Object[] list = null;
  ArrayList highlightedIndices = new ArrayList();
  int mouseOffset = 0;
  int paneHeight = 0;
  int scroll = 0;
  int scrollForIndex = 0;
  int scrollBarHeight = 0;
  float scrollRatio = 0.0F;
  boolean isShowing = true;
  boolean useScrollForIndex = false;

  ListWatcher(LContext paramLContext) {
    if (paramLContext != null) {
      this.canvas = paramLContext.canvas;
      this.lc = paramLContext;
    }
    this.box = new StretchyBox();
    this.box.setFrameImage(Skin.listWatcherOuterFrame);
    this.pane = new ListWatcherPane(this);
  }

  public void setList(Object[] paramArrayOfObject) {
    this.list = paramArrayOfObject;
    this.pane.setList(this.list);
    this.paneHeight = this.pane.totalHeight;
    initScrollBar();
  }

  public void initScrollBar() {
    this.scrollBarHeight = (this.box.h - 23 - 20);
    this.scrollRatio = (this.paneHeight / this.scrollBarHeight);
    if (this.scrollRatio < 1.0D)
    {
      this.scrollRatio = 0.0F;
      this.scrollBar = null;
      return;
    }if (this.scrollBar == null)
    {
      this.scrollBar = new ListWatcherScrollBar();
    }
    this.scrollBar.nubBox.h = (int)(this.scrollBarHeight / this.scrollRatio);

    if (this.scrollBar.nubBox.h < 23) {
      this.scrollBar.nubBox.h = 23;
      this.scrollRatio = (float)(this.paneHeight / (this.scrollBarHeight - 23.0D));
    }
  }

  void show() {
    this.isShowing = true; inval(); } 
  void hide() { this.isShowing = false; inval(); } 
  public boolean isShowing() { return this.isShowing; } 
  public Rectangle rect() {
    return new Rectangle(this.box.x, this.box.y, this.box.w, this.box.h); } 
  public Rectangle fullRect() { return rect(); } 
  void inval() { this.canvas.inval(rect()); } 
  public void paintBubble(Graphics paramGraphics) {
  }

  public void paint(Graphics paramGraphics) {
    this.box.paint(paramGraphics);

    paramGraphics.setColor(Color.BLACK);
    paramGraphics.setFont(LABEL_FONT);
    paramGraphics.drawString(this.listTitle, this.box.x + (this.box.w - WatcherReadout.stringWidth(this.listTitle, LABEL_FONT, paramGraphics)) / 2, this.box.y + 23 - 8);

    if (this.scrollBar != null) {
      this.scrollBar.paint(paramGraphics, this.box.x + this.box.w, this.box.y + 23, this.scrollBarHeight, this.box.y + 23 + this.scroll);
    }

    Graphics localGraphics = paramGraphics.create();
    this.pane.paint(localGraphics, this.box.x + 5, this.useScrollForIndex ? this.scrollForIndex : (int)(this.scroll * this.scrollRatio), this.box.y + 23, this.scrollBarHeight);

    paramGraphics.setColor(Color.BLACK);
    paramGraphics.setFont(LABEL_FONT_SMALL);
    String str = "length: " + this.list.length;
    paramGraphics.drawString(str, this.box.x + (this.box.w - WatcherReadout.stringWidth(str, LABEL_FONT_SMALL, paramGraphics)) / 2, this.box.y + this.box.h - 5);
  }

  boolean inScrollbar(int paramInt1, int paramInt2) {
    if (this.scrollBar != null) {
      if ((paramInt1 < this.box.x + this.box.w - 20) || (paramInt1 > this.box.x + this.box.w)) return false;
      return (paramInt2 >= this.box.y + 23 + this.scroll) && (paramInt2 <= this.box.y + 23 + this.scroll + this.scrollBar.nubBox.h);
    }

    return false;
  }

  public void mouseDown(int paramInt1, int paramInt2)
  {
    if (this.scrollBar != null)
      this.mouseOffset = (paramInt2 - (this.box.y + 23) - this.scroll);
  }

  public void dragTo(int paramInt1, int paramInt2)
  {
    if (this.scrollBar != null)
      setScroll(paramInt2 - this.mouseOffset - (this.box.y + 23));
  }

  void setScroll(int paramInt)
  {
    if (this.scrollBar != null) {
      if (paramInt < 0)
        this.scroll = 0;
      else if (paramInt > this.scrollBarHeight - this.scrollBar.nubBox.h)
        this.scroll = (this.scrollBarHeight - this.scrollBar.nubBox.h);
      else {
        this.scroll = paramInt;
      }
      this.useScrollForIndex = false;
      inval();
    }
  }

  void setScrollForHighlightIndex(int paramInt) {
    if (this.scrollBar != null) {
      this.scrollForIndex = (this.pane.getYPositionAtIndex(paramInt) - this.scrollBarHeight / 2);
      this.useScrollForIndex = true;

      int i = (int)(this.scrollForIndex / this.scrollRatio);
      if (i < 0)
        this.scroll = 0;
      else if (i > this.scrollBarHeight - this.scrollBar.nubBox.h)
        this.scroll = (this.scrollBarHeight - this.scrollBar.nubBox.h);
      else {
        this.scroll = i;
      }
      inval();
    }
  }

  void highlightIndex(int paramInt) {
    if ((paramInt < 1) || (paramInt > this.list.length))
    {
      this.box.setFrameImage(Skin.listWatcherOuterFrameError);
    }
    else {
      setScrollForHighlightIndex(paramInt);
      this.highlightedIndices.add(new Integer(paramInt));
      this.box.setFrameImage(Skin.listWatcherOuterFrame);
    }
  }

  void clearHighlights() {
    this.highlightedIndices = new ArrayList();
    this.box.setFrameImage(Skin.listWatcherOuterFrame);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ListWatcher
 * JD-Core Version:    0.6.0
 */