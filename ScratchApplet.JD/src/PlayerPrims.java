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
import java.util.Vector;
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
  static int[] sensorValues = { 0, 0, 0, 1000, 1000, 1000, 1000, 1000, 0, 0, 0, 0, 0, 0, 0, 0 };

  static final String[] primlist = { "redrawall", "0", "redraw", "0", "drawrect", "4", "stage", "0", "setstage", "1", "sprites", "0", "setsprites", "1", "readprojfile", "1", "readprojurl", "0", "applet?", "0", "string?", "1", "sprite?", "1", "color?", "1", "mouseX", "0", "mouseY", "0", "mouseIsDown", "0", "mouseClick", "0", "keystroke", "0", "keydown?", "1", "clearkeys", "0", "midisetinstrument", "2", "midinoteon", "3", "midinoteoff", "2", "midicontrol", "3", "updatePenTrails", "0", "soundLevel", "0", "jpegDecode", "1", "memTotal", "0", "memFree", "0", "gc", "0", "clearall", "0", "requestFocus", "0", "setMessage", "1", "getSensorValue", "1", "autostart?", "0", "quoted?", "1", "unquote", "1" };

  static final String[] primclasses = { "SystemPrims", "MathPrims", "ControlPrims", "DefiningPrims", "WordListPrims", "FilePrims", "PlayerPrims", "SpritePrims" };

  public String[] primlist()
  {
    return primlist;
  }
  public Object dispatch(int paramInt, Object[] paramArrayOfObject, LContext paramLContext) {
    switch (paramInt) { case 0:
      paramLContext.canvas.redraw_all(); return null;
    case 1:
      paramLContext.canvas.redraw_invalid(); return null;
    case 2:
      return prim_drawrect(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramArrayOfObject[3], paramLContext);
    case 3:
      return paramLContext.canvas.stage == null ? new Object[0] : paramLContext.canvas.stage;
    case 4:
      return prim_setstage(paramArrayOfObject[0], paramLContext);
    case 5:
      return paramLContext.canvas.sprites;
    case 6:
      paramLContext.canvas.sprites = ((Object[])paramArrayOfObject[0]); return null;
    case 7:
      return prim_readprojfile(paramArrayOfObject[0], paramLContext);
    case 8:
      return prim_readprojurl(paramLContext);
    case 9:
      return new Boolean(true);
    case 10:
      return new Boolean(((paramArrayOfObject[0] instanceof String)) || ((paramArrayOfObject[0] instanceof Symbol)));
    case 11:
      return new Boolean(paramArrayOfObject[0] instanceof Sprite);
    case 12:
      return new Boolean(paramArrayOfObject[0] instanceof Color);
    case 13:
      return new Double(paramLContext.canvas.mouseX);
    case 14:
      return new Double(paramLContext.canvas.mouseY);
    case 15:
      return new Boolean(paramLContext.canvas.mouseIsDown);
    case 16:
      return prim_mouseclick(paramLContext);
    case 17:
      return prim_keystroke(paramLContext);
    case 18:
      return prim_keydown(paramArrayOfObject[0], paramLContext);
    case 19:
      paramLContext.canvas.clearkeys(); return null;
    case 20:
      return prim_midisetinstrument(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 21:
      return prim_midinoteon(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext);
    case 22:
      return prim_midinoteoff(paramArrayOfObject[0], paramArrayOfObject[1], paramLContext);
    case 23:
      return prim_midicontrol(paramArrayOfObject[0], paramArrayOfObject[1], paramArrayOfObject[2], paramLContext);
    case 24:
      paramLContext.canvas.updatePenTrails(); return null;
    case 25:
      return new Double(paramLContext.canvas.soundLevel());
    case 26:
      return prim_jpegDecode(paramArrayOfObject[0], paramLContext);
    case 27:
      return new Double(Runtime.getRuntime().totalMemory());
    case 28:
      return new Double(Runtime.getRuntime().freeMemory());
    case 29:
      Runtime.getRuntime().gc(); return null;
    case 30:
      paramLContext.canvas.clearall(paramLContext); return null;
    case 31:
      paramLContext.canvas.requestFocus(); return null;
    case 32:
      paramLContext.canvas.setMessage(Logo.aString(paramArrayOfObject[0], paramLContext)); return null;
    case 33:
      return prim_getSensorValue(paramArrayOfObject[0], paramLContext);
    case 34:
      return new Boolean(paramLContext.autostart);
    case 35:
      return new Boolean(paramArrayOfObject[0] instanceof QuotedSymbol);
    case 36:
      return ((QuotedSymbol)paramArrayOfObject[0]).sym;
    }
    return null;
  }

  static synchronized LContext startup(String paramString1, String paramString2, Container paramContainer, boolean paramBoolean)
  {
    LContext localLContext = new LContext();
    Logo.setupPrims(primclasses, localLContext);
    localLContext.codeBase = paramString1;
    localLContext.projectURL = paramString2;
    localLContext.autostart = paramBoolean;
    PlayerCanvas localPlayerCanvas = new PlayerCanvas();
    localPlayerCanvas.lc = localLContext;
    localLContext.canvas = localPlayerCanvas;

    Skin.readSkin(localPlayerCanvas);
    SoundPlayer.startPlayer();
    paramContainer.add(localPlayerCanvas, "Center");
    LogoCommandRunner.startLogoThread("load \"startup startup", localLContext);
    return localLContext;
  }

  static synchronized void shutdown(LContext paramLContext) {
    if (paramLContext != null) {
      LogoCommandRunner.stopLogoThread(paramLContext);
      paramLContext.canvas.clearall(paramLContext);
    }
    SoundPlayer.stopSoundsForApplet(paramLContext);
    sensorValues = new int[16];
    for (int i = 3; i < 8; i++) sensorValues[i] = 1000; 
  }

  static void stopMIDINotes()
  {
    if (midiSynth == null) return;
    MidiChannel[] arrayOfMidiChannel = midiSynth.getChannels();
    for (int i = 0; i < arrayOfMidiChannel.length; i++)
      if (arrayOfMidiChannel[i] != null) {
        arrayOfMidiChannel[i].allNotesOff();
        arrayOfMidiChannel[i].programChange(0);
      }
  }

  Object prim_drawrect(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, LContext paramLContext)
  {
    int i = Logo.anInt(paramObject1, paramLContext);
    int j = Logo.anInt(paramObject2, paramLContext);
    int k = Logo.anInt(paramObject3, paramLContext);
    int m = Logo.anInt(paramObject4, paramLContext);
    paramLContext.canvas.drawRect(i, j, k, m);
    return null;
  }

  Object prim_setstage(Object paramObject, LContext paramLContext) {
    if (!(paramObject instanceof Sprite)) return null;
    Sprite localSprite = (Sprite)paramObject;
    localSprite.x = (localSprite.y = 0.0D);
    paramLContext.canvas.stage = localSprite;
    return null;
  }
  Object prim_readprojfile(Object paramObject, LContext paramLContext) {
    Object[] arrayOfObject = new Object[0];

    paramLContext.canvas.startLoading();
    FileInputStream localFileInputStream;
    try { localFileInputStream = new FileInputStream(paramObject.toString());
    } catch (FileNotFoundException localFileNotFoundException) {
      Logo.error("File not found: " + paramObject, paramLContext);
      return arrayOfObject; } 
paramLContext.canvas.loadingProgress(0.5D);
    Object[][] arrayOfObject1;
    try { ObjReader localObjReader = new ObjReader(localFileInputStream);
      arrayOfObject1 = localObjReader.readObjects(paramLContext);
      localFileInputStream.close();
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
      return arrayOfObject;
    }
    paramLContext.canvas.stopLoading();
    return arrayOfObject1;
  }

  Object prim_readprojurl(LContext paramLContext) {
    Object[] arrayOfObject = new Object[0];

    if (paramLContext.projectURL == null) return arrayOfObject; URL localURL;
    try { localURL = new URL(paramLContext.projectURL);
    } catch (MalformedURLException localMalformedURLException) {
      Logo.error("Bad project URL: " + paramLContext.projectURL, paramLContext);
      return arrayOfObject;
    }Object[][] arrayOfObject1;
    try {
      paramLContext.canvas.startLoading();
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.connect();
      int i = localURLConnection.getContentLength();
      byte[] arrayOfByte = new byte[i];
      InputStream localInputStream = localURLConnection.getInputStream();
      int j = 0; int k = 0;

      while ((j >= 0) && (k < i)) {
        j = localInputStream.read(arrayOfByte, k, i - k);
        if (j > 0) {
          k += j;
          paramLContext.canvas.loadingProgress(k / i); continue;
        }try {
          Thread.sleep(100L); } catch (InterruptedException localInterruptedException) {
        }
      }
      localInputStream.close();

      ObjReader localObjReader = new ObjReader(new ByteArrayInputStream(arrayOfByte));
      arrayOfObject1 = localObjReader.readObjects(paramLContext);
    } catch (IOException localIOException) {
      Logo.error("Problem reading project from URL: " + paramLContext.projectURL, paramLContext);
      return arrayOfObject;
    }
    paramLContext.canvas.stopLoading();
    return arrayOfObject1;
  }

  Object prim_mouseclick(LContext paramLContext) {
    if (paramLContext.canvas.mouseclicks.isEmpty()) return new Object[0];
    return paramLContext.canvas.mouseclicks.remove(0);
  }

  Object prim_keystroke(LContext paramLContext) {
    if (paramLContext.canvas.keystrokes.isEmpty()) return new Object[0];
    return paramLContext.canvas.keystrokes.remove(0);
  }

  Object prim_keydown(Object paramObject, LContext paramLContext) {
    int i = Logo.anInt(paramObject, paramLContext);
    if (i > 255) return new Boolean(false);
    return new Boolean(paramLContext.canvas.keydown[i]);
  }

  Object prim_midisetinstrument(Object paramObject1, Object paramObject2, LContext paramLContext) {
    init_midi(paramLContext);
    if (midiSynth == null) return null;
    int i = Logo.anInt(paramObject1, paramLContext) - 1 & 0xF;
    int j = Logo.anInt(paramObject2, paramLContext) - 1 & 0x7F;
    midiSynth.getChannels()[i].programChange(j);
    return null;
  }

  Object prim_midinoteon(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    init_midi(paramLContext);
    if (midiSynth == null) return null;
    int i = Logo.anInt(paramObject1, paramLContext) - 1 & 0xF;
    int j = Logo.anInt(paramObject2, paramLContext) & 0x7F;
    int k = Logo.anInt(paramObject3, paramLContext) & 0x7F;
    midiSynth.getChannels()[i].noteOn(j, k);
    return null;
  }

  Object prim_midinoteoff(Object paramObject1, Object paramObject2, LContext paramLContext) {
    init_midi(paramLContext);
    if (midiSynth == null) return null;
    int i = Logo.anInt(paramObject1, paramLContext) - 1 & 0xF;
    int j = Logo.anInt(paramObject2, paramLContext) & 0x7F;
    midiSynth.getChannels()[i].noteOff(j);
    return null;
  }

  Object prim_midicontrol(Object paramObject1, Object paramObject2, Object paramObject3, LContext paramLContext) {
    init_midi(paramLContext);
    if (midiSynth == null) return null;
    int i = Logo.anInt(paramObject1, paramLContext) - 1 & 0xF;
    int j = Logo.anInt(paramObject2, paramLContext) & 0x7F;
    int k = Logo.anInt(paramObject3, paramLContext) & 0x7F;
    midiSynth.getChannels()[i].controlChange(j, k);
    return null;
  }

  void init_midi(LContext paramLContext)
  {
    if (midiSynthInitialized) return;
    midiSynthInitialized = true;
    try {
      midiSynth = MidiSystem.getSynthesizer();
      midiSynth.open();
      if (midiSynth.getDefaultSoundbank() == null) {
        paramLContext.canvas.setMessage("Reading sound bank from server. Please wait...");
        URL localURL = new URL(paramLContext.codeBase + "soundbank.gm");

        Soundbank localSoundbank = MidiSystem.getSoundbank(localURL);
        if (localSoundbank != null) {
          midiSynth.loadAllInstruments(localSoundbank);
          paramLContext.canvas.setMessage("");
        } else {
          midiSynth.close();
          midiSynth = null;
        }
      }
    } catch (MidiUnavailableException localMidiUnavailableException) {
      localMidiUnavailableException.printStackTrace();
      midiSynth = null;
    } catch (MalformedURLException localMalformedURLException) {
      localMalformedURLException.printStackTrace();
      midiSynth = null;
    } catch (InvalidMidiDataException localInvalidMidiDataException) {
      localInvalidMidiDataException.printStackTrace();
      midiSynth = null;
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
      midiSynth = null;
    } catch (AccessControlException localAccessControlException) {
      localAccessControlException.printStackTrace();
      midiSynth = null;
    }
    if (midiSynth != null) {
      MidiChannel[] arrayOfMidiChannel = midiSynth.getChannels();
      for (int i = 0; i < arrayOfMidiChannel.length; i++) {
        if (arrayOfMidiChannel[i] == null) continue; arrayOfMidiChannel[i].programChange(0);
      }
    }
    paramLContext.canvas.setMessage("No soundbank; note & drum commands disabled.");
  }

  Object prim_jpegDecode(Object paramObject, LContext paramLContext)
  {
    if (!(paramObject instanceof byte[])) return null;
    byte[] arrayOfByte = (byte[])paramObject;
    Toolkit localToolkit = Toolkit.getDefaultToolkit();
    Image localImage = localToolkit.createImage(arrayOfByte);

    MediaTracker localMediaTracker = new MediaTracker(paramLContext.canvas);
    localMediaTracker.addImage(localImage, 0);
    try { localMediaTracker.waitForID(0); } catch (InterruptedException localInterruptedException) {
    }if (localImage == null) return null;

    int i = localImage.getWidth(null);
    int j = localImage.getHeight(null);
    BufferedImage localBufferedImage = new BufferedImage(i, j, 2);
    Graphics localGraphics = localBufferedImage.getGraphics();
    localGraphics.drawImage(localImage, 0, 0, i, j, null);
    localGraphics.dispose();
    return localBufferedImage;
  }

  Object prim_getSensorValue(Object paramObject, LContext paramLContext) {
    int i = Logo.anInt(paramObject, paramLContext) - 1;
    if ((i < 0) || (i > 15)) return new Double(123.0D);
    return new Double(sensorValues[i] / 10.0D);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     PlayerPrims
 * JD-Core Version:    0.6.0
 */