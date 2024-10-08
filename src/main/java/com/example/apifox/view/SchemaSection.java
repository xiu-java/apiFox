package com.example.apifox.view;

import com.example.apifox.model.SchemaItem;
import com.example.apifox.view.schema.Table;
import com.example.apifox.view.schema.TreeTable;
import com.example.apifox.view.table.Column;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class SchemaSection extends JBPanel {
    TreeTable  treeTable;
    Table table;
    String type = "tree";
    SchemaSection(String label ,String type){
        this.type = type;
        setLayout(new BorderLayout());
        setBorder(new RoundedRectangleBorder(10, 10));
        Box box = Box.createHorizontalBox();
        JBLabel title = new JBLabel(label);
        box.add(title);
        box.setBackground(JBColor.BLUE);
        add(box, BorderLayout.NORTH);
        JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        if(type.equals("tree")){
            treeTable = new TreeTable();
            scrollPane.setViewportView(treeTable);
        }else {
            table = new Table();
            scrollPane.setViewportView(table);
        }
        add(scrollPane, BorderLayout.CENTER);
    }

    public void load(SchemaItem dataSource) {
        if(type.equals("tree")){
            treeTable.update(dataSource);
        }else {
            table.update(dataSource);
        }
    }

    public JComponent getComponent() {
        return this;
    }

    // 自定义圆角边框
    public static class RoundedRectangleBorder implements Border {
        private final int arcWidth;
        private final int arcHeight;
        private final Color borderColor;

        public RoundedRectangleBorder(int arcWidth, int arcHeight) {
            this(arcWidth, arcHeight, Color.BLACK);
        }

        public RoundedRectangleBorder(int arcWidth, int arcHeight, Color borderColor) {
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            this.borderColor = borderColor;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(arcHeight, arcWidth, arcHeight, arcWidth);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor);

            RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, width - 1, height - 1, arcWidth, arcHeight);
            g2d.draw(rect);

            g2d.dispose();
        }
    }
}
