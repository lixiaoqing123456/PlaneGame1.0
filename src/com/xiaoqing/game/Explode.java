/*
 * 
 * Create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Graphics;
import java.awt.Image;
/**
 * 
 * 敌方飞机爆炸动画
 *
 */
public class Explode extends Animation{
	int count;//爆炸动画的帧数
    private static Image[] imgs = new Image[16];
    static {
        for(int i=0;i<16;i++){
            imgs[i] = GameUtil.getImage("images/explode/e"+(i+1)+".gif");
            imgs[i].getWidth(null);
        }
    }
     
    public void draw(Graphics g){
        if(count<=15){
            g.drawImage(imgs[count], (int)getX(), (int)getY(), null);
            count++;
        }
    }    
}
