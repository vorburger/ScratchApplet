// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:34
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import javax.swing.JApplet;

public class ScratchApplet extends JApplet {

   LContext lc;


   public static void setSensorValue(int var0, int var1) {
      if(var0 >= 0 && var0 <= 15) {
         PlayerPrims.sensorValues[var0] = var1;
      }
   }

   public static int getSensorValue(int var0) {
      return var0 >= 0 && var0 <= 15?PlayerPrims.sensorValues[var0]:0;
   }

   public void init() {
      String var1 = this.getCodeBase().toString();
      String var2 = this.getParameter("project");
      String var3 = var2 != null?var1 + var2:null;
      String var4 = this.getParameter("autostart");
      boolean var5 = true;
      if(var4 != null) {
         if(var4.equalsIgnoreCase("false")) {
            var5 = false;
         }

         if(var4.equalsIgnoreCase("no")) {
            var5 = false;
         }
      }

      try {
         Thread.sleep(50L);
      } catch (InterruptedException var7) {
         ;
      }

      this.lc = PlayerPrims.startup(var1, var3, this.getContentPane(), var5);
      this.lc.tyo = System.out;
   }

   public void destroy() {
      PlayerPrims.shutdown(this.lc);
   }
}
