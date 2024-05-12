package com.example.apifox.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Breadcrumb extends JPanel {
    public Breadcrumb(String... paths) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < paths.length; i++) {
            final String path = paths[i];
            JButton button = new JButton(path);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);


            // Add Action Listener to handle click events
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Navigating to " + path);
                }
            });

            this.add(button);

            // Add a separator label unless it's the last button
            if (i < paths.length - 1) {
                this.add(new JLabel(">"));
            }
        }
    }
}