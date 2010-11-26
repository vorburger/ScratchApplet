// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerPrims.java

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.security.AccessControlException;
import java.util.Vector;
import javax.sound.midi.*;

class PlayerPrims extends Primitives
{

    PlayerPrims()
    {
    }

    public String[] primlist()
    {
        return primlist;
    }

    public Object dispatch(int i, Object aobj[], LContext lcontext)
    {
        switch(i)
        {
        case 0: // '\0'
            lcontext.canvas.redraw_all();
            return null;

        case 1: // '\001'
            lcontext.canvas.redraw_invalid();
            return null;

        case 2: // '\002'
            return prim_drawrect(aobj[0], aobj[1], aobj[2], aobj[3], lcontext);

        case 3: // '\003'
            return lcontext.canvas.stage != null ? lcontext.canvas.stage : new Object[0];

        case 4: // '\004'
            return prim_setstage(aobj[0], lcontext);

        case 5: // '\005'
            return ((Object) (lcontext.canvas.sprites));

        case 6: // '\006'
            lcontext.canvas.sprites = (Object[])aobj[0];
            return null;

        case 7: // '\007'
            return prim_readprojfile(aobj[0], lcontext);

        case 8: // '\b'
            return prim_readprojurl(lcontext);

        case 9: // '\t'
            LContext _tmp = lcontext;
            return new Boolean(true);

        case 10: // '\n'
            return new Boolean((aobj[0] instanceof String) || (aobj[0] instanceof Symbol));

        case 11: // '\013'
            return new Boolean(aobj[0] instanceof Sprite);

        case 12: // '\f'
            return new Boolean(aobj[0] instanceof Color);

        case 13: // '\r'
            return new Double(lcontext.canvas.mouseX);

        case 14: // '\016'
            return new Double(lcontext.canvas.mouseY);

        case 15: // '\017'
            return new Boolean(lcontext.canvas.mouseIsDown);

        case 16: // '\020'
            return prim_mouseclick(lcontext);

        case 17: // '\021'
            return prim_keystroke(lcontext);

        case 18: // '\022'
            return prim_keydown(aobj[0], lcontext);

        case 19: // '\023'
            lcontext.canvas.clearkeys();
            return null;

        case 20: // '\024'
            return prim_midisetinstrument(aobj[0], aobj[1], lcontext);

        case 21: // '\025'
            return prim_midinoteon(aobj[0], aobj[1], aobj[2], lcontext);

        case 22: // '\026'
            return prim_midinoteoff(aobj[0], aobj[1], lcontext);

        case 23: // '\027'
            return prim_midicontrol(aobj[0], aobj[1], aobj[2], lcontext);

        case 24: // '\030'
            lcontext.canvas.updatePenTrails();
            return null;

        case 25: // '\031'
            return new Double(lcontext.canvas.soundLevel());

        case 26: // '\032'
            return prim_jpegDecode(aobj[0], lcontext);

        case 27: // '\033'
            return new Double(Runtime.getRuntime().totalMemory());

        case 28: // '\034'
            return new Double(Runtime.getRuntime().freeMemory());

        case 29: // '\035'
            Runtime.getRuntime().gc();
            return null;

        case 30: // '\036'
            lcontext.canvas.clearall(lcontext);
            return null;

        case 31: // '\037'
            lcontext.canvas.requestFocus();
            return null;

        case 32: // ' '
            lcontext.canvas.setMessage(Logo.aString(aobj[0], lcontext));
            return null;

        case 33: // '!'
            return prim_getSensorValue(aobj[0], lcontext);

        case 34: // '"'
            return new Boolean(lcontext.autostart);

        case 35: // '#'
            return new Boolean(aobj[0] instanceof QuotedSymbol);

        case 36: // '$'
            return ((QuotedSymbol)aobj[0]).sym;
        }
        return null;
    }

    static synchronized LContext startup(String s, String s1, Container container, boolean flag)
    {
        LContext lcontext = new LContext();
        Logo.setupPrims(primclasses, lcontext);
        lcontext.codeBase = s;
        lcontext.projectURL = s1;
        lcontext.autostart = flag;
        PlayerCanvas playercanvas = new PlayerCanvas();
        playercanvas.lc = lcontext;
        lcontext.canvas = playercanvas;
        Skin.readSkin(playercanvas);
        SoundPlayer.startPlayer();
        container.add(playercanvas, "Center");
        LogoCommandRunner.startLogoThread("load \"startup startup", lcontext);
        return lcontext;
    }

    static synchronized void shutdown(LContext lcontext)
    {
        if(lcontext != null)
        {
            LogoCommandRunner.stopLogoThread(lcontext);
            lcontext.canvas.clearall(lcontext);
        }
        SoundPlayer.stopSoundsForApplet(lcontext);
        sensorValues = new int[16];
        for(int i = 3; i < 8; i++)
            sensorValues[i] = 1000;

    }

    static void stopMIDINotes()
    {
        if(midiSynth == null)
            return;
        MidiChannel amidichannel[] = midiSynth.getChannels();
        for(int i = 0; i < amidichannel.length; i++)
            if(amidichannel[i] != null)
            {
                amidichannel[i].allNotesOff();
                amidichannel[i].programChange(0);
            }

    }

    Object prim_drawrect(Object obj, Object obj1, Object obj2, Object obj3, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        int j = Logo.anInt(obj1, lcontext);
        int k = Logo.anInt(obj2, lcontext);
        int l = Logo.anInt(obj3, lcontext);
        lcontext.canvas.drawRect(i, j, k, l);
        return null;
    }

    Object prim_setstage(Object obj, LContext lcontext)
    {
        if(!(obj instanceof Sprite))
        {
            return null;
        } else
        {
            Sprite sprite = (Sprite)obj;
            sprite.x = sprite.y = 0.0D;
            lcontext.canvas.stage = sprite;
            return null;
        }
    }

    Object prim_readprojfile(Object obj, LContext lcontext)
    {
        Object aobj[] = new Object[0];
        lcontext.canvas.startLoading();
        FileInputStream fileinputstream;
        try
        {
            fileinputstream = new FileInputStream(obj.toString());
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            Logo.error("File not found: " + obj, lcontext);
            return ((Object) (aobj));
        }
        lcontext.canvas.loadingProgress(0.5D);
        Object aobj1[][];
        try
        {
            ObjReader objreader = new ObjReader(fileinputstream);
            aobj1 = objreader.readObjects(lcontext);
            fileinputstream.close();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
            return ((Object) (aobj));
        }
        lcontext.canvas.stopLoading();
        return ((Object) (aobj1));
    }

    Object prim_readprojurl(LContext lcontext)
    {
        Object aobj[] = new Object[0];
        if(lcontext.projectURL == null)
            return ((Object) (aobj));
        URL url;
        try
        {
            url = new URL(lcontext.projectURL);
        }
        catch(MalformedURLException malformedurlexception)
        {
            Logo.error("Bad project URL: " + lcontext.projectURL, lcontext);
            return ((Object) (aobj));
        }
        Object aobj1[][];
        try
        {
            lcontext.canvas.startLoading();
            URLConnection urlconnection = url.openConnection();
            urlconnection.connect();
            int i = urlconnection.getContentLength();
            byte abyte0[] = new byte[i];
            InputStream inputstream = urlconnection.getInputStream();
            int j = 0;
            for(int k = 0; j >= 0 && k < i;)
            {
                j = inputstream.read(abyte0, k, i - k);
                if(j > 0)
                {
                    k += j;
                    lcontext.canvas.loadingProgress((double)k / (double)i);
                } else
                {
                    try
                    {
                        Thread.sleep(100L);
                    }
                    catch(InterruptedException interruptedexception) { }
                }
            }

            inputstream.close();
            ObjReader objreader = new ObjReader(new ByteArrayInputStream(abyte0));
            aobj1 = objreader.readObjects(lcontext);
        }
        catch(IOException ioexception)
        {
            Logo.error("Problem reading project from URL: " + lcontext.projectURL, lcontext);
            return ((Object) (aobj));
        }
        lcontext.canvas.stopLoading();
        return ((Object) (aobj1));
    }

    Object prim_mouseclick(LContext lcontext)
    {
        if(lcontext.canvas.mouseclicks.isEmpty())
            return ((Object) (new Object[0]));
        else
            return lcontext.canvas.mouseclicks.remove(0);
    }

    Object prim_keystroke(LContext lcontext)
    {
        if(lcontext.canvas.keystrokes.isEmpty())
            return ((Object) (new Object[0]));
        else
            return lcontext.canvas.keystrokes.remove(0);
    }

    Object prim_keydown(Object obj, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        if(i > 255)
            return new Boolean(false);
        else
            return new Boolean(lcontext.canvas.keydown[i]);
    }

    Object prim_midisetinstrument(Object obj, Object obj1, LContext lcontext)
    {
        init_midi(lcontext);
        if(midiSynth == null)
        {
            return null;
        } else
        {
            int i = Logo.anInt(obj, lcontext) - 1 & 0xf;
            int j = Logo.anInt(obj1, lcontext) - 1 & 0x7f;
            midiSynth.getChannels()[i].programChange(j);
            return null;
        }
    }

    Object prim_midinoteon(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        init_midi(lcontext);
        if(midiSynth == null)
        {
            return null;
        } else
        {
            int i = Logo.anInt(obj, lcontext) - 1 & 0xf;
            int j = Logo.anInt(obj1, lcontext) & 0x7f;
            int k = Logo.anInt(obj2, lcontext) & 0x7f;
            midiSynth.getChannels()[i].noteOn(j, k);
            return null;
        }
    }

    Object prim_midinoteoff(Object obj, Object obj1, LContext lcontext)
    {
        init_midi(lcontext);
        if(midiSynth == null)
        {
            return null;
        } else
        {
            int i = Logo.anInt(obj, lcontext) - 1 & 0xf;
            int j = Logo.anInt(obj1, lcontext) & 0x7f;
            midiSynth.getChannels()[i].noteOff(j);
            return null;
        }
    }

    Object prim_midicontrol(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        init_midi(lcontext);
        if(midiSynth == null)
        {
            return null;
        } else
        {
            int i = Logo.anInt(obj, lcontext) - 1 & 0xf;
            int j = Logo.anInt(obj1, lcontext) & 0x7f;
            int k = Logo.anInt(obj2, lcontext) & 0x7f;
            midiSynth.getChannels()[i].controlChange(j, k);
            return null;
        }
    }

    void init_midi(LContext lcontext)
    {
        if(midiSynthInitialized)
            return;
        midiSynthInitialized = true;
        try
        {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();
            if(midiSynth.getDefaultSoundbank() == null)
            {
                lcontext.canvas.setMessage("Reading sound bank from server. Please wait...");
                LContext _tmp = lcontext;
                URL url = new URL(lcontext.codeBase + "soundbank.gm");
                javax.sound.midi.Soundbank soundbank = MidiSystem.getSoundbank(url);
                if(soundbank != null)
                {
                    midiSynth.loadAllInstruments(soundbank);
                    lcontext.canvas.setMessage("");
                } else
                {
                    midiSynth.close();
                    midiSynth = null;
                }
            }
        }
        catch(MidiUnavailableException midiunavailableexception)
        {
            midiunavailableexception.printStackTrace();
            midiSynth = null;
        }
        catch(MalformedURLException malformedurlexception)
        {
            malformedurlexception.printStackTrace();
            midiSynth = null;
        }
        catch(InvalidMidiDataException invalidmididataexception)
        {
            invalidmididataexception.printStackTrace();
            midiSynth = null;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
            midiSynth = null;
        }
        catch(AccessControlException accesscontrolexception)
        {
            accesscontrolexception.printStackTrace();
            midiSynth = null;
        }
        if(midiSynth != null)
        {
            MidiChannel amidichannel[] = midiSynth.getChannels();
            for(int i = 0; i < amidichannel.length; i++)
                if(amidichannel[i] != null)
                    amidichannel[i].programChange(0);

        } else
        {
            lcontext.canvas.setMessage("No soundbank; note & drum commands disabled.");
        }
    }

    Object prim_jpegDecode(Object obj, LContext lcontext)
    {
        if(!(obj instanceof byte[]))
            return null;
        byte abyte0[] = (byte[])obj;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(abyte0);
        MediaTracker mediatracker = new MediaTracker(lcontext.canvas);
        mediatracker.addImage(image, 0);
        try
        {
            mediatracker.waitForID(0);
        }
        catch(InterruptedException interruptedexception) { }
        if(image == null)
        {
            return null;
        } else
        {
            int i = image.getWidth(null);
            int j = image.getHeight(null);
            BufferedImage bufferedimage = new BufferedImage(i, j, 2);
            Graphics g = bufferedimage.getGraphics();
            g.drawImage(image, 0, 0, i, j, null);
            g.dispose();
            return bufferedimage;
        }
    }

    Object prim_getSensorValue(Object obj, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext) - 1;
        if(i < 0 || i > 15)
            return new Double(123D);
        else
            return new Double((double)sensorValues[i] / 10D);
    }

    static Synthesizer midiSynth;
    static boolean midiSynthInitialized = false;
    static int sensorValues[] = {
        0, 0, 0, 1000, 1000, 1000, 1000, 1000, 0, 0, 
        0, 0, 0, 0, 0, 0
    };
    static final String primlist[] = {
        "redrawall", "0", "redraw", "0", "drawrect", "4", "stage", "0", "setstage", "1", 
        "sprites", "0", "setsprites", "1", "readprojfile", "1", "readprojurl", "0", "applet?", "0", 
        "string?", "1", "sprite?", "1", "color?", "1", "mouseX", "0", "mouseY", "0", 
        "mouseIsDown", "0", "mouseClick", "0", "keystroke", "0", "keydown?", "1", "clearkeys", "0", 
        "midisetinstrument", "2", "midinoteon", "3", "midinoteoff", "2", "midicontrol", "3", "updatePenTrails", "0", 
        "soundLevel", "0", "jpegDecode", "1", "memTotal", "0", "memFree", "0", "gc", "0", 
        "clearall", "0", "requestFocus", "0", "setMessage", "1", "getSensorValue", "1", "autostart?", "0", 
        "quoted?", "1", "unquote", "1"
    };
    static final String primclasses[] = {
        "SystemPrims", "MathPrims", "ControlPrims", "DefiningPrims", "WordListPrims", "FilePrims", "PlayerPrims", "SpritePrims"
    };

}
