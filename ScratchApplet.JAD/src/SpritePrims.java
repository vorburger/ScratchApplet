// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpritePrims.java

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Hashtable;

class SpritePrims extends Primitives
{

    SpritePrims()
    {
    }

    public String[] primlist()
    {
        return primlist;
    }

    public Object dispatch(int i, Object aobj[], LContext lcontext)
    {
        Sprite sprite = lcontext.who;
        switch(i)
        {
        case 0: // '\0'
            return sprite != null ? sprite : new Object[0];

        case 1: // '\001'
            lcontext.who = (aobj[0] instanceof Sprite) ? (Sprite)aobj[0] : null;
            return null;

        case 2: // '\002'
            return new Double(sprite != null ? sprite.x : 0.0D);

        case 3: // '\003'
            return new Double(sprite != null ? sprite.y : 0.0D);

        case 4: // '\004'
            sprite.x = Logo.aDouble(aobj[0], lcontext);
            return null;

        case 5: // '\005'
            sprite.y = Logo.aDouble(aobj[0], lcontext);
            return null;

        case 6: // '\006'
            return new Double(sprite.screenX());

        case 7: // '\007'
            return new Double(sprite.screenY());

        case 8: // '\b'
            return new Double(sprite.screenX() + sprite.outImage().getWidth(null));

        case 9: // '\t'
            return new Double(sprite.screenY() + sprite.outImage().getHeight(null));

        case 10: // '\n'
            sprite.setscreenX(Logo.aDouble(aobj[0], lcontext));
            return null;

        case 11: // '\013'
            sprite.setscreenY(Logo.aDouble(aobj[0], lcontext));
            return null;

        case 12: // '\f'
            return new Double(sprite.outImage().getWidth(null));

        case 13: // '\r'
            return new Double(sprite.outImage().getHeight(null));

        case 14: // '\016'
            return sprite.costume;

        case 15: // '\017'
            sprite.setcostume(aobj[0], aobj[1], aobj[2], lcontext);
            sprite.costumeChanged();
            return null;

        case 16: // '\020'
            return new Double(sprite.scale);

        case 17: // '\021'
            sprite.setscale(aobj[0], lcontext);
            return null;

        case 18: // '\022'
            return prim_heading(lcontext);

        case 19: // '\023'
            sprite.rotationDegrees = Logo.aDouble(aobj[0], lcontext) % 360D;
            sprite.costumeChanged();
            return null;

        case 20: // '\024'
            return new Double(sprite.rotationstyle);

        case 21: // '\025'
            sprite.rotationstyle = Logo.anInt(aobj[0], lcontext);
            sprite.costumeChanged();
            return null;

        case 22: // '\026'
            sprite.show();
            return null;

        case 23: // '\027'
            sprite.hide();
            return null;

        case 24: // '\030'
            sprite.inval();
            return null;

        case 25: // '\031'
            return prim_containsPoint(aobj[0], aobj[1], lcontext);

        case 26: // '\032'
            return new Double(sprite.alpha);

        case 27: // '\033'
            sprite.setalpha(aobj[0], lcontext);
            return null;

        case 28: // '\034'
            return new Double(sprite.color);

        case 29: // '\035'
            sprite.color = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 30: // '\036'
            return new Double(sprite.brightness);

        case 31: // '\037'
            sprite.brightness = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 32: // ' '
            return new Double(sprite.fisheye);

        case 33: // '!'
            sprite.fisheye = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 34: // '"'
            return new Double(sprite.whirl);

        case 35: // '#'
            sprite.whirl = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 36: // '$'
            return new Double(sprite.mosaic);

        case 37: // '%'
            sprite.mosaic = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 38: // '&'
            return new Double(sprite.pixelate);

        case 39: // '\''
            sprite.pixelate = Logo.aDouble(aobj[0], lcontext);
            sprite.filterChanged = true;
            return null;

        case 40: // '('
            Toolkit.getDefaultToolkit().beep();
            return null;

        case 41: // ')'
            return SoundPlayer.startSound(aobj[0], sprite, lcontext);

        case 42: // '*'
            return new Boolean(SoundPlayer.isSoundPlaying(aobj[0]));

        case 43: // '+'
            SoundPlayer.stopSound(aobj[0]);
            return null;

        case 44: // ','
            SoundPlayer.stopSoundsForApplet(lcontext);
            return null;

        case 45: // '-'
            sprite.setPenDown(Logo.aBoolean(aobj[0], lcontext));
            return null;

        case 46: // '.'
            if(aobj[0] instanceof Color)
                sprite.setPenColor((Color)aobj[0]);
            return null;

        case 47: // '/'
            return new Double(sprite.penSize);

        case 48: // '0'
            sprite.penSize = Logo.anInt(aobj[0], lcontext);
            return null;

        case 49: // '1'
            return new Double(sprite.penHue);

        case 50: // '2'
            sprite.setPenHue(Logo.aDouble(aobj[0], lcontext));
            return null;

        case 51: // '3'
            return new Double(sprite.penShade);

        case 52: // '4'
            sprite.setPenShade(Logo.aDouble(aobj[0], lcontext));
            return null;

        case 53: // '5'
            lcontext.canvas.clearPenTrails();
            return null;

        case 54: // '6'
            lcontext.canvas.stampCostume(sprite);
            return null;

        case 55: // '7'
            return prim_newcolor(aobj[0], aobj[1], aobj[2], lcontext);

        case 56: // '8'
            return new Boolean(sprite.touchingSprite(aobj[0], lcontext));

        case 57: // '9'
            return new Boolean(sprite.touchingColor(aobj[0], lcontext));

        case 58: // ':'
            return new Boolean(sprite.colorTouchingColor(aobj[0], aobj[1], lcontext));

        case 59: // ';'
            return new Boolean((aobj[0] instanceof Sprite) && ((Sprite)aobj[0]).isShowing());

        case 60: // '<'
            sprite.talkbubble(aobj[0], false, true, lcontext);
            return null;

        case 61: // '='
            sprite.talkbubble(aobj[0], false, false, lcontext);
            return null;

        case 62: // '>'
            sprite.updateBubble();
            return null;

        case 63: // '?'
            return new Boolean(aobj[0] instanceof Watcher);

        case 64: // '@'
            prim_setWatcherXY(aobj[0], aobj[1], aobj[2], lcontext);
            return null;

        case 65: // 'A'
            prim_setWatcherColorAndLabel(aobj[0], aobj[1], aobj[2], lcontext);
            return null;

        case 66: // 'B'
            prim_setWatcherSliderMinMax(aobj[0], aobj[1], aobj[2], lcontext);
            return null;

        case 67: // 'C'
            prim_setWatcherMode(aobj[0], aobj[1], lcontext);
            return null;

        case 68: // 'D'
            prim_setWatcherText(aobj[0], aobj[1], lcontext);
            return null;

        case 69: // 'E'
            return new Boolean(sprite.isDraggable);

        case 70: // 'F'
            sprite.isDraggable = Logo.aBoolean(aobj[0], lcontext);
            return null;

        case 71: // 'G'
            ((Watcher)aobj[0]).show();
            return null;

        case 72: // 'H'
            ((Watcher)aobj[0]).hide();
            return null;

        case 73: // 'I'
            return prim_listContents(aobj[0], lcontext);

        case 74: // 'J'
            return prim_hasKey(aobj[0], aobj[1], lcontext);

        case 75: // 'K'
            return new Boolean(aobj[0] instanceof ListWatcher);

        case 76: // 'L'
            return prim_newListWatcher(lcontext);

        case 77: // 'M'
            prim_setListWatcherXY(aobj[0], aobj[1], aobj[2], lcontext);
            return null;

        case 78: // 'N'
            prim_setListWatcherWidthHeight(aobj[0], aobj[1], aobj[2], lcontext);
            return null;

        case 79: // 'O'
            prim_setListWatcherLabel(aobj[0], aobj[1], lcontext);
            return null;

        case 80: // 'P'
            prim_setListWatcherList(aobj[0], aobj[1], lcontext);
            return null;

        case 81: // 'Q'
            prim_highlightListWatcherIndex(aobj[0], aobj[1], lcontext);
            return null;

        case 82: // 'R'
            prim_clearListWatcherHighlights(aobj[0], lcontext);
            return null;

        case 83: // 'S'
            sprite.talkbubble(aobj[0], true, true, lcontext);
            return null;

        case 84: // 'T'
            lcontext.canvas.showAskPrompt(Logo.aString(aobj[0], lcontext));
            return null;

        case 85: // 'U'
            lcontext.canvas.hideAskPrompt();
            return null;

        case 86: // 'V'
            return new Boolean(lcontext.canvas.askPromptShowing());

        case 87: // 'W'
            return lcontext.canvas.lastAnswer;

        case 88: // 'X'
            return new Boolean((aobj[0] instanceof Sprite) && ((Sprite)aobj[0]).isVisible());

        case 89: // 'Y'
            return prim_newVarWatcher(aobj[0], lcontext);

        case 90: // 'Z'
            return prim_watcherX(aobj[0], lcontext);

        case 91: // '['
            return prim_watcherY(aobj[0], lcontext);
        }
        return null;
    }

    Object prim_heading(LContext lcontext)
    {
        double d = lcontext.who.rotationDegrees % 360D;
        if(d > 180D)
            d -= 360D;
        return new Double(d);
    }

    Object prim_containsPoint(Object obj, Object obj1, LContext lcontext)
    {
        if(lcontext.who == null)
        {
            return new Boolean(false);
        } else
        {
            int i = Logo.anInt(obj, lcontext) + 241;
            int j = 206 - Logo.anInt(obj1, lcontext);
            return new Boolean(lcontext.who.containsPoint(i, j));
        }
    }

    Color prim_newcolor(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        int j = Logo.anInt(obj1, lcontext);
        int k = Logo.anInt(obj2, lcontext);
        return new Color(i, j, k);
    }

    void prim_setWatcherXY(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof Watcher))
        {
            return;
        } else
        {
            Watcher watcher = (Watcher)obj;
            watcher.inval();
            watcher.box.x = Logo.anInt(obj1, lcontext);
            watcher.box.y = Logo.anInt(obj2, lcontext);
            watcher.inval();
            return;
        }
    }

    void prim_setWatcherColorAndLabel(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof Watcher))
        {
            return;
        } else
        {
            Watcher watcher = (Watcher)obj;
            watcher.inval();
            watcher.readout.color = (Color)obj1;
            watcher.label = (String)obj2;
            watcher.inval();
            return;
        }
    }

    void prim_setWatcherSliderMinMax(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof Watcher))
        {
            return;
        } else
        {
            Watcher watcher = (Watcher)obj;
            watcher.sliderMin = Logo.aDouble(obj1, lcontext);
            watcher.sliderMax = Logo.aDouble(obj2, lcontext);
            watcher.isDiscrete = (double)Math.round(watcher.sliderMin) == watcher.sliderMin && (double)Math.round(watcher.sliderMax) == watcher.sliderMax;
            return;
        }
    }

    void prim_setWatcherMode(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof Watcher))
        {
            return;
        } else
        {
            Watcher watcher = (Watcher)obj;
            int i = Logo.anInt(obj1, lcontext);
            watcher.inval();
            watcher.mode = Math.max(0, Math.min(i, 3));
            watcher.inval();
            return;
        }
    }

    void prim_setWatcherText(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof Watcher))
            return;
        Watcher watcher = (Watcher)obj;
        String s = Logo.prs(obj1);
        if(obj1 instanceof Double)
        {
            double d = ((Double)obj1).doubleValue();
            double d1 = Math.abs(d);
            DecimalFormat decimalformat = new DecimalFormat("0.#");
            if(d1 < 1.0D)
                decimalformat = new DecimalFormat("0.######");
            if(d1 < 1.0000000000000001E-05D || d1 >= 1000000D)
                decimalformat = new DecimalFormat("0.###E0");
            if(d1 == 0.0D)
                decimalformat = new DecimalFormat("0.#");
            s = decimalformat.format(d);
        }
        if(s.equals(watcher.readout.contents))
        {
            return;
        } else
        {
            watcher.inval();
            watcher.readout.contents = s;
            watcher.inval();
            return;
        }
    }

    Object prim_listContents(Object obj, LContext lcontext)
    {
        if(!(obj instanceof Object[]))
        {
            Logo.error("argument must be a list", lcontext);
            return "";
        }
        Object aobj[] = (Object[])obj;
        if(aobj.length == 0)
            return "";
        StringBuffer stringbuffer = new StringBuffer(1000);
        boolean flag = false;
        for(int i = 0; i < aobj.length; i++)
        {
            Object obj1 = aobj[i];
            if(!(obj1 instanceof String))
                obj1 = Logo.prs(obj1);
            if(((String)obj1).length() > 1)
                flag = true;
        }

        for(int j = 0; j < aobj.length; j++)
        {
            Object obj2 = aobj[j];
            if(!(obj2 instanceof String))
                obj2 = Logo.prs(obj2);
            stringbuffer.append(obj2);
            if(flag)
                stringbuffer.append(" ");
        }

        if(flag)
            stringbuffer.deleteCharAt(stringbuffer.length() - 1);
        return stringbuffer.toString();
    }

    Object prim_hasKey(Object obj, Object obj1, LContext lcontext)
    {
        Hashtable hashtable = (Hashtable)lcontext.props.get(obj);
        if(hashtable == null)
            return new Boolean(false);
        else
            return new Boolean(hashtable.containsKey(obj1));
    }

    Object prim_newListWatcher(LContext lcontext)
    {
        ListWatcher listwatcher = new ListWatcher(lcontext);
        Object aobj[] = new Object[lcontext.canvas.sprites.length + 1];
        for(int i = 0; i < lcontext.canvas.sprites.length; i++)
            aobj[i] = lcontext.canvas.sprites[i];

        aobj[aobj.length - 1] = listwatcher;
        lcontext.canvas.sprites = aobj;
        return listwatcher;
    }

    void prim_setListWatcherXY(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.inval();
            listwatcher.box.x = Logo.anInt(obj1, lcontext);
            listwatcher.box.y = Logo.anInt(obj2, lcontext);
            listwatcher.inval();
            return;
        }
    }

    void prim_setListWatcherWidthHeight(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.inval();
            listwatcher.box.w = Logo.anInt(obj1, lcontext);
            listwatcher.box.h = Logo.anInt(obj2, lcontext);
            listwatcher.scrollBarHeight = listwatcher.box.h - 23 - 20;
            listwatcher.pane.w = listwatcher.box.w;
            listwatcher.inval();
            return;
        }
    }

    void prim_setListWatcherLabel(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.inval();
            listwatcher.listTitle = Logo.aString(obj1, lcontext);
            listwatcher.inval();
            return;
        }
    }

    void prim_setListWatcherList(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
            return;
        if(!(obj1 instanceof Object[]))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.setList((Object[])obj1);
            listwatcher.inval();
            return;
        }
    }

    void prim_highlightListWatcherIndex(Object obj, Object obj1, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.highlightIndex(Logo.anInt(obj1, lcontext));
            listwatcher.inval();
            return;
        }
    }

    void prim_clearListWatcherHighlights(Object obj, LContext lcontext)
    {
        if(!(obj instanceof ListWatcher))
        {
            return;
        } else
        {
            ListWatcher listwatcher = (ListWatcher)obj;
            listwatcher.clearHighlights();
            listwatcher.inval();
            return;
        }
    }

    Watcher prim_newVarWatcher(Object obj, LContext lcontext)
    {
        return new Watcher(lcontext);
    }

    Object prim_watcherX(Object obj, LContext lcontext)
    {
        if(obj instanceof Watcher)
            return new Double(((Watcher)obj).box.x);
        if(obj instanceof ListWatcher)
            return new Double(((ListWatcher)obj).box.x);
        else
            return new Double(0.0D);
    }

    Object prim_watcherY(Object obj, LContext lcontext)
    {
        if(obj instanceof Watcher)
            return new Double(((Watcher)obj).box.y);
        if(obj instanceof ListWatcher)
            return new Double(((ListWatcher)obj).box.y);
        else
            return new Double(0.0D);
    }

    static final String primlist[] = {
        "who", "0", "talkto", "1", "xpos", "0", "ypos", "0", "setx", "1", 
        "sety", "1", "%left", "0", "%top", "0", "%right", "0", "%bottom", "0", 
        "%setleft", "1", "%settop", "1", "%w", "0", "%h", "0", "costume", "0", 
        "setcostume", "3", "%scale", "0", "setscale", "1", "heading", "0", "setheading", "1", 
        "rotationstyle", "0", "setrotationstyle", "1", "show", "0", "hide", "0", "changed", "0", 
        "containsPoint?", "2", "alpha", "0", "setalpha", "1", "color", "0", "setcolor", "1", 
        "brightness", "0", "setbrightness", "1", "fisheye", "0", "setfisheye", "1", "whirl", "0", 
        "setwhirl", "1", "mosaic", "0", "setmosaic", "1", "pixelate", "0", "setpixelate", "1", 
        "beep", "0", "startSound", "1", "isSoundPlaying?", "1", "stopSound", "1", "stopAllSounds", "0", 
        "setPenDown", "1", "setPenColor", "1", "penSize", "0", "setPenSize", "1", "penHue", "0", 
        "setPenHue", "1", "penShade", "0", "setPenShade", "1", "clearPenTrails", "0", "stampCostume", "0", 
        "newcolor", "3", "touchingSprite?", "1", "touchingColor?", "1", "colorTouchingColor?", "2", "isShowing", "1", 
        "talkbubble", "1", "thinkbubble", "1", "updateBubble", "0", "watcher?", "1", "setWatcherXY", "3", 
        "setWatcherColorAndLabel", "3", "setWatcherSliderMinMax", "3", "setWatcherMode", "2", "setWatcherText", "2", "isDraggable", "0", 
        "setDraggable", "1", "showWatcher", "1", "hideWatcher", "1", "listContents", "1", "hasKey", "2", 
        "listWatcher?", "1", "newListWatcher", "0", "setListWatcherXY", "3", "setListWatcherWidthHeight", "3", "setListWatcherLabel", "2", 
        "setListWatcherList", "2", "highlightListWatcherIndex", "2", "clearListWatcherHighlights", "1", "askbubble", "1", "showAskPrompt", "1", 
        "hideAskPrompt", "0", "askPromptShowing?", "0", "lastAnswer", "0", "isVisible", "1", "newVarWatcher", "1", 
        "watcherX", "1", "watcherY", "1"
    };

}
