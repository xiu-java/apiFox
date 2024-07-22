package com.example.apifox.view;

import com.example.apifox.model.Detail;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

public class DetailPanel extends JPanel {
    private final JLabel title = new JBLabel();
    private final Box apiPlane = new Box(BoxLayout.X_AXIS);

    private final JBLabel method = new JBLabel();
    private final JBLabel url = new JBLabel();

    private final SchemaSection kk = new SchemaSection();
    public DetailPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        configUi();
    }

    private void configUi(){
      add(title);
      title.setAlignmentX(LEFT_ALIGNMENT);
      apiPlane.add(method);
      apiPlane.add(url);
      apiPlane.setBackground(JBColor.red);
      apiPlane.setAlignmentX(LEFT_ALIGNMENT);
      add(apiPlane);
      add(kk);
    }

    public void open(Detail detail){
        title.setText(detail.getSummary());
        method.setText(detail.getMethod().getValue());
        url.setText(detail.getUrl());
      System.out.println(detail);
    }
}
