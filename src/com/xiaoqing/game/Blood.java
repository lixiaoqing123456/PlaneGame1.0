package com.xiaoqing.game;

import java.awt.Graphics;
import java.awt.Image;

public class Blood extends Animation {
	private static Image[] imgs = new Image[6];
	private int count=5;
    static {
        for(int i=0;i<6;i++){
            imgs[i] = GameUtil.getImage("images/blood/blood"+(i+1)+".png");
            imgs[i].getWidth(null);
        }
    }
    public Blood() {
	}
	public void draw(Graphics g) {
		g.drawImage(imgs[count], 400, 40, null);
	}
	public void addBlood(){
		if(count<5)count++;
	}
	public void minusBlood(){
		if(count>0)count--;
	}

}
