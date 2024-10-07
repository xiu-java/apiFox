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
    private JTextArea templateTextArea;

    public SettingUI() {

    }

    public JComponent getComponent() {
        String token = PropertiesComponent.getInstance().getValue("ApiFox.Token");
        String template = PropertiesComponent.getInstance().getValue("ApiFox.Template");
        textField.setText(token);
        templateTextArea.setText(template);
        return mainPanel;
    }

    public JTextField getTextField() {
        return textField;
    }
    public JTextArea getTemplateTextArea() {
        return templateTextArea;
    }
}
