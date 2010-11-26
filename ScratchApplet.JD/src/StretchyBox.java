import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class StretchyBox
  implements Drawable
{
  int x;
  int y;
  int w = 100; int h = 75;
  BufferedImage cornerTL;
  BufferedImage cornerTR;
  BufferedImage cornerBL;
  BufferedImage cornerBR;
  BufferedImage edgeTop;
  BufferedImage edgeBottom;
  BufferedImage edgeLeft;
  BufferedImage edgeRight;
  Color fillColor;

  void setFrameImage(BufferedImage paramBufferedImage)
  {
    int i = paramBufferedImage.getWidth(null);
    int j = paramBufferedImage.getHeight(null);
    int k = i / 2;
    int m = j / 2;
    this.cornerTL = paramBufferedImage.getSubimage(0, 0, k, m);
    this.cornerTR = paramBufferedImage.getSubimage(i - k, 0, k, m);
    this.cornerBL = paramBufferedImage.getSubimage(0, j - m, k, m);
    this.cornerBR = paramBufferedImage.getSubimage(i - k, j - m, k, m);
    this.edgeTop = paramBufferedImage.getSubimage(k, 0, 1, m);
    this.edgeBottom = paramBufferedImage.getSubimage(k, j - m, 1, m);
    this.edgeLeft = paramBufferedImage.getSubimage(0, m, k, 1);
    this.edgeRight = paramBufferedImage.getSubimage(i - k, m, k, 1);
    this.fillColor = new Color(paramBufferedImage.getRGB(k, m));
  }
  public boolean isShowing() {
    return true; } 
  public Rectangle rect() { return new Rectangle(this.x, this.y, this.w, this.h); } 
  public Rectangle fullRect() { return rect(); }

  public void paint(Graphics paramGraphics) {
    if (this.cornerTL == null) {
      paramGraphics.setColor(new Color(100, 100, 250));
      paramGraphics.fillRect(this.x, this.y, this.w, this.h);
      return;
    }
    int i = this.cornerTL.getWidth(null);
    int j = this.cornerTL.getHeight(null);
    paramGraphics.setColor(this.fillColor);
    paramGraphics.fillRect(this.x + i, this.y + j, this.w - 2 * i, this.h - 2 * j);
    for (int k = this.x + i; k < this.x + this.w - i; k++) {
      paramGraphics.drawImage(this.edgeTop, k, this.y, null);
      paramGraphics.drawImage(this.edgeBottom, k, this.y + this.h - j, null);
    }
    for (k = this.y + j; k < this.y + this.h - j; k++) {
      paramGraphics.drawImage(this.edgeLeft, this.x, k, null);
      paramGraphics.drawImage(this.edgeRight, this.x + this.w - i, k, null);
    }
    paramGraphics.drawImage(this.cornerTL, this.x, this.y, null);
    paramGraphics.drawImage(this.cornerTR, this.x + this.w - i, this.y, null);
    paramGraphics.drawImage(this.cornerBL, this.x, this.y + this.h - j, null);
    paramGraphics.drawImage(this.cornerBR, this.x + this.w - i, this.y + this.h - j, null);
  }

  public void paintBubble(Graphics paramGraphics)
  {
  }

  public void dragTo(int paramInt1, int paramInt2)
  {
  }

  public void mouseDown(int paramInt1, int paramInt2)
  {
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     StretchyBox
 * JD-Core Version:    0.6.0
 */