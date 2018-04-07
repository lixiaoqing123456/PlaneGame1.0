/*
 * 
 * Create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * 
 * 游戏主窗口
 * 游戏开始前，关闭输入法
 * w前，s后，a左，d右+shift（可以加速）
 * 
 *
 */
public class MyGameFrame extends Frame {
	private Image plane_img;//飞机图片
	private Image bg;//背景图片
	private Image supply_img;//加血包
	private MyPlane plane;//我的飞机
	private Image army_images[];//敌机图片
	private Plane armys[];//敌机
	private Image offScreenImage;//用于双缓冲
	private int score;//所获分数
	private SupplyPacket supply;//医疗包
	
	/*用于画窗口的线程*/
	private Thread paintThread;//重画窗口线程
	private Thread planefire;//我方飞机发射子弹的线程
	private Thread armysfire[];//敌方飞机发射子弹的线程
	private Thread supplyThread;//补给出现的线程

	/**
	 * 用于加载图片
	 */
	public void load(){
		/*加载飞机图片*/
		plane_img=GameUtil.getImage("images/plane.png");
		/*加载背景图片*/
		bg=GameUtil.getImage("images/bg.jpg");
		/*加载敌机图片*/
		army_images=new Image[6];
		for(int i=0;i<army_images.length;i++){
			army_images[i] = GameUtil.getImage("images/army/army"+(i+1)+".png");
            army_images[i].getWidth(null);
		}
		/*加载补给包图片*/
		supply_img=GameUtil.getImage("images/supply.png");
		
	}
	/**
	 * 用于初始化窗口
	 */
	public void init(){
		setTitle("PlaneGame1.0");
		setVisible(true);
		setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		setLocation(400, 100);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		addKeyListener(new KeyMonitors());
		plane=new MyPlane(plane_img,288,460);
		supply=new SupplyPacket(supply_img);
		armys=new Plane[6];
		for(int i=0;i<armys.length;i++){
			armys[i]=new Plane(army_images[i]);
		}
		
		paintThread=new PaintThread();
		planefire=new Thread(plane);
		armysfire=new Thread[armys.length]; //创建敌方飞机发射子弹的线程
		for(int i=0;i<armys.length;i++){//敌方飞机发射子弹的线程
			armysfire[i]=new Thread(armys[i]);
		}
		supplyThread=new Thread(supply);
	}
	/**
	 * 用于开启线程
	 */
	public void start(){
		paintThread.start();
		planefire.start();
		for(int i=0;i<armys.length;i++){
			armysfire[i].start();
		}
		supplyThread.start();
	}
	/**
	 * 内部的一个线程类，用于重画窗口
	 */
	class PaintThread extends Thread{
		public void run() {
			while(true){
				repaint();
				try{
					Thread.sleep(40);//1秒画25次
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 内部类，监听键盘
	 */
	class KeyMonitors extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
	}
	@Override
	/**
	 * 用于画窗口
	 */
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);
		drawScore(g);
		supply.drawSelf(g);
		plane.drawSelf(g);
		for(int i=0;i<plane.bullets.length;i++){
			plane.bullets[i].drawSelf(g);
		}

		for(int i=0;i<armys.length;i++){
			armys[i].drawSelf(g);
		}
		for(int i=0;i<armys.length;i++){
			for(int j=0;j<armys[i].bullets.length;j++){
				armys[i].bullets[j].drawSelf(g);
			}
		}
		plane.blood.draw(g);
		isCollide();//判断是否发现碰撞
			
	}
	/**
	 * 用于双缓冲
	 */
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
	/**
	 * 用于判断是否发生碰撞
	 */
	public void isCollide(){
		for(int i=0;i<armys.length;i++){
			boolean peng=false;
			for(int j=0;j<plane.bullets.length;j++){
				if(plane.bullets[j].isLive()&&
						(armys[i].getRect().intersects
								(plane.bullets[j].getRect()))){
					peng=true;
					plane.bullets[j].setLive(false);
					score+=10;
					break;
				}
				else{
					continue;
				}
			}
			if(peng){
				armys[i].setCollide(true);
				if(armys[i].explode1.count==0){
					armys[i].explode1.setLocation(armys[i].getX(), armys[i].getY());
					armys[i].ex1=true;
				}
				else{
					armys[i].explode2.setLocation(armys[i].getX(), armys[i].getY());
					armys[i].ex2=true;
				}
					armys[i].setLive(false);
			}//判断敌方飞机是否被击中
			for(int j=0;j<armys[i].bullets.length;j++){
				if(armys[i].bullets[j].isLive()&&(armys[i].bullets[j].getRect().intersects
						(plane.getRect()))){
					plane.blood.minusBlood();
					armys[i].bullets[j].setLive(false);
				}
			}//判断我方飞机是否被击中
		}
		if(supply.isLive()&&plane.isLive()
				&&plane.getRect()
				.intersects(supply.getRect())){
			plane.blood.addBlood();
			supply.setLive(false);
		}//判断我方飞机是否获得补给
	}
	/**
	 * 用于画分数
	 */
	public void drawScore(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.WHITE);
		g.setFont(new Font("宋体", Font.BOLD, 20));
		g.drawString("得分为："+score, 20, 50);
		g.setColor(c);
	}
	public static void main(String[] args) {
		MyGameFrame f=new MyGameFrame();
		f.load();
		f.init();
		f.start();
	}

}
