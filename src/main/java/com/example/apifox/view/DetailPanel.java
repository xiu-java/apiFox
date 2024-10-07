package com.example.apifox.view;

import com.example.apifox.model.SchemaItem;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.media.Schema;
import com.example.apifox.model.openapi.v3.models.parameters.RequestBody;
import com.example.apifox.model.openapi.v3.models.responses.ApiResponses;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import static com.intellij.openapi.util.NullUtils.notNull;

public class DetailPanel extends JPanel {
    Components components;
    SpringLayout layout = new SpringLayout();


    public DetailPanel() {
        setBorder(JBUI.Borders.empty(10));
        setLayout(new GridBagLayout());
    }

    private void configUi(){




    }

    public void open(TreeItemVO detail, Components components){
        this.components = components;
        removeAll();
        collectSchema(detail.getNode(),detail);
        JLabel title = new JBLabel();
        title.setText(detail.getTitle());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(title,gc);
        JBLabel method = new JBLabel();
        JBLabel url = new JBLabel();
        Box apiPlane = new Box(BoxLayout.LINE_AXIS);
        method.setText(detail.getMethod().getValue());
        Font font = new Font("Arial", Font.BOLD, 12);
        method.setFont(font);
        switch (detail.getMethod()){
            case GET -> {
                method.setForeground(JBColor.GREEN);
            }
            case POST -> {
                method.setForeground(JBColor.ORANGE);
            }
            case PUT -> {
                method.setForeground(JBColor.BLUE);
            }
            case DELETE -> {
                method.setForeground(JBColor.RED);
            }
        }
        method.setBorder(JBUI.Borders.emptyRight(5));
        url.setText(detail.getUrl());
        apiPlane.add(method);
        apiPlane.add(url);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.insets = JBUI.insetsTop(5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(apiPlane,gbc);
        Box content = new Box(BoxLayout.PAGE_AXIS);
        if(notNull(detail.path)){
            SchemaSection path = new SchemaSection("path","table");
            path.load(detail.path);
            path.setPreferredSize(new Dimension(0, 200));
            content.add(path);
            content.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if(notNull(detail.query)){
            SchemaSection query = new SchemaSection("query","table");
            query.load(detail.query);
            query.setPreferredSize(new Dimension(0, 200));
            content.add(query);
            content.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if(notNull(detail.body)){
            SchemaSection body = new SchemaSection("body","tree");
            body.load(detail.body);
            content.add(body);
            content.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if(notNull(detail.response)){
            SchemaSection response = new SchemaSection("response","tree");
            response.load(detail.response);
            content.add(response);
        }
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.weighty = 1.5;
        gbc2.insets = JBUI.insetsTop(5);
        gbc2.fill = GridBagConstraints.BOTH;
        add(content,gbc2);
    }

    public void collectSchema(Operation operation,TreeItemVO node){
        if(node.completed) return;
        operation.getParameters().forEach(p->{
            if(p.getIn().equals("query")){
                node.query = new SchemaItem("query","object","","Record<String,any>");
                node.query.add(new SchemaItem(p.getName(),p.getSchema().getType(),p.getDescription(),p.getSchema().getType()));
            }
            if(p.getIn().equals("path")){
                node.path = new SchemaItem("path","object","","Record<String,any>");
                node.path.add(new SchemaItem(p.getName(),p.getSchema().getType(),p.getDescription(),p.getSchema().getType()));
            }
        });

        RequestBody requestBody = operation.getRequestBody();

        if(notNull(requestBody)){
            String ref =requestBody.getContent().get("application/json").getSchema().get$ref();
            if(notNull(ref)){
                node.body = refToItem(ref,"root","object");
            }
        }
        ApiResponses responses = operation.getResponses();

        if(notNull(responses)){
           responses.forEach((k,v)->{
               String ref = v.getContent().get("application/json").getSchema().get$ref();
               if(notNull(ref)){
                   node.response = refToItem(ref,"root", "object");
               }
           });
        }
        node.completed = true;
    }

    public SchemaItem refToItem(String ref, String key,String t){
        String refPath = URLDecoder.decode(ref.substring(ref.lastIndexOf("/")+1), StandardCharsets.UTF_8);
        Schema schema =   this.components.getSchemas().get(refPath);
        SchemaItem row = new SchemaItem(key,t,"",refPath.replaceAll("«","<").replaceAll("»",">"));
        String type = schema.getType();
        Map<String, Schema> properties = schema.getProperties();
        if(type.equals("object")){
            properties.forEach((k,v)->{
               if(notNull(v.get$ref())){
                   row.add(refToItem(v.get$ref(), k, type));
               }else {
                   if(v.getType().equals("object")){
                       if(notNull(v.get$ref())){
                           row.add(refToItem(v.get$ref(), k,"object"));
                       }else {
                           row.add(new SchemaItem(k,v.getType(),v.getDescription(), v.getType()));
                       }
                   }else if(v.getType().equals("array")){
                       Schema items = v.getItems();
                       if(notNull(items.get$ref())){
                           row.add(refToItem(items.get$ref(), k,"array"));
                       }else {
                           row.add(new SchemaItem(k,v.getType(),v.getDescription(),v.getType()));
                       }
                   }else {
                       row.add(new SchemaItem(k,v.getType(),v.getDescription(),v.getType()));
                   }
               }
            });
        }
        return row;
    }
}
