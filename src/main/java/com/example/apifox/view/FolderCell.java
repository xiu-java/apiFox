package com.example.apifox.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Objects;

public class FolderCell extends HBox {
    public FolderCell(String title) {
        setAlignment(Pos.CENTER_LEFT);
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/folder.png"))));
        icon.setFitHeight(16);
        icon.setFitWidth(16);
        this.getChildren().add(icon);
        Label label = new Label();
        label.setText(title);
        getChildren().add(label);
    }
}
