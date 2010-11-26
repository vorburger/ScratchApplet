/* PlayerCanvas - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
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
import javax.sound.sampled.DataLine;
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
    double loadingFraction = 0.2;
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
    /*synthetic*/ static Class class$javax$sound$sampled$TargetDataLine;
    
    PlayerCanvas() {
	setLayout(null);
	addKeyListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
    }
    
    public void addNotify() {
	super.addNotify();
	((PlayerCanvas) this).offscreen
	    = (BufferedImage) createImage(482, 387);
	((PlayerCanvas) this).offscreen.getRaster();
	Graphics graphics = ((PlayerCanvas) this).offscreen.getGraphics();
	graphics.setColor(WHITE);
	graphics.fillRect(0, 0, 482, 387);
	graphics.dispose();
	repaint();
    }
    
    public Dimension getMinimumSize() {
	return new Dimension(482, 387);
    }
    
    public Dimension getPreferredSize() {
	return new Dimension(482, 387);
    }
    
    public synchronized void paintComponent(Graphics graphics) {
	graphics.drawImage(((PlayerCanvas) this).offscreen, 0, 0, 482, 387,
			   this);
    }
    
    void clearall(LContext lcontext) {
	((PlayerCanvas) this).stage = null;
	((PlayerCanvas) this).sprites = new Object[0];
	((PlayerCanvas) this).askPrompt = null;
	((PlayerCanvas) this).lastAnswer = "";
	((PlayerCanvas) this).penTrails = null;
	SoundPlayer.stopSoundsForApplet(lcontext);
	((PlayerCanvas) this).soundLevel = 0;
	((LContext) lcontext).props = new Hashtable();
	Runtime.getRuntime().gc();
	clearkeys();
	((PlayerCanvas) this).mouseclicks = new Vector();
	((PlayerCanvas) this).mouseIsDown = false;
	((PlayerCanvas) this).mouseDragTarget = null;
    }
    
    void setMessage(String string) {
	((PlayerCanvas) this).message = string;
	redraw_all();
    }
    
    synchronized void inval(Rectangle rectangle) {
	if (((PlayerCanvas) this).invalrect.isEmpty())
	    ((PlayerCanvas) this).invalrect = new Rectangle(rectangle);
	else
	    ((PlayerCanvas) this).invalrect
		= ((PlayerCanvas) this).invalrect.union(rectangle);
    }
    
    void invalAll() {
	inval(new Rectangle(0, 0, 482, 387));
    }
    
    void redraw_all() {
	redraw(new Rectangle(0, 0, 482, 387), false);
    }
    
    void redraw_invalid() {
	redraw(((PlayerCanvas) this).invalrect, false);
    }
    
    synchronized void redraw(Rectangle rectangle, boolean bool) {
	Graphics graphics = ((PlayerCanvas) this).offscreen.getGraphics();
	graphics.setClip(rectangle.x, rectangle.y, rectangle.width,
			 rectangle.height);
	graphics.setColor(WHITE);
	graphics.fillRect(0, 0, 482, 387);
	if (((PlayerCanvas) this).isLoading)
	    drawProgressBar(graphics);
	else {
	    if (((PlayerCanvas) this).stage != null) {
		((PlayerCanvas) this).stage.setStageOffset();
		((PlayerCanvas) this).stage.paint(graphics);
	    }
	    if (((PlayerCanvas) this).penTrails != null)
		graphics.drawImage(((PlayerCanvas) this).penTrails, 0, 0, 482,
				   387, null);
	    for (int i = ((PlayerCanvas) this).sprites.length - 1; i >= 0;
		 i--) {
		Drawable drawable
		    = (Drawable) ((PlayerCanvas) this).sprites[i];
		if (drawable.isShowing()
		    && rectangle.intersects(drawable.fullRect()))
		    drawable.paint(graphics);
	    }
	    for (int i = ((PlayerCanvas) this).sprites.length - 1; i >= 0;
		 i--) {
		Drawable drawable
		    = (Drawable) ((PlayerCanvas) this).sprites[i];
		if (drawable.isShowing()
		    && rectangle.intersects(drawable.fullRect()))
		    drawable.paintBubble(graphics);
	    }
	    if (((PlayerCanvas) this).askPrompt != null)
		((PlayerCanvas) this).askPrompt.paint(graphics);
	}
	drawBorder(graphics);
	if (bool) {
	    graphics.setColor(new Color(200, 0, 0));
	    graphics.fillRect(rectangle.x, rectangle.y, rectangle.width,
			      rectangle.height);
	}
	graphics.dispose();
	repaint(rectangle);
	((PlayerCanvas) this).invalrect = new Rectangle();
    }
    
    void drawBorder(Graphics graphics) {
	graphics.setColor(new Color(130, 130, 130));
	graphics.fillRect(0, 0, 482, 25);
	graphics.setColor(BLACK);
	graphics.fillRect(0, 0, 482, 1);
	graphics.fillRect(0, 0, 1, 387);
	graphics.fillRect(0, 386, 482, 1);
	graphics.fillRect(481, 0, 1, 387);
	graphics.fillRect(0, 25, 482, 1);
	graphics.drawImage((((PlayerCanvas) this).overGoButton
			    ? Skin.goButtonOver : Skin.goButton),
			   418, 4, null);
	graphics.drawImage((((PlayerCanvas) this).overStopButton
			    ? Skin.stopButtonOver : Skin.stopButton),
			   451, 4, null);
	graphics.setColor(WHITE);
	graphics.setFont(new Font("SansSerif", 0, 8));
	graphics.drawString("v38", 5, 11);
	if (((PlayerCanvas) this).message.length() > 0) {
	    graphics.setFont(new Font("SansSerif", 1, 13));
	    graphics.setColor(new Color(250, 250, 0));
	    graphics.drawString(((PlayerCanvas) this).message, 70, 17);
	}
    }
    
    void drawProgressBar(Graphics graphics) {
	graphics.setColor(BLACK);
	graphics.setFont(new Font("SansSerif", 1, 24));
	graphics.drawString("Loading...", 188, 173);
	int i = 91;
	int i_0_ = 193;
	graphics.fillRect(i, i_0_, 300, 1);
	graphics.fillRect(i, i_0_, 1, 29);
	graphics.fillRect(i, i_0_ + 28, 300, 1);
	graphics.fillRect(i + 299, i_0_, 1, 29);
	graphics.setColor(new Color(10, 10, 200));
	graphics.fillRect(i + 2, i_0_ + 2,
			  (int) (296.0 * Math.min((((PlayerCanvas) this)
						   .loadingFraction),
						  1.0)),
			  25);
	drawBorder(graphics);
    }
    
    BufferedImage drawAreaWithoutSprite(Rectangle rectangle, Sprite sprite) {
	BufferedImage bufferedimage
	    = new BufferedImage(rectangle.width, rectangle.height, 2);
	Graphics graphics = bufferedimage.getGraphics();
	graphics.setColor(WHITE);
	graphics.fillRect(0, 0, rectangle.width, rectangle.height);
	graphics = graphics.create(-rectangle.x, -rectangle.y, rectangle.width,
				   rectangle.height);
	graphics.setClip(rectangle.x, rectangle.y, rectangle.width,
			 rectangle.height);
	if (((PlayerCanvas) this).stage != null) {
	    ((PlayerCanvas) this).stage.setStageOffset();
	    ((PlayerCanvas) this).stage.paint(graphics);
	}
	if (((PlayerCanvas) this).penTrails != null)
	    graphics.drawImage(((PlayerCanvas) this).penTrails, 0, 0, 482, 387,
			       null);
	for (int i = ((PlayerCanvas) this).sprites.length - 1; i >= 0; i--) {
	    Drawable drawable = (Drawable) ((PlayerCanvas) this).sprites[i];
	    if (drawable != sprite && drawable.isShowing()
		&& rectangle.intersects(drawable.rect()))
		drawable.paint(graphics);
	}
	graphics.dispose();
	return bufferedimage;
    }
    
    void drawRect(int i, int i_1_, int i_2_, int i_3_) {
	Graphics graphics = ((PlayerCanvas) this).offscreen.getGraphics();
	graphics.setColor(BLACK);
	graphics.fillRect(i, i_1_, i_2_, i_3_);
	graphics.dispose();
	repaint();
    }
    
    void startLoading() {
	((PlayerCanvas) this).isLoading = true;
	((PlayerCanvas) this).loadingFraction = 0.0;
	redraw_all();
    }
    
    void stopLoading() {
	((PlayerCanvas) this).loadingFraction = 1.0;
	redraw_all();
	((PlayerCanvas) this).loadingFraction = 0.0;
	((PlayerCanvas) this).isLoading = false;
    }
    
    void loadingProgress(double d) {
	((PlayerCanvas) this).loadingFraction = d;
	redraw_all();
    }
    
    boolean logoIsRunning() {
	return ((LContext) ((PlayerCanvas) this).lc).logoThread != null;
    }
    
    void updatePenTrails() {
	for (int i = ((PlayerCanvas) this).sprites.length - 1; i >= 0; i--) {
	    if (((PlayerCanvas) this).sprites[i] instanceof Sprite) {
		Sprite sprite = (Sprite) ((PlayerCanvas) this).sprites[i];
		if (((Sprite) sprite).penDown)
		    updatePenTrailForSprite(sprite);
	    }
	}
    }
    
    void updatePenTrailForSprite(Sprite sprite) {
	if (((PlayerCanvas) this).penTrails == null)
	    createPenTrails();
	int i = 241 + (int) ((Sprite) sprite).x;
	int i_4_ = 206 - (int) ((Sprite) sprite).y;
	if ((double) ((Sprite) sprite).lastPenX == -1000000.0) {
	    ((Sprite) sprite).lastPenX = i;
	    ((Sprite) sprite).lastPenY = i_4_;
	} else if (((Sprite) sprite).lastPenX == i
		   && ((Sprite) sprite).lastPenY == i_4_)
	    return;
	Graphics2D graphics2d
	    = ((PlayerCanvas) this).penTrails.createGraphics();
	graphics2d.setColor(((Sprite) sprite).penColor);
	graphics2d.setStroke(new BasicStroke((float) ((Sprite) sprite).penSize,
					     1, 1));
	graphics2d.drawLine(((Sprite) sprite).lastPenX,
			    ((Sprite) sprite).lastPenY, i, i_4_);
	graphics2d.dispose();
	Rectangle rectangle = new Rectangle(((Sprite) sprite).lastPenX,
					    ((Sprite) sprite).lastPenY, 0, 0);
	rectangle.add(i, i_4_);
	rectangle.grow(((Sprite) sprite).penSize, ((Sprite) sprite).penSize);
	inval(rectangle);
	((Sprite) sprite).lastPenX = i;
	((Sprite) sprite).lastPenY = i_4_;
    }
    
    void stampCostume(Sprite sprite) {
	if (((PlayerCanvas) this).penTrails == null)
	    createPenTrails();
	Graphics2D graphics2d
	    = ((PlayerCanvas) this).penTrails.createGraphics();
	if (((Sprite) sprite).filterChanged)
	    sprite.applyFilters();
	graphics2d.drawImage(sprite.outImage(), sprite.screenX(),
			     sprite.screenY(), null);
	graphics2d.dispose();
	sprite.inval();
    }
    
    void createPenTrails() {
	((PlayerCanvas) this).penTrails = new BufferedImage(482, 387, 2);
	((PlayerCanvas) this).penTrails.getRaster();
    }
    
    void clearPenTrails() {
	if (((PlayerCanvas) this).penTrails != null)
	    ((PlayerCanvas) this).penTrails.flush();
	((PlayerCanvas) this).penTrails = null;
	inval(new Rectangle(0, 0, 482, 387));
    }
    
    public void mouseEntered(MouseEvent mouseevent) {
	requestFocus();
	mouseDragOrMove(mouseevent);
    }
    
    public void mouseExited(MouseEvent mouseevent) {
	updateMouseXY(mouseevent);
    }
    
    public void mousePressed(MouseEvent mouseevent) {
	mouseDown(mouseevent);
    }
    
    public void mouseReleased(MouseEvent mouseevent) {
	mouseUp(mouseevent);
    }
    
    public void mouseClicked(MouseEvent mouseevent) {
	updateMouseXY(mouseevent);
    }
    
    public void mouseDragged(MouseEvent mouseevent) {
	mouseDragOrMove(mouseevent);
    }
    
    public void mouseMoved(MouseEvent mouseevent) {
	mouseDragOrMove(mouseevent);
    }
    
    void mouseDown(MouseEvent mouseevent) {
	updateMouseXY(mouseevent);
	requestFocus();
	if (inGoButton(mouseevent)) {
	    doStopButton();
	    LogoCommandRunner.startLogoThread("greenflag",
					      ((PlayerCanvas) this).lc);
	} else if (inStopButton(mouseevent)) {
	    doStopButton();
	    LogoCommandRunner.startLogoThread("interact",
					      ((PlayerCanvas) this).lc);
	} else {
	    ((PlayerCanvas) this).mouseIsDown = true;
	    ((PlayerCanvas) this).mouseDownX = mouseevent.getX();
	    ((PlayerCanvas) this).mouseDownY = mouseevent.getY();
	    ((PlayerCanvas) this).mouseDragTarget
		= findDragTarget(mouseevent.getX(), mouseevent.getY());
	    ((PlayerCanvas) this).mouseDragXOffset
		= ((PlayerCanvas) this).mouseDragYOffset = 0;
	    ((PlayerCanvas) this).reportClickOnMouseUp = true;
	    if (((PlayerCanvas) this).mouseDragTarget instanceof Sprite) {
		Sprite sprite = (Sprite) ((PlayerCanvas) this).mouseDragTarget;
		if (((Sprite) sprite).isDraggable) {
		    ((PlayerCanvas) this).mouseDragXOffset
			= sprite.screenX() - mouseevent.getX();
		    ((PlayerCanvas) this).mouseDragYOffset
			= sprite.screenY() - mouseevent.getY();
		    moveSpriteToFront(sprite);
		} else
		    ((PlayerCanvas) this).mouseDragTarget = null;
	    }
	    if (((PlayerCanvas) this).mouseDragTarget instanceof ListWatcher) {
		ListWatcher listwatcher
		    = (ListWatcher) ((PlayerCanvas) this).mouseDragTarget;
		listwatcher.mouseDown(mouseevent.getX(), mouseevent.getY());
	    }
	    if (((PlayerCanvas) this).askPrompt != null) {
		boolean bool = (((PlayerCanvas) this).askPrompt.mouseDown
				(mouseevent.getX(), mouseevent.getY(), this));
		if (bool)
		    return;
	    }
	    if (((PlayerCanvas) this).mouseDragTarget == null)
		reportClick();
	}
    }
    
    void mouseDragOrMove(MouseEvent mouseevent) {
	updateMouseXY(mouseevent);
	if (mouseevent.getX() != ((PlayerCanvas) this).mouseDownX
	    || mouseevent.getY() != ((PlayerCanvas) this).mouseDownY)
	    ((PlayerCanvas) this).reportClickOnMouseUp = false;
	if (((PlayerCanvas) this).mouseDragTarget != null)
	    ((PlayerCanvas) this).mouseDragTarget.dragTo
		(mouseevent.getX() + ((PlayerCanvas) this).mouseDragXOffset,
		 mouseevent.getY() + ((PlayerCanvas) this).mouseDragYOffset);
	else {
	    boolean bool = ((PlayerCanvas) this).overGoButton;
	    boolean bool_5_ = ((PlayerCanvas) this).overStopButton;
	    ((PlayerCanvas) this).overGoButton = inGoButton(mouseevent);
	    ((PlayerCanvas) this).overStopButton = inStopButton(mouseevent);
	    if (bool != ((PlayerCanvas) this).overGoButton
		|| bool_5_ != ((PlayerCanvas) this).overStopButton) {
		inval(new Rectangle(0, 0, 482, 26));
		redraw_invalid();
	    }
	}
    }
    
    void mouseUp(MouseEvent mouseevent) {
	updateMouseXY(mouseevent);
	if (((PlayerCanvas) this).reportClickOnMouseUp) {
	    if (((PlayerCanvas) this).mouseDragTarget instanceof Watcher)
		((Watcher) ((PlayerCanvas) this).mouseDragTarget)
		    .click(mouseevent.getX(), mouseevent.getY());
	    else
		reportClick();
	}
	((PlayerCanvas) this).mouseDragTarget = null;
	((PlayerCanvas) this).mouseIsDown = false;
    }
    
    void reportClick() {
	Object[] objects = new Object[2];
	objects[0]
	    = new Double((double) (((PlayerCanvas) this).mouseDownX - 241));
	objects[1]
	    = new Double((double) (206 - ((PlayerCanvas) this).mouseDownY));
	((PlayerCanvas) this).mouseclicks.addElement(objects);
	((PlayerCanvas) this).reportClickOnMouseUp = false;
    }
    
    void updateMouseXY(MouseEvent mouseevent) {
	((PlayerCanvas) this).mouseX = mouseevent.getX() - 241;
	((PlayerCanvas) this).mouseY = 206 - mouseevent.getY();
    }
    
    boolean inGoButton(MouseEvent mouseevent) {
	if (mouseevent.getY() >= 26)
	    return false;
	int i = mouseevent.getX();
	return i >= 418 && i <= 418 + Skin.goButton.getWidth(null);
    }
    
    boolean inStopButton(MouseEvent mouseevent) {
	if (mouseevent.getY() >= 26)
	    return false;
	int i = mouseevent.getX();
	return i >= 451 && i <= 451 + Skin.stopButton.getWidth(null);
    }
    
    void doStopButton() {
	SoundPlayer.stopSoundsForApplet(((PlayerCanvas) this).lc);
	LogoCommandRunner.stopLogoThread(((PlayerCanvas) this).lc);
	new LogoCommandRunner("stopAll", ((PlayerCanvas) this).lc, true).run();
	clearkeys();
	((PlayerCanvas) this).mouseclicks = new Vector();
	((PlayerCanvas) this).mouseIsDown = false;
	((PlayerCanvas) this).mouseDragTarget = null;
	redraw_all();
    }
    
    Drawable findDragTarget(int i, int i_6_) {
	for (int i_7_ = 0; i_7_ < ((PlayerCanvas) this).sprites.length;
	     i_7_++) {
	    if (((PlayerCanvas) this).sprites[i_7_] instanceof Watcher) {
		Watcher watcher
		    = (Watcher) ((PlayerCanvas) this).sprites[i_7_];
		if (watcher.inSlider(i, i_6_))
		    return watcher;
	    }
	    if (((PlayerCanvas) this).sprites[i_7_] instanceof ListWatcher) {
		ListWatcher listwatcher
		    = (ListWatcher) ((PlayerCanvas) this).sprites[i_7_];
		if (listwatcher.inScrollbar(i, i_6_))
		    return listwatcher;
	    }
	    if (((PlayerCanvas) this).sprites[i_7_] instanceof Sprite) {
		Sprite sprite = (Sprite) ((PlayerCanvas) this).sprites[i_7_];
		if (sprite.isShowing() && sprite.containsPoint(i, i_6_))
		    return sprite;
	    }
	}
	return null;
    }
    
    void moveSpriteToFront(Sprite sprite) {
	int i = -1;
	for (int i_8_ = 0; i_8_ < ((PlayerCanvas) this).sprites.length;
	     i_8_++) {
	    if (((PlayerCanvas) this).sprites[i_8_] == sprite)
		i = i_8_;
	}
	if (i >= 0) {
	    Object object = ((PlayerCanvas) this).sprites[i];
	    for (int i_9_ = i; i_9_ > 0; i_9_--)
		((PlayerCanvas) this).sprites[i_9_]
		    = ((PlayerCanvas) this).sprites[i_9_ - 1];
	    ((PlayerCanvas) this).sprites[0] = object;
	    sprite.inval();
	}
    }
    
    public void keyPressed(KeyEvent keyevent) {
	int i = keyCodeFor(keyevent);
	((PlayerCanvas) this).keydown[i] = true;
	if (i == 10 || i >= 28 && i <= 31)
	    ((PlayerCanvas) this).keystrokes
		.addElement(new Double((double) i));
    }
    
    public void keyReleased(KeyEvent keyevent) {
	int i = keyCodeFor(keyevent);
	((PlayerCanvas) this).keydown[i] = false;
    }
    
    public void keyTyped(KeyEvent keyevent) {
	int i = keyevent.getKeyChar();
	if (((PlayerCanvas) this).askPrompt != null)
	    ((PlayerCanvas) this).askPrompt.handleKeystroke(i, this);
	else {
	    if (i >= 65 && i <= 90)
		i += 32;
	    ((PlayerCanvas) this).keystrokes
		.addElement(new Double((double) i));
	}
    }
    
    int keyCodeFor(KeyEvent keyevent) {
	int i = keyevent.getKeyCode();
	if (i == 10)
	    return 10;
	if (i == 37)
	    return 28;
	if (i == 38)
	    return 30;
	if (i == 39)
	    return 29;
	if (i == 40)
	    return 31;
	if (i >= 65 && i <= 90)
	    return i + 32;
	return Math.min(i, 255);
    }
    
    void clearkeys() {
	for (int i = 0; i < ((PlayerCanvas) this).keydown.length; i++)
	    ((PlayerCanvas) this).keydown[i] = false;
	((PlayerCanvas) this).keystrokes = new Vector();
    }
    
    int soundLevel() {
	if (soundInputLine == null)
	    return 0;
	int i = soundInputLine.available();
	if (i == 0)
	    return ((PlayerCanvas) this).soundLevel;
	i = soundInputLine.read(((PlayerCanvas) this).soundInputBuf, 0, i);
	int i_10_ = 0;
	for (int i_11_ = 0; i_11_ < i / 2; i_11_++) {
	    int i_12_ = ((((PlayerCanvas) this).soundInputBuf[2 * i_11_] << 8)
			 + ((PlayerCanvas) this).soundInputBuf[2 * i_11_ + 1]);
	    if (i_12_ >= 32768)
		i_12_ = 65536 - i_12_;
	    if (i_12_ > i_10_)
		i_10_ = i_12_;
	}
	((PlayerCanvas) this).soundLevel = i_10_ / 327;
	return ((PlayerCanvas) this).soundLevel;
    }
    
    void openSoundInput() {
	if (soundInputLine != null)
	    soundInputLine.close();
	AudioFormat audioformat = new AudioFormat(44100.0F, 16, 1, true, true);
	DataLine.Info info
	    = (new DataLine.Info
	       ((class$javax$sound$sampled$TargetDataLine == null
		 ? (class$javax$sound$sampled$TargetDataLine
		    = class$("javax.sound.sampled.TargetDataLine"))
		 : class$javax$sound$sampled$TargetDataLine),
		audioformat));
	try {
	    soundInputLine = (TargetDataLine) AudioSystem.getLine(info);
	    soundInputLine.open(audioformat, 50000);
	    soundInputLine.start();
	} catch (LineUnavailableException lineunavailableexception) {
	    soundInputLine = null;
	}
    }
    
    void showAskPrompt(String string) {
	((PlayerCanvas) this).askPrompt = new AskPrompter(string);
	invalAll();
    }
    
    void hideAskPrompt() {
	if (((PlayerCanvas) this).askPrompt != null)
	    ((PlayerCanvas) this).lastAnswer
		= ((AskPrompter) ((PlayerCanvas) this).askPrompt).answerString;
	((PlayerCanvas) this).askPrompt = null;
	invalAll();
    }
    
    boolean askPromptShowing() {
	return ((PlayerCanvas) this).askPrompt != null;
    }
    
    /*synthetic*/ static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
}
