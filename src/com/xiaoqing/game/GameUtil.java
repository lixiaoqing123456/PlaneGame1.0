/*
 * 
 * create by_xiaoqing on 2018-04-05
 * 
 */
package com.xiaoqing.game;

import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * 
 *用于获取图片的工具类
 *
 */
public final class GameUtil {
    // 工具类最好将构造器私有化。
    private GameUtil() {
     
    } 
 
    public static Image getImage(String path) {
        BufferedImage bi = null;
        try {
            URL u = GameUtil.class.getClassLoader().getResource(path);
            bi = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }
}
