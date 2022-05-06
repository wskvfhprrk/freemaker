package com.hejz.generate.entity;

import lombok.Data;

/**
 * 表中列参数类
 */
@Data
public class Column {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列名称(处理后的列名称)
     */
    private String javaBeanName;
    /**
     * 列类型
     */
    private String columnJavaType;
    /**
     * 列数据库类型
     */
    private String columnDbType;
    /**
     * 列备注——中文信息
     */
    private String columnComment;
    /**
     * 是否是主键
     */
    private Boolean isKey;
    /**
     * 主键是否自增：true是自增，false不是自增
     */
    private Boolean isAutoincrement;
    /**
     * 是否含有空值null：1可以为空，0不能为空
     *
     */
    private int isNullable;
    /**
     * 最大数据长度——结果集中的指定列的正常最大宽度（以字符为单位）。 
     */
    private int columnDisplaySize;
    /**
     * 是否唯一索引导
     */
   // private Boolean isUnique;
}
