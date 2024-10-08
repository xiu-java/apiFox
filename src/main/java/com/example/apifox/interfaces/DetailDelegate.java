package com.example.apifox.interfaces;

import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;

public interface DetailDelegate {
    void onDetailClick(TreeItemVO detail);
}
