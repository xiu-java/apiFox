package com.example.apifox.view;

import com.example.apifox.model.Detail;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;

public class DetailPanel extends JPanel {
    private final JLabel title = new JBLabel();

    public DetailPanel() {
     configUi();
    }

    private void configUi(){
      add(title);
    }

    public void open(Detail detail){
        title.setText(detail.getSummary());
      System.out.println(detail);
    }
}
