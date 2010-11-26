// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:29
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.PrintStream;
import java.util.Hashtable;

class LContext {

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
