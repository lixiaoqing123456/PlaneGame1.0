/*
 * 
 * Create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
/**
 * 
 * 此类为制作游戏对象的基类，是一个抽象类不能被实例化
 *
 */
public abstract class GameObject {
	
	private Image img;//图片
	private int width,height;//宽度和高度
	private double x,y;//位置
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight(){
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public void move(double x,double y){
		this.x=x;
		this.y=y;
	}
	public void moveX(double x){
		this.x=x;
	}
	public void moveY(double y){
		this.y=y;
	}
	public void setPosition(double x,double y){
		move(x,y);
	}
	/** 
	 * 画自己
	 */
	public abstract void drawSelf(Graphics g);
    /**
     * 检查位置
     */
	public abstract void checkLocation();
	/**
	 * 移动自己
	 */
	public abstract void move();
	/**
	 *  返回物体所在的矩形，用于检测碰撞
	 */
	public Rectangle getRect(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
}
