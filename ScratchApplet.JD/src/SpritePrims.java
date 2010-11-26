import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Hashtable;

class SpritePrims extends Primitives
{
  static final String[] primlist = { "who", "0", "talkto", "1", "xpos", "0", "ypos", "0", "setx", "1", "sety", "1", "%left", "0", "%top", "0", "%right", "0", "%bottom", "0", "%setleft", "1", "%settop", "1", "%w", "0", "%h", "0", "costume", "0", "setcostume", "3", "%scale", "0", "setscale", "1", "heading", "0", "setheading", "1", "rotationstyle", "0", "setrotationstyle", "1", "show", "0", "hide", "0", "changed", "0", "containsPoint?", "2", "alpha", "0", "setalpha", "1", "color", "0", "setcolor", "1", "brightness", "0", "setbrightness", "1", "fisheye", "0", "setfisheye", "1", "whirl", "0", "setwhirl", "1", "mosaic", "0", "setmosaic", "1", "pixelate", "0", "setpixelate", "1", "beep", "0", "startSound", "1", "isSoundPlaying?", "1", "stopSound", "1", "stopAllSounds", "0", "setPenDown", "1", "setPenColor", "1", "penSize", "0", "setPenSize", "1", "penHue", "0", "setPenHue", "1", "penShade", "0", "setPenShade", "1", "clearPenTrails", "0", "stampCostume", "0", "newcolor", "3", "touchingSprite?", "1", "touchingColor?", "1", "colorTouchingColor?", "2", "isShowing", "1", "talkbubble", "1", "thinkbubble", "1", "updateBubble", "0", "watcher?", "1", "setWatcherXY", "3", "setWatcherColorAndLabel", "3", "setWatcherSliderMinMax", "3", "setWatcherMode", "2", "setWatcherText", "2", "isDraggable", "0", "setDraggable", "1", "showWatcher", "1", "hideWatcher", "1", "listContents", "1", "hasKey", "2", "listWatcher?", "1", "newListWatcher", "0", "setListWatcherXY", "3", "setListWatcherWidthHeight", "3", "setListWatcherLabel", "2", "setListWatcherList", "2", "highlightListWatcherIndex", "2", "clearListWatcherHighlights", "1", "askbubble", "1", "showAskPrompt", "1", "hideAskPrompt", "0", "askPromptShowing?", "0", "lastAnswer", "0", "isVisible", "1", "newVarWatcher", "1", "watcherX", "1", "watcherY", "1" };

  public String[] primlist()
  {
    return primlist;
  }
  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    Sprite localSprite = paramLContext.who;

    switch (paramInt) { case 0:
      return localSprite == null ? new Object[0] : localSprite;
    case 1:
      paramLContext.who = ((paramArrayOfObject[0] instanceof Sprite) ? (Sprite)paramArrayOfObject[0] : null); return null;
    case 2:
      return new Double(localSprite == null ? 0.0D : localSprite.x);
    case 3:
      return new Double(localSprite == null ? 0.0D : localSprite.y);
    case 4:
      localSprite.x = Logo.aDouble(paramArrayOfObject[0], paramLContext); return null;
    case 5:
      localSprite.y = Logo.aDouble(paramArrayOfObject[0], paramLContext); return null;
    case 6:
      return new Double(localSprite.screenX());
    case 7:
      return new Double(localSprite.screenY());
    case 8:
      return new Double(localSprite.screenX() + localSprite.outImage().getWidth(null));
    case 9:
      return new Double(localSprite.screenY() + localSprite.outImage().getHeight(null));
    case 10:
      localSprite.setscreenX(Logo.aDouble(paramArrayOfObject[0], paramLContext)); return null;
    case 11:
      localSprite.setscreenY(Logo.aDouble(paramArrayOfObject[0], paramLContext)); return null;
    case 12:
      return new Double(localSprite.outImage().getWidth(null));
    case 13:
      return new Double(localSprite.outImage().getHeight(null));
    case 14:
      return localSprite.costume;
    case 15:
      localSprite.setcostume(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); localSprite.costumeChanged(); return null;
    case 16:
      return new Double(localSprite.scale);
    case 17:
      localSprite.setscale(paramArrayOfObject[0], paramLContext); return null;
    case 18:
      return prim_heading(paramLContext);
    case 19:
      localSprite.rotationDegrees = (Logo.aDouble(paramArrayOfObject[0], paramLContext) % 360.0D); localSprite.costumeChanged(); return null;
    case 20:
      return new Double(localSprite.rotationstyle);
    case 21:
      localSprite.rotationstyle = Logo.anInt(paramArrayOfObject[0], paramLContext); localSprite.costumeChanged(); return null;
    case 22:
      localSprite.show(); return null;
    case 23:
      localSprite.hide(); return null;
    case 24:
      localSprite.inval(); return null;
    case 25:
      return prim_containsPoint(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 26:
      return new Double(localSprite.alpha);
    case 27:
      localSprite.setalpha(paramArrayOfObject[0], paramLContext); return null;
    case 28:
      return new Double(localSprite.color);
    case 29:
      localSprite.color = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 30:
      return new Double(localSprite.brightness);
    case 31:
      localSprite.brightness = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 32:
      return new Double(localSprite.fisheye);
    case 33:
      localSprite.fisheye = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 34:
      return new Double(localSprite.whirl);
    case 35:
      localSprite.whirl = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 36:
      return new Double(localSprite.mosaic);
    case 37:
      localSprite.mosaic = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 38:
      return new Double(localSprite.pixelate);
    case 39:
      localSprite.pixelate = Logo.aDouble(paramArrayOfObject[0], paramLContext); localSprite.filterChanged = true; return null;
    case 40:
      Toolkit.getDefaultToolkit().beep(); return null;
    case 41:
      return SoundPlayer.startSound(paramArrayOfObject[0], localSprite, paramLContext);
    case 42:
      return new Boolean(SoundPlayer.isSoundPlaying(paramArrayOfObject[0]));
    case 43:
      SoundPlayer.stopSound(paramArrayOfObject[0]); return null;
    case 44:
      SoundPlayer.stopSoundsForApplet(paramLContext); return null;
    case 45:
      localSprite.setPenDown(Logo.aBoolean(paramArrayOfObject[0], paramLContext)); return null;
    case 46:
      if ((paramArrayOfObject[0] instanceof Color)) localSprite.setPenColor((Color)paramArrayOfObject[0]); return null;
    case 47:
      return new Double(localSprite.penSize);
    case 48:
      localSprite.penSize = Logo.anInt(paramArrayOfObject[0], paramLContext); return null;
    case 49:
      return new Double(localSprite.penHue);
    case 50:
      localSprite.setPenHue(Logo.aDouble(paramArrayOfObject[0], paramLContext)); return null;
    case 51:
      return new Double(localSprite.penShade);
    case 52:
      localSprite.setPenShade(Logo.aDouble(paramArrayOfObject[0], paramLContext)); return null;
    case 53:
      paramLContext.canvas.clearPenTrails(); return null;
    case 54:
      paramLContext.canvas.stampCostume(localSprite); return null;
    case 55:
      return prim_newcolor(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext);
    case 56:
      return new Boolean(localSprite.touchingSprite(paramArrayOfObject[0], paramLContext));
    case 57:
      return new Boolean(localSprite.touchingColor(paramArrayOfObject[0], paramLContext));
    case 58:
      return new Boolean(localSprite.colorTouchingColor(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext));
    case 59:
      return new Boolean(((paramArrayOfObject[0] instanceof Sprite)) && (((Sprite)paramArrayOfObject[0]).isShowing()));
    case 60:
      localSprite.talkbubble(paramArrayOfObject[0], false, true, paramLContext); return null;
    case 61:
      localSprite.talkbubble(paramArrayOfObject[0], false, false, paramLContext); return null;
    case 62:
      localSprite.updateBubble(); return null;
    case 63:
      return new Boolean(paramArrayOfObject[0] instanceof Watcher);
    case 64:
      prim_setWatcherXY(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); return null;
    case 65:
      prim_setWatcherColorAndLabel(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); return null;
    case 66:
      prim_setWatcherSliderMinMax(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); return null;
    case 67:
      prim_setWatcherMode(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext); return null;
    case 68:
      prim_setWatcherText(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext); return null;
    case 69:
      return new Boolean(localSprite.isDraggable);
    case 70:
      localSprite.isDraggable = Logo.aBoolean(paramArrayOfObject[0], paramLContext); return null;
    case 71:
      ((Watcher)paramArrayOfObject[0]).show(); return null;
    case 72:
      ((Watcher)paramArrayOfObject[0]).hide(); return null;
    case 73:
      return prim_listContents(paramArrayOfObject[0], paramLContext);
    case 74:
      return prim_hasKey(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 75:
      return new Boolean(paramArrayOfObject[0] instanceof ListWatcher);
    case 76:
      return prim_newListWatcher(paramLContext);
    case 77:
      prim_setListWatcherXY(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); return null;
    case 78:
      prim_setListWatcherWidthHeight(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext); return null;
    case 79:
      prim_setListWatcherLabel(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext); return null;
    case 80:
      prim_setListWatcherList(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext); return null;
    case 81:
      prim_highlightListWatcherIndex(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext); return null;
    case 82:
      prim_clearListWatcherHighlights(paramArrayOfObject[0], paramLContext); return null;
    case 83:
      localSprite.talkbubble(paramArrayOfObject[0], true, true, paramLContext); return null;
    case 84:
      paramLContext.canvas.showAskPrompt(Logo.aString(paramArrayOfObject[0], paramLContext)); return null;
    case 85:
      paramLContext.canvas.hideAskPrompt(); return null;
    case 86:
      return new Boolean(paramLContext.canvas.askPromptShowing());
    case 87:
      return paramLContext.canvas.lastAnswer;
    case 88:
      return new Boolean(((paramArrayOfObject[0] instanceof Sprite)) && (((Sprite)paramArrayOfObject[0]).isVisible()));
    case 89:
      return prim_newVarWatcher(paramArrayOfObject[0], paramLContext);
    case 90:
      return prim_watcherX(paramArrayOfObject[0], paramLContext);
    case 91:
      return prim_watcherY(paramArrayOfObject[0], paramLContext);
    }
    return null;
  }

  Object prim_heading(LContext paramLContext) {
    double d = paramLContext.who.rotationDegrees % 360.0D;
    if (d > 180.0D) d -= 360.0D;
    return new Double(d);
  }

  Object prim_containsPoint(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (paramLContext.who == null) return new Boolean(false);
    int i = Logo.anInt(paramObject1, paramLContext) + 241;
    int j = 206 - Logo.anInt(paramObject2, paramLContext);
    return new Boolean(paramLContext.who.containsPoint(i, j));
  }

  Color prim_newcolor(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    int i = Logo.anInt(paramObject1, paramLContext);
    int j = Logo.anInt(paramObject2, paramLContext);
    int k = Logo.anInt(paramObject3, paramLContext);
    return new Color(i, j, k);
  }

  void prim_setWatcherXY(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof Watcher)) return;
    Watcher localWatcher = (Watcher)paramObject1;
    localWatcher.inval();
    localWatcher.box.x = Logo.anInt(paramObject2, paramLContext);
    localWatcher.box.y = Logo.anInt(paramObject3, paramLContext);
    localWatcher.inval();
  }

  void prim_setWatcherColorAndLabel(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof Watcher)) return;
    Watcher localWatcher = (Watcher)paramObject1;
    localWatcher.inval();
    localWatcher.readout.color = ((Color)paramObject2);
    localWatcher.label = ((String)paramObject3);
    localWatcher.inval();
  }

  void prim_setWatcherSliderMinMax(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof Watcher)) return;
    Watcher localWatcher = (Watcher)paramObject1;
    localWatcher.sliderMin = Logo.aDouble(paramObject2, paramLContext);
    localWatcher.sliderMax = Logo.aDouble(paramObject3, paramLContext);
    localWatcher.isDiscrete = ((Math.round(localWatcher.sliderMin) == localWatcher.sliderMin) && (Math.round(localWatcher.sliderMax) == localWatcher.sliderMax));
  }

  void prim_setWatcherMode(Object paramObject1, Object paramObject2, LContext paramLContext)
  {
    if (!(paramObject1 instanceof Watcher)) return;
    Watcher localWatcher = (Watcher)paramObject1;
    int i = Logo.anInt(paramObject2, paramLContext);
    localWatcher.inval();
    localWatcher.mode = Math.max(0, Math.min(i, 3));
    localWatcher.inval();
  }

  void prim_setWatcherText(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (!(paramObject1 instanceof Watcher)) return;
    Watcher localWatcher = (Watcher)paramObject1;
    String str = Logo.prs(paramObject2);
    if ((paramObject2 instanceof Double)) {
      double d1 = ((Double)paramObject2).doubleValue();
      double d2 = Math.abs(d1);
      DecimalFormat localDecimalFormat = new DecimalFormat("0.#");
      if (d2 < 1.0D) localDecimalFormat = new DecimalFormat("0.######");
      if ((d2 < 1.E-05D) || (d2 >= 1000000.0D)) localDecimalFormat = new DecimalFormat("0.###E0");
      if (d2 == 0.0D) localDecimalFormat = new DecimalFormat("0.#");
      str = localDecimalFormat.format(d1);
    }
    if (str.equals(localWatcher.readout.contents)) return;
    localWatcher.inval();
    localWatcher.readout.contents = str;
    localWatcher.inval();
  }

  Object prim_listContents(Object paramObject, LContext paramLContext) {
    if (!(paramObject instanceof Object[])) {
      Logo.error("argument must be a list", paramLContext);
      return "";
    }
    Object[] arrayOfObject = (Object[])paramObject;
    if (arrayOfObject.length == 0) return "";
    StringBuffer localStringBuffer = new StringBuffer(1000);

    int i = 0;
    Object localObject;
    for (int j = 0; j < arrayOfObject.length; j++) {
      localObject = arrayOfObject[j];
      if (!(localObject instanceof String)) localObject = Logo.prs(localObject);
      if (((String)localObject).length() <= 1) continue; i = 1;
    }

    for (j = 0; j < arrayOfObject.length; j++) {
      localObject = arrayOfObject[j];
      if (!(localObject instanceof String)) localObject = Logo.prs(localObject);
      localStringBuffer.append(localObject);
      if (i == 0) continue; localStringBuffer.append(" ");
    }
    if (i != 0) localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
    return localStringBuffer.toString();
  }

  Object prim_hasKey(Object paramObject1, Object paramObject2, LContext paramLContext) {
    Hashtable localHashtable = (Hashtable)paramLContext.props.get(paramObject1);
    if (localHashtable == null) return new Boolean(false);
    return new Boolean(localHashtable.containsKey(paramObject2));
  }

  Object prim_newListWatcher(LContext paramLContext) {
    ListWatcher localListWatcher = new ListWatcher(paramLContext);
    Object[] arrayOfObject = new Object[paramLContext.canvas.sprites.length + 1];
    for (int i = 0; i < paramLContext.canvas.sprites.length; i++) {
      arrayOfObject[i] = paramLContext.canvas.sprites[i];
    }
    arrayOfObject[(arrayOfObject.length - 1)] = localListWatcher;
    paramLContext.canvas.sprites = arrayOfObject;
    return localListWatcher;
  }

  void prim_setListWatcherXY(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof ListWatcher)) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject1;
    localListWatcher.inval();
    localListWatcher.box.x = Logo.anInt(paramObject2, paramLContext);
    localListWatcher.box.y = Logo.anInt(paramObject3, paramLContext);
    localListWatcher.inval();
  }

  void prim_setListWatcherWidthHeight(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    if (!(paramObject1 instanceof ListWatcher)) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject1;
    localListWatcher.inval();
    localListWatcher.box.w = Logo.anInt(paramObject2, paramLContext);
    localListWatcher.box.h = Logo.anInt(paramObject3, paramLContext);
    localListWatcher.scrollBarHeight = (localListWatcher.box.h - 23 - 20);
    localListWatcher.pane.w = localListWatcher.box.w;
    localListWatcher.inval();
  }

  void prim_setListWatcherLabel(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (!(paramObject1 instanceof ListWatcher)) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject1;
    localListWatcher.inval();
    localListWatcher.listTitle = Logo.aString(paramObject2, paramLContext);
    localListWatcher.inval();
  }

  void prim_setListWatcherList(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (!(paramObject1 instanceof ListWatcher)) return;
    if (!(paramObject2 instanceof Object[])) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject1;
    localListWatcher.setList((Object[])paramObject2);
    localListWatcher.inval();
  }

  void prim_highlightListWatcherIndex(Object paramObject1, Object paramObject2, LContext paramLContext) {
    if (!(paramObject1 instanceof ListWatcher)) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject1;
    localListWatcher.highlightIndex(Logo.anInt(paramObject2, paramLContext));
    localListWatcher.inval();
  }

  void prim_clearListWatcherHighlights(Object paramObject, LContext paramLContext) {
    if (!(paramObject instanceof ListWatcher)) return;
    ListWatcher localListWatcher = (ListWatcher)paramObject;
    localListWatcher.clearHighlights();
    localListWatcher.inval();
  }

  Watcher prim_newVarWatcher(Object paramObject, LContext paramLContext) {
    return new Watcher(paramLContext);
  }

  Object prim_watcherX(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Watcher)) return new Double(((Watcher)paramObject).box.x);
    if ((paramObject instanceof ListWatcher)) return new Double(((ListWatcher)paramObject).box.x);
    return new Double(0.0D);
  }

  Object prim_watcherY(Object paramObject, LContext paramLContext) {
    if ((paramObject instanceof Watcher)) return new Double(((Watcher)paramObject).box.y);
    if ((paramObject instanceof ListWatcher)) return new Double(((ListWatcher)paramObject).box.y);
    return new Double(0.0D);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     SpritePrims
 * JD-Core Version:    0.6.0
 */