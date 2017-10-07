package com.zj.tank;
import java.awt.*;

public class Explode {
	
	int x,y;
	private boolean live = true;
	private TankClient tc;
	//int[] diameter = {1,5,10,15,20,25,30,40,20,10,0	};
	private static boolean init = false;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("Images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Images/10.gif"))
	};
	
	int step = 0;
	
	public Explode(int x,int y,TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!init) {
			
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		if(step == imgs.length) {
			live = false;
			step = 0;
		}
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		g.drawImage(imgs[step], x, y, null);
		
		/*Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);*/
		step++;
	}
	
	
	
	
}
