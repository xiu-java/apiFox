package com.example.apifox.utils;

import com.example.apifox.model.SchemaItem;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.media.Schema;
import com.example.apifox.model.openapi.v3.models.parameters.Parameter;
import com.example.apifox.model.openapi.v3.models.parameters.RequestBody;
import com.example.apifox.model.openapi.v3.models.responses.ApiResponses;
import com.example.apifox.service.ProjectConfigImpl;
import com.intellij.openapi.project.ProjectManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.intellij.openapi.util.NullUtils.notNull;
public class SchemaData {
    public SchemaData(){

    }
    public void collectSchema(Operation operation, TreeItemVO node){
        if(node.completed||operation == null) return;
        List<Parameter> parameters = operation.getParameters();
        if(notNull(parameters)){
            parameters.forEach(p->{
                if(p.getIn().equals("query")){
                    node.query = new SchemaItem("query","object","","Record<String,any>");
                    node.query.add(new SchemaItem(p.getName(),p.getSchema().getType(),p.getDescription(),p.getSchema().getType()));
                }
                if(p.getIn().equals("path")){
                    node.path = new SchemaItem("path","object","","Record<String,any>");
                    node.path.add(new SchemaItem(p.getName(),p.getSchema().getType(),p.getDescription(),p.getSchema().getType()));
                }
            });
        };


        RequestBody requestBody = operation.getRequestBody();

        if(notNull(requestBody)){
            if(notNull(requestBody.getContent().get("application/json"))){
                String ref =requestBody.getContent().get("application/json").getSchema().get$ref();
                if(notNull(ref)){
                    node.body = refToItem(ref,"root","object");
                }
            }
        }
        ApiResponses responses = operation.getResponses();

        if(notNull(responses)){
            responses.forEach((k,v)->{
                if(notNull(v.getContent().get("application/json"))){
                    String ref = v.getContent().get("application/json").getSchema().get$ref();
                    if(notNull(ref)){
                        node.response = refToItem(ref,"root", "object");
                    }
                }
            });
        }
        node.completed = true;
    }

    public SchemaItem refToItem(String ref, String key,String t){
        ProjectConfigImpl dataSourceService = ProjectConfigImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        String refPath = URLDecoder.decode(ref.substring(ref.lastIndexOf("/")+1), StandardCharsets.UTF_8);
        Components components = dataSourceService.getComponents();
        Schema schema = components.getSchemas().get(refPath);
        SchemaItem row = new SchemaItem(key,t,"",refPath.replaceAll("«","<").replaceAll("»",">"));
        String type = schema.getType();
        Map<String, Schema> properties = schema.getProperties();
        List<String> r =  schema.getRequired();
        HashSet<String> required = new HashSet<String>();
        if(notNull(r)){
            required.addAll(r);
            }
        if(type.equals("object")){
            properties.forEach((k,v)->{
                if(notNull(v.get$ref())){
                    row.add(refToItem(v.get$ref(), k, type));
                }else {
                    if(v.getType().equals("object")){
                        if(notNull(v.get$ref())){
                            row.add(refToItem(v.get$ref(), k,"object"));
                        }else {
                            row.add(new SchemaItem(k,transformType(v.getType()),required.contains(k),v.getDescription(), transformType(v.getType())));
                        }
                    }else if(v.getType().equals("array")){
                        Schema items = v.getItems();
                        if(notNull(items.get$ref())){
                            row.add(refToItem(items.get$ref(), k,"array"));
                        }else {
                            row.add(new SchemaItem(k,transformType(v.getType()),required.contains(k),v.getDescription(),transformType(items.getType())));
                        }
                    }else {
                        row.add(new SchemaItem(k,transformType(v.getType()),required.contains(k),v.getDescription(),transformType(v.getType())));
                    }
                }
            });
        }
        return row;
    }
   public String  transformType(String type){
        if(type.equals("integer")){
            return "number";
        }
        return type;
   }

}
