package com.example.apifox.component;

import com.example.apifox.model.Tree;
import com.example.apifox.model.TreeNode;
import com.intellij.util.messages.Topic;

import java.util.List;
import java.util.Map;

public interface DataUpdateTopic {
    Topic<DataUpdateTopic> DATA_UPDATE_TOPIC = Topic.create("Data Update Topic", DataUpdateTopic.class);

    void dataUpdated(Tree newData);
}
