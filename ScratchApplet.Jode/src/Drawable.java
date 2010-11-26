/* Drawable - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Graphics;
import java.awt.Rectangle;

interface Drawable
{
    public boolean isShowing();
    
    public Rectangle rect();
    
    public Rectangle fullRect();
    
    public void paint(Graphics graphics);
    
    public void paintBubble(Graphics graphics);
    
    public void dragTo(int i, int i_0_);
    
    public void mouseDown(int i, int i_1_);
}
