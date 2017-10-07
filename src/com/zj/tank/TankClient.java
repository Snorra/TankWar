package com.zj.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

//import com.zj.tank.Tank.Direction;
/**
 * 
 * @author zhongjie
 *这是坦克游戏的主窗口
 */
public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(500,500,true,Direction.STOP,this);
	//Tank enemyTank = new Tank(100,100,false,Tank.Direction.D,this);
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	Wall w1 = new Wall(300,200,300,20,this);
	Wall w2 = new Wall(200,300,20,200,this);
	Blood b = new Blood();
	
	Image offScreenImage = null;
	
	public void paint(Graphics g) {
		
		g.drawString("missile count: " + missiles.size(), 10,50);
		g.drawString("explode count: " + explodes.size(), 10, 70);
		g.drawString("tank count: " + tanks.size(), 10, 90);
		g.drawString("tank life: " + myTank.getLife(), 10, 110);
		
		for(int i = 0;i<missiles.size();i++) {
			Missile m = missiles.get(i);
			//if(!m.isLive()) missiles.remove(m);
			//m.hitTank(enemyTank);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		
		for(int i = 0;i<explodes.size();i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		if(tanks.size() <= 0) {
			for(int i = 0;i<10;i++) {
				tanks.add(new Tank(50*(i+1),40,false,Direction.D,this));
			}
		}
		
		for(int i = 0;i<tanks.size();i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		//if(m != null) m.draw(g);
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		myTank.collidesWithTanks(tanks);
		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
		b.draw(g);
		myTank.eat(b);
		//enemyTank.draw(g);
		
	}
	
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.black );
		gOffScreen.fillRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}



	public void lauchFrame() {
		for(int i = 0;i<10;i++) {
			tanks.add(new Tank(50*(i+1),40,false,Direction.D,this));
		}
		setLocation(200,100);
		setSize(GAME_WIDTH,GAME_HEIGHT);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		setVisible(true);
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	
	private class PaintThread implements Runnable {
		
		public void run() {
			while(true) {
				 repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}		
	}
	
	private class KeyMonitor extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}
		
		
		
	}
	

}
