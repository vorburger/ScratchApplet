/* SpritePrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.Hashtable;

class SpritePrims extends Primitives
{
    static final String[] primlist
	= { "who", "0", "talkto", "1", "xpos", "0", "ypos", "0", "setx", "1",
	    "sety", "1", "%left", "0", "%top", "0", "%right", "0", "%bottom",
	    "0", "%setleft", "1", "%settop", "1", "%w", "0", "%h", "0",
	    "costume", "0", "setcostume", "3", "%scale", "0", "setscale", "1",
	    "heading", "0", "setheading", "1", "rotationstyle", "0",
	    "setrotationstyle", "1", "show", "0", "hide", "0", "changed", "0",
	    "containsPoint?", "2", "alpha", "0", "setalpha", "1", "color", "0",
	    "setcolor", "1", "brightness", "0", "setbrightness", "1",
	    "fisheye", "0", "setfisheye", "1", "whirl", "0", "setwhirl", "1",
	    "mosaic", "0", "setmosaic", "1", "pixelate", "0", "setpixelate",
	    "1", "beep", "0", "startSound", "1", "isSoundPlaying?", "1",
	    "stopSound", "1", "stopAllSounds", "0", "setPenDown", "1",
	    "setPenColor", "1", "penSize", "0", "setPenSize", "1", "penHue",
	    "0", "setPenHue", "1", "penShade", "0", "setPenShade", "1",
	    "clearPenTrails", "0", "stampCostume", "0", "newcolor", "3",
	    "touchingSprite?", "1", "touchingColor?", "1",
	    "colorTouchingColor?", "2", "isShowing", "1", "talkbubble", "1",
	    "thinkbubble", "1", "updateBubble", "0", "watcher?", "1",
	    "setWatcherXY", "3", "setWatcherColorAndLabel", "3",
	    "setWatcherSliderMinMax", "3", "setWatcherMode", "2",
	    "setWatcherText", "2", "isDraggable", "0", "setDraggable", "1",
	    "showWatcher", "1", "hideWatcher", "1", "listContents", "1",
	    "hasKey", "2", "listWatcher?", "1", "newListWatcher", "0",
	    "setListWatcherXY", "3", "setListWatcherWidthHeight", "3",
	    "setListWatcherLabel", "2", "setListWatcherList", "2",
	    "highlightListWatcherIndex", "2", "clearListWatcherHighlights",
	    "1", "askbubble", "1", "showAskPrompt", "1", "hideAskPrompt", "0",
	    "askPromptShowing?", "0", "lastAnswer", "0", "isVisible", "1",
	    "newVarWatcher", "1", "watcherX", "1", "watcherY", "1" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	Sprite sprite = ((LContext) lcontext).who;
	switch (i) {
	case 0:
	    return sprite == null ? (Object) new Object[0] : sprite;
	case 1:
	    ((LContext) lcontext).who
		= objects[0] instanceof Sprite ? (Sprite) objects[0] : null;
	    return null;
	case 2:
	    return new Double(sprite == null ? 0.0 : ((Sprite) sprite).x);
	case 3:
	    return new Double(sprite == null ? 0.0 : ((Sprite) sprite).y);
	case 4:
	    ((Sprite) sprite).x = Logo.aDouble(objects[0], lcontext);
	    return null;
	case 5:
	    ((Sprite) sprite).y = Logo.aDouble(objects[0], lcontext);
	    return null;
	case 6:
	    return new Double((double) sprite.screenX());
	case 7:
	    return new Double((double) sprite.screenY());
	case 8:
	    return new Double((double) (sprite.screenX()
					+ sprite.outImage().getWidth(null)));
	case 9:
	    return new Double((double) (sprite.screenY()
					+ sprite.outImage().getHeight(null)));
	case 10:
	    sprite.setscreenX(Logo.aDouble(objects[0], lcontext));
	    return null;
	case 11:
	    sprite.setscreenY(Logo.aDouble(objects[0], lcontext));
	    return null;
	case 12:
	    return new Double((double) sprite.outImage().getWidth(null));
	case 13:
	    return new Double((double) sprite.outImage().getHeight(null));
	case 14:
	    return ((Sprite) sprite).costume;
	case 15:
	    sprite.setcostume(objects[0], objects[1], objects[2], lcontext);
	    sprite.costumeChanged();
	    return null;
	case 16:
	    return new Double(((Sprite) sprite).scale);
	case 17:
	    sprite.setscale(objects[0], lcontext);
	    return null;
	case 18:
	    return prim_heading(lcontext);
	case 19:
	    ((Sprite) sprite).rotationDegrees
		= Logo.aDouble(objects[0], lcontext) % 360.0;
	    sprite.costumeChanged();
	    return null;
	case 20:
	    return new Double((double) ((Sprite) sprite).rotationstyle);
	case 21:
	    ((Sprite) sprite).rotationstyle = Logo.anInt(objects[0], lcontext);
	    sprite.costumeChanged();
	    return null;
	case 22:
	    sprite.show();
	    return null;
	case 23:
	    sprite.hide();
	    return null;
	case 24:
	    sprite.inval();
	    return null;
	case 25:
	    return prim_containsPoint(objects[0], objects[1], lcontext);
	case 26:
	    return new Double(((Sprite) sprite).alpha);
	case 27:
	    sprite.setalpha(objects[0], lcontext);
	    return null;
	case 28:
	    return new Double(((Sprite) sprite).color);
	case 29:
	    ((Sprite) sprite).color = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 30:
	    return new Double(((Sprite) sprite).brightness);
	case 31:
	    ((Sprite) sprite).brightness = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 32:
	    return new Double(((Sprite) sprite).fisheye);
	case 33:
	    ((Sprite) sprite).fisheye = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 34:
	    return new Double(((Sprite) sprite).whirl);
	case 35:
	    ((Sprite) sprite).whirl = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 36:
	    return new Double(((Sprite) sprite).mosaic);
	case 37:
	    ((Sprite) sprite).mosaic = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 38:
	    return new Double(((Sprite) sprite).pixelate);
	case 39:
	    ((Sprite) sprite).pixelate = Logo.aDouble(objects[0], lcontext);
	    ((Sprite) sprite).filterChanged = true;
	    return null;
	case 40:
	    Toolkit.getDefaultToolkit().beep();
	    return null;
	case 41:
	    return SoundPlayer.startSound(objects[0], sprite, lcontext);
	case 42:
	    return new Boolean(SoundPlayer.isSoundPlaying(objects[0]));
	case 43:
	    SoundPlayer.stopSound(objects[0]);
	    return null;
	case 44:
	    SoundPlayer.stopSoundsForApplet(lcontext);
	    return null;
	case 45:
	    sprite.setPenDown(Logo.aBoolean(objects[0], lcontext));
	    return null;
	case 46:
	    if (objects[0] instanceof Color)
		sprite.setPenColor((Color) objects[0]);
	    return null;
	case 47:
	    return new Double((double) ((Sprite) sprite).penSize);
	case 48:
	    ((Sprite) sprite).penSize = Logo.anInt(objects[0], lcontext);
	    return null;
	case 49:
	    return new Double(((Sprite) sprite).penHue);
	case 50:
	    sprite.setPenHue(Logo.aDouble(objects[0], lcontext));
	    return null;
	case 51:
	    return new Double(((Sprite) sprite).penShade);
	case 52:
	    sprite.setPenShade(Logo.aDouble(objects[0], lcontext));
	    return null;
	case 53:
	    ((LContext) lcontext).canvas.clearPenTrails();
	    return null;
	case 54:
	    ((LContext) lcontext).canvas.stampCostume(sprite);
	    return null;
	case 55:
	    return prim_newcolor(objects[0], objects[1], objects[2], lcontext);
	case 56:
	    return new Boolean(sprite.touchingSprite(objects[0], lcontext));
	case 57:
	    return new Boolean(sprite.touchingColor(objects[0], lcontext));
	case 58:
	    return new Boolean(sprite.colorTouchingColor(objects[0],
							 objects[1],
							 lcontext));
	case 59:
	    return new Boolean(objects[0] instanceof Sprite
			       && ((Sprite) objects[0]).isShowing());
	case 60:
	    sprite.talkbubble(objects[0], false, true, lcontext);
	    return null;
	case 61:
	    sprite.talkbubble(objects[0], false, false, lcontext);
	    return null;
	case 62:
	    sprite.updateBubble();
	    return null;
	case 63:
	    return new Boolean(objects[0] instanceof Watcher);
	case 64:
	    prim_setWatcherXY(objects[0], objects[1], objects[2], lcontext);
	    return null;
	case 65:
	    prim_setWatcherColorAndLabel(objects[0], objects[1], objects[2],
					 lcontext);
	    return null;
	case 66:
	    prim_setWatcherSliderMinMax(objects[0], objects[1], objects[2],
					lcontext);
	    return null;
	case 67:
	    prim_setWatcherMode(objects[0], objects[1], lcontext);
	    return null;
	case 68:
	    prim_setWatcherText(objects[0], objects[1], lcontext);
	    return null;
	case 69:
	    return new Boolean(((Sprite) sprite).isDraggable);
	case 70:
	    ((Sprite) sprite).isDraggable
		= Logo.aBoolean(objects[0], lcontext);
	    return null;
	case 71:
	    ((Watcher) objects[0]).show();
	    return null;
	case 72:
	    ((Watcher) objects[0]).hide();
	    return null;
	case 73:
	    return prim_listContents(objects[0], lcontext);
	case 74:
	    return prim_hasKey(objects[0], objects[1], lcontext);
	case 75:
	    return new Boolean(objects[0] instanceof ListWatcher);
	case 76:
	    return prim_newListWatcher(lcontext);
	case 77:
	    prim_setListWatcherXY(objects[0], objects[1], objects[2],
				  lcontext);
	    return null;
	case 78:
	    prim_setListWatcherWidthHeight(objects[0], objects[1], objects[2],
					   lcontext);
	    return null;
	case 79:
	    prim_setListWatcherLabel(objects[0], objects[1], lcontext);
	    return null;
	case 80:
	    prim_setListWatcherList(objects[0], objects[1], lcontext);
	    return null;
	case 81:
	    prim_highlightListWatcherIndex(objects[0], objects[1], lcontext);
	    return null;
	case 82:
	    prim_clearListWatcherHighlights(objects[0], lcontext);
	    return null;
	case 83:
	    sprite.talkbubble(objects[0], true, true, lcontext);
	    return null;
	case 84:
	    ((LContext) lcontext).canvas.showAskPrompt(Logo.aString(objects[0],
								    lcontext));
	    return null;
	case 85:
	    ((LContext) lcontext).canvas.hideAskPrompt();
	    return null;
	case 86:
	    return new Boolean(((LContext) lcontext).canvas
				   .askPromptShowing());
	case 87:
	    return ((PlayerCanvas) ((LContext) lcontext).canvas).lastAnswer;
	case 88:
	    return new Boolean(objects[0] instanceof Sprite
			       && ((Sprite) objects[0]).isVisible());
	case 89:
	    return prim_newVarWatcher(objects[0], lcontext);
	case 90:
	    return prim_watcherX(objects[0], lcontext);
	case 91:
	    return prim_watcherY(objects[0], lcontext);
	default:
	    return null;
	}
    }
    
    Object prim_heading(LContext lcontext) {
	double d
	    = ((Sprite) ((LContext) lcontext).who).rotationDegrees % 360.0;
	if (d > 180.0)
	    d -= 360.0;
	return new Double(d);
    }
    
    Object prim_containsPoint(Object object, Object object_0_,
			      LContext lcontext) {
	if (((LContext) lcontext).who == null)
	    return new Boolean(false);
	int i = Logo.anInt(object, lcontext) + 241;
	int i_1_ = 206 - Logo.anInt(object_0_, lcontext);
	return new Boolean(((LContext) lcontext).who.containsPoint(i, i_1_));
    }
    
    Color prim_newcolor(Object object, Object object_2_, Object object_3_,
			LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	int i_4_ = Logo.anInt(object_2_, lcontext);
	int i_5_ = Logo.anInt(object_3_, lcontext);
	return new Color(i, i_4_, i_5_);
    }
    
    void prim_setWatcherXY(Object object, Object object_6_, Object object_7_,
			   LContext lcontext) {
	if (object instanceof Watcher) {
	    Watcher watcher = (Watcher) object;
	    watcher.inval();
	    ((StretchyBox) ((Watcher) watcher).box).x
		= Logo.anInt(object_6_, lcontext);
	    ((StretchyBox) ((Watcher) watcher).box).y
		= Logo.anInt(object_7_, lcontext);
	    watcher.inval();
	}
    }
    
    void prim_setWatcherColorAndLabel(Object object, Object object_8_,
				      Object object_9_, LContext lcontext) {
	if (object instanceof Watcher) {
	    Watcher watcher = (Watcher) object;
	    watcher.inval();
	    ((WatcherReadout) ((Watcher) watcher).readout).color
		= (Color) object_8_;
	    ((Watcher) watcher).label = (String) object_9_;
	    watcher.inval();
	}
    }
    
    void prim_setWatcherSliderMinMax(Object object, Object object_10_,
				     Object object_11_, LContext lcontext) {
	if (object instanceof Watcher) {
	    Watcher watcher = (Watcher) object;
	    ((Watcher) watcher).sliderMin = Logo.aDouble(object_10_, lcontext);
	    ((Watcher) watcher).sliderMax = Logo.aDouble(object_11_, lcontext);
	    ((Watcher) watcher).isDiscrete
		= (((double) Math.round(((Watcher) watcher).sliderMin)
		    == ((Watcher) watcher).sliderMin)
		   && ((double) Math.round(((Watcher) watcher).sliderMax)
		       == ((Watcher) watcher).sliderMax));
	}
    }
    
    void prim_setWatcherMode(Object object, Object object_12_,
			     LContext lcontext) {
	if (object instanceof Watcher) {
	    Watcher watcher = (Watcher) object;
	    int i = Logo.anInt(object_12_, lcontext);
	    watcher.inval();
	    ((Watcher) watcher).mode = Math.max(0, Math.min(i, 3));
	    watcher.inval();
	}
    }
    
    void prim_setWatcherText(Object object, Object object_13_,
			     LContext lcontext) {
	if (object instanceof Watcher) {
	    Watcher watcher = (Watcher) object;
	    String string = Logo.prs(object_13_);
	    if (object_13_ instanceof Double) {
		double d = ((Double) object_13_).doubleValue();
		double d_14_ = Math.abs(d);
		DecimalFormat decimalformat = new DecimalFormat("0.#");
		if (d_14_ < 1.0)
		    decimalformat = new DecimalFormat("0.######");
		if (d_14_ < 1.0E-5 || d_14_ >= 1000000.0)
		    decimalformat = new DecimalFormat("0.###E0");
		if (d_14_ == 0.0)
		    decimalformat = new DecimalFormat("0.#");
		string = decimalformat.format(d);
	    }
	    if (!string.equals(((WatcherReadout) ((Watcher) watcher).readout)
			       .contents)) {
		watcher.inval();
		((WatcherReadout) ((Watcher) watcher).readout).contents
		    = string;
		watcher.inval();
	    }
	}
    }
    
    Object prim_listContents(Object object, LContext lcontext) {
	if (!(object instanceof Object[])) {
	    Logo.error("argument must be a list", lcontext);
	    return "";
	}
	Object[] objects = (Object[]) object;
	if (objects.length == 0)
	    return "";
	StringBuffer stringbuffer = new StringBuffer(1000);
	boolean bool = false;
	for (int i = 0; i < objects.length; i++) {
	    Object object_15_ = objects[i];
	    if (!(object_15_ instanceof String))
		object_15_ = Logo.prs(object_15_);
	    if (((String) object_15_).length() > 1)
		bool = true;
	}
	for (int i = 0; i < objects.length; i++) {
	    Object object_16_ = objects[i];
	    if (!(object_16_ instanceof String))
		object_16_ = Logo.prs(object_16_);
	    stringbuffer.append(object_16_);
	    if (bool)
		stringbuffer.append(" ");
	}
	if (bool)
	    stringbuffer.deleteCharAt(stringbuffer.length() - 1);
	return stringbuffer.toString();
    }
    
    Object prim_hasKey(Object object, Object object_17_, LContext lcontext) {
	Hashtable hashtable
	    = (Hashtable) ((LContext) lcontext).props.get(object);
	if (hashtable == null)
	    return new Boolean(false);
	return new Boolean(hashtable.containsKey(object_17_));
    }
    
    Object prim_newListWatcher(LContext lcontext) {
	ListWatcher listwatcher = new ListWatcher(lcontext);
	Object[] objects
	    = (new Object
	       [(((PlayerCanvas) ((LContext) lcontext).canvas).sprites.length
		 + 1)]);
	for (int i = 0;
	     i < ((PlayerCanvas) ((LContext) lcontext).canvas).sprites.length;
	     i++)
	    objects[i]
		= ((PlayerCanvas) ((LContext) lcontext).canvas).sprites[i];
	objects[objects.length - 1] = listwatcher;
	((PlayerCanvas) ((LContext) lcontext).canvas).sprites = objects;
	return listwatcher;
    }
    
    void prim_setListWatcherXY(Object object, Object object_18_,
			       Object object_19_, LContext lcontext) {
	if (object instanceof ListWatcher) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.inval();
	    ((StretchyBox) ((ListWatcher) listwatcher).box).x
		= Logo.anInt(object_18_, lcontext);
	    ((StretchyBox) ((ListWatcher) listwatcher).box).y
		= Logo.anInt(object_19_, lcontext);
	    listwatcher.inval();
	}
    }
    
    void prim_setListWatcherWidthHeight(Object object, Object object_20_,
					Object object_21_, LContext lcontext) {
	if (object instanceof ListWatcher) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.inval();
	    ((StretchyBox) ((ListWatcher) listwatcher).box).w
		= Logo.anInt(object_20_, lcontext);
	    ((StretchyBox) ((ListWatcher) listwatcher).box).h
		= Logo.anInt(object_21_, lcontext);
	    ((ListWatcher) listwatcher).scrollBarHeight
		= ((StretchyBox) ((ListWatcher) listwatcher).box).h - 23 - 20;
	    ((ListWatcherPane) ((ListWatcher) listwatcher).pane).w
		= ((StretchyBox) ((ListWatcher) listwatcher).box).w;
	    listwatcher.inval();
	}
    }
    
    void prim_setListWatcherLabel(Object object, Object object_22_,
				  LContext lcontext) {
	if (object instanceof ListWatcher) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.inval();
	    ((ListWatcher) listwatcher).listTitle
		= Logo.aString(object_22_, lcontext);
	    listwatcher.inval();
	}
    }
    
    void prim_setListWatcherList(Object object, Object object_23_,
				 LContext lcontext) {
	if (object instanceof ListWatcher && object_23_ instanceof Object[]) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.setList((Object[]) object_23_);
	    listwatcher.inval();
	}
    }
    
    void prim_highlightListWatcherIndex(Object object, Object object_24_,
					LContext lcontext) {
	if (object instanceof ListWatcher) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.highlightIndex(Logo.anInt(object_24_, lcontext));
	    listwatcher.inval();
	}
    }
    
    void prim_clearListWatcherHighlights(Object object, LContext lcontext) {
	if (object instanceof ListWatcher) {
	    ListWatcher listwatcher = (ListWatcher) object;
	    listwatcher.clearHighlights();
	    listwatcher.inval();
	}
    }
    
    Watcher prim_newVarWatcher(Object object, LContext lcontext) {
	return new Watcher(lcontext);
    }
    
    Object prim_watcherX(Object object, LContext lcontext) {
	if (object instanceof Watcher)
	    return new Double((double) ((StretchyBox)
					((Watcher) (Watcher) object).box).x);
	if (object instanceof ListWatcher)
	    return new Double((double) ((StretchyBox)
					(((ListWatcher) (ListWatcher) object)
					 .box)).x);
	return new Double(0.0);
    }
    
    Object prim_watcherY(Object object, LContext lcontext) {
	if (object instanceof Watcher)
	    return new Double((double) ((StretchyBox)
					((Watcher) (Watcher) object).box).y);
	if (object instanceof ListWatcher)
	    return new Double((double) ((StretchyBox)
					(((ListWatcher) (ListWatcher) object)
					 .box)).y);
	return new Double(0.0);
    }
}
