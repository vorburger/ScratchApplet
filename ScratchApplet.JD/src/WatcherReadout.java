import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

class WatcherReadout
{
  int x;
  int y;
  int w = 40; int h = 14;
  Color color = new Color(100, 60, 20);
  String contents = "123";
  boolean isLarge = false;

  static final Font smallFont = new Font("Arial Unicode MS", 1, 10);
  static final Font bigFont = new Font("Arial Unicode MS", 1, 14);

  WatcherReadout(boolean paramBoolean) { beLarge(paramBoolean); }

  void beLarge(boolean paramBoolean) {
    if (this.isLarge == paramBoolean) return;
    this.isLarge = paramBoolean;
    this.w = (this.isLarge ? 50 : 40);
    this.h = (this.isLarge ? 23 : 14);
  }

  void adjustWidth(Graphics paramGraphics) {
    Font localFont = this.isLarge ? bigFont : smallFont;
    this.w = Math.max(this.w, stringWidth(this.contents, localFont, paramGraphics) + 12);
  }

  void paint(Graphics paramGraphics) {
    paramGraphics.setColor(Color.WHITE);
    paramGraphics.fillRect(this.x + 2, this.y, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + this.h - 1, this.w - 4, 1);
    paramGraphics.fillRect(this.x, this.y + 2, 1, this.h - 4);
    paramGraphics.fillRect(this.x + this.w - 1, this.y + 2, 1, this.h - 4);
    paramGraphics.fillRect(this.x + 1, this.y + 1, 1, 1);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + 1, 1, 1);
    paramGraphics.fillRect(this.x + 1, this.y + this.h - 2, 1, 1);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + this.h - 2, 1, 1);

    paramGraphics.setColor(darker(darker(this.color)));
    paramGraphics.fillRect(this.x + 2, this.y + 1, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 1, this.y + 2, 1, this.h - 4);

    paramGraphics.setColor(darker(this.color));
    paramGraphics.fillRect(this.x + 2, this.y + 2, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + this.h - 2, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + 2, 1, this.h - 3);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + 2, 1, this.h - 4);

    paramGraphics.setColor(this.color);
    paramGraphics.fillRect(this.x + 3, this.y + 3, this.w - 5, this.h - 5);

    Font localFont = this.isLarge ? bigFont : smallFont;
    int i = this.isLarge ? 17 : 11;
    paramGraphics.setColor(Color.WHITE);
    paramGraphics.setFont(localFont);
    paramGraphics.drawString(this.contents, this.x + (this.w - stringWidth(this.contents, localFont, paramGraphics)) / 2 - 1, this.y + i);
  }

  public static int stringWidth(String paramString, Font paramFont, Graphics paramGraphics) {
    if (paramString.length() == 0) return 0;
    TextLayout localTextLayout = new TextLayout(paramString, paramFont, ((Graphics2D)paramGraphics).getFontRenderContext());
    return (int)localTextLayout.getBounds().getBounds().getWidth();
  }

  public static Color darker(Color paramColor) {
    double d = 0.8333D;
    return new Color((int)(d * paramColor.getRed()), (int)(d * paramColor.getGreen()), (int)(d * paramColor.getBlue()));
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     WatcherReadout
 * JD-Core Version:    0.6.0
 */