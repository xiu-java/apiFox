package com.example.apifox.layout;

import java.awt.*;

public class ContentLayout extends FlowLayout{
    public ContentLayout() {
        super(FlowLayout.LEFT, 5, 5);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            int maxWidth = parent.getWidth();
            int maxHeight = 0;
            int x = getHgap();
            for (int i = 0; i < ncomponents; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    Dimension d = comp.getPreferredSize();
                    if(x==getHgap()){
                        maxHeight += d.height + getVgap();
                    }
                    if(x+d.width+getHgap()>maxWidth){
                        x=getHgap();
                    }else {
                        x += d.width + getHgap();
                    }
                }
            }
            return new Dimension(maxWidth, maxHeight+getVgap());
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            int maxWidth = parent.getWidth();
            int x = getHgap();
            int y = getVgap();

            for (int i = 0; i < ncomponents; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    Dimension d = comp.getPreferredSize();
                    if(x+d.width+getVgap()>maxWidth){
                        x=getHgap();
                        y += d.height + getVgap();
                    }
                    comp.setBounds(x, y, d.width, d.height);
                    x += d.width + getHgap();
                }
            }
        }
    }
}
