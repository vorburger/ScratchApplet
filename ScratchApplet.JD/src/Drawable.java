import java.awt.Graphics;
import java.awt.Rectangle;

abstract interface Drawable
{
  public abstract boolean isShowing();

  public abstract Rectangle rect();

  public abstract Rectangle fullRect();

  public abstract void paint(Graphics paramGraphics);

  public abstract void paintBubble(Graphics paramGraphics);

  public abstract void dragTo(int paramInt1, int paramInt2);

  public abstract void mouseDown(int paramInt1, int paramInt2);
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Drawable
 * JD-Core Version:    0.6.0
 */