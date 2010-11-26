import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JPanel;

class PlayerCanvas extends JPanel
  implements KeyListener, MouseListener, MouseMotionListener
{
  static final String versionString = "v38";
  static final int width = 482;
  static final int height = 387;
  static final int topBarHeight = 26;
  static final int goButtonX = 418;
  static final int stopButtonX = 451;
  static final int buttonY = 4;
  static final Color BLACK = new Color(0, 0, 0);
  static final Color WHITE = new Color(255, 255, 255);
  static final int soundInputBufSize = 50000;
  static TargetDataLine soundInputLine;
  byte[] soundInputBuf = new byte[50000];
  int soundLevel = 0;

  boolean overGoButton = false;
  boolean overStopButton = false;

  String message = "";
  boolean isLoading = true;
  double loadingFraction = 0.2D;
  LContext lc;
  Sprite stage;
  Object[] sprites = new Object[0];
  BufferedImage offscreen;
  BufferedImage penTrails;
  Rectangle invalrect = new Rectangle();
  int mouseX;
  int mouseY;
  boolean mouseIsDown = false;
  int mouseDownX;
  int mouseDownY;
  Drawable mouseDragTarget;
  int mouseDragXOffset;
  int mouseDragYOffset;
  boolean reportClickOnMouseUp;
  Vector mouseclicks = new Vector();
  boolean[] keydown = new boolean[256];
  Vector keystrokes = new Vector();

  AskPrompter askPrompt = null;
  String lastAnswer = "";

  PlayerCanvas() {
    setLayout(null);
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void addNotify() {
    super.addNotify();
    this.offscreen = ((BufferedImage)createImage(482, 387));
    this.offscreen.getRaster();
    Graphics localGraphics = this.offscreen.getGraphics();
    localGraphics.setColor(WHITE);
    localGraphics.fillRect(0, 0, 482, 387);
    localGraphics.dispose();

    repaint();
  }
  public Dimension getMinimumSize() {
    return new Dimension(482, 387); } 
  public Dimension getPreferredSize() { return new Dimension(482, 387); }

  public synchronized void paintComponent(Graphics paramGraphics) {
    paramGraphics.drawImage(this.offscreen, 0, 0, 482, 387, this);
  }

  void clearall(LContext paramLContext) {
    this.stage = null;
    this.sprites = new Object[0];
    this.askPrompt = null;
    this.lastAnswer = "";
    this.penTrails = null;
    SoundPlayer.stopSoundsForApplet(paramLContext);
    this.soundLevel = 0;
    paramLContext.props = new Hashtable();
    Runtime.getRuntime().gc();
    clearkeys();
    this.mouseclicks = new Vector();
    this.mouseIsDown = false;
    this.mouseDragTarget = null;
  }

  void setMessage(String paramString) {
    this.message = paramString;
    redraw_all();
  }

  synchronized void inval(Rectangle paramRectangle) {
    if (this.invalrect.isEmpty()) this.invalrect = new Rectangle(paramRectangle); else
      this.invalrect = this.invalrect.union(paramRectangle); 
  }

  void invalAll() {
    inval(new Rectangle(0, 0, 482, 387));
  }
  void redraw_all() { redraw(new Rectangle(0, 0, 482, 387), false); } 
  void redraw_invalid() { redraw(this.invalrect, false); }

  synchronized void redraw(Rectangle paramRectangle, boolean paramBoolean) {
    Graphics localGraphics = this.offscreen.getGraphics();
    localGraphics.setClip(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    localGraphics.setColor(WHITE);
    localGraphics.fillRect(0, 0, 482, 387);
    if (this.isLoading) {
      drawProgressBar(localGraphics);
    } else {
      if (this.stage != null) { this.stage.setStageOffset(); this.stage.paint(localGraphics); }
      if (this.penTrails != null) localGraphics.drawImage(this.penTrails, 0, 0, 482, 387, null);
      Drawable localDrawable;
      for (int i = this.sprites.length - 1; i >= 0; i--) {
        localDrawable = (Drawable)this.sprites[i];
        if ((!localDrawable.isShowing()) || (!paramRectangle.intersects(localDrawable.fullRect()))) continue; localDrawable.paint(localGraphics);
      }
      for (i = this.sprites.length - 1; i >= 0; i--) {
        localDrawable = (Drawable)this.sprites[i];
        if ((!localDrawable.isShowing()) || (!paramRectangle.intersects(localDrawable.fullRect()))) continue; localDrawable.paintBubble(localGraphics);
      }
      if (this.askPrompt != null) this.askPrompt.paint(localGraphics);
    }
    drawBorder(localGraphics);
    if (paramBoolean) {
      localGraphics.setColor(new Color(200, 0, 0));
      localGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    }
    localGraphics.dispose();
    repaint(paramRectangle);
    this.invalrect = new Rectangle();
  }

  void drawBorder(Graphics paramGraphics) {
    paramGraphics.setColor(new Color(130, 130, 130));
    paramGraphics.fillRect(0, 0, 482, 25);

    paramGraphics.setColor(BLACK);
    paramGraphics.fillRect(0, 0, 482, 1);
    paramGraphics.fillRect(0, 0, 1, 387);
    paramGraphics.fillRect(0, 386, 482, 1);
    paramGraphics.fillRect(481, 0, 1, 387);
    paramGraphics.fillRect(0, 25, 482, 1);

    paramGraphics.drawImage(this.overGoButton ? Skin.goButtonOver : Skin.goButton, 418, 4, null);

    paramGraphics.drawImage(this.overStopButton ? Skin.stopButtonOver : Skin.stopButton, 451, 4, null);

    paramGraphics.setColor(WHITE);
    paramGraphics.setFont(new Font("SansSerif", 0, 8));
    paramGraphics.drawString("v38", 5, 11);
    if (this.message.length() > 0) {
      paramGraphics.setFont(new Font("SansSerif", 1, 13));
      paramGraphics.setColor(new Color(250, 250, 0));
      paramGraphics.drawString(this.message, 70, 17);
    }
  }

  void drawProgressBar(Graphics paramGraphics) {
    paramGraphics.setColor(BLACK);
    paramGraphics.setFont(new Font("SansSerif", 1, 24));
    paramGraphics.drawString("Loading...", 188, 173);

    int i = 91;
    int j = 193;
    paramGraphics.fillRect(i, j, 300, 1);
    paramGraphics.fillRect(i, j, 1, 29);
    paramGraphics.fillRect(i, j + 28, 300, 1);
    paramGraphics.fillRect(i + 299, j, 1, 29);

    paramGraphics.setColor(new Color(10, 10, 200));
    paramGraphics.fillRect(i + 2, j + 2, (int)(296.0D * Math.min(this.loadingFraction, 1.0D)), 25);

    drawBorder(paramGraphics);
  }

  BufferedImage drawAreaWithoutSprite(Rectangle paramRectangle, Sprite paramSprite) {
    BufferedImage localBufferedImage = new BufferedImage(paramRectangle.width, paramRectangle.height, 2);
    Graphics localGraphics = localBufferedImage.getGraphics();
    localGraphics.setColor(WHITE);
    localGraphics.fillRect(0, 0, paramRectangle.width, paramRectangle.height);
    localGraphics = localGraphics.create(-paramRectangle.x, -paramRectangle.y, paramRectangle.width, paramRectangle.height);
    localGraphics.setClip(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    if (this.stage != null) { this.stage.setStageOffset(); this.stage.paint(localGraphics); }
    if (this.penTrails != null) localGraphics.drawImage(this.penTrails, 0, 0, 482, 387, null);
    for (int i = this.sprites.length - 1; i >= 0; i--) {
      Drawable localDrawable = (Drawable)this.sprites[i];
      if ((localDrawable == paramSprite) || (!localDrawable.isShowing()) || (!paramRectangle.intersects(localDrawable.rect()))) continue; localDrawable.paint(localGraphics);
    }
    localGraphics.dispose();
    return localBufferedImage;
  }

  void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Graphics localGraphics = this.offscreen.getGraphics();
    localGraphics.setColor(BLACK);
    localGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
    localGraphics.dispose();
    repaint();
  }

  void startLoading() {
    this.isLoading = true;
    this.loadingFraction = 0.0D;
    redraw_all();
  }

  void stopLoading() {
    this.loadingFraction = 1.0D;
    redraw_all();
    this.loadingFraction = 0.0D;
    this.isLoading = false;
  }

  void loadingProgress(double paramDouble) {
    this.loadingFraction = paramDouble;
    redraw_all();
  }

  boolean logoIsRunning() {
    return this.lc.logoThread != null;
  }

  void updatePenTrails() {
    for (int i = this.sprites.length - 1; i >= 0; i--)
      if ((this.sprites[i] instanceof Sprite)) {
        Sprite localSprite = (Sprite)this.sprites[i];
        if (!localSprite.penDown) continue; updatePenTrailForSprite(localSprite);
      }
  }

  void updatePenTrailForSprite(Sprite paramSprite)
  {
    if (this.penTrails == null) createPenTrails();
    int i = 241 + (int)paramSprite.x;
    int j = 206 - (int)paramSprite.y;
    if (paramSprite.lastPenX == -1000000.0D) {
      paramSprite.lastPenX = i;
      paramSprite.lastPenY = j;
    }
    else if ((paramSprite.lastPenX == i) && (paramSprite.lastPenY == j)) { return;
    }
    Graphics2D localGraphics2D = this.penTrails.createGraphics();
    localGraphics2D.setColor(paramSprite.penColor);
    localGraphics2D.setStroke(new BasicStroke(paramSprite.penSize, 1, 1));
    localGraphics2D.drawLine(paramSprite.lastPenX, paramSprite.lastPenY, i, j);
    localGraphics2D.dispose();

    Rectangle localRectangle = new Rectangle(paramSprite.lastPenX, paramSprite.lastPenY, 0, 0);
    localRectangle.add(i, j);
    localRectangle.grow(paramSprite.penSize, paramSprite.penSize);
    inval(localRectangle);

    paramSprite.lastPenX = i;
    paramSprite.lastPenY = j;
  }

  void stampCostume(Sprite paramSprite) {
    if (this.penTrails == null) createPenTrails();
    Graphics2D localGraphics2D = this.penTrails.createGraphics();
    if (paramSprite.filterChanged) paramSprite.applyFilters();
    localGraphics2D.drawImage(paramSprite.outImage(), paramSprite.screenX(), paramSprite.screenY(), null);
    localGraphics2D.dispose();
    paramSprite.inval();
  }

  void createPenTrails() {
    this.penTrails = new BufferedImage(482, 387, 2);
    this.penTrails.getRaster();
  }

  void clearPenTrails() {
    if (this.penTrails != null) this.penTrails.flush();
    this.penTrails = null;
    inval(new Rectangle(0, 0, 482, 387));
  }
  public void mouseEntered(MouseEvent paramMouseEvent) {
    requestFocus(); mouseDragOrMove(paramMouseEvent); } 
  public void mouseExited(MouseEvent paramMouseEvent) { updateMouseXY(paramMouseEvent); } 
  public void mousePressed(MouseEvent paramMouseEvent) { mouseDown(paramMouseEvent); } 
  public void mouseReleased(MouseEvent paramMouseEvent) { mouseUp(paramMouseEvent); } 
  public void mouseClicked(MouseEvent paramMouseEvent) { updateMouseXY(paramMouseEvent); } 
  public void mouseDragged(MouseEvent paramMouseEvent) { mouseDragOrMove(paramMouseEvent); } 
  public void mouseMoved(MouseEvent paramMouseEvent) { mouseDragOrMove(paramMouseEvent); }

  void mouseDown(MouseEvent paramMouseEvent) {
    updateMouseXY(paramMouseEvent);
    requestFocus();
    if (inGoButton(paramMouseEvent)) {
      doStopButton();
      LogoCommandRunner.startLogoThread("greenflag", this.lc);
      return;
    }
    if (inStopButton(paramMouseEvent)) {
      doStopButton();
      LogoCommandRunner.startLogoThread("interact", this.lc);
      return;
    }
    this.mouseIsDown = true;
    this.mouseDownX = paramMouseEvent.getX();
    this.mouseDownY = paramMouseEvent.getY();
    this.mouseDragTarget = findDragTarget(paramMouseEvent.getX(), paramMouseEvent.getY());
    this.mouseDragXOffset = (this.mouseDragYOffset = 0);
    this.reportClickOnMouseUp = true;
    Object localObject;
    if ((this.mouseDragTarget instanceof Sprite)) {
      localObject = (Sprite)this.mouseDragTarget;
      if (((Sprite)localObject).isDraggable) {
        this.mouseDragXOffset = (((Sprite)localObject).screenX() - paramMouseEvent.getX());
        this.mouseDragYOffset = (((Sprite)localObject).screenY() - paramMouseEvent.getY());
        moveSpriteToFront((Sprite)localObject);
      } else {
        this.mouseDragTarget = null;
      }
    }
    if ((this.mouseDragTarget instanceof ListWatcher)) {
      localObject = (ListWatcher)this.mouseDragTarget;
      ((ListWatcher)localObject).mouseDown(paramMouseEvent.getX(), paramMouseEvent.getY());
    }
    if (this.askPrompt != null) {
      boolean bool = this.askPrompt.mouseDown(paramMouseEvent.getX(), paramMouseEvent.getY(), this);
      if (bool) return;
    }
    if (this.mouseDragTarget == null)
      reportClick();
  }

  void mouseDragOrMove(MouseEvent paramMouseEvent)
  {
    updateMouseXY(paramMouseEvent);
    if ((paramMouseEvent.getX() != this.mouseDownX) || (paramMouseEvent.getY() != this.mouseDownY)) {
      this.reportClickOnMouseUp = false;
    }
    if (this.mouseDragTarget != null) {
      this.mouseDragTarget.dragTo(paramMouseEvent.getX() + this.mouseDragXOffset, paramMouseEvent.getY() + this.mouseDragYOffset);
    } else {
      boolean bool1 = this.overGoButton;
      boolean bool2 = this.overStopButton;
      this.overGoButton = inGoButton(paramMouseEvent);
      this.overStopButton = inStopButton(paramMouseEvent);
      if ((bool1 != this.overGoButton) || (bool2 != this.overStopButton)) {
        inval(new Rectangle(0, 0, 482, 26));
        redraw_invalid();
      }
    }
  }

  void mouseUp(MouseEvent paramMouseEvent) {
    updateMouseXY(paramMouseEvent);
    if (this.reportClickOnMouseUp) {
      if ((this.mouseDragTarget instanceof Watcher))
        ((Watcher)this.mouseDragTarget).click(paramMouseEvent.getX(), paramMouseEvent.getY());
      else {
        reportClick();
      }
    }
    this.mouseDragTarget = null;
    this.mouseIsDown = false;
  }

  void reportClick() {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = new Double(this.mouseDownX - 241);
    arrayOfObject[1] = new Double(206 - this.mouseDownY);
    this.mouseclicks.addElement(arrayOfObject);
    this.reportClickOnMouseUp = false;
  }

  void updateMouseXY(MouseEvent paramMouseEvent) {
    this.mouseX = (paramMouseEvent.getX() - 241);
    this.mouseY = (206 - paramMouseEvent.getY());
  }

  boolean inGoButton(MouseEvent paramMouseEvent) {
    if (paramMouseEvent.getY() >= 26) return false;
    int i = paramMouseEvent.getX();
    return (i >= 418) && (i <= 418 + Skin.goButton.getWidth(null));
  }

  boolean inStopButton(MouseEvent paramMouseEvent) {
    if (paramMouseEvent.getY() >= 26) return false;
    int i = paramMouseEvent.getX();
    return (i >= 451) && (i <= 451 + Skin.stopButton.getWidth(null));
  }

  void doStopButton() {
    SoundPlayer.stopSoundsForApplet(this.lc);
    LogoCommandRunner.stopLogoThread(this.lc);
    new LogoCommandRunner("stopAll", this.lc, true).run();
    clearkeys();
    this.mouseclicks = new Vector();
    this.mouseIsDown = false;
    this.mouseDragTarget = null;
    redraw_all();
  }

  Drawable findDragTarget(int paramInt1, int paramInt2) {
    for (int i = 0; i < this.sprites.length; i++)
    {
      Object localObject;
      if ((this.sprites[i] instanceof Watcher)) {
        localObject = (Watcher)this.sprites[i];
        if (((Watcher)localObject).inSlider(paramInt1, paramInt2)) return localObject;
      }
      if ((this.sprites[i] instanceof ListWatcher)) {
        localObject = (ListWatcher)this.sprites[i];
        if (((ListWatcher)localObject).inScrollbar(paramInt1, paramInt2)) return localObject;
      }
      if ((this.sprites[i] instanceof Sprite)) {
        localObject = (Sprite)this.sprites[i];
        if ((((Sprite)localObject).isShowing()) && (((Sprite)localObject).containsPoint(paramInt1, paramInt2))) return localObject;
      }
    }
    return (Drawable)null;
  }

  void moveSpriteToFront(Sprite paramSprite) {
    int i = -1;
    for (int j = 0; j < this.sprites.length; j++) {
      if (this.sprites[j] != paramSprite) continue; i = j;
    }
    if (i < 0) return;
    Object localObject = this.sprites[i];
    for (int k = i; k > 0; k--) this.sprites[k] = this.sprites[(k - 1)];
    this.sprites[0] = localObject;
    paramSprite.inval();
  }

  public void keyPressed(KeyEvent paramKeyEvent) {
    int i = keyCodeFor(paramKeyEvent);
    this.keydown[i] = true;
    if ((i == 10) || ((i >= 28) && (i <= 31))) this.keystrokes.addElement(new Double(i)); 
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
    int i = keyCodeFor(paramKeyEvent);
    this.keydown[i] = false;
  }

  public void keyTyped(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyChar();

    if (this.askPrompt != null) {
      this.askPrompt.handleKeystroke(i, this);
      return;
    }

    if ((i >= 65) && (i <= 90)) i += 32;
    this.keystrokes.addElement(new Double(i));
  }

  int keyCodeFor(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    if (i == 10) return 10;
    if (i == 37) return 28;
    if (i == 38) return 30;
    if (i == 39) return 29;
    if (i == 40) return 31;
    if ((i >= 65) && (i <= 90)) return i + 32;
    return Math.min(i, 255);
  }

  void clearkeys() {
    for (int i = 0; i < this.keydown.length; i++) this.keydown[i] = false;
    this.keystrokes = new Vector();
  }

  int soundLevel() {
    if (soundInputLine == null) return 0;

    int i = soundInputLine.available();
    if (i == 0) return this.soundLevel;
    i = soundInputLine.read(this.soundInputBuf, 0, i);

    int j = 0;
    for (int k = 0; k < i / 2; k++) {
      int m = (this.soundInputBuf[(2 * k)] << 8) + this.soundInputBuf[(2 * k + 1)];
      if (m >= 32768) m = 65536 - m;
      if (m <= j) continue; j = m;
    }
    this.soundLevel = (j / 327);
    return this.soundLevel;
  }

  void openSoundInput() {
    if (soundInputLine != null) soundInputLine.close();
    AudioFormat localAudioFormat = new AudioFormat(44100.0F, 16, 1, true, true);
    DataLine.Info localInfo = new DataLine.Info(TargetDataLine.class, localAudioFormat);
    try {
      soundInputLine = (TargetDataLine)AudioSystem.getLine(localInfo);
      soundInputLine.open(localAudioFormat, 50000);
      soundInputLine.start();
    } catch (LineUnavailableException localLineUnavailableException) {
      soundInputLine = null;
    }
  }

  void showAskPrompt(String paramString) {
    this.askPrompt = new AskPrompter(paramString);
    invalAll();
  }

  void hideAskPrompt() {
    if (this.askPrompt != null) this.lastAnswer = this.askPrompt.answerString;
    this.askPrompt = null;
    invalAll();
  }

  boolean askPromptShowing() {
    return this.askPrompt != null;
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     PlayerCanvas
 * JD-Core Version:    0.6.0
 */