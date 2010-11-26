/* ScratchApplet - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import javax.swing.JApplet;

public class ScratchApplet extends JApplet
{
    LContext lc;
    
    public static void setSensorValue(int i, int i_0_) {
	if (i >= 0 && i <= 15)
	    PlayerPrims.sensorValues[i] = i_0_;
    }
    
    public static int getSensorValue(int i) {
	if (i < 0 || i > 15)
	    return 0;
	return PlayerPrims.sensorValues[i];
    }
    
    public void init() {
	String string = getCodeBase().toString();
	String string_1_ = getParameter("project");
	String string_2_
	    = string_1_ != null ? string_2_ = string + string_1_ : null;
	String string_3_ = getParameter("autostart");
	boolean bool = true;
	if (string_3_ != null) {
	    if (string_3_.equalsIgnoreCase("false"))
		bool = false;
	    if (string_3_.equalsIgnoreCase("no"))
		bool = false;
	}
	try {
	    Thread.sleep(50L);
	} catch (InterruptedException interruptedexception) {
	    /* empty */
	}
	((ScratchApplet) this).lc
	    = PlayerPrims.startup(string, string_2_, getContentPane(), bool);
	((LContext) ((ScratchApplet) this).lc).tyo = System.out;
    }
    
    public void destroy() {
	PlayerPrims.shutdown(((ScratchApplet) this).lc);
    }
}
