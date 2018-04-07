package com.xiaoqing.game;

import java.awt.Graphics;
import java.awt.Image;


public class Plane extends GameObject implements Runnable{
	private double degree=(Math.random()*(Math.PI/2)+Math.PI/4);
	private boolean live=true;
	private boolean iscollide=false;
	/*
	 *ex1为飞机第一次炸毁后的动画
	 *ex2用于解决飞机第一次炸毁，第二出现也炸毁，
	 *而第一次的动画没有播放完的情况 
	 */
	Explode explode1=new Explode();
	Explode explode2=new Explode();
	boolean ex1=false;
	boolean ex2=false;
	Bullet bullets[];
	private int bulletcount=Constant.DEFAULT_ARMY_BC;
	public Plane(Image img){
		setImg(img);
		bullets=new Bullet[bulletcount];
		for(int i=0;i<bullets.length;i++){
			bullets[i]=new Bullet();
		}
		setPosition((Math.random()*6*64+64), -34);
		setHeight(img.getHeight(null));
		setWidth(img.getWidth(null));
		}
	public Plane(Image img,double x,double y,int bulletcount){
		setImg(img);
		this.bulletcount=bulletcount;
		bullets=new Bullet[bulletcount];
		for(int i=0;i<bullets.length;i++){
			bullets[i]=new Bullet();
		}
		setPosition((Math.random()*6*64+64), -34);
		setWidth(getImg().getWidth(null));
		setHeight(getImg().getHeight(null));
	}
	public void drawSelf(Graphics g) {
		if(live){
			g.drawImage(getImg(),(int)getX(),(int)getY(), null);
			move();
		}
		if(iscollide){
			exp_anim(g);
		}
		checkLocation();
	}
	
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public void setCollide(boolean collide){
		this.iscollide=collide;
	}
	public void setBulletCount(int bulletcount){
		this.bulletcount=bulletcount;
	}
	public void exp_anim(Graphics g){
		if(iscollide&&ex1){
			explode1.draw(g);
		}
		if(iscollide&&ex2){
			explode2.draw(g);
		}
		if(explode1.count>=15){
			ex1=false;
			explode1.count=0;
		}
		if(explode2.count>=15){
			ex2=false;
			explode2.count=0;
		}
		if(!ex1&&!ex2){
			iscollide=false;
		}
	}
	/**
	 * 检查位置,每次死后重新设置位置和角度
	 */
	public void checkLocation(){
		if(!live||getY()>Constant.GAME_HEIGHT){
			setPosition(Math.random()*8*64, -34);
			degree=(Math.random()*(Math.PI/2)+Math.PI/4);
			live=true;
		}
		if(getX()<0||getX()>Constant.GAME_WIDTH-64){
			degree=Math.PI-degree;
		}
	}
	/**
	 * 移动位置
	 */
	public void move(){
		move(getX()+Constant.ARMY_STEP*Math.cos(degree),
				getY()+Constant.ARMY_STEP*Math.sin(degree));
	}
	/**
	 * 发射子弹
	 */
	public void fire(){
		/*
		 * 寻找一个子弹，再发射
		 */
		
		if(live&&(Math.random()<0.5)){
			 for(int i=0;i<bullets.length;i++){
					if(!bullets[i].isLive()){
						bullets[i].setPosition(getX()+25, getY()+60);
						bullets[i].setLive(true);
						break;
					}
				}
		 }
		
	}
	public void run() {
		while(true){
			fire();
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
}
