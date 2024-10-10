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
    private JTextField interfaceText;
    private JTextField apiText;

    public SettingUI() {

    }

    public JComponent getComponent() {
        String token = PropertiesComponent.getInstance().getValue("ApiFox.Token");
        String template = PropertiesComponent.getInstance().getValue("ApiFox.Template");
        String apiDir = PropertiesComponent.getInstance().getValue("ApiFox.ApiDir");
        String interfaceDir = PropertiesComponent.getInstance().getValue("ApiFox.InterfaceDir");
        textField.setText(token);
        templateTextArea.setText(template);
        apiText.setText(apiDir);
        interfaceText.setText(interfaceDir);
        return mainPanel;
    }

    public JTextField getTextField() {
        return textField;
    }
    public JTextArea getTemplateTextArea() {
        return templateTextArea;
    }
    public JTextField getApiText() {
        return apiText;
    }

    public JTextField getInterfaceText() {
        return interfaceText;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
