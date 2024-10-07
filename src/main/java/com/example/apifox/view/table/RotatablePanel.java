package com.example.apifox.view.table;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatablePanel extends JBLabel {

    private int rotationAngle = 0;

    public RotatablePanel(boolean collapsed){
        rotationAngle = collapsed? 0 : 90;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置旋转中心
        AffineTransform transform = g2d.getTransform();
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.rotate(Math.toRadians(90));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // 绘制内容
        g2d.drawString("Rotated Text", 50, 50);

        // 恢复原始变换
        g2d.setTransform(transform);
    }

}
