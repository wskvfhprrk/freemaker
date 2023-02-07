package com.hejz.generate.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据表类
 */
@Data
public class Table {
    /**
     * 表名称
     */
    private String name;
    /**
     * 处理后的表名称
     */
    private String className;
    /**
     * 数据库表备注信息——注意数据库备注信息不要写XXX表——带表注释
     */
    private String tableComment;
    /**
     * 主键列——多主键以逗号隔开
     */
    private String key;
    /**
     * 列集合
     */
    private List<Column> columns;
    /**
     * 表外键——可能为空集体——写进了Colum中了，现在没有意义了
     */
    private List<ImportedKey> importedKeys;
}
