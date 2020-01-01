//C:\Users\devin\eclipse-workspace
//import com.mycompany.ball2.MyPanel;
package com.mycompany.ball2;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pool.Helper;

class Ball2 {
    public MyPanel myPanel;
    public double xx, yy;    // �ｿｽ{�ｿｽ[�ｿｽ�ｿｽ�ｿｽﾌ抵ｿｽ�ｿｽS�ｿｽ�ｿｽ�ｿｽW
    public double dx, dy; // �ｿｽ�ｿｽ�ｿｽx=X�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽY�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ移難ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
    public int  rr;          // �ｿｽ�ｿｽ�ｿｽa
    public Color color;
    public double GG; // �ｿｽd�ｿｽﾍ会ｿｽ�ｿｽ�ｿｽ�ｿｽx
    public double KK; // �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽW�ｿｽ�ｿｽ
    public int number;
    public double ddy,ddx;
    public boolean iswhiteball;
    Ball2(MyPanel mp, int id, double x, double y,int rr,boolean iswhite) {
        myPanel = mp;
        iswhiteball=iswhite;
        init();
        xx = x; yy = y;
       // this.dx = dx; this.dy = dy;
        this.rr = rr; 
        number=id;
    }
    
     Ball2(MyPanel mp, int id, double x, double y,int rr) {
        myPanel = mp;
        init();
        xx = x; yy = y;
       // this.dx = dx; this.dy = dy;
        this.rr = rr; 
        number=id;
    }
    
 
    Ball2(MyPanel mp) {
        myPanel = mp;
        init();
    }
    Ball2(MyPanel mp, double x, double y, double dx, double dy, 
         double rr, Color col) {
        myPanel = mp;
        init();
        xx = x; yy = y;
        this.dx = dx; this.dy = dy;
        this.rr = (int)rr; color = col;
    }
    

    
    
	void init() {
    	xx = 0.0; yy = 0.0;
    	dx = 0; dy = 0;
    	ddy=0; ddx=0;
    	rr = 10;
    	
    }
    double getX() { return xx; }
    double getY() { return yy; }
    double getDX() { return dx; }
    double getDY() { return dy; }
    double getR() { return rr; }
    void setX(double x) { xx = x; }
    void setY(double y) { yy = y; }
    void setXY(double x, double y) { xx = x; yy = y; }
    void setG(double g) { GG = g; }
    void setK(double k) { KK = k; }

    // �ｿｽ�ｿｽﾊ搾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾖ移難ｿｽ
    void goHome() {
        xx = rr; 
        yy = myPanel.getHeight() - rr; 
    }
    void setDX(double dx) { this.dx = dx; }
    void setDY(double dy) { this.dy = dy; }
    void setDXY(double dx, double dy) { this.dx = dx; this.dy = dy; }
    void setR(double r) { rr = (int)r; }
    void setColor(Color col) {
        color = col;
    }
    // �ｿｽ`�ｿｽ�ｿｽ
//    void draw(Graphics g) {
//        Color backup = g.getColor();
//        g.setColor(color);
//        int d = (int)(rr + rr);
//        g.fillOval((int)(xx - rr), (int)(yy - rr), d, d);
//        g.setColor(backup);
//    }

     public void draw(Graphics g)
    {    Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(Helper.BALL_COLORS.get(number)[0]);
        graphics2D.fillOval((int) xx, (int) yy,(int) (2 * rr), (int)(2 * rr));
        graphics2D.setColor(Helper.BALL_COLORS.get(number)[1]);
        graphics2D.fillOval((int) (xx + rr / 2), (int) (yy + rr / 2), rr, rr);
        graphics2D.setColor(Helper.BALL_COLORS.get(number)[2]);
        graphics2D.setFont(new Font("Arial Bold", Font.BOLD, 8));
        graphics2D.drawString(String.valueOf(number), (float) (xx + (number >= 10 ? rr - 4.6 : rr - 2.6)), (float) (yy + rr + 2.6));
        
        //graphics2D.drawString((int) getCenterX() + "-" + (int) x + ":" + (int) getCenterY() + "-" + (int) y, (float) x, (float) y);

    }
    // �ｿｽ�ｿｽ�ｿｽW�ｿｽX�ｿｽV

    
    public double getcenterx() {
    	return this.xx+rr;
    }
    
    public double getcentery() {
    	return this.yy+rr;
    	
    }


	public void startFriction() {
		// TODO Auto-generated method stub
			double k = dx * dx + dy * dy; // jarak

	        k = Math.sqrt(k) * Helper.FPS / 2; // k =akar jarak

	        if (k == 0) return;

	        ddy = -dy*2 / k;//perlambat 1/jarak
	        ddx = -dx*2 / k;
	    
	}
	
	
    public void update()
    {
        if (dy != 0 || dx != 0)
        {
            yy += dy;
            xx += dx;

            if(Helper.FR) handleBallFriction();
        }
    }
    

    private void handleBallFriction()
    {
        dy += ddy;
        dx += ddx;

        if (dx > 0 == ddx > 0)
        {
            dx = 0;
        }

        if (dy > 0 == ddy > 0)
        {
            dy = 0;
        }
    }
    
 


	public void handleBounds() {
		// TODO Auto-generated method stub
		boolean bound = false;

        if (xx > Helper.BX + Helper.SW - (Helper.TB *2))
        {
            dx = -Math.abs(dx);
            ddx = Math.abs(ddx); // krn ddx=negative jadi kekanan bair negative mengurangi
            bound = true;
        }
        else if (xx < Helper.BX + Helper.TB)
        {
            dx = Math.abs(dx);
            ddx =- Math.abs(ddx); // karena melawan arah ke kiri jadi dikurangin
            bound = true;
        }

        if (yy < Helper.BY + Helper.TB)
        {
            dy = Math.abs(dy);
            ddy = -Math.abs(ddy);
            bound = true;
        }
        else if (yy > Helper.BY + Helper.SH - (Helper.TB + 2 * rr))
        {
            dy = -Math.abs(dy);
            ddy = Math.abs(ddy); //making ke bawah makin positif k
            bound = true;
        }

	}



}
