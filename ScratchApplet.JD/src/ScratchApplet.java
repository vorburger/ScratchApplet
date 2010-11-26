import java.net.URL;
import javax.swing.JApplet;

public class ScratchApplet extends JApplet
{
  LContext lc;

  public static void setSensorValue(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > 15)) return;
    PlayerPrims.sensorValues[paramInt1] = paramInt2;
  }

  public static int getSensorValue(int paramInt) {
    if ((paramInt < 0) || (paramInt > 15)) return 0;
    return PlayerPrims.sensorValues[paramInt];
  }

  public void init() {
    String str1 = getCodeBase().toString();
    String str2 = getParameter("project");
    String str3 = str2 != null ? (str3 = str1 + str2) : null;
    String str4 = getParameter("autostart");

    boolean bool = true;
    if (str4 != null) {
      if (str4.equalsIgnoreCase("false")) bool = false;
      if (str4.equalsIgnoreCase("no")) bool = false;
    }
    try
    {
      Thread.sleep(50L); } catch (InterruptedException localInterruptedException) {
    }this.lc = PlayerPrims.startup(str1, str3, getContentPane(), bool);
    this.lc.tyo = System.out;
  }
  public void destroy() {
    PlayerPrims.shutdown(this.lc);
  }
}

/* Location:           /Users/vorburger/dev/workspace.Scratch4Android/ScratchApplet.jar
 * Qualified Name:     ScratchApplet
 * JD-Core Version:    0.6.0
 */