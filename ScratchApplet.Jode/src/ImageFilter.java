/* ImageFilter - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class ImageFilter
{
    BufferedImage filteredImage;
    BufferedImage tempImage;
    
    void setSourceImage(BufferedImage bufferedimage) {
	if (((ImageFilter) this).filteredImage == null
	    || (((ImageFilter) this).filteredImage.getWidth(null)
		!= bufferedimage.getWidth(null))
	    || (((ImageFilter) this).filteredImage.getHeight(null)
		!= bufferedimage.getHeight(null))) {
	    if (((ImageFilter) this).filteredImage != null)
		((ImageFilter) this).filteredImage.flush();
	    ((ImageFilter) this).filteredImage
		= new BufferedImage(bufferedimage.getWidth(null),
				    bufferedimage.getHeight(null), 2);
	}
	((ImageFilter) this).filteredImage.getRaster()
	    .setDataElements(0, 0, bufferedimage.getData());
    }
    
    BufferedImage makeOutputImage(BufferedImage bufferedimage) {
	if (((ImageFilter) this).tempImage == null
	    || (((ImageFilter) this).tempImage.getWidth(null)
		!= bufferedimage.getWidth(null))
	    || (((ImageFilter) this).tempImage.getHeight(null)
		!= bufferedimage.getHeight(null))) {
	    if (((ImageFilter) this).tempImage != null)
		((ImageFilter) this).tempImage.flush();
	    ((ImageFilter) this).tempImage
		= new BufferedImage(bufferedimage.getWidth(null),
				    bufferedimage.getHeight(null), 2);
	}
	return ((ImageFilter) this).tempImage;
    }
    
    void applyHueShift(int i) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	BufferedImage bufferedimage_0_ = makeOutputImage(bufferedimage);
	int i_1_ = bufferedimage.getWidth();
	int i_2_ = bufferedimage.getHeight();
	for (int i_3_ = 0; i_3_ < i_1_; i_3_++) {
	    for (int i_4_ = 0; i_4_ < i_2_; i_4_++) {
		int i_6_;
		int i_5_ = i_6_ = bufferedimage.getRGB(i_3_, i_4_);
		int i_7_ = i_5_ & ~0xffffff;
		if (i_7_ != 0) {
		    int i_8_ = i_5_ >> 16 & 0xff;
		    int i_9_ = i_5_ >> 8 & 0xff;
		    int i_10_ = i_5_ & 0xff;
		    int i_11_ = i_8_ < i_9_ ? i_8_ : i_9_;
		    if (i_10_ < i_11_)
			i_11_ = i_10_;
		    int i_12_ = i_8_ > i_9_ ? i_8_ : i_9_;
		    if (i_10_ > i_12_)
			i_12_ = i_10_;
		    int i_13_ = i_12_ * 1000 / 255;
		    if (i_13_ < 150)
			i_13_ = 150;
		    int i_14_
			= i_12_ == 0 ? 0 : (i_12_ - i_11_) * 1000 / i_12_;
		    if (i_14_ < 150)
			i_14_ = 150;
		    int i_15_ = (rgb2hue(i_8_, i_9_, i_10_, i_11_, i_12_)
				 + 180 * i / 100);
		    i_6_ = i_7_ | hsv2rgb(i_15_, i_14_, i_13_);
		}
		bufferedimage_0_.setRGB(i_3_, i_4_, i_6_);
	    }
	}
	((ImageFilter) this).tempImage = ((ImageFilter) this).filteredImage;
	((ImageFilter) this).filteredImage = bufferedimage_0_;
    }
    
    void applyBrightnessShift(int i) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	BufferedImage bufferedimage_16_ = makeOutputImage(bufferedimage);
	int i_17_ = bufferedimage.getWidth();
	int i_18_ = bufferedimage.getHeight();
	for (int i_19_ = 0; i_19_ < i_17_; i_19_++) {
	    for (int i_20_ = 0; i_20_ < i_18_; i_20_++) {
		int i_22_;
		int i_21_ = i_22_ = bufferedimage.getRGB(i_19_, i_20_);
		int i_23_ = i_21_ & ~0xffffff;
		if (i_23_ != 0) {
		    int i_24_ = i_21_ >> 16 & 0xff;
		    int i_25_ = i_21_ >> 8 & 0xff;
		    int i_26_ = i_21_ & 0xff;
		    int i_27_ = i_24_ < i_25_ ? i_24_ : i_25_;
		    if (i_26_ < i_27_)
			i_27_ = i_26_;
		    int i_28_ = i_24_ > i_25_ ? i_24_ : i_25_;
		    if (i_26_ > i_28_)
			i_28_ = i_26_;
		    int i_29_ = rgb2hue(i_24_, i_25_, i_26_, i_27_, i_28_);
		    int i_30_
			= i_28_ == 0 ? 0 : (i_28_ - i_27_) * 1000 / i_28_;
		    int i_31_ = i_28_ * 1000 / 255 + 10 * i;
		    if (i_31_ > 1000)
			i_31_ = 1000;
		    if (i_31_ < 0)
			i_31_ = 0;
		    i_22_ = i_23_ | hsv2rgb(i_29_, i_30_, i_31_);
		}
		bufferedimage_16_.setRGB(i_19_, i_20_, i_22_);
	    }
	}
	((ImageFilter) this).tempImage = ((ImageFilter) this).filteredImage;
	((ImageFilter) this).filteredImage = bufferedimage_16_;
    }
    
    int rgb2hue(int i, int i_32_, int i_33_, int i_34_, int i_35_) {
	int i_36_ = i_35_ - i_34_;
	if (i_36_ == 0)
	    return 0;
	if (i == i_35_)
	    return 60 * (i_32_ - i_33_) / i_36_;
	if (i_32_ == i_35_)
	    return 120 + 60 * (i_33_ - i) / i_36_;
	return 240 + 60 * (i - i_32_) / i_36_;
    }
    
    int hsv2rgb(int i, int i_37_, int i_38_) {
	int i_39_ = i % 360;
	if (i_39_ < 0)
	    i_39_ += 360;
	int i_40_ = i_39_ / 60;
	int i_41_ = i_39_ % 60;
	int i_42_ = (1000 - i_37_) * i_38_ / 3922;
	int i_43_ = (1000 - i_37_ * i_41_ / 60) * i_38_ / 3922;
	int i_44_ = (1000 - i_37_ * (60 - i_41_) / 60) * i_38_ / 3922;
	int i_45_ = i_38_ * 1000 / 3922;
	switch (i_40_) {
	case 0:
	    return i_45_ << 16 | i_44_ << 8 | i_42_;
	case 1:
	    return i_43_ << 16 | i_45_ << 8 | i_42_;
	case 2:
	    return i_42_ << 16 | i_45_ << 8 | i_44_;
	case 3:
	    return i_42_ << 16 | i_43_ << 8 | i_45_;
	case 4:
	    return i_44_ << 16 | i_42_ << 8 | i_45_;
	case 5:
	    return i_45_ << 16 | i_42_ << 8 | i_43_;
	default:
	    return 0;
	}
    }
    
    void applyFisheye(double d) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	BufferedImage bufferedimage_46_ = makeOutputImage(bufferedimage);
	int i = bufferedimage.getWidth();
	int i_47_ = bufferedimage.getHeight();
	double d_48_ = (double) (i / 2);
	double d_49_ = (double) (i_47_ / 2);
	double d_50_ = (d + 100.0) / 100.0;
	for (int i_51_ = 0; i_51_ < i; i_51_++) {
	    for (int i_52_ = 0; i_52_ < i_47_; i_52_++) {
		double d_53_ = ((double) i_51_ - d_48_) / d_48_;
		double d_54_ = ((double) i_52_ - d_49_) / d_49_;
		double d_55_
		    = Math.pow(Math.sqrt(d_53_ * d_53_ + d_54_ * d_54_),
			       d_50_);
		double d_56_;
		double d_57_;
		if (d_55_ <= 1.0) {
		    double d_58_ = Math.atan2(d_54_, d_53_);
		    d_56_ = d_48_ + d_55_ * Math.cos(d_58_) * d_48_;
		    d_57_ = d_49_ + d_55_ * Math.sin(d_58_) * d_49_;
		} else {
		    d_56_ = (double) i_51_;
		    d_57_ = (double) i_52_;
		}
		bufferedimage_46_.setRGB(i_51_, i_52_,
					 interpolate(bufferedimage, d_56_,
						     d_57_));
	    }
	}
	((ImageFilter) this).tempImage = ((ImageFilter) this).filteredImage;
	((ImageFilter) this).filteredImage = bufferedimage_46_;
    }
    
    int interpolate(BufferedImage bufferedimage, double d, double d_59_) {
	int i = (int) Math.round(d);
	if (i < 0)
	    i = 0;
	if (i >= bufferedimage.getWidth(null))
	    i = bufferedimage.getWidth(null) - 1;
	int i_60_ = (int) Math.round(d_59_);
	if (i_60_ < 0)
	    i_60_ = 0;
	if (i_60_ >= bufferedimage.getHeight(null))
	    i_60_ = bufferedimage.getHeight(null) - 1;
	return bufferedimage.getRGB(i, i_60_);
    }
    
    void applyWhirl(double d) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	BufferedImage bufferedimage_61_ = makeOutputImage(bufferedimage);
	double d_62_ = Math.toRadians(-d);
	int i = bufferedimage.getWidth();
	int i_63_ = bufferedimage.getHeight();
	double d_64_ = (double) (i / 2);
	double d_65_ = (double) (i_63_ / 2);
	double d_66_;
	double d_67_;
	double d_68_;
	if (d_64_ < d_65_) {
	    d_66_ = d_64_;
	    d_67_ = d_65_ / d_64_;
	    d_68_ = 1.0;
	} else {
	    d_66_ = d_65_;
	    d_67_ = 1.0;
	    if (d_65_ < d_64_)
		d_68_ = d_64_ / d_65_;
	    else
		d_68_ = 1.0;
	}
	double d_69_ = d_66_ * d_66_;
	for (int i_70_ = 0; i_70_ < i; i_70_++) {
	    for (int i_71_ = 0; i_71_ < i_63_; i_71_++) {
		double d_72_ = d_67_ * ((double) i_70_ - d_64_);
		double d_73_ = d_68_ * ((double) i_71_ - d_65_);
		double d_74_ = d_72_ * d_72_ + d_73_ * d_73_;
		if (d_74_ < d_69_) {
		    double d_75_ = 1.0 - Math.sqrt(d_74_) / d_66_;
		    double d_76_ = d_62_ * (d_75_ * d_75_);
		    double d_77_ = Math.sin(d_76_);
		    double d_78_ = Math.cos(d_76_);
		    double d_79_
			= (d_78_ * d_72_ - d_77_ * d_73_) / d_67_ + d_64_;
		    double d_80_
			= (d_77_ * d_72_ + d_78_ * d_73_) / d_68_ + d_65_;
		    bufferedimage_61_.setRGB
			(i_70_, i_71_,
			 bufferedimage.getRGB((int) d_79_, (int) d_80_));
		} else
		    bufferedimage_61_.setRGB(i_70_, i_71_,
					     bufferedimage.getRGB(i_70_,
								  i_71_));
	    }
	}
	((ImageFilter) this).tempImage = ((ImageFilter) this).filteredImage;
	((ImageFilter) this).filteredImage = bufferedimage_61_;
    }
    
    void applyMosaic(double d) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	int i = (int) (Math.abs(d) + 10.0) / 10;
	i = Math.min(i, Math.min(bufferedimage.getWidth(null),
				 bufferedimage.getHeight(null)) - 1);
	if (i > 1) {
	    AffineTransform affinetransform
		= AffineTransform.getScaleInstance(1.0 / (double) i,
						   1.0 / (double) i);
	    AffineTransformOp affinetransformop
		= new AffineTransformOp(affinetransform, 1);
	    BufferedImage bufferedimage_81_
		= affinetransformop.filter(bufferedimage, null);
	    int i_82_ = i * bufferedimage_81_.getWidth(null);
	    int i_83_ = i * bufferedimage_81_.getHeight(null);
	    BufferedImage bufferedimage_84_
		= new BufferedImage(i_82_, i_83_, 2);
	    bufferedimage_84_.getRaster();
	    Graphics graphics = bufferedimage_84_.getGraphics();
	    int i_85_ = bufferedimage_81_.getWidth(null);
	    int i_86_ = bufferedimage_81_.getHeight(null);
	    for (int i_87_ = 0; i_87_ < bufferedimage_84_.getHeight(null);
		 i_87_ += i_86_) {
		for (int i_88_ = 0; i_88_ < bufferedimage_84_.getWidth(null);
		     i_88_ += i_85_)
		    graphics.drawImage(bufferedimage_81_, i_88_, i_87_, null);
	    }
	    graphics.dispose();
	    bufferedimage_81_.flush();
	    if (((ImageFilter) this).filteredImage != null)
		((ImageFilter) this).filteredImage.flush();
	    affinetransform
		= (AffineTransform.getScaleInstance
		   (((double) bufferedimage.getWidth(null)
		     / (double) bufferedimage_84_.getWidth(null)),
		    ((double) bufferedimage.getHeight(null)
		     / (double) bufferedimage_84_.getHeight(null))));
	    affinetransformop = new AffineTransformOp(affinetransform, 1);
	    ((ImageFilter) this).filteredImage
		= affinetransformop.filter(bufferedimage_84_, null);
	    bufferedimage_84_.flush();
	}
    }
    
    void applyPixelate(double d) {
	BufferedImage bufferedimage = ((ImageFilter) this).filteredImage;
	double d_89_ = (Math.abs(d) + 10.0) / 10.0;
	d_89_ = Math.min(d_89_,
			 (double) Math.min(bufferedimage.getWidth(null),
					   bufferedimage.getHeight(null)));
	if (!(d_89_ <= 1.0)) {
	    AffineTransform affinetransform
		= AffineTransform.getScaleInstance(1.0 / d_89_, 1.0 / d_89_);
	    AffineTransformOp affinetransformop
		= new AffineTransformOp(affinetransform, 2);
	    BufferedImage bufferedimage_90_
		= affinetransformop.filter(bufferedimage, null);
	    affinetransform = AffineTransform.getScaleInstance(d_89_, d_89_);
	    affinetransformop = new AffineTransformOp(affinetransform, 1);
	    ((ImageFilter) this).filteredImage
		= affinetransformop.filter(bufferedimage_90_,
					   ((ImageFilter) this).filteredImage);
	    bufferedimage_90_.flush();
	}
    }
}
