package com.hejz.generate.entity;

import com.hejz.generate.utils.StringUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 设置
 */
@Data
public class Settings {
    /**
     * 项目名称
     */
    private String project = "example";
    /**
     * 项目包名称
     */
    private String pPackage = "com.example.demo";
    /**
     * 二级包名
     */
    private String packageName;
    /**
     * 项目中文描述信息
     */
    private String projectComment;
    /**
     * 作者
     */
    private String author;
    /**
     * 第一层路径
     */
    private String path1 = "com";
    /**
     * 第二层路径
     */
    private String path2 = "example";
    /**
     * 第三层路径
     */
    private String path3 = "demo";
    /**
     * 完整的路径——拼接的
     */
    private String pathAll;

    public Settings(String project, String pPackage, String projectComment, String author) {
        if (StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if (StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.projectComment = projectComment;
        this.author = author;
        String[] paths = pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length > 1 ? paths[1] : path2;
        path3 = paths.length > 2 ? paths[2] : path3;
        pathAll = pPackage.replaceAll(".", "/");
    }

    /**
     * 反射机制把settings属性和值放进map中
     * @return
     */
    public  Map<String,Object> getSettingMap(){
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = Settings.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(),field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
