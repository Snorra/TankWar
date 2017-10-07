package com.zj.tank;
import java.awt.*;

public class Blood {
	
	int x,y,w = 10,h = 10;
	private int step = 0;
	private boolean live = true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int[][] pos ={
			{300,400},{350,400},{500,450},{400,350},{450,500},{360,400},{370,530},{340,400}
	};
	
	
	public void move() {
		
		if(step >= 7) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
		
		step++;
		
	}
	
	public void draw(Graphics g) {
		if(!this.isLive())
			return;
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
}
