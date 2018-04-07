/*
 * 
 * Create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Graphics;
/**
 * 
 * 此类是制作爆炸的基类
 *
 */
public abstract class Animation {
	private double x,y;//位置
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public abstract void draw(Graphics g);
	public void setLocation(double x,double y){
    	this.x = x;
        this.y = y;
    }
	

}
