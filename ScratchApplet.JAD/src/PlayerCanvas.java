// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerPrims.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;
import javax.sound.sampled.*;
import javax.swing.JPanel;

class PlayerCanvas extends JPanel
    implements KeyListener, MouseListener, MouseMotionListener
{

    PlayerCanvas()
    {
        soundInputBuf = new byte[50000];
        soundLevel = 0;
        overGoButton = false;
        overStopButton = false;
        message = "";
        isLoading = true;
        loadingFraction = 0.20000000000000001D;
        sprites = new Object[0];
        invalrect = new Rectangle();
        mouseIsDown = false;
        mouseclicks = new Vector();
        keydown = new boolean[256];
        keystrokes = new Vector();
        askPrompt = null;
        lastAnswer = "";
        setLayout(null);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void addNotify()
    {
        super.addNotify();
        offscreen = (BufferedImage)createImage(482, 387);
        offscreen.getRaster();
        Graphics g = offscreen.getGraphics();
        g.setColor(WHITE);
        g.fillRect(0, 0, 482, 387);
        g.dispose();
        repaint();
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(482, 387);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(482, 387);
    }

    public synchronized void paintComponent(Graphics g)
    {
        g.drawImage(offscreen, 0, 0, 482, 387, this);
    }

    void clearall(LContext lcontext)
    {
        stage = null;
        sprites = new Object[0];
        askPrompt = null;
        lastAnswer = "";
        penTrails = null;
        SoundPlayer.stopSoundsForApplet(lcontext);
        soundLevel = 0;
        lcontext.props = new Hashtable();
        Runtime.getRuntime().gc();
        clearkeys();
        mouseclicks = new Vector();
        mouseIsDown = false;
        mouseDragTarget = null;
    }

    void setMessage(String s)
    {
        message = s;
        redraw_all();
    }

    synchronized void inval(Rectangle rectangle)
    {
        if(invalrect.isEmpty())
            invalrect = new Rectangle(rectangle);
        else
            invalrect = invalrect.union(rectangle);
    }

    void invalAll()
    {
        inval(new Rectangle(0, 0, 482, 387));
    }

    void redraw_all()
    {
        redraw(new Rectangle(0, 0, 482, 387), false);
    }

    void redraw_invalid()
    {
        redraw(invalrect, false);
    }

    synchronized void redraw(Rectangle rectangle, boolean flag)
    {
        Graphics g = offscreen.getGraphics();
        g.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(WHITE);
        g.fillRect(0, 0, 482, 387);
        if(isLoading)
        {
            drawProgressBar(g);
        } else
        {
            if(stage != null)
            {
                stage.setStageOffset();
                stage.paint(g);
            }
            if(penTrails != null)
                g.drawImage(penTrails, 0, 0, 482, 387, null);
            for(int i = sprites.length - 1; i >= 0; i--)
            {
                Drawable drawable = (Drawable)sprites[i];
                if(drawable.isShowing() && rectangle.intersects(drawable.fullRect()))
                    drawable.paint(g);
            }

            for(int j = sprites.length - 1; j >= 0; j--)
            {
                Drawable drawable1 = (Drawable)sprites[j];
                if(drawable1.isShowing() && rectangle.intersects(drawable1.fullRect()))
                    drawable1.paintBubble(g);
            }

            if(askPrompt != null)
                askPrompt.paint(g);
        }
        drawBorder(g);
        if(flag)
        {
            g.setColor(new Color(200, 0, 0));
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g.dispose();
        repaint(rectangle);
        invalrect = new Rectangle();
    }

    void drawBorder(Graphics g)
    {
        g.setColor(new Color(130, 130, 130));
        g.fillRect(0, 0, 482, 25);
        g.setColor(BLACK);
        g.fillRect(0, 0, 482, 1);
        g.fillRect(0, 0, 1, 387);
        g.fillRect(0, 386, 482, 1);
        g.fillRect(481, 0, 1, 387);
        g.fillRect(0, 25, 482, 1);
        g.drawImage(overGoButton ? ((java.awt.Image) (Skin.goButtonOver)) : ((java.awt.Image) (Skin.goButton)), 418, 4, null);
        g.drawImage(overStopButton ? ((java.awt.Image) (Skin.stopButtonOver)) : ((java.awt.Image) (Skin.stopButton)), 451, 4, null);
        g.setColor(WHITE);
        g.setFont(new Font("SansSerif", 0, 8));
        g.drawString("v38", 5, 11);
        if(message.length() > 0)
        {
            g.setFont(new Font("SansSerif", 1, 13));
            g.setColor(new Color(250, 250, 0));
            g.drawString(message, 70, 17);
        }
    }

    void drawProgressBar(Graphics g)
    {
        g.setColor(BLACK);
        g.setFont(new Font("SansSerif", 1, 24));
        g.drawString("Loading...", 188, 173);
        byte byte0 = 91;
        char c = '\301';
        g.fillRect(byte0, c, 300, 1);
        g.fillRect(byte0, c, 1, 29);
        g.fillRect(byte0, c + 28, 300, 1);
        g.fillRect(byte0 + 299, c, 1, 29);
        g.setColor(new Color(10, 10, 200));
        g.fillRect(byte0 + 2, c + 2, (int)(296D * Math.min(loadingFraction, 1.0D)), 25);
        drawBorder(g);
    }

    BufferedImage drawAreaWithoutSprite(Rectangle rectangle, Sprite sprite)
    {
        BufferedImage bufferedimage = new BufferedImage(rectangle.width, rectangle.height, 2);
        Graphics g = bufferedimage.getGraphics();
        g.setColor(WHITE);
        g.fillRect(0, 0, rectangle.width, rectangle.height);
        g = g.create(-rectangle.x, -rectangle.y, rectangle.width, rectangle.height);
        g.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if(stage != null)
        {
            stage.setStageOffset();
            stage.paint(g);
        }
        if(penTrails != null)
            g.drawImage(penTrails, 0, 0, 482, 387, null);
        for(int i = sprites.length - 1; i >= 0; i--)
        {
            Drawable drawable = (Drawable)sprites[i];
            if(drawable != sprite && drawable.isShowing() && rectangle.intersects(drawable.rect()))
                drawable.paint(g);
        }

        g.dispose();
        return bufferedimage;
    }

    void drawRect(int i, int j, int k, int l)
    {
        Graphics g = offscreen.getGraphics();
        g.setColor(BLACK);
        g.fillRect(i, j, k, l);
        g.dispose();
        repaint();
    }

    void startLoading()
    {
        isLoading = true;
        loadingFraction = 0.0D;
        redraw_all();
    }

    void stopLoading()
    {
        loadingFraction = 1.0D;
        redraw_all();
        loadingFraction = 0.0D;
        isLoading = false;
    }

    void loadingProgress(double d)
    {
        loadingFraction = d;
        redraw_all();
    }

    boolean logoIsRunning()
    {
        return lc.logoThread != null;
    }

    void updatePenTrails()
    {
        for(int i = sprites.length - 1; i >= 0; i--)
        {
            if(!(sprites[i] instanceof Sprite))
                continue;
            Sprite sprite = (Sprite)sprites[i];
            if(sprite.penDown)
                updatePenTrailForSprite(sprite);
        }

    }

    void updatePenTrailForSprite(Sprite sprite)
    {
        if(penTrails == null)
            createPenTrails();
        int i = 241 + (int)sprite.x;
        int j = 206 - (int)sprite.y;
        if((double)sprite.lastPenX == -1000000D)
        {
            sprite.lastPenX = i;
            sprite.lastPenY = j;
        } else
        if(sprite.lastPenX == i && sprite.lastPenY == j)
            return;
        Graphics2D graphics2d = penTrails.createGraphics();
        graphics2d.setColor(sprite.penColor);
        graphics2d.setStroke(new BasicStroke(sprite.penSize, 1, 1));
        graphics2d.drawLine(sprite.lastPenX, sprite.lastPenY, i, j);
        graphics2d.dispose();
        Rectangle rectangle = new Rectangle(sprite.lastPenX, sprite.lastPenY, 0, 0);
        rectangle.add(i, j);
        rectangle.grow(sprite.penSize, sprite.penSize);
        inval(rectangle);
        sprite.lastPenX = i;
        sprite.lastPenY = j;
    }

    void stampCostume(Sprite sprite)
    {
        if(penTrails == null)
            createPenTrails();
        Graphics2D graphics2d = penTrails.createGraphics();
        if(sprite.filterChanged)
            sprite.applyFilters();
        graphics2d.drawImage(sprite.outImage(), sprite.screenX(), sprite.screenY(), null);
        graphics2d.dispose();
        sprite.inval();
    }

    void createPenTrails()
    {
        penTrails = new BufferedImage(482, 387, 2);
        penTrails.getRaster();
    }

    void clearPenTrails()
    {
        if(penTrails != null)
            penTrails.flush();
        penTrails = null;
        inval(new Rectangle(0, 0, 482, 387));
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        requestFocus();
        mouseDragOrMove(mouseevent);
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        updateMouseXY(mouseevent);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        mouseDown(mouseevent);
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        mouseUp(mouseevent);
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
        updateMouseXY(mouseevent);
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
        mouseDragOrMove(mouseevent);
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
        mouseDragOrMove(mouseevent);
    }

    void mouseDown(MouseEvent mouseevent)
    {
        updateMouseXY(mouseevent);
        requestFocus();
        if(inGoButton(mouseevent))
        {
            doStopButton();
            LogoCommandRunner.startLogoThread("greenflag", lc);
            return;
        }
        if(inStopButton(mouseevent))
        {
            doStopButton();
            LogoCommandRunner.startLogoThread("interact", lc);
            return;
        }
        mouseIsDown = true;
        mouseDownX = mouseevent.getX();
        mouseDownY = mouseevent.getY();
        mouseDragTarget = findDragTarget(mouseevent.getX(), mouseevent.getY());
        mouseDragXOffset = mouseDragYOffset = 0;
        reportClickOnMouseUp = true;
        if(mouseDragTarget instanceof Sprite)
        {
            Sprite sprite = (Sprite)mouseDragTarget;
            if(sprite.isDraggable)
            {
                mouseDragXOffset = sprite.screenX() - mouseevent.getX();
                mouseDragYOffset = sprite.screenY() - mouseevent.getY();
                moveSpriteToFront(sprite);
            } else
            {
                mouseDragTarget = null;
            }
        }
        if(mouseDragTarget instanceof ListWatcher)
        {
            ListWatcher listwatcher = (ListWatcher)mouseDragTarget;
            listwatcher.mouseDown(mouseevent.getX(), mouseevent.getY());
        }
        if(askPrompt != null)
        {
            boolean flag = askPrompt.mouseDown(mouseevent.getX(), mouseevent.getY(), this);
            if(flag)
                return;
        }
        if(mouseDragTarget == null)
            reportClick();
    }

    void mouseDragOrMove(MouseEvent mouseevent)
    {
        updateMouseXY(mouseevent);
        if(mouseevent.getX() != mouseDownX || mouseevent.getY() != mouseDownY)
            reportClickOnMouseUp = false;
        if(mouseDragTarget != null)
        {
            mouseDragTarget.dragTo(mouseevent.getX() + mouseDragXOffset, mouseevent.getY() + mouseDragYOffset);
        } else
        {
            boolean flag = overGoButton;
            boolean flag1 = overStopButton;
            overGoButton = inGoButton(mouseevent);
            overStopButton = inStopButton(mouseevent);
            if(flag != overGoButton || flag1 != overStopButton)
            {
                inval(new Rectangle(0, 0, 482, 26));
                redraw_invalid();
            }
        }
    }

    void mouseUp(MouseEvent mouseevent)
    {
        updateMouseXY(mouseevent);
        if(reportClickOnMouseUp)
            if(mouseDragTarget instanceof Watcher)
                ((Watcher)mouseDragTarget).click(mouseevent.getX(), mouseevent.getY());
            else
                reportClick();
        mouseDragTarget = null;
        mouseIsDown = false;
    }

    void reportClick()
    {
        Object aobj[] = new Object[2];
        aobj[0] = new Double(mouseDownX - 241);
        aobj[1] = new Double(206 - mouseDownY);
        mouseclicks.addElement(((Object) (aobj)));
        reportClickOnMouseUp = false;
    }

    void updateMouseXY(MouseEvent mouseevent)
    {
        mouseX = mouseevent.getX() - 241;
        mouseY = 206 - mouseevent.getY();
    }

    boolean inGoButton(MouseEvent mouseevent)
    {
        if(mouseevent.getY() >= 26)
        {
            return false;
        } else
        {
            int i = mouseevent.getX();
            return i >= 418 && i <= 418 + Skin.goButton.getWidth(null);
        }
    }

    boolean inStopButton(MouseEvent mouseevent)
    {
        if(mouseevent.getY() >= 26)
        {
            return false;
        } else
        {
            int i = mouseevent.getX();
            return i >= 451 && i <= 451 + Skin.stopButton.getWidth(null);
        }
    }

    void doStopButton()
    {
        SoundPlayer.stopSoundsForApplet(lc);
        LogoCommandRunner.stopLogoThread(lc);
        (new LogoCommandRunner("stopAll", lc, true)).run();
        clearkeys();
        mouseclicks = new Vector();
        mouseIsDown = false;
        mouseDragTarget = null;
        redraw_all();
    }

    Drawable findDragTarget(int i, int j)
    {
        for(int k = 0; k < sprites.length; k++)
        {
            if(sprites[k] instanceof Watcher)
            {
                Watcher watcher = (Watcher)sprites[k];
                if(watcher.inSlider(i, j))
                    return watcher;
            }
            if(sprites[k] instanceof ListWatcher)
            {
                ListWatcher listwatcher = (ListWatcher)sprites[k];
                if(listwatcher.inScrollbar(i, j))
                    return listwatcher;
            }
            if(!(sprites[k] instanceof Sprite))
                continue;
            Sprite sprite = (Sprite)sprites[k];
            if(sprite.isShowing() && sprite.containsPoint(i, j))
                return sprite;
        }

        return null;
    }

    void moveSpriteToFront(Sprite sprite)
    {
        int i = -1;
        for(int j = 0; j < sprites.length; j++)
            if(sprites[j] == sprite)
                i = j;

        if(i < 0)
            return;
        Object obj = sprites[i];
        for(int k = i; k > 0; k--)
            sprites[k] = sprites[k - 1];

        sprites[0] = obj;
        sprite.inval();
    }

    public void keyPressed(KeyEvent keyevent)
    {
        int i = keyCodeFor(keyevent);
        keydown[i] = true;
        if(i == 10 || i >= 28 && i <= 31)
            keystrokes.addElement(new Double(i));
    }

    public void keyReleased(KeyEvent keyevent)
    {
        int i = keyCodeFor(keyevent);
        keydown[i] = false;
    }

    public void keyTyped(KeyEvent keyevent)
    {
        int i = keyevent.getKeyChar();
        if(askPrompt != null)
        {
            askPrompt.handleKeystroke(i, this);
            return;
        }
        if(i >= 65 && i <= 90)
            i += 32;
        keystrokes.addElement(new Double(i));
    }

    int keyCodeFor(KeyEvent keyevent)
    {
        int i = keyevent.getKeyCode();
        if(i == 10)
            return 10;
        if(i == 37)
            return 28;
        if(i == 38)
            return 30;
        if(i == 39)
            return 29;
        if(i == 40)
            return 31;
        if(i >= 65 && i <= 90)
            return i + 32;
        else
            return Math.min(i, 255);
    }

    void clearkeys()
    {
        for(int i = 0; i < keydown.length; i++)
            keydown[i] = false;

        keystrokes = new Vector();
    }

    int soundLevel()
    {
        if(soundInputLine == null)
            return 0;
        int i = soundInputLine.available();
        if(i == 0)
            return soundLevel;
        i = soundInputLine.read(soundInputBuf, 0, i);
        int j = 0;
        for(int k = 0; k < i / 2; k++)
        {
            int l = (soundInputBuf[2 * k] << 8) + soundInputBuf[2 * k + 1];
            if(l >= 32768)
                l = 0x10000 - l;
            if(l > j)
                j = l;
        }

        soundLevel = j / 327;
        return soundLevel;
    }

    void openSoundInput()
    {
        if(soundInputLine != null)
            soundInputLine.close();
        AudioFormat audioformat = new AudioFormat(44100F, 16, 1, true, true);
        javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(javax.sound.sampled.TargetDataLine.class, audioformat);
        try
        {
            soundInputLine = (TargetDataLine)AudioSystem.getLine(info);
            soundInputLine.open(audioformat, 50000);
            soundInputLine.start();
        }
        catch(LineUnavailableException lineunavailableexception)
        {
            soundInputLine = null;
        }
    }

    void showAskPrompt(String s)
    {
        askPrompt = new AskPrompter(s);
        invalAll();
    }

    void hideAskPrompt()
    {
        if(askPrompt != null)
            lastAnswer = askPrompt.answerString;
        askPrompt = null;
        invalAll();
    }

    boolean askPromptShowing()
    {
        return askPrompt != null;
    }

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
    byte soundInputBuf[];
    int soundLevel;
    boolean overGoButton;
    boolean overStopButton;
    String message;
    boolean isLoading;
    double loadingFraction;
    LContext lc;
    Sprite stage;
    Object sprites[];
    BufferedImage offscreen;
    BufferedImage penTrails;
    Rectangle invalrect;
    int mouseX;
    int mouseY;
    boolean mouseIsDown;
    int mouseDownX;
    int mouseDownY;
    Drawable mouseDragTarget;
    int mouseDragXOffset;
    int mouseDragYOffset;
    boolean reportClickOnMouseUp;
    Vector mouseclicks;
    boolean keydown[];
    Vector keystrokes;
    AskPrompter askPrompt;
    String lastAnswer;

}
