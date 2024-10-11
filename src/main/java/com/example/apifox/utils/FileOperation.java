package com.example.apifox.utils;

import com.example.apifox.model.MethodType;
import com.example.apifox.model.SchemaItem;
import com.example.apifox.model.TreeItemVO;
import com.github.weisj.jsvg.S;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.intellij.openapi.util.NullUtils.notNull;

public class FileOperation {

    String template = PropertiesComponent.getInstance().getValue("ApiFox.Template");
    String apiDir = PropertiesComponent.getInstance().getValue("ApiFox.ApiDir");
    String interfaceDir = PropertiesComponent.getInstance().getValue("ApiFox.InterfaceDir");
    String exCloudInterface = PropertiesComponent.getInstance().getValue("ApiFox.Excloud");
    HashSet<String> whiteList = new HashSet<>(Arrays.asList("Object", "integer", "Boolean","string","Null","Number","Date","List","Array","Map","Set","Object[]","String[]","integer[]","Boolean[]","Date[]"));
    HashSet<String> exCloudInterfaces = new HashSet<>();

    public FileOperation(){
        if(exCloudInterface!=null){
            exCloudInterfaces.addAll(Arrays.stream(exCloudInterface.split("/")).toList());
        }
    }

    public  void  write(Project project,String targetDirectory, String fileName, String fileContent){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                // 获取项目的真实根目录
                String projectRootPath = project.getBasePath();
                if (projectRootPath == null) {
                    throw new IllegalStateException("无法获取项目根目录");
                }
                // 获取或创建目标目录
                Path directoryPath = Paths.get(projectRootPath,targetDirectory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                // 创建 .ts 文件路径
                Path filePath = Paths.get(projectRootPath,targetDirectory, fileName + ".ts");

                // 写入文件内容
                Files.writeString(filePath, fileContent+ System.lineSeparator(),StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                // 刷新虚拟文件系统以使 IDEA 识别新文件
                VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(filePath.toString());
                if (virtualFile != null) {
                    VfsUtil.markDirtyAndRefresh(true, true, true, virtualFile);
                }

                System.out.println("TypeScript 文件创建成功：" + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public  void  clear(Project project, String filePath){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            // 获取项目的真实根目录
            String projectRootPath = project.getBasePath();
            if (projectRootPath == null) {
                throw new IllegalStateException("无法获取项目根目录");
            }
            // 获取或创建目标目录
            Path directoryPath = Paths.get(projectRootPath,filePath);
            if (Files.exists(directoryPath)) {
                try {
                    Files.writeString(directoryPath, "", StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void createApi(Project project,TreeItemVO item){
        clear(project,apiDir+item.getUrl()+ ".ts");
        clear(project,interfaceDir+item.getUrl()+ ".d.ts");
        StringBuilder namespace= new StringBuilder("API");
        StringBuilder interfaceTemplate = new StringBuilder("declare namespace API {\n");
        StringBuilder apiTemplate = new StringBuilder();
        for (String p : item.getUrl().split("/")) {
            if(!Objects.equals(p, "")){
                interfaceTemplate.append(String.format("namespace %s {\n", p));
                namespace.append('.').append(p);
            }
        }
        item.getChildren().forEach(child -> {
        InterfaceFormat(child,interfaceTemplate,namespace.toString());
        ApiFormat(child,apiTemplate,namespace.toString());
        });
        for (String p : item.getUrl().split("/")) {
            if(!Objects.equals(p, "")){
                interfaceTemplate.append("}\n");
            }
        }
        interfaceTemplate.append("}");

    }

    public void ApiFormat(TreeItemVO item, StringBuilder apiTemplate, String namespace){
        String [] tags = item.getUrl().split("/");
        String name = tags[tags.length-1];
        if(item.getMethod() == MethodType.GET){
            if(notNull(item.query)){
                String queryNs = buildNs(item.query,namespace);
                String responseNs = buildNs(item.response,namespace);
                String template = """
                   /*
                    * %s
                    * GET %s
                   */
                   export const %s: (params: %s) => Promise<%s> = (params: %s) => http.get('%s', { params });""";
                apiTemplate.append(String.format(template,item.getTitle(),item.getUrl(),name,queryNs,responseNs,queryNs,item.getUrl()));
            }else {
                String responseNs = buildNs(item.response,namespace);
                String template = """
                   /*
                    * %s
                    * GET %s
                   */
                   export const %s: () => Promise<%s> = () => http.get('%s');""";
               apiTemplate.append(String.format(template,item.getTitle(),item.getUrl(),name,responseNs,item.getUrl()));
            }

        }else{
            String bodyNs = buildNs(item.query,namespace);
            String responseNs = buildNs(item.response,namespace);
            String template = """
                   /*
                    * %s
                    * POST %s
                   */
                   export const %s: (params: %s) => Promise<%s> = (params: %s) => http.post('%s', { params });""";
            apiTemplate.append(String.format(template,item.getTitle(),item.getUrl(),name,bodyNs,responseNs,bodyNs,item.getUrl()));
        }

    }


    public String buildNs(SchemaItem schemaItem,String namespace){
        List<String> generics = extractGenerics(schemaItem.interfaces);
        StringBuilder ns = new StringBuilder();
        for (int i = 0; i < generics.size(); i++) {
            String g = generics.get(i);
            if(exCloudInterface.contains(g)){
                if(generics.size()-1==i){
                    ns.append("API.").append(g).append(">".repeat(generics.size()-1));
                }else {
                    ns.append("API.").append(g).append("<");
                }
            } else if (whiteList.contains(g)) {
                if(generics.size()-1==i){
                    ns.append(g).append(">".repeat(generics.size()-1));
                }else {
                    ns.append(g).append("<");
                }
            } else {
                if(generics.size()-1==i){
                    ns.append(namespace).append('.').append(g).append(">".repeat(generics.size()-1));
                }else {
                    ns.append(namespace).append('.').append(g).append("<");
                }
            }
        }
        return ns.toString();
    }


    public void InterfaceFormat(TreeItemVO item,StringBuilder interfaceTemplate,String namespace){
        String [] tags = item.getUrl().split("/");
        String name = tags[tags.length-1];
        interfaceTemplate.append(String.format("namespace %s {",name));
        if(notNull(item.query)){
            addInterfaceRow(item.query,interfaceTemplate, String.format("%s.%s",namespace,name));
        }

        if(notNull(item.body)){
            addInterfaceRow(item.body,interfaceTemplate, String.format("%s.%s",namespace,name));
        }

        if(notNull(item.response)){
            addInterfaceRow(item.response,interfaceTemplate, String.format("%s.%s",namespace,name));
        }
        interfaceTemplate.append("};\n");
    }

    public void addInterfaceRow(SchemaItem item,StringBuilder interfaces,String namespace){
        List<String> generics = extractGenerics(item.interfaces);
        if(exCloudInterface.contains(generics.getFirst())){
            if(item.hasChildren()){
                item.getChildren().forEach(child->{
                    this.addInterfaceRow(child,interfaces,namespace);
                });
            }
        }else {
            if(item.hasChildren()){
             AtomicReference<String> template = new AtomicReference<>(String.format("interface %s {\n", item.interfaces));
             List<SchemaItem>  cache = new ArrayList<>();
             item.getChildren().forEach(child->{
                 String v = child.interfaces;
                 if(Objects.equals(v, "Integer")){
                     v = "number";
                 }else if(Objects.equals(v, "Boolean")){
                     v = "boolean";
                 }else if(Objects.equals(v, "String")){
                     v = "string";
                 }
                 if(child.required){
                     String temp = String.format("/**\n * %s\n */%s: %s;\n",child.description,child.key,v);
                     template.set(template + temp);
                 }else {
                     String temp = String.format("/**\n * %s\n */%s?: %s;\n",child.description,child.key,v);
                     template.set(template + temp);
                 }
                 if(child.hasChildren()){
                     cache.add(child);
                 }
             });
             template.set(template+"}\n");
             interfaces.append(template);
                cache.forEach(c->addInterfaceRow(c,interfaces,namespace));
            }
        }
    };


    private static List<String> extractGenerics(String input) {
        // 匹配尖括号内的内容
       return Arrays.stream(input.split("<")).map(s -> s.replaceAll(">","")).toList();
        // 如果没有匹配到泛型，直接添加当前字符串
    }

}
