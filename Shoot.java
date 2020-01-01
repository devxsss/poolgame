/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ball2;
import static java.lang.Math.abs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import pool.Helper;


//import pool.Helper;

//import pool.Ball;
/**
 *
 * @author devin
 */
public final class Shoot implements MouseListener, MouseMotionListener,ActionListener{
    private Ball2 ball;
    private MyPanel pinball;
    private boolean mouseDown=false;
    volatile private boolean isRunning  = false;
   protected boolean aiming=false;
    private int aim_line_x1, aim_line_x2, aim_line_y1, aim_line_y2;
    private int cue_x1, cue_x2, cue_y1, cue_y2, cue_head_x1, cue_head_y1, cue_middle_x1, cue_middle_y1;
    boolean cue=true;
    private int aim_oval_x=0,aim_oval_y=0;
	
	private int aim_r=10;
    private static float[] fracs = {0.1f, 0.2f, 0.3f};
    Shoot(MyPanel p){
        this.pinball=p;
        p.addMouseListener(this);
        p.addMouseMotionListener(this);
    }
    
    public void draw(Graphics2D g){
    	 Ball2 b = pinball.balls.get(pinball.getcurrentindexwhiteball(pinball.balls));
    
    
        
        g.setColor(Helper.BC.darker().darker());
        g.setStroke(new BasicStroke(4));
        g.drawLine(cue_x1, cue_y1, cue_x2, cue_y2);
    	g.setColor(Color.RED);
        g.drawLine(cue_x2, cue_y2, cue_x2, cue_y2);
        //g.setColor(Helper.BALL_WHITE);
        //g.drawRect(Helper.SW+Helper.BX+20,Helper.Sh/2,20,200);
    }
    
         @Override public void mouseClicked(MouseEvent e)
    {		 Ball2 b = pinball.balls.get(pinball.getcurrentindexwhiteball(pinball.balls));
 
   if(aiming==true && !pinball.movingwhiteball  && !pinball.readyForShoot) {
     	//pinball.readyForShoot()
     //pinball.readyForShoot=false;
     	double vx=aim_oval_x-b.xx; // xlines  minus b.xx(center)
     	double vy=aim_oval_y-b.yy; // ylinex minus b.yy(center)
     	double dist=Math.sqrt((vx*vx)+(vy*vy));
     	b.dx= (vx / dist)*600/ Helper.FPS ;
     	b.dy= vy / dist *600/ Helper.FPS;
     	  b.startFriction();
     	//gamestart();
     	  
           aiming = false;
           pinball.repaint();
	}
 
    }
//         

         @Override public void mousePressed(MouseEvent e)
    {		 if (!pinball.readyForShoot)
    			{
    				return;
    			}
    		 Ball2 b;
        	 for(int i=0;i<pinball.balls.size();i++) {
        		 b=pinball.balls.get(i);
        		 
        		 if(i==pinball.indexOfWhiteBall && !pinball.movingwhiteball) {
        			 aiming=true;
        		 }
        	 }
             if (pinball.readyForShoot && aiming)
             {
                 if (e.getButton() == MouseEvent.BUTTON1)
                 {
                     mouseDown = true;
                     initThread();
                 }
             }
        	 
        
        	
        	 
    }
           @Override public void mouseEntered(MouseEvent e)
    {
    }

    @Override public void mouseExited(MouseEvent e)
    {

    }
    
    @Override public void mouseMoved(MouseEvent e) {
   	 Ball2 b = pinball.balls.get(pinball.getcurrentindexwhiteball(pinball.balls));
   	 if (pinball.indexOfWhiteBall == -1) return;
   	
    
    	if(pinball.movingwhiteball) {
 
    		//pinball.movingwhiteball=false;
    		
    		
    		
    		
    	}else if(aiming && !pinball.movingwhiteball) {

    		aim_line_x1 = e.getX() ;
            aim_line_y1 = e.getY();
            aim_oval_x=e.getX()-10;
            aim_oval_y=e.getY()-20;
    		
    		double angle=Math.atan2( aim_line_y1-b.getcentery() ,aim_line_x1-b.getcenterx());
    		  //System.out.println(aim_line_y1 -b.getcentery());
    		//System.out.println(b.getcenterx());
    		cue_x2=(int)(b.getcenterx()-Math.cos(angle)*20);//getcenterx= white ball center 
    		//- jarak dari center ke ujung tongkat
    	//pokonya kaya segitiga, x axis=x, y axis=y
    		cue_y2=(int)(b.getcentery()-Math.sin(angle)*20);
    		cue_x1=(int)((cue_x2)-200*Math.cos(angle));
    		cue_y1=(int)((cue_y2)-200*Math.sin(angle));
    		
    		cue=true;
    		//System.out.println(pinball.readyForShoot);
    		pinball.readyForShoot=false;
    		
    		
    		
    	}
    	
    }
    @Override public void mouseReleased(MouseEvent e)
    {
        Ball2 b = pinball.balls.get(pinball.getcurrentindexwhiteball(pinball.balls));
        if (!pinball.readyForShoot)
        {
            return;
        }
        cue =false;
        if(pinball.movingwhiteball) {
        	
        }else if(aiming==true) {
        	//pinball.readyForShoot()
        	pinball.readyForShoot=false;
        	double vx=aim_oval_x-b.xx; // xlines  minus b.xx(center)
        	double vy=aim_oval_y-b.yy; // ylinex minus b.yy(center)
        	double dist=Math.sqrt((vx*vx)+(vy*vy));
        	b.dx= vx / dist * 5;
        	b.dy= vy / dist * 5;
        	  b.startFriction();
              aiming = false;
        	
        }
     
        
        
    }
    @Override public void mouseDragged(MouseEvent e){
    	 Ball2 b = pinball.balls.get(pinball.getcurrentindexwhiteball(pinball.balls));
      //  Ball2 b = pinball.balls.get(pinball.indexOfWhiteBall);
        int midX=e.getX();
        int midY=e.getY();
        
        //penting banget mouse draggnya jadi buat bola putih gak kluar frame
        if(!pinball.movingwhiteball && pinball.whiteballfallen==false) {
        	b.xx=midX;
        	b.yy=midY;
        	
    		double nxx=Helper.SW+Helper.BX;
    		double nyy=Helper.BY+Helper.SH;
    		double nxxright=Helper.BX;
    		double nytopw=Helper.BY;
    		if(b.xx<nxxright ) {
    			b.xx=Helper.BX+2*10;
    		}else if(b.yy<nytopw) {
    			b.yy=Helper.BY+2*10;
    		}
    		else if(b.xx>nxx) {
    			b.xx=Helper.SW+Helper.BX-40;
    			
    		}else if(b.yy>nyy) {
    			
    			b.yy=Helper.BY+Helper.SH-40;
    		}
        	pinball.movingwhiteball=true;
        	
    }else if(pinball.movingwhiteball) {
    	
    }
    		//pinball.movingwhiteball=false;
    		
    		
        
        
    
}
    

    private void initThread()
    {
            new Thread()
            {
                public void run()
                {
                    while (mouseDown)
                    {
                        long startTime, timeMillis, waitTime;
                        long targetTime = 1000 / Helper.FPS;

                        startTime = System.nanoTime();

                        timeMillis = (System.nanoTime() - startTime) / 1000000;
                        waitTime = abs(targetTime - timeMillis);

                        try
                        {
                            Thread.sleep(waitTime);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

           
                    }

                    isRunning = false;
                }
            }.start();
         //   pinball.tableupdate();
        }
    }
    

