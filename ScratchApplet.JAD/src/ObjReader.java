// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjReader.java

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

class ObjReader
{

    ObjReader(InputStream inputstream)
    {
        s = new DataInputStream(inputstream);
    }

    Object[][] readObjects(LContext lcontext)
        throws IOException
    {
        readInfo();
        readObjTable(lcontext);
        return objTable;
    }

    Hashtable readInfo()
        throws IOException
    {
        byte abyte0[] = new byte[10];
        s.read(abyte0);
        if(!"ScratchV01".equals(new String(abyte0)) && !"ScratchV02".equals(new String(abyte0)))
            throw new IOException();
        int i = s.readInt();
        readObjTable(null);
        Object aobj[] = (Object[])objTable[0][0];
        Hashtable hashtable = new Hashtable(aobj.length);
        for(int j = 0; j < aobj.length - 1; j += 2)
            hashtable.put(aobj[j], aobj[j + 1]);

        return hashtable;
    }

    void readObjTable(LContext lcontext)
        throws IOException
    {
        byte abyte0[] = new byte[4];
        s.read(abyte0);
        if(!"ObjS".equals(new String(abyte0)) || s.readByte() != 1)
            throw new IOException();
        s.read(abyte0);
        if(!"Stch".equals(new String(abyte0)) || s.readByte() != 1)
            throw new IOException();
        int i = s.readInt();
        objTable = new Object[i][];
        for(int j = 0; j < i; j++)
            objTable[j] = readObj();

        createSpritesAndWatchers(lcontext);
        buildImagesAndSounds();
        fixSounds();
        resolveReferences();
        uncompressMedia();
    }

    Object[] readObj()
        throws IOException
    {
        int i = s.readUnsignedByte();
        Object aobj[];
        if(i < 99)
        {
            aobj = new Object[2];
            aobj[0] = readFixedFormat(i);
            aobj[1] = new Integer(i);
        } else
        {
            int j = s.readUnsignedByte();
            int k = s.readUnsignedByte();
            aobj = new Object[3 + k];
            aobj[0] = ((Object) (empty));
            aobj[1] = new Integer(i);
            aobj[2] = new Integer(j);
            for(int l = 3; l < aobj.length; l++)
                aobj[l] = readField();

        }
        return aobj;
    }

    Object readField()
        throws IOException
    {
        int i = s.readUnsignedByte();
        if(i == 99)
        {
            int j = s.readUnsignedByte() << 16;
            j += s.readUnsignedByte() << 8;
            j += s.readUnsignedByte();
            return new Ref(j);
        } else
        {
            return readFixedFormat(i);
        }
    }

    Object readFixedFormat(int i)
        throws IOException
    {
        i;
        JVM INSTR tableswitch 1 35: default 788
    //                   1 156
    //                   2 160
    //                   3 164
    //                   4 168
    //                   5 183
    //                   6 198
    //                   7 198
    //                   8 276
    //                   9 291
    //                   10 291
    //                   11 378
    //                   12 403
    //                   13 430
    //                   14 475
    //                   15 788
    //                   16 788
    //                   17 788
    //                   18 788
    //                   19 788
    //                   20 521
    //                   21 521
    //                   22 521
    //                   23 521
    //                   24 564
    //                   25 564
    //                   26 788
    //                   27 788
    //                   28 788
    //                   29 788
    //                   30 609
    //                   31 609
    //                   32 674
    //                   33 699
    //                   34 740
    //                   35 740;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L7 _L8 _L9 _L9 _L10 _L11 _L12 _L13 _L1 _L1 _L1 _L1 _L1 _L14 _L14 _L14 _L14 _L15 _L15 _L1 _L1 _L1 _L1 _L16 _L16 _L17 _L18 _L19 _L19
_L2:
        return ((Object) (empty));
_L3:
        return Boolean.TRUE;
_L4:
        return Boolean.FALSE;
_L5:
        return new Integer(s.readInt());
_L6:
        return new Integer(s.readShort());
_L7:
        double d = 0.0D;
        double d1 = 1.0D;
        short word0 = s.readShort();
        for(int i2 = 0; i2 < word0; i2++)
        {
            int k2 = s.readUnsignedByte();
            d += d1 * (double)k2;
            d1 *= 256D;
        }

        if(i == 7)
            d = -d;
        return new Double(d);
_L8:
        return new Double(s.readDouble());
_L9:
        byte abyte0[];
        int j = s.readInt();
        s.read(abyte0 = new byte[j]);
        for(int j2 = 0; j2 < j; j2++)
            if(abyte0[j2] < 0)
                abyte0[j2] = macRomanToISOLatin[abyte0[j2] + 128];

        return new String(abyte0, "ISO-8859-1");
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        return new String(abyte0);
_L10:
        int k = s.readInt();
        s.read(abyte0 = new byte[k]);
        return abyte0;
_L11:
        int l = s.readInt();
        s.read(abyte0 = new byte[2 * l]);
        return abyte0;
_L12:
        int i1 = s.readInt();
        int ai[] = new int[i1];
        for(int l2 = 0; l2 < ai.length; l2++)
            ai[l2] = s.readInt();

        return ai;
_L13:
        int j1 = s.readInt();
        s.read(abyte0 = new byte[j1]);
        return new String(abyte0, "UTF-8");
        UnsupportedEncodingException unsupportedencodingexception1;
        unsupportedencodingexception1;
        return new String(abyte0);
_L14:
        int k1 = s.readInt();
        Object aobj[] = new Object[k1];
        for(int i3 = 0; i3 < aobj.length; i3++)
            aobj[i3] = readField();

        return ((Object) (aobj));
_L15:
        int l1 = s.readInt();
        Object aobj1[] = new Object[2 * l1];
        for(int j3 = 0; j3 < aobj1.length; j3++)
            aobj1[j3] = readField();

        return ((Object) (aobj1));
_L16:
        int k3 = s.readInt();
        int l3 = 255;
        if(i == 31)
            l3 = s.readUnsignedByte();
        return new Color(k3 >> 22 & 0xff, k3 >> 12 & 0xff, k3 >> 2 & 0xff, l3);
_L17:
        Object aobj2[] = new Object[2];
        aobj2[0] = readField();
        aobj2[1] = readField();
        return ((Object) (aobj2));
_L18:
        Object aobj3[] = new Object[4];
        aobj3[0] = readField();
        aobj3[1] = readField();
        aobj3[2] = readField();
        aobj3[3] = readField();
        return ((Object) (aobj3));
_L19:
        Object aobj4[] = new Object[6];
        for(int i4 = 0; i4 < 5; i4++)
            aobj4[i4] = readField();

        if(i == 35)
            aobj4[5] = readField();
        return ((Object) (aobj4));
_L1:
        System.out.println("Unknown fixed-format class " + i);
        throw new IOException();
    }

    void createSpritesAndWatchers(LContext lcontext)
    {
        for(int i = 0; i < objTable.length; i++)
        {
            Object aobj[] = objTable[i];
            int j = ((Number)aobj[1]).intValue();
            if(j == 124 || j == 125)
                aobj[0] = new Sprite(lcontext);
            if(j != 155)
                continue;
            aobj[0] = new Watcher(lcontext);
            if(((Number)aobj[2]).intValue() <= 3)
                continue;
            Number number = (Number)aobj[23];
            Number number1 = (Number)aobj[24];
            if(floatWithoutFraction(number) || floatWithoutFraction(number1))
                aobj[24] = new Double(number1.doubleValue() + 1E-08D);
        }

    }

    boolean floatWithoutFraction(Object obj)
    {
        if(!(obj instanceof Double))
        {
            return false;
        } else
        {
            double d = ((Double)obj).doubleValue();
            return (double)Math.round(d) == d;
        }
    }

    void resolveReferences()
        throws IOException
    {
        for(int i = 0; i < objTable.length; i++)
        {
            int j = ((Number)objTable[i][1]).intValue();
            if(j >= 20 && j <= 29)
            {
                Object aobj[] = (Object[])objTable[i][0];
                for(int l = 0; l < aobj.length; l++)
                {
                    Object obj1 = aobj[l];
                    if(obj1 instanceof Ref)
                        aobj[l] = deRef((Ref)obj1);
                }

            }
            if(j <= 99)
                continue;
            for(int k = 3; k < objTable[i].length; k++)
            {
                Object obj = objTable[i][k];
                if(obj instanceof Ref)
                    objTable[i][k] = deRef((Ref)obj);
            }

        }

    }

    Object deRef(Ref ref)
    {
        Object aobj[] = objTable[ref.index];
        return aobj[0] != null && aobj[0] != empty ? aobj[0] : aobj;
    }

    void buildImagesAndSounds()
        throws IOException
    {
        for(int i = 0; i < objTable.length; i++)
        {
            int j = ((Number)objTable[i][1]).intValue();
            if(j == 34 || j == 35)
            {
                Object aobj[] = (Object[])objTable[i][0];
                int k = ((Number)aobj[0]).intValue();
                int l = ((Number)aobj[1]).intValue();
                int i1 = ((Number)aobj[2]).intValue();
                int ai[] = decodePixels(objTable[((Ref)aobj[4]).index][0]);
                MemoryImageSource memoryimagesource = null;
                objTable[i][0] = ((Object) (empty));
                if(i1 <= 8)
                {
                    IndexColorModel indexcolormodel;
                    if(aobj[5] != null)
                    {
                        Object aobj2[] = (Object[])objTable[((Ref)aobj[5]).index][0];
                        indexcolormodel = customColorMap(i1, aobj2);
                    } else
                    {
                        indexcolormodel = squeakColorMap(i1);
                    }
                    memoryimagesource = new MemoryImageSource(k, l, indexcolormodel, rasterToByteRaster(ai, k, l, i1), 0, k);
                }
                if(i1 == 16)
                    memoryimagesource = new MemoryImageSource(k, l, raster16to32(ai, k, l), 0, k);
                if(i1 == 32)
                    memoryimagesource = new MemoryImageSource(k, l, rasterAddAlphaTo32(ai), 0, k);
                if(memoryimagesource != null)
                {
                    int ai1[] = new int[k * l];
                    PixelGrabber pixelgrabber = new PixelGrabber(memoryimagesource, 0, 0, k, l, ai1, 0, k);
                    try
                    {
                        pixelgrabber.grabPixels();
                    }
                    catch(InterruptedException interruptedexception) { }
                    BufferedImage bufferedimage = new BufferedImage(k, l, 2);
                    bufferedimage.getRaster().setDataElements(0, 0, k, l, ai1);
                    objTable[i][0] = bufferedimage;
                }
            }
            if(j == 109)
            {
                Object aobj1[] = objTable[((Ref)objTable[i][6]).index];
                objTable[i][0] = new ScratchSound(((Number)objTable[i][7]).intValue(), (byte[])aobj1[0]);
            }
        }

    }

    int[] decodePixels(Object obj)
        throws IOException
    {
        if(obj instanceof int[])
            return (int[])obj;
        DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream((byte[])obj));
        int i = decodeInt(datainputstream);
        int ai[] = new int[i];
        int j = 0;
        do
            if((datainputstream.available() > 0) & (j < i))
            {
                int k = decodeInt(datainputstream);
                int l = k >> 2;
                int i1 = k & 3;
                switch(i1)
                {
                case 0: // '\0'
                    j += l;
                    break;

                case 1: // '\001'
                    int j1 = datainputstream.readUnsignedByte();
                    int k1 = j1 << 24 | j1 << 16 | j1 << 8 | j1;
                    int j2 = 0;
                    while(j2 < l) 
                    {
                        ai[j++] = k1;
                        j2++;
                    }
                    break;

                case 2: // '\002'
                    int l1 = datainputstream.readInt();
                    int k2 = 0;
                    while(k2 < l) 
                    {
                        ai[j++] = l1;
                        k2++;
                    }
                    break;

                case 3: // '\003'
                    int l2 = 0;
                    while(l2 < l) 
                    {
                        int i2 = datainputstream.readUnsignedByte() << 24;
                        i2 |= datainputstream.readUnsignedByte() << 16;
                        i2 |= datainputstream.readUnsignedByte() << 8;
                        i2 |= datainputstream.readUnsignedByte();
                        ai[j++] = i2;
                        l2++;
                    }
                    break;
                }
            } else
            {
                return ai;
            }
        while(true);
    }

    int decodeInt(DataInputStream datainputstream)
        throws IOException
    {
        int i = datainputstream.readUnsignedByte();
        if(i <= 223)
            return i;
        if(i <= 254)
            return (i - 224) * 256 + datainputstream.readUnsignedByte();
        else
            return datainputstream.readInt();
    }

    int[] rasterAddAlphaTo32(int ai[])
    {
        for(int i = 0; i < ai.length; i++)
        {
            int j = ai[i];
            if(j != 0)
                ai[i] = 0xff000000 | j;
        }

        return ai;
    }

    int[] raster16to32(int ai[], int i, int j)
    {
        int ai1[] = new int[i * j];
        int l1 = (i + 1) / 2;
        for(int i2 = 0; i2 < j; i2++)
        {
            byte byte0 = 16;
            for(int j2 = 0; j2 < i; j2++)
            {
                int k = ai[i2 * l1 + j2 / 2] >> byte0 & 0xffff;
                int l = (k >> 10 & 0x1f) << 3;
                int i1 = (k >> 5 & 0x1f) << 3;
                int j1 = (k & 0x1f) << 3;
                int k1 = l + i1 + j1 != 0 ? 0xff000000 | l << 16 | i1 << 8 | j1 : 0;
                ai1[i2 * i + j2] = k1;
                byte0 = ((byte)(byte0 != 16 ? 16 : 0));
            }

        }

        return ai1;
    }

    byte[] rasterToByteRaster(int ai[], int i, int j, int k)
    {
        byte abyte0[] = new byte[i * j];
        int l = ai.length / j;
        int i1 = (1 << k) - 1;
        int j1 = 32 / k;
        for(int k1 = 0; k1 < j; k1++)
        {
            for(int l1 = 0; l1 < i; l1++)
            {
                int i2 = ai[k1 * l + l1 / j1];
                int j2 = k * (j1 - l1 % j1 - 1);
                abyte0[k1 * i + l1] = (byte)(i2 >> j2 & i1);
            }

        }

        return abyte0;
    }

    IndexColorModel squeakColorMap(int i)
    {
        byte byte0 = ((byte)(i != 1 ? 0 : -1));
        return new IndexColorModel(i, 256, squeakColors, 0, false, byte0);
    }

    IndexColorModel customColorMap(int i, Object aobj[])
    {
        byte abyte0[] = new byte[4 * aobj.length];
        int j = 0;
        for(int k = 0; k < aobj.length; k++)
        {
            Color color = (Color)objTable[((Ref)aobj[k]).index][0];
            abyte0[j++] = (byte)color.getRed();
            abyte0[j++] = (byte)color.getGreen();
            abyte0[j++] = (byte)color.getBlue();
            abyte0[j++] = (byte)color.getAlpha();
        }

        return new IndexColorModel(i, aobj.length, abyte0, 0, true, 0);
    }

    void fixSounds()
    {
        boolean flag = false;
        for(int i = 0; i < objTable.length; i++)
        {
            int k = ((Number)objTable[i][1]).intValue();
            if(k == 109 && ((ScratchSound)objTable[i][0]).isByteReversed())
                flag = true;
        }

        if(!flag)
            return;
        for(int j = 0; j < objTable.length; j++)
        {
            int l = ((Number)objTable[j][1]).intValue();
            if(l == 109)
                ((ScratchSound)objTable[j][0]).reverseBytes();
        }

    }

    void uncompressMedia()
    {
        for(int i = 0; i < objTable.length; i++)
        {
            Object aobj[] = objTable[i];
            int j = ((Number)aobj[1]).intValue();
            int k = -1;
            if(j >= 100)
                k = ((Number)aobj[2]).intValue();
            if(j == 162 && k >= 4)
            {
                if(aobj[7] instanceof byte[])
                {
                    BufferedImage bufferedimage = jpegDecode((byte[])aobj[7]);
                    if(bufferedimage != null)
                    {
                        if(aobj[4] instanceof Image)
                            ((Image)aobj[4]).flush();
                        aobj[4] = bufferedimage;
                        aobj[7] = ((Object) (empty));
                    }
                }
                if(aobj[8] instanceof BufferedImage)
                {
                    aobj[4] = aobj[8];
                    aobj[8] = ((Object) (empty));
                }
            }
            if(j == 164 && k >= 2 && (aobj[9] instanceof byte[]))
            {
                int l = ((Number)aobj[7]).intValue();
                int i1 = ((Number)aobj[8]).intValue();
                byte abyte0[] = (byte[])aobj[9];
                int j1 = (abyte0.length * 8 + (i1 - 1)) / i1;
                int ai[] = (new ADPCMDecoder(abyte0, i1)).decode(j1);
                aobj[4] = new ScratchSound(l, ai);
                aobj[7] = aobj[8] = aobj[9] = ((Object) (empty));
            }
        }

    }

    void canonicalizeMedia()
    {
        Vector vector = new Vector(100);
        Vector vector1 = new Vector(100);
        for(int i = 0; i < objTable.length; i++)
        {
            Object aobj[] = objTable[i];
            int j = ((Number)aobj[1]).intValue();
            Object obj;
            if(j == 162)
                obj = (BufferedImage)aobj[4];
            if(j == 164)
                obj = (ScratchSound)aobj[4];
        }

    }

    BufferedImage jpegDecode(byte abyte0[])
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(abyte0);
        MediaTracker mediatracker = new MediaTracker(canvas);
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
            image.flush();
            return bufferedimage;
        }
    }

    void printit(Object obj)
    {
        if((obj instanceof Object[]) && ((Object[])obj).length == 0)
        {
            System.out.print(" []");
            return;
        }
        if(obj instanceof BufferedImage)
        {
            BufferedImage bufferedimage = (BufferedImage)obj;
            System.out.print(" BufferedImage_" + obj.hashCode() + "(" + bufferedimage.getWidth(null) + "x" + bufferedimage.getHeight(null) + ")");
            return;
        } else
        {
            System.out.print(" " + obj);
            return;
        }
    }

    DataInputStream s;
    Object objTable[][];
    static final int ObjectRefID = 99;
    static final Object empty[] = new Object[0];
    static final Canvas canvas = new Canvas();
    static final byte macRomanToISOLatin[] = {
        -60, -59, -57, -55, -47, -42, -36, -31, -32, -30, 
        -28, -29, -27, -25, -23, -24, -22, -21, -19, -20, 
        -18, -17, -15, -13, -14, -12, -10, -11, -6, -7, 
        -5, -4, -122, -80, -94, -93, -89, -107, -74, -33, 
        -82, -87, -103, -76, -88, -128, -58, -40, -127, -79, 
        -118, -115, -91, -75, -114, -113, -112, -102, -99, -86, 
        -70, -98, -26, -8, -65, -95, -84, -90, -125, -83, 
        -78, -85, -69, -123, -96, -64, -61, -43, -116, -100, 
        -106, -105, -109, -108, -111, -110, -9, -77, -1, -97, 
        -71, -92, -117, -101, -68, -67, -121, -73, -126, -124, 
        -119, -62, -54, -63, -53, -56, -51, -50, -49, -52, 
        -45, -44, -66, -46, -38, -37, -39, -48, -120, -104, 
        -81, -41, -35, -34, -72, -16, -3, -2
    };
    static final byte squeakColors[] = {
        -1, -1, -1, 0, 0, 0, -1, -1, -1, -128, 
        -128, -128, -1, 0, 0, 0, -1, 0, 0, 0, 
        -1, 0, -1, -1, -1, -1, 0, -1, 0, -1, 
        32, 32, 32, 64, 64, 64, 96, 96, 96, -97, 
        -97, -97, -65, -65, -65, -33, -33, -33, 8, 8, 
        8, 16, 16, 16, 24, 24, 24, 40, 40, 40, 
        48, 48, 48, 56, 56, 56, 72, 72, 72, 80, 
        80, 80, 88, 88, 88, 104, 104, 104, 112, 112, 
        112, 120, 120, 120, -121, -121, -121, -113, -113, -113, 
        -105, -105, -105, -89, -89, -89, -81, -81, -81, -73, 
        -73, -73, -57, -57, -57, -49, -49, -49, -41, -41, 
        -41, -25, -25, -25, -17, -17, -17, -9, -9, -9, 
        0, 0, 0, 0, 51, 0, 0, 102, 0, 0, 
        -103, 0, 0, -52, 0, 0, -1, 0, 0, 0, 
        51, 0, 51, 51, 0, 102, 51, 0, -103, 51, 
        0, -52, 51, 0, -1, 51, 0, 0, 102, 0, 
        51, 102, 0, 102, 102, 0, -103, 102, 0, -52, 
        102, 0, -1, 102, 0, 0, -103, 0, 51, -103, 
        0, 102, -103, 0, -103, -103, 0, -52, -103, 0, 
        -1, -103, 0, 0, -52, 0, 51, -52, 0, 102, 
        -52, 0, -103, -52, 0, -52, -52, 0, -1, -52, 
        0, 0, -1, 0, 51, -1, 0, 102, -1, 0, 
        -103, -1, 0, -52, -1, 0, -1, -1, 51, 0, 
        0, 51, 51, 0, 51, 102, 0, 51, -103, 0, 
        51, -52, 0, 51, -1, 0, 51, 0, 51, 51, 
        51, 51, 51, 102, 51, 51, -103, 51, 51, -52, 
        51, 51, -1, 51, 51, 0, 102, 51, 51, 102, 
        51, 102, 102, 51, -103, 102, 51, -52, 102, 51, 
        -1, 102, 51, 0, -103, 51, 51, -103, 51, 102, 
        -103, 51, -103, -103, 51, -52, -103, 51, -1, -103, 
        51, 0, -52, 51, 51, -52, 51, 102, -52, 51, 
        -103, -52, 51, -52, -52, 51, -1, -52, 51, 0, 
        -1, 51, 51, -1, 51, 102, -1, 51, -103, -1, 
        51, -52, -1, 51, -1, -1, 102, 0, 0, 102, 
        51, 0, 102, 102, 0, 102, -103, 0, 102, -52, 
        0, 102, -1, 0, 102, 0, 51, 102, 51, 51, 
        102, 102, 51, 102, -103, 51, 102, -52, 51, 102, 
        -1, 51, 102, 0, 102, 102, 51, 102, 102, 102, 
        102, 102, -103, 102, 102, -52, 102, 102, -1, 102, 
        102, 0, -103, 102, 51, -103, 102, 102, -103, 102, 
        -103, -103, 102, -52, -103, 102, -1, -103, 102, 0, 
        -52, 102, 51, -52, 102, 102, -52, 102, -103, -52, 
        102, -52, -52, 102, -1, -52, 102, 0, -1, 102, 
        51, -1, 102, 102, -1, 102, -103, -1, 102, -52, 
        -1, 102, -1, -1, -103, 0, 0, -103, 51, 0, 
        -103, 102, 0, -103, -103, 0, -103, -52, 0, -103, 
        -1, 0, -103, 0, 51, -103, 51, 51, -103, 102, 
        51, -103, -103, 51, -103, -52, 51, -103, -1, 51, 
        -103, 0, 102, -103, 51, 102, -103, 102, 102, -103, 
        -103, 102, -103, -52, 102, -103, -1, 102, -103, 0, 
        -103, -103, 51, -103, -103, 102, -103, -103, -103, -103, 
        -103, -52, -103, -103, -1, -103, -103, 0, -52, -103, 
        51, -52, -103, 102, -52, -103, -103, -52, -103, -52, 
        -52, -103, -1, -52, -103, 0, -1, -103, 51, -1, 
        -103, 102, -1, -103, -103, -1, -103, -52, -1, -103, 
        -1, -1, -52, 0, 0, -52, 51, 0, -52, 102, 
        0, -52, -103, 0, -52, -52, 0, -52, -1, 0, 
        -52, 0, 51, -52, 51, 51, -52, 102, 51, -52, 
        -103, 51, -52, -52, 51, -52, -1, 51, -52, 0, 
        102, -52, 51, 102, -52, 102, 102, -52, -103, 102, 
        -52, -52, 102, -52, -1, 102, -52, 0, -103, -52, 
        51, -103, -52, 102, -103, -52, -103, -103, -52, -52, 
        -103, -52, -1, -103, -52, 0, -52, -52, 51, -52, 
        -52, 102, -52, -52, -103, -52, -52, -52, -52, -52, 
        -1, -52, -52, 0, -1, -52, 51, -1, -52, 102, 
        -1, -52, -103, -1, -52, -52, -1, -52, -1, -1, 
        -1, 0, 0, -1, 51, 0, -1, 102, 0, -1, 
        -103, 0, -1, -52, 0, -1, -1, 0, -1, 0, 
        51, -1, 51, 51, -1, 102, 51, -1, -103, 51, 
        -1, -52, 51, -1, -1, 51, -1, 0, 102, -1, 
        51, 102, -1, 102, 102, -1, -103, 102, -1, -52, 
        102, -1, -1, 102, -1, 0, -103, -1, 51, -103, 
        -1, 102, -103, -1, -103, -103, -1, -52, -103, -1, 
        -1, -103, -1, 0, -52, -1, 51, -52, -1, 102, 
        -52, -1, -103, -52, -1, -52, -52, -1, -1, -52, 
        -1, 0, -1, -1, 51, -1, -1, 102, -1, -1, 
        -103, -1, -1, -52, -1, -1, -1, -1
    };

}
