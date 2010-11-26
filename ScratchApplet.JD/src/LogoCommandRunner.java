import java.io.PrintStream;

class LogoCommandRunner
  implements Runnable
{
  Object[] listtorun;
  LContext context;
  boolean silent;

  static void startLogoThread(String paramString, LContext paramLContext)
  {
    stopLogoThread(paramLContext);
    paramLContext.logoThread = new Thread(new LogoCommandRunner(paramString, paramLContext, true), "Logo");
    paramLContext.logoThread.start();
  }

  static void stopLogoThread(LContext paramLContext) {
    if (paramLContext.logoThread == null) return;
    paramLContext.timeToStop = true;
    try { paramLContext.logoThread.join(); } catch (InterruptedException localInterruptedException) {
    }paramLContext.logoThread = null;
  }

  LogoCommandRunner(String paramString, LContext paramLContext, boolean paramBoolean)
  {
    this.listtorun = Logo.parse(paramString, paramLContext);
    this.context = paramLContext;
    this.silent = paramBoolean;
  }

  public void run() {
    synchronized (this.context) {
      if (this.context.logoThread == null) this.context.logoThread = Thread.currentThread();
      if (this.context.logoThread != Thread.currentThread()) return;
      String str = Logo.runToplevel(this.listtorun, this.context);
      if ((this.context.tyo != null) && (!this.context.timeToStop)) {
        if (str != null) {
          this.context.tyo.println(str);
          this.context.errormessage = str;
        }
        if (!this.silent) this.context.tyo.println("ok");
      }
      this.context.logoThread = null;
    }
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     LogoCommandRunner
 * JD-Core Version:    0.6.0
 */