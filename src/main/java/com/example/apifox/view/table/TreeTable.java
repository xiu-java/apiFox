package com.example.apifox.view.table;

import com.example.apifox.view.table.interfaces.TreeNode;
import com.example.apifox.view.table.content.Body;
import com.example.apifox.view.table.content.Header;
import com.example.apifox.view.table.interfaces.TreeTableDelegate;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class TreeTable<T extends TreeNode> extends JBPanel implements TreeTableDelegate {
    Column<T>[] columns;
    ArrayList<T> dataSource = new ArrayList<>();
    private Body<T> body;
    public TreeTable(Column<T>[] columns){
        this.columns = columns;
        this.configUi();
    }

    public TreeTable(Column<T>[] columns, ArrayList<T> dataSource){

        this.columns = columns;
        this.dataSource = dataSource;

        this.configUi();
    }

    public void Update(ArrayList<T> data){
        body.Update(data);
    }
    public void configUi(){
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        Header<T> header = new Header<>(columns);
        springLayout.putConstraint(SpringLayout.NORTH, header, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, header, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, header, 0, SpringLayout.EAST, this);
        add(header);
        body = new Body<>(columns);
        body.delegate = this;
        springLayout.putConstraint(SpringLayout.NORTH, body, 0, SpringLayout.SOUTH, header);
        springLayout.putConstraint(SpringLayout.WEST, body, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, body, 0, SpringLayout.EAST, this);
        add(body);
    }

    @Override
    public void resize(Integer count) {
        setPreferredSize(new Dimension(0,count*27+34));
    }
}
