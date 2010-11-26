import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

class ListWatcherCell
{
  Color CELL_COLOR = new Color(218, 77, 17);
  String contents;
  AttributedCharacterIterator ci;
  AttributedString as;
  LineBreakMeasurer lineMeasurer;
  FontRenderContext renderContext;
  BufferedImage contentsImage;
  int w;
  int h;
  int x;
  int y;

  ListWatcherCell(String paramString, int paramInt)
  {
    this.contents = paramString;
    this.w = paramInt;
    this.x = 0;
    this.y = 0;
    this.as = new AttributedString(paramString);
    this.as.addAttribute(TextAttribute.FONT, ListWatcher.LABEL_FONT);
    this.ci = this.as.getIterator();
    Graphics2D localGraphics2D1 = Skin.bubbleFrame.createGraphics();
    this.renderContext = ((Graphics2D)localGraphics2D1).getFontRenderContext();

    this.h = 4;
    this.lineMeasurer = new LineBreakMeasurer(this.ci, this.renderContext);
    this.lineMeasurer.setPosition(this.ci.getBeginIndex());
    while (this.lineMeasurer.getPosition() < this.ci.getEndIndex()) {
      localObject = this.lineMeasurer.nextLayout(this.w - 10);
      this.h = (int)(this.h + ((TextLayout)localObject).getAscent());
      this.h = (int)(this.h + (((TextLayout)localObject).getDescent() + ((TextLayout)localObject).getLeading()));
    }

    this.contentsImage = new BufferedImage(this.w, this.h, 6);
    Object localObject = this.contentsImage.getGraphics();
    ((Graphics)localObject).setColor(Color.WHITE);
    Graphics2D localGraphics2D2 = (Graphics2D)localObject;
    int i = this.y + 2;
    this.lineMeasurer.setPosition(this.ci.getBeginIndex());
    while (this.lineMeasurer.getPosition() < this.ci.getEndIndex()) {
      TextLayout localTextLayout = this.lineMeasurer.nextLayout(this.w - 10);
      i = (int)(i + localTextLayout.getAscent());
      localTextLayout.draw(localGraphics2D2, this.x + 6, i);
      int j = (int)(i + (localTextLayout.getDescent() + localTextLayout.getLeading()));
    }
  }

  public void paint(Graphics paramGraphics)
  {
    paramGraphics.setColor(Color.WHITE);
    paramGraphics.fillRect(this.x + 2, this.y, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + this.h - 1, this.w - 4, 1);
    paramGraphics.fillRect(this.x, this.y + 2, 1, this.h - 4);
    paramGraphics.fillRect(this.x + this.w - 1, this.y + 2, 1, this.h - 4);
    paramGraphics.fillRect(this.x + 1, this.y + 1, 1, 1);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + 1, 1, 1);
    paramGraphics.fillRect(this.x + 1, this.y + this.h - 2, 1, 1);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + this.h - 2, 1, 1);

    paramGraphics.setColor(WatcherReadout.darker(WatcherReadout.darker(this.CELL_COLOR)));
    paramGraphics.fillRect(this.x + 2, this.y + 1, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 1, this.y + 2, 1, this.h - 4);

    paramGraphics.setColor(WatcherReadout.darker(this.CELL_COLOR));
    paramGraphics.fillRect(this.x + 2, this.y + 2, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + this.h - 2, this.w - 4, 1);
    paramGraphics.fillRect(this.x + 2, this.y + 2, 1, this.h - 3);
    paramGraphics.fillRect(this.x + this.w - 2, this.y + 2, 1, this.h - 4);

    paramGraphics.setColor(this.CELL_COLOR);
    paramGraphics.fillRect(this.x + 3, this.y + 3, this.w - 5, this.h - 5);

    paramGraphics.drawImage(this.contentsImage, this.x, this.y, new Color(0, 0, 0, 1), null);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ListWatcherCell
 * JD-Core Version:    0.6.0
 */