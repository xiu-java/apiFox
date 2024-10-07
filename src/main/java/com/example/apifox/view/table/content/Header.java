package com.example.apifox.view.table.content;

import com.example.apifox.view.table.Column;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;


public class Header<T> extends JBPanel {

  public Header(Column<T>[] columns) {
      GridLayout gridBagLayout = new GridLayout(1, columns.length);
      setLayout(gridBagLayout);
      setBorder( BorderFactory.createMatteBorder(0, 0, 0, 1, JBColor.BLACK));
      Arrays.stream(columns).forEach(item->{
          JBLabel label = new JBLabel(item.getLabel());
          label.setBorder( BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(1, 1, 0, 0, JBColor.BLACK),
                  BorderFactory.createEmptyBorder(8, 5, 8, 5)
          ));
          this.add(label);
      });
  }
}
