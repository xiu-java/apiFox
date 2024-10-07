package com.example.apifox.view.schema;

import com.example.apifox.model.SchemaItem;
import com.intellij.ui.table.JBTable;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Table extends JBTable {
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;  // 设置所有单元格不可编辑
        }
    };


    public Table(){
        setPreferredSize(new Dimension(800, 100));
        configUi();
    }
    private void configUi(){
       model.addColumn("字段");
       model.addColumn("类型");
       model.addColumn("备注");
   }

    public void update(SchemaItem data){
       removeAll();
       data.getChildren().forEach(item -> {
           model.addRow(new Object[]{item.key,item.type,item.description});
       });
       setModel(model);
    }
}
