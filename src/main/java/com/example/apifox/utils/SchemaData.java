package com.example.apifox.utils;

import com.example.apifox.component.ApiService;
import com.example.apifox.model.SchemaItem;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.media.Schema;
import com.example.apifox.model.openapi.v3.models.parameters.RequestBody;
import com.example.apifox.model.openapi.v3.models.responses.ApiResponses;
import com.example.apifox.service.ApiServiceImpl;
import com.intellij.openapi.project.ProjectManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.intellij.openapi.util.NullUtils.notNull;

public class SchemaData {
    ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
    public SchemaData(){

    }
    public void collectSchema(Operation operation, TreeItemVO node){
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
        Schema schema =  apiService.components.getSchemas().get(refPath);
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
