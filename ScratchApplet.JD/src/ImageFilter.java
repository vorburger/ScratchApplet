import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

class ImageFilter
{
  BufferedImage filteredImage;
  BufferedImage tempImage;

  void setSourceImage(BufferedImage paramBufferedImage)
  {
    if ((this.filteredImage == null) || (this.filteredImage.getWidth(null) != paramBufferedImage.getWidth(null)) || (this.filteredImage.getHeight(null) != paramBufferedImage.getHeight(null)))
    {
      if (this.filteredImage != null) this.filteredImage.flush();
      this.filteredImage = new BufferedImage(paramBufferedImage.getWidth(null), paramBufferedImage.getHeight(null), 2);
    }
    this.filteredImage.getRaster().setDataElements(0, 0, paramBufferedImage.getData());
  }

  BufferedImage makeOutputImage(BufferedImage paramBufferedImage) {
    if ((this.tempImage == null) || (this.tempImage.getWidth(null) != paramBufferedImage.getWidth(null)) || (this.tempImage.getHeight(null) != paramBufferedImage.getHeight(null)))
    {
      if (this.tempImage != null) this.tempImage.flush();
      this.tempImage = new BufferedImage(paramBufferedImage.getWidth(null), paramBufferedImage.getHeight(null), 2);
    }
    return this.tempImage;
  }

  void applyHueShift(int paramInt) {
    BufferedImage localBufferedImage1 = this.filteredImage;
    BufferedImage localBufferedImage2 = makeOutputImage(localBufferedImage1);

    int i7 = localBufferedImage1.getWidth();
    int i8 = localBufferedImage1.getHeight();

    for (int i9 = 0; i9 < i7; i9++) {
      for (int i10 = 0; i10 < i8; i10++)
      {
        int j;
        int i = j = localBufferedImage1.getRGB(i9, i10);
        int k = i & 0xFF000000;
        if (k != 0) {
          int m = i >> 16 & 0xFF;
          int n = i >> 8 & 0xFF;
          int i1 = i & 0xFF;
          int i2 = m < n ? m : n; if (i1 < i2) i2 = i1;
          int i3 = m > n ? m : n; if (i1 > i3) i3 = i1;

          int i6 = i3 * 1000 / 255;
          if (i6 < 150) i6 = 150;

          int i5 = i3 == 0 ? 0 : (i3 - i2) * 1000 / i3;
          if (i5 < 150) i5 = 150;

          int i4 = rgb2hue(m, n, i1, i2, i3) + 180 * paramInt / 100;
          j = k | hsv2rgb(i4, i5, i6);
        }
        localBufferedImage2.setRGB(i9, i10, j);
      }
    }
    this.tempImage = this.filteredImage;
    this.filteredImage = localBufferedImage2;
  }

  void applyBrightnessShift(int paramInt) {
    BufferedImage localBufferedImage1 = this.filteredImage;
    BufferedImage localBufferedImage2 = makeOutputImage(localBufferedImage1);

    int i7 = localBufferedImage1.getWidth();
    int i8 = localBufferedImage1.getHeight();

    for (int i9 = 0; i9 < i7; i9++) {
      for (int i10 = 0; i10 < i8; i10++)
      {
        int j;
        int i = j = localBufferedImage1.getRGB(i9, i10);
        int k = i & 0xFF000000;
        if (k != 0) {
          int m = i >> 16 & 0xFF;
          int n = i >> 8 & 0xFF;
          int i1 = i & 0xFF;
          int i2 = m < n ? m : n; if (i1 < i2) i2 = i1;
          int i3 = m > n ? m : n; if (i1 > i3) i3 = i1;
          int i4 = rgb2hue(m, n, i1, i2, i3);
          int i5 = i3 == 0 ? 0 : (i3 - i2) * 1000 / i3;
          int i6 = i3 * 1000 / 255 + 10 * paramInt;
          if (i6 > 1000) i6 = 1000;
          if (i6 < 0) i6 = 0;

          j = k | hsv2rgb(i4, i5, i6);
        }
        localBufferedImage2.setRGB(i9, i10, j);
      }
    }
    this.tempImage = this.filteredImage;
    this.filteredImage = localBufferedImage2;
  }

  int rgb2hue(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = paramInt5 - paramInt4;
    if (i == 0) return 0;
    if (paramInt1 == paramInt5) return 60 * (paramInt2 - paramInt3) / i;
    if (paramInt2 == paramInt5) return 120 + 60 * (paramInt3 - paramInt1) / i;
    return 240 + 60 * (paramInt1 - paramInt2) / i;
  }

  int hsv2rgb(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 % 360;
    if (i < 0) i += 360;
    int j = i / 60;
    int k = i % 60;
    int m = (1000 - paramInt2) * paramInt3 / 3922;
    int n = (1000 - paramInt2 * k / 60) * paramInt3 / 3922;
    int i1 = (1000 - paramInt2 * (60 - k) / 60) * paramInt3 / 3922;
    int i2 = paramInt3 * 1000 / 3922;

    switch (j) { case 0:
      return i2 << 16 | i1 << 8 | m;
    case 1:
      return n << 16 | i2 << 8 | m;
    case 2:
      return m << 16 | i2 << 8 | i1;
    case 3:
      return m << 16 | n << 8 | i2;
    case 4:
      return i1 << 16 | m << 8 | i2;
    case 5:
      return i2 << 16 | m << 8 | n;
    }
    return 0;
  }

  void applyFisheye(double paramDouble) {
    BufferedImage localBufferedImage1 = this.filteredImage;
    BufferedImage localBufferedImage2 = makeOutputImage(localBufferedImage1);
    int i = localBufferedImage1.getWidth();
    int j = localBufferedImage1.getHeight();
    double d1 = i / 2;
    double d2 = j / 2;
    double d3 = (paramDouble + 100.0D) / 100.0D;

    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++) {
        double d4 = (k - d1) / d1;
        double d5 = (m - d2) / d2;
        double d6 = Math.pow(Math.sqrt(d4 * d4 + d5 * d5), d3);
        double d8;
        double d9;
        if (d6 <= 1.0D) {
          double d7 = Math.atan2(d5, d4);
          d8 = d1 + d6 * Math.cos(d7) * d1;
          d9 = d2 + d6 * Math.sin(d7) * d2;
        } else {
          d8 = k;
          d9 = m;
        }
        localBufferedImage2.setRGB(k, m, interpolate(localBufferedImage1, d8, d9));
      }
    }
    this.tempImage = this.filteredImage;
    this.filteredImage = localBufferedImage2;
  }

  int interpolate(BufferedImage paramBufferedImage, double paramDouble1, double paramDouble2) {
    int i = (int)Math.round(paramDouble1);
    if (i < 0) i = 0;
    if (i >= paramBufferedImage.getWidth(null)) i = paramBufferedImage.getWidth(null) - 1;
    int j = (int)Math.round(paramDouble2);
    if (j < 0) j = 0;
    if (j >= paramBufferedImage.getHeight(null)) j = paramBufferedImage.getHeight(null) - 1;
    return paramBufferedImage.getRGB(i, j);
  }

  void applyWhirl(double paramDouble) {
    BufferedImage localBufferedImage1 = this.filteredImage;
    BufferedImage localBufferedImage2 = makeOutputImage(localBufferedImage1);

    double d14 = Math.toRadians(-paramDouble);
    int i = localBufferedImage1.getWidth();
    int j = localBufferedImage1.getHeight();
    double d15 = i / 2;
    double d16 = j / 2;
    double d1;
    double d3;
    double d4;
    if (d15 < d16) {
      d1 = d15;
      d3 = d16 / d15;
      d4 = 1.0D;
    } else {
      d1 = d16;
      d3 = 1.0D;
      if (d16 < d15)
        d4 = d15 / d16;
      else {
        d4 = 1.0D;
      }
    }
    double d2 = d1 * d1;
    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++) {
        double d5 = d3 * (k - d15);
        double d6 = d4 * (m - d16);
        double d7 = d5 * d5 + d6 * d6;
        if (d7 < d2) {
          double d8 = 1.0D - Math.sqrt(d7) / d1;
          double d9 = d14 * (d8 * d8);
          double d10 = Math.sin(d9);
          double d11 = Math.cos(d9);
          double d12 = (d11 * d5 - d10 * d6) / d3 + d15;
          double d13 = (d10 * d5 + d11 * d6) / d4 + d16;
          localBufferedImage2.setRGB(k, m, localBufferedImage1.getRGB((int)d12, (int)d13));
        } else {
          localBufferedImage2.setRGB(k, m, localBufferedImage1.getRGB(k, m));
        }
      }
    }
    this.tempImage = this.filteredImage;
    this.filteredImage = localBufferedImage2;
  }

  void applyMosaic(double paramDouble) {
    BufferedImage localBufferedImage1 = this.filteredImage;
    int i = (int)(Math.abs(paramDouble) + 10.0D) / 10;
    i = Math.min(i, Math.min(localBufferedImage1.getWidth(null), localBufferedImage1.getHeight(null)) - 1);
    if (i <= 1) return;

    AffineTransform localAffineTransform = AffineTransform.getScaleInstance(1.0D / i, 1.0D / i);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    BufferedImage localBufferedImage2 = localAffineTransformOp.filter(localBufferedImage1, null);

    int j = i * localBufferedImage2.getWidth(null);
    int k = i * localBufferedImage2.getHeight(null);
    BufferedImage localBufferedImage3 = new BufferedImage(j, k, 2);
    localBufferedImage3.getRaster();
    Graphics localGraphics = localBufferedImage3.getGraphics();
    int m = localBufferedImage2.getWidth(null);
    int n = localBufferedImage2.getHeight(null);
    for (int i1 = 0; i1 < localBufferedImage3.getHeight(null); i1 += n) {
      for (int i2 = 0; i2 < localBufferedImage3.getWidth(null); i2 += m) {
        localGraphics.drawImage(localBufferedImage2, i2, i1, null);
      }
    }
    localGraphics.dispose();
    localBufferedImage2.flush();
    if (this.filteredImage != null) this.filteredImage.flush();

    localAffineTransform = AffineTransform.getScaleInstance(localBufferedImage1.getWidth(null) / localBufferedImage3.getWidth(null), localBufferedImage1.getHeight(null) / localBufferedImage3.getHeight(null));

    localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    this.filteredImage = localAffineTransformOp.filter(localBufferedImage3, null);
    localBufferedImage3.flush();
  }

  void applyPixelate(double paramDouble)
  {
    BufferedImage localBufferedImage1 = this.filteredImage;
    double d = (Math.abs(paramDouble) + 10.0D) / 10.0D;
    d = Math.min(d, Math.min(localBufferedImage1.getWidth(null), localBufferedImage1.getHeight(null)));
    if (d <= 1.0D) return;

    AffineTransform localAffineTransform = AffineTransform.getScaleInstance(1.0D / d, 1.0D / d);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 2);
    BufferedImage localBufferedImage2 = localAffineTransformOp.filter(localBufferedImage1, null);

    localAffineTransform = AffineTransform.getScaleInstance(d, d);
    localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    this.filteredImage = localAffineTransformOp.filter(localBufferedImage2, this.filteredImage);
    localBufferedImage2.flush();
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ImageFilter
 * JD-Core Version:    0.6.0
 */