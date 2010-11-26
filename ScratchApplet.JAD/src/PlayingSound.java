// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerPrims.java

import javax.sound.sampled.*;

class PlayingSound
{

    PlayingSound(ScratchSound scratchsound, Sprite sprite1)
    {
        snd = scratchsound;
        sprite = sprite1;
    }

    void startPlaying(LContext lcontext)
    {
        owner = lcontext;
        i = 0;
        if(line == null)
            openLine();
        writeSomeSamples();
    }

    boolean isPlaying()
    {
        if(line == null)
            return false;
        else
            return line.getFramePosition() < snd.samples.length / 2;
    }

    void writeSomeSamples()
    {
        if(line == null)
            return;
        int j = Math.min(line.available(), snd.samples.length - i);
        if(j > 0)
        {
            if(!line.isRunning())
                line.start();
            int k = line.write(snd.samples, i, j);
            i += k;
        }
    }

    void openLine()
    {
        try
        {
            AudioFormat audioformat = new AudioFormat(snd.rate, 16, 1, true, true);
            line = (SourceDataLine)AudioSystem.getLine(new javax.sound.sampled.DataLine.Info(javax.sound.sampled.SourceDataLine.class, audioformat));
            line.open(audioformat);
        }
        catch(LineUnavailableException lineunavailableexception)
        {
            line = null;
            return;
        }
        line.start();
    }

    void closeLine()
    {
        if(line != null)
        {
            line.close();
            line = null;
        }
    }

    LContext owner;
    ScratchSound snd;
    Sprite sprite;
    SourceDataLine line;
    int i;
}
