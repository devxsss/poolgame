package com.mycompany.ball2;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    protected static final int FPS = 120;
    protected static final int BX = 100, BY = 100;  // Background x y
    protected static final int SW  = 640, SH = 358; // Screen width and height
    protected static final int     TB = 20; // Table border width
    protected static final int     HR = 14; // Hole radius
    protected static       boolean FR = true; // Friction on/off
    protected static       boolean HA = true; // Holes on/off

    protected static final int HM = 1; // Hole margin from border

    protected static final Color BC         = new Color(136, 49, 23); // Border color
    protected static final Color TC         = new Color(60, 180, 7); // Table color
    protected static final Color BALL_WHITE = new Color(255, 247, 200); // Milky white
    protected static final Color HAC        = new Color(239, 193, 82); // Hole arc color

   static final HashMap<String,int[]>holes=new HashMap<>(){{
        put("TL",new int[]{Helper.BX,Helper.BY});
        put("TM",new int[]{Helper.BX+Helper.SW/2-Helper.TB,Helper.BY});
        put("TR",new int[]{Helper.BX+Helper.SW-Helper.TB-10,Helper.BY});
        put("TRB",new int[]{Helper.BX,Helper.BY+Helper.SH-Helper.TB-10});
        put("TMB",new int[]{Helper.BX+Helper.SW/2,Helper.BY+Helper.SH-Helper.TB-10});
        put("TLB",new int[]{Helper.BX+Helper.SW-Helper.TB-10,Helper.BY+Helper.SH-Helper.TB-10});
    }};   
   
   protected static final HashMap<Integer, Color[]> BALL_COLORS = new HashMap<Integer, Color[]>()
    {{
        put(0, new Color[]{BALL_WHITE, BALL_WHITE, BALL_WHITE, null});
        put(1, new Color[]{new Color(245, 195, 18), BALL_WHITE, Color.BLACK, null});
        put(2, new Color[]{new Color(49, 85, 171), BALL_WHITE, Color.BLACK, null});
        put(3, new Color[]{new Color(255, 69, 0), BALL_WHITE, Color.BLACK, null});
        put(4, new Color[]{new Color(82, 60, 171), BALL_WHITE, Color.BLACK, null});
        put(5, new Color[]{new Color(214, 19, 29), BALL_WHITE, Color.BLACK, null});
        put(6, new Color[]{new Color(37, 123, 61), BALL_WHITE, Color.BLACK, null});
        put(7, new Color[]{new Color(128, 0, 0), BALL_WHITE, Color.BLACK, null});
        put(8, new Color[]{new Color(37, 123, 61),BALL_WHITE, Color.BLACK, null});
        put(9, new Color[]{new Color(245, 195, 18), BALL_WHITE, Color.BLACK});
        put(10, new Color[]{new Color(49, 85, 171), BALL_WHITE, Color.BLACK});
        put(11, new Color[]{new Color(255, 69, 0), BALL_WHITE, Color.BLACK});
        put(12, new Color[]{ new Color(82, 60, 171), BALL_WHITE, Color.BLACK});
        put(13, new Color[]{new Color(214, 19, 29), BALL_WHITE, Color.BLACK});
        put(14, new Color[]{new Color(37, 123, 61), BALL_WHITE, Color.BLACK});
        put(15, new Color[]{ new Color(128, 0, 0), BALL_WHITE, Color.BLACK});
    }};
    

}
