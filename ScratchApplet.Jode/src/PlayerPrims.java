/* PlayerPrims - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

class PlayerPrims extends Primitives
{
    static Synthesizer midiSynth;
    static boolean midiSynthInitialized = false;
    static int[] sensorValues
	= { 0, 0, 0, 1000, 1000, 1000, 1000, 1000, 0, 0, 0, 0, 0, 0, 0, 0 };
    static final String[] primlist
	= { "redrawall", "0", "redraw", "0", "drawrect", "4", "stage", "0",
	    "setstage", "1", "sprites", "0", "setsprites", "1", "readprojfile",
	    "1", "readprojurl", "0", "applet?", "0", "string?", "1", "sprite?",
	    "1", "color?", "1", "mouseX", "0", "mouseY", "0", "mouseIsDown",
	    "0", "mouseClick", "0", "keystroke", "0", "keydown?", "1",
	    "clearkeys", "0", "midisetinstrument", "2", "midinoteon", "3",
	    "midinoteoff", "2", "midicontrol", "3", "updatePenTrails", "0",
	    "soundLevel", "0", "jpegDecode", "1", "memTotal", "0", "memFree",
	    "0", "gc", "0", "clearall", "0", "requestFocus", "0", "setMessage",
	    "1", "getSensorValue", "1", "autostart?", "0", "quoted?", "1",
	    "unquote", "1" };
    static final String[] primclasses
	= { "SystemPrims", "MathPrims", "ControlPrims", "DefiningPrims",
	    "WordListPrims", "FilePrims", "PlayerPrims", "SpritePrims" };
    
    public String[] primlist() {
	return primlist;
    }
    
    public Object dispatch(int i, Object[] objects, LContext lcontext) {
	switch (i) {
	case 0:
	    ((LContext) lcontext).canvas.redraw_all();
	    return null;
	case 1:
	    ((LContext) lcontext).canvas.redraw_invalid();
	    return null;
	case 2:
	    return prim_drawrect(objects[0], objects[1], objects[2],
				 objects[3], lcontext);
	case 3:
	    return (((PlayerCanvas) ((LContext) lcontext).canvas).stage == null
		    ? (Object) new Object[0]
		    : ((PlayerCanvas) ((LContext) lcontext).canvas).stage);
	case 4:
	    return prim_setstage(objects[0], lcontext);
	case 5:
	    return ((PlayerCanvas) ((LContext) lcontext).canvas).sprites;
	case 6:
	    ((PlayerCanvas) ((LContext) lcontext).canvas).sprites
		= (Object[]) objects[0];
	    return null;
	case 7:
	    return prim_readprojfile(objects[0], lcontext);
	case 8:
	    return prim_readprojurl(lcontext);
	case 9: {
	    Boolean var_boolean = new Boolean;
	    if (lcontext != null) {
		/* empty */
	    }
	    ((UNCONSTRUCTED)var_boolean).Boolean(true);
	    return var_boolean;
	}
	case 10:
	    return new Boolean(objects[0] instanceof String
			       || objects[0] instanceof Symbol);
	case 11:
	    return new Boolean(objects[0] instanceof Sprite);
	case 12:
	    return new Boolean(objects[0] instanceof Color);
	case 13:
	    return new Double((double) ((PlayerCanvas)
					((LContext) lcontext).canvas).mouseX);
	case 14:
	    return new Double((double) ((PlayerCanvas)
					((LContext) lcontext).canvas).mouseY);
	case 15:
	    return new Boolean(((PlayerCanvas) ((LContext) lcontext).canvas)
			       .mouseIsDown);
	case 16:
	    return prim_mouseclick(lcontext);
	case 17:
	    return prim_keystroke(lcontext);
	case 18:
	    return prim_keydown(objects[0], lcontext);
	case 19:
	    ((LContext) lcontext).canvas.clearkeys();
	    return null;
	case 20:
	    return prim_midisetinstrument(objects[0], objects[1], lcontext);
	case 21:
	    return prim_midinoteon(objects[0], objects[1], objects[2],
				   lcontext);
	case 22:
	    return prim_midinoteoff(objects[0], objects[1], lcontext);
	case 23:
	    return prim_midicontrol(objects[0], objects[1], objects[2],
				    lcontext);
	case 24:
	    ((LContext) lcontext).canvas.updatePenTrails();
	    return null;
	case 25:
	    return new Double((double) ((LContext) lcontext).canvas
					   .soundLevel());
	case 26:
	    return prim_jpegDecode(objects[0], lcontext);
	case 27:
	    return new Double((double) Runtime.getRuntime().totalMemory());
	case 28:
	    return new Double((double) Runtime.getRuntime().freeMemory());
	case 29:
	    Runtime.getRuntime().gc();
	    return null;
	case 30:
	    ((LContext) lcontext).canvas.clearall(lcontext);
	    return null;
	case 31:
	    ((LContext) lcontext).canvas.requestFocus();
	    return null;
	case 32:
	    ((LContext) lcontext).canvas.setMessage(Logo.aString(objects[0],
								 lcontext));
	    return null;
	case 33:
	    return prim_getSensorValue(objects[0], lcontext);
	case 34:
	    return new Boolean(((LContext) lcontext).autostart);
	case 35:
	    return new Boolean(objects[0] instanceof QuotedSymbol);
	case 36:
	    return ((QuotedSymbol) (QuotedSymbol) objects[0]).sym;
	default:
	    return null;
	}
    }
    
    static synchronized LContext startup(String string, String string_0_,
					 Container container, boolean bool) {
	LContext lcontext = new LContext();
	Logo.setupPrims(primclasses, lcontext);
	((LContext) lcontext).codeBase = string;
	((LContext) lcontext).projectURL = string_0_;
	((LContext) lcontext).autostart = bool;
	PlayerCanvas playercanvas = new PlayerCanvas();
	((PlayerCanvas) playercanvas).lc = lcontext;
	((LContext) lcontext).canvas = playercanvas;
	Skin.readSkin(playercanvas);
	SoundPlayer.startPlayer();
	container.add(playercanvas, "Center");
	LogoCommandRunner.startLogoThread("load \"startup startup", lcontext);
	return lcontext;
    }
    
    static synchronized void shutdown(LContext lcontext) {
	if (lcontext != null) {
	    LogoCommandRunner.stopLogoThread(lcontext);
	    ((LContext) lcontext).canvas.clearall(lcontext);
	}
	SoundPlayer.stopSoundsForApplet(lcontext);
	sensorValues = new int[16];
	for (int i = 3; i < 8; i++)
	    sensorValues[i] = 1000;
    }
    
    static void stopMIDINotes() {
	if (midiSynth != null) {
	    MidiChannel[] midichannels = midiSynth.getChannels();
	    for (int i = 0; i < midichannels.length; i++) {
		if (midichannels[i] != null) {
		    midichannels[i].allNotesOff();
		    midichannels[i].programChange(0);
		}
	    }
	}
    }
    
    Object prim_drawrect(Object object, Object object_1_, Object object_2_,
			 Object object_3_, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	int i_4_ = Logo.anInt(object_1_, lcontext);
	int i_5_ = Logo.anInt(object_2_, lcontext);
	int i_6_ = Logo.anInt(object_3_, lcontext);
	((LContext) lcontext).canvas.drawRect(i, i_4_, i_5_, i_6_);
	return null;
    }
    
    Object prim_setstage(Object object, LContext lcontext) {
	if (!(object instanceof Sprite))
	    return null;
	Sprite sprite = (Sprite) object;
	((Sprite) sprite).x = ((Sprite) sprite).y = 0.0;
	((PlayerCanvas) ((LContext) lcontext).canvas).stage = sprite;
	return null;
    }
    
    Object prim_readprojfile(Object object, LContext lcontext) {
	Object[] objects = new Object[0];
	((LContext) lcontext).canvas.startLoading();
	FileInputStream fileinputstream;
	try {
	    fileinputstream = new FileInputStream(object.toString());
	} catch (FileNotFoundException filenotfoundexception) {
	    Logo.error("File not found: " + object, lcontext);
	    return objects;
	}
	((LContext) lcontext).canvas.loadingProgress(0.5);
	Object[][] objects_7_;
	try {
	    ObjReader objreader = new ObjReader(fileinputstream);
	    objects_7_ = objreader.readObjects(lcontext);
	    fileinputstream.close();
	} catch (IOException ioexception) {
	    ioexception.printStackTrace();
	    return objects;
	}
	((LContext) lcontext).canvas.stopLoading();
	return objects_7_;
    }
    
    Object prim_readprojurl(LContext lcontext) {
	Object[] objects = new Object[0];
	if (((LContext) lcontext).projectURL == null)
	    return objects;
	URL url;
	try {
	    url = new URL(((LContext) lcontext).projectURL);
	} catch (MalformedURLException malformedurlexception) {
	    Logo.error("Bad project URL: " + ((LContext) lcontext).projectURL,
		       lcontext);
	    return objects;
	}
	Object[][] objects_8_;
	try {
	    ((LContext) lcontext).canvas.startLoading();
	    URLConnection urlconnection = url.openConnection();
	    urlconnection.connect();
	    int i = urlconnection.getContentLength();
	    byte[] is = new byte[i];
	    InputStream inputstream = urlconnection.getInputStream();
	    int i_9_ = 0;
	    int i_10_ = 0;
	    while (i_9_ >= 0 && i_10_ < i) {
		i_9_ = inputstream.read(is, i_10_, i - i_10_);
		if (i_9_ > 0) {
		    i_10_ += i_9_;
		    ((LContext) lcontext).canvas
			.loadingProgress((double) i_10_ / (double) i);
		} else {
		    try {
			Thread.sleep(100L);
		    } catch (InterruptedException interruptedexception) {
			/* empty */
		    }
		}
	    }
	    inputstream.close();
	    ObjReader objreader = new ObjReader(new ByteArrayInputStream(is));
	    objects_8_ = objreader.readObjects(lcontext);
	} catch (IOException ioexception) {
	    Logo.error(("Problem reading project from URL: "
			+ ((LContext) lcontext).projectURL),
		       lcontext);
	    return objects;
	}
	((LContext) lcontext).canvas.stopLoading();
	return objects_8_;
    }
    
    Object prim_mouseclick(LContext lcontext) {
	if (((PlayerCanvas) ((LContext) lcontext).canvas).mouseclicks
		.isEmpty())
	    return new Object[0];
	return ((PlayerCanvas) ((LContext) lcontext).canvas).mouseclicks
		   .remove(0);
    }
    
    Object prim_keystroke(LContext lcontext) {
	if (((PlayerCanvas) ((LContext) lcontext).canvas).keystrokes.isEmpty())
	    return new Object[0];
	return ((PlayerCanvas) ((LContext) lcontext).canvas).keystrokes
		   .remove(0);
    }
    
    Object prim_keydown(Object object, LContext lcontext) {
	int i = Logo.anInt(object, lcontext);
	if (i > 255)
	    return new Boolean(false);
	return new Boolean(((PlayerCanvas) ((LContext) lcontext).canvas)
			   .keydown[i]);
    }
    
    Object prim_midisetinstrument(Object object, Object object_11_,
				  LContext lcontext) {
	init_midi(lcontext);
	if (midiSynth == null)
	    return null;
	int i = Logo.anInt(object, lcontext) - 1 & 0xf;
	int i_12_ = Logo.anInt(object_11_, lcontext) - 1 & 0x7f;
	midiSynth.getChannels()[i].programChange(i_12_);
	return null;
    }
    
    Object prim_midinoteon(Object object, Object object_13_, Object object_14_,
			   LContext lcontext) {
	init_midi(lcontext);
	if (midiSynth == null)
	    return null;
	int i = Logo.anInt(object, lcontext) - 1 & 0xf;
	int i_15_ = Logo.anInt(object_13_, lcontext) & 0x7f;
	int i_16_ = Logo.anInt(object_14_, lcontext) & 0x7f;
	midiSynth.getChannels()[i].noteOn(i_15_, i_16_);
	return null;
    }
    
    Object prim_midinoteoff(Object object, Object object_17_,
			    LContext lcontext) {
	init_midi(lcontext);
	if (midiSynth == null)
	    return null;
	int i = Logo.anInt(object, lcontext) - 1 & 0xf;
	int i_18_ = Logo.anInt(object_17_, lcontext) & 0x7f;
	midiSynth.getChannels()[i].noteOff(i_18_);
	return null;
    }
    
    Object prim_midicontrol(Object object, Object object_19_,
			    Object object_20_, LContext lcontext) {
	init_midi(lcontext);
	if (midiSynth == null)
	    return null;
	int i = Logo.anInt(object, lcontext) - 1 & 0xf;
	int i_21_ = Logo.anInt(object_19_, lcontext) & 0x7f;
	int i_22_ = Logo.anInt(object_20_, lcontext) & 0x7f;
	midiSynth.getChannels()[i].controlChange(i_21_, i_22_);
	return null;
    }
    
    void init_midi(LContext lcontext) {
	if (!midiSynthInitialized) {
	    midiSynthInitialized = true;
	    try {
		midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
		if (midiSynth.getDefaultSoundbank() == null) {
		    ((LContext) lcontext).canvas.setMessage
			("Reading sound bank from server. Please wait...");
		    if (lcontext != null) {
			/* empty */
		    }
		    URL url = new URL(((LContext) lcontext).codeBase
				      + "soundbank.gm");
		    Soundbank soundbank = MidiSystem.getSoundbank(url);
		    if (soundbank != null) {
			midiSynth.loadAllInstruments(soundbank);
			((LContext) lcontext).canvas.setMessage("");
		    } else {
			midiSynth.close();
			midiSynth = null;
		    }
		}
	    } catch (MidiUnavailableException midiunavailableexception) {
		midiunavailableexception.printStackTrace();
		midiSynth = null;
	    } catch (MalformedURLException malformedurlexception) {
		malformedurlexception.printStackTrace();
		midiSynth = null;
	    } catch (InvalidMidiDataException invalidmididataexception) {
		invalidmididataexception.printStackTrace();
		midiSynth = null;
	    } catch (IOException ioexception) {
		ioexception.printStackTrace();
		midiSynth = null;
	    } catch (AccessControlException accesscontrolexception) {
		accesscontrolexception.printStackTrace();
		midiSynth = null;
	    }
	    if (midiSynth != null) {
		MidiChannel[] midichannels = midiSynth.getChannels();
		for (int i = 0; i < midichannels.length; i++) {
		    if (midichannels[i] != null)
			midichannels[i].programChange(0);
		}
	    } else
		((LContext) lcontext).canvas.setMessage
		    ("No soundbank; note & drum commands disabled.");
	}
    }
    
    Object prim_jpegDecode(Object object, LContext lcontext) {
	if (!(object instanceof byte[]))
	    return null;
	byte[] is = (byte[]) object;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image = toolkit.createImage(is);
	MediaTracker mediatracker
	    = new MediaTracker(((LContext) lcontext).canvas);
	mediatracker.addImage(image, 0);
	try {
	    mediatracker.waitForID(0);
	} catch (InterruptedException interruptedexception) {
	    /* empty */
	}
	if (image == null)
	    return null;
	int i = image.getWidth(null);
	int i_23_ = image.getHeight(null);
	BufferedImage bufferedimage = new BufferedImage(i, i_23_, 2);
	Graphics graphics = bufferedimage.getGraphics();
	graphics.drawImage(image, 0, 0, i, i_23_, null);
	graphics.dispose();
	return bufferedimage;
    }
    
    Object prim_getSensorValue(Object object, LContext lcontext) {
	int i = Logo.anInt(object, lcontext) - 1;
	if (i < 0 || i > 15)
	    return new Double(123.0);
	return new Double((double) sensorValues[i] / 10.0);
    }
}
