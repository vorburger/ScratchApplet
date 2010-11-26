// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Hashtable;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine.Info;
import javax.swing.JPanel;

class PlayerCanvas extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

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
   byte[] soundInputBuf = new byte['\uc350'];
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
   static Class class$javax$sound$sampled$TargetDataLine;


   PlayerCanvas() {
      this.setLayout((LayoutManager)null);
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }

   public void addNotify() {
      super.addNotify();
      this.offscreen = (BufferedImage)this.createImage(482, 387);
      this.offscreen.getRaster();
      Graphics var1 = this.offscreen.getGraphics();
      var1.setColor(WHITE);
      var1.fillRect(0, 0, 482, 387);
      var1.dispose();
      this.repaint();
   }

   public Dimension getMinimumSize() {
      return new Dimension(482, 387);
   }

   public Dimension getPreferredSize() {
      return new Dimension(482, 387);
   }

   public synchronized void paintComponent(Graphics var1) {
      var1.drawImage(this.offscreen, 0, 0, 482, 387, this);
   }

   void clearall(LContext var1) {
      this.stage = null;
      this.sprites = new Object[0];
      this.askPrompt = null;
      this.lastAnswer = "";
      this.penTrails = null;
      SoundPlayer.stopSoundsForApplet(var1);
      this.soundLevel = 0;
      var1.props = new Hashtable();
      Runtime.getRuntime().gc();
      this.clearkeys();
      this.mouseclicks = new Vector();
      this.mouseIsDown = false;
      this.mouseDragTarget = null;
   }

   void setMessage(String var1) {
      this.message = var1;
      this.redraw_all();
   }

   synchronized void inval(Rectangle var1) {
      if(this.invalrect.isEmpty()) {
         this.invalrect = new Rectangle(var1);
      } else {
         this.invalrect = this.invalrect.union(var1);
      }

   }

   void invalAll() {
      this.inval(new Rectangle(0, 0, 482, 387));
   }

   void redraw_all() {
      this.redraw(new Rectangle(0, 0, 482, 387), false);
   }

   void redraw_invalid() {
      this.redraw(this.invalrect, false);
   }

   synchronized void redraw(Rectangle var1, boolean var2) {
      Graphics var3 = this.offscreen.getGraphics();
      var3.setClip(var1.x, var1.y, var1.width, var1.height);
      var3.setColor(WHITE);
      var3.fillRect(0, 0, 482, 387);
      if(this.isLoading) {
         this.drawProgressBar(var3);
      } else {
         if(this.stage != null) {
            this.stage.setStageOffset();
            this.stage.paint(var3);
         }

         if(this.penTrails != null) {
            var3.drawImage(this.penTrails, 0, 0, 482, 387, (ImageObserver)null);
         }

         int var4;
         Drawable var5;
         for(var4 = this.sprites.length - 1; var4 >= 0; --var4) {
            var5 = (Drawable)this.sprites[var4];
            if(var5.isShowing() && var1.intersects(var5.fullRect())) {
               var5.paint(var3);
            }
         }

         for(var4 = this.sprites.length - 1; var4 >= 0; --var4) {
            var5 = (Drawable)this.sprites[var4];
            if(var5.isShowing() && var1.intersects(var5.fullRect())) {
               var5.paintBubble(var3);
            }
         }

         if(this.askPrompt != null) {
            this.askPrompt.paint(var3);
         }
      }

      this.drawBorder(var3);
      if(var2) {
         var3.setColor(new Color(200, 0, 0));
         var3.fillRect(var1.x, var1.y, var1.width, var1.height);
      }

      var3.dispose();
      this.repaint(var1);
      this.invalrect = new Rectangle();
   }

   void drawBorder(Graphics var1) {
      var1.setColor(new Color(130, 130, 130));
      var1.fillRect(0, 0, 482, 25);
      var1.setColor(BLACK);
      var1.fillRect(0, 0, 482, 1);
      var1.fillRect(0, 0, 1, 387);
      var1.fillRect(0, 386, 482, 1);
      var1.fillRect(481, 0, 1, 387);
      var1.fillRect(0, 25, 482, 1);
      var1.drawImage(this.overGoButton?Skin.goButtonOver:Skin.goButton, 418, 4, (ImageObserver)null);
      var1.drawImage(this.overStopButton?Skin.stopButtonOver:Skin.stopButton, 451, 4, (ImageObserver)null);
      var1.setColor(WHITE);
      var1.setFont(new Font("SansSerif", 0, 8));
      var1.drawString("v38", 5, 11);
      if(this.message.length() > 0) {
         var1.setFont(new Font("SansSerif", 1, 13));
         var1.setColor(new Color(250, 250, 0));
         var1.drawString(this.message, 70, 17);
      }

   }

   void drawProgressBar(Graphics var1) {
      var1.setColor(BLACK);
      var1.setFont(new Font("SansSerif", 1, 24));
      var1.drawString("Loading...", 188, 173);
      byte var2 = 91;
      short var3 = 193;
      var1.fillRect(var2, var3, 300, 1);
      var1.fillRect(var2, var3, 1, 29);
      var1.fillRect(var2, var3 + 28, 300, 1);
      var1.fillRect(var2 + 299, var3, 1, 29);
      var1.setColor(new Color(10, 10, 200));
      var1.fillRect(var2 + 2, var3 + 2, (int)(296.0D * Math.min(this.loadingFraction, 1.0D)), 25);
      this.drawBorder(var1);
   }

   BufferedImage drawAreaWithoutSprite(Rectangle var1, Sprite var2) {
      BufferedImage var3 = new BufferedImage(var1.width, var1.height, 2);
      Graphics var4 = var3.getGraphics();
      var4.setColor(WHITE);
      var4.fillRect(0, 0, var1.width, var1.height);
      var4 = var4.create(-var1.x, -var1.y, var1.width, var1.height);
      var4.setClip(var1.x, var1.y, var1.width, var1.height);
      if(this.stage != null) {
         this.stage.setStageOffset();
         this.stage.paint(var4);
      }

      if(this.penTrails != null) {
         var4.drawImage(this.penTrails, 0, 0, 482, 387, (ImageObserver)null);
      }

      for(int var5 = this.sprites.length - 1; var5 >= 0; --var5) {
         Drawable var6 = (Drawable)this.sprites[var5];
         if(var6 != var2 && var6.isShowing() && var1.intersects(var6.rect())) {
            var6.paint(var4);
         }
      }

      var4.dispose();
      return var3;
   }

   void drawRect(int var1, int var2, int var3, int var4) {
      Graphics var5 = this.offscreen.getGraphics();
      var5.setColor(BLACK);
      var5.fillRect(var1, var2, var3, var4);
      var5.dispose();
      this.repaint();
   }

   void startLoading() {
      this.isLoading = true;
      this.loadingFraction = 0.0D;
      this.redraw_all();
   }

   void stopLoading() {
      this.loadingFraction = 1.0D;
      this.redraw_all();
      this.loadingFraction = 0.0D;
      this.isLoading = false;
   }

   void loadingProgress(double var1) {
      this.loadingFraction = var1;
      this.redraw_all();
   }

   boolean logoIsRunning() {
      return this.lc.logoThread != null;
   }

   void updatePenTrails() {
      for(int var1 = this.sprites.length - 1; var1 >= 0; --var1) {
         if(this.sprites[var1] instanceof Sprite) {
            Sprite var2 = (Sprite)this.sprites[var1];
            if(var2.penDown) {
               this.updatePenTrailForSprite(var2);
            }
         }
      }

   }

   void updatePenTrailForSprite(Sprite var1) {
      if(this.penTrails == null) {
         this.createPenTrails();
      }

      int var2 = 241 + (int)var1.x;
      int var3 = 206 - (int)var1.y;
      if((double)var1.lastPenX == -1000000.0D) {
         var1.lastPenX = var2;
         var1.lastPenY = var3;
      } else if(var1.lastPenX == var2 && var1.lastPenY == var3) {
         return;
      }

      Graphics2D var4 = this.penTrails.createGraphics();
      var4.setColor(var1.penColor);
      var4.setStroke(new BasicStroke((float)var1.penSize, 1, 1));
      var4.drawLine(var1.lastPenX, var1.lastPenY, var2, var3);
      var4.dispose();
      Rectangle var5 = new Rectangle(var1.lastPenX, var1.lastPenY, 0, 0);
      var5.add(var2, var3);
      var5.grow(var1.penSize, var1.penSize);
      this.inval(var5);
      var1.lastPenX = var2;
      var1.lastPenY = var3;
   }

   void stampCostume(Sprite var1) {
      if(this.penTrails == null) {
         this.createPenTrails();
      }

      Graphics2D var2 = this.penTrails.createGraphics();
      if(var1.filterChanged) {
         var1.applyFilters();
      }

      var2.drawImage(var1.outImage(), var1.screenX(), var1.screenY(), (ImageObserver)null);
      var2.dispose();
      var1.inval();
   }

   void createPenTrails() {
      this.penTrails = new BufferedImage(482, 387, 2);
      this.penTrails.getRaster();
   }

   void clearPenTrails() {
      if(this.penTrails != null) {
         this.penTrails.flush();
      }

      this.penTrails = null;
      this.inval(new Rectangle(0, 0, 482, 387));
   }

   public void mouseEntered(MouseEvent var1) {
      this.requestFocus();
      this.mouseDragOrMove(var1);
   }

   public void mouseExited(MouseEvent var1) {
      this.updateMouseXY(var1);
   }

   public void mousePressed(MouseEvent var1) {
      this.mouseDown(var1);
   }

   public void mouseReleased(MouseEvent var1) {
      this.mouseUp(var1);
   }

   public void mouseClicked(MouseEvent var1) {
      this.updateMouseXY(var1);
   }

   public void mouseDragged(MouseEvent var1) {
      this.mouseDragOrMove(var1);
   }

   public void mouseMoved(MouseEvent var1) {
      this.mouseDragOrMove(var1);
   }

   void mouseDown(MouseEvent var1) {
      this.updateMouseXY(var1);
      this.requestFocus();
      if(this.inGoButton(var1)) {
         this.doStopButton();
         LogoCommandRunner.startLogoThread("greenflag", this.lc);
      } else if(this.inStopButton(var1)) {
         this.doStopButton();
         LogoCommandRunner.startLogoThread("interact", this.lc);
      } else {
         this.mouseIsDown = true;
         this.mouseDownX = var1.getX();
         this.mouseDownY = var1.getY();
         this.mouseDragTarget = this.findDragTarget(var1.getX(), var1.getY());
         this.mouseDragXOffset = this.mouseDragYOffset = 0;
         this.reportClickOnMouseUp = true;
         if(this.mouseDragTarget instanceof Sprite) {
            Sprite var2 = (Sprite)this.mouseDragTarget;
            if(var2.isDraggable) {
               this.mouseDragXOffset = var2.screenX() - var1.getX();
               this.mouseDragYOffset = var2.screenY() - var1.getY();
               this.moveSpriteToFront(var2);
            } else {
               this.mouseDragTarget = null;
            }
         }

         if(this.mouseDragTarget instanceof ListWatcher) {
            ListWatcher var4 = (ListWatcher)this.mouseDragTarget;
            var4.mouseDown(var1.getX(), var1.getY());
         }

         if(this.askPrompt != null) {
            boolean var3 = this.askPrompt.mouseDown(var1.getX(), var1.getY(), this);
            if(var3) {
               return;
            }
         }

         if(this.mouseDragTarget == null) {
            this.reportClick();
         }

      }
   }

   void mouseDragOrMove(MouseEvent var1) {
      this.updateMouseXY(var1);
      if(var1.getX() != this.mouseDownX || var1.getY() != this.mouseDownY) {
         this.reportClickOnMouseUp = false;
      }

      if(this.mouseDragTarget != null) {
         this.mouseDragTarget.dragTo(var1.getX() + this.mouseDragXOffset, var1.getY() + this.mouseDragYOffset);
      } else {
         boolean var2 = this.overGoButton;
         boolean var3 = this.overStopButton;
         this.overGoButton = this.inGoButton(var1);
         this.overStopButton = this.inStopButton(var1);
         if(var2 != this.overGoButton || var3 != this.overStopButton) {
            this.inval(new Rectangle(0, 0, 482, 26));
            this.redraw_invalid();
         }
      }

   }

   void mouseUp(MouseEvent var1) {
      this.updateMouseXY(var1);
      if(this.reportClickOnMouseUp) {
         if(this.mouseDragTarget instanceof Watcher) {
            ((Watcher)this.mouseDragTarget).click(var1.getX(), var1.getY());
         } else {
            this.reportClick();
         }
      }

      this.mouseDragTarget = null;
      this.mouseIsDown = false;
   }

   void reportClick() {
      Object[] var1 = new Object[]{new Double((double)(this.mouseDownX - 241)), new Double((double)(206 - this.mouseDownY))};
      this.mouseclicks.addElement(var1);
      this.reportClickOnMouseUp = false;
   }

   void updateMouseXY(MouseEvent var1) {
      this.mouseX = var1.getX() - 241;
      this.mouseY = 206 - var1.getY();
   }

   boolean inGoButton(MouseEvent var1) {
      if(var1.getY() >= 26) {
         return false;
      } else {
         int var2 = var1.getX();
         return var2 >= 418 && var2 <= 418 + Skin.goButton.getWidth((ImageObserver)null);
      }
   }

   boolean inStopButton(MouseEvent var1) {
      if(var1.getY() >= 26) {
         return false;
      } else {
         int var2 = var1.getX();
         return var2 >= 451 && var2 <= 451 + Skin.stopButton.getWidth((ImageObserver)null);
      }
   }

   void doStopButton() {
      SoundPlayer.stopSoundsForApplet(this.lc);
      LogoCommandRunner.stopLogoThread(this.lc);
      (new LogoCommandRunner("stopAll", this.lc, true)).run();
      this.clearkeys();
      this.mouseclicks = new Vector();
      this.mouseIsDown = false;
      this.mouseDragTarget = null;
      this.redraw_all();
   }

   Drawable findDragTarget(int var1, int var2) {
      for(int var3 = 0; var3 < this.sprites.length; ++var3) {
         if(this.sprites[var3] instanceof Watcher) {
            Watcher var4 = (Watcher)this.sprites[var3];
            if(var4.inSlider(var1, var2)) {
               return var4;
            }
         }

         if(this.sprites[var3] instanceof ListWatcher) {
            ListWatcher var5 = (ListWatcher)this.sprites[var3];
            if(var5.inScrollbar(var1, var2)) {
               return var5;
            }
         }

         if(this.sprites[var3] instanceof Sprite) {
            Sprite var6 = (Sprite)this.sprites[var3];
            if(var6.isShowing() && var6.containsPoint(var1, var2)) {
               return var6;
            }
         }
      }

      return null;
   }

   void moveSpriteToFront(Sprite var1) {
      int var2 = -1;

      for(int var3 = 0; var3 < this.sprites.length; ++var3) {
         if(this.sprites[var3] == var1) {
            var2 = var3;
         }
      }

      if(var2 >= 0) {
         Object var5 = this.sprites[var2];

         for(int var4 = var2; var4 > 0; --var4) {
            this.sprites[var4] = this.sprites[var4 - 1];
         }

         this.sprites[0] = var5;
         var1.inval();
      }
   }

   public void keyPressed(KeyEvent var1) {
      int var2 = this.keyCodeFor(var1);
      this.keydown[var2] = true;
      if(var2 == 10 || var2 >= 28 && var2 <= 31) {
         this.keystrokes.addElement(new Double((double)var2));
      }

   }

   public void keyReleased(KeyEvent var1) {
      int var2 = this.keyCodeFor(var1);
      this.keydown[var2] = false;
   }

   public void keyTyped(KeyEvent var1) {
      int var2 = var1.getKeyChar();
      if(this.askPrompt != null) {
         this.askPrompt.handleKeystroke(var2, this);
      } else {
         if(var2 >= 65 && var2 <= 90) {
            var2 += 32;
         }

         this.keystrokes.addElement(new Double((double)var2));
      }
   }

   int keyCodeFor(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      return var2 == 10?10:(var2 == 37?28:(var2 == 38?30:(var2 == 39?29:(var2 == 40?31:(var2 >= 65 && var2 <= 90?var2 + 32:Math.min(var2, 255))))));
   }

   void clearkeys() {
      for(int var1 = 0; var1 < this.keydown.length; ++var1) {
         this.keydown[var1] = false;
      }

      this.keystrokes = new Vector();
   }

   int soundLevel() {
      if(soundInputLine == null) {
         return 0;
      } else {
         int var1 = soundInputLine.available();
         if(var1 == 0) {
            return this.soundLevel;
         } else {
            var1 = soundInputLine.read(this.soundInputBuf, 0, var1);
            int var2 = 0;

            for(int var3 = 0; var3 < var1 / 2; ++var3) {
               int var4 = (this.soundInputBuf[2 * var3] << 8) + this.soundInputBuf[2 * var3 + 1];
               if(var4 >= '\u8000') {
                  var4 = 65536 - var4;
               }

               if(var4 > var2) {
                  var2 = var4;
               }
            }

            this.soundLevel = var2 / 327;
            return this.soundLevel;
         }
      }
   }

   void openSoundInput() {
      if(soundInputLine != null) {
         soundInputLine.close();
      }

      AudioFormat var1 = new AudioFormat(44100.0F, 16, 1, true, true);
      Info var2 = new Info(class$javax$sound$sampled$TargetDataLine == null?(class$javax$sound$sampled$TargetDataLine = class$("javax.sound.sampled.TargetDataLine")):class$javax$sound$sampled$TargetDataLine, var1);

      try {
         soundInputLine = (TargetDataLine)AudioSystem.getLine(var2);
         soundInputLine.open(var1, '\uc350');
         soundInputLine.start();
      } catch (LineUnavailableException var4) {
         soundInputLine = null;
      }

   }

   void showAskPrompt(String var1) {
      this.askPrompt = new AskPrompter(var1);
      this.invalAll();
   }

   void hideAskPrompt() {
      if(this.askPrompt != null) {
         this.lastAnswer = this.askPrompt.answerString;
      }

      this.askPrompt = null;
      this.invalAll();
   }

   boolean askPromptShowing() {
      return this.askPrompt != null;
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

}
