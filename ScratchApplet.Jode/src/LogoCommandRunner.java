/* LogoCommandRunner - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

class LogoCommandRunner implements Runnable
{
    Object[] listtorun;
    LContext context;
    boolean silent;
    
    static void startLogoThread(String string, LContext lcontext) {
	stopLogoThread(lcontext);
	((LContext) lcontext).logoThread
	    = new Thread(new LogoCommandRunner(string, lcontext, true),
			 "Logo");
	((LContext) lcontext).logoThread.start();
    }
    
    static void stopLogoThread(LContext lcontext) {
	if (((LContext) lcontext).logoThread != null) {
	    ((LContext) lcontext).timeToStop = true;
	    try {
		((LContext) lcontext).logoThread.join();
	    } catch (InterruptedException interruptedexception) {
		/* empty */
	    }
	    ((LContext) lcontext).logoThread = null;
	}
    }
    
    LogoCommandRunner(String string, LContext lcontext, boolean bool) {
	((LogoCommandRunner) this).listtorun = Logo.parse(string, lcontext);
	((LogoCommandRunner) this).context = lcontext;
	((LogoCommandRunner) this).silent = bool;
    }
    
    public void run() {
	object = object_1_;
	break;
    }
}
