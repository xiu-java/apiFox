package com.example.apifox.utils;

import com.example.apifox.model.TreeItemVO;
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

public class FileOperation {


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

    public void createApi(Project project,TreeItemVO item){

        String template = """
                    export const %s = (params:V) => http.get("%s",params);
                    """;
        String content = String.format(template,item.getUrl(),item.getUrl());

        this.write(project,"src/main/resources/api",item.getTitle(),content);

    }


}
