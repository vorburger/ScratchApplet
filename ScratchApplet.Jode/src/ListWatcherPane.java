/* ListWatcherPane - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Graphics;
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
    
    ListWatcherPane(ListWatcher listwatcher) {
	((ListWatcherPane) this).cells = new ArrayList();
	((ListWatcherPane) this).ownerListWatcher = listwatcher;
    }
    
    public void setList(Object[] objects) {
	((ListWatcherPane) this).list = objects;
	((ListWatcherPane) this).cells = new ArrayList();
	((ListWatcherPane) this).totalHeight = 0;
	((ListWatcherPane) this).maxIndexWidth
	    = maxIndexWidth(Skin.bubbleFrame.createGraphics());
	for (int i = 0; i < objects.length; i++) {
	    ListWatcherCell listwatchercell
		= new ListWatcherCell(((ListWatcherPane) this).list[i]
					  .toString(),
				      (((ListWatcherPane) this).w - 20 - 5
				       - ((ListWatcherPane) this).maxIndexWidth
				       - 5));
	    ((ListWatcherPane) this).cells.add(listwatchercell);
	    ((ListWatcherPane) this).totalHeight
		+= ((ListWatcherCell) listwatchercell).h;
	}
    }
    
    public void paint(Graphics graphics, int i, int i_0_, int i_1_, int i_2_) {
	int i_3_ = i_1_ - i_0_;
	if (i_3_ > i_1_ || i_0_ == 0
	    || ((ListWatcherPane) this).totalHeight < i_2_)
	    i_3_ = i_1_;
	else if (i_3_ < i_1_ - (((ListWatcherPane) this).totalHeight - i_2_))
	    i_3_ = i_1_ - (((ListWatcherPane) this).totalHeight - i_2_);
	graphics.setClip(i, i_1_, ((ListWatcherPane) this).w - 20 - 5, i_2_);
	graphics.setFont(ListWatcher.CELL_NUM_FONT);
	if (((ListWatcherPane) this).list != null) {
	    for (int i_4_ = 0; i_4_ < ((ListWatcherPane) this).cells.size();
		 i_4_++) {
		ListWatcherCell listwatchercell
		    = ((ListWatcherCell)
		       ((ListWatcherPane) this).cells.get(i_4_));
		((ListWatcherCell) listwatchercell).x
		    = i + ((ListWatcherPane) this).maxIndexWidth + 3;
		((ListWatcherCell) listwatchercell).y = i_3_ + 2;
		listwatchercell.paint(graphics);
		if (((ListWatcher) ((ListWatcherPane) this).ownerListWatcher)
			.highlightedIndices.contains(new Integer(i_4_ + 1)))
		    graphics.setColor(Color.WHITE);
		else
		    graphics.setColor(new Color(60, 60, 60));
		String string = Integer.toString(i_4_ + 1);
		graphics.drawString(string,
				    i + (((ListWatcherPane) this).maxIndexWidth
					 - (WatcherReadout.stringWidth
					    (string, ListWatcher.CELL_NUM_FONT,
					     graphics))) / 2,
				    i_3_ + (int) ((float) ((ListWatcherCell)
							   listwatchercell).h
						  / 2.0F) + 5);
		i_3_ += ((ListWatcherCell) listwatchercell).h;
	    }
	}
    }
    
    public int getYPositionAtIndex(int i) {
	if (((ListWatcherPane) this).cells.size() > 0) {
	    int i_5_ = 0;
	    for (int i_6_ = 0; i_6_ < i; i_6_++)
		i_5_ += ((ListWatcherCell) (ListWatcherCell)
			 ((ListWatcherPane) this).cells.get(i_6_)).h;
	    return i_5_;
	}
	return 0;
    }
    
    int maxIndexWidth(Graphics graphics) {
	double d = 0.0;
	for (int i = 1; i < ((ListWatcherPane) this).list.length + 1; i++)
	    d = Math.max(d,
			 (double) WatcherReadout.stringWidth(Integer
								 .toString(i),
							     (ListWatcher
							      .LABEL_FONT),
							     graphics));
	return (int) d;
    }
}
