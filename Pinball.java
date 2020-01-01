package com.mycompany.ball2;

	import javax.swing.JFrame;
import javax.swing.JPanel;
	import javax.swing.Timer;

import pool.Ball;
import pool.Helper;

import static java.lang.Math.abs;

import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

	class Pinball extends JFrame {
	    public static void main(String[] args) {
	        JFrame f = new JFrame();
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setSize(Helper.SW+Helper.BX*2,Helper.SH+2*Helper.BX+Helper.TB);
	        f.setTitle("Pinball");
	        Container contentPane = f.getContentPane();
	        MyPanel p = new MyPanel();
	        contentPane.add(p);
	        f.setVisible(true);
	       // p.start();
	    }
	}
	class MyPanel extends JPanel implements ActionListener,Runnable {
	    //final Ball2 ball;
	  //  final Timer timer;
             private Shoot Shoot;
	    protected boolean isRunning       = true;
		int r=10;
             protected ArrayList<Ball2> balls;
             private Thread thread;
			public boolean readyForShoot=true;
			public boolean movingwhiteball=true;
			protected int indexOfWhiteBall = -1;
			public boolean whiteballfallen=false;
			 private MyPanel pinball;
			 private int totalscore=0;
			//final Timer timer;
             public MyPanel(){
            	 pinball=this;
            	 generateBalls();
            	// timer = new Timer(50, this);
            	 Shoot=new Shoot(this);
            	 if(thread==null) { //abis start ke run()
            		 thread=new Thread(this);
            		 thread.start();
            		 
            		 
            	 }
             }
             @Override
//             public void addNotify()
//             {
//                 super.addNotify();
//                 if (thread == null)
//                 {
//                     thread = new Thread(this);
//                     thread.start();
//                 }
//             } 
             
//             public void timerstart() {
//            
//            		timer.start();
//            	
//             }
//             
            public void run(){
                generateBalls();
                
                long startTime, timeMillis, waitTime;
                long targetTime = 1000 / Helper.FPS;

                while (isRunning)
                {
                    startTime = System.nanoTime();

                   updateBalls();
                   tableupdate();
                   
                   repaint();
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
              
                
                
            }
             
             private void updatescore(int num) {
            	 totalscore+=num;
             }
             
             public int getscore(int num) {
            	 return totalscore;
             }
            
            private void tableupdate() {
				// TODO Auto-generated method stub
            	int totalBall = balls.size();

                if(totalBall == 0 || (totalBall == 1 && balls.get(0).number == 0))
                    createNewGame();
              
            	  readyForShoot =false;
            	  movingwhiteball=false;
                  Shoot.aiming = true;
			}
            
            public void createNewGame()
            {
                generateBalls();
                movingwhiteball = true;
            }
            
			private void updateBalls()
            {

                Ball2 b1, b2;

                for (int i = 0; i < balls.size(); i++)
                {

                    b1 = balls.get(i);
                    
               
					if(whiteballfallen) {
                    	//Shoot.mouseMoved(e);
						  int xx=MouseInfo.getPointerInfo().getLocation().x;
			              int yy=MouseInfo.getPointerInfo().getLocation().y;
			               
						  redrawwhiteball(xx,yy);

                    }

                    b1.update();
                    if(ifpocket(b1)) {
                    	b1.dx = 0;
                        b1.dy = 0;
                        int num=b1.number;
                        updatescore(num);
                        balls.remove(i);
                        if(b1.number==0) {
                        	whiteballfallen=true;
                        
                        }
                    }else {
                    
                   b1.handleBounds();

                       for (int b = i + 1; b < balls.size(); b++)
                        {
                            b2 = balls.get(b);
                            if (handleBallCollision(b1, b2))
                            {
                                b1.startFriction();
                                b2.startFriction();
                            }
                            
                        }

                    }
            }
            }
            private void redrawwhiteball(int xx,int yy){
            
            	balls.add(new Ball2(pinball,0,xx,yy,r,true));
            	pinball.movingwhiteball=false;
            	
            	pinball.whiteballfallen=false;
            }
            
            
            private boolean handleBallCollision(Ball2 b1, Ball2 b2) {
				// TODO Auto-generated method stub
            	  double dx = b1.getcenterx() - b2.getcenterx();
                  double dy = b1.getcentery() - b2.getcentery();
                  double dist = dx * dx + dy * dy;
                //  double unitvec=(dx,dy)/Math.sqrt((dist));
             
                  
                  
                 
                  if (dist <= (b1.rr + b2.rr) * (b1.rr + b2.rr))
                  {
                      double xSpeed    = b2.dx - b1.dx;
                      double ySpeed    = b2.dy - b1.dy;
                      double getVector = dx * xSpeed + dy * ySpeed;//dot product dx,dy * speed
                  
                      if (getVector > 0)
                      {
                    	  
                    	 
                      	  double newX = dx *getVector/ dist;
                          double newY = dy *getVector / dist;
                          b1.dx += newX;
                          b1.dy += newY;
                          b2.dx -= newX ;
                          b2.dy -= newY;


                          return true;
				       }
                  }
                      
					return false;
                  
				
				
                  	
				
			}
			private double[] rowY(int rownum) {
                
                switch(rownum) {
         	case 0: return new double[] {0};
        	case 1:return new double[] {-r,r};
        	case 2:return new double[] {-2*r,0,2*r};
        	 case 3: return new double[] {-3*r, -r, r, 3*r};
             case 4: return new double[] {-4*r, -2*r, 0, 2*r, 4*r};
             case 5:return new double[] {Helper.BX + Helper.SW / 4 - r};
             default:throw new IllegalArgumentException("no more than 5");
                }
            }
            
            private double rowx(int rowNumber) {
            	return rowNumber * (Math.sqrt(5) * r);
            }
            
            private void generateBalls(){

           balls=new ArrayList<Ball2>();
           int ballid=0;
           indexOfWhiteBall=0;
           if (balls.size()==0)
           {
              int x= Helper.BX + Helper.SW / 4 - r;
              int y = Helper.BY + Helper.SH / 2 - r;
              Ball2 balle=new Ball2(pinball,ballid,(Helper.BX + Helper.SW / 4 - r),Helper.BY + Helper.SH / 2 - r,r);
              balls.add(balle);
              balls.get(0).iswhiteball=true;
              ballid++;
              
           }
           	for(int i=0;i<5;i++) {
        	  double y= rowx(i);
        	  for(double x:rowY(i)) {
        	
        		  Ball2 balle=new Ball2(pinball,ballid,(Helper.BX + (2.5*Helper.SW)/4 ) +y,(Helper.BY + Helper.SH / 2) +x,r);
        		  balls.add(balle);
        		  ballid++;
        		  System.out.print(balle + " ");
                  
        	  }
          }
        }
		
                
            
//	    public MyPanel() {
//	        timer = new Timer(50, this);
//	        ball = new Ball2(this);
//               // Shoot=new Shoot(this);
//	    }
//	    public void start() {
//	    
//	        ball.goHome();
//
//	        timer.start();
//	    }
	    public void paint(Graphics g) {
	        super.paint(g);
                Graphics2D graphics2D=(Graphics2D)g;
	        //ball.draw(g);
	        tablerender(graphics2D);
	    }

	    private void tablerender(Graphics2D g){
	    	
	    	g.fillRect(0,0,Helper.SW+Helper.BX*2,Helper.SH+Helper.BY*2);
                
	    	RoundRectangle2D roundedRectangle=new RoundRectangle2D.Float(Helper.BX,Helper.BY,Helper.SW,Helper.SH,30,30);
                 g.setColor(Helper.BC);
                 g.fill(roundedRectangle);
                 
                g.setColor(Helper.TC);
                g.fillRect(Helper.BX + Helper.TB, Helper.BY + Helper.TB, Helper.SW - Helper.TB * 2, Helper.SH - Helper.TB * 2);
		
                g.setColor(Helper.BALL_WHITE);
                g.drawLine(Helper.BX + Helper.SW - Helper.SW / 4, Helper.BY + Helper.TB, Helper.BX + Helper.SW - Helper.SW / 4, Helper.BY + Helper.SH - Helper.TB);
                g.drawOval(Helper.BX + Helper.SW / 4, Helper.BY + Helper.SH / 2, 2, 2);
                
                //circle around desk
                Helper.holes.forEach((String key,int[] value)->{
                g.setColor(Color.BLACK);
                g.fillOval(Helper.holes.get(key)[0],Helper.holes.get(key)[1],30,30);
                
                //gakuseki
                g.setColor(Helper.BC);
                g.setFont(new Font("Arial Bold", Font.BOLD, 25));
                g.setFont(new Font("Arial Bold", Font.BOLD, 25));
                g.drawString("POOL",Helper.BX,Helper.BY/2);
                g.drawString("T170D517",Helper.BX,Helper.BY/2+25);
                g.drawString("POOL",Helper.BX,Helper.BY/2);
                g.drawString("MARIA DEVINA",Helper.BX,Helper.BY/2+25+25);
                
                //score
                g.setColor(Color.RED);
                g.setFont(new Font("Arial Bold", Font.BOLD, 25));
                g.setFont(new Font("Arial Bold", Font.BOLD, 25));
                g.drawString("SCORE:",Helper.SW,Helper.BY/2);
                g.drawString(String.valueOf(totalscore),Helper.BX+Helper.SW,Helper.BY/2);
                
                redrawballs(g);
                Shoot.draw(g); //shootdraw + init thread
                });
            }
            
             private void redrawballs(Graphics2D g)
        {
            for(int i=0;i<balls.size();i++) {
            	
            	 Ball2 ball = balls.get(i);
                 ball.draw((Graphics)g);
            }

        }

		public int getcurrentindexwhiteball(ArrayList<Ball2> balls2) {
			// TODO Auto-generated method stub
			for(int i=0;i<=balls2.size();i++) {
				if(balls2.get(i).iswhiteball==true)
					return i;
			}
			
			return balls.get(0).number;
		}
		
		public boolean ifpocket(Ball2 b) {
			 
			Map<String,int[]>map=Helper.holes;
			for(Entry<String, int[]> entry : map.entrySet()) {
				int x=entry.getValue()[0]+ Helper.HR;
				int y=entry.getValue()[1]+ Helper.HR;
						
				double xx=b.getcenterx();
			    double yy=b.getcentery();
				double dist=Math.sqrt((xx-x)*(xx-x)+(yy-y)*(yy-y));
				if(dist<Math.sqrt((Helper.HR + b.rr) * (Helper.HR + b.rr))) {
					
					tableupdate();
					return true;
				}
				
			}
			return false;	
		}
	}

