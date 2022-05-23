package com.hejz.generate.entity;

import lombok.Data;

/**
 * 表外键
 */
@Data
public class ImportedKey {
    /**
     * 外键列名
     */
    private String fkColumnName;
    /**
     * 导入的主键表java名称
     */
    private String javaBeanName;
    /**
     * 导入的外键表
     */
    private String pkTableName;
    /**
     * 导入的主键表名称
     */
    private String pkColumnName;
}
