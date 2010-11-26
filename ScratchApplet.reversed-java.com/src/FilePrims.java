// Decompiled by:       Fernflower v0.6
// Date:                26.11.2010 23:09:28
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

class FilePrims extends Primitives {

   String readtext;
   int textoffset;
   static String[] primlist = new String[]{"filetostring", "1", "resourcetostring", "1", "load", "1", "stringtofile", "2", "file?", "1", "setread", "1", "readline", "0", "eot?", "0", "lineback", "0", "filenamefrompath", "1", "dirnamefrompath", "1", "dir", "1"};
   static Class class$FilePrims;


   public String[] primlist() {
      return primlist;
   }

   public Object dispatch(int var1, Object[] var2, LContext var3) {
      switch(var1) {
      case 0:
         return this.prim_filetostring(var2[0], var3);
      case 1:
         return this.prim_resourcetostring(var2[0], var3);
      case 2:
         return this.prim_load(var2[0], var3);
      case 3:
         return this.prim_stringtofile(var2[0], var2[1], var3);
      case 4:
         return this.prim_filep(var2[0], var3);
      case 5:
         return this.prim_setread(var2[0], var3);
      case 6:
         return this.prim_readline(var3);
      case 7:
         return this.prim_eot(var3);
      case 8:
         return this.prim_lineback(var3);
      case 9:
         return this.prim_filenamefrompath(var2[0], var3);
      case 10:
         return this.prim_dirnamefrompath(var2[0], var3);
      case 11:
         return this.prim_dir(var2[0], var3);
      default:
         return null;
      }
   }

   Object prim_filetostring(Object var1, LContext var2) {
      String var3 = Logo.prs(var1);
      return this.fileToString(var3, var2);
   }

   Object prim_resourcetostring(Object var1, LContext var2) {
      String var3 = Logo.prs(var1);
      return this.resourceToString(var3, var2);
   }

   Object prim_load(Object var1, LContext var2) {
      String var3 = Logo.prs(var1);
      String var4 = System.getProperty("file.separator");
      String var5 = this.resourceToString(var3 + ".logo", var2);
      Logo.readAllFunctions(var5, var2);
      return null;
   }

   String resourceToString(String var1, LContext var2) {
      InputStream var3 = (class$FilePrims == null?(class$FilePrims = class$("FilePrims")):class$FilePrims).getResourceAsStream(var1);
      BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
      StringWriter var5 = new StringWriter();
      PrintWriter var6 = new PrintWriter(new BufferedWriter(var5), true);

      try {
         String var7;
         while((var7 = var4.readLine()) != null) {
            var6.println(var7);
         }

         String var8 = var5.toString();
         return var8;
      } catch (IOException var9) {
         Logo.error("Can\'t open file " + var1, var2);
         return null;
      }
   }

   String fileToString(String var1, LContext var2) {
      byte[] var3 = null;
      Object var4 = null;

      try {
         File var5 = new File(var1);
         int var6 = (int)var5.length();
         FileInputStream var7 = new FileInputStream(var5);
         DataInputStream var8 = new DataInputStream(var7);
         var3 = new byte[var6];
         var8.readFully(var3);
         var7.close();
      } catch (IOException var9) {
         Logo.error("Can\'t open file " + var1, var2);
      }

      return new String(var3);
   }

   Object prim_stringtofile(Object var1, Object var2, LContext var3) {
      String var4 = Logo.prs(var1);
      String var5 = Logo.prs(var2);

      try {
         FileWriter var6 = new FileWriter(var4);
         var6.write(var5, 0, var5.length());
         var6.close();
      } catch (IOException var7) {
         Logo.error("Can\'t write file " + var4, var3);
      }

      return null;
   }

   Object prim_filep(Object var1, LContext var2) {
      String var3 = Logo.prs(var1);
      return new Boolean((new File(var3)).exists());
   }

   Object prim_setread(Object var1, LContext var2) {
      this.readtext = Logo.prs(var1);
      this.textoffset = 0;
      return null;
   }

   Object prim_readline(LContext var1) {
      String var2 = "";
      int var3 = this.readtext.indexOf("\n", this.textoffset);
      if(var3 == -1) {
         if(this.textoffset < this.readtext.length()) {
            var2 = this.readtext.substring(this.textoffset, this.readtext.length());
            this.textoffset = this.readtext.length();
         }
      } else {
         var2 = this.readtext.substring(this.textoffset, var3);
         this.textoffset = var3 + 1;
      }

      if(var2.length() == 0) {
         return var2;
      } else {
         if(var2.charAt(var2.length() - 1) == 13) {
            var2 = var2.substring(0, var2.length() - 1);
         }

         return var2;
      }
   }

   Object prim_eot(LContext var1) {
      return new Boolean(this.textoffset >= this.readtext.length());
   }

   Object prim_lineback(LContext var1) {
      int var2 = this.readtext.lastIndexOf("\n", this.textoffset - 2);
      if(var2 < 0) {
         this.textoffset = 0;
      } else {
         this.textoffset = var2 + 1;
      }

      return null;
   }

   Object prim_filenamefrompath(Object var1, LContext var2) {
      return (new File(Logo.prs(var1))).getName();
   }

   Object prim_dirnamefrompath(Object var1, LContext var2) {
      File var3 = new File(Logo.prs(var1));
      return var3.isDirectory()?var3.getPath():var3.getParent();
   }

   Object prim_dir(Object var1, LContext var2) {
      String[] var3 = (new File(Logo.prs(var1))).list();
      return var3 == null?new Object[0]:var3;
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

}
