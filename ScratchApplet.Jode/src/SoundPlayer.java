/* SoundPlayer - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Iterator;
import java.util.Vector;

class SoundPlayer implements Runnable
{
    static Vector activeSounds = new Vector();
    static Thread sndThread;
    
    static synchronized Object startSound(Object object, Sprite sprite,
					  LContext lcontext) {
	if (!(object instanceof ScratchSound)) {
	    Logo.error("argument of startSound must be a ScratchSound",
		       lcontext);
	    return new Object[0];
	}
	Object[] objects = activeSounds.toArray();
	for (int i = 0; i < objects.length; i++) {
	    PlayingSound playingsound = (PlayingSound) objects[i];
	    if (((PlayingSound) playingsound).snd == (ScratchSound) object
		&& ((PlayingSound) playingsound).sprite == sprite) {
		playingsound.closeLine();
		activeSounds.remove(playingsound);
	    }
	}
	PlayingSound playingsound
	    = new PlayingSound((ScratchSound) object, sprite);
	playingsound.startPlaying(lcontext);
	activeSounds.add(playingsound);
	return playingsound;
    }
    
    static synchronized boolean isSoundPlaying(Object object) {
	if (!(object instanceof PlayingSound))
	    return false;
	return ((PlayingSound) object).isPlaying();
    }
    
    static synchronized void stopSound(Object object) {
	if (object instanceof PlayingSound) {
	    ((PlayingSound) object).closeLine();
	    activeSounds.remove(object);
	}
    }
    
    static synchronized void stopSoundsForApplet(LContext lcontext) {
	PlayerPrims.stopMIDINotes();
	Vector vector = new Vector();
	Iterator iterator = activeSounds.iterator();
	while (iterator.hasNext()) {
	    PlayingSound playingsound = (PlayingSound) iterator.next();
	    if (((PlayingSound) playingsound).owner == lcontext)
		playingsound.closeLine();
	    else
		vector.addElement(playingsound);
	}
	activeSounds = vector;
    }
    
    static synchronized void updateActiveSounds() {
	Vector vector = new Vector();
	Iterator iterator = activeSounds.iterator();
	while (iterator.hasNext()) {
	    PlayingSound playingsound = (PlayingSound) iterator.next();
	    if (playingsound.isPlaying()) {
		playingsound.writeSomeSamples();
		vector.addElement(playingsound);
	    } else
		playingsound.closeLine();
	}
	activeSounds = vector;
    }
    
    static synchronized void startPlayer() {
	sndThread = new Thread(new SoundPlayer(), "SoundPlayer");
	sndThread.setPriority(10);
	sndThread.start();
    }
    
    public void run() {
	Thread thread = Thread.currentThread();
	while (sndThread == thread) {
	    updateActiveSounds();
	    try {
		Thread.sleep(10L);
	    } catch (InterruptedException interruptedexception) {
		/* empty */
	    }
	}
    }
}
