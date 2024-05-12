package com.example.apifox.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class RoundImageIcon extends JLabel {
    private ImageIcon icon;
    private final int cornerRadius;
    private int width = 50;
    private int height = 50;
    public RoundImageIcon(String imagePath, int cornerRadius, int width, int height) {
        this.cornerRadius = cornerRadius;
        this.width = width;
        this.height = height;
        try {
            URL url = new URL(imagePath);
            BufferedImage image = ImageIO.read(url);
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.icon = new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            Graphics2D g2d = (Graphics2D) g.create();


            // 创建圆角矩形
            RoundRectangle2D round = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
            g2d.setClip(round);

            // 绘制图像
            g2d.drawImage(icon.getImage(), 0, 0, width, height, null);

            g2d.dispose();
        }
    }


}