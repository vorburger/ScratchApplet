import java.awt.Graphics;

class ListWatcherScrollBar
{
  public static final int SCROLLBAR_WIDTH = 20;
  StretchyBox frameBox;
  StretchyBox nubBox;

  ListWatcherScrollBar()
  {
    this.frameBox = new StretchyBox();
    this.frameBox.setFrameImage(Skin.vScrollFrame);
    this.nubBox = new StretchyBox();
    this.nubBox.setFrameImage(Skin.vScrollSlider);
  }

  public void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.frameBox.x = (paramInt1 - 20);
    this.frameBox.y = paramInt2;
    this.frameBox.w = 16;
    this.frameBox.h = (paramInt3 + 5);
    this.frameBox.paint(paramGraphics);

    this.nubBox.x = (paramInt1 - 20 + 2);
    this.nubBox.y = (paramInt4 + 2);
    this.nubBox.w = 12;
    if (this.nubBox.h < 23) this.nubBox.h = 23;
    this.nubBox.paint(paramGraphics);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ListWatcherScrollBar
 * JD-Core Version:    0.6.0
 */