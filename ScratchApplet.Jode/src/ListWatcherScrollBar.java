/* ListWatcherScrollBar - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Graphics;

class ListWatcherScrollBar
{
    public static final int SCROLLBAR_WIDTH = 20;
    StretchyBox frameBox = new StretchyBox();
    StretchyBox nubBox;
    
    ListWatcherScrollBar() {
	((ListWatcherScrollBar) this).frameBox
	    .setFrameImage(Skin.vScrollFrame);
	((ListWatcherScrollBar) this).nubBox = new StretchyBox();
	((ListWatcherScrollBar) this).nubBox.setFrameImage(Skin.vScrollSlider);
    }
    
    public void paint(Graphics graphics, int i, int i_0_, int i_1_, int i_2_) {
	((StretchyBox) ((ListWatcherScrollBar) this).frameBox).x = i - 20;
	((StretchyBox) ((ListWatcherScrollBar) this).frameBox).y = i_0_;
	((StretchyBox) ((ListWatcherScrollBar) this).frameBox).w = 16;
	((StretchyBox) ((ListWatcherScrollBar) this).frameBox).h = i_1_ + 5;
	((ListWatcherScrollBar) this).frameBox.paint(graphics);
	((StretchyBox) ((ListWatcherScrollBar) this).nubBox).x = i - 20 + 2;
	((StretchyBox) ((ListWatcherScrollBar) this).nubBox).y = i_2_ + 2;
	((StretchyBox) ((ListWatcherScrollBar) this).nubBox).w = 12;
	if (((StretchyBox) ((ListWatcherScrollBar) this).nubBox).h < 23)
	    ((StretchyBox) ((ListWatcherScrollBar) this).nubBox).h = 23;
	((ListWatcherScrollBar) this).nubBox.paint(graphics);
    }
}
