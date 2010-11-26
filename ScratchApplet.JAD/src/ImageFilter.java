// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageFilter.java

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

class ImageFilter
{

    ImageFilter()
    {
    }

    void setSourceImage(BufferedImage bufferedimage)
    {
        if(filteredImage == null || filteredImage.getWidth(null) != bufferedimage.getWidth(null) || filteredImage.getHeight(null) != bufferedimage.getHeight(null))
        {
            if(filteredImage != null)
                filteredImage.flush();
            filteredImage = new BufferedImage(bufferedimage.getWidth(null), bufferedimage.getHeight(null), 2);
        }
        filteredImage.getRaster().setDataElements(0, 0, bufferedimage.getData());
    }

    BufferedImage makeOutputImage(BufferedImage bufferedimage)
    {
        if(tempImage == null || tempImage.getWidth(null) != bufferedimage.getWidth(null) || tempImage.getHeight(null) != bufferedimage.getHeight(null))
        {
            if(tempImage != null)
                tempImage.flush();
            tempImage = new BufferedImage(bufferedimage.getWidth(null), bufferedimage.getHeight(null), 2);
        }
        return tempImage;
    }

    void applyHueShift(int i)
    {
        BufferedImage bufferedimage = filteredImage;
        BufferedImage bufferedimage1 = makeOutputImage(bufferedimage);
        int i3 = bufferedimage.getWidth();
        int j3 = bufferedimage.getHeight();
        for(int k3 = 0; k3 < i3; k3++)
        {
            for(int l3 = 0; l3 < j3; l3++)
            {
                int k;
                int j = k = bufferedimage.getRGB(k3, l3);
                int l = j & 0xff000000;
                if(l != 0)
                {
                    int i1 = j >> 16 & 0xff;
                    int j1 = j >> 8 & 0xff;
                    int k1 = j & 0xff;
                    int l1 = i1 >= j1 ? j1 : i1;
                    if(k1 < l1)
                        l1 = k1;
                    int i2 = i1 <= j1 ? j1 : i1;
                    if(k1 > i2)
                        i2 = k1;
                    int l2 = (i2 * 1000) / 255;
                    if(l2 < 150)
                        l2 = 150;
                    int k2 = i2 != 0 ? ((i2 - l1) * 1000) / i2 : 0;
                    if(k2 < 150)
                        k2 = 150;
                    int j2 = rgb2hue(i1, j1, k1, l1, i2) + (180 * i) / 100;
                    k = l | hsv2rgb(j2, k2, l2);
                }
                bufferedimage1.setRGB(k3, l3, k);
            }

        }

        tempImage = filteredImage;
        filteredImage = bufferedimage1;
    }

    void applyBrightnessShift(int i)
    {
        BufferedImage bufferedimage = filteredImage;
        BufferedImage bufferedimage1 = makeOutputImage(bufferedimage);
        int i3 = bufferedimage.getWidth();
        int j3 = bufferedimage.getHeight();
        for(int k3 = 0; k3 < i3; k3++)
        {
            for(int l3 = 0; l3 < j3; l3++)
            {
                int k;
                int j = k = bufferedimage.getRGB(k3, l3);
                int l = j & 0xff000000;
                if(l != 0)
                {
                    int i1 = j >> 16 & 0xff;
                    int j1 = j >> 8 & 0xff;
                    int k1 = j & 0xff;
                    int l1 = i1 >= j1 ? j1 : i1;
                    if(k1 < l1)
                        l1 = k1;
                    int i2 = i1 <= j1 ? j1 : i1;
                    if(k1 > i2)
                        i2 = k1;
                    int j2 = rgb2hue(i1, j1, k1, l1, i2);
                    int k2 = i2 != 0 ? ((i2 - l1) * 1000) / i2 : 0;
                    int l2 = (i2 * 1000) / 255 + 10 * i;
                    if(l2 > 1000)
                        l2 = 1000;
                    if(l2 < 0)
                        l2 = 0;
                    k = l | hsv2rgb(j2, k2, l2);
                }
                bufferedimage1.setRGB(k3, l3, k);
            }

        }

        tempImage = filteredImage;
        filteredImage = bufferedimage1;
    }

    int rgb2hue(int i, int j, int k, int l, int i1)
    {
        int j1 = i1 - l;
        if(j1 == 0)
            return 0;
        if(i == i1)
            return (60 * (j - k)) / j1;
        if(j == i1)
            return 120 + (60 * (k - i)) / j1;
        else
            return 240 + (60 * (i - j)) / j1;
    }

    int hsv2rgb(int i, int j, int k)
    {
        int l = i % 360;
        if(l < 0)
            l += 360;
        int i1 = l / 60;
        int j1 = l % 60;
        int k1 = ((1000 - j) * k) / 3922;
        int l1 = ((1000 - (j * j1) / 60) * k) / 3922;
        int i2 = ((1000 - (j * (60 - j1)) / 60) * k) / 3922;
        int j2 = (k * 1000) / 3922;
        switch(i1)
        {
        case 0: // '\0'
            return j2 << 16 | i2 << 8 | k1;

        case 1: // '\001'
            return l1 << 16 | j2 << 8 | k1;

        case 2: // '\002'
            return k1 << 16 | j2 << 8 | i2;

        case 3: // '\003'
            return k1 << 16 | l1 << 8 | j2;

        case 4: // '\004'
            return i2 << 16 | k1 << 8 | j2;

        case 5: // '\005'
            return j2 << 16 | k1 << 8 | l1;
        }
        return 0;
    }

    void applyFisheye(double d)
    {
        BufferedImage bufferedimage = filteredImage;
        BufferedImage bufferedimage1 = makeOutputImage(bufferedimage);
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        double d1 = i / 2;
        double d2 = j / 2;
        double d3 = (d + 100D) / 100D;
        for(int k = 0; k < i; k++)
        {
            for(int l = 0; l < j; l++)
            {
                double d4 = ((double)k - d1) / d1;
                double d5 = ((double)l - d2) / d2;
                double d6 = Math.pow(Math.sqrt(d4 * d4 + d5 * d5), d3);
                double d8;
                double d9;
                if(d6 <= 1.0D)
                {
                    double d7 = Math.atan2(d5, d4);
                    d8 = d1 + d6 * Math.cos(d7) * d1;
                    d9 = d2 + d6 * Math.sin(d7) * d2;
                } else
                {
                    d8 = k;
                    d9 = l;
                }
                bufferedimage1.setRGB(k, l, interpolate(bufferedimage, d8, d9));
            }

        }

        tempImage = filteredImage;
        filteredImage = bufferedimage1;
    }

    int interpolate(BufferedImage bufferedimage, double d, double d1)
    {
        int i = (int)Math.round(d);
        if(i < 0)
            i = 0;
        if(i >= bufferedimage.getWidth(null))
            i = bufferedimage.getWidth(null) - 1;
        int j = (int)Math.round(d1);
        if(j < 0)
            j = 0;
        if(j >= bufferedimage.getHeight(null))
            j = bufferedimage.getHeight(null) - 1;
        return bufferedimage.getRGB(i, j);
    }

    void applyWhirl(double d)
    {
        BufferedImage bufferedimage = filteredImage;
        BufferedImage bufferedimage1 = makeOutputImage(bufferedimage);
        double d14 = Math.toRadians(-d);
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        double d15 = i / 2;
        double d16 = j / 2;
        double d1;
        double d3;
        double d4;
        if(d15 < d16)
        {
            d1 = d15;
            d3 = d16 / d15;
            d4 = 1.0D;
        } else
        {
            d1 = d16;
            d3 = 1.0D;
            if(d16 < d15)
                d4 = d15 / d16;
            else
                d4 = 1.0D;
        }
        double d2 = d1 * d1;
        for(int k = 0; k < i; k++)
        {
            for(int l = 0; l < j; l++)
            {
                double d5 = d3 * ((double)k - d15);
                double d6 = d4 * ((double)l - d16);
                double d7 = d5 * d5 + d6 * d6;
                if(d7 < d2)
                {
                    double d8 = 1.0D - Math.sqrt(d7) / d1;
                    double d9 = d14 * (d8 * d8);
                    double d10 = Math.sin(d9);
                    double d11 = Math.cos(d9);
                    double d12 = (d11 * d5 - d10 * d6) / d3 + d15;
                    double d13 = (d10 * d5 + d11 * d6) / d4 + d16;
                    bufferedimage1.setRGB(k, l, bufferedimage.getRGB((int)d12, (int)d13));
                } else
                {
                    bufferedimage1.setRGB(k, l, bufferedimage.getRGB(k, l));
                }
            }

        }

        tempImage = filteredImage;
        filteredImage = bufferedimage1;
    }

    void applyMosaic(double d)
    {
        BufferedImage bufferedimage = filteredImage;
        int i = (int)(Math.abs(d) + 10D) / 10;
        i = Math.min(i, Math.min(bufferedimage.getWidth(null), bufferedimage.getHeight(null)) - 1);
        if(i <= 1)
            return;
        AffineTransform affinetransform = AffineTransform.getScaleInstance(1.0D / (double)i, 1.0D / (double)i);
        AffineTransformOp affinetransformop = new AffineTransformOp(affinetransform, 1);
        BufferedImage bufferedimage1 = affinetransformop.filter(bufferedimage, null);
        int j = i * bufferedimage1.getWidth(null);
        int k = i * bufferedimage1.getHeight(null);
        BufferedImage bufferedimage2 = new BufferedImage(j, k, 2);
        bufferedimage2.getRaster();
        Graphics g = bufferedimage2.getGraphics();
        int l = bufferedimage1.getWidth(null);
        int i1 = bufferedimage1.getHeight(null);
        for(int j1 = 0; j1 < bufferedimage2.getHeight(null); j1 += i1)
        {
            for(int k1 = 0; k1 < bufferedimage2.getWidth(null); k1 += l)
                g.drawImage(bufferedimage1, k1, j1, null);

        }

        g.dispose();
        bufferedimage1.flush();
        if(filteredImage != null)
            filteredImage.flush();
        affinetransform = AffineTransform.getScaleInstance((double)bufferedimage.getWidth(null) / (double)bufferedimage2.getWidth(null), (double)bufferedimage.getHeight(null) / (double)bufferedimage2.getHeight(null));
        affinetransformop = new AffineTransformOp(affinetransform, 1);
        filteredImage = affinetransformop.filter(bufferedimage2, null);
        bufferedimage2.flush();
    }

    void applyPixelate(double d)
    {
        BufferedImage bufferedimage = filteredImage;
        double d1 = (Math.abs(d) + 10D) / 10D;
        d1 = Math.min(d1, Math.min(bufferedimage.getWidth(null), bufferedimage.getHeight(null)));
        if(d1 <= 1.0D)
        {
            return;
        } else
        {
            AffineTransform affinetransform = AffineTransform.getScaleInstance(1.0D / d1, 1.0D / d1);
            AffineTransformOp affinetransformop = new AffineTransformOp(affinetransform, 2);
            BufferedImage bufferedimage1 = affinetransformop.filter(bufferedimage, null);
            affinetransform = AffineTransform.getScaleInstance(d1, d1);
            affinetransformop = new AffineTransformOp(affinetransform, 1);
            filteredImage = affinetransformop.filter(bufferedimage1, filteredImage);
            bufferedimage1.flush();
            return;
        }
    }

    BufferedImage filteredImage;
    BufferedImage tempImage;
}
