/* PlayingSound - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

class PlayingSound
{
    LContext owner;
    ScratchSound snd;
    Sprite sprite;
    SourceDataLine line;
    int i;
    /*synthetic*/ static Class class$javax$sound$sampled$SourceDataLine;
    
    PlayingSound(ScratchSound scratchsound, Sprite sprite) {
	((PlayingSound) this).snd = scratchsound;
	((PlayingSound) this).sprite = sprite;
    }
    
    void startPlaying(LContext lcontext) {
	((PlayingSound) this).owner = lcontext;
	((PlayingSound) this).i = 0;
	if (((PlayingSound) this).line == null)
	    openLine();
	writeSomeSamples();
    }
    
    boolean isPlaying() {
	if (((PlayingSound) this).line == null)
	    return false;
	return (((PlayingSound) this).line.getFramePosition()
		< (((ScratchSound) ((PlayingSound) this).snd).samples.length
		   / 2));
    }
    
    void writeSomeSamples() {
	if (((PlayingSound) this).line != null) {
	    int i = Math.min(((PlayingSound) this).line.available(),
			     (((ScratchSound) ((PlayingSound) this).snd)
			      .samples).length - ((PlayingSound) this).i);
	    if (i > 0) {
		if (!((PlayingSound) this).line.isRunning())
		    ((PlayingSound) this).line.start();
		int i_0_
		    = (((PlayingSound) this).line.write
		       (((ScratchSound) ((PlayingSound) this).snd).samples,
			((PlayingSound) this).i, i));
		((PlayingSound) this).i += i_0_;
	    }
	}
    }
    
    void openLine() {
	try {
	    AudioFormat audioformat
		= new AudioFormat((float) ((ScratchSound)
					   ((PlayingSound) this).snd).rate,
				  16, 1, true, true);
	    ((PlayingSound) this).line
		= ((SourceDataLine)
		   (AudioSystem.getLine
		    (new DataLine.Info
		     ((class$javax$sound$sampled$SourceDataLine == null
		       ? (class$javax$sound$sampled$SourceDataLine
			  = class$("javax.sound.sampled.SourceDataLine"))
		       : class$javax$sound$sampled$SourceDataLine),
		      audioformat))));
	    ((PlayingSound) this).line.open(audioformat);
	} catch (LineUnavailableException lineunavailableexception) {
	    ((PlayingSound) this).line = null;
	    return;
	}
	((PlayingSound) this).line.start();
    }
    
    void closeLine() {
	if (((PlayingSound) this).line != null) {
	    ((PlayingSound) this).line.close();
	    ((PlayingSound) this).line = null;
	}
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
