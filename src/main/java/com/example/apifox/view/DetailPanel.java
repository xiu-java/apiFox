package com.example.apifox.view;

import com.example.apifox.model.Detail;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;
import java.awt.*;

public class DetailPanel extends JPanel {
    private final JLabel title = new JBLabel();

    public DetailPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        configUi();
    }

    private void configUi(){
        title.setBackground(JBColor.BLUE);
      add(title);
    }

    public void open(Detail detail){
        title.setText(detail.getSummary());
      System.out.println(detail);
    }
}
