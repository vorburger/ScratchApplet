// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ADPCMDecoder.java

import java.io.*;

class ADPCMDecoder
{

    ADPCMDecoder(byte abyte0[], int i)
    {
        this(((InputStream) (new ByteArrayInputStream(abyte0))), i);
    }

    ADPCMDecoder(InputStream inputstream, int i)
    {
        in = new DataInputStream(inputstream);
        bitsPerSample = i;
        switch(bitsPerSample)
        {
        case 2: // '\002'
            indexTable = indices2bit;
            break;

        case 3: // '\003'
            indexTable = indices3bit;
            break;

        case 4: // '\004'
            indexTable = indices4bit;
            break;

        case 5: // '\005'
            indexTable = indices5bit;
            break;
        }
    }

    int[] decode(int i)
    {
        int j = 1 << bitsPerSample - 1;
        int k = j - 1;
        int l = j >> 1;
        int ai[] = new int[i];
        for(int i2 = 0; i2 < i; i2++)
        {
            int i1 = nextBits();
            int j1 = stepSizeTable[index];
            int k1 = 0;
            for(int l1 = l; l1 > 0; l1 >>= 1)
            {
                if((i1 & l1) != 0)
                    k1 += j1;
                j1 >>= 1;
            }

            k1 += j1;
            predicted += (i1 & j) == 0 ? k1 : -k1;
            if(predicted > 32767)
                predicted = 32767;
            if(predicted < -32768)
                predicted = -32768;
            ai[i2] = predicted;
            index += indexTable[i1 & k];
            if(index < 0)
                index = 0;
            if(index > 88)
                index = 88;
        }

        return ai;
    }

    int nextBits()
    {
        int i = 0;
        int j = bitsPerSample;
        do
        {
            int k = j - bitPosition;
            i += k >= 0 ? currentByte << k : currentByte >> -k;
            if(k > 0)
            {
                j -= bitPosition;
                try
                {
                    currentByte = in.readUnsignedByte();
                }
                catch(IOException ioexception)
                {
                    currentByte = -1;
                }
                bitPosition = 8;
                if(currentByte < 0)
                {
                    bitPosition = 0;
                    return i;
                }
            } else
            {
                bitPosition -= j;
                currentByte = currentByte & 255 >> 8 - bitPosition;
                return i;
            }
        } while(true);
    }

    static final int stepSizeTable[] = {
        7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 
        19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 
        50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 
        130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 
        337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 
        876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 
        2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 
        5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 
        15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767
    };
    static final int indices2bit[] = {
        -1, 2
    };
    static final int indices3bit[] = {
        -1, -1, 2, 4
    };
    static final int indices4bit[] = {
        -1, -1, -1, -1, 2, 4, 6, 8
    };
    static final int indices5bit[] = {
        -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 
        4, 6, 8, 10, 13, 16
    };
    DataInputStream in;
    int bitsPerSample;
    int currentByte;
    int bitPosition;
    int indexTable[];
    int predicted;
    int index;

}
