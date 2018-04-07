/*
 * 
 * Create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Color;
import java.awt.Graphics;


public class Bullet extends GameObject {
	private boolean live=false;

	public Bullet(){
		this.setHeight(10);
		this.setWidth(10);
	}

	public void drawSelf(Graphics g) {
		if(live){
			Color c=g.getColor();
			g.setColor(Color.YELLOW);
			g.fillOval((int)getX(),(int)getY(),getWidth(), getWidth());
			g.setColor(c);
			move();	
			checkLocation();
		}
	}
	public void setLive(boolean live){
		this.live=live;
	}
	public boolean isLive(){
		return live;
	}
	
	public void checkLocation() {
		if(getY()>Constant.GAME_HEIGHT)setLive(false);
	}

	public void move() {
		moveY(getY()+Constant.BULLET_STEP);
	}

	
	
}
