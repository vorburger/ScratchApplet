/* LContext - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.PrintStream;
import java.util.Hashtable;

class LContext
{
    static final boolean isApplet = true;
    Hashtable oblist = new Hashtable();
    Hashtable props = new Hashtable();
    MapList iline;
    Symbol cfun;
    Symbol ufun;
    Object ufunresult;
    Object juststop = new Object();
    boolean mustOutput;
    boolean timeToStop;
    int priority = 0;
    Object[] locals;
    String errormessage;
    String codeBase;
    String projectURL;
    PlayerCanvas canvas;
    Sprite who;
    Thread logoThread;
    PrintStream tyo;
    boolean autostart;
}
