package com.example.apifox.view.table.content;

import com.example.apifox.view.table.interfaces.TreeNode;
import com.example.apifox.view.table.Column;
import com.example.apifox.view.table.RotatablePanel;
import com.example.apifox.view.table.interfaces.TreeTableDelegate;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;


public class Body<T extends TreeNode> extends JBPanel {
    public TreeTableDelegate delegate;
    GridLayout gridBagLayout = new GridLayout();
   ArrayList<T>  dataSource;
   Column<T>[] columns;
  public Body(Column<T>[] columns) {
      setLayout(gridBagLayout);
      this.columns = columns;
      setBorder( BorderFactory.createMatteBorder(0, 0, 1, 1, JBColor.BLACK));

  }

  public void Body(Column<T>[] columns,ArrayList<T> dataSource) {
      setLayout(gridBagLayout);
      this.dataSource = dataSource;
  }

  private Integer addRow(ArrayList<T> data ,Integer count, Integer index){
      count+=data.size();
      for (int i = 0; i < data.size(); i++) {
        T row = data.get(i);
          for (int j = 0; j <columns.length; j++) {
             Column<T> column = columns[j];
             if(j==0&&row.hasChildren()){
                 Box box = Box.createHorizontalBox();
                 RotatablePanel iconLabel = new RotatablePanel(row.isCollapse());
                 ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/arrow.png")));
                 Image scaledImage = icon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
                 Icon scaledIcon = new ImageIcon(scaledImage);
                 iconLabel.setIcon(scaledIcon);
                 iconLabel.addMouseListener(new MouseAdapter(){
                     @Override
                     public void mouseClicked(MouseEvent e) {
                         super.mouseClicked(e);
                         row.collapse();
                         Update(data);
                     }
                 });
                 box.add(iconLabel);
                 JBLabel label = new JBLabel(column.getValue(row));
                 label.setBorder( BorderFactory.createEmptyBorder(0, 2, 0, 0));
                 box.add(label);
                 box.setBorder( BorderFactory.createCompoundBorder(
                         BorderFactory.createMatteBorder(1, 1, 0, 0, JBColor.BLACK),
                         BorderFactory.createEmptyBorder(5, index*15 + 5, 5, 5)
                 ));
                 add(box);
             }else {
                 JBLabel label = new JBLabel(column.getValue(row));
                 label.setBorder( BorderFactory.createCompoundBorder(
                         BorderFactory.createMatteBorder(1, 1, 0, 0, JBColor.BLACK),
                         BorderFactory.createEmptyBorder(5, index*15 + 5, 5, 5)
                 ));
                 add(label);
             }

          }
          if(row.hasChildren()&&row.isCollapse()){
              index++;
              count = addRow((ArrayList<T>) row.getChildren(),count,index);
          }
      }
      return count;
  }
  public void Update(ArrayList<T> data) {
      removeAll();
      Integer count = addRow(data,0,0);
      delegate.resize(count);
      gridBagLayout.setColumns(columns.length);
      gridBagLayout.setRows(count);
      revalidate();  // 重新验证布局
      repaint();
  }
}

