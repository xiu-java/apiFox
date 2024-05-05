package com.example.apifox.view;

import com.example.apifox.model.TreeItemVO;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class CustomTreeCell extends TreeCell<TreeItemVO> {
    private final ImageView expand = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/arrow.png"))));
    @Override
    protected void updateItem(TreeItemVO item, boolean empty) {

        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (item.isDirectory()) {
                setPrefHeight(35);
                FolderCell cell = new FolderCell(item.getTitle());
                setGraphic(cell);
            } else {
                setPrefHeight(30);
               FileCell cell = new FileCell(item.getTitle(),item.getNode().getMethod());
                setGraphic(cell);
            }
        }
    }




    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
    }
}
