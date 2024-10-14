package com.example.apifox.component;


import com.example.apifox.model.openapi.v3.models.Components;

public interface DataSourceService {

    Components getComponents();

    void setComponents(Components components);

}
