package com.example.apifox.view;

import com.intellij.ide.util.PropertiesComponent;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:08
 **/
public class SettingUI {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JLabel textLabel;
    private JTextField textField;

    public SettingUI() {

    }

    public JComponent getComponent() {
        String token = PropertiesComponent.getInstance().getValue("ApiFox.Token");
        textField.setText(token);
        return mainPanel;
    }

    public JTextField getTextField() {
        return textField;
    }
}
