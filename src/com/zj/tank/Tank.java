package com.zj.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank {
	
	private static final int XSPEED = 10,YSPEED = 10;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private boolean good;
	private boolean live = true;
	private boolean bL = false, bR = false, bU = false, bD = false;
	private Random r = new Random();
	private int step = r.nextInt(12) + 3;
	private int life = 100;
	private bloodBar bb = new bloodBar();
	
	//enum Direction{	L,LU,U,RU,R,RD,D,LD,STOP}
	Direction dir;
	Direction ptDir = Direction.D;
	int x,y;
	int oldX,oldY;
	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>(); 
	static {
		tankImages = new Image[] {
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankLD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/tankU.gif"))
			};
		
		imgs.put("D", tankImages[0]);
		imgs.put("L", tankImages[1]);
		imgs.put("LD", tankImages[2]);
		imgs.put("LU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("RU", tankImages[6]);
		imgs.put("U", tankImages[7]);
		};
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc) {
		this(x,y,good);
		this.tc = tc;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		
		if(this.isLive()&&this.isGood())
			bb.draw(g);
		
		if(!this.isLive()) {
			if(!good)
				tc.tanks.remove(this);
			return;
		}
		/*Color c = g.getColor();
		if(good == true)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);*/
		
		switch(ptDir) {
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
	
	public void move() {
		
		oldX = x;
		oldY = y;
		
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
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x > tc.GAME_WIDTH - Tank.WIDTH) x = tc.GAME_WIDTH - Tank.WIDTH;
		if(y > tc.GAME_HEIGHT - Tank.HEIGHT) y = tc.GAME_HEIGHT - Tank.HEIGHT;
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step ==0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				this.dir = dirs[rn];
			}
			if(r.nextInt(40)>38) {
				this.fire();
			}
			step--;
		}
	}
	
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_F2:
				if(!this.isLive()&&this.isGood()) {
					this.setLive(true);
					this.setLife(100);
				}
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
		}
		locateDirection();	
	}
	
	public Missile fire() {
		if(!this.isLive())
			return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x,y,this.good,this.ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!this.isLive())
			return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x,y,this.good,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public void superFire() {
		Direction[] dir = Direction.values();
		for(int i = 0;i < 8;i++) {
			this.fire(dir[i]);
		}
	}

	public void locateDirection() {
		if(bR&&!bL&&!bU&&!bD) dir = Direction.R;
		else if(bR&&!bL&&!bU&&bD) dir = Direction.RD;
		else if(!bR&&!bL&&!bU&&bD) dir = Direction.D;
		else if(!bR&&bL&&!bU&&bD) dir = Direction.LD;
		else if(!bR&&bL&&!bU&&!bD) dir = Direction.L;
		else if(!bR&&bL&&bU&&!bD) dir = Direction.LU;
		else if(!bR&&!bL&&bU&&!bD) dir = Direction.U;
		else if(bR&&!bL&&bU&&!bD) dir = Direction.RU;
		else if(!bR&&!bL&&!bU&&!bD) dir = Direction.STOP;
		
		if(dir != Direction.STOP) ptDir = dir;
		
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_CONTROL:
				fire();
			break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_A:
				this.superFire();
				break;
		}		
		locateDirection();		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public void stay() {
		x = oldX;
		y = oldY;
	}
	
	public boolean collidesWithWall(Wall w) {
		if(this.getRect().intersects(w.getRect())&&this.isLive()) {
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(List<Tank> tanks) {
		for(int i = 0;i<tanks.size();i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.getRect().intersects(t.getRect())&&this.isLive()) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;		
	}
	
	public boolean eat(Blood b) {
		if(this.getRect().intersects(b.getRect())&&this.isLive()&&b.isLive()) {
			b.setLive(false);
			this.setLife(100);
			return true;
		}
		return false;
	}
	
	private class bloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life/100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
}
