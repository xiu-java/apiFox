package com.example.apifox.view;

import com.example.apifox.model.MethodType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class FileCell extends HBox {
    public FileCell(String title,MethodType type) {
        setAlignment(Pos.CENTER_LEFT);
        Label methodLabel = new Label(title);
        methodLabel.getStyleClass().add("method-"+type.getValue());
        methodLabel.setText(type.getValue());
        getChildren().add(methodLabel);
        Label label = new Label();
        label.setMaxWidth(250);
        label.setText(title);
        getChildren().add(label);
    }
}
