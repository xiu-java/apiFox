package com.example.apifox.layout;

import java.awt.*;

public class ProjectGroupLayout extends FlowLayout{
    public ProjectGroupLayout() {
        super(FlowLayout.LEFT, 5, 5);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            int maxWidth = parent.getWidth() - getHgap() * 2;
            int maxHeight = 0;

            for (int i = 0; i < ncomponents; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    Dimension d = comp.getPreferredSize();
                    comp.setSize(maxWidth, d.height); // 设置宽度为父容器的宽度
                    maxHeight += d.height + getVgap();
                }
            }

            return new Dimension(maxWidth, maxHeight - getVgap());
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            int maxWidth = parent.getWidth() - getHgap() * 2;
            int maxHeight = 0;
            int x = getHgap();
            int y = getVgap();

            for (int i = 0; i < ncomponents; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    Dimension d = comp.getPreferredSize();
                    comp.setSize(maxWidth, d.height); // 设置宽度为父容器的宽度
                    comp.setBounds(x, y, maxWidth, d.height);
                    y += d.height + getVgap();
                }
            }
        }
    }
}
