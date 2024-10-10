package com.example.apifox.utils;

import com.example.apifox.model.MethodType;
import com.example.apifox.model.TreeItemVO;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.openapi.util.NullUtils.notNull;

public class FileOperation {

    String template = PropertiesComponent.getInstance().getValue("ApiFox.Template");
    String apiDir = PropertiesComponent.getInstance().getValue("ApiFox.ApiDir");
    String interfaceDir = PropertiesComponent.getInstance().getValue("ApiFox.InterfaceDir");

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
                Files.writeString(filePath, fileContent+ System.lineSeparator(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);

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

        String template = """
                    export const %s = (params:V) => http.get("%s",params);
                    """;
        String content = String.format(template,item.getUrl(),item.getUrl());

        clear(project,apiDir+"/"+item.getUrl()+ ".ts");
        item.getChildren().forEach(child -> {
          String interfaced = InterfaceFormat(child);
//        String api =     ApiFormat(child,item.getUrl());
//         write(project,apiDir,item.getUrl(),api);
        });

    }

    public String ApiFormat(TreeItemVO item,String fileName){
        String [] tags = item.getUrl().split("/");
        String name = tags[tags.length-1];
        if(item.getMethod() == MethodType.GET){
            if(notNull(item.query)){
                String template = """
                   /*
                    * %s
                    * GET %s
                   */
                   export const %s: (params: API.%s.%s.%s) => Promise<API.%s.%s.%s> = (params: API.%s.%s.%) => http.get('%s', { params });""";
                return String.format(template,item.getTitle(),item.getUrl(),name,fileName,name,item.query.interfaces,fileName,name,item.response.interfaces,fileName,name,item.query.interfaces,item.getUrl());
            }else {
                String template = """
                   /*
                    * %s
                    * GET %s
                   */
                   export const %s: () => Promise<API.%s.%s.%s> = () => http.get('%s');""";
                return String.format(template,item.getTitle(),item.getUrl(),name,fileName,item.response.interfaces,fileName,item.getUrl());
            }

        }else{
            String template = """
                   /*
                    * %s
                    * POST %s
                   */
                   export const %s: (params: API.%s.%s.%s) => Promise<API.%s.%s.%s> = (params: API.%s.%s.%s) => http.post('%s', params);""";
            return String.format(template,item.getTitle(),item.getUrl(),name,fileName,name,item.body.interfaces,fileName,name,item.response.interfaces,fileName,name,item.body.interfaces,item.getUrl());
        }
    }

    public String InterfaceFormat(TreeItemVO item){
        extractGenerics(item.response.interfaces);
        return item.getTitle();
    }


    private static void extractGenerics(String input) {
        // 匹配尖括号内的内容
       String[] generics =  Arrays.stream(input.split("<")).map(s -> s.replaceAll(">","")).toArray(String[]::new);

        // 如果没有匹配到泛型，直接添加当前字符串
        System.out.println(generics);


    }

}
