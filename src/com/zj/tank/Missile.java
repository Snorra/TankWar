package com.zj.tank;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	
	public static final int XSPEED = 30,YSPEED = 30;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	int x,y;
	Direction dir;
	TankClient tc;
	private boolean live = true;
	private boolean good;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>(); 
	static {
		missileImages = new Image[] {
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/missileRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/MissileD.gif"))
			};
		
		imgs.put("D", missileImages[0]);
		imgs.put("L", missileImages[1]);
		imgs.put("LD", missileImages[2]);
		imgs.put("LU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("RU",missileImages[6]);
		imgs.put("U", missileImages[7]);
		};
	
	public boolean isLive() {		
		return live;
	}

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good,Direction dir,TankClient tc) {
		this(x,y,dir);
		this.tc = tc;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!this.isLive()) {
			tc.missiles.remove(this);
			return;
		}
	
		/*Color c = g.getColor();
		if(!good) {
			g.setColor(Color.blue);
		}
		else
			g.setColor(Color.red);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);*/
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"),x,y,null);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"),x,y,null);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"),x,y,null);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"),x,y,null);
			break;
		}
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += XSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		
		if(x < 0||y < 0||x > tc.GAME_WIDTH||y > tc.GAME_HEIGHT) {
			live = false;
		}
		
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect())&&t.isLive()&&this.good != t.isGood()) {
			if(t.isGood()) {
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0) t.setLive(false);
			}
			else {
				t.setLive(false);
			}
			this.live = false;
			Explode e = new Explode(t.x,t.y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i = 0;i < tanks.size();i++) {
			Tank t = tanks.get(i);
			if(this.hitTank(t))
				return true;
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.getRect().intersects(w.getRect())&&this.isLive()) {
			this.live = false;
			return true;
		}
		return false;
	}
	
}
