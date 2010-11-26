import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Vector;

class Bubble extends StretchyBox
{
  boolean pointLeft = false;
  BufferedImage leftPointer;
  BufferedImage rightPointer;
  int fontSize = 13;
  Font font = new Font("Arial Unicode MS", 1, this.fontSize);
  FontRenderContext renderContext;
  int wrapWidth = 135;
  String contents;
  String[] lines;
  int[] xOffsets;

  Bubble()
  {
    this.renderContext = Skin.bubbleFrame.createGraphics().getFontRenderContext();
    setFrameImage(Skin.bubbleFrame);
    beThinkBubble(false);
  }

  void beThinkBubble(boolean paramBoolean) {
    if (paramBoolean) {
      this.leftPointer = Skin.thinkPointerL;
      this.rightPointer = Skin.thinkPointerR;
    } else {
      this.leftPointer = Skin.talkPointerL;
      this.rightPointer = Skin.talkPointerR;
    }
  }

  void beAskBubble() {
    this.renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
    setFrameImage(Skin.askBubbleFrame);
    this.leftPointer = Skin.askPointerL;
    this.rightPointer = Skin.askPointerR;
  }

  void setContents(String paramString) {
    this.contents = paramString;
    Vector localVector = new Vector();
    int i = 0;
    while (i < paramString.length()) {
      j = findLineEnd(paramString, i);
      localVector.addElement(paramString.substring(i, j));
      i = j;
    }

    this.lines = new String[localVector.size()];
    this.w = 65;
    for (int j = 0; j < this.lines.length; j++) {
      this.lines[j] = ((String)localVector.get(j));
      this.w = Math.max(this.w, widthOf(this.lines[j]) + 15);
    }

    this.xOffsets = new int[this.lines.length];
    for (j = 0; j < this.lines.length; j++) {
      this.xOffsets[j] = ((this.w - widthOf(this.lines[j])) / 2);
    }

    this.h = (this.lines.length * (this.fontSize + 2) + 19);
  }

  int findLineEnd(String paramString, int paramInt) {
    int i = paramInt + 1;

    while ((i < paramString.length()) && (widthOf(paramString.substring(paramInt, i + 1)) < this.wrapWidth)) i++;

    if (i == paramString.length()) return i;
    if (widthOf(paramString.substring(paramInt, i + 1)) < this.wrapWidth) return i + 1;

    int j = i + 1;
    while (i > paramInt + 1) {
      if ((i < paramString.length()) && (paramString.charAt(i) == ' ')) return i + 1;
      i--;
    }
    return j;
  }

  int widthOf(String paramString) {
    if (paramString.length() == 0) return 0;
    return (int)new TextLayout(paramString, this.font, this.renderContext).getAdvance();
  }
  public Rectangle rect() {
    return new Rectangle(this.x, this.y, this.w, this.h + this.leftPointer.getHeight(null));
  }
  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    if (this.pointLeft)
      paramGraphics.drawImage(this.leftPointer, this.x + 7, this.y + this.h - 3, null);
    else {
      paramGraphics.drawImage(this.rightPointer, this.x - 9 + this.w - this.rightPointer.getWidth(null), this.y + this.h - 3, null);
    }

    paramGraphics.setColor(new Color(0, 0, 0));
    paramGraphics.setFont(this.font);
    int i = this.y + this.fontSize + 8;
    for (int j = 0; j < this.lines.length; j++) {
      paramGraphics.drawString(this.lines[j], this.x + this.xOffsets[j], i);
      i += this.fontSize + 2;
    }
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     Bubble
 * JD-Core Version:    0.6.0
 */