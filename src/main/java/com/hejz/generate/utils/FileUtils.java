package com.hejz.generate.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    //文件处理工具类
    //得到相对路径
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file)) {
            return "";
        }
        if (baseDir.getParentFile() == null) {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        } else {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
        }
    }

    //查询整个目录下的所有文件
    public static List<File> searchAllFile(File dir) throws IOException {
        ArrayList arrayList = new ArrayList();
        searchFiles(dir, arrayList);
        return arrayList;

    }

    //递归获取某个目录下的所有文件
    public static void searchFiles(File dir, List<File> collector) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                searchFiles(files[i], collector);
            }
        } else {
            collector.add(dir);
        }
    }

    //创建文件
    public static File mkdir(String dir, String file) {
        if (dir == null) {
            throw new IllegalArgumentException("文件夹不许为空");
        }
        File result = new File(dir, file);
        if (result.getParentFile() != null) {
            result.getParentFile().mkdirs();
        }
        return result;
    }
}