import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class ListWatcherPane
{
  static final int CELL_MARGIN = 20;
  static final int CELL_WIDTH = 42;
  static final int CELL_HEIGHT = 21;
  Object[] list;
  ArrayList cells;
  ListWatcher ownerListWatcher;
  int w = 0;
  int maxIndexWidth = 0;
  int totalHeight = 0;

  ListWatcherPane(ListWatcher paramListWatcher) {
    this.cells = new ArrayList();
    this.ownerListWatcher = paramListWatcher;
  }

  public void setList(Object[] paramArrayOfObject) {
    this.list = paramArrayOfObject;
    this.cells = new ArrayList();
    this.totalHeight = 0;
    this.maxIndexWidth = maxIndexWidth(Skin.bubbleFrame.createGraphics());
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      ListWatcherCell localListWatcherCell = new ListWatcherCell(this.list[i].toString(), this.w - 20 - 5 - this.maxIndexWidth - 5);

      this.cells.add(localListWatcherCell);
      this.totalHeight += localListWatcherCell.h;
    }
  }

  public void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt2;
    if ((i > paramInt3) || (paramInt2 == 0) || (this.totalHeight < paramInt4))
      i = paramInt3;
    else if (i < paramInt3 - (this.totalHeight - paramInt4)) {
      i = paramInt3 - (this.totalHeight - paramInt4);
    }

    paramGraphics.setClip(paramInt1, paramInt3, this.w - 20 - 5, paramInt4);

    paramGraphics.setFont(ListWatcher.CELL_NUM_FONT);
    if (this.list == null) return;
    for (int j = 0; j < this.cells.size(); j++)
    {
      ListWatcherCell localListWatcherCell = (ListWatcherCell)this.cells.get(j);
      localListWatcherCell.x = (paramInt1 + this.maxIndexWidth + 3);
      localListWatcherCell.y = (i + 2);
      localListWatcherCell.paint(paramGraphics);

      if (this.ownerListWatcher.highlightedIndices.contains(new Integer(j + 1)))
        paramGraphics.setColor(Color.WHITE);
      else {
        paramGraphics.setColor(new Color(60, 60, 60));
      }
      String str = Integer.toString(j + 1);
      paramGraphics.drawString(str, paramInt1 + (this.maxIndexWidth - WatcherReadout.stringWidth(str, ListWatcher.CELL_NUM_FONT, paramGraphics)) / 2, i + (int)(localListWatcherCell.h / 2.0F) + 5);

      i += localListWatcherCell.h;
    }
  }

  public int getYPositionAtIndex(int paramInt) {
    if (this.cells.size() > 0) {
      int i = 0;
      for (int j = 0; j < paramInt; j++) {
        i += ((ListWatcherCell)this.cells.get(j)).h;
      }
      return i;
    }
    return 0;
  }

  int maxIndexWidth(Graphics paramGraphics)
  {
    double d = 0.0D;
    for (int i = 1; i < this.list.length + 1; i++) {
      d = Math.max(d, WatcherReadout.stringWidth(Integer.toString(i), ListWatcher.LABEL_FONT, paramGraphics));
    }
    return (int)d;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ListWatcherPane
 * JD-Core Version:    0.6.0
 */