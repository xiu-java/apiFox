package com.example.apifox.view;

import com.example.apifox.model.FileItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class FileTreeCell extends TreeCell<FileItem> {
    private final ImageView directoryIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));
    private final ImageView fileIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));
    private final ImageView expandedIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/apifox.png"))));
    private final ImageView collapsedIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));

    @Override
    protected void updateItem(FileItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.name());
            if (item.isDirectory()) {
                setGraphic(directoryIcon);
            } else {
                setGraphic(fileIcon);
            }
        }
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
    }
}
