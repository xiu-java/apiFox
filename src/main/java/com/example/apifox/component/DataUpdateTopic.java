package com.example.apifox.component;

import com.example.apifox.model.openapi.v3.models.OpenAPI;
import com.intellij.util.messages.Topic;

public interface DataUpdateTopic {
    Topic<DataUpdateTopic> DATA_UPDATE_TOPIC = Topic.create("Data Update Topic", DataUpdateTopic.class);

    void dataUpdated(OpenAPI newData);
}
