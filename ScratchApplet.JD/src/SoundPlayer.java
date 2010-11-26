import java.util.Iterator;
import java.util.Vector;

class SoundPlayer
  implements Runnable
{
  static Vector activeSounds = new Vector();
  static Thread sndThread;

  static synchronized Object startSound(Object paramObject, Sprite paramSprite, LContext paramLContext)
  {
    if (!(paramObject instanceof ScratchSound)) {
      Logo.error("argument of startSound must be a ScratchSound", paramLContext);
      return new Object[0];
    }
    Object[] arrayOfObject = activeSounds.toArray();
    for (int i = 0; i < arrayOfObject.length; i++) {
      PlayingSound localPlayingSound2 = (PlayingSound)arrayOfObject[i];
      if ((localPlayingSound2.snd != (ScratchSound)paramObject) || (localPlayingSound2.sprite != paramSprite))
        continue;
      localPlayingSound2.closeLine();
      activeSounds.remove(localPlayingSound2);
    }

    PlayingSound localPlayingSound1 = new PlayingSound((ScratchSound)paramObject, paramSprite);
    localPlayingSound1.startPlaying(paramLContext);
    activeSounds.add(localPlayingSound1);
    return localPlayingSound1;
  }

  static synchronized boolean isSoundPlaying(Object paramObject) {
    if (!(paramObject instanceof PlayingSound)) return false;
    return ((PlayingSound)paramObject).isPlaying();
  }

  static synchronized void stopSound(Object paramObject) {
    if (!(paramObject instanceof PlayingSound)) return;
    ((PlayingSound)paramObject).closeLine();
    activeSounds.remove(paramObject);
  }

  static synchronized void stopSoundsForApplet(LContext paramLContext) {
    PlayerPrims.stopMIDINotes();
    Vector localVector = new Vector();
    for (Iterator localIterator = activeSounds.iterator(); localIterator.hasNext(); ) {
      PlayingSound localPlayingSound = (PlayingSound)localIterator.next();
      if (localPlayingSound.owner == paramLContext) {
        localPlayingSound.closeLine(); continue;
      }
      localVector.addElement(localPlayingSound);
    }

    activeSounds = localVector;
  }

  static synchronized void updateActiveSounds() {
    Vector localVector = new Vector();
    for (Iterator localIterator = activeSounds.iterator(); localIterator.hasNext(); ) {
      PlayingSound localPlayingSound = (PlayingSound)localIterator.next();
      if (localPlayingSound.isPlaying()) {
        localPlayingSound.writeSomeSamples();
        localVector.addElement(localPlayingSound); continue;
      }
      localPlayingSound.closeLine();
    }

    activeSounds = localVector;
  }

  static synchronized void startPlayer() {
    sndThread = new Thread(new SoundPlayer(), "SoundPlayer");
    sndThread.setPriority(10);
    sndThread.start();
  }

  public void run() {
    Thread localThread = Thread.currentThread();
    while (sndThread == localThread) {
      updateActiveSounds();
      try { Thread.sleep(10L);
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     SoundPlayer
 * JD-Core Version:    0.6.0
 */