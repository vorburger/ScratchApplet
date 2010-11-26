import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

class AskPrompter extends StretchyBox
{
  int fontSize = 11;
  Font font = new Font("Arial Unicode MS", 1, this.fontSize);
  Font answerFont = new Font("Arial Unicode MS", 0, 13);
  FontRenderContext renderContext;
  String promptString = "";
  String answerString = "";
  int lineX;
  int lineY;

  AskPrompter(String paramString)
  {
    this.renderContext = Skin.askBubbleFrame.createGraphics().getFontRenderContext();
    setFrameImage(Skin.askBubbleFrame);
    setPrompt(paramString);
  }

  void setPrompt(String paramString) {
    int i = paramString.length() == 0 ? 1 : 2;
    this.promptString = paramString;
    this.x = 15;
    this.w = 452;
    this.h = (i * (this.fontSize + 4) + 22);
    this.y = (378 - this.h);
  }
  public Rectangle rect() {
    return new Rectangle(this.x, this.y, this.w, this.h);
  }
  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;

    this.lineX = (this.x + 12);
    this.lineY = (this.y + this.fontSize + 14);

    localGraphics2D.setColor(new Color(0, 0, 0));
    localGraphics2D.setFont(this.font);
    if (this.promptString.length() > 0) {
      localGraphics2D.drawString(this.promptString, this.lineX - 2, this.lineY - 8);
      this.lineY += this.fontSize + 4;
    }

    localGraphics2D.setStroke(new BasicStroke(2.0F));
    localGraphics2D.setColor(new Color(213, 213, 213));
    localGraphics2D.drawLine(this.lineX - 4, this.lineY - this.fontSize - 5, this.lineX - 4 + this.w - 38, this.lineY - this.fontSize - 5);
    localGraphics2D.drawLine(this.lineX - 4, this.lineY - this.fontSize - 5, this.lineX - 4, this.lineY - this.fontSize - 5 + this.fontSize + 9);
    localGraphics2D.setColor(new Color(242, 242, 242));
    localGraphics2D.fillRect(this.lineX - 3, this.lineY - this.fontSize - 4, this.w - 39, this.fontSize + 8);

    Shape localShape = paramGraphics.getClip();
    paramGraphics.setClip(this.lineX - 3, this.lineY - this.fontSize - 4, this.w - 39, this.fontSize + 8);
    localGraphics2D.setColor(new Color(0, 0, 0));
    localGraphics2D.setFont(this.answerFont);
    localGraphics2D.drawString(this.answerString, this.lineX, this.lineY - 1);
    paramGraphics.setClip(localShape);

    localGraphics2D.drawImage(Skin.promptCheckButton, null, this.lineX + this.w - 38, this.lineY - this.fontSize - 6);
  }

  public boolean mouseDown(int paramInt1, int paramInt2, PlayerCanvas paramPlayerCanvas)
  {
    if ((paramInt1 > this.lineX + this.w - 38) && (paramInt1 < this.lineX + this.w - 38 + 20) && (paramInt2 > this.lineY - this.fontSize - 6) && (paramInt2 < this.lineY - this.fontSize - 6 + 20))
    {
      paramPlayerCanvas.hideAskPrompt();
      return true;
    }
    return false;
  }

  public void handleKeystroke(int paramInt, PlayerCanvas paramPlayerCanvas) {
    paramPlayerCanvas.inval(rect());
    if ((paramInt == 3) || (paramInt == 10)) {
      paramPlayerCanvas.hideAskPrompt();
      return;
    }
    if ((paramInt == 8) || (paramInt == 127)) {
      if (this.answerString.length() > 0) this.answerString = this.answerString.substring(0, this.answerString.length() - 1);
      return;
    }

    char[] arrayOfChar = new char[1];
    arrayOfChar[0] = (char)paramInt;
    if (paramInt >= 32) this.answerString += new String(arrayOfChar);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     AskPrompter
 * JD-Core Version:    0.6.0
 */