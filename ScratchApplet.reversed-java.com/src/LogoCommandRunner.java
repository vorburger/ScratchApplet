// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:31
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com


class LogoCommandRunner implements Runnable {

   Object[] listtorun;
   LContext context;
   boolean silent;


   static void startLogoThread(String var0, LContext var1) {
      stopLogoThread(var1);
      var1.logoThread = new Thread(new LogoCommandRunner(var0, var1, true), "Logo");
      var1.logoThread.start();
   }

   static void stopLogoThread(LContext var0) {
      if(var0.logoThread != null) {
         var0.timeToStop = true;

         try {
            var0.logoThread.join();
         } catch (InterruptedException var2) {
            ;
         }

         var0.logoThread = null;
      }
   }

   LogoCommandRunner(String var1, LContext var2, boolean var3) {
      this.listtorun = Logo.parse(var1, var2);
      this.context = var2;
      this.silent = var3;
   }

   public void run() {
      LContext var1 = this.context;
      synchronized(this.context) {
         if(this.context.logoThread == null) {
            this.context.logoThread = Thread.currentThread();
         }

         if(this.context.logoThread == Thread.currentThread()) {
            String var2 = Logo.runToplevel(this.listtorun, this.context);
            if(this.context.tyo != null && !this.context.timeToStop) {
               if(var2 != null) {
                  this.context.tyo.println(var2);
                  this.context.errormessage = var2;
               }

               if(!this.silent) {
                  this.context.tyo.println("ok");
               }
            }

            this.context.logoThread = null;
         }
      }
   }
}
